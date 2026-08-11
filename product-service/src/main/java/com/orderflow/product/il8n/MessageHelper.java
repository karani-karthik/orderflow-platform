package com.orderflow.product.il8n;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MessageHelper {

	private static MessageSource messageSource;

	@Autowired
	public MessageHelper(MessageSource messageSource) {
		MessageHelper.messageSource = messageSource;
	}

	public static String getMessage(String key, Object... args) {
		return messageSource.getMessage(key, args, LocaleContextHolder.getLocale());
	}

	public static String getMessage(String key) {
		return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
	}

}
