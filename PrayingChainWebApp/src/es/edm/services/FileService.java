package es.edm.services;

import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.vfs2.FileSystemException;

import es.edm.domain.ListOfTurns;
import es.edm.exceptions.TurnException;
import es.edm.util.DayOfWeek;
import es.edm.util.TurnsOfDay;

public interface FileService {

	public void UploadFileFTP(String localFileName, String remoteFileName) throws SocketException, IOException;
	public void UploadFileSFTP(String localFileName, String remoteFileName) throws FileSystemException;
	public void WriteFile(String stringToWrite, String fileName) throws IOException;
	public String getCalendarTableString(); //Should create the calendar string, then the file, the upload the file 
	public String getStatisticsString(); //Should create the statistics string, then the file, the upload the file
	public String getPrayersOnTurnString(DayOfWeek day, TurnsOfDay turn) throws TurnException;
	public ListOfTurns[][] loadAllTurns();
	public int getEmptyTurns();
	public int getNumberOfCommittedPrayers();
}
