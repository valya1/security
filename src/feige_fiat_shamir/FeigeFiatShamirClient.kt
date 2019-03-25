package feige_fiat_shamir

class FeigeFiatShamirClient(val n: Int, val s: Int) {

    private var r = 18

    fun authorize(server: FeigeFiatShamirServer): Boolean {

        val z = (r * r).rem(n)

        return if (server.getBit(z) == 0) {
            server.auth(r)
        } else {
            server.auth((r * s).rem(n))
        }

    }

}