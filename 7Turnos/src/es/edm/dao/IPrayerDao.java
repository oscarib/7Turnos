package es.edm.dao;

import java.util.List;

import es.edm.domain.entity.PrayerEntity;
import es.edm.domain.entity.TurnEntity;

public interface IPrayerDao {

	List<PrayerEntity> getPrayers();
	
	void updatePrayer(PrayerEntity prayer);
	
	List<PrayerEntity> getCommittedPrayers();
	
	List<PrayerEntity> getNonCommittedPrayers();
	
	void addPrayer(PrayerEntity prayer);
	
	void removePrayer(PrayerEntity prayer);
	
	List<PrayerEntity> getPrayersByEmail(PrayerEntity prayer, boolean inlcudeErased);
	
	PrayerEntity getPrayerByID(Integer uid);
	
	List<PrayerEntity> getForeignPrayers();
	
	List<PrayerEntity> getLocalPrayers();
	
	List<PrayerEntity> getPrayersOnTurn(TurnEntity turn);
	
	List<PrayerEntity> find(PrayerEntity prayer);
	
	List<PrayerEntity> getOrphanPrayers();
	
	List<PrayerEntity> getCancelledPrayers();
	
	List<PrayerEntity> getPrayersByPhone(PrayerEntity prayer, boolean includeErased);
	
	List<PrayerEntity> getPublicPrayers();
	
	List<PrayerEntity> getHiddenPrayers();

	void updateTurn(TurnEntity turn);
}
