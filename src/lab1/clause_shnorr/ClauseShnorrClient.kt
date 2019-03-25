package lab1.clause_shnorr

import java.lang.Math.pow

class ClauseShnorrClient(private val keysGenerator: KeysGenerator) {

    private val k = 20

    fun authorize(server: ClauseShnorrServer): Boolean {

        with(keysGenerator.generateOneKey()) {

            val r = pow(g.toDouble(), k.toDouble()).toInt().rem(p)

            val e = server.getE(r)

            val s = (k + x * e).rem(q)

            return server.auth(s = s, g = g, y = y, p = p)
        }

    }

}