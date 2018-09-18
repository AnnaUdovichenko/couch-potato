
package main



fun main(args: Array<String>) {
    println("Hello JavaScript!")
    connect2db()

    val express = require("express")
    val app = express()

    app.use("/", router())

    app.listen(3000) {
        println("Listening on port 3000")
    }
}