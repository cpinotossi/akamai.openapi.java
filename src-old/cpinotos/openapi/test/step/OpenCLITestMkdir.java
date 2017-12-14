package cpinotos.openapi.test.step;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import cpinotos.openapi.OpenAPI;
import cpinotos.openapi.services.NetStorageAPI;


public class OpenCLITestMkdir {


	@Test
	public void test() {
		boolean isDebug = false;
		String configPath = "./openapi.property";
		String rootPath = "/599907";
		String testFolderPath = rootPath+"/cpttest";
		boolean result = false;
		String path = testFolderPath;
		OpenAPI openAPI = new OpenAPI(configPath, isDebug);
		NetStorageAPI nsapi = new NetStorageAPI(openAPI);
		result = nsapi.doNetstorageMkdir(path);
		if (result) {
			System.out.println("testMkdir(" + path + ") was successfully");
		}
		assertTrue(result);
	}

}
