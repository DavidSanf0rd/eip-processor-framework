package com.financial.processor.server.echo

import com.financial.processor.core.FinancialMessage
import com.financial.processor.core.log.IsoLogger
import com.financial.processor.server.routes.InboundRoutesProvider.Companion.EXAMPLE_ROUTE
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.integration.annotation.ServiceActivator
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class EchoController @Autowired constructor(private val isoLogger: IsoLogger) {

    @ServiceActivator(inputChannel = EXAMPLE_ROUTE, outputChannel = "outbound-mono-unwraper-channel")
    fun processEchoTest(rintisRequestMono: Mono<FinancialMessage>): Mono<FinancialMessage> {
        return rintisRequestMono
            .doOnNext(isoLogger::log)
            .map(this::doSomeProcessing)
            .doOnNext(isoLogger::log)

    }

    private fun doSomeProcessing(financialMessage: FinancialMessage): FinancialMessage {
        return financialMessage;
    }
}