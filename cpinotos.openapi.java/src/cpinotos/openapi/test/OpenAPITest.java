package cpinotos.openapi.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.security.SignatureException;
import java.time.Instant;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

//import com.akamai.edgeauth.AkamaiTokenConfig;
//import com.akamai.edgeauth.AkamaiTokenGenerator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import cpinotos.openapi.OpenAPI;
import cpinotos.openapi.netstorage.NetStorageDirResultStat;
import cpinotos.openapi.papi.PapiSearchResult;
import cpinotos.openapi.papi.PapiSearchResultItem;




@FixMethodOrder(MethodSorters.NAME_ASCENDING)



public class OpenAPITest {
	boolean isDebug = false;
	String configPath = "./openapi.property";
	String rootPath = "/599907";
	String testFolderPath = rootPath+"/updates/cpttest";
	String testFileName = "test.txt";
	String testFilePath = testFolderPath+"/"+testFileName;

	
	@Test
	public void test1() { testMkdir(); testDir(); testDownload(); testDeleteFolder();}
	
	public void testMkdir() {
		boolean result = false;
		String path = testFolderPath;
		OpenAPI openCLI = new OpenAPI(configPath, isDebug);
		result = openCLI.doNetstorageMkdir(path);
		if (result) {
			System.out.println("testMkdir(" + path + ") was successfully");
		}
		assertTrue(result);
	}

	@Ignore
	@Test
	public void testBUpload() {
		boolean result = false;
		String localPath = "./"+testFileName;
		String uploadPath = testFilePath;
		OpenAPI openCLI = new OpenAPI(configPath, isDebug);
		result = openCLI.doNetstorageUpload(uploadPath, localPath);
		if (result) {
			System.out.println("upload(" + uploadPath + ", " + localPath + ") was successfully");
		}
		assertTrue(result);
	}

	@Ignore
	@Test
	public void test_03_Upload2() {
		boolean uploadResult = false;
		String localPath = "./"+testFileName;
		String uploadPath = testFolderPath +"/"+ System.currentTimeMillis() / 1000L + ".txt";
		OpenAPI openCLI = new OpenAPI(configPath, isDebug);
		uploadResult = openCLI.doNetstorageUpload(uploadPath, localPath);
		if (uploadResult) {
			System.out.println("upload(" + uploadPath + ", " + localPath + ") was successfully");
		}
		assertTrue(uploadResult);

	}
	

	public void testDir() {
		String path = testFolderPath;
		NetStorageDirResultStat response = null;
		OpenAPI openCLI = new OpenAPI(configPath, isDebug);
		response = openCLI.doNetstorageDir(path, false);
		if (response.getFile().get(1).getName().equals(testFileName)) {
			System.out.println("testDir(" + path + ") was successfully");
		}
		assertNotNull(response);
	}
	
	public void testDownload() {
		String downloadPath = testFilePath;
		String localPath = "./test.tmp.txt";
		boolean isDownloadOk = false;
		OpenAPI openAPI = new OpenAPI(configPath, isDebug);
		isDownloadOk = openAPI.doNetstorageDownload(downloadPath,localPath);
		if (isDownloadOk) {
			System.out.println("download(" + downloadPath + ","+localPath+") was successfully");

		}
		assertTrue(isDownloadOk);
	}



	@Ignore
	@Test
	public void test_06_DirRecursive() {
		String path = rootPath;
		NetStorageDirResultStat response = null;
		OpenAPI openCLI = new OpenAPI(configPath, isDebug);
		response = openCLI.doNetstorageDir(path, true);
		Gson gsonBuilder = new GsonBuilder().setPrettyPrinting().create();
		System.out.println("testDirRecursive("+path+"): " +gsonBuilder.toJson(response));
		assertNotNull(response);
	}
	
	@Ignore
	@Test
	public void test_07_Du() {
		String netstoragePathToFolder = testFolderPath;
		String responseString = null;
		OpenAPI openCLI = new OpenAPI(configPath, isDebug);
		responseString = openCLI.doNetstorageDu(netstoragePathToFolder, true);
		if (responseString.startsWith("{\"du\": {")) {
			System.out.println("du(" + netstoragePathToFolder + ") was successfully");
		}
		assertNotNull(responseString);
	}
	
