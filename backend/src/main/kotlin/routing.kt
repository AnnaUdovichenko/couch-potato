package main

import kotlinx.serialization.Optional
import kotlinx.serialization.SerialId
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JSON

external fun require(module:String):dynamic

val express = require("express")

@Serializable
data class InterestList(@SerialId(1) @Optional val list: List<String> = emptyList())

val interests = InterestList(listOf("Programming",
        "Sports",
        "Painting",
        "Theatre",
        "Photography",
        "Reading"))

fun router(){
    val router = express.Router();

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
        println("getting interests $req")
        res.type("application/json")
        val str = JSON.stringify<InterestList>(interests)
        res.send(str)
    }

    router.get("/") { req, res ->
        res.type("text/plain")
        res.send("Hello world")
    }
    return router
}

