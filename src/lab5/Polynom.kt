package lab5

import lab2.Utils.lcm

data class Polynom(
    val devider: Int = 1,
    val denominator: Int = 1,
    val x2Coeff: Int = 1,
    val xCoeff: Int,
    val freeCoeff: Int
) {


    operator fun plus(other: Polynom): Polynom {

        val lcmDenominator = lcm(this.denominator, other.denominator) //нахождение общего знаменателя
        val deviderNew = devider * (lcmDenominator / denominator) //домножение числителя первого полинома
        val otherDeviderNew = other.devider * (lcmDenominator / other.denominator) //домножение числителя второго полинома

        return Polynom(
            devider = 1,
            denominator = lcmDenominator,
            x2Coeff = deviderNew * x2Coeff + otherDeviderNew * other.x2Coeff,
            xCoeff = deviderNew * xCoeff + otherDeviderNew * other.xCoeff,
            freeCoeff = deviderNew * freeCoeff + otherDeviderNew * other.freeCoeff
        )
    }
}