package com.financial.processor.core

import org.springframework.stereotype.Component
import org.springframework.stereotype.Service

private const val CHANNEL_PREFIX = "financial-processor"
private const val ISSUING_ROUTE_HEADER = "issuing-route-header"

@Component("financial-processor.inbound-enricher")
@Service //TODO: remove this
class InboundHeaderEnricher {

    fun addRouteHeader(message: FinancialMessage): Map<String, Any> {
        val headersToAdd = HashMap<String, Any>()
        val route: String = CHANNEL_PREFIX + message.isoMessageTypeIndicator
        headersToAdd[ISSUING_ROUTE_HEADER] = route
        return headersToAdd
    }
}
