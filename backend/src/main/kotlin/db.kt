package main

/*
var MongoClient = require('mongodb').MongoClient;
var url = "mongodb://localhost:27017/mydb";

MongoClient.connect(url, function(err, db) {
  if (err) throw err;
  console.log("Database created!");
  db.close();
});
 */

val mongoClient = require("mongodb").MongoClient

fun connect2db(){
    val url = "mongodb://localhost:27017/data"
    mongoClient.connect(url) { err, db ->
        console.log("Connected!")
        db.close()
    }
}