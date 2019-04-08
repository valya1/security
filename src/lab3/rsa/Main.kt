package lab3.rsa

import lab2.ars.KeysGenerator
import java.lang.Math.pow


fun main(vararg params: String) {


    val keysGenerator = KeysGenerator()
    val (e, n, d) = keysGenerator.generateOneKey(3, 5)


    val sender = Sender(d) //d
    val receiver = Receiver(e, n) //e,n

    //результат хеширования исходного сообщения
    val h = 18
    val s = pow(h.toDouble(), d.toDouble()).toInt().rem(n)

    sender.send(s, receiver)


}