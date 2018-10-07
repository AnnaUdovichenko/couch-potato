package main

import db.database

external fun require(module:String):dynamic

val path = require("path")
external val __dirname: String
val bodyParser = require("body-parser")
val querystring = require("querystring")

val express = require("express")

fun parseInterests(param: String):List<String> = param.split(",").map { it.trim() }
fun stringifyInterests(param: List<String>): String = param.joinToString("," )


fun router(){
    val router = express.Router()

    router.use(express.static(path.resolve("$__dirname/../../../frontend/src/main/web")))
    router.use(express.static(path.resolve("$__dirname/../../../frontend/bin/bundle")))
    router.use(bodyParser.urlencoded(object { val extended = false}))
    router.use(bodyParser.json())

    router.get("/interests") {req, res ->

        db.loadInterests{interests ->
            console.log("getting interests $req, $interests")
            val str = stringifyInterests(interests.toList())
            res.send(str)
        }

    }
    router.get("/idea") {req, res ->
        console.log("Get idea request $req, $res")
        // res.send("Hi ${req.query.interests}")
        if (req.query.interests is String){
            val interestList = parseInterests(req.query.interests as String)
            console.log("Parsed interestList $interestList")
            db.findIdea(db.InterestList(interestList.toTypedArray())) { text ->
                res.send(text)
            }
        }

    }

    router.post("/idea") {req, res ->
        val text = req.body.text as String
        val list = parseInterests(req.body.interests)

        console.log("Saving to database $text, $list")
        val idea = db.Idea(text, list.toTypedArray())
        database.addIdea(idea) { ok ->
            if (ok) {
                res.send("Ok")
            }
            else {
                res.send("Failed to save data")
            }
        }
    }

    router.get("*") {req, res ->
        val url = path.resolve("$__dirname/../../../frontend/src/main/web/index.html")
        console.log("getting client file $url")
        res.sendFile(url)
    }

    return router
}

