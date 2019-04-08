package lab3.gost_2001

fun main(vararg args: String) {

    //результат h(T)
    val h = 19


    val sender = Sender()

    val receiver = Receiver()

    println("Message was ${if (sender.sendMessage(receiver)) "verified" else "not verified"}")

}