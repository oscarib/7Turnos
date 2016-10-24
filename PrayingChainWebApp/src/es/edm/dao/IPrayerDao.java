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
	
	PrayerEntity getPrayerByID(Integer uid);
	
	List<PrayerEntity> getForeignPrayers();
	
	List<PrayerEntity> getLocalPrayers();
	
	List<PrayerEntity> getPrayersOnTurn(TurnEntity turn);
	
	List<PrayerEntity> find(PrayerEntity prayer);
	
	List<PrayerEntity> getOrphanPrayers();
	
	List<PrayerEntity> getCancelledPrayers();
	
	List<PrayerEntity> getPrayersByPhone(PrayerEntity prayer);
}
