var PrayingChain = angular.module("PrayingChain");

PrayingChain.controller("configuration", ['$scope','$rootScope','pcUtils', function($scope,$rootScope,pcUtils) {
	var vm = this;
	
	vm.configuration = {};
	vm.unchangedConfiguration = {};
	vm.needSave = false;
	
	var configuration = pcUtils.getConfiguration();
	configuration.then(function(dataOut){
		vm.configuration.calendarFileName = dataOut.data.calendarFileName;
		vm.configuration.emailServiceHostName = dataOut.data.emailServiceHostName;
		vm.configuration.emailServiceSmtpPort = dataOut.data.emailServiceSmtpPort;
		vm.configuration.emailServiceUserName = dataOut.data.emailServiceUserName;
		vm.configuration.emailServiceUserPassword = dataOut.data.emailServiceUserPassword;
		vm.configuration.ftpPort = dataOut.data.ftpPort;
		vm.configuration.ftpPwd = dataOut.data.ftpPwd;
		vm.configuration.ftpServerName = dataOut.data.ftpServerName;
		vm.configuration.ftpUser = dataOut.data.ftpUser;
		vm.configuration.localFilePath = dataOut.data.localFilePath;
		vm.configuration.MailchimpUrlPwd = dataOut.data.mailchimpUrlPwd;
		vm.configuration.prayersPerTurn = dataOut.data.prayersPerTurn;
		vm.configuration.remoteFilePath = dataOut.data.remoteFilePath;
		vm.configuration.statisticsFileName = dataOut.data.statisticsFileName;
		vm.configuration.emailServiceSSL = dataOut.data.emailServiceSSL;
		vm.configuration.uid = dataOut.data.uid;
		vm.unchagedConfiguration = angular.copy(vm.configuration);
		vm.configuration.chain = dataOut.data.chain;
		
		var chainName = pcUtils.getChainName(dataOut.data.chain);
		chainName.then(function(dataOut){
			vm.chainName = dataOut.data;
		});
	});
	
	vm.onChange = function (){
		vm.needSave = angular.toJson(vm.configuration) !== angular.toJson(vm.unchagedConfiguration);
	};
	
	vm.updateConfiguration = function (isValid){
	};
}]);