package es.edm.services.Impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;

import es.edm.domain.ListOfTurns;
import es.edm.domain.Prayer;
import es.edm.domain.SimpleTurn;
import es.edm.exceptions.DDBBException;
import es.edm.exceptions.DataTimeException;
import es.edm.exceptions.EmptyParameterException;
import es.edm.exceptions.MoreThanOnePrayerException;
import es.edm.exceptions.PrayerAlreadyExistsException;
import es.edm.exceptions.PrayerException;
import es.edm.exceptions.PrayerHasTurnsException;
import es.edm.exceptions.PrayerNotFoundException;
import es.edm.exceptions.TurnAlreadyExistsException;
import es.edm.exceptions.TurnException;
import es.edm.services.Configuration;
import es.edm.services.DAO;
import es.edm.services.MainService;
import es.edm.util.DayOfWeek;

public class MainService_Impl implements MainService {
	
	private DAO dao;
	private Configuration conf;
	
	MainService_Impl(DAO dao, Configuration conf) throws DDBBException{
		this.dao = dao;
		dao.setConfiguration(conf);
		this.conf = conf;
	}

	@Override
	public void addNewPrayer(Prayer prayer) throws PrayerAlreadyExistsException {
		
		Prayer foundPrayer;
		//If email already exists, then error
		try {
			foundPrayer = getPrayerByEmail(prayer.getEmail());
			throw new PrayerAlreadyExistsException("Email '" + prayer.getEmail() + "' already exists in the ddbb"); 
		} catch (PrayerNotFoundException e) {

			//If Phone already exists, then error...
			try{
				foundPrayer = getPrayerByPhone(prayer.getPhone());
				throw new PrayerAlreadyExistsException("Phone '" + prayer.getPhone() + "' already exists in the ddbb"); 
				
			//Neither email or phone was found, so keep going with saving
			} catch (PrayerNotFoundException ex) {
				try {
					//TODO: Parametrizar admin
					//Add the prayer to the ddbb
					dao.addNewPrayer(1, prayer.getName(), prayer.getEmail(), prayer.getPhone(), prayer.isOwnCountry(), 
							prayer.getOptinDate(), prayer.getNotes(), prayer.isHidden(), prayer.getPseudonym());

				} catch (PrayerNotFoundException e1) {
					throw new RuntimeException("Something went really really wrong!!");
				}
			}
		}
	}

	@Override
	public void addTurn(int prayerId, SimpleTurn turn) throws PrayerNotFoundException, TurnAlreadyExistsException, TurnException {
		
		Prayer foundPrayer = getPrayerByID(prayerId);

		//If turn is already set for that prayer, then error
		if (isTurnPrayedByPrayer(foundPrayer.getUid(), turn.getDow(), turn.getTurn())){
			throw new TurnAlreadyExistsException("This prayer already prays that turn");
		}
		
		dao.addNewTurn(prayerId, turn.getDow(), SimpleTurn.getHourByTurn(turn.getTurn()), "accepted", turn.getNotes(), turn.getPax());
	}

	@Override
	public void removePrayer(int prayerId) throws PrayerNotFoundException, PrayerHasTurnsException {
		
		//If prayer doens't exists, then error
		if (getPrayerByID(prayerId)==null){
			throw new PrayerNotFoundException("That prayer uid (" + prayerId + " is not in the ddbb");
		}
		
		//To see if that prayer has turns
		List<SimpleTurn> prayerTurns = getPrayerTurns(prayerId);
		
		//If so, then it cannot be deleted, so we launch an error
		if (prayerTurns==null | prayerTurns.size()>0){
			throw new PrayerHasTurnsException("That prayer cannot be removed, since there are already " + prayerTurns.size() + " turns for him on the ddbb");
		}

		dao.removePrayer(prayerId);
	}

	@Override
	public List<Prayer> getAllPrayers() throws DDBBException {
		return dao.getAllPrayers();
	}

	@Override
	public List<SimpleTurn> getAllActiveTurns() throws DDBBException {
		return dao.getAllActiveTurns();
	}

