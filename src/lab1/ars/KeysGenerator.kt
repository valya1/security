package lab1.ars

import lab1.Utils.gcd
import lab1.Utils.generatePrime

class KeysGenerator {

    fun generateKeys() {

        val primes = hashSetOf<Int>()
        var prime = generatePrime(2)

        while (primes.size < 10) {
            primes.add(prime)
            prime = generatePrime(prime + 1)
        }

        for (p in primes) {
            for (q in primes) {
                val (e, n, d) = generateOneKey(p, q)
                println("e: $e, n: $n, d: $d")
            }
        }

    }

    fun getD(e: Int, en: Int): Int {

        var d = 2
        while ((e * d).rem(en) != 1) d++
        return d

    }


    fun generateOneKey(
        p: Int = generatePrime(3),
        q: Int = generatePrime(p + 1)
    ): Triple<Int, Int, Int> {

        val n = p * q
        if (n <= 19) { //n всегда больше K
            val p1 = generatePrime(p + 1)
            return generateOneKey(p1)
        }


        val en = (p - 1) * (q - 1)

        for (e in 2 until n) {
            if (gcd(e, en) == 1) {
                val d = getD(e, en)
                return Triple(e, n, d)
            }

        }

        throw Exception("Проверяй все еще раз")
    }

}




