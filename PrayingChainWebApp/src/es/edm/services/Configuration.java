package es.edm.services;

public interface Configuration {

	public String getFtpServerName();
	public boolean showPrayerNamesOnCalendar();
	public int getPrayersPerTurn();
	public int getFtpPort();
	public String getFtpUser();
	public String getFtpPwd();
	public boolean isPrintFtpUpladingMessages();
	public String getCalendarFile2UploadURI();
	public String getCalendarRemoteFileURI();
	public String getStatisticsFile2UploadURI();
	public String getStatisticsRemoteFileURI();
	public String getDDBBConnectionString();
	public String getDDBBClassName();
	public boolean isMySqlWarningMessagesActivated();
	public boolean isDetailedInfoActivatedForSaturedTurns();
	public int getMinimumNumberOfPrayersForWhatsappGroups();
	public boolean isEnoughPrayersForWhatsappGroupWarningActive();
}
