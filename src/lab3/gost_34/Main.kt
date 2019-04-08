package lab3.gost_34

import lab2.Utils
import java.lang.Math.pow
import java.util.*


fun main(vararg params: String) {

    val p = Utils.generatePrime(30)
    val q = Utils.generatePrime(p + 5)

    val a = findA(p, q)

    var x = Random().nextInt(q) + 1
    if (x == q) x--

    val y = pow(a.toDouble(), x.toDouble()).toInt().rem(p)

    //результат функции h(T)
    val h = 19

    val (w1, s) = findW1andS(a, q, x, h)


    val sender = Sender(p, q, a)
    val receiver = Receiver(p, q, a, y)

    println("Message was ${if(sender.send(w1, s, receiver)) "verified" else "not verified"}")

}

fun findA(p: Int, q: Int): Int {
    var a = 1
    while (pow(a.toDouble(), q.toDouble()).toInt().rem(p) != 1) a++
    return a
}


fun findW1andS(a: Int, q: Int, x: Int, h: Int): Pair<Int, Int> {
    var k = 1

    val w = pow(a.toDouble(), k.toDouble()).toInt()
    val w1 = w.rem(q)
    val s = (x * w1 + k * h)

    if (w1 == 0 || s == 0) return findW1andS(k++, q, x, h)

    return Pair(w1, s)
}
