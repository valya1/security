package lab1

object Utils {

    fun generatePrime(start: Int): Int {
        var num = start
        while (!num.isPrime()) num++
        return num
    }


    fun Int.isPrime(): Boolean {

        if (this % 2 == 0) return false

        var d = 3
        while (d * d <= this && this % d != 0) {
            d++
        }
        return d * d > this
    }

    fun gcd(num1: Int, num2: Int): Int {
        if (num2 == 0) return num1

        return gcd(num2, num1 % num2)

    }

}