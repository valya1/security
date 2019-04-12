package lab4


val paritetTestValues = arrayOf(
    201.toBase2(),
    210.toBase2(),
    208.toBase2(),
    211.toBase2(),
    215.toBase2(),
    234.toBase2(),
    238.toBase2(),
    226.toBase2()
)


val alphabet by lazy {

    val tempAlphabet = sortedMapOf<Char, Int>()

    ('а'..'я').forEachIndexed { index, c ->
        tempAlphabet[c] = index + 1
    }

    tempAlphabet
}


fun main() {

    while (true) {
        val c = readLine()

        when (c) {
            "1" -> paritetBit()
            "2" -> luhn()
            "3" -> EAN13()
            "4" -> ENN()
            "5" -> railworksCode()
            "6" -> CRC()
            "7" -> ECC_encode()
        }
    }
}


fun paritetBit() {
    for (value in paritetTestValues) {
        println("Value: $value")
        if (value.count { ch -> ch == '1' } % 2 == 0) {
            println("Even paritet bit = 1")
            println("Odd paritet bit = 0")
        } else {
            println("Even paritet bit = 0")
            println("Odd paritet bit = 1")
        }
        println()
    }
}


fun luhn() {

    fun calculateCD(sum1: Int, sum2: Int) = {
        var cd = 0
        while ((sum1 + sum2 + cd) % 10 != 0) cd++
        cd
    }()

    val str = "СтручковМихаилП"

    val nums = str.toNums()

    val cd = {

        var sum1 = 0
        var sum2 = 0

        //количество цифр нечетное
        nums.forEachIndexed { index, i ->
            if (index % 2 != 1) {
                sum1 += (i * 2) % 9
            } else if (index != nums.size - 1) {
                sum2 += i
            }
        }

        calculateCD(sum1, sum2)
    }()

    println("Luhn control num is: $cd")

}

fun EAN13() {

    fun calculateCD(sum1: Int, sum2: Int) = {

        var cd = 0
        while ((sum1 + sum2 + cd) % 10 != 0) cd++
        cd

    }()


    val str = "СтручковМиха"

    var sum1 = 0
    var sum2 = 0

    str.toLowerCase().forEachIndexed { index, c ->
        if (index % 2 == 1 && index != str.length - 1) {
            sum1 += alphabet[c]!!
        } else {
            sum2 += 3 * alphabet[c]!!
        }
    }

    println("EAN 13 control num: ${calculateCD(sum1, sum2)}")

}

fun ENN() {
    val str = "СтручковМ"

    val nums = str.toNums()

    val n10 =
        (2 * nums[0] +
                4 * nums[1] + 10 * nums[2] +
                3 * nums[3] + 5 * nums[4] +
                9 * nums[5] + 4 * nums[6] +
                6 * nums[7] + 8 * nums[8]
                ) % 10

    println("ENN control num for individuals: $n10 ")
}

fun railworksCode() {

    val str = "Михаи"

    val nums = str.toNums()

    var n6 = (nums[0] + 2 * nums[1] + 3 * nums[2] + 4 * nums[3] + 5 * nums[4]) % 11

    if (n6 >= 10)
        n6 = (3 * nums[0] + 4 * nums[1] + 5 * nums[2] + 6 * nums[3] + 7 * nums[4]) % 11

    println("Railworks control: $n6")


}


fun CRC() {


    fun toPolynom(x: String) = {
        val poly = IntArray(x.length)
        (0 until x.length).forEach { index ->
            poly[index] = x[index].toString().toInt()

        }
        poly
    }()

    val str = "Стр"
    val nums = str.toNums()

    nums.forEach { num ->

        val dividend = toPolynom("${num.toBase2()}0000") // X^N
        val divider = intArrayOf(1, 0, 0, 1, 1) //g(x) = x4 + x1 + 1

        val (_, quotient) = dividend polynomialDivide divider

        //остаток перевести в двоичную
        var q = ""
        quotient.forEach { i ->
            q += i.toString()
        }

        val r = q.toInt().toBase10()
        println("R($num) = $r")
    }
}


