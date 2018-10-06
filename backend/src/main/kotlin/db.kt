package db



val mongoClient = main.require("mongodb").MongoClient
val url = main.require("process").env.MONGOLAB_URI


data class Idea(val text: String, val interests: Array<String>)
data class InterestList(val interests: Array<String> = emptyArray())

val defaultIdea = """Sorry, we couldn't find anything special according to your interests.
    | How about to get a random bus and go to some unfamiliar place?
""".trimMargin()

//data class InterestList(val interests: Array<String>)

class Projection<out T>(val projection: T)

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

    fun <P, T>getProjectedCollection(projection: P, callback: (Array<T>) -> Unit){
        if (connection != null) {
            connection.collection(collectionName).find(object{}, projection).toArray { err, res ->
                if (err) {
                    callback(arrayOf())
                } else {
                    callback(res as Array<T>)
                }
            }
        }
        else {
            callback(arrayOf())
        }
    }

    fun getInterestsList(callback: (Array<InterestList>) -> Unit) {
        return getProjectedCollection(Projection(object{
            val _id: Int = 0
            val interests: Int = 1
        }), callback)
    }

    fun getIdeasList(callback: (Array<Idea>) -> Unit) {
        console.log("Getting ideas list")
        return getProjectedCollection(Projection(
                object {
                    val _id: Int = 0
                    val text: Int = 1
                    val interests: Int = 1
                }
        ), callback)
    }
    fun addIdea(idea: Idea, callback: (Boolean) -> Unit) {
        connect()
        connection.collection(collectionName).insertOne(idea) { err, res ->
            if (err){
                console.log("Failed to add a record to database $err")
                callback(false)
            }
            else {
                console.log("Data $idea has been successfully stored")
                callback(true)
            }
        }
    }
}


fun getUniqueInterests(interests: Array<InterestList>): HashSet<String> {
    var unique = HashSet<String>()
    interests.forEach {
        unique.addAll(it.interests)
    }
    return unique
}

fun loadInterests(callback: (Array<String>) -> Unit) {
    database.connect()
    database.getInterestsList { res ->
        callback(getUniqueInterests(res).toTypedArray())
    }
}

fun findIdea(toFind: InterestList, callback: (String) -> Unit) {
    database.connect()
    database.getIdeasList { res ->
        val filtered = res.filter{
            it.interests.intersect(toFind.interests.asIterable()).isNotEmpty()
        }
        if (filtered.isNotEmpty()) {
            // Unefficient
            callback(filtered.shuffled()[0].text)
        }
        else callback(defaultIdea)
    }
}

