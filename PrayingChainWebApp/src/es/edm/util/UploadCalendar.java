package es.edm.util;

import java.io.IOException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import es.edm.services.Configuration;
import es.edm.services.FileService;
import es.edm.services.MainService;
public class UploadCalendar {
	
    
    public static void main(String args[]) throws IOException{
    	ClassPathXmlApplicationContext container = new ClassPathXmlApplicationContext("Application.xml");
    	Configuration conf = container.getBean(Configuration.class);

        System.out.println("");
        System.out.println("");
        System.out.println("Execution of Remote Calendar bussiness logic");
        System.out.println("============================================");
        System.out.println("");
        System.out.println("");
        System.out.println("Reading DDBB prayers and turns...");
    	MainService main = container.getBean(MainService.class);
        if (!conf.isMySqlWarningMessagesActivated()) {
            System.out.println("\t(Skipping warning messages)");
        }
                
        //TODO: Implement Writting and Uploading Files
        FileService fs = container.getBean(FileService.class);
        fs.WriteFile(main.getCalendarTableString(), conf.getCalendarFile2UploadURI());
        fs.WriteFile(main.getStatisticsString(), conf.getStatisticsFile2UploadURI());
        fs.UploadFileFTP(conf.getCalendarFile2UploadURI(), conf.getCalendarRemoteFileURI());
        fs.UploadFileFTP(conf.getStatisticsFile2UploadURI(), conf.getStatisticsRemoteFileURI());
        System.out.println("");
        System.out.println("Statistics of the Praying Plan");
        System.out.println("---------------------------------------------\t-------------");
        System.out.println("Total Turns: \t\t\t\t\t" + main.getTotalTurns());
        System.out.println("Turns covered: \t\t\t\t\t" + main.getUsedTurns() + " (" + main.getTurnsUsedPercentage() + "%)");
        System.out.println("Available turns: \t\t\t\t" + main.getFreeTurns() + " (" + main.getFreeTurnsPercentage() + "%)");
        System.out.println("Empty turns: \t\t\t\t\t" + main.getEmptyTurns() + " (" + main.getEmptyTurnsPercentage() + "%)");
        int committedPrayers = main.getNumberOfCommittedPrayers();
        int nonCommittedPrayers = main.getNumberOfNonCommittedPrayers();
        System.out.println("Committed Prayers: \t\t\t\t" + committedPrayers);
        System.out.println("Non Committed Prayers: \t\t\t\t" + nonCommittedPrayers);
        System.out.println("Total Prayers: \t\t\t\t\t" + (nonCommittedPrayers + committedPrayers));
        if (conf.isEnoughPrayersForWhatsappGroupWarningActive()) {
//        	if (true){
        	if (main.getWhatssappCandidates(DayOfWeek.monday, "6").size() >= conf.getMinimumNumberOfPrayersForWhatsappGroups()) {
        		System.out.println("Warning!! Whatsapp Candidates: (Mondays): \t" + main.getWhatssappCandidates(DayOfWeek.monday, "6").size());
        	}
        	if (main.getWhatssappCandidates(DayOfWeek.tuesday, "6").size() >= conf.getMinimumNumberOfPrayersForWhatsappGroups()) {
        		System.out.println("Warning!! Whatsapp Candidates: (Tuesdays): \t" + main.getWhatssappCandidates(DayOfWeek.tuesday, "6").size());
        	}
        	if (main.getWhatssappCandidates(DayOfWeek.wednesday, "6").size() >= conf.getMinimumNumberOfPrayersForWhatsappGroups()) {
        		System.out.println("Warning!! Whatsapp Candidates: (Wednesdays): \t" + main.getWhatssappCandidates(DayOfWeek.wednesday, "6").size());
        	}
        	if (main.getWhatssappCandidates(DayOfWeek.thursday, "6").size() >= conf.getMinimumNumberOfPrayersForWhatsappGroups()) {
        		System.out.println("Warning!! Whatsapp Candidates: (Thursdays): \t" + main.getWhatssappCandidates(DayOfWeek.thursday, "6").size());
        	}
        	if (main.getWhatssappCandidates(DayOfWeek.friday, "6").size() >= conf.getMinimumNumberOfPrayersForWhatsappGroups()) {
        		System.out.println("Warning!! Whatsapp Candidates: (Fridays): \t" + main.getWhatssappCandidates(DayOfWeek.friday, "6").size());
        	}
        	if (main.getWhatssappCandidates(DayOfWeek.saturday, "6").size() >= conf.getMinimumNumberOfPrayersForWhatsappGroups()) {
        		System.out.println("Warning!! Whatsapp Candidates: (Saturdays): \t" + main.getWhatssappCandidates(DayOfWeek.saturday, "6").size());
        	}
        	if (main.getWhatssappCandidates(DayOfWeek.sunday, "6").size() >= conf.getMinimumNumberOfPrayersForWhatsappGroups()) {
        		System.out.println("Warning!! Whatsapp Candidates: (Sundays): \t" + main.getWhatssappCandidates(DayOfWeek.sunday, "6").size());
        	}
        }
        System.out.println("Redundancy: (Committed / Used Turns)\t\t" + main.getCommittedRedundancy() + "%");
        System.out.println("Redundancy: (Total Prayers / Used Turns)\t" + main.getTotalRedundancy() + "%");
        System.out.println("");
        System.out.println("");
        System.out.println("================================");
        System.out.println("End.");
        System.out.println("___");
        System.out.println("");
        System.out.println("UploadCalendar v1.5.2.beta");
        System.out.println("Made by Oscar Ib.");
        System.out.println("oscar.ibafer@gmail.com");
        System.out.println("Website: http://españademaria.es");
        System.out.println("================================");
        System.out.println("");
        container.close();
    }
}
