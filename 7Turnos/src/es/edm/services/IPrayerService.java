package es.edm.services;

import java.util.List;

import es.edm.domain.entity.PrayerEntity;
import es.edm.domain.entity.TurnEntity;
import es.edm.exceptions.PrayerNotFoundException;
import es.edm.util.DayOfWeek;
import es.edm.util.TurnsOfDay;

public interface IPrayerService {
	
	List<PrayerEntity> getPrayers();

	void addPrayer(PrayerEntity prayer);
	
	void removePrayer(PrayerEntity prayer);
	
	PrayerEntity getPrayerByEmail(PrayerEntity prayer);
	
	List<PrayerEntity> getPrayersByPhone(PrayerEntity prayer);
	
	List<PrayerEntity> getPrayersOnTurn(TurnEntity turn);
	
	List<PrayerEntity> find(PrayerEntity prayer);
	
	PrayerEntity getPrayer(int prayerID);
	
	boolean updatePrayer(PrayerEntity prayer);

	Boolean updateTurn(TurnEntity turn);
	
	public List<TurnEntity> getPrayerTurns(int prayerId) throws PrayerNotFoundException;

}
