package org.david.sessionkotlin_lib.dsl.exception

import org.david.sessionkotlin_lib.dsl.TerminalInstruction

public class TerminalInstructionException internal constructor(i: TerminalInstruction) :
    SessionKotlinException("${i.simpleName()} is a terminal operation.")
