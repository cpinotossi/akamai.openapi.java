package cpinotos.openapi.test.step;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import cpinotos.openapi.OpenAPI;
import cpinotos.openapi.services.NetStorageAPI;

public class OpenCLITestDeleteFile {


	@Test
	public void test() {
		boolean isDebug = false;
		String configPath = "./openapi.property";
		String rootPath = "/599907";
		String testFolderPath = rootPath+"/cpttest";
		String testFileName = "test.txt";
		String testFilePath = testFolderPath+"/"+testFileName;
		String path = testFilePath;
		boolean result = false;
		OpenAPI openAPI = new OpenAPI(configPath, isDebug);
		NetStorageAPI nsapi = new NetStorageAPI(openAPI);
		result = nsapi.doNetstorageDelete(path);

		
		if (result) {
			System.out.println("deleteFile(" + path + ") was successfully");
		}
		assertTrue(result);
	}

}
