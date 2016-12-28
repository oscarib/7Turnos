package es.edm.controllers;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import es.edm.domain.entity.ConfigurationEntity;
import es.edm.domain.entity.StatisticsEntity;
import es.edm.exceptions.DDBBException;
import es.edm.services.Configuration;
import es.edm.services.FileService;
import es.edm.services.IOtherServices;
import es.edm.services.IPrayerService;
import es.edm.services.ITurnService;

@RestController
public class OtherServicesController {

	@Autowired
	FileService fileService;
	
	@Autowired
	IPrayerService prayerService;
	
	@Autowired
	ITurnService turnService;
	
	@Autowired
	IOtherServices otherServices;
	
	@Autowired
	Configuration conf;
	
	@Value("${label_name}")
	String labelName;
	@Value("${label_email}")
	String labelEmail;
	@Value("${label_ownCountry}")
	String labelOwnCountry;
	@Value("${label_hidden}")
	String labelHidden;
	@Value("${label_phone}")
	String labelPhone;
	@Value("${label_pseudonym}")
	String labelPseudonym;
	@Value("${label_warning}")
	String labelWarning;
	@Value("${label_there_are}")
	String labelThereAre;
	@Value("${label_without_assigned_turns}")
	String labelWithoutAssignedTurns;
	@Value("${label_prayer}")
	String labelPrayer;
	@Value("${label_prayers}")
	String labelPrayers;
	@Value("${label_createPrayer}")
	String labelCreatePrayer;
	@Value("${label_createPrayerOneStep}")
	String labelCreatePrayerOneStep;
	@Value("${label_showCalendar}")
	String labelShowCalendar;
	@Value("${label_showCalendarDescription}")
	String labelShowCalendarDescription;
	@Value("${label_prayerList}")
	String labelPrayerList;
	@Value("${label_prayerListDescription}")
	String labelPrayerListDescription;
	@Value("${label_goTo}")
	String labelGoTo;
	@Value("${label_show}")
	String labelShow;
	@Value("${label_turnList}")
	String labelTurnList;
	@Value("${label_turnListDescription}")
	String labelTurnListDescription;
	@Value("${label_sendCalendar}")
	String labelSendCalendar;
	@Value("${label_sendCalendarDescription}")
	String labelSendCalendarDescription;
	@Value("${label_send}")
	String labelSend;
	@Value("${label_stopedPrayers}")
	String labelStopedPrayers;
	@Value("${label_stopedPrayersDescription}")
	String labelStopedPrayersDescription;
	@Value("${label_emptyTurns}")
	String labelEmptyTurns;
	@Value("${label_emptyTurnsDescription}")
	String labelEmptyTurnsDescription;
	@Value("${label_prayersInCountry}")
	String labelPrayersInCountry;
	@Value("${label_anonymousPrayers}")
	String labelAnonymousPrayers;
	@Value("${label_committed}")
	String labelCommitted;
	@Value("${label_turns}")
	String labelTurns;
	@Value("${label_covered}")
	String labelCovered;
	@Value("${label_empty}")
	String labelEmpty;
	@Value("${label_coveredTurns}")
	String label_coveredTurns;
	@Value("${label_committedPrayers}")
	String label_committedPrayers;
	@Value("${label_nonCommittedPrayers}")
	String label_nonCommittedPrayers;
	@Value("${label_listings}")
	String label_listings;
	@Value("${label_calendar}")
	String label_calendar;
	@Value("${label_dashboard}")
	String label_dashboard;
	@Value("${label_logout}")
	String label_logout;
	@Value("${label_fileRequired}")
	String label_fileRequired;
	@Value("${label_emailError}")
	String label_emailError;
	@Value("${label_phone}")
	String label_phone;
	@Value("${label_country}")
	String label_country;
	@Value("${label_notes}")
	String label_notes;
	@Value("${label_visibility}")
	String label_visibility;
	@Value("${label_prayerData}")
	String label_prayerData;
	@Value("${label_yes}")
	String label_yes;
	@Value("${label_no}")
	String label_no;
	@Value("${label_turnsData}")
	String label_turnsData;
	@Value("${label_day}")
	String label_day;
	@Value("${label_turn}")
	String label_turn;
	@Value("${label_status}")
	String label_status;
	@Value("${label_monday}")
	String label_monday;
	@Value("${label_tuesday}")
	String label_tuesday;
	@Value("${label_wednesday}")
	String label_wednesday;
	@Value("${label_thursday}")
	String label_thursday;
	@Value("${label_friday}")
	String label_friday;
	@Value("${label_saturday}")
	String label_saturday;
	@Value("${label_sunday}")
	String label_sunday;
	@Value("${label_accepted}")
	String label_accepted;
	@Value("${label_notCommitted}")
	String label_notCommitted;
	@Value("${label_cancelled}")
	String label_cancelled;
	@Value("${label_errorAlreadyCreatingPrayer}")
	String label_errorAlreadyCreatingPrayer;
	@Value("${label_errorSavingTurn}")
	String label_errorSavingTurn;
	@Value("${label_errorWas}")
	String label_errorWas;
	@Value("${label_errorSavingPrayer}")
	String label_errorSavingPrayer;
	@Value("${label_turnRemoved}")
	String label_turnRemoved;
	@Value("${label_removeTurnConfirmation}")
	String label_removeTurnConfirmation;
	@Value("${label_prayerRemoved}")
	String label_prayerRemoved;
	@Value("${label_removePrayerAndTurnConfirmation}")
	String label_removePrayerAndTurnConfirmation;
	@Value("${label_errorGettingPrayer}")
	String label_errorGettingPrayer;
	@Value("${label_errorMissingPrayerID}")
	String label_errorMissingPrayerID;
	@Value("${label_errorNotFound}")
	String label_errorNotFound;
	@Value("${label_prayerCreated}")
	String label_prayerCreated;
	@Value("${label_errorPrayerAlreadyExists}")
	String label_errorPrayerAlreadyExists;
	@Value("${label_errorRepetitiveID}")
	String label_errorRepetitiveID;
	@Value("${label_errorCreatingPrayer}")
	String label_errorCreatingPrayer;
	@Value("${label_otherCountry}")
	String label_otherCountry;
	@Value("${label_publico}")
	String label_publico;
	@Value("${label_foreign}")
	String label_foreign;
	@Value("${label_errorGettingPrayerList}")
	String label_errorGettingPrayerList;
	@Value("${label_gettingPrayerList}")
	String label_gettingPrayerList;
	@Value("${label_calendarUpdated}")
	String label_calendarUpdated;
	@Value("${label_errorUpdatingCalendar}")
	String label_errorUpdatingCalendar;
	@Value("${label_loginError}")
	String label_loginError;
	@Value("${label_errorCheckingCredentials}")
	String label_errorCheckingCredentials;
	@Value("${label_uploadingCalendar}")
	String label_uploadingCalendar;
	@Value("${label_loadingStatistics}")
	String label_loadingStatistics;
	@Value("${label_errorLoadingStatistics}")
	String label_errorLoadingStatistics;
	@Value("${label_notCommittedPlural}")
	String label_notCommittedPlural;
	@Value("${label_available}")
	String label_available;
	@Value("${label_publicoPlural}")
	String label_publicoPlural;
	@Value("${label_anonymousPlural}")
	String label_anonymousPlural;
	@Value("${label_notEmpty}")
	String label_notEmpty;
	@Value("${label_updateCalendarShort}")
	String label_updateCalendarShort;
	@Value("${label_configuration}")
	String label_configuration;
	@Value("${label_configurationDescription}")
	String label_configurationDescription;
	@Value("${label_configurationTitle}")
	String label_configurationTitle;
	@Value("${label_chainName}")
	String label_chainName;
	@Value("${label_prayersPerTurn}")
	String label_prayersPerTurn;
	@Value("${label_ftpServerName}")
	String label_ftpServerName;
	@Value("${label_ftpServer}")
	String label_ftpServer;
	@Value("${label_chainSection}")
	String label_chainSection;
	@Value("${label_ftpPort}")
	String label_ftpPort;
	@Value("${label_ftpUser}")
	String label_ftpUser;
	@Value("${label_ftpPwd}")
	String label_ftpPwd;
	@Value("${label_remoteFilePath}")
	String label_remoteFilePath;
	@Value("${label_localFilePath}")
	String label_localFilePath;
	@Value("${label_calendarFileName}")
	String label_calendarFileName;
	@Value("${label_statisticsFileName}")
	String label_statisticsFileName;
	@Value("${label_mailchimpUrlPwd}")
	String label_mailchimpUrlPwd;
	@Value("${label_emailServiceUserName}")
	String label_emailServiceUserName;
	@Value("${label_emailServiceUserPassword}")
	String label_emailServiceUserPassword;
	@Value("${label_emailServiceHostName}")
	String label_emailServiceHostName;
	@Value("${label_emailServiceSmtpPort}")
	String label_emailServiceSmtpPort;
	@Value("${label_emailStuff}")
	String label_emailStuff;
	@Value("${label_otherConfiguration}")
	String label_otherConfiguration;
	@Value("${label_noNeedToSave}")
	String label_noNeedToSave;
	@Value("${label_noDataHasChanged}")
	String label_noDataHasChanged;
	@Value("${label_emailServiceSSL}")
	String label_emailServiceSSL;
	
