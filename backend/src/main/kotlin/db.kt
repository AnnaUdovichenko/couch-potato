package db

import wrappers.require

private val url = require("process").env.MONGOLAB_URI


data class Idea(val text: String, val interests: Array<String>)
data class InterestList(val interests: Array<String> = emptyArray())

private val defaultIdea = """Sorry, we couldn't find anything special according to your interests.
    | How about to get a random bus and go to some unfamiliar place?
""".trimMargin()


private class Projection<out T>(val projection: T)

private object database {
    val dbName = "couch-potato"
    val collectionName = "ideas"
    val mongoClient = require("mongodb").MongoClient
    val url = require("process").env.MONGOLAB_URI

    fun connect(callback: (dynamic)->Unit){
        mongoClient.connect(url) { err, db ->
            if (err) {
                console.log("Failed to connect to db: $err")
                callback(null)
            } else {
                callback(db)
            }
        }
    }

    fun <P, T>getProjectedCollection(projection: P, callback: (Array<T>) -> Unit){
        connect { connection ->
            if (connection == null) {
                callback(arrayOf())
            } else {
                connection.db(dbName).collection(collectionName).find(object {}, projection).toArray { err, res ->
                    if (err) {
                        callback(arrayOf())
                    } else {
                        callback(res as Array<T>)
                    }
                }
                connection.close()
            }
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
        connect { connection ->
            if (connection == null){
                callback(false)
            }
            else{
                connection.db(dbName).collection(collectionName).insertOne(idea) { err, res ->
                    if (err){
                        console.log("Failed to add a record to database $err")
                        callback(false)
                    }
                    else {
                        console.log("Data $idea has been successfully stored")
                        callback(true)
                    }
                }
                connection.close()
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
    // TreeSet seems to be missing in Kotlin/JS
    database.getInterestsList { res ->
        callback(getUniqueInterests(res).sorted().toTypedArray())
    }
}

fun findIdea(toFind: InterestList, callback: (String) -> Unit) {
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

fun addIdea(idea: Idea, callback: (Boolean) -> Unit) {
    database.addIdea(idea, callback)
}

