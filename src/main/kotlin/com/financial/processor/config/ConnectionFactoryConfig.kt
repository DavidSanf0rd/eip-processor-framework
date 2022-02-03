package com.financial.processor.config

import org.springframework.context.annotation.Bean
import org.springframework.integration.ip.tcp.serializer.ByteArrayLengthHeaderSerializer
import org.springframework.integration.ip.tcp.serializer.ByteArrayLengthHeaderSerializer.HEADER_SIZE_UNSIGNED_SHORT
import org.springframework.stereotype.Component

const val maxMessageSizeInKBytes = 100_000

@Component
class ConnectionFactoryConfig {

    @Bean("byteLengthSerializer")
    fun initCustomMessageLengthSerializer(): ByteArrayLengthHeaderSerializer {
        val byteArrayLengthHeaderSerializer = ByteArrayLengthHeaderSerializer(HEADER_SIZE_UNSIGNED_SHORT)

        byteArrayLengthHeaderSerializer.maxMessageSize = maxMessageSizeInKBytes
        return byteArrayLengthHeaderSerializer
    }
}