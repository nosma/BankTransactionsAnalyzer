app.controller('VoiceCtrl', ['$scope', function ($scope) {

    $scope.phrase = "";
    $scope.visibleDiv = false;
    $scope.hiddenDiv = false;


    var home = function () {
        window.location.replace('#/Home');
    };
    var statistics = function () {
        window.location.replace('#/Statistics');
    };
    var transactions = function () {
        window.location.replace('#/Transactions');
    };
    var micro = function () {
        window.location.replace('#/Microphone');
    };

    $scope.stopMic = function () {
        annyang.abort()
    };
    $scope.startMic = function () {
        annyang.start({autoRestart: false, continuous: true});
    };

    //$scope.Cat = "C A T";
    //$scope.Dog = "D O G";

    var voiceCtrl = function(val) {
        alert(val);
        $scope.phrase = val;
    };

    $scope.commands = {
        'home': home,
        'statistics': statistics,
        'transactions': transactions,
        'microphone': micro,
        'text *val' : voiceCtrl
    };

    // Add our commands to annyang
    annyang.addCommands($scope.commands);
    annyang.debug();
    //annyang.start();


    // Start listening, don't restart automatically, stop recognition after first phrase recognized
    //annyang.start({autoRestart: false, continuous: true});

    ////Let us know when we are ready to go
    //annyang.addCallback('start', function(){
    //    console.log("Ready to listen...");
    //});
    //
    //annyang.addCallback('resultNoMatch', function(userSaid){
    //    console.log("Could not recognize phrase \"" + userSaid + "\"")
    //});
    //
    //
    ////Being smart about listening
    //var stopListening = function() {
    //    console.log("removing commands...");
    //    annyang.removeCommands(commands)
    //};
    //
    //var startListening = function() {
    //    console.log("adding commands...");
    //    annyang.addCommands(commands);
    //};


// *******************************************
//    $scope.todoList = [{todoText:'Clean House', done:false}];
//
//    $scope.todoAdd = function() {
//        $scope.todoList.push({todoText:$scope.todoInput, done:false});
//        $scope.todoInput = "";
//    };
//
//    $scope.remove = function() {
//        var oldList = $scope.todoList;
//        $scope.todoList = [];
//        angular.forEach(oldList, function(x) {
//            if (!x.done) $scope.todoList.push(x);
//        });
//    };
// *******************************************
}]);