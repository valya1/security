package lab1.ars

fun main() {


    val generator = KeysGenerator()
    val keys = generator.generateOneKey()

    val server = ARSServer(keys.first, keys.second, keys.third)
    val client = ARSClient()

    client.authorize(server)

    generator.generateKeys()

}