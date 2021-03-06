package cpinotos.openapi;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Iterator;
import java.util.TimeZone;

import com.akamai.netstorage.NetStorageException;
//import com.akamai.edgeauth.AkamaiTokenConfig;
//import com.akamai.edgeauth.AkamaiTokenGenerator;
import com.beust.jcommander.JCommander;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

//import ch.qos.logback.classic.Level;
import cpinotos.openapi.cli.CommandDU;
import cpinotos.openapi.cli.CommandDelete;
import cpinotos.openapi.cli.CommandDir;
import cpinotos.openapi.cli.CommandDownload;
import cpinotos.openapi.cli.CommandEdgeAuth;
import cpinotos.openapi.cli.CommandGetLogLinesByIP;
import cpinotos.openapi.cli.CommandMkdir;
import cpinotos.openapi.cli.CommandOverTheAirDownloadNotifications;
import cpinotos.openapi.cli.CommandPapiCPCode;
import cpinotos.openapi.cli.CommandProducts;
import cpinotos.openapi.cli.CommandPurge;
import cpinotos.openapi.cli.CommandPurgeCPCode;
import cpinotos.openapi.cli.CommandReName;
import cpinotos.openapi.cli.CommandSymLink;
import cpinotos.openapi.cli.CommandTranslatedError;
import cpinotos.openapi.cli.CommandUpload;
import cpinotos.openapi.cli.CommandUrlDebug;
import cpinotos.openapi.cli.Commands;
import cpinotos.openapi.services.DiagnosticToolsAPI;
import cpinotos.openapi.services.NetStorageAPI;
import cpinotos.openapi.services.OverTheAirAPI;
import cpinotos.openapi.services.PropertyManagerAPI;
import cpinotos.openapi.services.PurgeAPI;
import cpinotos.openapi.services.data.CreateNewCPCodeResultV0;
import cpinotos.openapi.services.data.ListCPCode;
import cpinotos.openapi.services.data.ListCPCodeResult;
import cpinotos.openapi.services.data.LogLines;
import cpinotos.openapi.services.data.NetStorageDirResultStat;
import cpinotos.openapi.services.data.Notifications;
import cpinotos.openapi.services.data.ProductsResult;
import cpinotos.openapi.services.data.SearchPropertyVersionsBySingleValueResponseItemV0;
import cpinotos.openapi.services.data.SearchPropertyVersionsBySingleValueResponseV0;
import cpinotos.openapi.services.data.TranslatedError;
import cpinotos.openapi.services.data.UrlDebug;

//TODO ota api support
public class OpenCLI {

