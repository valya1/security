package lab3.gost_2001

class Receiver {


    fun verifySender(r: Int, s: Int, openKey: OpenKey): Boolean {

        val hT = 19

        val q = openKey.q

        val e = hT % q // заранее посчитано
        val e2 = 27 //e обратное
        val v = e2 % q

        val z1 = (s * v) % q
        val z2 = ((q - r) * v) % q

        val xc = 16

        val r2 = xc % q

        return r == r2
    }

}
