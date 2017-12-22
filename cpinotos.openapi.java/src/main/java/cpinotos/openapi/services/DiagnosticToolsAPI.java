package cpinotos.openapi.services;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;

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


public class DiagnosticToolsAPI extends OpenAPI {

public DiagnosticToolsAPI(String hostname, String edgercFilePath, String apiClientNameSection, boolean debug){
	super(hostname, edgercFilePath, debug);
	this.setApiClientName(apiClientNameSection);
	initApiCredentials();
}

public static void main(String[] args) throws UnknownHostException, UnsupportedEncodingException{
	//Expected IP 23.50.55.45
	System.out.println("2d373217");	
	String ip = getGHostIPfromDebugString("2d373217");
	System.out.println(ip);	
	String hex = getDebugStringforGHostIP(ip);
	System.out.println(hex);
	
}


public static String getGHostIPfromDebugString(String hexStringIp)
{

	//Convert hex string to int
	long intIP = Long.parseLong(hexStringIp, 16);
	System.out.println(intIP);
	//Convert int to InetAddress	
	String ipStr = String.format("%d.%d.%d.%d",
			         (intIP & 0xff),   
			         (intIP >> 8 & 0xff),             
			         (intIP >> 16 & 0xff),    
			         (intIP >> 24 & 0xff));
	return ipStr;
}

public static String getDebugStringforGHostIP(String reqIpAddr){
	String hex = "";
	String[] part = reqIpAddr.split("[\\.,]");
	if (part.length < 4) {
		return "00000000";
	}
	for (int i = 3; i >= 0; i--) {
		int decimal = Integer.parseInt(part[i]);
		if (decimal < 16) // Append a 0 to maintian 2 digits for every
							// number
		{
			hex += "0" + String.format("%01X", decimal);
		} else {
			hex += String.format("%01X", decimal);
		}
	}
	return hex;
}

public String doUrlDebug(String url, String edgeip, ArrayList<String> header) {
	UrlDebug urlDebug = null;
	if(edgeip!=null&&header.isEmpty()){
		urlDebug =  this.urlDebug(url);
	}else if(header.isEmpty()){
		urlDebug =  this.urlDebug(url, edgeip);
	}else{
		urlDebug = this.urlDebug(url, edgeip, header);
	}
	return urlDebug.getUrlDebug().getResponseHeaders().toString();
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
	String apiPath = this.getApiDiagnosticToolsUrlDebugEndpoint()+apiQuery;
	// Use com.google.api.client.http Helper for HTTP Request
	HttpTransport httpTransport = new ApacheHttpTransport();
	HttpRequestFactory requestFactory = httpTransport.createRequestFactory();
	URI uri = null;
	HttpRequest request = null;
	try {
		uri = new URI("https://" + this.getApiHost() + apiPath);		
		// Ensure to use Content-Type application/json
		request = requestFactory.buildGetRequest(new GenericUrl(uri));
		request.setReadTimeout(0);
		// Create a new EdgeGrid Signer Object
		GoogleHttpClientEdgeGridRequestSigner requestSigner = new GoogleHttpClientEdgeGridRequestSigner(
				this.getApiCredential());
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
	String currentApiPapiEndpoint = this.getApiDiagnosticToolsTranslatedErrorEndpoint();
	currentApiPapiEndpoint = currentApiPapiEndpoint.replace("{errorCode}", errorString);
	Gson gson = new Gson();
	return gson.fromJson(this.doEdgeGridAPIRequest(currentApiPapiEndpoint), TranslatedError.class);
}



public LogLines doGetLogLinesFromIP(String ipAddress, String endTime, String arl, String clientIp, String cpCode, String duration, String hostHeader, String httpStatusCode, String logType, String maxLogLines, String objStatus, String requestId, String userAgent){
	String currentApiPapiEndpoint = this.addValueToAPIEndPointURL(this.getApiDiagnosticToolsGetLogLinesFromIPEndpoint(), "ipAddress", ipAddress);
	currentApiPapiEndpoint = this.addValueToAPIEndPointURL(currentApiPapiEndpoint, "endTime", endTime);
	currentApiPapiEndpoint = this.addValueToAPIEndPointURL(currentApiPapiEndpoint, "arl", arl);
	currentApiPapiEndpoint = this.addValueToAPIEndPointURL(currentApiPapiEndpoint, "clientIp", clientIp);
	currentApiPapiEndpoint = this.addValueToAPIEndPointURL(currentApiPapiEndpoint, "cpCode", cpCode);
	currentApiPapiEndpoint = this.addValueToAPIEndPointURL(currentApiPapiEndpoint, "duration", duration);
	currentApiPapiEndpoint = this.addValueToAPIEndPointURL(currentApiPapiEndpoint, "hostHeader", hostHeader);
	currentApiPapiEndpoint = this.addValueToAPIEndPointURL(currentApiPapiEndpoint, "httpStatusCode", httpStatusCode);
	currentApiPapiEndpoint = this.addValueToAPIEndPointURL(currentApiPapiEndpoint, "logType", logType);
	currentApiPapiEndpoint = this.addValueToAPIEndPointURL(currentApiPapiEndpoint, "maxLogLines", maxLogLines);
	currentApiPapiEndpoint = this.addValueToAPIEndPointURL(currentApiPapiEndpoint, "objStatus", objStatus);
	currentApiPapiEndpoint = this.addValueToAPIEndPointURL(currentApiPapiEndpoint, "requestId", requestId);
	currentApiPapiEndpoint = this.addValueToAPIEndPointURL(currentApiPapiEndpoint, "userAgent", userAgent);
	Gson gson = new Gson();
	return gson.fromJson(this.doEdgeGridAPIRequest(currentApiPapiEndpoint), LogLines.class);
}



}
