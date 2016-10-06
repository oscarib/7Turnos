package es.edm.dao;

import java.util.List;

import es.edm.domain.entity.PrayerEntity;
import es.edm.domain.entity.TurnEntity;

public interface IPrayerDao {

	List<PrayerEntity> getPrayers();
	
	List<PrayerEntity> getCommittedPrayers();
	
	List<PrayerEntity> getNonCommittedPrayers();
	
	List<PrayerEntity> getPrayersWithoutEmailButWithPhone();
	
	void addPrayer(PrayerEntity prayer);
	
	void removePrayer(PrayerEntity prayer);
	
	PrayerEntity getPrayerByEmail(PrayerEntity prayer);
	
	List<PrayerEntity> getForeignPrayers();
	
	List<PrayerEntity> getLocalPrayers();
	
	List<PrayerEntity> getPrayersOnTurn(TurnEntity turn);
	
	List<PrayerEntity> find(PrayerEntity prayer);
	
	
}
