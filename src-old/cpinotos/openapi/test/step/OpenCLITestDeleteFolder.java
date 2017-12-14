package cpinotos.openapi.test.step;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import cpinotos.openapi.OpenAPI;
import cpinotos.openapi.services.NetStorageAPI;


public class OpenCLITestDeleteFolder {


	@Test
	public void test() {
		boolean isDebug = false;
		String configPath = "./openapi.property";
		String rootPath = "/599907";
		String testFolderPath = rootPath+"/cpttest";
		String path = testFolderPath;
		boolean result = false;
		OpenAPI openAPI = new OpenAPI(configPath, isDebug);
		NetStorageAPI nsapi = new NetStorageAPI(openAPI);
		result = nsapi.doNetstorageDelete(path);
		if (result) {
			System.out.println("deleteFolder(" + path + ") was successfully");
		}
		assertTrue(result);
	}

}
