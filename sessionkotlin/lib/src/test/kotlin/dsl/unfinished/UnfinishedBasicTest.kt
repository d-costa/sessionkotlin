package dsl.unfinished

import com.github.d_costa.sessionkotlin.dsl.SKRole
import com.github.d_costa.sessionkotlin.dsl.exception.UnfinishedRolesException
import com.github.d_costa.sessionkotlin.dsl.globalProtocolInternal
import com.github.d_costa.sessionkotlin.dsl.types.LEnd
import com.github.d_costa.sessionkotlin.dsl.types.LocalTypeSend
import dsl.util.UnitClass
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class UnfinishedBasicTest {
    companion object {
        val a = SKRole("A")
        val b = SKRole("B")
        val c = SKRole("C")
        val d = SKRole("D")
    }

    @Test
    fun `unfinished roles`() {
        assertFailsWith<UnfinishedRolesException> {
            globalProtocolInternal {
                choice(a) {
                    branch("Case1") {
                        send<Unit>(a, b)
                        // 'c' not enabled
                        // 'b' enabled
                    }
                    branch("Case2") {
                        send<Unit>(a, c)
                        // 'c' enabled
                        // 'b' not enabled
                    }
                }
            }
        }
    }

    @Test
    fun `erasable choice`() {
        val g = globalProtocolInternal {
            send<Unit>(a, b)
            send<Unit>(c, b)
            choice(a) {
                branch("Case1") {
                    send<String>(a, b)
                }
                branch("Case2") {
                    send<Int>(a, b)
                }
                // 'c' not enabled in any branch
            }
        }
        val lC = LocalTypeSend(b, UnitClass, LEnd)
        assertEquals(g.project(c), lC)
    }
}
