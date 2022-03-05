package com.financial.processor.reactivetest

import org.springframework.context.annotation.Bean
import org.springframework.integration.annotation.InboundChannelAdapter
import org.springframework.integration.annotation.Poller
import org.springframework.integration.annotation.ServiceActivator
import org.springframework.integration.channel.FluxMessageChannel
import org.springframework.integration.dsl.IntegrationFlow
import org.springframework.integration.dsl.IntegrationFlows
import org.springframework.messaging.MessageChannel
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.util.concurrent.atomic.AtomicInteger
import java.util.function.Supplier


@Component
class ProcessingServiceActivator {

//
//    @ServiceActivator(inputChannel = "fluxChannel", outputChannel ="splitInput")
//    fun processingServiceActivator(input: Int): Mono<Int> {
//        println("arrived $input")
//        return Mono.just(input)
//    }
//
//    @ServiceActivator(inputChannel = "reactiveChannel")
//    fun reactiveServiceActivator(input: Int) {
//        println("reactive subscribed:  $input")
//    }
//
//    @Bean
//    fun counter(): AtomicInteger {
//        return AtomicInteger()
//    }
//
//    @Bean
//    @InboundChannelAdapter(
//        value = "fluxChannel",
//        poller = [Poller(fixedDelay = "2000", maxMessagesPerPoll = "3", taskExecutor = "taskExecutor")]
//    )
//    fun counterMessageSupplier(counter: AtomicInteger): Supplier<Int> {
//        return Supplier<Int> {
//            val i: Int = counter.incrementAndGet()
//            if (i % 2 == 0) i else null
//        }
//    }
//
//    @Bean
//    fun fluxChannel(): MessageChannel {
//        return FluxMessageChannel()
//    }
//
//    @Bean
//    fun splitFlow(): IntegrationFlow {
//        return IntegrationFlows.from("splitInput")
//            .split()
//            .channel("reactiveChannel")
//            .get()
//    }
}