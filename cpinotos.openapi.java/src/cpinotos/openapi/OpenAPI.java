package cpinotos.openapi;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
//import java.time.Instant;
import java.util.Iterator;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import com.akamai.edgeauth.AkamaiTokenConfig;
//import com.akamai.edgeauth.Algorithm;

import io.github.astinchoi.authtoken.AuthToken;
import io.github.astinchoi.authtoken.AuthTokenBuilder;
import io.github.astinchoi.authtoken.AuthTokenException;

import com.akamai.edgegrid.signer.ClientCredential;
import com.akamai.edgegrid.signer.exceptions.RequestSigningException;
import com.akamai.edgegrid.signer.googlehttpclient.GoogleHttpClientEdgeGridRequestSigner;
import com.akamai.netstorage.DefaultCredential;
import com.akamai.netstorage.NetStorage;
import com.akamai.netstorage.NetStorageException;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ch.qos.logback.classic.Level;
import cpinotos.openapi.netstorage.NetStorageDirResult;
import cpinotos.openapi.netstorage.NetStorageDirResultFile;
import cpinotos.openapi.netstorage.NetStorageDirResultStat;
import cpinotos.openapi.papi.PapiSearchResult;

public class OpenAPI {
	// Instantiate an instance of slf4j logger
	public ch.qos.logback.classic.Logger logger = null;

	// Define all needed attributes
	private String host, netstorageClient, netstorageKey, netstorageHost, purgeClientSecret, purgeHost,
			purgeAccessToken, purgeClientToken, purgeInvalidateEndpoint, purgeInvalidateCPCodeEndpoint, apiClientSecret, apiHost, apiAccessToken,
			apiClientToken, apiPapiSearchEndpoint, apiPapiGetEndpoint, apiPapiGetRuletreeEndpoint;
	// Credential objects needed for the different Akamai OPEN API endpoints
	private ClientCredential purgeCredential, apiCredential;
	private NetStorage netstorage = null;
	
