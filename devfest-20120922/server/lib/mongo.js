/*
 * Author: Thomas Amsler : tamsler@gmail.com
 */


var mongodb = require('mongodb');
var db;

/*
 * Get this from MongoLab
 */
var MONGO_DB = "raffle";
var MONGO_HOST = "xyz.mongolab.com";
var MONGO_PORT = 31597;


/*
 * Creating a connection to the MongoDB and open it. The connection will be reused.
 */
exports.init = function(callback) {

    db = new mongodb.Db(MONGO_DB, new mongodb.Server(MONGO_HOST, MONGO_PORT, {auto_reconnect:true}), {});

    db.open(function(err, p_client) {

        if(err) {

            console.log("ERROR: db.open : err = " + err.message);
        }
        else {

            db.authenticate(MONGO.USER, MONGO.PASSWORD, function(err) {

                if (err) {

                    console.log("ERROR: db.authenticate : err = " + err.message);
                }
                else {

                    callback();
                }
            });
        }
    });
};

/*
 * Getting a collection for the provided collection name
 */
exports.collection = function(collectionName, callback) {

    db.collection(collectionName, {safe:true}, function(err, collection) {

        if(err) {

            console.log("ERROR: db.collection : err = " + err.message);
        }

        callback(collection);
    });
};

/*
 * Get an ObjectID
 */
exports.objectID = function(hexId) {

    if(hexId) {

        return new mongodb.ObjectID.createFromHexString(hexId);
    }
    else {

        return new mongodb.ObjectID();
    }
}

exports.db = db;





