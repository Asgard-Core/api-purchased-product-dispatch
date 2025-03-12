package co.com.asgard.core.config;

import co.com.asgard.core.enums.LogLevel;
import co.com.asgard.core.enums.ProcessType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LoggerPrinter {

    private static final Logger logger = LoggerFactory.getLogger(LoggerPrinter.class);

    public void log(LogLevel level, String message, String details, String statusCode, String business, ProcessType processType) {
        String logMessage = String.format(
                "[%s] [%s] [%s] - %s | Details: %s | Status Code: %s | Business: %s",
                LoggerContext.getUuid(), processType, level, message, details, statusCode, business
        );

        switch (level) {
            case INFO -> logger.info(logMessage);
            case DEBUG -> logger.debug(logMessage);
            case WARN -> logger.warn(logMessage);
            case ERROR -> logger.error(logMessage);
        }
    }
}