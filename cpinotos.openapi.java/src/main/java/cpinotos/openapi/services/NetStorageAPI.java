package cpinotos.openapi.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.Iterator;

import javax.xml.bind.DatatypeConverter;

import com.akamai.netstorage.NetStorage;
import com.akamai.netstorage.NetStorageException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import cpinotos.openapi.OpenAPI;
import cpinotos.openapi.services.data.NetStorageDirResult;
import cpinotos.openapi.services.data.NetStorageDirResultFile;
import cpinotos.openapi.services.data.NetStorageDirResultStat;
import cpinotos.openapi.services.data.NetStorageDuResult;
import lombok.Getter;
import lombok.Setter;


public class NetStorageAPI extends OpenAPI{
@Getter @Setter private NetStorage netStorage;


public NetStorageAPI(String hostname, String edgercFilePath, String apiUploadAccountNameSection, boolean debug){
	super(hostname, edgercFilePath, debug);
	this.setApiUploadAccountName(apiUploadAccountNameSection);	
	initApiCredentialsNetStorage();
	this.setNetStorage(new NetStorage(this.getNetstorageCredential()));
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

public boolean doNetstorageDelete(String path){
	boolean isDeleted = false;
	boolean isFolder = isNetstorageFolder(path);
	try{
		if(isFolder){
			isDeleted = this.getNetStorage().quickDelete(path);		
		}else{
			isDeleted = this.getNetStorage().delete(path);	
		}
	}catch (Exception e) {
		OpenAPI.LOGGER.debug("doNetstorageDelete",e);
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
	}catch (Exception e) {
		OpenAPI.LOGGER.debug("NetStorageDirResultStat",e);
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
		OpenAPI.LOGGER.debug("doNetstorageDir recursive lookup currentDir: " +currentDir);

			//look for directories inside the Stats file list
			Iterator<NetStorageDirResultFile> i = currentStat.getFile().iterator();
			while(i.hasNext()){
				currentFile = i.next();
				if(currentFile.getType().equals("dir")){
					nextDir = currentDir+"/"+currentFile.getName();
					//lookup dir
					nextStat = doNetstorageDir(nextDir, true);
					if(nextStat.getFile().size()>0){
						currentFile.setFile(nextStat.getFile());	
					}
					
				}
			}				
	}
	Gson gsonBuilder = new GsonBuilder().setPrettyPrinting().create();
	OpenAPI.LOGGER.debug("doNetstorageDir recursive lookup gson.toJson(currentStat): " +gsonBuilder.toJson(currentStat));
	return currentStat;
}



public boolean isNetstorageFolder(String path){
	boolean isFolder = false;
	try {
		this.getNetStorage().du(path);
		isFolder = true;
	}
	catch (Exception e) {
		OpenAPI.LOGGER.debug("isNetstorageFolder",e);
	}
	return isFolder;
}

public NetStorageDuResult doNetstorageDu(String path){
	String xmlResult;
	String jsonResult;
	NetStorageDuResult response = null;
	try {
		InputStream responseStream = this.getNetStorage().du(path);
		Gson gson = new Gson();
		xmlResult = OpenAPI.inputStreamReader(responseStream);
		jsonResult = OpenAPI.xml2json(xmlResult);
		response =  gson.fromJson(jsonResult, NetStorageDuResult.class);
	}catch (Exception e) {
		OpenAPI.LOGGER.debug("doNetstorageDu",e);
	}
	return response;
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
		OpenAPI.LOGGER.debug("ns.download(" + pathNetstorage + " , "+pathLocal+")");
	} catch (Exception e) {
		OpenAPI.LOGGER.debug("doNetstorageDownload",e);
	}
	return isDone;
}



public boolean doNetstorageUpload(String targetPath, String sourcePath) {
	boolean uploadResult = false;
		try {
			InputStream stream = this.fileReader(sourcePath);
			uploadResult = this.getNetStorage().upload(targetPath, stream);
		} catch (FileNotFoundException e){
			// TODO Need to figure out if we need to retry
			OpenAPI.LOGGER.info("Local File does not exist");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			OpenAPI.LOGGER.info("IOException, need to retry");
			OpenAPI.LOGGER.debug("doNetstorageUpload",e);
		} catch (com.akamai.netstorage.NetStorageException e){
			// TODO Need to figure out if we need to retry
			OpenAPI.LOGGER.info("NetStorageException, need to retry");
			OpenAPI.LOGGER.debug("doNetstorageUpload",e);
		} catch (Exception e) {
			OpenAPI.LOGGER.info("Something did went wrong");
			OpenAPI.LOGGER.debug("doNetstorageUpload",e);
		}			
		OpenAPI.LOGGER.debug("ns.upload(" + targetPath + "):" + uploadResult);
	return uploadResult;
}

public boolean doNetstorageUpload(String targetPath, String sourcePath, String releaseDate) {
	boolean uploadResult = false;
		try {
			InputStream stream = this.fileReader(sourcePath);
			byte[] b = Files.readAllBytes(Paths.get(sourcePath));
			String targetPathFileName = Paths.get(sourcePath).getFileName().toString();
			byte[] hash = MessageDigest.getInstance("MD5").digest(b);
			String hashValue = DatatypeConverter.printHexBinary(hash).toLowerCase();
			String hashValuePath = targetPath + hashValue + ".png";
			String newestPath = targetPath + releaseDate + ".png";
			String originPath = targetPath + targetPathFileName;
			
			OpenAPI.LOGGER.info("hashValuePath: " + hashValuePath);
			OpenAPI.LOGGER.info("newestPath: " + newestPath);
			OpenAPI.LOGGER.info("originPath: " + originPath);
			
			uploadResult = this.getNetStorage().upload(hashValuePath, stream);
			uploadResult = this.doNetstorageSymLink(newestPath, hashValuePath);
			uploadResult = this.doNetstorageSymLink(originPath, hashValuePath);
		} catch (FileNotFoundException e){
			// TODO Need to figure out if we need to retry
			OpenAPI.LOGGER.info("Local File does not exist");
			OpenAPI.LOGGER.debug("doNetstorageUpload",e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			OpenAPI.LOGGER.info("IOException, need to retry");
			OpenAPI.LOGGER.debug("doNetstorageUpload",e);
		} catch (com.akamai.netstorage.NetStorageException e){
			// TODO Need to figure out if we need to retry
			OpenAPI.LOGGER.info("NetStorageException, need to retry");
			OpenAPI.LOGGER.debug("doNetstorageUpload",e);
		} catch (Exception e) {
			OpenAPI.LOGGER.info("Something did went wrong");
			OpenAPI.LOGGER.debug("doNetstorageUpload",e);
		}			
		OpenAPI.LOGGER.debug("ns.upload(" + targetPath + "):" + uploadResult);
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
