package lab3.gost_34


class Sender(val p: Int, val q: Int, val a: Int) {


    fun send(w1: Int, s: Int, receiver: Receiver): Boolean {
        return receiver.verifySender(w1, s)
    }


}