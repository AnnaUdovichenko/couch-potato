package db


val mongoClient = main.require("mongodb").MongoClient
val url = main.require("process").env.MONGOLAB_URI;
//val url = "mongodb://potato:lazy-c0uch-pot%40to@ds213053.mlab.com:13053/couch-potato"


data class Idea(val text: String, val interests: Array<String>)
val projection = object{
    val projection = object{
        val _id: Int = 0
        val interests: Int = 1
    }
}

data class InterestList(val interests: Array<String>)


fun loadInterests(callback: (HashSet<String>?) -> Unit){
    console.log("Url: $url")
    mongoClient.connect(url) { err, db ->
        if (err) {
            console.log("Connection error: $err")
        } else {
            console.log("Connected!")
            val dbo = db.db("couch-potato")
            dbo.collection("ideas").find(object{}, projection).toArray { err, res ->
                if (err) {
                    console.log("error: $err")
                    callback(null)
                } else {
                    if (res is Array<InterestList>){
                        // Cast is shown like  unnecessary, but build fails without it
                        val interests = res as Array<InterestList>
                        console.log("found: $res")
                        var unique = HashSet<String>()
                        interests.forEach {
                            unique.addAll(it.interests)
                        }
                        callback(unique)
                    }
                    else {
                        console.log("Failed do cast interest list")
                        callback(null)
                    }
                }
                db.close()
            }
        }
    }
}

