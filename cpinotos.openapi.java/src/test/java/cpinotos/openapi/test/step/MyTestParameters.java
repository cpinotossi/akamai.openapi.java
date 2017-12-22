package cpinotos.openapi.test.step;

import java.io.IOException;
import java.io.InputStream;

import org.ini4j.InvalidFileFormatException;
import org.ini4j.Wini;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cpinotos.openapi.OpenAPI;
import cpinotos.openapi.services.DiagnosticToolsAPI;
import cpinotos.openapi.services.NetStorageAPI;
import cpinotos.openapi.services.PropertyManagerAPI;
import cpinotos.openapi.services.PurgeAPI;
import cpinotos.openapi.services.data.NetStorageDirResultStat;
import lombok.Getter;
import lombok.Setter;

public class MyTestParameters {
	@Getter @Setter private boolean result=false;
	@Getter @Setter private boolean isDebug=false;
	@Getter @Setter private String hostname = "ota.edgegate.de";
	@Getter @Setter private String edgercFilePath1 = "/Users/cpinotos/.edgerc";
	@Getter @Setter private String edgercFilePath2 = "/Users/cpinotos/git/cpinotos.openapi.java/ota.edgegate.de.edgerc";
	@Getter @Setter private String apiClientNameDefault = "api-client";
	@Getter @Setter private String apiClientNamePurgeDefault = "api-client-purge";
	@Getter @Setter private String apiClientNameNetStorageDefault = "api-client-netstorage";
	
	@Getter @Setter private String netStorageRootPath = "/599907";
	@Getter @Setter private String netStorageFolderName = "/cpttest";
	@Getter @Setter private String netStorageTestFolderPath = netStorageRootPath+netStorageFolderName;
	@Getter @Setter private String testFileName1 = "test.txt";
	@Getter @Setter private String testFileName2 = "test2.txt";
	@Getter @Setter private String folderNameUpdates = "updates";
	@Getter @Setter private String testSymLinkName = "test.symlink.txt";
	@Getter @Setter private String netStorageTestFilePath = netStorageTestFolderPath+"/"+testFileName1;
	@Getter @Setter private String netStorageTestSymLinkPath = netStorageTestFolderPath+"/"+testSymLinkName;
	@Getter @Setter private String netStorageTestUrlPath = netStorageFolderName +"/"+ testFileName1;	
	@Getter @Setter private String localPath = "./"+testFileName1;
	@Getter @Setter private NetStorageAPI nsapi;
	@Getter @Setter private NetStorageDirResultStat netStorageResponse=null;
	@Getter @Setter private PurgeAPI puapi;
	@Getter @Setter private PropertyManagerAPI papi;
	@Getter @Setter private DiagnosticToolsAPI dapi;
	@Getter @Setter private String testPurgeJsonPath = "/Users/cpinotos/git/cpinotos.openapi.java/purge.json";
	@Getter @Setter private String testPurgeURL1 = "http://"+hostname+"/"+netStorageFolderName+"/"+testFileName1;
	@Getter @Setter private String testPurgeURL2 = "http://"+hostname+"/"+netStorageFolderName+"/"+testFileName2;

	static Logger LOGGER = LoggerFactory.getLogger(OpenCLITestDir.class);

}
