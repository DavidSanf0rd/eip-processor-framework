package com.financial.processor

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ImportResource
import org.springframework.integration.annotation.IntegrationComponentScan

@SpringBootApplication
@ImportResource("connector.xml")
@IntegrationComponentScan("com.financial.processor")
open class Application

fun main(args: Array<String>) {
    System.setProperty("spring.config.location", "optional:classpath:application.yml, optional:file:/etc/seven/application.yml")
    runApplication<Application>(*args)
}