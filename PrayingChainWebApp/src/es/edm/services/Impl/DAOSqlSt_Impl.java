package es.edm.services.Impl;

import es.edm.services.DAOSqlSt;

public class DAOSqlSt_Impl implements
		DAOSqlSt {

	@Override
	public String GetPrayers() {
		return "SELECT distinct edm_prayers.* FROM `edm_prayers` join edm_turns on edm_prayers.uid=edm_turns.prayer_id where edm_turns.status!='cancelled' AND edm_turns.status!='NotCommitted'";
	}

	@Override
	public String GetTurns() {
		return "SELECT * FROM `edm_turns`";
	}

	@Override
	public String GetActiveTurns() {
		return "SELECT * FROM `edm_turns` WHERE status!='cancelled' AND status!='NotCommitted'";
	}

	@Override
	public String GetNumberOfCommittedPrayers() {
		return "SELECT distinct edm_prayers.* FROM `edm_prayers` join edm_turns on edm_prayers.uid=edm_turns.prayer_id where edm_turns.status!='cancelled' and edm_turns.status!='NotCommitted';";
	}

	@Override
	public String getNumberOfNonCommittedPrayers() {
		return "SELECT distinct edm_prayers.* FROM `edm_prayers` join edm_turns on edm_prayers.uid=edm_turns.prayer_id where edm_turns.status!='cancelled' and edm_turns.status='NotCommitted';";
	}

	@Override
	public String getPrayersWithoutEmailButWithPhone() {
		return "select edm_prayers.name, edm_prayers.phone, edm_prayers.notes as NotasOrador, edm_prayers.hidden, edm_prayers.pseudonym, edm_turns.day, edm_turns.hour, edm_turns.notes as "
				+ "NotasTurno from edm_prayers join edm_turns on edm_prayers.uid=edm_turns.prayer_id where edm_turns.status!='Cancelled' and edm_prayers.email IS NULL and edm_prayers.phone Like "
				+ "CONCAT('%?%')";
	}

	@Override
	//Should return value of pax field for the provided prayer Id
	public String getPax() {
		return "SELECT * FROM `edm_turns` WHERE prayer_id=?";
	}

	@Override
	public String addNewPrayer() {
		return "INSERT INTO edm_prayers (admin, name, email, phone, own_country, "+
				"optin_date, notes, hidden, pseudonym) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
	}

	@Override
	public String addNewTurn() {
		return "INSERT INTO edm_turns (prayer_id, day, hour, status, notes, pax) " +
				"VALUES (?, ?, ?, ?, ?, ?);"; 
	}

	@Override
	public String removePrayer() {
		return "DELETE FROM edm_prayers WHERE uid=?;";
	}

	@Override
	public String getFtpServer() {
		return "SELECT ftpServer FROM edm_conf WHERE admin=?";
	}

	@Override
	public String getFtpPort() {
		return "SELECT ftpServer FROM edm_conf WHERE admin=?";
	}

	@Override
	public String getPrayerByEmail() {
		return "SELECT * FROM edm_prayers WHERE email=?;";
	}
	
	@Override
	public String getTurn() {
		return "SELECT * FROM edm_turns WHERE prayer_id=? AND day=? AND hour=? AND status='accepted';";
	}

	@Override
	public String getForeignPrayers() {
		return "SELECT * FROM edm_prayers WHERE own_country=0;";
	}

	@Override
	public String getLocalPrayers() {
		return "SELECT * FROM edm_prayers WHERE own_country=1;";
	}

	@Override
	public String getPrayersOnTurn() {
		return "select edm_prayers.* FROM edm_prayers JOIN edm_turns on edm_prayers.uid=edm_turns.prayer_id "
				+ "where edm_turns.day=? and edm_turns.hour=? and edm_turns.status!='cancelled' and edm_turns.status!='NotCommitted'";
	}

	@Override
	public String GetPrayersByName() {
		return "SELECT * FROM edm_prayers WHERE name Like CONCAT('%',?,'%')";
	}

	@Override
	public String getPrayersByPhone() {
		return "SELECT * FROM edm_prayers WHERE phone LIKE CONCAT('%',?,'%')";
	}

	@Override
	public String getHiddenPrayers() {
		return "SELECT * FROM edm_prayers WHERE hidden=1";
	}

	@Override
	public String getPublicPrayers() {
		return "SELECT * FROM edm_prayers WHERE hidden=0";
	}

	@Override
	public String getPrayerBetween() {
		return "SELECT * FROM `edm_prayers` WHERE optin_date>=? and optin_Date>=? ORDER BY optin_date DESC";
	}

	@Override
	public String getOrphanTurns() {
		return "SELECT edm_turns.* FROM edm_turns LEFT JOIN edm_prayers ON edm_turns.prayer_Id = edm_prayers.uid WHERE edm_prayers.uid IS NULL";
	}

	@Override
	public String getOrphanPrayers() {
		return "SELECT edm_prayers.* FROM edm_prayers LEFT JOIN edm_turns ON edm_prayers.uid = edm_turns.prayer_id WHERE edm_turns.uid IS NULL";
	}

	@Override
	public String getCancelledPrayers() {
		return "SELECT edm_prayers.*, edm_turns.status from edm_prayers JOIN edm_turns ON edm_prayers.uid = edm_turns.prayer_id WHERE edm_turns.status='cancelled'";
	}

	@Override
	public String getWhatsappCandidates() {
		return "SELECT edm_prayers.*, edm_turns.day from edm_prayers JOIN edm_turns on edm_prayers.uid = edm_turns.prayer_id WHERE email IS NULL and day=? and phone LIKE CONCAT('%',?,'%')";
	}

	@Override
	public String getPrayerTurns() {
		return "SELECT edm_turns.* from edm_turns join edm_prayers on edm_prayers.uid = edm_turns.prayer_id WHERE edm_prayers.uid=?";
	}

	@Override
	public String getPrayerByID() {
		return "SELECT * FROM edm_prayers WHERE uid=?"; 
	}

	@Override
	public String getPrayersByNotes() {
		return "SELECT * FROM edm_prayers WHERE notes LIKE CONCAT('%',?,'%')";
	}

	@Override
	public String getTurnByID() {
		return "SELECT * FROM edm_turns WHERE uid=?";
	}

	@Override
	public String changeTurn() {
		return "UPDATE edm_turns SET day=?, hour=?, status=?, Notes=? WHERE uid=?";
	}

	@Override
	public String changePrayer() {
		return "UPDATE edm_prayers SET name=?, email=?, phone=?, own_country=?, notes=?, hidden=?, pseudonym=? WHERE uid=?";
	}
}