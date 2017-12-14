package cpinotos.openapi.cli;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;



@Parameters(commandDescription = "Invalidate/Purge Files from Akamai Edge Server via Purge")
public class CommandEdgeurl {
	@Parameter(names = { "--in", "-i" }, description = "Path of the url (e.g. /path/to/file.txt)", required = true)
	public String in;
	@Parameter(names = { "--duration", "-d" }, description = "duration the token will be valid in seconds (default 3600sec)", required = false)
	public Integer duration = 3600;
	@Parameter(names = { "--starttime", "-s" }, description = "start time of the token in epoch unix timestamp", required = false)
	public Integer starttime = null;
}
