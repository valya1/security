package lab5

import lab2.Utils
import lab2.ars.KeysGenerator
import lab4.alphabet
import lab4.toBase2

fun main() {

    while (true) {
        when (readLine()) {
            "1" -> average()
            "2" -> secretSharing()
            "3" -> sharingShamir()
        }
    }

}

fun average() {

    val rsaKeysGenerator = KeysGenerator()

    val numbers = intArrayOf(18, 19, 17)

    val (eD, nD, dD) = rsaKeysGenerator.generateOneKey(3)
    val (eC, nC, dC) = rsaKeysGenerator.generateOneKey(7)
    val (eY, nY, dY) = rsaKeysGenerator.generateOneKey(11)


    val secretX = 8
    val y1 = numbers[0].toBigDecimal() + secretX.toBigDecimal()
    val x1 = y1.pow(eD).rem(nD.toBigDecimal())//C шифрует сообщение D


    val y2 = x1.pow(dD).rem(nD.toBigDecimal()) //D расшифровал сообщение C
    val x2 =
        (y2 + numbers[1].toBigDecimal()).pow(eY).rem(nY.toBigDecimal())//D добавил зарплату, зашифровал и отправил Y


    val y3 = x2.pow(dY).rem(nY.toBigDecimal())//Y расшифровал сообщение D
    val x3 =
        (y3 + numbers[2].toBigDecimal()).pow(eC).rem(nC.toBigDecimal())//Y добавил зарплату, зашифровал и отправил C


    val r = (x3.pow(dC).rem(nC.toBigDecimal()) - secretX.toBigDecimal()) / 3.toBigDecimal()


    println("Average is $r")

}

fun secretSharing() {

    val secret = listOf(alphabet['с']!!.toBase2(), alphabet['т']!!.toBase2(), alphabet['р']!!.toBase2())
    println("Secret: ${secret.joinToString(separator = " ")}")

    val gamma1 = listOf(alphabet['т']!!.toBase2(), alphabet['о']!!.toBase2(), alphabet['р']!!.toBase2())
    println(gamma1.joinToString(separator = " "))

    val gamma2 = listOf(alphabet['к']!!.toBase2(), alphabet['о']!!.toBase2(), alphabet['т']!!.toBase2())
    println(gamma2.joinToString(separator = " "))

    val gamma3 = listOf(alphabet['к']!!.toBase2(), alphabet['и']!!.toBase2(), alphabet['т']!!.toBase2())
    println(gamma3.joinToString(separator = " "))


    val code = listOf(
        secret[0] xor gamma1[0] xor gamma2[0] xor gamma3[0],
        secret[1] xor gamma1[1] xor gamma2[1] xor gamma3[1],
        secret[2] xor gamma1[2] xor gamma3[2] xor gamma3[2]
    )

    println("Code: ${code.joinToString(separator = " ")}")


    val decodedSecret = listOf(
        code[0] xor gamma1[0] xor gamma2[0] xor gamma3[0],
        code[1] xor gamma1[1] xor gamma2[1] xor gamma3[1],
        code[2] xor gamma1[2] xor gamma2[2] xor gamma3[2]
    )

    print("Decoded secret is: ")
    for (c in decodedSecret) {
        print("$c ")
    }
}


fun sharingShamir() {

    var m = 3
    val n = 5
    val secret = alphabet['с']!!
//    val secret = 11 //TODO test
    val p = 37 //TODO test


    fun f(x: Int) = (10 * x * x + 23 * x + secret).rem(p) // a1 = 10, a2 = 23

    fun findBasisPolynoms(shares: Map<Int, Int>): List<Polynom> {


        val polynoms = arrayListOf<Polynom>()

        for (share in shares) {

            val (x, y) = share
            var denominator = 0
            var xCoeff = 0
            var freeCoeff = 1
            for (otherShare in HashMap(shares).apply { remove(x) }) { //проходим по остальным долям

                val (x0, _) = otherShare
                denominator = if (denominator == 0) x - x0 else denominator * (x - x0)
                xCoeff -= x0 //если забудешь, возьми листок и перемножь скобки - коэффиценты будут формироваться именно так
                freeCoeff *= x0
            }

            //полиномы формируются сразу с числителями
            polynoms.add(Polynom(devider = y, denominator = denominator, xCoeff = xCoeff, freeCoeff = freeCoeff))
        }
        return polynoms
    }


    fun lagrange(polynom: List<Polynom>) = polynom[0] + polynom[1] + polynom[2]

    fun getB1(resultPolynom: Polynom, p: Int): Int {
        val denominator = resultPolynom.denominator
        var b1 = 0

        while ((denominator * b1).rem(p) != 1)
            b1++
        return b1
    }


    val shares = {
        val tempY = hashMapOf<Int, Int>()
        (1..n).forEach { i ->
            tempY[i] = f(i)
        }
        tempY
    }()


    val basisPolynoms = findBasisPolynoms(shares.filter { (i, _) -> i != 1 && i != 4 }) //берутся 3 доли

    val lagrangePolynom = lagrange(basisPolynoms)

    val b1 = getB1(lagrangePolynom, p)

    var a2 = (b1 * lagrangePolynom.x2Coeff) % p
    if (a2 < 0) a2 += p

    var a1 = (b1 * lagrangePolynom.xCoeff) % p
    if (a1 < 0) a1 += p

    var a0 = (b1 * lagrangePolynom.freeCoeff) % p
    if (a0 < 0) a0 += p

    println("S = $a0")

}


fun asmutBlumDeviding() {

    val n = 5
    val m = 3
    val secret = alphabet['с']!!

    val p = Utils.generatePrime(secret)

    val dList = arrayListOf(17, 20, 23, 29, 37)

    fun findS1(dList: ArrayList<Int>, p: Int): Int {

        var multi = 1
        for (d in dList)
            multi *= d

        val criteria = multi / p

        val r = java.util.Random().nextInt(criteria)

        return secret + r * p
    }

    fun findShares(s1: Int, dList: ArrayList<Int>): HashMap<Int, Int> {

        val result = hashMapOf<Int, Int>()

        (0 until dList.size).forEach { i ->
            result[dList[i]] = s1 % dList[i]
        }

        return result
    }


    fun findBackDMultiplierList(dMultiplierList: ArrayList<Int>): ArrayList<Int> {

        val result = arrayListOf<Int>()

        fun findD1(index: Int): Int {
            var d1 = 0
            while ((d1 * dMultiplierList[index]) % dList[index] != 1) d1++
            return d1
        }


        (0 until dList.size).forEach { index ->
            result.add(findD1(index))
        }

        return result
    }

    val s1 = findS1(dList, p)
    val shares = findShares(s1, dList)


    val checkShares = shares.filter { (i, _) -> i < 3 }

    var D = 1
    for ((d, _) in checkShares)
        D *= d

    var dMultipliersList = {
        val temp = arrayListOf<Int>()
        for ((d, _) in checkShares) {
            temp.add(D / d)
        }
        temp
    }()

    val backDmultipliersList = findBackDMultiplierList(dMultipliersList)

    val s11 = {

        var sTemp = 0

        for((d,k) in checkShares){
            sTemp += //TODO доделать
        }
    }

}


infix fun String.xor(binaryStr: String): String {

    val a = Integer.parseInt(this, 2)
    val b = Integer.parseInt(binaryStr, 2)

    return (a xor b).toBase2()
}