
package main

import wrappers.require
import wrappers.express

fun main() {
    val app = express()

    app.use("/", router())

    val port = require("process").env.PORT ?: 3000

    app.listen(port) {
        println("Listening on port $port")
    }
}