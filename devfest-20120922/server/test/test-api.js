var request = require('request');
var should = require('should');

/*
Tests the REST CRUD for raffle
 */
describe('Test raffle CRUD ', function() {

    var raffleId;

    it('list raffle', function(done) {
        request.get({url:'http://localhost:8080/api/v1/raffle', json:true}, function(err, res, body) {
            var raffleArray = body;
            raffleArray.should.have.lengthOf(0);
            done();
        });
    });

    it('create raffle', function(done) {
        request.post({url:'http://localhost:8080/api/v1/raffle',json:{raffle_name:"test raffle"}}, function(err, res, body) {
            body.should.have.lengthOf(1);
            var raffle = body[0];
            raffleId = raffle._id;
            raffle.should.have.property('name');
            raffle.should.have.property('_id');
            raffle.name.should.equal("test raffle");
            done();
        });
    });

    it('list raffle', function(done) {
        request.get({url:'http://localhost:8080/api/v1/raffle', json:true}, function(err, res, body) {
            var raffleArray = body;
            raffleArray.should.have.lengthOf(1);
            done();
        });
    });

    it('delete raffle', function(done) {
        request.del({url:'http://localhost:8080/api/v1/raffle/'+raffleId, json:true}, function(err, res, body) {
            res.statusCode.should.equal(200);
            done();
        });
    });

    it('list raffle', function(done) {
        request.get({url:'http://localhost:8080/api/v1/raffle', json:true}, function(err, res, body) {
            var raffleArray = body;
            raffleArray.should.have.lengthOf(0);
            done();
        });
    });

});




