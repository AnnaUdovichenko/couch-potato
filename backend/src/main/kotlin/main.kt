
package main

fun foo(): Int{
    return 10
}

fun main(args: Array<String>) {
    println("Hello JavaScript!")

    val express = require("express")
    val app = express()

    app.use("/", router())

    val port = 55060
    app.listen(port) {
        println("Listening on port $port")
    }
}