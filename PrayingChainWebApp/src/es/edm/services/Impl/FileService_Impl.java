package es.edm.services.Impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.util.List;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.Selectors;
import org.apache.commons.vfs2.impl.StandardFileSystemManager;
import org.apache.commons.vfs2.provider.sftp.SftpFileSystemConfigBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import es.edm.domain.ListOfTurns;
import es.edm.domain.Prayer;
import es.edm.domain.SimpleTurn;
import es.edm.exceptions.DDBBException;
import es.edm.exceptions.PrayerNotFoundException;
import es.edm.exceptions.TurnException;
import es.edm.services.Configuration;
import es.edm.services.DAO;
import es.edm.services.FileService;
import es.edm.util.DayOfWeek;

public class FileService_Impl implements FileService {
	
	Configuration conf;
	
	@Autowired
	DAO dao;
	
	private final static Logger logger = LoggerFactory.getLogger(FileService.class);
	
	FileService_Impl(Configuration conf){
		this.conf = conf;
	}

	@Override
    public void UploadFileFTP(String localFileName, String remoteFileName) throws SocketException, IOException {
    	
    	FTPClient ftp = new FTPClient();

       	try {
 
            ftp.connect(conf.getFtpServerName(), conf.getFtpPort());
            ftp.login(conf.getFtpUser(), conf.getFtpPwd());
            ftp.enterLocalPassiveMode();
 
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
 
            boolean done;
            try ( // Uploads file using an InputStream
                    InputStream inputStream = new FileInputStream(localFileName)) {
                if (conf.isPrintFtpUpladingMessages()) {
                	logger.info("Uploading file to " + conf.getFtpServerName());
                	logger.info("\tLocal File URI is " + localFileName);
                	logger.info("\tRemote File URI is " + remoteFileName);
                }   done = ftp.storeFile(remoteFileName, inputStream);
            }
            if (done && conf.isPrintFtpUpladingMessages()) {
            	logger.info("\tThe file was uploaded successfully.");
            }
 
        } finally {
            try {
                if (ftp.isConnected()) {
                    ftp.logout();
                    ftp.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
	public void UploadFileSFTP(String localFileName, String remoteFileName) throws FileSystemException {
    	
    	StandardFileSystemManager manager = new StandardFileSystemManager();

    	try {

    		String serverAddress = conf.getFtpServerName();
    		String userId = conf.getFtpUser();
    		String password = conf.getFtpPwd();
    		String remoteDirectory = remoteFileName;

    		//check if the file exists
    		String filepath = localFileName;
    		File file = new File(filepath);
    		if (!file.exists())
    			throw new RuntimeException("Error. Local file not found");

    		//Initializes the file manager
    		manager.init();

            logger.info("Uploading file to "+ serverAddress);
            logger.info("\tLocal File URI is " + localFileName);
            logger.info("\tRemote File URI is " + remoteFileName);

            //Setup our SFTP configuration
    		FileSystemOptions opts = new FileSystemOptions();
    		SftpFileSystemConfigBuilder.getInstance().setStrictHostKeyChecking(
    				opts, "no");
    		SftpFileSystemConfigBuilder.getInstance().setUserDirIsRoot(opts, true);
    		SftpFileSystemConfigBuilder.getInstance().setTimeout(opts, 10000);

    		//Create the SFTP URI using the host name, userid, password,  remote path and file name
    		String sftpUri = "sftp://" + userId + ":" + password +  "@" + serverAddress + "/" + 
    				remoteDirectory;

    		// Create local file object
    		FileObject localFile = manager.resolveFile(file.getAbsolutePath());

    		// Create remote file object
    		FileObject remoteFile = manager.resolveFile(sftpUri, opts);

    		// Copy local file to sftp server
    		remoteFile.copyFrom(localFile, Selectors.SELECT_SELF);
            logger.info("\tThe file was uploaded successfully.");

    	} finally {
    		manager.close();
    	}
    }

    @Override
	public void WriteFile(String stringToWrite, String fileName) throws IOException {
    	BufferedWriter bw = getBufferedWriter(fileName);
		bw.write(stringToWrite);
		bw.close();
	}
    
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
	public List<SimpleTurn> getPrayerTurns(int prayerId) throws PrayerNotFoundException {
		return dao.getPrayerTurns(prayerId);
	}

}
