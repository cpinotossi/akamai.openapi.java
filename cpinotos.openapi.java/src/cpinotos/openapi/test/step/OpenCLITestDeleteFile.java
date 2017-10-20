package cpinotos.openapi.test.step;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import cpinotos.openapi.OpenAPI;

public class OpenCLITestDeleteFile {


	@Test
	public void test() {
		boolean isDebug = false;
		String configPath = "./openapi.property";
		String rootPath = "/599907";
		String testFolderPath = rootPath+"/updates/cpttest";
		String testFileName = "test.txt";
		String testFilePath = testFolderPath+"/"+testFileName;
		String path = testFilePath;
		boolean result = false;
		OpenAPI openAPI = new OpenAPI(configPath, isDebug);
		result = openAPI.doNetstorageDelete(path);
		if (result) {
			System.out.println("deleteFile(" + path + ") was successfully");
		}
		assertTrue(result);
	}

}
