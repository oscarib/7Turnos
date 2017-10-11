package es.edm.services;

import es.edm.domain.entity.ConfigurationEntity;

public interface Configuration {

    public String getFtpServerName();

    public int getPrayersPerTurn();

    public int getFtpPort();

    public String getFtpUser();

    public String getFtpPwd();

    public String getCalendarFile2UploadURI();

    public String getCalendarRemoteFileURI();

    public String getStatisticsFile2UploadURI();

    public String getStatisticsRemoteFileURI();

    public String getMailchimpUrlPwd();

    public String getEmailServiceUserName();

    public String getEmailServiceUserPassword();

    public String getEmailServiceHostName();

    public boolean isEmailServiceSSL();

    public int getEmailServiceSmtpPort();

    public ConfigurationEntity getConfigurationEntity();
}
