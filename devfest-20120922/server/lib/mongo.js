/*
 * Author: Thomas Amsler : tamsler@gmail.com
 */

var MONGO = require('config').MONGO;
var mongodb = require('mongodb');
var db;

/*
 * Creating a connection to the MongoDB and open it. The connection will be reused.
 */
exports.init = function(callback) {

    mongodb.MongoClient.connect(MONGO.URL_CONNECTION,
    {
        db: {
            native_parser: false,
            w: 1
        },
        server: {
            auto_reconnect: true
        },
        replSet: {},
        mongos: {}
    },
    function(err, newDb) {

        if(err) {

            console.log("ERROR: db.open : err = " + err.message);
        }
        else {

            db = newDb;
            callback();
        }
    });
};

/*
 * Getting a collection for the provided collection name
 */
exports.collection = function(collectionName, callback) {

    db.collection(collectionName, function(err, collection) {

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
};

exports.db = db;