package lab6

fun main(){

    val cesar = Cesar()
    val vishener = Vishener()


    val cesaredMessage = cesar.encode("Для записи, зарегистрируйтесь на сайте")
    println(cesaredMessage)
    println(cesar.decode(cesaredMessage))

    val vishenerMessage = vishener.encode("Конденсированное молоко")
    println(vishenerMessage)
    println(vishener.decode(vishenerMessage))





}