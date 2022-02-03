package com.financial.processor.core

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component


@Component("financial-processor.iso-message-transformer")
class IsoMessageTransformer @Autowired constructor(private val messageFactory: IsoMessageFactory) {

    fun transformToString(message: FinancialMessage): String {
        val isoMessageString = String(message.fields.writeData())
//        log.debug("Outbound iso message to string: {}", isoMessageString)
        return isoMessageString
    }

    fun transformFromString(message: String): FinancialMessage {
//        log.debug("Incoming iso message: {}", message)
        return messageFactory.buildIsoMessage(message, 12, FinancialMessage.Type.INCOMING)
    }
}