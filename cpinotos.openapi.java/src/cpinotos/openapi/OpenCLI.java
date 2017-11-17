package cpinotos.openapi;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.TimeZone;

//import com.akamai.edgeauth.AkamaiTokenConfig;
//import com.akamai.edgeauth.AkamaiTokenGenerator;
import com.beust.jcommander.JCommander;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ch.qos.logback.classic.Level;
import cpinotos.openapi.cli.CommandDU;
import cpinotos.openapi.cli.CommandDelete;
import cpinotos.openapi.cli.CommandDir;
import cpinotos.openapi.cli.CommandDownload;
import cpinotos.openapi.cli.CommandEdgeurl;
import cpinotos.openapi.cli.CommandGetLogLinesByIP;
import cpinotos.openapi.cli.CommandMkdir;
import cpinotos.openapi.cli.CommandPurge;
import cpinotos.openapi.cli.CommandPurgeCPCode;
import cpinotos.openapi.cli.CommandTranslatedError;
import cpinotos.openapi.cli.CommandUpload;
import cpinotos.openapi.cli.CommandUrlDebug;
import cpinotos.openapi.cli.Commands;
import cpinotos.openapi.netstorage.NetStorageDirResultStat;
import cpinotos.openapi.papi.PapiSearchResult;
import cpinotos.openapi.papi.PapiSearchResultItem;
import cpinotos.openapi.services.DiagnosticTools;
import cpinotos.openapi.services.data.LogLines;
import cpinotos.openapi.services.data.TranslatedError;

public class OpenCLI {

