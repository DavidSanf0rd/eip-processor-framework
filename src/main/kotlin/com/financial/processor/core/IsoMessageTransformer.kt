package com.financial.processor.core

import com.financial.processor.core.log.IsoLogger
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct


@Component("financial-processor.iso-message-transformer")
class IsoMessageTransformer @Autowired constructor(private val messageFactory: IsoMessageFactory, private val isoLogger: IsoLogger) {

    @Value("\${financial-processor.iso8583.header-lenght}")
    val headerLength = 0

    @PostConstruct
    fun logHeaderLength() {
        print("Iso header length: $headerLength\n")
    }

    fun transformToString(message: FinancialMessage): String {
        val isoMessageString = String(message.fields.writeData())
        log.debug("Outbound iso message to string: $isoMessageString")
        return isoMessageString
    }

    fun transformFromString(message: String): FinancialMessage {
        log.debug("Incoming iso message: $message")
        return messageFactory.buildIsoMessage(message, headerLength, FinancialMessage.Type.INCOMING) //TODO: parameterize header
    }

    companion object {
        private val log = LogFactory.getLog(IsoMessageTransformer::class.java)
    }
}