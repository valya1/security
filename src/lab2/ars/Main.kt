package lab2.ars


fun main(vararg params: String) {

    val generator = KeysGenerator()
    val keys = generator.generateOneKey()

    val server = ARSServer(keys.first, keys.second, keys.third)
    val client = ARSClient()

    println("Client ${if (client.authorize(server)) "authorized" else "unauthorized"}")

//    generator.generateKeys()
}
