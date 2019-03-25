package lab1.clause_shnorr

import lab1.Utils.generatePrime
import java.lang.Math.pow
import java.util.*

class KeysGenerator {

    //генерация публичного ключа
    fun generateOneKey(
        p: Int = generatePrime(23),
        q: Int = generateQ(p)
    ): KeyParams {

        var x = Random().nextInt(q) + 1
        if (x == p) x--

        val g = calcG(p, q)
        val y = calcY(g, x, p)

        return KeyParams(p, q, x, y, g)
    }


    fun calcG(p: Int, q: Int): Int {

        var g = 2
        while (pow(g.toDouble(), q.toDouble()).toInt().rem(p) != 1){
            g++
            if(g > 100) throw Exception("Слишком большое G")
        }
        return g
    }

    fun calcY(g: Int, x: Int, p: Int): Int {

        var y = 2
        while ((pow(g.toDouble(), x.toDouble()) * y).toInt().rem(p) != 1){
            y++
            if(y > 100) throw Exception("Слишком большой Y")
        }
        return y
    }

    fun generateQ(p: Int): Int {

        var q = 3
        while ((p - 1).rem(q) != 0){
            q = generatePrime(q + 1)
            if (q > 100) {
                throw Exception("Слишком большое Q")
            }
        }
        return q
    }

    fun generatePandQ(): HashSet<Pair<Int, Int>> {

        val result = hashSetOf<Pair<Int, Int>>()
        var p = 3
        var q = 3

        while (p < 1000) {
            p = generatePrime(p + 1)
            q = 3
            while (q < 1000) {
                q = generatePrime(q + 1)
                if (validatePandQ(p, q))
                    result.add(Pair(p, q))
            }
        }

        return result
    }

    fun validatePandQ(p: Int, q: Int) = ((p - 1).rem(q) == 0)


}