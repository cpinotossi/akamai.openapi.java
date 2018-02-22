package cpinotos.openapi.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.http.client.ClientProtocolException;

import com.google.gson.Gson;

import cpinotos.openapi.OpenAPI;
import cpinotos.openapi.services.data.PollingResponse;
import cpinotos.openapi.services.data.Response;

public class PurgeAPI extends OpenAPI {

	public PurgeAPI(String hostname, String edgercFilePath, String apiClientPurgeNameSection, boolean debug) {
		super(hostname, edgercFilePath, debug);
		this.setApiClientNamePurge(apiClientPurgeNameSection);
		initApiCredentials(this.getApiClientNamePurge());
	}

	public boolean doPurgeInvalidate(String purgeJSON) throws ClientProtocolException, UnsupportedOperationException, IOException {
		return doPurgeInvalidate(purgeJSON, false);
	}

	public boolean doPurgeInvalidate(ArrayList<String> urlList, Boolean isStaging) throws ClientProtocolException, UnsupportedOperationException, IOException {
		// Define Network which should be purged
		String network = "production";
		if(isStaging){
			network = "staging";
		}
		// Generate JSON from ArrayList
		String purgeJSON;
		Iterator<String> i = urlList.iterator();
		purgeJSON = "{\"objects\":[";
		while (i.hasNext()) {
			purgeJSON += "\"" + i.next() + "\",";
		}
		purgeJSON = purgeJSON.substring(0, purgeJSON.length() - 1);
		purgeJSON += "]}";
		OpenAPI.LOGGER.debug("purgeJSON:\n" + purgeJSON);
		
		// Transform API Endpoint
		String currentApiPapiEndpoint = this.getApiPurgeInvalidateEndpoint();
		currentApiPapiEndpoint = currentApiPapiEndpoint.replace("{network}", network);
		
		return verifyPurgeResponse(this.doEdgeGridAPIRequestPOST(currentApiPapiEndpoint, purgeJSON));
		
	}
	
	public boolean doPurgeInvalidate(String jsonFilePath, Boolean isStaging) throws ClientProtocolException, UnsupportedOperationException, IOException {
		// Define Network which should be purged
		String network = "production";
		if(isStaging){
			network = "staging";
		}
		// Retrieve JSON from File
		String purgeJSON;
		purgeJSON = OpenAPI.jsonFileReader(jsonFilePath);
		OpenAPI.LOGGER.debug("purgeJSON:\n" + purgeJSON);
		
		// Transform API Endpoint
		String currentApiPapiEndpoint = this.getApiPurgeInvalidateEndpoint();
		currentApiPapiEndpoint = currentApiPapiEndpoint.replace("{network}", network);
		
		return verifyPurgeResponse(this.doEdgeGridAPIRequestPOST(currentApiPapiEndpoint, purgeJSON));
	}


	public boolean doPurgeInvalidateCPCode(String cpcode) throws ClientProtocolException, UnsupportedOperationException, IOException {
		//by default we always purge the production network
		return doPurgeInvalidateCPCode(cpcode, false);
	}

	public boolean doPurgeInvalidateCPCode(String cpcode, Boolean isStaging) throws ClientProtocolException, UnsupportedOperationException, IOException {
		// Define Network which should be purged
		String network = "production";
		if(isStaging){
			network = "staging";
		}
		// Create JSON for CPCode
		OpenAPI.LOGGER.debug("purge CPCode:\n" + cpcode);
		String cpcodeJSON = "{\"objects\":[" + cpcode + "]}";
		
		// Transform API Endpoint
		String currentApiPapiEndpoint = this.getApiPurgeInvalidateCPCodeEndpoint();
		currentApiPapiEndpoint = currentApiPapiEndpoint.replace("{network}", network);
		
		return verifyPurgeResponse(this.doEdgeGridAPIRequestPOST(currentApiPapiEndpoint, cpcodeJSON));
	}
	
	protected boolean verifyPurgeResponse(String jsonResult) {
		boolean purgeExecuted = false;
		Gson gson = new Gson();
		Response purgeResponse = gson.fromJson(jsonResult, Response.class);
		if(purgeResponse.getHttpStatus() == 201){
			purgeExecuted = true;
		}
		return purgeExecuted;
	}

	
}
