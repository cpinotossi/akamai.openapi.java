package cpinotos.openapi;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.ini4j.Wini;
import org.json.JSONException;
import org.json.JSONObject;
//import org.json.XML;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.json.XML;

import com.akamai.edgegrid.signer.ClientCredential;
import com.akamai.edgegrid.signer.exceptions.RequestSigningException;
import com.akamai.edgegrid.signer.googlehttpclient.GoogleHttpClientEdgeGridRequestSigner;
import com.akamai.netstorage.DefaultCredential;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import cpinotos.openapi.services.DiagnosticToolsAPI;
import cpinotos.openapi.services.data.UrlDebug;

//import com.akamai.edgeauth.AkamaiTokenConfig;
//import com.akamai.edgeauth.Algorithm;

import io.github.astinchoi.authtoken.AuthToken;
import io.github.astinchoi.authtoken.AuthTokenBuilder;
import io.github.astinchoi.authtoken.AuthTokenException;
import lombok.Getter;
import lombok.Setter;

/*
 * TODO use lombok
 * TODO split into smaller pieces/classes. Single Responsibility Pattern.
 * TODO Use more interfaces
 * TODO Zip and Unzip for large Netstorage Uploads
 * TODO reactive streams, akka framework https://www.youtube.com/watch?v=qaiwalDyayA&t=2132s
 */
public class OpenAPI {
	// Instantiate an instance of slf4j logger
	//public Logger logger = null;
	@Getter @Setter
	public static org.apache.log4j.Logger logger;

	// Define all needed attributes
	private String netstorageClient, netstorageKey, netstorageHost, purgeClientSecret, purgeHost,
			purgeAccessToken, purgeClientToken, apiClientSecret, apiHost, apiAccessToken,
			apiClientToken;
	
	@Getter @Setter private String host;
	@Getter @Setter private String apiClientName;
	@Getter @Setter private String apiClientPurgeName;
	@Getter @Setter private String apiPurgeInvalidateEndpoint;
	@Getter @Setter private String apiPurgeInvalidateCPCodeEndpoint;
	@Getter @Setter private String apiPurgeInvalidateTagEndpoint;
	@Getter @Setter private String apiPapiSearchEndpoint;
	@Getter @Setter private String apiPapiGetEndpoint;
	@Getter @Setter private String apiDiagnosticToolsUrlDebugEndpoint;
	@Getter @Setter private String apiPapiGetRuletreeEndpoint;
	@Getter @Setter private String apiDiagnosticToolsTranslatedErrorEndpoint;
	@Getter @Setter private String apiDiagnosticToolsGetLogLinesFromIPEndpoint;
	@Getter @Setter private String apiPapiListCPCodesEndpoint;
	@Getter @Setter private String apiPapiCreateCPCodesEndpoint;
	@Getter @Setter private String apiPapiListProductsEndpoint;
	
	// Credential objects needed for the different Akamai OPEN API endpoints
	@Getter @Setter private ClientCredential purgeCredential;
	@Getter @Setter private ClientCredential apiCredential;
	@Getter @Setter private DefaultCredential netstorageCredential;
	
//	private NetStorage netstorage = null;
	
