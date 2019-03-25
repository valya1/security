package lab1.clause_shnorr

import java.lang.Math.pow
import java.util.*

class ClauseShnorrServer {

    private var e: Int = -1

    fun getE(r: Int): Int {

        val t = Random().nextInt(5) + 2

        val temp = pow(2.0, t.toDouble()).toInt() - 1

        e = Random().nextInt(temp) + 1

        return e
    }

    fun auth(s: Int): Boolean {
        val r = pow()
    }

}