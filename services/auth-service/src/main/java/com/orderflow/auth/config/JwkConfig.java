package com.orderflow.auth.config;

import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.orderflow.auth.domain.entity.SigningKey;
import com.orderflow.auth.enums.KeyStatus;
import com.orderflow.auth.repository.SigningKeyRepository;

@Configuration
public class JwkConfig {

	private static final Logger log = LoggerFactory.getLogger(JwkConfig.class);

	@Bean
	public JWKSource<SecurityContext> jwkSource(SigningKeyRepository repo) {
		return (jwkSelector, context) -> {
			JWKSet jwkSet = fetchJwkSet(repo); // Calls cached method
			log.debug("JWKSet loaded with {} keys", jwkSet.getKeys().size());
			return jwkSelector.select(jwkSet);
		};
	}

	@Bean
	public JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
		return new NimbusJwtEncoder(jwkSource);
	}

	/**
	 * Fetches keys from the database and caches the result. Cache is automatically
	 * evicted when keys are added/rotated.
	 */
	@Cacheable(value = "jwkSet", unless = "#result == null || #result.getKeys().isEmpty()")
	public JWKSet fetchJwkSet(SigningKeyRepository repo) {
		log.info("Loading signing keys from database (cache miss)");
		List<SigningKey> keys = repo.findByStatusIn(List.of(KeyStatus.ACTIVE, KeyStatus.RETIRING));

		if (keys.isEmpty()) {
			log.warn("No ACTIVE or RETIRING keys found in the database");
			return new JWKSet(List.of());
		}

		// 2. Sort: ACTIVE first, then RETIRING.
		// This ensures Nimbus picks the first ACTIVE key for signing new tokens.
		keys.sort(Comparator.comparingInt(k -> k.getStatus() == KeyStatus.ACTIVE ? 0 : 1));

		List<JWK> jwks = new ArrayList<>();

		for (SigningKey key : keys) {
			jwks.add(toRsaKey(key));
		}

		log.info("Loaded {} signing keys (ACTIVE: {}, RETIRING: {})", jwks.size(),
				jwks.stream().filter(k -> k.getKeyUse() == KeyUse.SIGNATURE).count(),
				keys.stream().filter(k -> k.getStatus() == KeyStatus.RETIRING).count());

		return new JWKSet(jwks);
	}

	private RSAKey toRsaKey(SigningKey k) {
		try {
			RSAPublicKey pub = readPublicKey(k.getPublicKey());
			RSAPrivateKey priv = readPrivateKey(k.getPrivateKey());

			return new RSAKey.Builder(pub).privateKey(priv).keyID(k.getKid()).algorithm(JWSAlgorithm.RS256)
					.keyUse(KeyUse.SIGNATURE).build();
		} catch (Exception e) {
			throw new IllegalStateException("Failed to load signing key: " + k.getKid(), e);
		}
	}

	private RSAPublicKey readPublicKey(String pem) throws Exception {
		String stripped = pem.replace("-----BEGIN RSA PUBLIC KEY-----", "")
				.replace("-----END RSA PUBLIC KEY-----", "")
				.replaceAll("\\s", "");

		byte[] decoded = Base64.getDecoder().decode(stripped);
		KeyFactory factory = KeyFactory.getInstance("RSA");
		
		return (RSAPublicKey) factory.generatePublic(new X509EncodedKeySpec(decoded));
	}

	private RSAPrivateKey readPrivateKey(String pem) throws Exception {
		String stripped = pem.replace("-----BEGIN RSA PRIVATE KEY-----", "")
				.replace("-----END RSA PRIVATE KEY-----", "")
				.replaceAll("\\s", "");

		byte[] decoded = Base64.getDecoder().decode(stripped);
		return (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
	}
}
