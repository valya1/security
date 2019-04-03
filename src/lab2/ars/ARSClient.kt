package lab2.ars

import java.lang.Math.pow

class ARSClient {

    private val randomK = 19

    fun authorize(server: ARSServer): Boolean {

        val (e, n) = Pair(server.e, server.n)

        val r = pow(randomK.toDouble(), e.toDouble()).toLong().rem(n).toInt()

        val clientAuthCode = server.getAuthCodeBy(r)

        return clientAuthCode == randomK

    }


}