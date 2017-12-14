package cpinotos.openapi.test.step;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import cpinotos.openapi.OpenAPI;
import cpinotos.openapi.services.NetStorageAPI;

public class OpenCLITestSymLink {


	@Test
	public void test() {
		boolean isDebug = false;
		String configPath = "./openapi.property";
		String rootPath = "/599907";
		String testFolderPath = rootPath+"/cpttest";
		String testFileName = "test.txt";
		String testSymLinkName = "test2.txt";
		String testFilePath = testFolderPath+"/"+testFileName;
		String path = testFilePath;
		String symLinkPath = testFolderPath+"/"+ testSymLinkName;
		boolean result = false;
		OpenAPI openAPI = new OpenAPI(configPath, isDebug);
		NetStorageAPI nsapi = new NetStorageAPI(openAPI);
		result = nsapi.doNetstorageSymLink(symLinkPath, path);
		if (result) {
			System.out.println("symlink(" + symLinkPath + ", " + path + ") was successfully");
		}
		assertTrue(result);
	}

}
