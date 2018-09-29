package db


val mongoClient = main.require("mongodb").MongoClient
val url = main.require("process").env.MONGOLAB_URI


data class Idea(val text: String, val interests: Array<String>)


data class InterestList(val interests: Array<String>)

fun getUniqueInterests(interests: Array<InterestList>): HashSet<String> {
    var unique = HashSet<String>()
    interests.forEach {
        unique.addAll(it.interests)
    }
    return unique
}

object database{
    val dbName = "couch-potato"
    val collectionName = "ideas"
    val mongoClient = main.require("mongodb").MongoClient
    val url = main.require("process").env.MONGOLAB_URI
    var connection: dynamic = null

    fun connect(){
        if (connection == null) {
            mongoClient.connect(url) { err, db ->
                if (err) {
                    console.log("Failed to connect to db: $err")
                } else {
                    connection = db.db(dbName)
                }
            }
        }
    }

    fun <T>getProjectedCollection(projection: T, callback: (List<T>) -> Unit){

    }

    fun getInterestsList(callback: (Array<InterestList>) -> Unit) {
        val projection = object{
            val projection = object{
                val _id: Int = 0
                val interests: Int = 1
            }
        }
        if (connection != null) {
            connection.collection(collectionName).find(object{}, projection).toArray { err, res ->
                if (err) {
                    callback(arrayOf())
                } else {
                    callback(res as Array<InterestList>)
                }
            }
        }
        else {
            callback(arrayOf())
        }
    }
}

fun loadInterests(callback: (List<String>) -> Unit) {
    database.connect()
    database.getInterestsList { res ->
        callback(getUniqueInterests(res).toList())
    }
}

