package com.wild.chucknorris

import kotlin.system.exitProcess

fun main() {
    while (true) {
        println("Please input operation (encode/decode/exit):")
        when (val operation = readln()) {
            "encode" -> println(encode())
            "decode" -> println(decode())
            "exit" -> {
                println("Bye!")
                exitProgram()
            }
            else -> println("There is no \'$operation\' operation")
        }
    }
}

fun encode(): String {
    println("Input string:")
    val input = readln()
    val binaryMessage = input.toCharArray().joinToString("") {
        it.code.toString(2).padStart(7, '0')
    }
    var result = ""
    var currentBit = binaryMessage[0]
    var count = 1
    for (i in 1..<binaryMessage.length) {
        if (binaryMessage[i] == currentBit) {
            count++
        } else {
            result += if (currentBit == '0') "00 " else "0 "
            result += "0".repeat(count) + " "
            currentBit = binaryMessage[i]
            count = 1
        }
    }
    result += if (currentBit == '0') "00 " else "0 "
    result += "0".repeat(count)
    return "Encoded string:\n${result.trim()}"
}

fun decode(): String {
    println("Input encoded string:")
    val encoded = readln().trim()
    return if (!encoded.all { it in listOf('0', ' ') } ||
        !encoded.split(" ").filterIndexed { i, _ -> i % 2 == 0 }.all { it in listOf("0", "00") } ||
        encoded.split(" ").size % 2 != 0) {
        "Encoded string is not valid."
    } else {
        try {
            println()
            "Decoded string:\n${encoded.unChuck()}"
        } catch (e: Exception) {
            "Encoded string is not valid."
        }
    }
}

fun String.unChuck(): String {
    return this.split(" ").mapIndexed { i, s ->
        if (i % 2 == 1) {
            if (this.split(" ")[i - 1] == "0") {
                "1".repeat(s.length)
            } else {
                s
            }
        } else {
            ""
        }
    }.joinToString("").chunked(7).map {
        if (it.length != 7) throw Exception() else it.toInt(2).toChar()
    }.joinToString("")
}

fun exitProgram() {
    exitProcess(0)
}