package com.financial.processor.core

import com.solab.iso8583.IsoMessage

data class FinancialMessage(
     val isoMessageTypeIndicator: String,
     val type: Type,
     val fields: IsoMessage,
     val isoSystemTraceAuditNumber: String,
     val isoDate: String) {

    enum class Type {
        INCOMING, OUTGOING;
    }
}
