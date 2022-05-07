import com.github.h0tk3y.betterParse.grammar.parseToEnd
import org.david.parser.grammar
import org.david.symbols.*
import org.david.symbols.variable.toVar
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class ComparisonTest {
    @Test
    fun `test equality`() {
        val ast = grammar.parseToEnd("a == 2")
        assertEquals(Eq(Name("a"), cInt(2)), ast)
        assert(ast.value(mapOf("a" to 2.toVar())))
        assertFalse(ast.value(mapOf("a" to 3.toVar())))

        val ast2 = grammar.parseToEnd("4 == b")
        assertEquals(Eq(cInt(4), Name("b")), ast2)
        assert(ast2.value(mapOf("b" to 4.toVar())))
        assertFalse(ast2.value(mapOf("b" to 3.toVar())))
    }

    @Test
    fun `test inequality`() {
        val ast = grammar.parseToEnd("a != b")
        assertEquals(Neq(Name("a"), Name("b")), ast)
        assertEquals(true, ast.value(mapOf("a" to 2.toVar(), "b" to 4.toVar())))

        val ast2 = grammar.parseToEnd("4 != 4")
        assertEquals(Neq(cInt(4), cInt(4)), ast2)
        assertFalse(ast2.value(emptyMap()))
    }

    @Test
    fun `test lower`() {
        val ast = grammar.parseToEnd("a + 2 < 10")
        assertEquals(Lower(Plus(Name("a"), cInt(2)), cInt(10)), ast)
        assert(ast.value(mapOf("a" to 2.toVar())))

        val ast2 = grammar.parseToEnd("2 <= 0 + 1")
        assertEquals(LowerEq(cInt(2), Plus(cInt(0), cInt(1))), ast2)
        assertFalse(ast2.value(emptyMap()))
    }

    @Test
    fun `test greater`() {
        val ast = grammar.parseToEnd("i - 1 > 0")
        assertEquals(Greater(Minus(Name("i"), cInt(1)), cInt(0)), ast)
        assertFalse(ast.value(mapOf("i" to 1.toVar())))

        val ast2 = grammar.parseToEnd("b >= c")
        assertEquals(GreaterEq(Name("b"), Name("c")), ast2)
        assert(ast2.value(mapOf("b" to 1.toVar(), "c" to 1.toVar())))
    }

    @Test
    fun `test negation`() {
        val ast = grammar.parseToEnd("!a == 2")
        assertEquals(Not(Eq(Name("a"), cInt(2))), ast)
        assertFalse(ast.value(mapOf("a" to 2.toVar())))
        assert(ast.value(mapOf("a" to 3.toVar())))

        val ast2 = grammar.parseToEnd("!4 == b")
        assertEquals(Not(Eq(cInt(4), Name("b"))), ast2)
        assertFalse(ast2.value(mapOf("b" to 4.toVar())))
        assert(ast2.value(mapOf("b" to 3.toVar())))
    }
}
