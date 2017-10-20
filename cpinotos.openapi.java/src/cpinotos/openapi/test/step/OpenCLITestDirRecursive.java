package cpinotos.openapi.test.step;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import cpinotos.openapi.OpenAPI;
import cpinotos.openapi.netstorage.NetStorageDirResultStat;

public class OpenCLITestDirRecursive {


	@Test
	public void test() {
		boolean isDebug = false;
		String configPath = "./openapi.property";
		String rootPath = "/599907";
		String testFolderPath = rootPath+"/updates";
		
		String path = testFolderPath;
		NetStorageDirResultStat response = null;
		OpenAPI openAPI = new OpenAPI(configPath, isDebug);
		response = openAPI.doNetstorageDir(path, true);
		Gson gsonBuilder = new GsonBuilder().setPrettyPrinting().create();
		System.out.println("testDirRecursive("+path+"): " +gsonBuilder.toJson(response));
		assertNotNull(response);
	}

}
