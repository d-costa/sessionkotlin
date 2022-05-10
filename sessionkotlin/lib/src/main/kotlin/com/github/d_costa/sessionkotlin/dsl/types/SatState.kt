package com.github.d_costa.sessionkotlin.dsl.types

import com.github.d_costa.sessionkotlin.dsl.exception.InvalidRefinementValueException
import com.github.d_costa.sessionkotlin.parser.RefinementParser
import com.github.d_costa.sessionkotlin.parser.symbols.*
import org.sosy_lab.java_smt.api.FormulaManager
import org.sosy_lab.java_smt.api.SolverContext

internal class SatState(
    private val ctx: SolverContext,
) {
    private enum class SmtSort(val value: String) {
        STRING("String"), INT("Int"), REAL("Real");
        override fun toString() = value
    }

    private data class SMTVariable(val id: String, val sort: SmtSort)

    private var formulaManager: FormulaManager = ctx.formulaManager
    private var variables: MutableSet<SMTVariable> = mutableSetOf()
    private var constraints: MutableList<String> = mutableListOf()

    fun addVariable(id: String, type: Class<*>) {
        val sort: SmtSort = when (type) {
            Long::class.javaObjectType -> SmtSort.INT
            Int::class.javaObjectType -> SmtSort.INT
            Short::class.javaObjectType -> SmtSort.INT
            Byte::class.javaObjectType -> SmtSort.INT
            Double::class.javaObjectType -> SmtSort.REAL
            String::class.javaObjectType -> SmtSort.STRING
            else -> throw InvalidRefinementValueException(type)
        }

        variables.add(SMTVariable(id, sort))
    }

    fun addCondition(condition: String) {
        val ast = RefinementParser.parseToEnd(condition)
        constraints.add(ast.toSMT())
    }

    private fun BooleanExpression.toSMT(): String =
        when (this) {
            True -> "TRUE"
            False -> "FALSE"

            is Impl -> "(=> ${c1.toSMT()} ${c2.toSMT()})"
            is Not -> "(not ${cond.toSMT()})"
            is And -> "(and ${c1.toSMT()} ${c2.toSMT()})"
            is Or -> "(or ${c1.toSMT()} ${c2.toSMT()})"

            is Eq -> "(= ${e1.toSMT()} ${e2.toSMT()})"
            is Neq -> "(not (= ${e1.toSMT()} ${e2.toSMT()}))"
            is Greater -> "(> ${e1.toSMT()} ${e2.toSMT()})"
            is GreaterEq -> "(>= ${e1.toSMT()} ${e2.toSMT()})"
            is Lower -> "(< ${e1.toSMT()} ${e2.toSMT()})"
            is LowerEq -> "(<= ${e1.toSMT()} ${e2.toSMT()}))"
        }

    private fun Term.toSMT(): String =
        when (this) {
            is Const -> "${v.value}"
            is Minus -> "(- ${t1.toSMT()} ${t2.toSMT()})"
            is Name -> id
            is Neg -> "(- ${t.toSMT()})"
            is Plus -> "(+ ${t1.toSMT()}, ${t2.toSMT()})"
        }

    fun satisfiable(): Boolean {
        ctx.newProverEnvironment().use { prover ->
            if (constraints.isNotEmpty()) {
                val declaration = StringBuilder()

                for (v in variables) {
                    declaration.append("(declare-fun ${v.id} () ${v.sort})")
                }

                declaration.append("(assert (and ${constraints.joinToString(" ")}))")
                prover.addConstraint(formulaManager.parse(declaration.toString()))
                return !prover.isUnsat
            } else {
                return true
            }
        }
    }

    fun clone(): SatState {
        val s = SatState(ctx)
        s.variables = variables.toMutableSet()
        s.constraints = constraints.toMutableList()
        return s
    }
}
