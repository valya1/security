package lab6



val letterByIndex by lazy {

    val tempAlphabet = sortedMapOf<Int, Char>()

    ('а'..'я').forEachIndexed { index, c ->
        tempAlphabet[index] = c
    }

    tempAlphabet
}

val indexByLetter by lazy {

    val tempAlphabet = sortedMapOf<Char, Int>()

    ('а'..'я').forEachIndexed { index, c ->
        tempAlphabet[c] = index
    }

    tempAlphabet
}




