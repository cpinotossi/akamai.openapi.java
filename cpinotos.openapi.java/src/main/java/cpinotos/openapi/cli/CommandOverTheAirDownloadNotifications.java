package cpinotos.openapi.cli;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;




@Parameters(commandDescription = "List OTA download notification for a certain cpcode")
public class CommandOverTheAirDownloadNotifications {
	@Parameter(names = { "--cpcode", "-c" }, description = "The content provider code assigned to the Internet of Things product. Run the List Content Provider Codes operation to get an identifier.", required = true)
	public String cpcode;
	@Parameter(names = { "--start", "-s" }, description = "The Unix timestamp in milliseconds that starts a range of notifications. It cannot be older than the notification retention period of the past 12 hours. If not specified, the API returns notifications from the past 12 hours.", required = false)
	public String start;
	@Parameter(names = { "--format", "-f" }, description = "Output as simple list, by default json (json|list)", required = false)
	public String format = "json";
}