	@Ignore
	@Test
	public void test_08_DeleteFile() {
		String path = testFilePath;
		boolean result = false;
		OpenAPI openCLI = new OpenAPI(configPath, isDebug);
		result = openCLI.doNetstorageDelete(path);
		if (result) {
			System.out.println("deleteFile(" + path + ") was successfully");
		}
		assertTrue(result);
	}
	
	public void testDeleteFolder() {
		String path = testFolderPath;
		boolean result = false;
		OpenAPI openCLI = new OpenAPI(configPath, isDebug);
		result = openCLI.doNetstorageDelete(path);
		if (result) {
			System.out.println("deleteFolder(" + path + ") was successfully");
		}
		assertTrue(result);
	}
	
	@Ignore
	@Test
	public void test_10_Invalidate() {
		String pathToinvalidationFileList = "./purgeobjects.json";
		boolean isInvalidated = false;
		String invalidationListJson = null;
		OpenAPI openCLI = new OpenAPI(configPath, isDebug);
		try {
			invalidationListJson = OpenAPI.jsonFileReader(pathToinvalidationFileList);
			isInvalidated = openCLI.doPurgeInvalidate(invalidationListJson);
			if (isInvalidated)
				System.out.println("doPurgeInvalidate(" + pathToinvalidationFileList + ") was successfully");
		} catch (IOException e) {
			fail("IO Exception");
			e.printStackTrace();
		}
		assertTrue(isInvalidated);
	}
	
	@Ignore
	@Test
	public void test_11_edgeURL() {
		String downloadPath = "/updates/mozart.mp3";
		OpenAPI openAPI = new OpenAPI(configPath, isDebug);
		PapiSearchResult psr = openAPI.searchPAPIConfiguration();
		System.out.println("edgeURL:step1/7: found Property Configuration for Hostname " + openAPI.getHost());
		PapiSearchResultItem psri = psr.getVersions().getItems().get(0);
		System.out.println("edgeURL:step2/7: found Property Configuration "+ psri.getPropertyName() +"  version "+ psri.getPropertyVersion());
		String prt = openAPI.getPAPIRuletree(psri.getPropertyId(), psri.getPropertyVersion(), psri.getContractId(),
				psri.getGroupId(), false);
		if(prt.equals(null)){
			fail("could not retreive property configuration");
		}else{
			System.out.println("edgeURL:step3/7: downloaded Property Configuration "+ psri.getPropertyName() +" version "+ psri.getPropertyVersion());
			String edgeAuthKey = openAPI.getEdgeAuthKeyFromPapiRuleSet(prt);
			System.out.println("edgeURL:step4/7: extract edgeauth secret key from Property Configuration "+ psri.getPropertyName() +" version "+ psri.getPropertyVersion());
			String edgeAuthLocationId =openAPI.getEdgeAuthLocationFromPapiRuleSet(prt);
			System.out.println("edgeURL:step5/7: extract edgeauth query name from Property Configuration "+ psri.getPropertyName() +" version "+ psri.getPropertyVersion());
			//AkamaiTokenConfig conf;
			String edgeAuthToken;
			try {
				//conf = OpenAPI.GenerateConfig(downloadPath, edgeAuthKey, 3600);
				System.out.println("edgeURL:step6/7: generate edgeauth token");
				//edgeAuthToken = AkamaiTokenGenerator.generateToken(conf);
				Integer startTime = (int) Instant.now().getEpochSecond();
				edgeAuthToken = openAPI.getEdgeAuthToken(downloadPath, edgeAuthKey, 3600, "token", startTime);
				//TODO Need to clean up the URL generation
				System.out.println("edgeURL:step7/7: EdgeURL: \nhttps://" + openAPI.getHost() + downloadPath + "?" + edgeAuthLocationId + "="
						+ edgeAuthToken);
			}catch (Exception e) {
				fail("Exception during EdgeAuth Token generation");
				e.printStackTrace();
			}
	}
	}
}
