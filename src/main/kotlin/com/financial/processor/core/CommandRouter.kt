package com.financial.processor.core

import com.financial.processor.core.util.StaticFinalFieldsLoader
import com.financial.processor.server.routes.InboundRoutesProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.handler.annotation.Header
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

private const val CHANNEL_PREFIX = "financial-processor."

@Component("financial-processor.command-router")
class CommandRouter @Autowired constructor(
    provider: InboundRoutesProvider,
    customRouters: List<IsoCustomRouter>,
    routesDeclarator: StaticFinalFieldsLoader
) {
    private val routes: List<String>
    private val customRouters: Map<String, IsoCustomRouter>

    fun route(@Header("route-header") route: String, messageMono: Mono<FinancialMessage>): String {
        val isRoutePresent = routes.contains(route)
        check(isRoutePresent) { "Iso inbound route not found: $route" }
        val isoMessage = messageMono.block() ?: throw IllegalStateException("empty mono when trying to route") //there's no problem to block here since it's a mono.just.
        if (customRouters.containsKey(route)) {
            val customRouter = customRouters[route] ?: throw IllegalStateException("Custom route should exist")
            return customRouter.route(isoMessage)
        }
//        log.info("routing inbound message to $route")
        return route
    }

    init {
        this.customRouters = customRouters.associateBy { it.mainRoute() }
        routes = routesDeclarator.getFields(provider::class)
    }
}