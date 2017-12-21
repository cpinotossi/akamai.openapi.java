package cpinotos.openapi.cli;

import com.beust.jcommander.Parameter;

public class Commands {
	@Parameter(names = { "--verbose", "-v" }, description = "Make the operation more talkative", required = false)
	public boolean verbose = false;
	@Parameter(names = { "--config", "-c" }, description = "Path to the Configuration/Properties file which includes all the credentials and parameter", required = false)
	public String config = "./openapi.property";
	//@Parameter(names = { "--out", "-o" }, description = "directory path", required = false)
	//String out = "/599907/updates/test" + System.currentTimeMillis() / 1000L + ".txt";
    @Parameter(names = {"--help"}, help = true)
    public boolean help = false;
    
    @Parameter(names = {"--hostname","-h"}, description = "hostname referenced inside an akamai configuration / property.", required = true)
	public String hostname;
}

