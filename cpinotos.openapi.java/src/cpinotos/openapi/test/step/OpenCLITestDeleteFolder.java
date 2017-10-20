package cpinotos.openapi.test.step;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import cpinotos.openapi.OpenAPI;


public class OpenCLITestDeleteFolder {


	@Test
	public void test() {
		boolean isDebug = false;
		String configPath = "./openapi.property";
		String rootPath = "/599907";
		String testFolderPath = rootPath+"/updates/cpttest";
		String path = testFolderPath;
		boolean result = false;
		OpenAPI openAPI = new OpenAPI(configPath, isDebug);
		result = openAPI.doNetstorageDelete(path, false);
		if (result) {
			System.out.println("deleteFolder(" + path + ") was successfully");
		}
		assertTrue(result);
	}

}
