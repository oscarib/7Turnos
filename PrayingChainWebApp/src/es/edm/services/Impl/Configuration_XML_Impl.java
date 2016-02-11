package es.edm.services.Impl;

import es.edm.services.Configuration;

public class Configuration_XML_Impl implements Configuration{
	String ftpServerName;
	int ftpPort;
	String ftpUser;
	String ftpPwd;
	boolean printFtpUpladingMessages;
	String localFilePath;
	String remoteFilePath;
	String calendarFileName;
	String statisticsFileName;
	String ddbbClassName;
	String mySqlServerHostName;
	String mySqlServerPortNumber;
	String mySqlServerDDBBName;
	String mySqlServerUser;
	String mySqlServerUserPwd;
	boolean displaySaturedTurns;
	boolean detailedNotesOnSaturedTurns;
	boolean enoughPrayersForWhatsappGroupWarning;
	int minimumNumberOfPrayersForWhatsappGroups;
	int prayersPerTurn;
	boolean showPrayerNamesOnCalendar;
	
	// For MailingList Configuration
	String mailingListSecretPassword;
	
	// For Mail Service
	String EmailServiceUserName;
	String EmailServiceUserPassword;
	String EmailServiceHostName;
	int EmailServiceSmtpPort;
	boolean EmailServiceIsSSL;

	public void setEmailServiceUserName(String emailServiceUserName) {
		EmailServiceUserName = emailServiceUserName;
	}

	public void setEmailServiceUserPassword(String emailServiceUserPassword) {
		EmailServiceUserPassword = emailServiceUserPassword;
	}

	public void setEmailServiceHostName(String emailServiceHostName) {
		EmailServiceHostName = emailServiceHostName;
	}

	public void setEmailServiceSmtpPort(int emailServiceSmtpPort) {
		EmailServiceSmtpPort = emailServiceSmtpPort;
	}

	@Override
	public String getMailingListSecretPassword() {
		return mailingListSecretPassword;
	}

	@Override
	public void setMailingListSecretPassword(String mailingListSecretPassword) {
		this.mailingListSecretPassword = mailingListSecretPassword;
	}

	public String getLocalFilePath() {
		return localFilePath;
	}

	public void setLocalFilePath(String localFilePath) {
		this.localFilePath = localFilePath;
	}

	public String getRemoteFilePath() {
		return remoteFilePath;
	}

	public void setRemoteFilePath(String remoteFilePath) {
		this.remoteFilePath = remoteFilePath;
	}

	public String getCalendarFileName() {
		return calendarFileName;
	}

	public void setCalendarFileName(String calendarFileName) {
		this.calendarFileName = calendarFileName;
	}

	public String getStatisticsFileName() {
		return statisticsFileName;
	}

	public void setStatisticsFileName(String statisticsFileName) {
		this.statisticsFileName = statisticsFileName;
	}

	public String getDdbbClassName() {
		return ddbbClassName;
	}

	public void setDdbbClassName(String ddbbClassName) {
		this.ddbbClassName = ddbbClassName;
	}

	public String getMySqlServerHostName() {
		return mySqlServerHostName;
	}

	public void setMySqlServerHostName(String mySqlServerHostName) {
		this.mySqlServerHostName = mySqlServerHostName;
	}

	public String getMySqlServerPortNumber() {
		return mySqlServerPortNumber;
	}

	public void setMySqlServerPortNumber(String mySqlServerPortNumber) {
		this.mySqlServerPortNumber = mySqlServerPortNumber;
	}

	public String getMySqlServerDDBBName() {
		return mySqlServerDDBBName;
	}

	public void setMySqlServerDDBBName(String mySqlServerDDBBName) {
		this.mySqlServerDDBBName = mySqlServerDDBBName;
	}

	public String getMySqlServerUser() {
		return mySqlServerUser;
	}

	public void setMySqlServerUser(String mySqlServerUser) {
		this.mySqlServerUser = mySqlServerUser;
	}

	public String getMySqlServerUserPwd() {
		return mySqlServerUserPwd;
	}

	public void setMySqlServerUserPwd(String mySqlServerUserPwd) {
		this.mySqlServerUserPwd = mySqlServerUserPwd;
	}

	public boolean isDisplaySaturedTurns() {
		return displaySaturedTurns;
	}

	public void setDisplaySaturedTurns(boolean displaySaturedTurns) {
		this.displaySaturedTurns = displaySaturedTurns;
	}

	public boolean isDetailedNotesOnSaturedTurns() {
		return detailedNotesOnSaturedTurns;
	}