fun ECC_encode() {

    fun applyEmptyControlBits(binaryInput: IntArray): IntArray {

        val res = arrayListOf<Int>()

        binaryInput.forEachIndexed { index, _ ->
            res.add(binaryInput[index])
        }

        res.add(0, 0)
        res.add(1, 0)
        res.add(3, 0)
        res.add(7, 0)

        return res.toIntArray()
    }


    fun generateAdditionalMatrix(length: Int = 15): ArrayList<IntArray> {

        val result = ArrayList<IntArray>()

        for (i in 0 until 4) {
            result.add(IntArray(length))
            for (j in 0 until result[i].size) {
                val num = (j + 1).toBase2()
                result[i][j] = if (i > num.length - 1) 0 else num[num.length - 1 - i].toString().toInt()
            }
        }
        return result

    }

    val first = 201
    val second = 210

    val binaryInput = {

        val ints = IntArray(11)

        (first.toBase2() + second.toBase2())
            .substring(0, 11)
            .forEachIndexed { index, c -> ints[index] = c.toString().toInt() }

        ints
    }()

    val binaryInputWithEmptyControlBits = applyEmptyControlBits(binaryInput)

    val matrix = arrayListOf<IntArray>()
        .apply {
            add(binaryInputWithEmptyControlBits)
            addAll(generateAdditionalMatrix())
        }

    val controlBits = multiplyXRandN(matrix)

    (0 until controlBits.size).forEach { i ->
        println("R(${i + 1}) = ${controlBits[i]}")
    }


    val pb = {

        val temp = ArrayList(binaryInputWithEmptyControlBits.toList())
        temp.add(0, controlBits[0])
        temp.add(1, controlBits[1])
        temp.add(3, controlBits[2])
        temp.add(7, controlBits[3])

        temp.sum() % 2

    }() //вычисление паритетного бита

    ECC_decode(binaryInput, controlBits, generateAdditionalMatrix(), pb)

    val withOneError = binaryInput.copyOf().apply { this[4] = 0 }
    ECC_decode(withOneError, controlBits, generateAdditionalMatrix(), pb)

    val withTwoErrors = binaryInput.copyOf().apply {
        this[3] = 1
        this[5] = 1
    }
    ECC_decode(withTwoErrors, controlBits, generateAdditionalMatrix(), pb)


}


fun ECC_decode(data: IntArray, controlBits: IntArray, additionalMatrix: ArrayList<IntArray>, pb: Int) {

    val d = ArrayList(data.toList())

    d.add(0, controlBits[0])
    d.add(1, controlBits[1])
    d.add(3, controlBits[2])
    d.add(7, controlBits[3])


    val syndrom = multiplyXRandN(ArrayList<IntArray>().apply {
        add(d.toIntArray())
        addAll(additionalMatrix)
    })

    val pb2 = d.sum() % 2

    when {
        pb2 == pb && !syndrom.contains(1) -> println("Ошибок нет")
        pb2 != pb && syndrom.contains(1) -> println(
            "Ошибка в позиции ${Integer.parseInt(syndrom.toStringNum(), 2)}"
        )
        else -> println("Неисправимая множественная ошибка")
    }

}


fun multiplyXRandN(xrAndNMatrix: ArrayList<IntArray>): IntArray {

    val controlBits = IntArray(4)

    (1 until xrAndNMatrix.size).forEach { i ->
        (0 until xrAndNMatrix[i].size).forEach { j ->

            controlBits[i - 1] += xrAndNMatrix[0][j] * xrAndNMatrix[i][j] // XR(i) * N(i, j)

        }
        controlBits[i - 1] = controlBits[i - 1] % 2
    }
    return controlBits
}


fun Int.toBase2() = Integer.toString(this, 2)

fun Int.toBase10() = Integer.toString(this, 10)


fun IntArray.toStringNum(): String {
    return this.joinToString(separator = "").reversed()
}

fun String.toNums() = {
    val tempNums = arrayListOf<Int>()
    forEach { tempNums.add(alphabet[it.toLowerCase()]!!) }
    tempNums
}()


infix fun IntArray.polynomialDivide(divider: IntArray): Pair<IntArray, IntArray> {
    val out = this.copyOf()
    val normalizer = divider[0]
    val separator = this.size - divider.size + 1
    for (i in 0 until separator) {
        out[i] /= normalizer
        val coef = out[i]
        if (coef != 0) {
            for (j in 1 until divider.size) out[i + j] += -divider[j] * coef
        }
    }
    return out.copyOfRange(0, separator) to out.copyOfRange(separator, out.size)
}


