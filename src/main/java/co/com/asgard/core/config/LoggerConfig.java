package co.com.asgard.core.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LoggerConfig {

    private static final Logger logger = LoggerFactory.getLogger(LoggerConfig.class);

    public void processData(String data) {
        logger.info("Iniciando procesamiento de datos: {}", data);

        try {
            if (data == null) {
                throw new IllegalArgumentException("Los datos no pueden ser nulos");
            }
            logger.debug("Datos procesados correctamente: {}", data);
        } catch (Exception e) {
            logger.error("Error procesando los datos: {}", e.getMessage(), e);
        }
    }

}