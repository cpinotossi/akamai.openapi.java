package cpinotos.openapi.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.Iterator;

import javax.xml.bind.DatatypeConverter;

import com.akamai.netstorage.NetStorage;
import com.akamai.netstorage.NetStorageException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import cpinotos.openapi.OpenAPI;
import cpinotos.openapi.netstorage.NetStorageDirResult;
import cpinotos.openapi.netstorage.NetStorageDirResultFile;
import cpinotos.openapi.netstorage.NetStorageDirResultStat;
import lombok.Getter;
import lombok.Setter;


public class NetStorageAPI {
@Getter @Setter private OpenAPI openAPI;
@Getter @Setter private NetStorage netStorage;


public static void main(String[] args) throws UnknownHostException, UnsupportedEncodingException{

	
}
public NetStorageAPI(OpenAPI openAPI){
	this.openAPI=openAPI;
	this.setNetStorage(new NetStorage(this.getOpenAPI().getNetstorageCredential()));
}
public boolean doNetstorageMkdir(String path) {
	boolean isCreated = false;
	try {
		isCreated = this.getNetStorage().mkdir(path);
	} catch (NetStorageException e) {
		e.printStackTrace();
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return isCreated;
}

public boolean doNetstorageDelete(String path) {
	boolean isDeleted = false;		
	try {
		isDeleted = this.getNetStorage().quickDelete(path);
	} catch (NetStorageException e) {
		//PRECONDITION FAILED (The Akamai Edge Server that received the API call thinks that the call included a 'Precondition' specification which the server detected was not met. Check the format of the call and try again.)
		System.out.println("deleteFile(" + path + ") failed");
		openAPI.logger.debug("doNetstorageDelete failed: " +path);
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return isDeleted;
}
public NetStorageDirResultStat doNetstorageDir(String dir) {
	String netStorageDirResultJson = null;
	NetStorageDirResult netStorageDirResult = null;
	String responseString = null;
	try {
		InputStream responseStream = this.getNetStorage().dir(dir);
		responseString = OpenAPI.inputStreamReader(responseStream);
		netStorageDirResultJson = OpenAPI.xml2json(responseString);
		netStorageDirResultJson = netStorageDirResultJson.replaceAll("[\\t\\n\\r\\s]","");
		netStorageDirResultJson = netStorageDirResultJson.replaceFirst("\"file\":\\{","\"file\":\\[\\{");
		netStorageDirResultJson = netStorageDirResultJson.replaceFirst("\\},\"directory\":","\\}\\],\"directory\":");
		Gson gson = new Gson();
		netStorageDirResult = gson.fromJson(netStorageDirResultJson, NetStorageDirResult.class);
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} catch (NetStorageException e) {
		// TODO Auto-generated catch block
		openAPI.logger.info("doNetstorageDir input is not a directory, please add the cpcode folder: " +dir);
		e.printStackTrace();
	}
	// ns.delete("/1234/example.zip");
	catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return netStorageDirResult.getStat();
}


public NetStorageDirResultStat doNetstorageDir(String path, boolean isRekursive) {
	NetStorageDirResultFile currentFile = null;
	NetStorageDirResultStat currentStat = null;
	NetStorageDirResultStat nextStat = null;
	String currentDir = null;
	String nextDir = null;
	    currentStat = doNetstorageDir(path);
		currentDir = currentStat.getDirectory();
					
	if(isRekursive){
		openAPI.logger.debug("doNetstorageDir recursive lookup currentDir: " +currentDir);

			//look for directories inside the Stats file list
			Iterator<NetStorageDirResultFile> i = currentStat.getFile().iterator();
			while(i.hasNext()){
				currentFile = i.next();
				if(currentFile.getType().equals("dir")){
					nextDir = currentDir+"/"+currentFile.getName();
					//lookup dir
					//this.logger.info("doNetstorageDir recursive lookup nextDir: " +nextDir);
					nextStat = doNetstorageDir(nextDir, true);
					if(nextStat.getFile().size()>0){
						currentFile.setFile(nextStat.getFile());	
					}
					
				}
			}				
	}
	Gson gsonBuilder = new GsonBuilder().setPrettyPrinting().create();
	openAPI.logger.debug("doNetstorageDir recursive lookup gson.toJson(currentStat): " +gsonBuilder.toJson(currentStat));
	return currentStat;
}

public String doNetstorageDu(String path) {
	String responseString = null;
	try {
		InputStream responseStream = this.getNetStorage().du(path);
		responseString = OpenAPI.inputStreamReader(responseStream);
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} catch (NetStorageException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	// ns.delete("/1234/example.zip");
	catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return responseString;
}

public String doNetstorageDu(String path, boolean isJson) {
	return OpenAPI.xml2json(doNetstorageDu(path));
}

public boolean doNetstorageDownload(String pathNetstorage, String pathLocal) {
	boolean isDone = false;
	try (InputStream responseStream = this.getNetStorage().download(pathNetstorage)){

		File targetFile = new File(pathLocal);
		try (OutputStream outStream = new FileOutputStream(targetFile)){
    	    byte[] buffer = new byte[8 * 1024];
    	    int bytesRead;
    	    while ((bytesRead = responseStream.read(buffer)) != -1) {
    	        outStream.write(buffer, 0, bytesRead);
    	    }				
		}
		isDone = true;
		openAPI.logger.debug("ns.download(" + pathNetstorage + " , "+pathLocal+")");
	} catch (NetStorageException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return isDone;
}



public boolean doNetstorageUpload(String targetPath, String sourcePath) {
	boolean uploadResult = false;
		try {
			InputStream stream = openAPI.fileReader(sourcePath);
			uploadResult = this.getNetStorage().upload(targetPath, stream);
		} catch (FileNotFoundException e){
			// TODO Need to figure out if we need to retry
			openAPI.logger.info("Local File does not exist");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			openAPI.logger.info("IOException, need to retry");
			e.printStackTrace();
		} catch (com.akamai.netstorage.NetStorageException e){
			// TODO Need to figure out if we need to retry
			openAPI.logger.info("NetStorageException, need to retry");
			e.printStackTrace();
		} catch (Exception e) {
			openAPI.logger.info("Something did went wrong");
			e.printStackTrace();
		}			
		openAPI.logger.debug("ns.upload(" + targetPath + "):" + uploadResult);
	return uploadResult;
}

public boolean doNetstorageUpload(String targetPath, String sourcePath, String releaseDate) {
	boolean uploadResult = false;
		try {
			InputStream stream = openAPI.fileReader(sourcePath);
			byte[] b = Files.readAllBytes(Paths.get(sourcePath));
			String targetPathFileName = Paths.get(sourcePath).getFileName().toString();
			byte[] hash = MessageDigest.getInstance("MD5").digest(b);
			String hashValue = DatatypeConverter.printHexBinary(hash).toLowerCase();
			String hashValuePath = targetPath + hashValue + ".png";
			String newestPath = targetPath + releaseDate + ".png";
			String originPath = targetPath + targetPathFileName;
			
			openAPI.logger.info("hashValuePath: " + hashValuePath);
			openAPI.logger.info("newestPath: " + newestPath);
			openAPI.logger.info("originPath: " + originPath);
			
			uploadResult = this.getNetStorage().upload(hashValuePath, stream);
			uploadResult = this.doNetstorageSymLink(newestPath, hashValuePath);
			uploadResult = this.doNetstorageSymLink(originPath, hashValuePath);
		} catch (FileNotFoundException e){
			// TODO Need to figure out if we need to retry
			openAPI.logger.info("Local File does not exist");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			openAPI.logger.info("IOException, need to retry");
			e.printStackTrace();
		} catch (com.akamai.netstorage.NetStorageException e){
			// TODO Need to figure out if we need to retry
			openAPI.logger.info("NetStorageException, need to retry");
			e.printStackTrace();
		} catch (Exception e) {
			openAPI.logger.info("Something did went wrong");
			e.printStackTrace();
		}			
		openAPI.logger.debug("ns.upload(" + targetPath + "):" + uploadResult);
	return uploadResult;
}

public boolean doNetstorageSymLink(String filePath, String targetPath) {
	boolean result = false;
	try {
		result = this.getNetStorage().symlink(filePath, targetPath);
	} catch (NetStorageException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return result;
}

public boolean doNetstorageReName(String originalPath, String newPath) {
	boolean result = false;
	try {
		result = this.getNetStorage().rename(originalPath, newPath);
	} catch (NetStorageException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return result;
}

}
