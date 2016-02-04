package es.edm.services.Impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import es.edm.domain.Prayer;
import es.edm.domain.SimpleTurn;
import es.edm.exceptions.DDBBException;
import es.edm.exceptions.EmptyParameterException;
import es.edm.exceptions.PrayerException;
import es.edm.exceptions.PrayerNotFoundException;
import es.edm.exceptions.TurnException;
import es.edm.services.Configuration;
import es.edm.services.DAO;
import es.edm.services.DAOSqlSt;
import es.edm.util.DayOfWeek;
import es.edm.util.TurnStatus;

public class DAO_MySql_JDBCTemplate_Impl implements DAO{
	
	HashMap<Integer, Prayer> prayers;
	HashMap<Integer, SimpleTurn> turns;
	Configuration conf;
	JdbcTemplate jdbc;
	DAOSqlSt sqlSt;
	
	public DAO_MySql_JDBCTemplate_Impl(JdbcTemplate jdbc, DAOSqlSt sqlSt){
		this.jdbc = jdbc;
		this.sqlSt = sqlSt;
	}
	
	public List<Prayer> getAllPrayers() throws DDBBException{
		return jdbc.query(sqlSt.GetPrayers(), new PrayerMapper());
	}
	
	public List<SimpleTurn> getAllTurns() throws DDBBException{
		return jdbc.query(sqlSt.GetTurns(), new SimpleTurnMapper());
	}
	
	public List<SimpleTurn> getAllActiveTurns() throws DDBBException{
		return jdbc.query(sqlSt.GetActiveTurns(), new SimpleTurnMapper());
	}
	
	@Override
	public List<Prayer> getCommittedPrayers(){
		return jdbc.query(sqlSt.GetNumberOfCommittedPrayers(), new PrayerMapper());
	}
	
	@Override
	public List<Prayer> getNonCommittedPrayers(){
		return jdbc.query(sqlSt.getNumberOfNonCommittedPrayers(), new PrayerMapper());
	}
	
	//Get the number of prayers without email, status 'NotCommitted' and phone number starting by 6 (Spanish mobile numbers)
	@Override
	public int getPrayersWithoutEmailButWithPhone(DayOfWeek dow){
		List<Prayer> prayerList = jdbc.query(sqlSt.getPrayersWithoutEmailButWithPhone(), new PrayerMapper(), dow.toString());
		return prayerList.size();
	}
	

	@Override
	public int getPax(int prayerId){
		try{
			SimpleTurn turn = jdbc.queryForObject(sqlSt.getPax(), new SimpleTurnMapper(), prayerId);
			return turn.getPax();	
		} catch (EmptyResultDataAccessException e) {
			return 0;
		}
	}
	
	//Should return the uid number of the added prayer
	@Override
	public void addNewPrayer(int admin, String name, String email, String phone, boolean own_country, 
							 Date optinDate, String notes, boolean hidden, String pseudonym) throws PrayerNotFoundException{
		//TODO: Parametrizar "admin"
		int own = 0;
		
		//This is not used at all, but maybe in the future...
		@SuppressWarnings("unused")
		int rows_affected;
		
		if (own_country) own = 1; 
		rows_affected = jdbc.update(sqlSt.addNewPrayer(), 1, name, email, phone, own, new Date(), notes, hidden, pseudonym);
	}
	
	@Override
	public int getPrayerID(String email) throws PrayerNotFoundException {
		Prayer foundPrayer;
		try{
			foundPrayer = jdbc.queryForObject(sqlSt.getPrayerByEmail(), new PrayerMapper(), email);
			return foundPrayer.getUid();
		} catch (EmptyResultDataAccessException e){
			throw new PrayerNotFoundException("It could not be found that email onto the ddbb");
		}
		
	}
	
	@Override
	public int addNewTurn(int prayer_id, DayOfWeek dow, String hour, String status, String notes, int pax){
		
		//This is not used at all, but maybe in the future...
		@SuppressWarnings("unused")
		int rows_affected;
		
		rows_affected = jdbc.update(sqlSt.addNewTurn(), prayer_id, dow.toString(), hour, status, notes, pax);
		return getTurnID(prayer_id, dow.toString(), hour);
	}

	@Override
	public int getTurnID(int prayer_id, String day, String hour){
		SimpleTurn foundTurn;
		try{
			foundTurn = jdbc.queryForObject(sqlSt.getTurn(), new SimpleTurnMapper(), prayer_id, day, hour);
			return foundTurn.getUid();
		} catch (EmptyResultDataAccessException e){
			throw new RuntimeException("Something went really wrong, since it should have returned a prayer_id");
		}
	}
	
	@Override
	public void removePrayer(int uid){
		int rows_affected;
		rows_affected = jdbc.update(sqlSt.removePrayer(), uid);
		if (rows_affected != 1) {
			throw new RuntimeException("Something went wrong, as it should have returned just 1 rows deleted");
		}
	}
	
