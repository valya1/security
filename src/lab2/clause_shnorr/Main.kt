package lab2.clause_shnorr


fun main(vararg params: String) {

    val keysGenerator = KeysGenerator()

    val server = ClauseShnorrServer()
    val client = ClauseShnorrClient(keysGenerator)

    println("Client ${if (client.authorize(server)) "authorized" else "unauthorized"}")

    for ((p, q) in keysGenerator.generatePandQ()) {
        try {
            val key = keysGenerator.generateOneKey(p, q)
            println("$key")
        } catch (ignored: Exception) { }
    }

}
