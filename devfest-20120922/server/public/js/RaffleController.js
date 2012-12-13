
var myApp = angular.module('myApp',[]);

function RaffleController($scope, $http) {

	$scope.raffle = { raffle_name : ""};

	$http.get('/api/v1/raffle').success(function(data) {

    	$scope.raffles = data;
  	});

  	$scope.selectRaffle = function(raffleId) {

  		console.log("Foo" + raffleId);
  	};

  	$scope.createRaffle = function() {

  		console.log("Raffle name = " + $scope.raffle.raffle_name);
  		$http.post('/api/v1/raffle', $scope.raffle).success(function(data) {
  		
  			$scope.raffles.push(data[0]);
  		});
  	}
}

RaffleController.$inject = ['$scope', '$http'];