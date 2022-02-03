package com.financial.processor.core

interface IsoCustomRouter {
    fun mainRoute(): String
    fun route(message: FinancialMessage): String
}