	@Override
	public List<SimpleTurn> getAllTurns() throws DDBBException {
		return dao.getAllTurns();
	}

	@Override
	public Prayer getPrayerByEmail(String email) throws PrayerNotFoundException {
		if ("".equals(email)){
			throw new PrayerNotFoundException("Can't search by email ''");
		}
		return dao.getPrayerByEmail(email);
	}

	@Override
	public List<Prayer> getPrayersByName(String name) throws PrayerNotFoundException {
		return dao.getPrayersByName(name);
	}

	@Override
	public Prayer getPrayerByPhone(String phone) throws MoreThanOnePrayerException, PrayerNotFoundException, EmptyParameterException {
		if ("".equals(phone) || phone == null){
			throw new PrayerNotFoundException("Can't search for an empty phone string");
		}
		List<Prayer> foundPrayers = dao.getPrayersByPhone(phone);
		if (foundPrayers.size()>1) {
			throw new MoreThanOnePrayerException("That phone '" + phone + "' was found more than 1 time into the ddbb");
		} else if(foundPrayers.size()<1)  {
			throw new PrayerNotFoundException("No prayer found by phone '" + phone + "'");
		}
		return foundPrayers.get(0);
	}

	@Override
	//Returns a list of prayers that are set as own_country=0
	public List<Prayer> getForeignPrayers() throws PrayerNotFoundException {
		try {
			return dao.getForeignPrayers();
		} catch (PrayerNotFoundException e){
			return new ArrayList<Prayer>();
		}
	}

	@Override
	public List<Prayer> getLocalPrayers() throws PrayerNotFoundException {
		try {
			return dao.getLocalPrayers();
		} catch (PrayerNotFoundException e){
			return new ArrayList<Prayer>();
		}
	}

	@Override
	public List<Prayer> getHiddenPrayers(){
		try {
			return dao.getHiddenPrayers();
		} catch (PrayerNotFoundException e){
			return new ArrayList<Prayer>();
		}
	}

	@Override
	public List<Prayer> getPublicPrayers() throws PrayerNotFoundException {
		try {
			return dao.getPublicPrayers();
		} catch (PrayerNotFoundException e){
			return new ArrayList<Prayer>();
		}
	}

	@Override
	public List<Prayer> getPrayersBetween(DateTime fromDate, DateTime toDate) throws DataTimeException, PrayerNotFoundException {
		//If toDate is before fromDate, then error
		if (toDate.isBefore(fromDate.toInstant())){
			throw new DataTimeException(toDate.toString() + " is before " + fromDate.toString());
		}
		
		return dao.getPrayersBetween(fromDate, toDate);
	}

	@Override
	public List<SimpleTurn> getOrphanTurns() {
		return dao.getOrphanTurns();
	}

	@Override
	public List<Prayer> getOrphanPrayers() {
		return dao.getOrphanPrayers();
	}

	@Override
	//Returns all prayers with cancelled Turns (which doens't mean that this prayer has not active Turns!)
	public List<Prayer> getCancelledPrayers() {
		return dao.getCancelledPrayers();
	}

	@Override
	public List<Prayer> getWhatssappCandidates(DayOfWeek dow, String phoneMask) {
		return dao.getWhatsappCandidates(dow, phoneMask);
	}

	@Override
	public List<Prayer> getCommittedPrayers() {
		return dao.getCommittedPrayers();
	}

	@Override
	public List<Prayer> getNonCommittedPrayers() {
		return dao.getNonCommittedPrayers();
	}
	
	@Override
	//We need to count the max number of pax on all turns prayed by the given prayer
	//If we only sum all paxes in all turns for the given prayer, then If a prayer is praying
	//3 turns, it will be counted 3 committed Prayers, when in fact there is just one
	public int getNumberOfCommittedPrayers() {
		int noCommittedPrayers = 0;
		//get all committedPrayers
		List<Prayer> committedPrayers = dao.getCommittedPrayers();
		
		//Loop them
		for (Prayer nextPrayer : committedPrayers){
			int maxPax = 0;

			try {

				//Get all turns prayed by nextPrayer
				List<SimpleTurn> turns = getPrayerTurns(nextPrayer.getUid());
				
				//Get the maximum number for pax in all turns
				for (SimpleTurn nextTurn : turns){
					if (nextTurn.getPax() > maxPax) maxPax = nextTurn.getPax();
				}
			} catch (PrayerNotFoundException e) {
			}
			
			noCommittedPrayers += maxPax;
		}
		
		return noCommittedPrayers;
	}

