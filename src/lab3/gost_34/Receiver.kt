package lab3.gost_34

import java.lang.Math.pow

class Receiver(val p: Int, val q: Int, val a: Int, val y: Int) {
    fun verifySender(w1: Int, s: Int): Boolean {

        val h1 = 19

        val v = pow(h1.toDouble(), (q - 2).toDouble()).toInt().rem(q)
        val z1 = (s * v).rem(q)
        val z2 = ((q - w1) * v).rem(q)
        val u = (pow(a.toDouble(), z1.toDouble()) * pow(y.toDouble(), z2.toDouble())).toInt().rem(p).rem(q)
        return w1 == u
    }
}