package com.example.lizhijiang.myapplication.kotlint

class TestGrammar {

    fun main() {
        val x = 5
        if (x in 1..8) {
            println("x 在区间内")
        }
    }

    fun lambadtest(){
        val sumLambda: (Int,Int) -> Int = {x,y -> x+y}

        sumLambda(1,2)
    }

}