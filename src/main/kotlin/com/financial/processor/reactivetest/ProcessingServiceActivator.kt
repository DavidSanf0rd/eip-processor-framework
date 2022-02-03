package com.financial.processor.reactivetest

import org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE
import org.springframework.integration.annotation.ServiceActivator
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


@Controller
class ProcessingServiceActivator {

    @ServiceActivator(inputChannel = "test" , outputChannel = "fluxChannel")
    fun processingServiceActivator(input: Mono<String>): Mono<String> {
        return input;
    }

//    @ServiceActivator(inputChannel = "fluxChannel" , outputChannel = "fluxChannel", reactive = Reactive("bean"))
//    fun processingServiceActivatorOutput(input: Mono<String>) {
//        return input;
//    }

    @GetMapping("/event" , produces = [TEXT_EVENT_STREAM_VALUE])
    fun getPatientAlerts(): Flux<String> {
        return Flux
    }


    @GetMapping("/generate/{text}")
    fun generateJmsMessage(@PathVariable text: String) {

    }
}