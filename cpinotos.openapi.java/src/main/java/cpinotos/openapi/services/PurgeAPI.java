package cpinotos.openapi.services;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import com.akamai.edgegrid.signer.exceptions.RequestSigningException;
import com.akamai.edgegrid.signer.googlehttpclient.GoogleHttpClientEdgeGridRequestSigner;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.apache.ApacheHttpTransport;

import cpinotos.openapi.OpenAPI;


public class PurgeAPI extends OpenAPI {

	public PurgeAPI(String hostname, String edgercFilePath, String apiClientPurgeNameSection, boolean debug){
		super(hostname, edgercFilePath, debug);		
		this.setApiClientPurgeName(apiClientPurgeNameSection);		
		initApiCredentialsPurge();
}
	
	public boolean doPurgeInvalidate(String purgeJSON) {
		return doPurgeInvalidate(purgeJSON, false);
	}
	
	public boolean doPurgeInvalidate(String purgeJSONOrURL, Boolean isStaging) {
		boolean purgeExecuted = false;
		String purgeJSON;
		//verify if JSON String or URL
		if(purgeJSONOrURL.startsWith("http")){
			//is url
			purgeJSON = "{\"objects\":[\""+purgeJSONOrURL+"\"]}";
		}else{
			purgeJSON = purgeJSONOrURL;
		}
		OpenAPI.LOGGER.debug("purgeJSON:\n" + purgeJSON);
		// Use com.google.api.client.http Helper for HTTP Request
		HttpTransport httpTransport = new ApacheHttpTransport();
		HttpRequestFactory requestFactory = httpTransport.createRequestFactory();
		URI uri = null;
		HttpRequest request = null;
		//Get the current Purge API Endpoint
		String purgeInvalidateEndpoint = this.getApiPurgeInvalidateEndpoint();
		if(isStaging){
			//In case isStaging is TRUE modify Purge API Endpoint to purge staging instead of production
			purgeInvalidateEndpoint = purgeInvalidateEndpoint.replaceAll("production", "staging");
		}		
		try {
			uri = new URI("https", this.getPurgeHost(), purgeInvalidateEndpoint, null, null);
			// Ensure to use Content-Type application/json
			request = requestFactory.buildPostRequest(new GenericUrl(uri),
					ByteArrayContent.fromString("application/json", purgeJSON));
			request.setReadTimeout(0);

			// Some logging
			OpenAPI.LOGGER.debug("request.getUrl():" + request.getUrl());
			OpenAPI.LOGGER.debug("request.getRequestMethod():" + request.getRequestMethod());

			// Create a new EdgeGrid Signer Object
			GoogleHttpClientEdgeGridRequestSigner requestSigner = new GoogleHttpClientEdgeGridRequestSigner(
					this.getPurgeCredential());
			// Sign the request
			requestSigner.sign(request);
			// send the request to the OPEN API Interface via HTTP POST
			HttpResponse response = request.execute();
			if (response.getStatusCode() == 201)
				purgeExecuted = true;
			// Log the response
			OpenAPI.LOGGER.debug("response.getStatusCode():" + response.getStatusCode());
			OpenAPI.LOGGER.debug("response.parseAsString():" + response.parseAsString());

		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RequestSigningException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return purgeExecuted;
	}

	public boolean doPurgeInvalidateCPCode(String cpcode) {
		return doPurgeInvalidateCPCode(cpcode, false);
	}
	
	public boolean doPurgeInvalidateCPCode(String cpcode, Boolean isStaging) {
		boolean purgeExecuted = false;
		OpenAPI.LOGGER.debug("purge CPCode:\n" + cpcode);
		cpcode = "{\"objects\":["+cpcode+"]}";
		// Use com.google.api.client.http Helper for HTTP Request
		HttpTransport httpTransport = new ApacheHttpTransport();
		HttpRequestFactory requestFactory = httpTransport.createRequestFactory();
		URI uri = null;
		HttpRequest request = null;
		//Get the current Purge API Endpoint
		String purgeCPCodeInvalidateEndpoint = this.getApiPurgeInvalidateCPCodeEndpoint();
		if(isStaging){
			//In case isStaging is TRUE modify Purge API Endpoint to purge staging instead of production
			purgeCPCodeInvalidateEndpoint = purgeCPCodeInvalidateEndpoint.replaceAll("production", "staging");
		}	
		try {
			uri = new URI("https", this.getPurgeHost(), purgeCPCodeInvalidateEndpoint, null, null);
			// Ensure to use Content-Type application/json
			request = requestFactory.buildPostRequest(new GenericUrl(uri),
					ByteArrayContent.fromString("application/json", cpcode));
			request.setReadTimeout(0);

			// Some logging
			OpenAPI.LOGGER.debug("request.getUrl():" + request.getUrl());
			OpenAPI.LOGGER.debug("request.getRequestMethod():" + request.getRequestMethod());

			// Create a new EdgeGrid Signer Object
			GoogleHttpClientEdgeGridRequestSigner requestSigner = new GoogleHttpClientEdgeGridRequestSigner(
					this.getPurgeCredential());
			// Sign the request
			requestSigner.sign(request);
			// send the request to the OPEN API Interface via HTTP POST
			HttpResponse response = request.execute();
			if (response.getStatusCode() == 201)
				purgeExecuted = true;
			// Log the response
			OpenAPI.LOGGER.debug("response.getStatusCode():" + response.getStatusCode());
			OpenAPI.LOGGER.debug("response.parseAsString():" + response.parseAsString());

		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RequestSigningException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return purgeExecuted;

	}	
	

}
