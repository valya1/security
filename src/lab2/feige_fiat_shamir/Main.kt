package lab2.feige_fiat_shamir


fun main(vararg params: String) {

    val keysGenerator = KeysGenerator()

    val keys = keysGenerator.generateOneKey()

    val server = FeigeFiatShamirServer(keys.first, keys.second) //v,n
    val client = FeigeFiatShamirClient(keys.second, keys.third) //n, s

    println("Client is ${if (client.authorize(server)) "authorized" else "unauthorized"}")

}