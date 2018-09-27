package main


import kotlinx.serialization.Optional
import kotlinx.serialization.SerialId
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JSON

external fun require(module:String):dynamic

val path = require("path")
external val __dirname: String

@Serializable
data class InterestList(@SerialId(1) @Optional val list: List<String> = emptyList())

@Serializable
data class Idea(@SerialId(1) val text: String, @SerialId(2) val interests: InterestList)


val express = require("express")

fun router(){
    val router = express.Router()

    router.use(express.static("$__dirname/../../../frontend/src/main/web"))
    router.use(express.static("$__dirname/../../../frontend/build/bundle"))
    router.get("/hi") { req, res ->
        res.type("text/plain")
        res.send("Hi!")
    }

    router.get("/interests") {req, res ->

        db.loadInterests{interestSet ->
            console.log("getting interests $req")
            val interests = InterestList(interestSet?.toList() ?: emptyList())
            val str = JSON.stringify(interests)
            res.send(str)
        }

    }

    router.get("*") {req, res ->
        val url = "$__dirname/../../../frontend/src/main/web/index.html"
        console.log("getting client file $url")
        res.sendFile(url)
    }

    return router
}

