package es.edm.services;

import java.util.List;

import es.edm.domain.entity.PrayerEntity;
import es.edm.domain.entity.TurnEntity;

public interface IPrayerService {
	
	List<PrayerEntity> getPrayers();
	
	List<PrayerEntity> getCommittedPrayers();
	
	List<PrayerEntity> getNonCommittedPrayers();
	
	void addPrayer(PrayerEntity prayer);
	
	void removePrayer(PrayerEntity prayer);
	
	PrayerEntity getPrayerByEmail(PrayerEntity prayer);
	
	List<PrayerEntity> getPrayersByPhone(PrayerEntity prayer);
	
	List<PrayerEntity> getForeignPrayers();
	
	List<PrayerEntity> getLocalPrayers();
	
	List<PrayerEntity> getPublicPrayers();
	
	List<PrayerEntity> getHiddenPrayers();
	
	List<PrayerEntity> getPrayersOnTurn(TurnEntity turn);
	
	List<PrayerEntity> find(PrayerEntity prayer);
	
	List<PrayerEntity> getOrphanPrayers();
	
	PrayerEntity getPrayer(int prayerID);
}