	@Override
	//TODO Retirar esto de aquí
	public String getFtpServer(int admin){
		String ftpServer;
		ftpServer = jdbc.queryForObject(sqlSt.getFtpServer(), new SimpleStringMapper(), admin);
		return ftpServer;
	}

	@Override
	//TODO Retirar esto de aquí
	public int getFtpPort(int admin){
		int ftpPort;
		ftpPort = jdbc.queryForObject(sqlSt.getFtpPort(), new SimpleIntegerMapper(), admin);
		return ftpPort;
	}

	@Override
	public Prayer getPrayerByEmail(String email) throws PrayerNotFoundException {
		try{
			return jdbc.queryForObject(sqlSt.getPrayerByEmail(), new PrayerMapper(), email);
		} catch (EmptyResultDataAccessException e){
			throw new PrayerNotFoundException("That email was not found into the ddbb");
		} catch (IncorrectResultSizeDataAccessException e){
			throw new PrayerNotFoundException("That email was found for several prayers. Please, correct");
		}
	}

	@Override
	public List<Prayer> getPrayersByName(String name) throws PrayerNotFoundException {
		List<Prayer> prayers;
		prayers = jdbc.query(sqlSt.GetPrayersByName(), new PrayerMapper(), name);
		if (prayers.size()==0) throw new PrayerNotFoundException("No prayer found with such name");
		return prayers;
	}

	@Override
	public List<Prayer> getForeignPrayers() throws PrayerNotFoundException {
		List<Prayer> prayers = jdbc.query(sqlSt.getForeignPrayers(), new PrayerMapper());
		if (prayers.size()==0) throw new PrayerNotFoundException("No prayer found at all");
		return prayers;
	}

	@Override
	public List<Prayer> getLocalPrayers() throws PrayerNotFoundException {
		List<Prayer> prayers = jdbc.query(sqlSt.getLocalPrayers(), new PrayerMapper());
		if (prayers.size()==0) throw new PrayerNotFoundException("No prayer found at all");
		return prayers;
	}

	@Override
	public List<Prayer> getPrayersByPhone(String phone) throws PrayerNotFoundException, EmptyParameterException {
		if (!phone.trim().equals("")){
		List<Prayer> prayers = jdbc.query(sqlSt.getPrayersByPhone(), new PrayerMapper(), phone);
		if (prayers.size()==0) throw new PrayerNotFoundException("No prayer found at all");
		return prayers;
		} else {
			throw new EmptyParameterException("Phone cannot be an empty string");
		}
	}

	@Override
	public List<Prayer> getHiddenPrayers() throws PrayerNotFoundException {
		List<Prayer> prayers = jdbc.query(sqlSt.getHiddenPrayers(), new PrayerMapper());
		if (prayers.size()==0) throw new PrayerNotFoundException("No prayer found at all");
		return prayers;
	}

	@Override
	public List<Prayer> getPublicPrayers() throws PrayerNotFoundException {
		List<Prayer> prayers = jdbc.query(sqlSt.getPublicPrayers(), new PrayerMapper());
		if (prayers.size()==0) throw new PrayerNotFoundException("No prayer found at all");
		return prayers;
	}

	@Override
	//prayers who signed up between two given dates
	public List<Prayer> getPrayersBetween(DateTime fromDate, DateTime toDate) throws PrayerNotFoundException{
		List<Prayer> prayers = jdbc.query(sqlSt.getPrayerBetween(), new PrayerMapper(), fromDate, toDate);
		if (prayers.size()==0) throw new PrayerNotFoundException("No prayer found at all");
		return prayers;
	}

	@Override
	public List<SimpleTurn> getOrphanTurns() {
		List<SimpleTurn> turns = jdbc.query(sqlSt.getOrphanTurns(), new SimpleTurnMapper());
		return turns;
	}

	@Override
	public List<Prayer> getOrphanPrayers() {
		List<Prayer> prayers = jdbc.query(sqlSt.getOrphanPrayers(), new PrayerMapper());
		return prayers;
	}

	@Override
	public List<Prayer> getCancelledPrayers() {
		List<Prayer> prayers = jdbc.query(sqlSt.getCancelledPrayers(), new PrayerMapper());
		return prayers;
	}

	@Override
	public List<Prayer> getWhatsappCandidates(DayOfWeek dow, String phoneMask) {
		List<Prayer> prayers = jdbc.query(sqlSt.getWhatsappCandidates(), new PrayerMapper(), dow.name(), phoneMask);
		return prayers;
	}

	@Override
	public List<SimpleTurn> getPrayerTurns(int prayerId) throws PrayerNotFoundException {
		List<SimpleTurn> turns = jdbc.query(sqlSt.getPrayerTurns(), new SimpleTurnMapper(), prayerId);
		return turns;
	}

