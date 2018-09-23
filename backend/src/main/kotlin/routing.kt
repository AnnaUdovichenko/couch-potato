package main


import kotlinx.serialization.Optional
import kotlinx.serialization.SerialId
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JSON

external fun require(module:String):dynamic

@Serializable
data class InterestList(@SerialId(1) @Optional val list: List<String> = emptyList())

@Serializable
data class Idea(@SerialId(1) val text: String, @SerialId(2) val interests: InterestList)


val express = require("express")

fun router(){
    val router = express.Router()

    router.use { req, res, next ->
        println("Some magic function $req")
        res.header("Access-Control-Allow-Origin", "*")
        res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept")
        next()
    }
    router.get("/hi") { req, res ->
        res.type("text/plain")
        res.send("Hi!")
    }

    router.get("/interests") {req, res ->

        /*val str = JSON.stringify(interests)
        res.send(str)*/
        db.loadInterests{interestSet ->
            console.log("getting interests $req")
            val interests = InterestList(interestSet?.toList() ?: emptyList())
            val str = JSON.stringify(interests)
            res.send(str)
        }

    }

    router.get("/") { req, res ->
        res.type("text/plain")
        res.send("Hello world")
    }
    return router
}

