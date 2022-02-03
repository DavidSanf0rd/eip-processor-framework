package com.financial.processor.core.log

import com.financial.processor.core.FinancialMessage
import com.solab.iso8583.IsoMessage


class IsoLogger {

    fun log(isoMessage: FinancialMessage) {
        try {

            var sourceTitle = when (isoMessage.type) {
                FinancialMessage.Type.INCOMING -> "Incomming from"
                FinancialMessage.Type.OUTGOING -> "Outgoing to"
            }
            if (isoMessage == null) {
                val output = StringBuilder()
                output.append("\n ============ ISO debug start =============\n")
                output.append(" ... ISO message is null \n")
                output.append(" ============ ISO debug end ===============")
                return
            }
            val j8583IsoMessage: IsoMessage = isoMessage.fields
            var numberOfFields = 0
            val operation = "0x" + Integer.toHexString(j8583IsoMessage.type)
            val output = StringBuilder()
            output.append("\n ============ ISO debug start =============\n")
//            output.append(" ... $sourceTitle : $sourceName\n")
            val header = j8583IsoMessage.isoHeader
            val hasHeader = header != null
            if (hasHeader) {
                output.append(
                    """ ... Message Header: ${j8583IsoMessage.isoHeader}
"""
                )
            }
            output.append(" ... Message Type  : $operation\n")
            buildBitmaps(output, j8583IsoMessage)
            for (i in 2..128) {
                if (j8583IsoMessage.hasField(i)) {
                    numberOfFields++
                    val log = String.format(
                        "%-3s (%-8s,%3d): [%s]\n",
                        i,
                        j8583IsoMessage.getField<Any>(i).type,
                        j8583IsoMessage.getField<Any>(i).length,
                        j8583IsoMessage.getField<Any>(i).toString()
                    )
                    output.append(log)
                }
            }
            output.append("... Total of Fields: $numberOfFields\n")
            output.append(" ============ ISO debug end ===============")
//            log.info(output.toString())
        } catch (ex: Exception) {
//            log.error("Error printing message ISO", ex)
        }
    }

    private fun buildBitmaps(output: StringBuilder, msg: IsoMessage) {
        val primaryBitmap = readPrimaryBitmap(msg)
        output.append(" ... Primary Bitmap: ").append(primaryBitmap).append("\n")
        val firstHex = Character.toString(primaryBitmap[0])
        val firstHexValue = firstHex.toInt(16)
        if (firstHexValue >= 8) {
            val secondaryBitmap = readSecondaryBitmap(msg)
            output.append(" ... Secondary Bitmap: ").append(secondaryBitmap).append("\n")
        }
    }

    private fun readPrimaryBitmap(msg: IsoMessage): String {
        val msgTypeSize = 4
        val primaryBitmapSize = 16
        var primaryBitmapBeginIndex = msgTypeSize
        var primaryBitmpaEndIndex = primaryBitmapSize + msgTypeSize
        if (msg.isoHeader != null) {
            val headerLength = msg.isoHeader.length
            primaryBitmapBeginIndex = primaryBitmapBeginIndex + headerLength
            primaryBitmpaEndIndex = primaryBitmpaEndIndex + headerLength
        }
        return msg.debugString().substring(primaryBitmapBeginIndex, primaryBitmpaEndIndex)
    }

    private fun readSecondaryBitmap(msg: IsoMessage): String {
        val msgTypeSize = 4
        val primaryBitmapSize = 16
        val secondaryBitmapSize = 16
        var secondaryBitmapBeginIndex = msgTypeSize + primaryBitmapSize
        var secondaryBitmpaEndIndex = msgTypeSize + primaryBitmapSize + secondaryBitmapSize
        if (msg.isoHeader != null) {
            val headerLength = msg.isoHeader.length
            secondaryBitmapBeginIndex = secondaryBitmapBeginIndex + headerLength
            secondaryBitmpaEndIndex = secondaryBitmpaEndIndex + headerLength
        }
        return msg.debugString().substring(secondaryBitmapBeginIndex, secondaryBitmpaEndIndex)
    }
}