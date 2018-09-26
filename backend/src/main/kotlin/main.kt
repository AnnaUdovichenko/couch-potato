
package main

fun foo(): Int{
    return 10
}

fun main(args: Array<String>) {
    println("Hello JavaScript!")

    val express = require("express")
    val app = express()

    app.use("/", router())

    app.listen(3000) {
        println("Listening on port 3000")
    }
}