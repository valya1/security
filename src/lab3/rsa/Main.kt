package lab3

import lab2.ars.KeysGenerator
import ru.bullyboo.encoder.hashes.Hash
import ru.bullyboo.encoder.methods.RSA
import sun.security.rsa.RSAPrivateKeyImpl
import sun.security.rsa.RSAPublicKeyImpl
import java.lang.Math.pow
import java.security.KeyFactory
import java.security.interfaces.RSAPublicKey
import java.security.spec.RSAPrivateKeySpec


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