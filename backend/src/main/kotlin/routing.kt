package main


import kotlinx.serialization.Optional
import kotlinx.serialization.SerialId
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JSON
import org.w3c.fetch.*

external fun require(module:String):dynamic

val path = require("path")
external val __dirname: String
val bodyParser = require("body-parser")
val querystring = require("querystring")

external fun decodeURIComponent(uri: String): String
external fun encodeURIComponent(uri: String): String

@Serializable
data class InterestList(@SerialId(1) @Optional val list: List<String> = emptyList())

@Serializable
data class Idea(@SerialId(1) val text: String, @SerialId(2) val interests: InterestList)


val express = require("express")

fun router(){
    val router = express.Router()

    router.use(express.static(path.resolve("$__dirname/../../../frontend/src/main/web")))
    router.use(express.static(path.resolve("$__dirname/../../../frontend/bin/bundle")))
    router.use(bodyParser.urlencoded(object { val extended = false}))
    router.use(bodyParser.json())

    router.get("/hi") { req, res ->
        res.type("text/plain")
        res.send("Hi!")
    }

    router.get("/interests") {req, res ->

        db.loadInterests{interests ->
            console.log("getting interests $req")
            val interestList = InterestList(interests.toList())
            val str = JSON.stringify(interestList)
            res.send(str)
        }
        /*val interestList = InterestList(listOf("A"))
        val str = JSON.stringify(interestList)
        res.send(str)*/

    }
    router.get("/idea") {req, res ->
        console.log("Get idea request $req, $res")
        //res.send("Hi ${req.query.interests}")
        if (req.query.interests is String){
            val interestList = JSON.parse<InterestList>(req.query.interests as String)
            console.log("Parsed interestList $interestList")
            db.findIdea(db.InterestList(interestList.list.toTypedArray()), { text ->
                res.send(text)
            })
        }

    }

    router.get("*") {req, res ->
        val url = path.resolve("$__dirname/../../../frontend/src/main/web/index.html")
        console.log("getting client file $url")
        res.sendFile(url)
    }

    return router
}

