package lab1.clause_shnorr

import lab1.Utils.generatePrime
import java.lang.Math.pow
import java.util.*

class KeysGenerator {

    //генерация публичного ключа
    fun generateOneKey(p: Int = generatePrime(5), q: Int = generatePrime(p + 1)): KeyParams {

        var x = Random().nextInt(p) + 2
        if (x == p) x--

        val g = calcG(p, q)
        val y = calcY(g, x, p)

        return KeyParams(p, q, x, y, g)
    }


    fun calcG(p: Int, q: Int): Int {

        var g = 2
        while (pow(g.toDouble(), q.toDouble()).toInt().rem(p) != 1) g++
        return g
    }

    fun calcY(g: Int, x: Int, p: Int): Int {

        var y = 2
        while ((pow(g.toDouble(), x.toDouble()) * y).toInt().rem(p) != 1) y++
        return y
    }

}