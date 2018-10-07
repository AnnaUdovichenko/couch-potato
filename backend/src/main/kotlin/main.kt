
package main

import wrappers.require
import wrappers.express

fun foo(): Int{
    return 10
}

fun main(args: Array<String>) {
    val app = express()

    app.use("/", router())

    val port = require("process").env.PORT ?: 3000

    app.listen(port) {
        println("Listening on port $port")
    }
}