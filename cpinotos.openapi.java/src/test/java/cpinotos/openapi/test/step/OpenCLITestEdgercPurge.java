package cpinotos.openapi.test.step;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import cpinotos.openapi.services.PurgeAPI;

public class OpenCLITestEdgercPurge extends MyTestParameters {
	ArrayList <String> urlList1;
	ArrayList <String> urlList2;
	
	@Before
	public void before(){
		this.urlList1 = new ArrayList<String>();
		urlList1.add(this.getTestPurgeURL1());
		this.urlList2 = new ArrayList<String>();
		urlList2.add(this.getTestPurgeURL1());
		urlList2.add(this.getTestPurgeURL2());
	}

	@Test
	public void test1() throws Exception {
		this.setPuapi(new PurgeAPI(getHostname(), null, this.getApiClientNamePurgeDefault(), this.isDebug()));
		LOGGER.info(String.format("PurgeAPI 1 %s", this.urlList1));
		assertTrue(this.getPuapi().doPurgeInvalidate(this.urlList1, true));
	}
	
	@Test
	public void test2() throws Exception {
		this.setPuapi(new PurgeAPI(getHostname(), null, null, this.isDebug()));
		LOGGER.info(String.format("PurgeAPI 2 %s", this.urlList1));
		assertTrue(this.getPuapi().doPurgeInvalidate(this.urlList1, true));
	}
	
	@Test
	public void test3() throws Exception {
		this.setPuapi(new PurgeAPI(getHostname(), null, null, false));
		LOGGER.info(String.format("PurgeAPI 3 %s", this.urlList1));
		assertTrue(this.getPuapi().doPurgeInvalidate(this.urlList1, true));
	}

	@Test
	public void test4() throws Exception {
		this.setPuapi(new PurgeAPI(getHostname(), getEdgercFilePath1(), null, false));
		LOGGER.info(String.format("PurgeAPI 4 %s", this.urlList1));
		assertTrue(this.getPuapi().doPurgeInvalidate(this.urlList1, true));
	}
	
	@Test
	public void test5() throws Exception {
		this.setPuapi(new PurgeAPI(getHostname(), getEdgercFilePath1(), this.getApiClientNamePurgeDefault(), false));
		LOGGER.info(String.format("PurgeAPI 5 %s", this.urlList1));
		assertTrue(this.getPuapi().doPurgeInvalidate(this.urlList1, true));
	}
	
	@Test
	public void test6() throws Exception {
		this.setPuapi(new PurgeAPI(getHostname(), getEdgercFilePath1(), this.getApiClientNamePurgeDefault(), this.isDebug()));
		LOGGER.info(String.format("PurgeAPI 6 %s", this.urlList1));
		assertTrue(this.getPuapi().doPurgeInvalidate(this.urlList1, true));
	}

	@Test
	public void test7() throws Exception {
		this.setPuapi(new PurgeAPI(getHostname(), getEdgercFilePath1(), this.getApiClientNamePurgeDefault(), this.isDebug()));
		LOGGER.info(String.format("PurgeAPI 7 %s", this.urlList2));
		assertTrue(this.getPuapi().doPurgeInvalidate(this.urlList2, true));
	}

	@Test
	public void test8() throws Exception {
		this.setPuapi(new PurgeAPI(getHostname(), getEdgercFilePath1(), this.getApiClientNamePurgeDefault(), this.isDebug()));
		LOGGER.info(String.format("PurgeAPI 8 %s", this.getTestPurgeJsonPath()));
		assertTrue(this.getPuapi().doPurgeInvalidate(this.getTestPurgeJsonPath(), true));
	}
}
