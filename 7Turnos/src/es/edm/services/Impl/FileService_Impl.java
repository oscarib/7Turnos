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
import es.edm.domain.SimpleTurn;
import es.edm.domain.entity.PrayerEntity;
import es.edm.domain.entity.TurnEntity;
import es.edm.exceptions.TurnException;
import es.edm.services.Configuration;
import es.edm.services.FileService;
import es.edm.services.IPrayerService;
import es.edm.services.ITurnService;
import es.edm.util.DayOfWeek;
import es.edm.util.TurnsOfDay;

public class FileService_Impl implements FileService {
	
	Configuration conf;
	
	@Autowired
	IPrayerService prayerService;
	
	@Autowired
	ITurnService turnService;
	
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
                done = ftp.storeFile(remoteFileName, inputStream);
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
		int committed = prayerService.getCommittedPrayers().size();
		html.append(Integer.toString(committed));
		html.append("' before='Ya somos ' after='' label='']");
		html.append("[thrive_number_counter color='blue' value='");
		int empty = turnService.getEmptyTurns();
		html.append(Integer.toString(empty));
		html.append("' before='Quedan: ' after='turnos vacíos' label='']");
		return html.toString();
	}
	
	@Override
	public String getCalendarTableString(int times) {
		
		ListOfTurns[][] turns = turnService.loadAllTurns();
		
		StringBuilder html = new StringBuilder();
		
		for (int times2Break = 0 ; times2Break<times ; times2Break++){
			html.append("<div class='col-lg-3 col-md-3 col-sm-6 col-xs-12'>");
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
			for (int turn=times2Break*(48/times); turn<(times2Break*(48/times))+(48/times); turn++){
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
						String prayersString = getPrayersOnTurnString(DayOfWeek.values()[day], TurnsOfDay.values()[turn]);
						if (turns[day][turn]!=null && !"".equals(prayersString)){
							int nOfPrayers = turns[day][turn].size();
							int freeTurns = prayersPerTurn-nOfPrayers;
							if (freeTurns==0) {
								html.append("\t\t<td ");
								html.append(" class='unavailableTurn'>");
								html.append("<span class='toolTip'><span class='toolTipText'>"+ prayersString + "</span></span>");
								html.append("\n");
							} else {
								if (freeTurns<0) {
									html.append("\t\t<td ");
									html.append(" class='saturedTurn'>");
									html.append("<span class='toolTip'><span class='toolTipText'>"+ prayersString + "</span></span>");
									html.append("\n");
									freeTurns=0;
								} else {
									html.append("\t\t<td ");
									html.append(" class='availableTurn'>");
									html.append("<span class='toolTip'><span class='toolTipText'>"+ prayersString + "</span></span>");
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
			html.append("</div>");
		}
		return html.toString();
	}
	
	@Override
	//Like "Prayers on this turn: anonymous, Peter, John"
	public String getPrayersOnTurnString(DayOfWeek day, TurnsOfDay turn) throws TurnException {
		//Get the prayers from the ddbb
		TurnEntity turn2Search = new TurnEntity();
		turn2Search.setDow(day);
		turn2Search.setTurn(turn.toString());
		List<PrayerEntity> prayers = prayerService.getPrayersOnTurn(turn2Search);
		StringBuilder prayersString = new StringBuilder();
		for (PrayerEntity nextPrayer : prayers){
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

}
