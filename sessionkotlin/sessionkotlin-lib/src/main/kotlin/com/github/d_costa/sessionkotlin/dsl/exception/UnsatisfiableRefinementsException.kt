package com.github.d_costa.sessionkotlin.dsl.exception

/**
 * Thrown when a refinement is proved not be satisfiable.
 */
internal class UnsatisfiableRefinementsException :
    SessionKotlinDSLException("Refinement expressions are not satisfiable.")
