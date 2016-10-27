package es.edm.services;

import java.util.List;

import org.joda.time.DateTime;

import es.edm.domain.Prayer;
import es.edm.domain.SimpleTurn;
import es.edm.exceptions.DDBBException;
import es.edm.exceptions.DataTimeException;
import es.edm.exceptions.EmptyParameterException;
import es.edm.exceptions.PrayerException;
import es.edm.exceptions.PrayerNotFoundException;
import es.edm.exceptions.TurnAlreadyExistsException;
import es.edm.exceptions.TurnException;
import es.edm.util.DayOfWeek;

public interface MainService {
	
	//Set methods
	public void changePrayer(Prayer prayer) throws PrayerNotFoundException, PrayerException;
	public void addTurn(int prayerId, SimpleTurn turn) throws PrayerNotFoundException, TurnAlreadyExistsException, TurnException;
	
	//Get methods
	public List<SimpleTurn> getAllTurns() throws DDBBException;
	public List<SimpleTurn> getAllActiveTurns() throws DDBBException;
	public List<Prayer> getPrayersByName(String name) throws PrayerNotFoundException;
	public Prayer getPrayerByID(int id) throws PrayerNotFoundException;
	public List<Prayer> getPrayersByNotes(String notesMask) throws PrayerNotFoundException;
	public boolean isTurnPrayedByPrayer(int prayerID, DayOfWeek dow, int turn) throws TurnException;
	public List<Prayer> getPrayersBetween(DateTime fromDate, DateTime toDate) throws DataTimeException, PrayerNotFoundException;
	public List<SimpleTurn> getOrphanTurns(); //Search for orphan turns
	public List<Prayer> getOrphanPrayers(); //Search for orphan prayers (no related turn at all)
	public List<Prayer> getCancelledPrayers(); //search for prayers with no more than cancelled turns
	public List<Prayer> getWhatssappCandidates(DayOfWeek dow, String phoneMask); //All prayers without email but with phone (starting by a mask)
	public List<SimpleTurn> getPrayerTurns(int prayerId) throws PrayerNotFoundException; //all turns on all days of a given prayer
	public List<Prayer> andMixingOfLists(List<Prayer> list1, List<Prayer> list2) throws EmptyParameterException;
	public List<Prayer> orMixingOfLists(List<Prayer> list1, List<Prayer> list2) throws EmptyParameterException;
	
	//Statistics methods
	public int getEmptyTurns();
	public int getNumberOfCommittedPrayers();
	public int getNumberOfNonCommittedPrayers();
	public int getFreeTurns(); //Total turns - used turns
	public int getTotalTurns();  
	public String getTurnsUsedPercentage();
	public String getFreeTurnsPercentage();
	public String getEmptyTurnsPercentage();
	public String getCommittedRedundancy();
	public String getTotalRedundancy();
	public int getUsedTurns(); //Return the number of Turns used, by adding all prayers in all used Turns in all days.
	
	//Web Methods
	public String getCalendarTableString(); //Should create the calendar string, then the file, the upload the file 
	public String getStatisticsString(); //Should create the statistics string, then the file, the upload the file
	public String getPrayersOnTurnString(DayOfWeek day, int turn) throws TurnException; //Like "Prayers on this turn: anonymous, Peter, John"
}