	@Override
	public int getNumberOfNonCommittedPrayers() {
		List<Prayer> nonCommittedPrayers = dao.getNonCommittedPrayers();
		return nonCommittedPrayers.size();
	}    	

	@Override
	public List<SimpleTurn> getPrayerTurns(int prayerId) throws PrayerNotFoundException {
		return dao.getPrayerTurns(prayerId);
	}

	@Override
	public int getEmptyTurns() {
		ListOfTurns[][] turns = loadAllTurns();
		int emptyTurns = 0;
		for (int day = 0; day < 7; day++){
			for (int turn=0; turn<48; turn++){
				if (turns[day][turn] == null) emptyTurns++;
			}
		}
		return emptyTurns;
	}

	@Override
	public int getFreeTurns() {
		return getTotalTurns() - getUsedTurns();
	}

	@Override
	public int getTotalTurns() {
		return conf.getPrayersPerTurn() * 48 * 7;
	}

	@Override
	public String getTurnsUsedPercentage() {
		double percentage;
		percentage = (double)getUsedTurns() / (double)getTotalTurns() * 100;
		String percentText = new DecimalFormat("#.##").format(percentage);
		return percentText;
	}

	@Override
	public String getFreeTurnsPercentage() {
		double percentage;
		percentage = (double)getFreeTurns() / (double)getTotalTurns() * 100;
		String percentText = new DecimalFormat("#.##").format(percentage);
		return percentText;
	}

	@Override
	public String getEmptyTurnsPercentage() {
		double percentage;
		percentage = (double)getEmptyTurns() / (double)getTotalTurns() * conf.getPrayersPerTurn() * 100;
		String percentText = new DecimalFormat("#.##").format(percentage);
		return percentText;
	}
	
	@Override
	public String getCommittedRedundancy(){
		double percentage;
		percentage = (double)getNumberOfCommittedPrayers() / (double)getUsedTurns() * 100;
		String percentText = new DecimalFormat("#").format(percentage);
		return percentText;
	}

	@Override
	public String getTotalRedundancy(){
		double percentage;
		percentage = (double)(getNumberOfCommittedPrayers()+getNumberOfNonCommittedPrayers()) / (double)getUsedTurns() * 100;
		String percentText = new DecimalFormat("#").format(percentage);
		return percentText;
	}

	@Override
	public int getUsedTurns() {
		ListOfTurns[][] turns = loadAllTurns();
		int usedTurns = 0;
		for (int day = 0; day < 7; day++){
			for (int turn=0; turn<48; turn++){
				if (turns[day][turn] != null){
					if (turns[day][turn].size() >= conf.getPrayersPerTurn()){
						usedTurns += conf.getPrayersPerTurn();
					} else {
						usedTurns += turns[day][turn].size();
					}
				}
			}
		}
		return usedTurns;
	}

