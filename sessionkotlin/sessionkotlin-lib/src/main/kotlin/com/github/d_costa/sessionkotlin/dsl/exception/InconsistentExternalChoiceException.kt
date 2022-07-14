package com.github.d_costa.sessionkotlin.dsl.exception

import com.github.d_costa.sessionkotlin.dsl.SKRole

/**
 * Thrown when a choice is not consistent from the viewpoint of the projected role.
 */
internal class InconsistentExternalChoiceException(role: SKRole, activators: Collection<SKRole>) :
    SessionKotlinDSLException("Inconsistent external choice: role $role activated by [${activators.joinToString()}]")
