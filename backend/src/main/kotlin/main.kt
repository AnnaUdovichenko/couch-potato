
package main

fun foo(): Int{
    return 10
}

fun main(args: Array<String>) {
    val express = require("express")
    val app = express()

    app.use("/", router())

    val port = main.require("process").env.PORT ?: 3000

    app.listen(port) {
        println("Listening on port $port")
    }
}