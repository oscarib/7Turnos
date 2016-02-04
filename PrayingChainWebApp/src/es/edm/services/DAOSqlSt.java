package es.edm.services;

public interface DAOSqlSt {

	public String GetPrayers();
	public String GetPrayersByName();
	public String GetTurns();
	public String GetActiveTurns();
	public String GetNumberOfCommittedPrayers();
	public String getNumberOfNonCommittedPrayers();
	public String getPrayersWithoutEmailButWithPhone();
	public String getPax();
	public String addNewPrayer();
	public String changePrayer();
	public String addNewTurn();
	public String changeTurn();
	public String removePrayer();
	public String getFtpServer();
	public String getFtpPort();
	public String getPrayerByEmail();
	public String getTurn();
	public String getForeignPrayers();
	public String getLocalPrayers();
	public String getPrayersOnTurn();
	public String getPrayersByPhone();
	public String getHiddenPrayers();
	public String getPublicPrayers();
	public String getPrayerBetween();
	public String getOrphanTurns();
	public String getOrphanPrayers();
	public String getCancelledPrayers();
	public String getWhatsappCandidates();
	public String getPrayerTurns();
	public String getPrayerByID();
	public String getPrayersByNotes();
	public String getTurnByID();
}
