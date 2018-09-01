package main

external fun require(module:String):dynamic

val express = require("express")



fun router(){
    val router = express.Router();
    router.get("/hi", {req, res ->
        res.type("text/plain")
        res.send("Hi!")
    })

    router.get("/", { req, res ->
            res.type("text/plain")
            res.send("Hello world")
        })
    return router
}