	@SuppressWarnings("static-access")
	public static void main(String[] args) throws Exception {
		if (null == args || args.length == 0) {
			System.out.println("try '--help' or '-h' for more information");
			System.exit(1);
		}
		// Read user input parameter.
		Commands commands = new Commands();
		CommandUpload cmdUpload = new CommandUpload();
		CommandSymLink cmdSymLink = new CommandSymLink();
		CommandDownload cmdDownload = new CommandDownload();
		CommandReName cmdReName = new CommandReName();
		CommandDU cmdDu = new CommandDU();
		CommandDir cmdDir = new CommandDir();
		CommandDelete cmdDelete = new cpinotos.openapi.cli.CommandDelete();
		CommandMkdir cmdMkdir = new CommandMkdir();
		CommandEdgeAuth cmdEdgeAuth = new CommandEdgeAuth();
		CommandPurge cmdPurge = new CommandPurge();
		CommandPurgeCPCode cmdPurgeCPCode = new CommandPurgeCPCode();
		CommandUrlDebug cmdUrlDebug = new CommandUrlDebug();
		CommandTranslatedError cmdTranslateError = new CommandTranslatedError();
		CommandGetLogLinesByIP cmdGetLogLinesByIP = new CommandGetLogLinesByIP();
		CommandPapiCPCode cmdCpCode = new CommandPapiCPCode();
		CommandProducts cmdProducts = new CommandProducts();
		CommandOverTheAirDownloadNotifications cmdOverTheAirDownloadNotifications = new CommandOverTheAirDownloadNotifications();
		JCommander jc = new JCommander();
		jc.addObject(commands);
		jc.addCommand("du", cmdDu);
		jc.addCommand("dir", cmdDir);
		jc.addCommand("mkdir", cmdMkdir);
		jc.addCommand("upload", cmdUpload);
		jc.addCommand("symlink", cmdSymLink);
		jc.addCommand("download", cmdDownload);
		jc.addCommand("rename", cmdReName);
		jc.addCommand("delete", cmdDelete);
		jc.addCommand("edgeauth", cmdEdgeAuth);
		jc.addCommand("invalidate", cmdPurge);
		jc.addCommand("invalidate_cpcode", cmdPurgeCPCode);
		jc.addCommand("url_debug",cmdUrlDebug);
		jc.addCommand("translate_error",cmdTranslateError);
		jc.addCommand("logs_by_ip", cmdGetLogLinesByIP);
		jc.addCommand("cpcode", cmdCpCode);
		jc.addCommand("products", cmdProducts);
		jc.addCommand("ota", cmdOverTheAirDownloadNotifications);
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

		boolean result = false;
		PropertyManagerAPI papi = null;
		NetStorageAPI nsapi = null;
		PurgeAPI puapi = null;
		DiagnosticToolsAPI dapi = null;
		OverTheAirAPI oapi = null;
		JsonParser parser;
		JsonObject json;
		Gson gsonBuilder;
		
		switch (currentCmd) {
		case "mkdir":
			nsapi = new NetStorageAPI(commands.hostname, commands.edgerc, commands.nssection, commands.verbose);
			String mkdirPath = "/"+nsapi.getNetstorageCpcode()+cmdMkdir.out;
			OpenAPI.LOGGER.info("Netstorage Location (out):" + mkdirPath);
			OpenAPI.LOGGER.info("Netstorage created:" + nsapi.doNetstorageMkdir(mkdirPath));
			OpenAPI.LOGGER.debug("done");
			break; // optional
		case "upload":
			nsapi = new NetStorageAPI(commands.hostname, commands.edgerc, commands.nssection, commands.verbose);
			String uploadPath = "/"+nsapi.getNetstorageCpcode()+cmdUpload.out;
			OpenAPI.LOGGER.info("Local File (in):" + cmdUpload.in);
			OpenAPI.LOGGER.info("Netstorage Location (out):" + uploadPath);
			result = nsapi.doNetstorageUpload(uploadPath, cmdUpload.in, cmdUpload.indexzip);
			OpenAPI.LOGGER.info("done");
			break; // optional
		case "download":
			nsapi = new NetStorageAPI(commands.hostname, commands.edgerc, commands.nssection, commands.verbose);
			String downloadPath = "/"+nsapi.getNetstorageCpcode()+cmdDownload.out;
			OpenAPI.LOGGER.info("settings.downloadpath:" + downloadPath);
			result = nsapi.doNetstorageDownload(downloadPath, cmdDownload.in);
			OpenAPI.LOGGER.info("download Response:" + result);
			OpenAPI.LOGGER.info("done");
			break; // optional
		case "rename":
			nsapi = new NetStorageAPI(commands.hostname, commands.edgerc, commands.nssection, commands.verbose);
			String oldName = "/"+nsapi.getNetstorageCpcode()+cmdReName.in;
			String newName = "/"+nsapi.getNetstorageCpcode()+cmdReName.out;
			OpenAPI.LOGGER.info("old path:" + oldName);
			OpenAPI.LOGGER.info("new path:" + newName);
			result = nsapi.doNetstorageReName(oldName, newName);
			OpenAPI.LOGGER.info("rename:" + result);
			OpenAPI.LOGGER.info("done");
			break; // optional
		case "symlink":
			nsapi = new NetStorageAPI(commands.hostname, commands.edgerc, commands.nssection, commands.verbose);
			OpenAPI.LOGGER.info("Start download:");
			String symlinkOut =  "/"+nsapi.getNetstorageCpcode()+cmdSymLink.out;
			String symlinkIn =  "/"+nsapi.getNetstorageCpcode()+cmdSymLink.in;
			OpenAPI.LOGGER.info("Netstorage SymLink Location (out):" + symlinkOut);
			result = nsapi.doNetstorageSymLink(symlinkOut, symlinkIn);
			OpenAPI.LOGGER.info("SymLink Response:" + result);
			OpenAPI.LOGGER.info("done");
			break; // optional
		case "dir":
			nsapi = new NetStorageAPI(commands.hostname, commands.edgerc, commands.nssection, commands.verbose);
			String dirOut =  "/"+nsapi.getNetstorageCpcode()+cmdDir.out;
			if(cmdDir.out.endsWith("/")){
				OpenAPI.LOGGER.info("ERROR: path should not end with an '/': " + dirOut);
				break;
			};
			if(cmdDir.recursive){
				NetStorageDirResultStat netStorageDirResultStat = nsapi.doNetstorageDir(dirOut, cmdDir.recursive);
				gsonBuilder = new GsonBuilder().setPrettyPrinting().create();
				OpenAPI.LOGGER.info(gsonBuilder.toJson(netStorageDirResultStat));

			}else{
				NetStorageDirResultStat netStorageDirResultStat = null;
				try{
					netStorageDirResultStat = nsapi.doNetstorageDir(dirOut);	
				}
				catch(NetStorageException e){
					OpenAPI.LOGGER.debug(e.getStackTrace().toString(),e);
					OpenAPI.LOGGER.info("Please check if the Netstorage Path is correct. Start by using just the CPCode folder.");
					break;
				}
				gsonBuilder = new GsonBuilder().setPrettyPrinting().create();
				OpenAPI.LOGGER.info(gsonBuilder.toJson(netStorageDirResultStat));				
			}
			break; // optional
		case "du":
			nsapi = new NetStorageAPI(commands.hostname, commands.edgerc, commands.nssection, commands.verbose);
			String duOut =  "/"+nsapi.getNetstorageCpcode()+cmdDu.out;
			OpenAPI.LOGGER.info("settings.path:" + duOut);
			String responseDu = nsapi.doNetstorageDu(duOut).toString();
			OpenAPI.LOGGER.info("ns.du(" + duOut + "):\n" + responseDu);
			OpenAPI.LOGGER.info("done");
			break; // optional
		case "delete":
			nsapi = new NetStorageAPI(commands.hostname, commands.edgerc, commands.nssection, commands.verbose);
			String deleteOut =  "/"+nsapi.getNetstorageCpcode()+cmdDelete.out;
			// delete
			OpenAPI.LOGGER.info("settings.path:" + deleteOut);
				//TODO fix the recursive hard coding
				boolean responseDelete = nsapi.doNetstorageDelete(deleteOut);
				OpenAPI.LOGGER.info("ns.delete(" + deleteOut + "): " + responseDelete);
				OpenAPI.LOGGER.info("done");	
			break; // optional	
		case "products":
			papi = new PropertyManagerAPI(commands.hostname, commands.edgerc, commands.section, commands.verbose);
			ProductsResult productsResults;
			if(cmdProducts.contractId==null){
				productsResults = papi.doListProducts();
			}else{
				productsResults =papi.doListProducts(cmdProducts.contractId);
			}
			gsonBuilder = new GsonBuilder().setPrettyPrinting().create();
			System.out.println(gsonBuilder.toJson(productsResults));
			break; // optional		
		case "cpcode":
			papi = new PropertyManagerAPI(commands.hostname, commands.edgerc, commands.section, commands.verbose);
			if (cmdCpCode.action.equals("list")){
				ListCPCodeResult listCPCodeResult;
				if(cmdCpCode.contractId == null || cmdCpCode.groupId == null){
					listCPCodeResult = papi.doListCPCodes();	
				}
				else{
					listCPCodeResult = papi.doListCPCodes(cmdCpCode.contractId,cmdCpCode.groupId);	
				}
				gsonBuilder = new GsonBuilder().setPrettyPrinting().create();
				StringBuilder jsonResultStringBuilder = new StringBuilder();
				Iterator<ListCPCode> i = listCPCodeResult.getCpcodes().getItems().iterator();
				while(i.hasNext()){
					jsonResultStringBuilder.append(gsonBuilder.toJson(i.next())+"\n");
				}
				OpenAPI.LOGGER.info(jsonResultStringBuilder.toString());
			}else if (cmdCpCode.action.equals("search")){
				//TBD
			}else if (cmdCpCode.action.equals("create")){
				CreateNewCPCodeResultV0 resultCP = null;
				if(cmdCpCode.name == null || cmdCpCode.productId == null){
					OpenAPI.LOGGER.info("Please enter an CPCode and Product ID.");
				}
				else if(cmdCpCode.contractId == null || cmdCpCode.groupId == null){
					resultCP = papi.doCreateCPCodes(cmdCpCode.name, cmdCpCode.productId);	
					gsonBuilder = new GsonBuilder().setPrettyPrinting().create();
					OpenAPI.LOGGER.info(gsonBuilder.toJson(resultCP));
				}
				else{
					resultCP = papi.doCreateCPCodes(cmdCpCode.contractId,cmdCpCode.groupId, cmdCpCode.name, cmdCpCode.productId);	
					gsonBuilder = new GsonBuilder().setPrettyPrinting().create();
					OpenAPI.LOGGER.info(gsonBuilder.toJson(resultCP));
				}

			};
			break; // optional	
		case "edgeauth":
			// init a new instance of Papi
			OpenAPI.LOGGER.info("Start edgeurl:");
			papi = new PropertyManagerAPI(commands.hostname, commands.edgerc, commands.section, commands.verbose);
			Integer startTime = ((cmdEdgeAuth.starttime == null) ?  (int) Instant.now().getEpochSecond() : cmdEdgeAuth.starttime);
			String edgeAuthToken = papi.getEdgeAuthToken(commands.hostname, cmdEdgeAuth.in, startTime, cmdEdgeAuth.duration, cmdEdgeAuth.network);
			OpenAPI.LOGGER.info("edgeURL:step7/7: EdgeURL: \n" + edgeAuthToken);
			break; // optional
		case "invalidate":
			puapi = new PurgeAPI(commands.hostname, commands.edgerc, commands.section, commands.verbose);
			// invalidate content based on
			OpenAPI.LOGGER.info("Start invalidate:");
			if(cmdPurge.urllist.size()>0){
				puapi.doPurgeInvalidate(cmdPurge.urllist, cmdPurge.staging);	
			}else{
				puapi.doPurgeInvalidate(cmdPurge.jsonPath, cmdPurge.staging);	
			}
			OpenAPI.LOGGER.info("done");
			break; // optional
		case "invalidate_cpcode":
			puapi = new PurgeAPI(commands.hostname, commands.edgerc, commands.section, commands.verbose);
			// invalidate content based on
			OpenAPI.LOGGER.info("Start invalidate by CPCode:");
			puapi.doPurgeInvalidateCPCode(cmdPurgeCPCode.in, cmdPurgeCPCode.staging);
			OpenAPI.LOGGER.info("done");
			break; // optional			
		case "url_debug":
			dapi = new DiagnosticToolsAPI(commands.hostname, commands.edgerc, commands.section, commands.verbose);
			UrlDebug responseUrlDebug = dapi.doUrlDebugAsynchronously(cmdUrlDebug.url,cmdUrlDebug.edgeip, cmdUrlDebug.header);
			gsonBuilder = new GsonBuilder().setPrettyPrinting().create();
			OpenAPI.LOGGER.info(gsonBuilder.toJson(responseUrlDebug));
			break; // optional
		case "translate_error":
			dapi = new DiagnosticToolsAPI(commands.hostname, commands.edgerc, commands.section, commands.verbose);
			OpenAPI.LOGGER.debug("Start Translate ErrorCode:" + cmdTranslateError.errorCode);
			String responseTranslateError = dapi.doTranslateErrorJson(cmdTranslateError.errorCode);
			parser = new JsonParser();
		    json = parser.parse(responseTranslateError).getAsJsonObject();
			gsonBuilder = new GsonBuilder().setPrettyPrinting().create();
			OpenAPI.LOGGER.info(gsonBuilder.toJson(json));
			//OpenAPI.LOGGER.info("ReasonForFailure: " + responseTranslateError.getTranslatedError().getReasonForFailure());
			break; // optional		
		case "logs_by_ip":
			dapi = new DiagnosticToolsAPI(commands.hostname, commands.edgerc, commands.section, commands.verbose);
			OpenAPI.LOGGER.info("Start Get Log Lines by IP:" + cmdGetLogLinesByIP.ipAddress);
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
			OpenAPI.LOGGER.info("endTime:" + endTime);
			LogLines logLines = dapi.doGetLogLinesFromIP(cmdGetLogLinesByIP.ipAddress, endTime, "", "", "", "", "", "", "", "", "", "", "");
			OpenAPI.LOGGER.info("Log Lines: " + logLines.getLogLines());
			OpenAPI.LOGGER.info("done");
			break; // optional
		case "ota":
			oapi = new OverTheAirAPI(commands.hostname, commands.edgerc, commands.section, commands.verbose);
			OpenAPI.LOGGER.debug("Start retrieve OTA Download Notifications for:" + cmdOverTheAirDownloadNotifications.cpcode);
			Notifications responseOTADownloadNotifications = oapi.doListDownloadNotificationsByCPCode(cmdOverTheAirDownloadNotifications.cpcode);
			if(cmdOverTheAirDownloadNotifications.format.equals("list")){
				OpenAPI.LOGGER.info(oapi.getListDownloadNotificationsAsTable(responseOTADownloadNotifications));	
			}else{
				gsonBuilder = new GsonBuilder().setPrettyPrinting().create();
				OpenAPI.LOGGER.info(gsonBuilder.toJson(responseOTADownloadNotifications));
			}
			break; // optional	
		default: // default
			OpenAPI.LOGGER.info("please use a command:");
		}

	}
}

