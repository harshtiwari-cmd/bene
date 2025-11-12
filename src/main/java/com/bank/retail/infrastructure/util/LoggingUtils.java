package com.bank.retail.infrastructure.util;

public class LoggingUtils {
    public static void loggingEvent(Class<?> clazz, String refNum, String operationType, String serviceName,
                                    String description, String requestXml, String responseXml,
                                    String statusCode, String statusMessage, String arg1, String arg2, String arg3) {
        // For local testing, just print logs
        System.out.println("LOG: " + description + " | Status: " + statusCode);
    }
}
