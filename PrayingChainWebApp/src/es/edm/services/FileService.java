package es.edm.services;

import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.vfs2.FileSystemException;

public interface FileService {

	public void UploadFileFTP(String localFileName, String remoteFileName) throws SocketException, IOException;
	public void UploadFileSFTP(String localFileName, String remoteFileName) throws FileSystemException;
	public void WriteFile(String stringToWrite, String fileName) throws IOException;
}
