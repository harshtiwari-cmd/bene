package com.bank.retail.infrastructure.util;

import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * Service for retrieving internationalized messages from message properties files
 * Supports English (default) and Arabic translations
 */
@Service
@AllArgsConstructor
public class MessageSourceService {
    
    private MessageSource messageSource;

    /**
     * Get message by key using current locale from context
     */
    public String getMessage(String key) {
        return getMessage(key, null, LocaleContextHolder.getLocale());
    }
    
    /**
     * Get message by key with arguments using current locale from context
     */
    public String getMessage(String key, Object[] args) {
        return getMessage(key, args, LocaleContextHolder.getLocale());
    }
    
    /**
     * Get message by key with specific locale
     */
    public String getMessage(String key, Locale locale) {
        return getMessage(key, null, locale);
    }
    
    /**
     * Get message by key with arguments and specific locale
     */
    public String getMessage(String key, Object[] args, Locale locale) {
        try {
            return messageSource.getMessage(key, args, locale);
        } catch (Exception e) {
            // If message not found, return the key itself as fallback
            return key;
        }
    }
    
    /**
     * Get message for English locale
     */
    public String getMessageEn(String key) {
        return getMessage(key, null, Locale.ENGLISH);
    }
    
    /**
     * Get message for English locale with arguments
     */
    public String getMessageEn(String key, Object[] args) {
        return getMessage(key, args, Locale.ENGLISH);
    }
    
    /**
     * Get message for Arabic locale
     */
    public String getMessageAr(String key) {
        return getMessage(key, null, new Locale("ar"));
    }
    
    /**
     * Get message for Arabic locale with arguments
     */
    public String getMessageAr(String key, Object[] args) {
        return getMessage(key, args, new Locale("ar"));
    }
    
    /**
     * Get message by language code (EN/AR - case insensitive)
     * Supports: en, En, EN, ar, Ar, AR, english, arabic
     */
    public String getMessageByLanguageCode(String key, String languageCode) {
        Locale locale = determineLocale(languageCode);
        return getMessage(key, null, locale);
    }
    
    /**
     * Get message by language code with arguments (EN/AR - case insensitive)
     * Supports: en, En, EN, ar, Ar, AR, english, arabic
     */
    public String getMessageByLanguageCode(String key, String languageCode, Object[] args) {
        Locale locale = determineLocale(languageCode);
        return getMessage(key, args, locale);
    }
    
    /**
     * Determine locale from language code (case insensitive)
     * 
     * Supported language codes:
     * - Arabic: ar, AR, Ar, aR, arabic, ARABIC, Arabic, AR-SA, ar-sa
     * - English: en, EN, En, eN, english, ENGLISH, English, EN-US, en-us
     * - Legacy: e, E (English), a, A (Arabic)
     * 
     * @param languageCode The language code in any case
     * @return Locale for the language (defaults to English if unrecognized)
     */
    private Locale determineLocale(String languageCode) {
        if (languageCode == null || languageCode.trim().isEmpty()) {
            return Locale.ENGLISH; // Default to English
        }
        
        // Convert to uppercase for case-insensitive comparison
        String upperLang = languageCode.toUpperCase().trim();
        
        // Handle Arabic variants (ar, AR, arabic, ARABIC, AR-SA, a, A)
        if (upperLang.equals("AR") || 
            upperLang.startsWith("AR-") || 
            upperLang.equals("ARABIC") ||
            upperLang.equals("A")) { // Legacy support
            return new Locale("ar");
        }
        
        // Default to English for all other cases
        // Handles: EN, en, En, ENGLISH, english, E, e, EN-US, en-us, or any unrecognized code
        return Locale.ENGLISH;
    }
}