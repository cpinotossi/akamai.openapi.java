package cpinotos.openapi.cli;

import java.util.ArrayList;

import com.beust.jcommander.Parameter;

public class Commands {
	@Parameter(names = { "--verbose", "-v" }, description = "Make the operation more talkative", required = false)
	public boolean verbose = false;
	
	@Parameter(names = { "--edgerc"}, description = "Full path to the credentials file. If not provided we will look <user.home>/.edgerc", required = false)
	public String edgerc;
	
	@Parameter(names = { "--section"}, description = "Title of the section of the .edgerc file which will be used.", required = true)
	public String section;

    @Parameter(names = {"--help"}, help = true)
    public boolean help = false;
    
    @Parameter(names = {"--hostname","-h"}, description = "hostname referenced inside an akamai configuration / property.", required = true)
	public String hostname;
        
    @Parameter(names = {"--nssection","-n"}, description = "NetStorage upload account Title of the section inside the .edgerc. If not provided we will look for the following section name api-upload-account", required = false)
	public String nssection;
    
}

