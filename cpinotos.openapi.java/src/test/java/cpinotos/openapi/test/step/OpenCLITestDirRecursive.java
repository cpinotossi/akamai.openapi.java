package cpinotos.openapi.test.step;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import cpinotos.openapi.services.NetStorageAPI;
import cpinotos.openapi.services.data.NetStorageDirResultStat;

public class OpenCLITestDirRecursive extends MyTestParameters {

	
	@Before
	public void before() {
		setNsapi(new NetStorageAPI(getHostname(), getEdgercFilePath1(), getApiClientNameDefault(), isDebug()));
	}
	
	@Test
	public void test() {
		NetStorageDirResultStat result = null;
		try{
			setNetStorageResponse(getNsapi().doNetstorageDir(getNetStorageTestFolderPath(), true));
			Gson gsonBuilder = new GsonBuilder().setPrettyPrinting().create();
			LOGGER.info(String.format("dir recursive %s", getNetStorageTestFolderPath()));
			LOGGER.debug("recursive dir result ("+getNetStorageTestFolderPath()+"):\n" +gsonBuilder.toJson(getNetStorageResponse()));
			result = getNetStorageResponse();
		}catch(Exception e){
			LOGGER.debug(e.getStackTrace().toString(), e);
		}
		assertNotNull(result);
	}
}
