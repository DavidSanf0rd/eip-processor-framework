package com.financial.processor.core

import com.solab.iso8583.IsoMessage
import com.solab.iso8583.MessageFactory
import com.solab.iso8583.parse.ConfigParser
import org.springframework.stereotype.Component

import java.text.SimpleDateFormat

private const val ISO_TEMPLATE_FILE_PATH = "iso-template/config_iso_message.xml"

@Component
class IsoMessageFactory {
    private val messageFactory: MessageFactory<IsoMessage> = ConfigParser.createFromClasspathConfig(ISO_TEMPLATE_FILE_PATH)

    fun buildIsoMessage(message: String, headerLength: Int, type: FinancialMessage.Type): FinancialMessage {
        val j8583Fields = messageFactory.parseMessage(message.toByteArray(Charsets.UTF_8), headerLength)
        return this.buildIsoMessage(j8583Fields, type)
    }

    fun buildIsoMessage(j8583Fields: IsoMessage, type: FinancialMessage.Type): FinancialMessage {
        val traceNumberString: String
        val traceNumber: Any = j8583Fields.getObjectValue(11)
        traceNumberString = when (traceNumber) {
            is String -> traceNumber
            is Int -> traceNumber.toString()
            else -> throw IllegalStateException("invalid trace number type: " + traceNumber::class.simpleName)

        }

        return FinancialMessage(
            isoMessageTypeIndicator = String.format("%04X", j8583Fields.type),
            isoSystemTraceAuditNumber = traceNumberString,
            isoDate = SimpleDateFormat("MMddHHmmss").format(j8583Fields.getObjectValue(7)),
            type = type,
            fields = j8583Fields)
    }

    val j8583Factory: MessageFactory<IsoMessage>
        get() = messageFactory

}