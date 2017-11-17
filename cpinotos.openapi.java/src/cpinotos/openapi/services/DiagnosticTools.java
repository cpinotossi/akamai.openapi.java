package cpinotos.openapi.services;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.TimeZone;

import org.joda.time.format.DateTimeFormat;

import com.akamai.edgegrid.signer.exceptions.RequestSigningException;
import com.akamai.edgegrid.signer.googlehttpclient.GoogleHttpClientEdgeGridRequestSigner;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.gson.Gson;

import cpinotos.openapi.OpenAPI;
import cpinotos.openapi.services.data.LogLines;
import cpinotos.openapi.services.data.TranslatedError;
import cpinotos.openapi.services.data.UrlDebug;


public class DiagnosticTools {
private OpenAPI openAPI;

	public DiagnosticTools(OpenAPI openAPI){
		this.openAPI=openAPI;
}


public UrlDebug urlDebug(String url){
	//TODO Maybe we should allow different search parameters
	String apiQuery = String.format("url=%s",url); 
	return doUrlDebug(apiQuery);
}

public UrlDebug urlDebug(String url, String edgeIP){
	//TODO Maybe we should allow different search parameters
	String apiQuery = String.format("?url=%s&edgeIp=%s",url,edgeIP); 
	return doUrlDebug(apiQuery);
}
public UrlDebug urlDebug(String url, String edgeIP, ArrayList<String> headerList){
	String apiQuery = String.format("?url=%s&edgeIp=%s",url,edgeIP); 
	Iterator<String> i = headerList.iterator();
	while(i.hasNext()){
		apiQuery.concat("&header="+i.next());
	}
	return doUrlDebug(apiQuery);
}

public UrlDebug doUrlDebug(String apiQuery){
	UrlDebug urlDebugResult = null;
	String apiPath = this.openAPI.getApiDiagnosticToolsUrlDebugEndpoint()+apiQuery;
	// Use com.google.api.client.http Helper for HTTP Request
	HttpTransport httpTransport = new ApacheHttpTransport();
	HttpRequestFactory requestFactory = httpTransport.createRequestFactory();
	URI uri = null;
	HttpRequest request = null;
	try {
		uri = new URI("https://" + openAPI.getApiHost() + apiPath);		
		// Ensure to use Content-Type application/json
		request = requestFactory.buildGetRequest(new GenericUrl(uri));
		request.setReadTimeout(0);
		// Create a new EdgeGrid Signer Object
		GoogleHttpClientEdgeGridRequestSigner requestSigner = new GoogleHttpClientEdgeGridRequestSigner(
				openAPI.getApiCredential());
		// Sign the request
		requestSigner.sign(request);
		// send the request to the OPEN API Interface via HTTP POST
		HttpResponse response = request.execute();
		String diagnosticToolsUrlDebugResultJson = response.parseAsString();
		Gson gson = new Gson();
		urlDebugResult = gson.fromJson(diagnosticToolsUrlDebugResultJson, UrlDebug.class);
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
	return urlDebugResult;
}

public TranslatedError doTranslateError(String errorString){
	String currentApiPapiEndpoint = this.openAPI.getApiDiagnosticToolsTranslatedErrorEndpoint();
	currentApiPapiEndpoint = currentApiPapiEndpoint.replace("{errorCode}", errorString);
	Gson gson = new Gson();
	return gson.fromJson(this.openAPI.doEdgeGridAPIRequest(currentApiPapiEndpoint), TranslatedError.class);
}

public LogLines doGetLogLinesFromIP(String ipAddress, String endTime, String arl, String clientIp, String cpCode, String duration, String hostHeader, String httpStatusCode, String logType, String maxLogLines, String objStatus, String requestId, String userAgent){
	String currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(this.openAPI.getApiDiagnosticToolsGetLogLinesFromIPEndpoint(), "ipAddress", ipAddress);
	currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(currentApiPapiEndpoint, "endTime", endTime);
	currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(currentApiPapiEndpoint, "arl", arl);
	currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(currentApiPapiEndpoint, "clientIp", clientIp);
	currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(currentApiPapiEndpoint, "cpCode", cpCode);
	currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(currentApiPapiEndpoint, "duration", duration);
	currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(currentApiPapiEndpoint, "hostHeader", hostHeader);
	currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(currentApiPapiEndpoint, "httpStatusCode", httpStatusCode);
	currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(currentApiPapiEndpoint, "logType", logType);
	currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(currentApiPapiEndpoint, "maxLogLines", maxLogLines);
	currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(currentApiPapiEndpoint, "objStatus", objStatus);
	currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(currentApiPapiEndpoint, "requestId", requestId);
	currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(currentApiPapiEndpoint, "userAgent", userAgent);
	Gson gson = new Gson();
	return gson.fromJson(this.openAPI.doEdgeGridAPIRequest(currentApiPapiEndpoint), LogLines.class);
}



}
