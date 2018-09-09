package main

external fun require(module:String):dynamic

val express = require("express")


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
        res.send("""
            {list: [
                Programming,
                Sports,
                Painting,
                Theatre,
                Photography,
                Reading
            ]}
        """.trimIndent())
    }

    router.get("/") { req, res ->
        res.type("text/plain")
        res.send("Hello world")
    }
    return router
}

