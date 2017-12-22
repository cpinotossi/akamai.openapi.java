package cpinotos.openapi.test.step;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	//@Getter @Setter private String configPath = "/Users/cpinotos/.edgerc";
	@Getter @Setter private String edgercFilePath1 = "/Users/cpinotos/.edgerc";
	@Getter @Setter private String edgercFilePath2 = "/Users/cpinotos/git/cpinotos.openapi.java/ota.edgegate.de.edgerc";
;	@Getter @Setter private String apiClientName="api-client";
	@Getter @Setter private String apiClientPurgeName="api-client-purge";
	@Getter @Setter private String apiUploadAccountName="api-upload-account";
	@Getter @Setter private String netStorageRootPath = "/599907";
	@Getter @Setter private String netStorageFolderName = "/cpttest";
	@Getter @Setter private String netStorageTestFolderPath = netStorageRootPath+netStorageFolderName;
	@Getter @Setter private String testFileName = "test.txt";
	@Getter @Setter private String folderNameUpdates = "updates";
	@Getter @Setter private String testSymLinkName = "test.symlink.txt";
	@Getter @Setter private String netStorageTestFilePath = netStorageTestFolderPath+"/"+testFileName;
	@Getter @Setter private String netStorageTestSymLinkPath = netStorageTestFolderPath+"/"+testSymLinkName;
	@Getter @Setter private String netStorageTestUrlPath = netStorageFolderName +"/"+ testFileName;	
	@Getter @Setter private String localPath = "./"+testFileName;
	@Getter @Setter private NetStorageAPI nsapi;
	@Getter @Setter private NetStorageDirResultStat netStorageResponse=null;
	@Getter @Setter private PurgeAPI puapi;
	@Getter @Setter private PropertyManagerAPI papi;
	@Getter @Setter private DiagnosticToolsAPI dapi;
	@Getter @Setter private String testPurgeURL = "http://"+hostname+"/"+netStorageFolderName+"/"+testFileName;

	static Logger LOGGER = LoggerFactory.getLogger(OpenCLITestDir.class);	
	

}
