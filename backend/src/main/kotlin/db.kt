package db


val mongoClient = main.require("mongodb").MongoClient
val url = "mongodb://localhost:27017/data"


data class Idea(val text: String, val interests: Array<String>)
val projection = object{
    val projection = object{
        val _id: Int = 0
        val interests: Int = 1
    }
}

data class InterestList(val interests: Array<String>)


fun loadInterests(callback: (HashSet<String>?) -> Unit){
    mongoClient.connect(url) { err, db ->
        if (err) {
            console.log("Connection error: $err")
        } else {
            console.log("Connected!")
            val dbo = db.db("data")
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