	public OpenAPI(String propertyFilePath, boolean debug) {
		this.logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(OpenAPI.class);
		this.logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		if (debug) {
			this.logger.setLevel(Level.DEBUG);
		} else {
			this.logger.setLevel(Level.INFO);
		}

		Properties properties = propertyFromFileReader(propertyFilePath);

		this.setHost(properties.getProperty("host"));
		this.logger.debug("Host: " + this.getHost());

		this.setNetstorageClient(properties.getProperty("netstorage_client"));
		this.logger.debug("Netstorage Client: " + this.getNetstorageClient());
		this.setNetstorageHost(properties.getProperty("netstorage_host"));
		this.logger.debug("Netstorage Host: " + this.getNetstorageHost());
		this.setNetstorageKey(properties.getProperty("netstorage_key"));
		this.logger.debug("Netstorage Key: " + this.getNetstorageKey());
		this.setPurgeAccessToken(properties.getProperty("purge_access_token"));
		this.logger.debug("Purge Access Token: " + this.getPurgeAccessToken());
		this.setPurgeClientSecret(properties.getProperty("purge_client_secret"));
		this.logger.debug("Purge Client Secret: " + this.getPurgeClientSecret());
		this.setPurgeClientToken(properties.getProperty("purge_client_token"));
		this.logger.debug("Purge Client Token: " + this.getPurgeClientToken());
		this.setPurgeHost(properties.getProperty("purge_host"));
		this.logger.debug("Purge Client Host: " + this.getPurgeHost());
		this.setPurgeInvalidateEndpoint(properties.getProperty("purge_invalidate_endpoint"));
		this.logger.debug("Purge Invalidation Endpoint: " + this.getPurgeInvalidateEndpoint());
		this.setPurgeInvalidateCPCodeEndpoint(properties.getProperty("purge_invalidate_cpcode_endpoint"));
		this.logger.debug("Purge Invalidation Endpoint: " + this.getPurgeInvalidateEndpoint());
		this.setApiClientSecret(properties.getProperty("api_client_secret"));
		this.logger.debug("API Client Secret: " + this.getApiClientSecret());

		this.setApiHost(properties.getProperty("api_host"));
		this.logger.debug("API Host: " + this.getApiHost());

		this.setApiAccessToken(properties.getProperty("api_access_token"));
		this.logger.debug("API Access Token: " + this.getApiAccessToken());

		this.setApiClientToken(properties.getProperty("api_client_token"));
		this.logger.debug("API Client Token: " + this.getApiClientToken());

		this.setApiPapiSearchEndpoint(properties.getProperty("api_papi_search_endpoint"));
		this.logger.debug("API PAPI Search Endpoint: " + this.getApiPapiSearchEndpoint());

		this.setApiPapiGetEndpoint(properties.getProperty("api_papi_get_endpoint"));
		this.logger.debug("API PAPI GET Endpoint: " + this.getApiPapiGetEndpoint());

		this.setApiPapiGetRuletreeEndpoint(properties.getProperty("api_papi_get_ruletree_endpoint"));
		this.logger.debug("API PAPI GET Ruletree Endpoint: " + this.getApiPapiGetRuletreeEndpoint());

		// Create the EdgeGrid credential object
		// NOTE: DEFAULT_MAX_BODY_SIZE_IN_BYTES = 131072;
		this.setPurgeCredential(ClientCredential.builder().accessToken(this.getPurgeAccessToken())
				.clientSecret(this.getPurgeClientSecret()).clientToken(this.getPurgeClientToken())
				.host(this.getPurgeHost()).build());
		this.setApiCredential(
				ClientCredential.builder().accessToken(this.getApiAccessToken()).clientSecret(this.getApiClientSecret())
						.clientToken(this.getApiClientToken()).host(this.getApiHost()).build());
		DefaultCredential dc = new DefaultCredential(this.getNetstorageHost(), this.getNetstorageClient(),
				this.getNetstorageKey());
		this.setNetstorage(new NetStorage(dc));
	}

	
	public boolean doNetstorageMkdir(String path) {
		boolean isCreated = false;	
		try {
			isCreated = this.getNetstorage().mkdir(path);
		} catch (NetStorageException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isCreated;
	}

	public boolean doNetstorageDelete(String path) {
		boolean isDeleted = false;		
		try {
			isDeleted = this.getNetstorage().quickDelete(path);
		} catch (NetStorageException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isDeleted;
	}

	public String getEdgeAuthToken(String path, String encrpytionKey, Integer duration, String tokenName){
		String url = null;
		try {
			  AuthToken at = new AuthTokenBuilder()
			      .key(encrpytionKey)
			      .windowSeconds(duration)
			      .escapeEarly(true)
			      .build();
			  at.setTokenName("token");
			  String token = at.generateURLToken(path);
			  //TODO Find a better way to select the protocol
			  url = String.format("http(s)://%s%s?%s=%s", this.getHost(), path,at.getTokenName(), token);
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

	public String getEdgeAuthKeyFromPapiRuleSet(String papiRuleSetJson) {
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

	public PapiSearchResult searchPAPIConfiguration(String host) {
		PapiSearchResult papiSearchResult = null;
		String searchJSON = "{\"propertyName\":\"" + host + "\"}";

		this.logger.debug("searchPAPIConfiguration():" + searchJSON);
		// Use com.google.api.client.http Helper for HTTP Request
		HttpTransport httpTransport = new ApacheHttpTransport();
		HttpRequestFactory requestFactory = httpTransport.createRequestFactory();
		URI uri = null;
		HttpRequest request = null;
		try {
			uri = new URI("https", this.getApiHost(), this.getApiPapiSearchEndpoint(), null, null);
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
			String papiSearchResultJson = response.parseAsString();
			Gson gson = new Gson();
			papiSearchResult = gson.fromJson(papiSearchResultJson, PapiSearchResult.class);
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
		return papiSearchResult;
	}

	public PapiSearchResult searchPAPIConfiguration() {
		return searchPAPIConfiguration(this.getHost());
	}

	public String getPAPIRuletree(String propertyId, String propertyVersion, String contractId, String groupId,
			boolean validateRules) {
		// Use com.google.api.client.http Helper for HTTP Request
		String papiRuletree = null;
		HttpTransport httpTransport = new ApacheHttpTransport();
		HttpRequestFactory requestFactory = httpTransport.createRequestFactory();
		URI uri = null;
		HttpRequest request = null;
		try {
			String currentApiPapiEndpoint = this.getApiPapiGetRuletreeEndpoint();
			currentApiPapiEndpoint = currentApiPapiEndpoint.replace("{propertyVersion}", propertyVersion);
			currentApiPapiEndpoint = currentApiPapiEndpoint.replace("{propertyId}", propertyId);
			currentApiPapiEndpoint = currentApiPapiEndpoint.replace("{contractId}", contractId);
			currentApiPapiEndpoint = currentApiPapiEndpoint.replace("{groupId}", groupId);
			currentApiPapiEndpoint = currentApiPapiEndpoint.replace("{validateRules}", Boolean.toString(validateRules));

			uri = new URI("https://" + this.getApiHost() + currentApiPapiEndpoint);
			request = requestFactory.buildGetRequest(new GenericUrl(uri));
			request.setReadTimeout(0);
			// Create a new EdgeGrid Signer Object
			GoogleHttpClientEdgeGridRequestSigner requestSigner = new GoogleHttpClientEdgeGridRequestSigner(
					this.getApiCredential());
			// Sign the request
			requestSigner.sign(request);
			// send the request to the OPEN API Interface via HTTP POST
			HttpResponse response = request.execute();
			papiRuletree = response.parseAsString();
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
		return papiRuletree;

	}

	public String getPAPIConfiguration(String propertyId, String contractId, String groupId) {
		String papiGetResultJson = null;
		HttpTransport httpTransport = new ApacheHttpTransport();
		HttpRequestFactory requestFactory = httpTransport.createRequestFactory();
		URI uri = null;
		HttpRequest request = null;
		try {
			String currentApiPapiGetEndpoint = this.getApiPapiGetEndpoint();
			currentApiPapiGetEndpoint = currentApiPapiGetEndpoint.replace("{propertyId}", propertyId);
			currentApiPapiGetEndpoint = currentApiPapiGetEndpoint.replace("{contractId}", contractId);
			currentApiPapiGetEndpoint = currentApiPapiGetEndpoint.replace("{groupId}", groupId);

			uri = new URI("https://" + this.getApiHost() + currentApiPapiGetEndpoint);
			request = requestFactory.buildGetRequest(new GenericUrl(uri));
			request.setReadTimeout(0);
			// Create a new EdgeGrid Signer Object
			GoogleHttpClientEdgeGridRequestSigner requestSigner = new GoogleHttpClientEdgeGridRequestSigner(
					this.getApiCredential());
			// Sign the request
			requestSigner.sign(request);
			// send the request to the OPEN API Interface via HTTP POST
			HttpResponse response = request.execute();
			papiGetResultJson = response.parseAsString();
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
		return papiGetResultJson;
	}

	public boolean doPurgeInvalidate(String purgeJSON) {
		boolean purgeExecuted = false;
		this.logger.debug("purgeJSON:\n" + purgeJSON);
		// Use com.google.api.client.http Helper for HTTP Request
		HttpTransport httpTransport = new ApacheHttpTransport();
		HttpRequestFactory requestFactory = httpTransport.createRequestFactory();
		URI uri = null;
		HttpRequest request = null;
		try {
			uri = new URI("https", this.getPurgeHost(), this.getPurgeInvalidateEndpoint(), null, null);
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
		boolean purgeExecuted = false;
		this.logger.debug("purge CPCode:\n" + cpcode);
		cpcode = "{\"objects\":["+cpcode+"]}";
		// Use com.google.api.client.http Helper for HTTP Request
		HttpTransport httpTransport = new ApacheHttpTransport();
		HttpRequestFactory requestFactory = httpTransport.createRequestFactory();
		URI uri = null;
		HttpRequest request = null;
		try {
			uri = new URI("https", this.getPurgeHost(), this.getPurgeInvalidateCPCodeEndpoint(), null, null);
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
	public NetStorageDirResultStat doNetstorageDir(String dir) {
		String netStorageDirResultJson = null;
		NetStorageDirResult netStorageDirResult = null;
		String responseString = null;
		try {
			InputStream responseStream = this.getNetstorage().dir(dir);
			responseString = inputStreamReader(responseStream);
			netStorageDirResultJson = xml2json(responseString);
			netStorageDirResultJson = netStorageDirResultJson.replaceAll("[\\t\\n\\r\\s]","");
			netStorageDirResultJson = netStorageDirResultJson.replaceFirst("\"file\":\\{","\"file\":\\[\\{");
			netStorageDirResultJson = netStorageDirResultJson.replaceFirst("\\},\"directory\":","\\}\\],\"directory\":");
			Gson gson = new Gson();
			netStorageDirResult = gson.fromJson(netStorageDirResultJson, NetStorageDirResult.class);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NetStorageException e) {
			// TODO Auto-generated catch block
			this.logger.info("doNetstorageDir input is not a directory: " +dir);
			e.printStackTrace();
		}
		// ns.delete("/1234/example.zip");
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return netStorageDirResult.getStat();
	}

	
	public NetStorageDirResultStat doNetstorageDir(String path, boolean isRekursive) {
		String netStorageDirResultJson = null;
		NetStorageDirResult netStorageDirResult = null;
		NetStorageDirResultFile currentFile = null;
		NetStorageDirResultStat currentStat = null;
		NetStorageDirResultStat nextStat = null;
		String currentDir = null;
		String nextDir = null;
		/*
		netStorageDirResultJson = xml2json(doNetstorageDir(path));
		netStorageDirResultJson = netStorageDirResultJson.replaceAll("[\\t\\n\\r\\s]","");
		netStorageDirResultJson = netStorageDirResultJson.replaceFirst("\"file\":\\{","\"file\":\\[\\{");
		netStorageDirResultJson = netStorageDirResultJson.replaceFirst("\\},\"directory\":","\\}\\],\"directory\":");			
		Gson gson = new Gson();
		netStorageDirResult = gson.fromJson(netStorageDirResultJson, NetStorageDirResult.class);
		//this.logger.debug("doNetstorageDir recursive lookup netStorageDirResult: " +netStorageDirResultJson);
			currentStat = netStorageDirResult.getStat();
			*/
		    currentStat = doNetstorageDir(path);
			currentDir = currentStat.getDirectory();
						
		if(isRekursive){
			this.logger.debug("doNetstorageDir recursive lookup currentDir: " +currentDir);

				//look for directories inside the Stats file list
				Iterator<NetStorageDirResultFile> i = currentStat.getFile().iterator();
				while(i.hasNext()){
					currentFile = i.next();
					if(currentFile.getType().equals("dir")){
						nextDir = currentDir+"/"+currentFile.getName();
						//lookup dir
						//this.logger.info("doNetstorageDir recursive lookup nextDir: " +nextDir);
						nextStat = doNetstorageDir(nextDir, true);
						if(nextStat.getFile().size()>0){
							currentFile.setFile(nextStat.getFile());	
						}
						
					}
				}				
		}
		Gson gsonBuilder = new GsonBuilder().setPrettyPrinting().create();
		this.logger.debug("doNetstorageDir recursive lookup gson.toJson(currentStat): " +gsonBuilder.toJson(currentStat));
		return currentStat;
	}
	
	public String doNetstorageDu(String path) {
		String responseString = null;
		try {
			InputStream responseStream = this.getNetstorage().du(path);
			responseString = inputStreamReader(responseStream);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NetStorageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// ns.delete("/1234/example.zip");
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return responseString;
	}

	public String doNetstorageDu(String path, boolean isJson) {
		return xml2json(doNetstorageDu(path));
	}

	public boolean doNetstorageDownload(String pathNetstorage, String pathLocal) {
		boolean isDone = false;
		try (InputStream responseStream = this.getNetstorage().download(pathNetstorage)){

			File targetFile = new File(pathLocal);
			try (OutputStream outStream = new FileOutputStream(targetFile)){
	    	    byte[] buffer = new byte[8 * 1024];
	    	    int bytesRead;
	    	    while ((bytesRead = responseStream.read(buffer)) != -1) {
	    	        outStream.write(buffer, 0, bytesRead);
	    	    }				
			}
			isDone = true;
			this.logger.debug("ns.download(" + pathNetstorage + " , "+pathLocal+")");
		} catch (NetStorageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isDone;
	}
	
	

	public boolean doNetstorageUpload(String path, String file) {
		boolean uploadResult = false;
		Integer tryCounter = 0;
		while(tryCounter<3){
			try {
				tryCounter++;
				InputStream stream = fileReader(file);
				uploadResult = this.getNetstorage().upload(path, stream);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				this.logger.info("IOException, need to retry");
				e.printStackTrace();
				tryCounter++;
			} catch (com.akamai.netstorage.NetStorageException e){
				// TODO Need to figure out if we need to retry
				this.logger.info("NetStorageException, need to retry");
				e.printStackTrace();
				tryCounter++;
			} finally{
				if(uploadResult){
					tryCounter=3;
				}
			}			
		}
		this.logger.debug("ns.upload(" + path + "):" + uploadResult);
		return uploadResult;
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

	private InputStream fileReader(String path) {
		File file = new File(path);
		FileInputStream fis = null;

		try {
			fis = new FileInputStream(file);
			this.logger.debug("filezise(" + path + "):" + fis.available());
		} catch (IOException e) {
			e.printStackTrace();
		}
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

	private static String xml2json(String xml) {
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



	public NetStorage getNetstorage() {
		return netstorage;
	}

	public void setNetstorage(NetStorage netstorage) {
		this.netstorage = netstorage;
	}

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

	public String getPurgeInvalidateEndpoint() {
		return purgeInvalidateEndpoint;
	}

	public void setPurgeInvalidateEndpoint(String purgeInvalidateEndpoint) {
		this.purgeInvalidateEndpoint = purgeInvalidateEndpoint;
	}

	public ClientCredential getPurgeCredential() {
		return purgeCredential;
	}

	public void setPurgeCredential(ClientCredential purgeCredential) {
		this.purgeCredential = purgeCredential;
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

	public String getApiPapiSearchEndpoint() {
		return apiPapiSearchEndpoint;
	}

	public void setApiPapiSearchEndpoint(String apiPapiSearchEndpoint) {
		this.apiPapiSearchEndpoint = apiPapiSearchEndpoint;
	}

	public ClientCredential getApiCredential() {
		return apiCredential;
	}

	public void setApiCredential(ClientCredential apiCredential) {
		this.apiCredential = apiCredential;
	}

	public String getApiPapiGetEndpoint() {
		return apiPapiGetEndpoint;
	}

	public void setApiPapiGetEndpoint(String apiPapiGetEndpoint) {
		this.apiPapiGetEndpoint = apiPapiGetEndpoint;
	}

	public String getApiPapiGetRuletreeEndpoint() {
		return apiPapiGetRuletreeEndpoint;
	}

	public void setApiPapiGetRuletreeEndpoint(String apiPapiGetRuletreeEndpoint) {
		this.apiPapiGetRuletreeEndpoint = apiPapiGetRuletreeEndpoint;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPurgeInvalidateCPCodeEndpoint() {
		return purgeInvalidateCPCodeEndpoint;
	}

	public void setPurgeInvalidateCPCodeEndpoint(String purgeInvalidateCPCodeEndpoint) {
		this.purgeInvalidateCPCodeEndpoint = purgeInvalidateCPCodeEndpoint;
	}
}