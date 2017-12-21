package cpinotos.openapi.test.step;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import cpinotos.openapi.OpenAPI;
import cpinotos.openapi.services.NetStorageAPI;

public class OpenCLITestDirRecursive extends MyTestParameters {

	@Before
	public void before() {
		setOpenAPI(new OpenAPI(getConfigPath(), getHost(), isDebug()));
		setNsapi(new NetStorageAPI(getOpenAPI()));
	}
	
	@Test
	public void test() {
		setNetStorageResponse(getNsapi().doNetstorageDir(getNetStorageTestFolderPath(), true));
		Gson gsonBuilder = new GsonBuilder().setPrettyPrinting().create();
		LOGGER.info(String.format("dir recursive %s", getNetStorageTestFolderPath()));
		LOGGER.debug("recursive dir result ("+getNetStorageTestFolderPath()+"):\n" +gsonBuilder.toJson(getNetStorageResponse()));
		assertNotNull(getNetStorageResponse());
	}
}
