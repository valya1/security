package lab3

class Sender(d: Int) {


    //h известно заранее по условию, поэтому получатель его просто объявляет
    fun send(s: Int, receiver: Receiver): Boolean {
        return receiver.verifySender(s)
    }

}
