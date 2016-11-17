package es.edm.services.Impl;

import org.springframework.beans.factory.annotation.Autowired;

import es.edm.domain.entity.ConfigurationEntity;
import es.edm.services.Configuration;
import es.edm.services.IOtherServices;

public class Configuration_DDBB_Impl implements Configuration {
	
	@Autowired
	IOtherServices otherServices;

	@Override
	public String getFtpServerName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getPrayersPerTurn() {
		ConfigurationEntity conf = otherServices.getConfiguration();
		return conf.getPrayersPerTurn();
	}

	@Override
	public int getFtpPort() {
		ConfigurationEntity conf = otherServices.getConfiguration();
		return conf.getFtpPort();
	}

	@Override
	public String getFtpUser() {
		ConfigurationEntity conf = otherServices.getConfiguration();
		return conf.getFtpUser();
	}

	@Override
	public String getFtpPwd() {
		ConfigurationEntity conf = otherServices.getConfiguration();
		return conf.getFtpPwd();
	}

	@Override
	public String getCalendarFile2UploadURI() {
		ConfigurationEntity conf = otherServices.getConfiguration();
		return conf.getLocalFilePath()+conf.getCalendarFileName();
	}

	@Override
	public String getCalendarRemoteFileURI() {
		ConfigurationEntity conf = otherServices.getConfiguration();
		return conf.getRemoteFilePath()+conf.getCalendarFileName();
	}

	@Override
	public String getStatisticsFile2UploadURI() {
		ConfigurationEntity conf = otherServices.getConfiguration();
		return conf.getLocalFilePath()+conf.getStatisticsFileName();
	}

	@Override
	public String getStatisticsRemoteFileURI() {
		ConfigurationEntity conf = otherServices.getConfiguration();
		return conf.getRemoteFilePath()+conf.getStatisticsFileName();
	}

	@Override
	public String getEmailServiceUserName() {
		ConfigurationEntity conf = otherServices.getConfiguration();
		return conf.getEmailServiceUserName();
	}

	@Override
	public String getEmailServiceUserPassword() {
		ConfigurationEntity conf = otherServices.getConfiguration();
		return conf.getEmailServiceUserPassword();
	}

	@Override
	public String getEmailServiceHostName() {
		ConfigurationEntity conf = otherServices.getConfiguration();
		return conf.getEmailServiceHostName();
	}

	@Override
	public boolean isEmailServiceSSL() {
		ConfigurationEntity conf = otherServices.getConfiguration();
		return conf.isEmailServiceSSL();
	}

	@Override
	public int getEmailServiceSmtpPort() {
		ConfigurationEntity conf = otherServices.getConfiguration();
		return conf.getEmailServiceSmtpPort();
	}

	@Override
	public String getMailchimpUrlPwd() {
		ConfigurationEntity conf = otherServices.getConfiguration();
		return conf.getMailchimpUrlPwd();
	}
}
