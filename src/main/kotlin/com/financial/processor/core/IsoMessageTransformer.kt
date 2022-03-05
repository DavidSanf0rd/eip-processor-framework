package com.financial.processor.core

import com.financial.processor.core.log.IsoLogger
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component


@Component("financial-processor.iso-message-transformer")
class IsoMessageTransformer @Autowired constructor(private val messageFactory: IsoMessageFactory, private val isoLogger: IsoLogger) {

    fun transformToString(message: FinancialMessage): String {
        val isoMessageString = String(message.fields.writeData())
        log.debug("Outbound iso message to string: $isoMessageString")
        return isoMessageString
    }

    fun transformFromString(message: String): FinancialMessage {
        log.debug("Incoming iso message: $message")
        return messageFactory.buildIsoMessage(message, 0, FinancialMessage.Type.INCOMING) //TODO: parameterize header
    }

    companion object {
        private val log = LogFactory.getLog(IsoMessageTransformer::class.java)
    }
}