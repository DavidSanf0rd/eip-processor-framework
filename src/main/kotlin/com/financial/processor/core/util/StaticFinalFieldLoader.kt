package com.financial.processor.core.util

import org.springframework.stereotype.Component
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.full.companionObjectInstance

@Component
class StaticFinalFieldsLoader {

    @Suppress("UNCHECKED_CAST")
    fun getFields(classType: KClass<*>): List<String> {
        val constantFields = ArrayList<String>()

        val companionObjectInstance = classType.companionObjectInstance ?: throw IllegalStateException("Class should have a companion")
        val companionProperties = companionObjectInstance::class.members.filter { it.isFinal }.map { it as KProperty1<Any, String> }
        for (property in companionProperties) {

            if (property.isFinal && property.isConst) {
                val value: String = property.get(companionObjectInstance)
                constantFields.add(value)
            }
        }
        return constantFields
    }
}