package lab2.feige_fiat_shamir

class FeigeFiatShamirServer(val v: Int, val n: Int) {

    var b = 0
    var z = -1

    fun auth(r: Int) = if (b == 0) {
        z == (r * r).rem(n)
    } else {
        z == (r * r * v).rem(n)
    }


    fun getBit(z: Int): Int {

        this.z = z
//        b = Random().nextInt(1)
        b = 1
        return b
    }


}