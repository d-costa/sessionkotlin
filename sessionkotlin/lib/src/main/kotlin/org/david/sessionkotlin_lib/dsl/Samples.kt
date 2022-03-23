package org.david.sessionkotlin_lib.dsl

class Samples {

    fun send() {
        val a = Role("A")
        val b = Role("B")
        val s = Role("C")

        globalProtocol {
            send<String>(a, s)
            send<Long>(s, a)
            send<Long>(s, b)
            send<Long>(b, s)
        }
    }

    fun sendTypes() {
        val a = Role("A")
        val b = Role("B")
        val s = Role("C")

        globalProtocol {
            send(a, s, String::class.java)
            send(s, a, Long::class.java)
            send(s, b, Long::class.java)
            send(b, s, Long::class.java)
        }
    }

    fun choice() {
        val a = Role("A")
        val b = Role("B")

        globalProtocol {
            choice(b) {
                case("Ok") {
                    send<String>(b, a)
                }
                case("Quit") {
                    send<Long>(b, a)
                }
            }
        }
    }

    fun exec() {
        val a = Role("A")
        val b = Role("B")
        val c = Role("C")

        val case1 = globalProtocol {
            send<Int>(b, a)
            send<Int>(a, c)
        }

        val case2 = globalProtocol {
            send<String>(b, a)
            send<String>(a, c)
        }

        globalProtocol {
            choice(b) {

                case("Case 1") {
                    exec(case1)
                }
                case("Case 2") {
                    exec(case2)
                }
            }
        }
    }

    fun rec() {
        val server = Role("Server")
        val client = Role("Client")

        globalProtocol {
            choice(client) {

                case("Add") {
                    send<Int>(client, server)
                    rec()
                }
                case("Result") {
                    send<String>(client, server)
                    send<Int>(server, client)
                }
            }

        }
    }
}
