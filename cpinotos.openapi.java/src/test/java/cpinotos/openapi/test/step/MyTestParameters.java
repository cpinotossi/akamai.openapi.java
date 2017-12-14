package cpinotos.openapi.test.step;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cpinotos.openapi.OpenAPI;
import cpinotos.openapi.services.NetStorageAPI;
import cpinotos.openapi.services.data.NetStorageDirResultStat;
import lombok.Getter;
import lombok.Setter;

public class MyTestParameters {
	@Getter @Setter private boolean result=false;
	@Getter @Setter private boolean isDebug=false;
	@Getter @Setter private String configPath = "./openapi.property";
	@Getter @Setter private String netStorageRootPath = "/599907";
	@Getter @Setter private String netStorageTestFolderPath = netStorageRootPath+"/cpttest";
	@Getter @Setter private String testFileName = "test.txt";
	@Getter @Setter private String testSymLinkName = "test.symlink.txt";
	@Getter @Setter private String netStorageTestFilePath = netStorageTestFolderPath+"/"+testFileName;
	@Getter @Setter private String netStorageTestSymLinkPath = netStorageTestFolderPath+"/"+testSymLinkName;
	
	@Getter @Setter private String localPath = "./"+testFileName;
	
	@Getter @Setter private OpenAPI openAPI;
	@Getter @Setter private NetStorageAPI nsapi;
	@Getter @Setter private NetStorageDirResultStat netStorageResponse=null;
	
	static Logger LOGGER = LoggerFactory.getLogger(OpenCLITestDir.class);	
	

}