	private final static Logger logger = LoggerFactory.getLogger(OtherServicesController.class);

	
	@ResponseBody
	@RequestMapping(value="/getProperties.do", method = RequestMethod.POST)
	public Properties getProperties(){
		Properties properties = new Properties();
		properties.setProperty("label_emailStuff", label_emailStuff);
		properties.setProperty("label_otherConfiguration", label_otherConfiguration);
		properties.setProperty("label_remoteFilePath", label_remoteFilePath);
		properties.setProperty("label_localFilePath", label_localFilePath);
		properties.setProperty("label_calendarFileName", label_calendarFileName);
		properties.setProperty("label_statisticsFileName", label_statisticsFileName);
		properties.setProperty("label_mailchimpUrlPwd", label_mailchimpUrlPwd);
		properties.setProperty("label_emailServiceUserName", label_emailServiceUserName);
		properties.setProperty("label_emailServiceUserPassword", label_emailServiceUserPassword);
		properties.setProperty("label_emailServiceHostName", label_emailServiceHostName);
		properties.setProperty("label_emailServiceSmtpPort", label_emailServiceSmtpPort);
		properties.setProperty("label_ftpUser", label_ftpUser);
		properties.setProperty("label_ftpPwd", label_ftpPwd);
		properties.setProperty("label_name", labelName);
		properties.setProperty("label_email", labelEmail);
		properties.setProperty("label_phone", labelPhone);
		properties.setProperty("label_ownCountry", labelOwnCountry);
		properties.setProperty("label_hidden", labelHidden);
		properties.setProperty("label_pseudonym", labelPseudonym);
		properties.setProperty("label_warning", labelWarning);
		properties.setProperty("label_there_are", labelThereAre);
		properties.setProperty("label_without_assigned_turns", labelWithoutAssignedTurns);
		properties.setProperty("label_prayer", labelPrayer);
		properties.setProperty("label_prayers", labelPrayers);
		properties.setProperty("label_createPrayer", labelCreatePrayer);
		properties.setProperty("label_createPrayerOneStep", labelCreatePrayerOneStep);
		properties.setProperty("label_showCalendar", labelShowCalendar);
		properties.setProperty("label_showCalendarDescription", labelShowCalendarDescription);
		properties.setProperty("label_prayerList", labelPrayerList);
		properties.setProperty("label_prayerListDescription", labelPrayerListDescription);
		properties.setProperty("label_goTo", labelGoTo);
		properties.setProperty("label_show", labelShow);
		properties.setProperty("label_turnList", labelTurnList);
		properties.setProperty("label_turnListDescription", labelTurnListDescription);
		properties.setProperty("label_sendCalendar", labelSendCalendar);
		properties.setProperty("label_sendCalendarDescription", labelSendCalendarDescription);
		properties.setProperty("label_send", labelSend);
		properties.setProperty("label_stopedPrayers", labelStopedPrayers);
		properties.setProperty("label_stopedPrayersDescription", labelStopedPrayersDescription);
		properties.setProperty("label_emptyTurns", labelEmptyTurns);
		properties.setProperty("label_emptyTurnsDescription", labelEmptyTurnsDescription);
		properties.setProperty("label_prayersInCountry", labelPrayersInCountry);
		properties.setProperty("label_anonymousPrayers", labelAnonymousPrayers);
		properties.setProperty("label_committed", labelCommitted);
		properties.setProperty("label_turns", labelTurns);
		properties.setProperty("label_covered", labelCovered);
		properties.setProperty("label_empty", labelEmpty);
		properties.setProperty("label_coveredTurns", label_coveredTurns);
		properties.setProperty("label_committedPrayers", label_committedPrayers);
		properties.setProperty("label_nonCommittedPrayers", label_nonCommittedPrayers);
		properties.setProperty("label_listings", label_listings);
		properties.setProperty("label_calendar", label_calendar);
		properties.setProperty("label_dashboard", label_dashboard);
		properties.setProperty("label_logout", label_logout);
		properties.setProperty("label_fileRequired", label_fileRequired);
		properties.setProperty("label_emailError", label_emailError);
		properties.setProperty("label_phone", label_phone);
		properties.setProperty("label_country", label_country);
		properties.setProperty("label_notes", label_notes);
		properties.setProperty("label_visibility", label_visibility);
		properties.setProperty("label_prayerData", label_prayerData);
		properties.setProperty("label_yes", label_yes);
		properties.setProperty("label_no", label_no);
		properties.setProperty("label_turnsData", label_turnsData);
		properties.setProperty("label_day", label_day);
		properties.setProperty("label_turn", label_turn);
		properties.setProperty("label_status", label_status);
		properties.setProperty("label_monday", label_monday);
		properties.setProperty("label_tuesday", label_tuesday);
		properties.setProperty("label_wednesday", label_wednesday);
		properties.setProperty("label_thursday", label_thursday);
		properties.setProperty("label_friday", label_friday);
		properties.setProperty("label_saturday", label_saturday);
		properties.setProperty("label_sunday", label_sunday);
		properties.setProperty("label_accepted", label_accepted);
		properties.setProperty("label_notCommitted", label_notCommitted);
		properties.setProperty("label_cancelled", label_cancelled);
		properties.setProperty("label_errorAlreadyCreatingPrayer", label_errorAlreadyCreatingPrayer);
		properties.setProperty("label_errorSavingTurn", label_errorSavingTurn);
		properties.setProperty("label_errorWas", label_errorWas);
		properties.setProperty("label_errorSavingPrayer", label_errorSavingPrayer);
		properties.setProperty("label_turnRemoved", label_turnRemoved);
		properties.setProperty("label_removeTurnConfirmation", label_removeTurnConfirmation);
		properties.setProperty("label_prayerRemoved", label_prayerRemoved);
		properties.setProperty("label_removePrayerAndTurnConfirmation", label_removePrayerAndTurnConfirmation);
		properties.setProperty("label_errorGettingPrayer", label_errorGettingPrayer);
		properties.setProperty("label_errorMissingPrayerID", label_errorMissingPrayerID);
		properties.setProperty("label_errorNotFound", label_errorNotFound);
		properties.setProperty("label_prayerCreated", label_prayerCreated);
		properties.setProperty("label_errorPrayerAlreadyExists", label_errorPrayerAlreadyExists);
		properties.setProperty("label_errorRepetitiveID", label_errorRepetitiveID);
		properties.setProperty("label_errorCreatingPrayer", label_errorCreatingPrayer);
		properties.setProperty("label_otherCountry", label_otherCountry);
		properties.setProperty("label_publico", label_publico);
		properties.setProperty("label_foreign", label_foreign);
		properties.setProperty("label_errorGettingPrayerList", label_errorGettingPrayerList);
		properties.setProperty("label_gettingPrayerList", label_gettingPrayerList);
		properties.setProperty("label_calendarUpdated", label_calendarUpdated);
		properties.setProperty("label_errorUpdatingCalendar", label_errorUpdatingCalendar);
		properties.setProperty("label_loginError", label_loginError);
		properties.setProperty("label_errorCheckingCredentials", label_errorCheckingCredentials);
		properties.setProperty("label_uploadingCalendar", label_uploadingCalendar);
		properties.setProperty("label_loadingStatistics", label_loadingStatistics);
		properties.setProperty("label_errorLoadingStatistics", label_errorLoadingStatistics);
		properties.setProperty("label_notCommittedPlural", label_notCommittedPlural);
		properties.setProperty("label_available", label_available);
		properties.setProperty("label_publicoPlural", label_publicoPlural);
		properties.setProperty("label_anonymousPlural", label_anonymousPlural);
		properties.setProperty("label_notEmpty", label_notEmpty);
		properties.setProperty("label_updateCalendarShort", label_updateCalendarShort);
		properties.setProperty("label_configuration", label_configuration);
		properties.setProperty("label_configurationDescription", label_configurationDescription);
		properties.setProperty("label_configurationTitle", label_configurationTitle);
		properties.setProperty("label_chainName", label_chainName);
		properties.setProperty("label_prayersPerTurn", label_prayersPerTurn);
		properties.setProperty("label_ftpServerName", label_ftpServerName);
		properties.setProperty("label_ftpServer", label_ftpServer);
		properties.setProperty("label_chainSection", label_chainSection);
		properties.setProperty("label_ftpPort", label_ftpPort);
		properties.setProperty("label_ftpPort", label_ftpPort);
		properties.setProperty("label_noDataHasChanged", label_noDataHasChanged);
		properties.setProperty("label_noNeedToSave", label_noNeedToSave);
		properties.setProperty("label_emailServiceSSL", label_emailServiceSSL);

		return properties;
	}

