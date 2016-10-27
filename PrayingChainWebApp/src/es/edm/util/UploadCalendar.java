package es.edm.util;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import es.edm.services.Configuration;
import es.edm.services.FileService;
import es.edm.services.MainService;
public class UploadCalendar {
	
	private final static Logger logger = LoggerFactory.getLogger(UploadCalendar.class);
    
    public static void main(String args[]) throws IOException{
    	ClassPathXmlApplicationContext container = new ClassPathXmlApplicationContext("Application.xml");
    	Configuration conf = container.getBean(Configuration.class);

        logger.info("");
        logger.info("");
        logger.info("Execution of Remote Calendar bussiness logic");
        logger.info("============================================");
        logger.info("");
        logger.info("");
        logger.info("Reading DDBB prayers and turns...");
    	MainService main = container.getBean(MainService.class);
        if (!conf.isMySqlWarningMessagesActivated()) {
            logger.info("\t(Skipping warning messages)");
        }
                
        FileService fs = container.getBean(FileService.class);
        fs.WriteFile(main.getCalendarTableString(), conf.getCalendarFile2UploadURI());
        fs.WriteFile(main.getStatisticsString(), conf.getStatisticsFile2UploadURI());
        fs.UploadFileFTP(conf.getCalendarFile2UploadURI(), conf.getCalendarRemoteFileURI());
        fs.UploadFileFTP(conf.getStatisticsFile2UploadURI(), conf.getStatisticsRemoteFileURI());
        logger.info("");
        logger.info("Statistics of the Praying Plan");
        logger.info("---------------------------------------------\t-------------");
        logger.info("Total Turns: \t\t\t\t\t" + main.getTotalTurns());
        logger.info("Turns covered: \t\t\t\t\t" + main.getUsedTurns() + " (" + main.getTurnsUsedPercentage() + "%)");
        logger.info("Available turns: \t\t\t\t" + main.getFreeTurns() + " (" + main.getFreeTurnsPercentage() + "%)");
        logger.info("Empty turns: \t\t\t\t\t" + main.getEmptyTurns() + " (" + main.getEmptyTurnsPercentage() + "%)");
        int committedPrayers = main.getNumberOfCommittedPrayers();
        int nonCommittedPrayers = main.getNumberOfNonCommittedPrayers();
        logger.info("Committed Prayers: \t\t\t\t" + committedPrayers);
        logger.info("Non Committed Prayers: \t\t\t\t" + nonCommittedPrayers);
        logger.info("Total Prayers: \t\t\t\t\t" + (nonCommittedPrayers + committedPrayers));
        if (conf.isEnoughPrayersForWhatsappGroupWarningActive()) {
        	if (main.getWhatssappCandidates(DayOfWeek.monday, "6").size() >= conf.getMinimumNumberOfPrayersForWhatsappGroups()) {
        		logger.info("Warning!! Whatsapp Candidates: (Mondays): \t" + main.getWhatssappCandidates(DayOfWeek.monday, "6").size());
        	}
        	if (main.getWhatssappCandidates(DayOfWeek.tuesday, "6").size() >= conf.getMinimumNumberOfPrayersForWhatsappGroups()) {
        		logger.info("Warning!! Whatsapp Candidates: (Tuesdays): \t" + main.getWhatssappCandidates(DayOfWeek.tuesday, "6").size());
        	}
        	if (main.getWhatssappCandidates(DayOfWeek.wednesday, "6").size() >= conf.getMinimumNumberOfPrayersForWhatsappGroups()) {
        		logger.info("Warning!! Whatsapp Candidates: (Wednesdays): \t" + main.getWhatssappCandidates(DayOfWeek.wednesday, "6").size());
        	}
        	if (main.getWhatssappCandidates(DayOfWeek.thursday, "6").size() >= conf.getMinimumNumberOfPrayersForWhatsappGroups()) {
        		logger.info("Warning!! Whatsapp Candidates: (Thursdays): \t" + main.getWhatssappCandidates(DayOfWeek.thursday, "6").size());
        	}
        	if (main.getWhatssappCandidates(DayOfWeek.friday, "6").size() >= conf.getMinimumNumberOfPrayersForWhatsappGroups()) {
        		logger.info("Warning!! Whatsapp Candidates: (Fridays): \t" + main.getWhatssappCandidates(DayOfWeek.friday, "6").size());
        	}
        	if (main.getWhatssappCandidates(DayOfWeek.saturday, "6").size() >= conf.getMinimumNumberOfPrayersForWhatsappGroups()) {
        		logger.info("Warning!! Whatsapp Candidates: (Saturdays): \t" + main.getWhatssappCandidates(DayOfWeek.saturday, "6").size());
        	}
        	if (main.getWhatssappCandidates(DayOfWeek.sunday, "6").size() >= conf.getMinimumNumberOfPrayersForWhatsappGroups()) {
        		logger.info("Warning!! Whatsapp Candidates: (Sundays): \t" + main.getWhatssappCandidates(DayOfWeek.sunday, "6").size());
        	}
        }
        logger.info("Redundancy: (Committed / Used Turns)\t\t" + main.getCommittedRedundancy() + "%");
        logger.info("Redundancy: (Total Prayers / Used Turns)\t" + main.getTotalRedundancy() + "%");
        logger.info("");
        logger.info("");
        logger.info("================================");
        logger.info("End.");
        logger.info("___");
        logger.info("");
        logger.info("UploadCalendar v1.5.2.beta");
        logger.info("Made by Oscar Ib.");
        logger.info("oscar.ibafer@gmail.com");
        logger.info("Website: http://españademaria.es");
        logger.info("================================");
        logger.info("");
        container.close();
    }
}
