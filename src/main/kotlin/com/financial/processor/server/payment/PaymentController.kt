package com.financial.processor.server.payment

import com.financial.processor.core.FinancialMessage
import com.financial.processor.core.FinancialMessage.Type.OUTGOING
import com.financial.processor.core.IsoMessageFactory
import com.financial.processor.core.log.IsoLogger
import com.financial.processor.server.routes.InboundRoutesProvider
import com.solab.iso8583.IsoType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.integration.annotation.ServiceActivator
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.util.*

@Component
class PaymentController @Autowired constructor(
    private val isoLogger: IsoLogger,
    private val messageFactory: IsoMessageFactory
) {

    @ServiceActivator(inputChannel = InboundRoutesProvider.PAYMENT, outputChannel = "outbound-mono-unwraper-channel")
    fun processPayment(rintisRequestMono: Mono<FinancialMessage>): Mono<FinancialMessage> =
        rintisRequestMono
            .doOnNext(isoLogger::log)
            .map(this::processPayment)
            .doOnNext(isoLogger::log)


    private fun processPayment(financialMessage: FinancialMessage): FinancialMessage {
        val fields = financialMessage.fields

        val date = fields.getObjectValue<Date>(7)
        val auditNumber = fields.getObjectValue<String>(11)

        val responseFields = messageFactory.j8583Factory.newMessage(0x210)
        responseFields.copyFieldsFrom(fields, 2,3,4,7, 11, 14, 18, 22, 25, 35, 37, 41, 49)
        responseFields.setValue(39, "00", IsoType.ALPHA, 2)

        return FinancialMessage(
            isoMessageTypeIndicator = "0210",
            type = OUTGOING,
            fields = responseFields,
            isoSystemTraceAuditNumber = auditNumber,
            isoDate = date.toString()
        )
    }
}