	//CONTROLLER: UPLOADCALENDAR
	@RequestMapping(path="/uploadCalendar.do", method=RequestMethod.POST)
	@ResponseBody
	public boolean UploadCalendar() throws IOException, DDBBException{
		try{
			fileService.WriteFile(fileService.getCalendarTableString(1), conf.getCalendarFile2UploadURI());
			logger.info("Generando el archivo del calendario...");
			fileService.WriteFile(fileService.getStatisticsString(), conf.getStatisticsFile2UploadURI());
			logger.info("Generando el archivo de estad√≠sticas...");
			fileService.UploadFileFTP(conf.getCalendarFile2UploadURI(), conf.getCalendarRemoteFileURI());
			logger.info("Subiendo los archivos al sitio FTP...");
			fileService.UploadFileFTP(conf.getStatisticsFile2UploadURI(), conf.getStatisticsRemoteFileURI());
		} catch (UnknownHostException e){
			throw new RuntimeException("Error: " + e);
		} catch (SocketException e) {
			throw new RuntimeException("Error: " + e);
		}
		logger.info("Se subieron los archivos con Èxito al sitio FTP.");
		return true;
	}
	
	//CONTROLLER: GETSTATISTICS
	@ResponseBody
	@RequestMapping(value = "/getStatistics.do", method = RequestMethod.POST)
	public StatisticsEntity getallStatistics() {
		
		StatisticsEntity statistics = otherServices.getStatistics();
		
		return statistics;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getCalendarHtml.do", method = RequestMethod.POST)
	public List<String> getCalendarString(){
		List<String> calendar = new ArrayList<String>();
		String calendarString = fileService.getCalendarTableString(4);
		calendar.add(calendarString);
		return calendar;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getConfiguration.do", method = RequestMethod.POST)
	public ConfigurationEntity getConfiguration(){
		return conf.getConfigurationEntity();
	}

	@ResponseBody
	@RequestMapping(value = "/setConfiguration.do", method = RequestMethod.POST)
	public Boolean setConfiguration(ConfigurationEntity conf){
		return true;
	}
}