	public void setDetailedNotesOnSaturedTurns(boolean detailedNotesOnSaturedTurns) {
		this.detailedNotesOnSaturedTurns = detailedNotesOnSaturedTurns;
	}

	public boolean isEnoughPrayersForWhatsappGroupWarning() {
		return enoughPrayersForWhatsappGroupWarning;
	}

	public void setEnoughPrayersForWhatsappGroupWarning(boolean enoughPrayersForWhatsappGroupWarning) {
		this.enoughPrayersForWhatsappGroupWarning = enoughPrayersForWhatsappGroupWarning;
	}

	public boolean isShowPrayerNamesOnCalendar() {
		return showPrayerNamesOnCalendar;
	}

	public void setShowPrayerNamesOnCalendar(boolean showPrayerNamesOnCalendar) {
		this.showPrayerNamesOnCalendar = showPrayerNamesOnCalendar;
	}

	public void setFtpServerName(String ftpServerName) {
		this.ftpServerName = ftpServerName;
	}

	public void setFtpPort(int ftpPort) {
		this.ftpPort = ftpPort;
	}

	public void setFtpUser(String ftpUser) {
		this.ftpUser = ftpUser;
	}

	public void setFtpPwd(String ftpPwd) {
		this.ftpPwd = ftpPwd;
	}

	public void setPrintFtpUpladingMessages(boolean printFtpUpladingMessages) {
		this.printFtpUpladingMessages = printFtpUpladingMessages;
	}

	public void setMinimumNumberOfPrayersForWhatsappGroups(int minimumNumberOfPrayersForWhatsappGroups) {
		this.minimumNumberOfPrayersForWhatsappGroups = minimumNumberOfPrayersForWhatsappGroups;
	}

	public void setPrayersPerTurn(int prayersPerTurn) {
		this.prayersPerTurn = prayersPerTurn;
	}

	/**
	 * @return the ftpServerName
	 */
	public String getFtpServerName() {
		return ftpServerName;
	}
	
	public boolean showPrayerNamesOnCalendar(){
		return showPrayerNamesOnCalendar;
	}
	
	/**
	 * @return the ftpServerName
	 */
	public int getPrayersPerTurn() {
		return prayersPerTurn;
	}

	/**
	 * @return the ftpPort
	 */
	public int getFtpPort() {
		return ftpPort;
	}

	/**
	 * @return the ftpUser
	 */
	public String getFtpUser() {
		return ftpUser;
	}

	/**
	 * @return the ftpPwd
	 */
	public String getFtpPwd() {
		return ftpPwd;
	}

	/**
	 * @return the printFtpUpladingMessages
	 */
	public boolean isPrintFtpUpladingMessages() {
		return printFtpUpladingMessages;
	}

	/**
	 * @return the file2UploadURI
	 */
	public String getCalendarFile2UploadURI() {
		return localFilePath+calendarFileName;
	}

	/**
	 * @return the remoteFileURI
	 */
	public String getCalendarRemoteFileURI() {
		return remoteFilePath+calendarFileName;
	}
	
	/**
	 * @return the file2UploadURI
	 */
	public String getStatisticsFile2UploadURI() {
		return localFilePath+statisticsFileName;
	}

	/**
	 * @return the remoteFileURI
	 */
	public String getStatisticsRemoteFileURI() {
		return remoteFilePath+statisticsFileName;
	}
	
	public String getDDBBConnectionString(){
		return "jdbc:mysql://"+mySqlServerHostName+":"+mySqlServerPortNumber+"/"+mySqlServerDDBBName+"?user="+mySqlServerUser+"&password="+mySqlServerUserPwd;
	}
	
	public String getDDBBClassName(){
		return ddbbClassName;
	}
	
	public boolean isMySqlWarningMessagesActivated(){
		return displaySaturedTurns;
	}
	
	public boolean isDetailedInfoActivatedForSaturedTurns(){
		return detailedNotesOnSaturedTurns;
	}
	
	public int getMinimumNumberOfPrayersForWhatsappGroups(){
		return minimumNumberOfPrayersForWhatsappGroups;
	}
	
	public boolean isEnoughPrayersForWhatsappGroupWarningActive(){
		return enoughPrayersForWhatsappGroupWarning;
	}

	@Override
	public String getEmailServiceUserName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getEmailServiceUserPassword() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getEmailServiceHostName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getEmailServiceSmtpPort() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isEmailServiceSSL() {
		return EmailServiceIsSSL;
	}
	
	@Override
	public void setEmailServiceSSL(boolean emailServiceIsSSL) {
		EmailServiceIsSSL = emailServiceIsSSL;
	}

}
