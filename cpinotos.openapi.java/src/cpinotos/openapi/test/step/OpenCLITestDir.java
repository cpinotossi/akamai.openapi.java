package cpinotos.openapi.test.step;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import cpinotos.openapi.OpenAPI;
import cpinotos.openapi.netstorage.NetStorageDirResultStat;

public class OpenCLITestDir {


	@Test
	public void test() {
		boolean isDebug = false;
		String configPath = "./openapi.property";
		String rootPath = "/599907";
		String testFolderPath = rootPath+"/updates/cpttest";
		String testFileName = "test.txt";
		OpenAPI openAPI = new OpenAPI(configPath, isDebug);
		String path = testFolderPath;
		NetStorageDirResultStat response = null;
		response = openAPI.doNetstorageDir(path, false);
		if (response.getFile().get(0).getName().equals(testFileName)) {
			System.out.println("testDir(" + path + ") was successfully");
		}
		assertNotNull(response);
	}

}
