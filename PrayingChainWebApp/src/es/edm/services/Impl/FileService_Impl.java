package es.edm.services.Impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;

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

import es.edm.services.Configuration;
import es.edm.services.FileService;

public class FileService_Impl implements FileService {
	
	Configuration conf;
	
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
}
