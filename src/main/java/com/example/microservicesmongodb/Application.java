package com.example.microservicesmongodb;

import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Log4j2
@SpringBootApplication
public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        logger.trace("TRACE level message: Detailed trace message.");
        logger.debug("DEBUG level message: Debugging information.");
        logger.info("INFO level message: Informational message.");
        logger.warn("WARN level message: Warning message.");
        logger.error("ERROR level message: Error message.");

        log.trace("This is a TRACE level message");
        log.debug("This is a DEBUG level message");
        log.info("This is an INFO level message");
        log.warn("This is a WARN level message");
        log.error("This is an ERROR level message");
        log.fatal("This is an FATAL level message");
    }

}
