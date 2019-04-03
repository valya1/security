package lab2.clause_shnorr

import java.lang.Math.pow
import java.util.*

class ClauseShnorrServer {

    private var e: Int = -1

    private var r: Int = -1


    fun getE(r: Int): Int {

        val t = Random().nextInt(5) + 2

        val temp = pow(2.0, t.toDouble()).toInt() - 1

        e = Random().nextInt(temp) + 1
        this.r = r

        return e
    }

    fun auth(s: Int, g: Int, y: Int, p: Int): Boolean {

        val one: Long = pow(g.toDouble(), s.toDouble()).toLong()
        val two: Long = pow(y.toDouble(), e.toDouble()).toLong()

        val r = (one * two).rem(p).toInt()

        return this.r == r
    }

}