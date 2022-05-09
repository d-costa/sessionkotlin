package org.david.sessionkotlin.parser.symbols.values

import org.david.sessionkotlin.parser.exception.IncompatibleTypesException

public fun Byte.toVal(): ByteValue = ByteValue(this)

public data class ByteValue(public override val value: Byte) : Value(value) {
    override fun unaryMinus(): IntValue = IntValue(value.unaryMinus())
    override fun compareTo(other: Value): Int =
        when (other) {
            is ByteValue -> value.compareTo(other.value)
            is ShortValue -> value.compareTo(other.value)
            is IntValue -> value.compareTo(other.value)
            is LongValue -> value.compareTo(other.value)
            is FloatValue -> value.compareTo(other.value)
            is DoubleValue -> value.compareTo(other.value)
            else -> throw IncompatibleTypesException(this, other)
        }

    override fun minus(other: Value): Value =
        when (other) {
            is ByteValue -> IntValue(value - other.value)
            is ShortValue -> IntValue(value - other.value)
            is IntValue -> IntValue(value - other.value)
            is LongValue -> LongValue(value - other.value)
            is FloatValue -> FloatValue(value - other.value)
            is DoubleValue -> DoubleValue(value - other.value)
            else -> throw IncompatibleTypesException(this, other)
        }

    override fun plus(other: Value): Value =
        when (other) {
            is ByteValue -> IntValue(value + other.value)
            is ShortValue -> IntValue(value + other.value)
            is IntValue -> IntValue(value + other.value)
            is LongValue -> LongValue(value + other.value)
            is FloatValue -> FloatValue(value + other.value)
            is DoubleValue -> DoubleValue(value + other.value)
            else -> throw IncompatibleTypesException(this, other)
        }
}