	public OpenAPI(String propertyFilePath, boolean debug) {
		this.logger = Logger.getLogger(OpenAPI.class);	
		//this.logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(OpenAPI.class);
		//this.logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);

		/*
		if (debug) {
			this.logger.setLevel(Level.DEBUG);
		} else {
			this.logger.setLevel(Level.INFO);
		}
		*/

		Wini ini = null;
		try {
			ini = new Wini(new File(propertyFilePath));
			//api-endpoints
			this.setApiPurgeInvalidateEndpoint(ini.get("api-endpoints","apiPurgeInvalidateEndpoint"));			
			this.setApiPurgeInvalidateCPCodeEndpoint(ini.get("api-endpoints","apiPurgeInvalidateCPCodeEndpoint"));
			this.setApiPapiSearchEndpoint(ini.get("api-endpoints","apiPapiSearchEndpoint"));
			this.setApiPapiGetEndpoint(ini.get("api-endpoints","apiPapiGetEndpoint"));
			this.setApiPapiGetRuletreeEndpoint(ini.get("api-endpoints","apiPapiGetRuletreeEndpoint"));
			this.setApiDiagnosticToolsUrlDebugEndpoint(ini.get("api-endpoints","apiDiagnosticToolsUrlDebugEndpoint"));
			this.setApiDiagnosticToolsTranslatedErrorEndpoint(ini.get("api-endpoints","apiDiagnosticToolsTranslatedErrorEndpoint"));
			this.setApiDiagnosticToolsGetLogLinesFromIPEndpoint(ini.get("api-endpoints","apiDiagnosticToolsGetLogLinesFromIPEndpoint"));
			this.setApiPapiListCPCodesEndpoint(ini.get("api-endpoints","apiPapiListCPCodesEndpoint"));
			this.setApiPapiCreateCPCodesEndpoint(ini.get("api-endpoints","apiPapiCreateCPCodesEndpoint"));
			this.setApiPapiListProductsEndpoint(ini.get("api-endpoints","apiPapiListProductsEndpoint"));
			
			
			
			//nestorage-upload-account
			this.setNetstorageClient(ini.get("nestorage-upload-account","client"));
			this.logger.debug("Netstorage Client: " + this.getNetstorageClient());
			this.setNetstorageHost(ini.get("nestorage-upload-account","host"));
			this.logger.debug("Netstorage Host: " + this.getNetstorageHost());
			this.setNetstorageKey(ini.get("nestorage-upload-account","key"));
			this.logger.debug("Netstorage Key: " + this.getNetstorageKey());
			
			//general
			this.setHost(ini.get("general", "host"));
			this.logger.debug("Host: " + this.getHost());

			this.setApiClientName(ini.get("general", "api-client"));
			this.setApiClientPurgeName(ini.get("general", "api-client-purge"));
			this.logger.debug("Host: " + this.getHost());
			
			if(!ini.get("general", "edgerc-file").isEmpty()){
				ini = new Wini(new File(ini.get("general", "edgerc-file")));
			}
			
			//akamai-api-purge
			if(!ini.containsKey(this.getApiClientPurgeName())){
				this.logger.info("Credentials section akamai-api-purge is missing");
				System.exit(1);
			}
			this.setPurgeAccessToken(ini.get(this.getApiClientPurgeName(),"access_token"));
			this.logger.debug("Purge Access Token: " + this.getPurgeAccessToken());
			this.setPurgeClientSecret(ini.get(this.getApiClientPurgeName(),"client_secret"));
			this.logger.debug("Purge Client Secret: " + this.getPurgeClientSecret());
			this.setPurgeClientToken(ini.get(this.getApiClientPurgeName(),"client_token"));
			this.logger.debug("Purge Client Token: " + this.getPurgeClientToken());
			this.setPurgeHost(ini.get(this.getApiClientPurgeName(),"host"));
			this.logger.debug("Purge Client Host: " + this.getPurgeHost());
				
			//akamai-api
			if(!ini.containsKey(this.getApiClientName())){
				this.logger.info("Credentials section akamai-api is missing");
				System.exit(1);
			}
			this.setApiClientSecret(ini.get(this.getApiClientName(),"client_secret"));
			this.logger.debug("API Client Secret: " + this.getApiClientSecret());
			this.setApiHost(ini.get(this.getApiClientName(),"host"));
			this.logger.debug("API Host: " + this.getApiHost());
			this.setApiAccessToken(ini.get(this.getApiClientName(),"access_token"));
			this.logger.debug("API Access Token: " + this.getApiAccessToken());
			this.setApiClientToken(ini.get(this.getApiClientName(),"client_token"));
			this.logger.debug("API Client Token: " + this.getApiClientToken());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Create the EdgeGrid credential object
		// NOTE: DEFAULT_MAX_BODY_SIZE_IN_BYTES = 131072;
		this.setPurgeCredential(ClientCredential.builder().accessToken(this.getPurgeAccessToken())
				.clientSecret(this.getPurgeClientSecret()).clientToken(this.getPurgeClientToken())
				.host(this.getPurgeHost()).build());
		this.setApiCredential(
				ClientCredential.builder().accessToken(this.getApiAccessToken()).clientSecret(this.getApiClientSecret())
						.clientToken(this.getApiClientToken()).host(this.getApiHost()).build());
		this.netstorageCredential = new DefaultCredential(this.getNetstorageHost(), this.getNetstorageClient(),
				this.getNetstorageKey());
	}


	public String getEdgeAuthToken(String path, String encrpytionKey, Integer duration, String tokenName, Integer startTime){
		String url = null;
		try {
			  AuthToken at = new AuthTokenBuilder()
			      .key(encrpytionKey)
			      .windowSeconds(duration)
			      .escapeEarly(false)
			      .startTime(startTime)
			      .build();
			  at.setTokenName("token");
			  String token = at.generateURLToken(path);
			  //TODO Find a better way to select the protocol
			  url = String.format("http://%s%s?%s=%s", this.getHost(), path,at.getTokenName(), token);
			  // => Link or Request "url" /w Query string
			} catch (AuthTokenException e) {
			  e.printStackTrace();
			}
		return url;
	}

	/*
	public static AkamaiTokenConfig GenerateConfig(String url, String key, long window) throws Exception {
		AkamaiTokenConfig config = new AkamaiTokenConfig();
		
		config.setAlgo(Algorithm.SHA256);
		// key
		config.setKey(key);
		// start_time
		//config.setStartTime(Instant.now().getEpochSecond());
		config.setStartTime(System.currentTimeMillis()/1000);
		// window
		config.setWindow(window);
		// url
		config.setAcl(url);
		config.setIsUrl(true);
		return config;
	}
	*/

	private String getEdgeAuthFromPapiRuleSet(String papiRuleSetJson) {
		String papiRuleSetJsonNew = papiRuleSetJson.replaceAll("\\r?\\n?\\s", "");
		this.logger.debug("papiRuleSetJson:" + papiRuleSetJsonNew);
		String edgeauth = null;
		// String pattern =
		// "\\{\"name\":\"verifyTokenAuthorization\"[^\\}\\}]*";
		// String pattern = ".*verifyTokenAuthorization.*";
		String pattern = "verifyTokenAuthorization.*";

		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(papiRuleSetJsonNew);
		if (m.matches()) {
			this.logger.debug("m1.group().length():" + m.group().length());
			this.logger.debug("m1.group(1):" + m.group(0));
			edgeauth = m.group(0);
		}
		return edgeauth;
	}

	public String getEdgeAuthKeyFromPapiRuleSet_(String papiRuleSetJson) {
		String edgeauthKey = null;
		String pattern = "^.*key\".:.\"(.*)\",.*failureResponse.*";
		// String pattern = "^.*key\".:.\"(.*)\",.*";
		Pattern r = Pattern.compile(pattern, Pattern.DOTALL);
		Matcher m = r.matcher(papiRuleSetJson);
		if (m.matches()) {
			this.logger.debug("m.group().length():" + m.group().length());
			this.logger.debug("m.group(1):" + m.group(1));
			edgeauthKey = m.group(1);
		}
		return edgeauthKey;

	}
	public String getEdgeAuthKeyFromPapiRuleSet(String papiRuleSetJson) {
		
		String edgeauthKey = null;
		//Try to find inside Advanced Section
		try{
			String pattern = "<key>(.+?)</key>";
			Pattern r = Pattern.compile(pattern, Pattern.DOTALL);
			Matcher m = r.matcher(papiRuleSetJson);
			m.find();
			edgeauthKey = m.group(1);
			this.logger.debug("m.group(1):" + m.group(1));			
		}catch(java.lang.IllegalStateException e){
			//Try to find inside JSON
			String pattern = ".*\"key\".:.\"(.+?)\",.*";
			Pattern r = Pattern.compile(pattern, Pattern.DOTALL);
			Matcher m = r.matcher(papiRuleSetJson);
			m.find();
			edgeauthKey = m.group(1);
			this.logger.debug("m.group(1):" + m.group(1));
		}

		return edgeauthKey;
	}

	public String getEdgeAuthLocationFromPapiRuleSet(String papiRuleSetJson) {
		String edgeauthKey = null;
		String pattern = "^.*locationId\".:.\"(.*)\",.*key.*";
		Pattern r = Pattern.compile(pattern, Pattern.DOTALL);
		Matcher m = r.matcher(papiRuleSetJson);
		if (m.matches()) {
			this.logger.debug("m.group().length():" + m.group().length());
			this.logger.debug("m.group(1):" + m.group(1));
			edgeauthKey = m.group(1);
		}
		return edgeauthKey;
	}


	public boolean doPurgeInvalidate(String purgeJSON) {
		return doPurgeInvalidate(purgeJSON, false);
	}
	
	public boolean doPurgeInvalidate(String purgeJSON, Boolean isStaging) {
		boolean purgeExecuted = false;
		this.logger.debug("purgeJSON:\n" + purgeJSON);
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
			this.logger.debug("request.getUrl():" + request.getUrl());
			this.logger.debug("request.getRequestMethod():" + request.getRequestMethod());

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
			this.logger.debug("response.getStatusCode():" + response.getStatusCode());
			this.logger.debug("response.parseAsString():" + response.parseAsString());

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
		this.logger.debug("purge CPCode:\n" + cpcode);
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
			this.logger.debug("request.getUrl():" + request.getUrl());
			this.logger.debug("request.getRequestMethod():" + request.getRequestMethod());

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
			this.logger.debug("response.getStatusCode():" + response.getStatusCode());
			this.logger.debug("response.parseAsString():" + response.parseAsString());

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
		
	public static String jsonFileReader(String path) throws IOException {
		String content = null;
		try {
			byte[] encoded = Files.readAllBytes(Paths.get(path));
			content = new String(encoded, StandardCharsets.UTF_8);

		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return content;
	}

	public InputStream fileReader(String path) throws Exception{
		File file = new File(path);
		FileInputStream fis = null;
		fis = new FileInputStream(file);
		this.logger.debug("filezise(" + path + "):" + fis.available());
		return fis;
	}

	public static String inputStreamReader(InputStream is) throws IOException {
		String response = null;
		BufferedInputStream bis = new BufferedInputStream(is);
		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		int result = bis.read();
		while (result != -1) {
			buf.write((byte) result);
			result = bis.read();
		}
		// StandardCharsets.UTF_8.name() > JDK 7
		try {
			response = buf.toString("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}


	public static String xml2json(String xml) {
		String json = null;
		try {
			int PRETTY_PRINT_INDENT_FACTOR = 4;
			JSONObject xmlJSONObj = XML.toJSONObject(xml);
			json = xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);
		} catch (JSONException je) {
			System.out.println(je.toString());
		}
		return json;
	}

	private static Properties propertyFromFileReader(String propFileName) {
		Properties prop = new Properties();
		File file = new File(propFileName);
		InputStream input = null;

		try {

			input = new FileInputStream(file);
			// load a properties file
			prop.load(input);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return prop;
	}


/*
	public NetStorage getNetstorage() {
		return netstorage;
	}

	public void setNetstorage(NetStorage netstorage) {
		this.netstorage = netstorage;
	}
*/
	public String getNetstorageClient() {
		return netstorageClient;
	}

	public void setNetstorageClient(String netstorageClient) {
		this.netstorageClient = netstorageClient;
	}

	public String getNetstorageKey() {
		return netstorageKey;
	}

	public void setNetstorageKey(String netstorageKey) {
		this.netstorageKey = netstorageKey;
	}

	public String getNetstorageHost() {
		return netstorageHost;
	}

	public void setNetstorageHost(String netstorageHost) {
		this.netstorageHost = netstorageHost;
	}

	public String getPurgeClientSecret() {
		return purgeClientSecret;
	}

	public void setPurgeClientSecret(String purgeClientSecret) {
		this.purgeClientSecret = purgeClientSecret;
	}

	public String getPurgeHost() {
		return purgeHost;
	}

	public void setPurgeHost(String purgeHost) {
		this.purgeHost = purgeHost;
	}

	public String getPurgeAccessToken() {
		return purgeAccessToken;
	}

	public void setPurgeAccessToken(String purgeAccessToken) {
		this.purgeAccessToken = purgeAccessToken;
	}

	public String getPurgeClientToken() {
		return purgeClientToken;
	}

	public void setPurgeClientToken(String purgeClientToken) {
		this.purgeClientToken = purgeClientToken;
	}



	public String getApiClientSecret() {
		return apiClientSecret;
	}

	public void setApiClientSecret(String apiClientSecret) {
		this.apiClientSecret = apiClientSecret;
	}

	public String getApiHost() {
		return apiHost;
	}

	public void setApiHost(String apiHost) {
		this.apiHost = apiHost;
	}

	public String getApiAccessToken() {
		return apiAccessToken;
	}

	public void setApiAccessToken(String apiAccessToken) {
		this.apiAccessToken = apiAccessToken;
	}

	public String getApiClientToken() {
		return apiClientToken;
	}

	public void setApiClientToken(String apiClientToken) {
		this.apiClientToken = apiClientToken;
	}






	public String doUrlDebug(String url, String edgeip, ArrayList<String> header) {
		DiagnosticToolsAPI dt = new DiagnosticToolsAPI(this);
		UrlDebug urlDebug = null;
		if(edgeip!=null&&header.isEmpty()){
			urlDebug =  dt.urlDebug(url);
		}else if(header.isEmpty()){
			urlDebug =  dt.urlDebug(url, edgeip);
		}else{
			urlDebug = dt.urlDebug(url, edgeip, header);
		}
		return urlDebug.getUrlDebug().getResponseHeaders().toString();
	}
	
	public String doEdgeGridAPIRequest(String apiRequestUrl) {
		String jsonResult = null;
		// Use com.google.api.client.http Helper for HTTP Request
		HttpTransport httpTransport = new ApacheHttpTransport();
		HttpRequestFactory requestFactory = httpTransport.createRequestFactory();
		URI uri = null;
		HttpRequest request = null;
		try {
			uri = new URI("https://" + this.getApiHost() + apiRequestUrl);		
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
			jsonResult = response.parseAsString();
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
		return jsonResult;
	}
	
	public String doEdgeGridAPIRequestPOST(String apiRequestUrl, String searchJSON) {
		String jsonResult = null;
		// Use com.google.api.client.http Helper for HTTP Request
		HttpTransport httpTransport = new ApacheHttpTransport();
		HttpRequestFactory requestFactory = httpTransport.createRequestFactory();
		URI uri = null;
		HttpRequest request = null;
		try {
			uri = new URI("https://" + this.getApiHost() + apiRequestUrl);		
			// Ensure to use Content-Type application/json
			request = requestFactory.buildPostRequest(new GenericUrl(uri),
					ByteArrayContent.fromString("application/json", searchJSON));
			request.setReadTimeout(0);
			// Create a new EdgeGrid Signer Object
			GoogleHttpClientEdgeGridRequestSigner requestSigner = new GoogleHttpClientEdgeGridRequestSigner(
					this.getApiCredential());
			// Sign the request
			requestSigner.sign(request);
			// send the request to the OPEN API Interface via HTTP POST
			HttpResponse response = request.execute();
			jsonResult = response.parseAsString();
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
		return jsonResult;
	}
	
	public static String addValueToAPIEndPointURL(String apiEndPointURL, String key, String value){
		if(value.isEmpty()){
			apiEndPointURL = apiEndPointURL.replace("/{"+key+"}", "/");
			apiEndPointURL = apiEndPointURL.replace("&{"+key+"}", "");			
		}else{
			apiEndPointURL = apiEndPointURL.replace("/{"+key+"}", "/"+value);
			apiEndPointURL = apiEndPointURL.replace("&{"+key+"}", "&"+key+"="+value);			
		}
		return apiEndPointURL;
	}
	
}