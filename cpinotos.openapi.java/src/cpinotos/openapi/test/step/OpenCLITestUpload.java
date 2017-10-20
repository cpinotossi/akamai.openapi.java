package cpinotos.openapi.test.step;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import cpinotos.openapi.OpenAPI;

public class OpenCLITestUpload {


	@Test
	public void test() {
		boolean isDebug = false;
		String configPath = "./openapi.property";
		String rootPath = "/599907";
		String testFolderPath = rootPath+"/updates/cpttest";
		String testFileName = "test.txt";
		String testFilePath = testFolderPath+"/"+testFileName;
		boolean result = false;
		String localPath = "./"+testFileName;
		String uploadPath = testFilePath;
		OpenAPI openAPI = new OpenAPI(configPath, isDebug);
		result = openAPI.doNetstorageUpload(uploadPath, localPath);
		if (result) {
			System.out.println("upload(" + uploadPath + ", " + localPath + ") was successfully");
		}
		assertTrue(result);
	}

}
