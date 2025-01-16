package fr.hamtec

import org.slf4j.Logger
import org.slf4j.LoggerFactory

object LogUtil {
    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    fun info(message: String) {
        log.info(message)
    }

    fun debug(message: String) {
        log.debug(message)
    }

    fun warn(message: String) {
        log.warn(message)
    }

    fun error(message: String, throwable: Throwable? = null) {
        if (throwable != null) {
            log.error(message, throwable)
        } else {
            log.error(message)
        }
    }
}