	@Override
	public List<Prayer> getPrayersOnTurn(DayOfWeek dow, int turn) throws TurnException {
		List<Prayer> prayers = jdbc.query(sqlSt.getPrayersOnTurn(), new PrayerMapper(), dow.name(), SimpleTurn.getHourByTurn(turn));
		return prayers;
	}

	@Override
	public void setConfiguration(Configuration conf) {
		this.conf = conf;
	}

	@Override
	public Prayer getPrayerByID(int prayer_id) throws PrayerNotFoundException {
		try{
		return jdbc.queryForObject(sqlSt.getPrayerByID(), new PrayerMapper(), prayer_id);
		} catch (EmptyResultDataAccessException e){
			throw new PrayerNotFoundException("That ID was not found into the ddbb");
		}

	}

	@Override
	public List<Prayer> getPrayersByNotes(String notesMask) throws PrayerNotFoundException {
		return jdbc.query(sqlSt.getPrayersByNotes(), new PrayerMapper(), notesMask);
	}

	@Override
	public SimpleTurn getTurnByID(int turnID) throws TurnException {
		try{
			return jdbc.queryForObject(sqlSt.getTurnByID(), new SimpleTurnMapper(), turnID);
		} catch (EmptyResultDataAccessException e){
			throw new TurnException("That ID was not found into the ddbb");
		}
	}

	@Override
	public void changeTurn(int uid, DayOfWeek dow, String hour, String status, String notes) throws TurnException {
		
		//This is not used at all, but maybe in the future...
		int rows_affected;
		
		rows_affected = jdbc.update(sqlSt.changeTurn(), dow.toString(), hour, status, notes, uid);
		
		if (rows_affected>1){
			throw new TurnException("WARNING!!!!!! There were a fatal error while trying to update a SINGLE turn, since there were " + rows_affected + " rows affected");
		}
	}

	@Override
	public void changePrayer(int uid, int admin, String name, String email, String phone, int ownCountry, Date date,
			String notes, int hidden, String pseudonym) throws PrayerNotFoundException, PrayerException {

		//This is not used at all, but maybe in the future...
		int rows_affected;
		
		rows_affected = jdbc.update(sqlSt.changePrayer(), name, email, phone, ownCountry, notes, hidden, pseudonym, uid);
		
		if (rows_affected>1){
			throw new PrayerException("WARNING!!!!!! There were a fatal error while trying to update a SINGLE prayer, since there were " + rows_affected + " rows affected");
		}
		
		if (rows_affected==0){
			throw new PrayerNotFoundException("WARNING!!!!!! There were a fatal error while trying to update a prayer, "
											+ "which was not found by ID " + uid);
		}
	}
}

class PrayerMapper implements RowMapper<Prayer>{

	@Override
	public Prayer mapRow(ResultSet rs, int number) throws SQLException {
		Prayer prayer;
		int uid = rs.getInt("uid");
		String name = rs.getString("name");
		String email = rs.getString("email");
		String phone = rs.getString("phone");
		boolean ownCountry = rs.getBoolean("own_country");
		Date optinDate = rs.getDate("optin_date");
		String notes = rs.getString("notes");
		boolean hidden = rs.getBoolean("hidden");
		String pseudonym = rs.getString("pseudonym");
		prayer = new Prayer(uid, name, email, phone, ownCountry, 
							optinDate, notes, hidden, pseudonym);
		return prayer;
	}	
}

class SimpleTurnMapper implements RowMapper<SimpleTurn>{

	@Override
	public SimpleTurn mapRow(ResultSet rs, int number) throws SQLException {
		SimpleTurn turn;
		int uid = rs.getInt("uid");
		int prayer_id = rs.getInt("prayer_id");
		String day = rs.getString("day");
		String hour = rs.getString("hour");
		String status = rs.getString("status");
		String notes = rs.getString("notes");
		int pax = rs.getInt("pax");
		try {
			turn = new SimpleTurn(uid, prayer_id, DayOfWeek.valueOf(day), SimpleTurn.getTurnByHour(hour), TurnStatus.valueOf(status), notes, pax);
		} catch (TurnException e) {
			throw new RuntimeException(e.toString());
		}
		return turn;
	}	
}

//Mapps a single string, for what the SQL statement should give a single result (Select * <-- this is forbidden)
class SimpleStringMapper implements RowMapper<String>{

	@Override
	public String mapRow(ResultSet rs, int number) throws SQLException {
		return rs.getString(1);
	}
	
}

//Mapps a single string, for what the SQL statement should give a single result (Select * <-- this is forbidden)
class SimpleIntegerMapper implements RowMapper<Integer>{

	@Override
	public Integer mapRow(ResultSet rs, int number) throws SQLException {
		return rs.getInt(1);
	}
	
}
