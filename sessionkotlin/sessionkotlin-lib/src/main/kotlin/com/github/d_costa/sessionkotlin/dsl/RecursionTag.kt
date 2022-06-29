package com.github.d_costa.sessionkotlin.dsl

/**
 * Recursion marker.
 *
 * Returned by [GlobalEnv.mu] and used in [GlobalEnv.goto] instructions.
 */
public class RecursionTag internal constructor() {
    override fun toString(): String {
        return "${this::class.simpleName}@${hashCode()}"
    }
}
