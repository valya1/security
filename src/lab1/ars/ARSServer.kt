package lab1.ars

import java.lang.StrictMath.pow

class ARSServer(val e: Int, val n: Int, private val d: Int) {

    fun getAuthCodeBy(r: Int): Int {

        val pow = pow(r.toDouble(), d.toDouble()).toLong()

        return pow.rem(n).toInt()
    }
}