	@Override
	public String getCalendarTableString() {
		
		ListOfTurns[][] turns = loadAllTurns();
		
		StringBuilder html = new StringBuilder();

		html.append("<table class='Planning'>");
		html.append("\n");
		html.append("\t<tr>");
		html.append("\n");
		html.append("\t\t<th></th>");
		html.append("\n");
		html.append("\t\t<th>L</th>");
		html.append("\n");
		html.append("\t\t<th>M</th>");
		html.append("\n");
		html.append("\t\t<th>X</th>");
		html.append("\n");
		html.append("\t\t<th>J</th>");
		html.append("\n");
		html.append("\t\t<th>V</th>");
		html.append("\n");
		html.append("\t\t<th>S</th>");
		html.append("\n");
		html.append("\t\t<th>D</th>");
		html.append("\n");
		html.append("\t</tr>");
		html.append("\n");

		//loop through the turns on the day
		for (int turn=0; turn<48; turn++){
			html.append("\t<tr>");
			html.append("\n");

			try{

				//Loop through the days on the week
				for (int day=0; day<7; day++){

					//Cell for monday
					int prayersPerTurn = conf.getPrayersPerTurn();
					if (day==0) {
						html.append("\t\t<td>");
						html.append("\n");
						html.append("\t\t\t"+SimpleTurn.getHourByTurn(turn));
						html.append("\n");
						html.append("\t\t</td>");
						html.append("\n");
					}
					if (turns[day][turn]!=null){
						String prayersString = getPrayersOnTurnString(DayOfWeek.values()[day], turn);
						int nOfPrayers = turns[day][turn].size();
						int freeTurns = prayersPerTurn-nOfPrayers;
						if (freeTurns==0) {
							html.append("\t\t<td title='");
							html.append(""+prayersString);
							html.append("' class='unavailableTurn'>");
							html.append("\n");
						} else {
							if (freeTurns<0) {
								html.append("\t\t<td title='");
								html.append(""+prayersString);
								html.append("' class='saturedTurn'>");
								html.append("\n");
								if (conf.isMySqlWarningMessagesActivated()){
									System.out.println("\t\tWarning: Satured turn! There are " + (-1*freeTurns) + " extra Prayers on " + DayOfWeek.values()[day] + ", "+ SimpleTurn.getHourByTurn(turn));
									if (conf.isDetailedInfoActivatedForSaturedTurns()){
										System.out.println("\t\tPrayers on the turn:");
										System.out.println("");
										System.out.println(prayersString);
										System.out.println("");
									}
								}
								freeTurns=0;
							} else {
								html.append("\t\t<td title='");
								html.append(""+prayersString);
								html.append("' class='availableTurn'>");
								html.append("\n");
							}
						}
					} else {
						html.append("\t\t<td title='");
						html.append("' class='freeTurn'>");
						html.append("\n");
					}
					html.append("\t\t</td>");				
					html.append("\n");
				}	

			} catch (TurnException e){
				throw new RuntimeException(e);
			} 

			html.append("\t</tr>");
			html.append("\n");
		}
		html.append("</table>");
		html.append("\n");
		return html.toString();
	}

	@Override
	public String getStatisticsString() {
		StringBuilder html = new StringBuilder();
		html.append("[thrive_number_counter color='blue' value='");
		int committed = getNumberOfCommittedPrayers();
		html.append(Integer.toString(committed));
		html.append("' before='Ya somos ' after='' label='']");
		html.append("[thrive_number_counter color='blue' value='");
		int empty = getEmptyTurns();
		html.append(Integer.toString(empty));
		html.append("' before='Quedan: ' after='turnos vacíos' label='']");
		return html.toString();
	}

	@Override
	//Like "Prayers on this turn: anonymous, Peter, John"
	public String getPrayersOnTurnString(DayOfWeek day, int turn) throws TurnException {
		//Get the prayers from the ddbb
		List<Prayer> prayers = dao.getPrayersOnTurn(day, turn);
		StringBuilder prayersString = new StringBuilder();
		for (Prayer nextPrayer : prayers){
			String pseudonym;
			if (nextPrayer.getPseudonym()==null){
				pseudonym = "anónimo";
			} else {
				if (nextPrayer.getPseudonym().equals("")){
					pseudonym = "anónimo";
				} else {
					pseudonym = nextPrayer.getPseudonym();
				}
			}
			prayersString.append(pseudonym + ", ");
		}
		return prayersString.toString();
	}

	@Override
	public boolean isTurnPrayedByPrayer(int prayerID, DayOfWeek dow, int turn) throws TurnException {
		List<Prayer> prayersOnTurn = dao.getPrayersOnTurn(dow, turn);
		for (Prayer nextPrayer : prayersOnTurn){
			if (nextPrayer.getUid()==prayerID) return true;
		}
		return false;
	}

	@Override
	public Prayer getPrayerByID(int id) throws PrayerNotFoundException {
		return dao.getPrayerByID(id);
	}

