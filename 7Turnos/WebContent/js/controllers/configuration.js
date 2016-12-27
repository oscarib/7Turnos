var PrayingChain = angular.module("PrayingChain");

PrayingChain.controller("configuration", ['$scope','pcUtils', function($scope,pcUtils) {
	var vm = this;
	
	var configuration = pcUtils.getConfiguration();
	configuration.then(function(dataOut){
		vm.calendarFileName = dataOut.data.calendarFileName;
		vm.chain = dataOut.data.chain;
		vm.emailServiceHostName = dataOut.data.emailServiceHostName;
		vm.emailServiceSmtpPort = dataOut.data.emailServiceSmtpPort;
		vm.emailServiceUserName = dataOut.data.emailServiceUserName;
		vm.emailServiceUserPassword = dataOut.data.emailServiceUserPassword;
		vm.ftpPort = dataOut.data.ftpPort;
		vm.ftpPwd = dataOut.data.ftpPwd;
		vm.ftpServerName = dataOut.data.ftpServerName;
		vm.ftpUser = dataOut.data.ftpUser;
		vm.localFilePath = dataOut.data.localFilePath;
		vm.MailchimpUrlPwd = dataOut.data.mailchimpUrlPwd;
		vm.prayersPerTurn = dataOut.data.prayersPerTurn;
		vm.remoteFilePath = dataOut.data.remoteFilePath;
		vm.statisticsFileName = dataOut.data.statisticsFileName;
		vm.uid = dataOut.data.uid;
	});
}]);