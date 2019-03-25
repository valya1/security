package feige_fiat_shamir

import lab1.Utils.generatePrime

class KeysGenerator {

    fun generateOneKey(p: Int = generatePrime(5), q: Int = generatePrime(p + 1)): Triple<Int, Int, Int> {
        val n = p * q
        if (n <= 18) return generateOneKey(p + 1) //для r

        val deductions = generateSquareDeductions(n)

        val (v, v1) = deductions.asIterable().first()

        val s = generateS(n, v1)

        return Triple(v, n, s)

    }

    fun generateSquareDeductions(n: Int): HashSet<Pair<Int, Int>> { //(v,v1)

        val result = hashSetOf<Pair<Int, Int>>()
        var v = 2

        while (v < 100) {
            var x = 1
            while (x <= n) {
                if ((x * x).rem(n) == v) {
                    var v1 = 1
                    while (v1 <= v) {
                        if ((v * v1).rem(n) == 1)
                            result.add(Pair(v, v1))
                        v1++
                    }
                }
                x++
            }
            v++
        }

        return result
    }

    fun generateS(n: Int, v1: Int): Int {

        var s = 2
        while (s < 100) {
            if ((s * s).rem(n) == v1)
                return s
            s++
        }

        throw Exception("Слишком большое S")

    }


    fun generateKeys(size: Int): ArrayList<Triple<Int, Int, Int>> {

        val result = arrayListOf<Triple<Int, Int, Int>>()

        val p = 3

        while (p <= 100) {
            val q = 3
            while (q <= 100) {
                //todo сгенерировать ключи
            }
        }

        return result
    }

}