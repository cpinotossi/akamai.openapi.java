package cpinotos.openapi.test.step;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import cpinotos.openapi.OpenAPI;


public class OpenCLITestMkdir {


	@Test
	public void test() {
		boolean isDebug = false;
		String configPath = "./openapi.property";
		String rootPath = "/599907";
		String testFolderPath = rootPath+"/updates/cpttest";
		boolean result = false;
		String path = testFolderPath;
		OpenAPI openAPI = new OpenAPI(configPath, isDebug);
		result = openAPI.doNetstorageMkdir(path);
		if (result) {
			System.out.println("testMkdir(" + path + ") was successfully");
		}
		assertTrue(result);
	}

}
