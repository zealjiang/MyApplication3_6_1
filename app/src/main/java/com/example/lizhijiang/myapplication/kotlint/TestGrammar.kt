package com.example.lizhijiang.myapplication.kotlint

class TestGrammar {

    fun main() {
        val x = 5
        if (x in 1..8) {
            println("x 在区间内")
        }
    }

    fun sum(a: Int,b : Int): Int{
        return a+b
    }

    fun sumNo(a: Int,b : Int): Unit{

    }
    fun sumNo2(a: Int,b : Int){

    }

    fun vars(vararg v:Int){
        for (vt in v){
            print(vt)
        }
    }

    fun lambadtest(){
        val sumLambda: (Int,Int) -> Int = {x,y -> x+y}

        sumLambda(1,2)
    }

}