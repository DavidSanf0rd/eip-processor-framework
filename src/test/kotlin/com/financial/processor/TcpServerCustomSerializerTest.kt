package com.financial.processor

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ImportResource
import org.springframework.integration.channel.QueueChannel
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.support.MessageBuilder
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

@SpringBootTest
@ContextConfiguration(
    locations = ["TcpServerCustomSerializer-context.xml"],
    classes = [Application::class],
    initializers = [ConfigDataApplicationContextInitializer::class]
)
@RunWith(SpringJUnit4ClassRunner::class)
@ActiveProfiles("test")
@ImportResource("integration_test_context.xml")
class TcpServerCustomSerializerTest {

    @Autowired
    lateinit var bytesToStringChannel: MessageChannel;

    @Autowired
    lateinit var testOutputChannel: QueueChannel;

    @Test
    fun shouldReplyWithEchoWhenReceivedAEchoMessage() {
        val inputIsoMessage =
            """
            0800822000000000000004000000000000000428132710123456001
            """.trimIndent()

        val message = MessageBuilder.withPayload(inputIsoMessage.encodeToByteArray()).build()
        bytesToStringChannel.send(message)
        val result = testOutputChannel.receive(0)
        val resultPayload = result.payload as String

        assert(resultPayload == inputIsoMessage)
    }

    @Test
    fun shouldSuccessfullyReplyA0210WhenReceivedA0200() {
        val inputIsoMessage =
            """
            020072244480288080001721123443211234111000300000000012300030405413300120502055399022003400340000000504=222511112340000123020630500001429110001840
            """.trimIndent()

        val expected0210 =
            """
            0210722444802A8080001721123443211234111000300000000012300030405413300120502055399022003400340000000504=22251111234000012302063050000140029110001840
            """.trimIndent()


        val message = MessageBuilder.withPayload(inputIsoMessage.encodeToByteArray()).build()
        bytesToStringChannel.send(message)
        val result = testOutputChannel.receive(0)
        val resultPayload = result.payload as String

        assert(resultPayload == expected0210)
    }
}