	@SuppressWarnings("static-access")
	public static void main(String[] args) throws Exception {
		if (null == args || args.length == 0) {
			System.out.println("try '--help' or '-h' for more information");
			System.exit(1);
		}
		// Read user input parameter.
		Commands commands = new cpinotos.openapi.cli.Commands();
		CommandUpload cmdUpload = new cpinotos.openapi.cli.CommandUpload();
		CommandDownload cmdDownload = new cpinotos.openapi.cli.CommandDownload();
		CommandDU cmdDu = new cpinotos.openapi.cli.CommandDU();
		CommandDir cmdDir = new cpinotos.openapi.cli.CommandDir();
		CommandDelete cmdDelete = new cpinotos.openapi.cli.CommandDelete();
		CommandMkdir cmdMkdir = new cpinotos.openapi.cli.CommandMkdir();
		CommandEdgeurl cmdEdgeurl = new cpinotos.openapi.cli.CommandEdgeurl();
		CommandPurge cmdPurge = new cpinotos.openapi.cli.CommandPurge();
		CommandPurgeCPCode cmdPurgeCPCode = new cpinotos.openapi.cli.CommandPurgeCPCode();
		CommandUrlDebug cmdUrlDebug = new CommandUrlDebug();
		CommandTranslatedError cmdTranslateError = new CommandTranslatedError();
		CommandGetLogLinesByIP cmdGetLogLinesByIP = new CommandGetLogLinesByIP();
		JCommander jc = new JCommander();
		jc.addObject(commands);
		jc.addCommand("du", cmdDu);
		jc.addCommand("dir", cmdDir);
		jc.addCommand("mkdir", cmdMkdir);
		jc.addCommand("upload", cmdUpload);
		jc.addCommand("download", cmdDownload);
		jc.addCommand("delete", cmdDelete);
		jc.addCommand("edgeurl", cmdEdgeurl);
		jc.addCommand("invalidate", cmdPurge);
		jc.addCommand("invalidate_cpcode", cmdPurgeCPCode);
		jc.addCommand("url_debug",cmdUrlDebug);
		jc.addCommand("translate_error",cmdTranslateError);
		jc.addCommand("logs_by_ip", cmdGetLogLinesByIP);
	    try {
	        jc.parse(args);
	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	        jc.usage();
	        System.exit(1);
	    }
	    if (commands.help || jc.getParsedCommand() == null) {
	        jc.usage();
	        System.exit(0);
	    }
        String currentCmd = jc.getParsedCommand();
		// Instantiate an instance of
		OpenAPI openAPI = new OpenAPI(commands.config, commands.verbose);
	 
		if (commands.verbose) {
			openAPI.logger.setLevel(Level.DEBUG);
		} else {
			openAPI.logger.setLevel(Level.INFO);
		}
		openAPI.logger.debug("Host: " + openAPI.getHost());
		openAPI.logger.debug("Netstorage Client: " + openAPI.getNetstorageClient());
		openAPI.logger.debug("Netstorage Host: " + openAPI.getNetstorageHost());
		openAPI.logger.debug("Netstorage Key: " + openAPI.getNetstorageKey());
		openAPI.logger.debug("Purge Access Token: " + openAPI.getPurgeAccessToken());
		openAPI.logger.debug("Purge Client Secret: " + openAPI.getPurgeClientSecret());
		openAPI.logger.debug("Purge Client Token: " + openAPI.getPurgeClientToken());
		openAPI.logger.debug("Purge Client Host: " + openAPI.getPurgeHost());
		openAPI.logger.debug("Purge Invalidation Endpoint: " + openAPI.getApiPurgeInvalidateEndpoint());
		openAPI.logger.debug("API Client Secret: " + openAPI.getApiClientSecret());
		openAPI.logger.debug("API Host: " + openAPI.getApiHost());
		openAPI.logger.debug("API Access Token: " + openAPI.getApiAccessToken());
		openAPI.logger.debug("API Client Token: " + openAPI.getApiClientToken());
		openAPI.logger.debug("API PAPI Search Endpoint: " + openAPI.getApiPapiSearchEndpoint());
		openAPI.logger.debug("API PAPI GET Endpoint: " + openAPI.getApiPapiGetEndpoint());
		openAPI.logger.debug("API PAPI GET Ruletree Endpoint: " + openAPI.getApiPapiGetRuletreeEndpoint());

		switch (currentCmd) {
		case "mkdir":
			openAPI.logger.info("Start mkdir:");
			openAPI.logger.info("Netstorage Location (out):" + cmdMkdir.out);
			openAPI.logger.info("Netstorage created:" + openAPI.doNetstorageMkdir(cmdMkdir.out));
			openAPI.logger.debug("done");
			break; // optional
		case "upload":
			openAPI.logger.info("Start upload:");
			openAPI.logger.info("Local File (in):" + cmdUpload.in);
			openAPI.logger.info("Netstorage Location (out):" + cmdUpload.out);
			openAPI.doNetstorageUpload(cmdUpload.out, cmdUpload.in);
			openAPI.logger.debug("done");
			break; // optional
		case "download":
			openAPI.logger.info("Start download:");
			openAPI.logger.info("settings.downloadpath:" + cmdDownload.out);
			boolean isDownloadOk = openAPI.doNetstorageDownload(cmdDownload.out, cmdDownload.in);
			openAPI.logger.info("download Response:" + isDownloadOk);
			openAPI.logger.info("done");
			break; // optional
		case "edgeurl":
			openAPI.logger.info("Start edgeurl:");
			openAPI.logger.info("edgeURL:step1/7: found Property Configuration for Hostname " + openAPI.getHost());
			PapiSearchResult psr = openAPI.searchPAPIConfiguration();
			//TODO Handle exception no config for provided hostname
			PapiSearchResultItem psri = psr.getVersions().getItems().get(0);
			openAPI.logger.info("edgeURL:step2/7: found Property Configuration "+ psri.getPropertyName() +"  version "+ psri.getPropertyVersion());
			String prt = openAPI.getPAPIRuletree(psri.getPropertyId(), psri.getPropertyVersion(), psri.getContractId(),
					psri.getGroupId(), false);
			openAPI.logger.info("edgeURL:step3/7: downloaded Property Configuration "+ psri.getPropertyName() +" version "+ psri.getPropertyVersion());
			openAPI.logger.debug(prt);
			if (prt.equals(null)) {
				openAPI.logger.info("Could not retrieve Property Configuration");
			} else {
				openAPI.logger.info("edgeURL:step4/7: extract edgeauth secret key from Property Configuration "+ psri.getPropertyName() +" version "+ psri.getPropertyVersion());
				String edgeAuthKey = openAPI.getEdgeAuthKeyFromPapiRuleSet(prt);
				openAPI.logger.info("edgeURL:step5/7: extract edgeauth query name from Property Configuration "+ psri.getPropertyName() +" version "+ psri.getPropertyVersion());
				//String edgeAuthLocationId = openAPI.getEdgeAuthLocationFromPapiRuleSet(prt);
				//Integer startTime = (int) Instant.now().getEpochSecond();
				Integer startTime = ((cmdEdgeurl.starttime == null) ?  (int) Instant.now().getEpochSecond() : cmdEdgeurl.starttime);
				openAPI.logger.info("edgeURL:step6/7: generate edgeauth token with startime: "+startTime);
				String edgeAuthToken;
				//AkamaiTokenConfig conf = openAPI.GenerateConfig(cmdEdgeurl.in, edgeAuthKey, 3600);
				//String edgeAuthToken = AkamaiTokenGenerator.generateToken(conf);
				edgeAuthToken = openAPI.getEdgeAuthToken(cmdEdgeurl.in, edgeAuthKey, cmdEdgeurl.duration, "token", startTime);
				openAPI.logger.info("edgeURL:step7/7: EdgeURL: \n" + edgeAuthToken);
				//openAPI.logger.info("edgeURL:step7/7: EdgeURL: \nhttps://" + openAPI.getHost() + cmdEdgeurl.in + "?" + edgeAuthLocationId + "="+ edgeAuthToken);
			}
			openAPI.logger.info("done");
			break; // optional
		case "invalidate":
			// invalidate content based on
			openAPI.logger.info("Start invalidate:");
			String jsonPurge = "{\"objects\":[\""+cmdPurge.in+"\"]}";
			//openAPI.doPurgeInvalidate(openAPI.jsonFileReader(jsonPurge));
			openAPI.doPurgeInvalidate(jsonPurge, cmdPurge.staging);
			openAPI.logger.info("done");
			break; // optional
		case "invalidate_cpcode":
			// invalidate content based on
			openAPI.logger.info("Start invalidate by CPCode:");
			openAPI.doPurgeInvalidateCPCode(cmdPurgeCPCode.in, cmdPurgeCPCode.staging);
			openAPI.logger.info("done");
			break; // optional			
		case "dir":
			if(cmdDir.recursive){
				openAPI.logger.info("Start dir recursive:");
				openAPI.logger.info("list the folder recursive:" + cmdDir.out);
				NetStorageDirResultStat netStorageDirResultStat = openAPI.doNetstorageDir(cmdDir.out, cmdDir.recursive);
				Gson gsonBuilder = new GsonBuilder().setPrettyPrinting().create();
				openAPI.logger.info("ns.dir.recursive(" + cmdDir.out + "):\n"+gsonBuilder.toJson(netStorageDirResultStat));
				openAPI.logger.info("done");						
			}else{
				openAPI.logger.info("Start dir:");
				openAPI.logger.info("list the folder:" + cmdDir.out);
				NetStorageDirResultStat netStorageDirResultStat = openAPI.doNetstorageDir(cmdDir.out);
				Gson gsonBuilder = new GsonBuilder().setPrettyPrinting().create();
				openAPI.logger.info("ns.dir(" + cmdDir.out + "):\n" + gsonBuilder.toJson(netStorageDirResultStat));
				openAPI.logger.info("done");				
			}
			break; // optional
		case "du":
			// disk utilisation
			openAPI.logger.info("settings.path:" + cmdDu.out);
			String responseDu = openAPI.doNetstorageDu(cmdDu.out, true);
			openAPI.logger.info("ns.du(" + cmdDu.out + "):\n" + responseDu);
			openAPI.logger.info("done");
			break; // optional
		case "delete":
			// delete
				openAPI.logger.info("settings.path:" + cmdDelete.out);
				boolean responseDelete = openAPI.doNetstorageDelete(cmdDelete.out);
				openAPI.logger.info("ns.delete(" + cmdDelete.out + "): " + responseDelete);
				openAPI.logger.info("done");	
			break; // optional	
		case "url_debug":
			openAPI.logger.info("Start URL Debug for URL:" + cmdUrlDebug.url);
			String responseUrlDebug = openAPI.doUrlDebug(cmdUrlDebug.url,cmdUrlDebug.edgeip, cmdUrlDebug.header);
			openAPI.logger.info("Result: " + responseUrlDebug);
			openAPI.logger.info("done");
			break; // optional
		case "translate_error":
			openAPI.logger.info("Start Translate ErrorCode:" + cmdTranslateError.errorCode);
			DiagnosticTools dt = new DiagnosticTools(openAPI);
			TranslatedError responseTranslateError = dt.doTranslateError(cmdTranslateError.errorCode);
			openAPI.logger.info("ReasonForFailure: " + responseTranslateError.getTranslatedError().getReasonForFailure());
			openAPI.logger.info("done");
			break; // optional		
		case "logs_by_ip":
			openAPI.logger.info("Start Get Log Lines by IP:" + cmdGetLogLinesByIP.ipAddress);
			String endTime = cmdGetLogLinesByIP.endTime;
			try {
				//Transform Date at HTTP Response Header in RFC2616 into Date needed in ISO 8601
				SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
				java.util.Date date = format.parse(endTime);
				TimeZone tz = TimeZone.getTimeZone("UTC");
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
				df.setTimeZone(tz);
				endTime = df.format(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			openAPI.logger.info("endTime:" + endTime);
			DiagnosticTools dt2 = new DiagnosticTools(openAPI);
			LogLines logLines = dt2.doGetLogLinesFromIP(cmdGetLogLinesByIP.ipAddress, endTime, "", "", "", "", "", "", "", "", "", "", "");
			openAPI.logger.info("Log Lines: " + logLines.getLogLines());
			openAPI.logger.info("done");
			break; // optional		
		default: // default
			openAPI.logger.info("please use a command:");
		}

	}
}