	@Override
	public List<Prayer> getPrayersByPhone(String phone) throws PrayerNotFoundException, EmptyParameterException {
		return dao.getPrayersByPhone(phone);
	}
	
	//PRIVATE METHODS //PRIVATE METHODS //PRIVATE METHODS
	
	@SuppressWarnings("unused")
	private BufferedWriter getBufferedWriter(String fileName){
		
		File file = new File(fileName); 
		BufferedWriter bw;

		if (file.exists()) {
			file.delete();
		}

		try {
			bw = new BufferedWriter(new FileWriter(fileName));
			return bw;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}
	
	//Load all turns in a simple array of 7 days, 48 turns per day. Each turn in each position (each position is itself an Array of SimpleTurns)
	public ListOfTurns[][] loadAllTurns(){
		
		//Create the empty array
		ListOfTurns[][] listOfTurns = new ListOfTurns[7][48];
		
		//Load all turns from ddbb
		try {
			List<SimpleTurn> ddbbTurns = dao.getAllActiveTurns();

			//Loop through all turns in the ddbb
			for (SimpleTurn nextTurn : ddbbTurns){
				
				//load Day of Week as an integer
				int day = nextTurn.getDow().ordinal();
				
				//Load the turn
				int turn = nextTurn.getTurn();
				
				//If the grid List is empty, create a new one
				if (listOfTurns[day][turn] == null) listOfTurns[day][turn] = new ListOfTurns();

				//Add the turn to its position
				listOfTurns[day][turn].add(nextTurn);
			}
			
			return listOfTurns;
		} catch (DDBBException e) {
			throw new RuntimeException("There was a problem connecting with the ddbb: " + e.toString());
		}
		
	}

	@Override
	public List<Prayer> andMixingOfLists(List<Prayer> list1, List<Prayer> list2) throws EmptyParameterException {
		if (list1!=null){
			if(list2!=null){
				List<Prayer> mixedList = new ArrayList<Prayer>();
				for (Prayer nextPrayer : list1){
					if (list2.contains(nextPrayer)){
						mixedList.add(nextPrayer);
					}
				}
				return mixedList;
			} else {
				return list1;
			}
		} else {
			if (list2!=null) {
				return list2;
			} else {
				throw new EmptyParameterException("Both lists are null");
			}
		}
	}

	@Override
	public List<Prayer> orMixingOfLists(List<Prayer> list1, List<Prayer> list2) throws EmptyParameterException{
		if (list1!=null){
			if (list2!=null){
				List<Prayer> mixedList = new ArrayList<Prayer>();
				mixedList.addAll(list1);
				for (Prayer nextPrayer : list2){
					if (!mixedList.contains(nextPrayer)){
						mixedList.add(nextPrayer);
					}
				}
				return mixedList;
			} else {
				return list1;
			}
		} else {
			if (list2!=null){
				return list2;
			} else {
				throw new EmptyParameterException("Both lists are null");
			}
		}
	}

	@Override
	public List<Prayer> getPrayersByNotes(String notesMask) throws PrayerNotFoundException {
		return dao.getPrayersByNotes(notesMask);
	}

	@Override
	public SimpleTurn getTurnByID(int turnID) throws TurnException {
		return dao.getTurnByID(turnID);
	}

	@Override
	public void changeTurn(SimpleTurn turn) throws TurnException {
		dao.changeTurn(turn.getUid(), turn.getDow(), SimpleTurn.getHourByTurn(turn.getTurn()), turn.getStatus().toString(), turn.getNotes());
	}

	@Override
	public void changePrayer(Prayer prayer) throws PrayerNotFoundException, PrayerException {
		int uid = prayer.getUid();
		String name = prayer.getName();
		String email = prayer.getEmail();
		String phone = prayer.getPhone();
		int ownCountry = prayer.isOwnCountry() ? 1 : 0;
		Date optinDate = new Date();
		String notes = prayer.getNotes();
		int hidden = prayer.isHidden() ? 1 : 0;
		String pseudonym = prayer.getPseudonym();
		dao.changePrayer(uid, 1, name, email, phone, ownCountry, optinDate, notes, hidden, pseudonym);
	}
}
