package cpinotos.openapi.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

import com.google.gson.Gson;

import cpinotos.openapi.OpenAPI;
import cpinotos.openapi.services.data.Notification;
import cpinotos.openapi.services.data.Notifications;

public class OverTheAirAPI extends OpenAPI {

	public OverTheAirAPI(String hostname, String edgercFilePath, String apiClientNameSection, boolean debug) {
		super(hostname, edgercFilePath, debug);
		this.setApiClientName(apiClientNameSection);
		initApiCredentials();
	}

	public Notifications doListDownloadNotificationsByCPCode(String cpcode) throws ClientProtocolException, UnsupportedOperationException, IOException{
		return doListDownloadNotificationsByCPCode(cpcode, "");
	}
	
	public Notifications doListDownloadNotificationsByCPCode(String cpcode, String start) throws ClientProtocolException, UnsupportedOperationException, IOException{
		String currentApiPapiEndpoint = this.getApiOverTheAirDownloadNotificationsEndpoint();
		currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(currentApiPapiEndpoint, "cpcode", cpcode);
		currentApiPapiEndpoint = OpenAPI.addValueToAPIEndPointURL(currentApiPapiEndpoint, "start", start);
		String jsonResult = this.doEdgeGridAPIRequest(currentApiPapiEndpoint);
		//OpenAPI.LOGGER.info("doListDownloadNotificationsByCPCode: "+ jsonResult);
		Gson gson = new Gson();
		Notifications notifications = gson.fromJson(jsonResult, Notifications.class);
		return notifications;
	}
	
	public String getListDownloadNotificationsAsTable(Notifications notifications){
		List<Notification> notificationsList = notifications.getNotifications();
		Iterator<Notification> i = notificationsList.iterator();
		Map<String,ArrayList> mapPath = new HashMap<String,ArrayList>();
		
		while(i.hasNext()){
			Notification notification = i.next();
			if(!mapPath.containsKey(notification.getPath())){
				mapPath.put(notification.getPath(), new ArrayList<Notification>());
			}
			ArrayList<Notification> current = (ArrayList) mapPath.get(notification.getPath());
			current.add(notification);
		}
		String header = "UID\tDownload-Time\tURL\n";
		StringBuffer resultBuffer = new StringBuffer();
		resultBuffer.append(header);
		for(Map.Entry<String, ArrayList> entry:mapPath.entrySet()){
			ArrayList<Notification> currentArray = entry.getValue();
			for(int counter=0;counter<currentArray.size() ;counter++){
				Notification currentNotification = currentArray.get(counter);
				String row = currentNotification.getUid() +"\t"+ currentNotification.getDownloadTime() +"\thttp(s)://"+ currentNotification.getHost() + currentNotification.getPath()+"\n";
				resultBuffer.append(row);
			}
		}
		return resultBuffer.toString();
	}



}
