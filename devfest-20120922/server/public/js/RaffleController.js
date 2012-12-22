
var myApp = angular.module('myApp',[]);

function RaffleController($scope, $http) {

	$scope.raffle = { raffle_name : ""};

  $scope.ticket = { raffle_id: "", user_name : ""};

  //{"raffle_id":"505f78b7e6c235e253000001", "user_name":"thomas"}

	$http.get('/api/v1/raffle').success(function(data) {

    	$scope.raffles = data;
  	});

  	$scope.selectRaffle = function(raffleId) {

      $scope.ticket.raffle_id = raffleId;
      console.dir($scope.ticket);

  	};

  	$scope.createRaffle = function() {

  		$http.post('/api/v1/raffle', $scope.raffle).success(function(data) {
  		
  			$scope.raffles.push(data[0]);
  		});
  	}

    $scope.createTicket = function() {

      console.log($scope.ticket.user_name);

      $http.post('/api/v1/ticket', $scope.ticket).success(function(data) {
          console.log("POST Ticket");
          console.dir(data);
      });
    }
}

RaffleController.$inject = ['$scope', '$http'];