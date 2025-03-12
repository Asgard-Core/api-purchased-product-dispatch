package co.com.asgard.core.config;

import org.slf4j.MDC;

public class LoggerContext {

    private static final String UUID_KEY = "correlationId";
    private static final String BUSINESS_KEY = "business";
    private static final String APP_KEY = "app";

    public static void setUuid(String correlationId) {
        MDC.put(UUID_KEY, correlationId);
    }

    public static String getUuid() {
        return MDC.get(UUID_KEY);
    }

    public static void setBusiness(String business) {
        MDC.put(BUSINESS_KEY, business);
    }

    public static String getBusiness() {
        return MDC.get(BUSINESS_KEY);
    }

    public static void setApp(String app) {
        MDC.put(APP_KEY, app);
    }

    public static String getApp() {
        return MDC.get(APP_KEY);
    }

    public static void clear() {
        MDC.clear();
    }
}