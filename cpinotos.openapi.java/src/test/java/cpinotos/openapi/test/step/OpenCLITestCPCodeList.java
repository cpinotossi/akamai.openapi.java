package cpinotos.openapi.test.step;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import cpinotos.openapi.services.PropertyManagerAPI;
import cpinotos.openapi.services.data.ListCPCode;
import cpinotos.openapi.services.data.ListCPCodeResult;

public class OpenCLITestCPCodeList extends MyTestParameters {

	@Before
	public void before() {
		this.setPapi(new PropertyManagerAPI(this.getHostname(), this.getEdgercFilePath1(), this.getApiClientNameDefault(), this.isDebug()));
	}
	
	@Test
	public void test() throws Exception {
		ListCPCodeResult listCPCodeResult = this.getPapi().doListCPCodes();
		Gson gsonBuilder = new GsonBuilder().setPrettyPrinting().create();
		StringBuilder jsonResultStringBuilder = new StringBuilder();
		Iterator<ListCPCode> i = listCPCodeResult.getCpcodes().getItems().iterator();
		while(i.hasNext()){
			jsonResultStringBuilder.append(gsonBuilder.toJson(i.next())+"\n");
		}
		LOGGER.info(String.format("List CPCodes \n %s", jsonResultStringBuilder.toString()));
		assertTrue(listCPCodeResult.getCpcodes().getItems().size()!=0);
		
	}

}
