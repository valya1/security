package lab3.gost_2001


class Sender {

    val openKey = OpenKey(3, 7, 7, 17, 47, 36, 20)

    val privateKey = 10

    fun sendMessage(server: Receiver): Boolean {

        val q = openKey.q

        val hT = 19

        val e = hT % q

        val k = 2

        val xc = 16
        val yc = 16

        val r = xc % q

        val s = (r * privateKey + k * e) % q

        return server.verifySender(r, s, openKey)
    }
}
