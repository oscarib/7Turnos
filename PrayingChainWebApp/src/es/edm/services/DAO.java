package es.edm.services;

import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;

import es.edm.domain.Prayer;
import es.edm.domain.SimpleTurn;
import es.edm.exceptions.DDBBException;
import es.edm.exceptions.EmptyParameterException;
import es.edm.exceptions.PrayerException;
import es.edm.exceptions.PrayerNotFoundException;
import es.edm.exceptions.TurnException;
import es.edm.util.DayOfWeek;

public interface DAO {
	
	public void setConfiguration(Configuration conf);
	public List<Prayer> getCommittedPrayers();
	//Should return all days prayers
	public List<Prayer> getNonCommittedPrayers();
	//Should return all prayers within the provided day
	public int getPrayersWithoutEmailButWithPhone(DayOfWeek dow);
	//Should return the value of pax for the given prayer ID
	public int getPax(int prayerId);
	//Should return the new prayer ID
	public void addNewPrayer(int admin, String name, String email, String phone, boolean own_country, 
			 Date optinDate, String notes, boolean hidden, String pseudonym) throws PrayerNotFoundException;
	//Should return the new Turn ID
	public int addNewTurn(int prayer_id, DayOfWeek dow, String hour, String status, String notes, int pax);
	public void changeTurn(int uid, DayOfWeek dow, String hour, String status, String notes) throws TurnException;
	public void changePrayer(int uid, int admin,String name, String email, String phone, int ownCountry, Date date, 
							 String notes, int hidden, String pseudonym) throws PrayerNotFoundException, PrayerException;
	//Should return the UID of a given turn. It should be provided prayer id, day and hour. Status will be set to accepted always
	public int getTurnID(int prayer_id, String day, String hour);
	//Should return the prayer ID for a given email
	public int getPrayerID(String email) throws PrayerNotFoundException;
	public Prayer getPrayerByEmail(String email) throws PrayerNotFoundException;
	public Prayer getPrayerByID(int id) throws PrayerNotFoundException;
	public List<Prayer> getPrayersByName(String name) throws PrayerNotFoundException;
	public List<Prayer> getForeignPrayers() throws PrayerNotFoundException;
	public List<Prayer> getLocalPrayers() throws PrayerNotFoundException;
	public List<Prayer> getPrayersByPhone(String phone) throws PrayerNotFoundException, EmptyParameterException;
	public List<Prayer> getHiddenPrayers() throws PrayerNotFoundException;
	public List<Prayer> getPublicPrayers() throws PrayerNotFoundException;
	public void removePrayer(int uid);
	public List<Prayer> getAllPrayers() throws DDBBException;
	public List<SimpleTurn> getAllTurns() throws DDBBException;
	public List<SimpleTurn> getAllActiveTurns() throws DDBBException;
	public List<Prayer> getPrayersBetween(DateTime fromDate, DateTime toDate) throws PrayerNotFoundException; //prayers who signed up between two given dates
	public List<SimpleTurn> getOrphanTurns();
	public List<Prayer> getOrphanPrayers();
	public List<Prayer> getCancelledPrayers();
	public List<Prayer> getWhatsappCandidates(DayOfWeek dow, String phoneMask);
	public List<SimpleTurn> getPrayerTurns(int prayerId) throws PrayerNotFoundException;
	public List<Prayer> getPrayersOnTurn(DayOfWeek dow, int turn) throws TurnException;
	public List<Prayer> getPrayersByNotes(String notesMask) throws PrayerNotFoundException;
	public SimpleTurn getTurnByID(int turnID) throws TurnException;

	//TODO Retirar esto de aquí
	public String getFtpServer(int admin);
	//TODO Retirar esto de aquí
	public int getFtpPort(int admin);
}