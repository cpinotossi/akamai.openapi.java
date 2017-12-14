package cpinotos.openapi.test.step;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import cpinotos.openapi.OpenAPI;
import cpinotos.openapi.netstorage.NetStorageDirResultStat;
import cpinotos.openapi.services.NetStorageAPI;

public class OpenCLITestDir {


	@Test
	public void test() {
		boolean isDebug = false;
		String configPath = "./openapi.property";
		String rootPath = "/599907";
		String testFolderPath = rootPath+"/cpttest";
		String testFileName = "test.txt";
		OpenAPI openAPI = new OpenAPI(configPath, isDebug);
		NetStorageAPI nsapi = new NetStorageAPI(openAPI);
		String path = testFolderPath;
		NetStorageDirResultStat response = null;
		response = nsapi.doNetstorageDir(path, false);
		if (response.getFile().get(0).getName().equals(testFileName)) {
			System.out.println("testDir(" + path + ") was successfully");
		}
		assertNotNull(response);
	}

}
