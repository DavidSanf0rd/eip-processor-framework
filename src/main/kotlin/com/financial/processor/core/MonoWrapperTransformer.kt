package com.financial.processor.core

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono


@Component("financial-processor.mono-wrapper-transformer")
class MonoWrapperTransformer {
    fun wrap(message: FinancialMessage): Mono<FinancialMessage> {
        return Mono.just(message)
    }
}