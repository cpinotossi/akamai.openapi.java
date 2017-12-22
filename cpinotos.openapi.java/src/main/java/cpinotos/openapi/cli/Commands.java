package cpinotos.openapi.cli;

import com.beust.jcommander.Parameter;

public class Commands{
		
	@Parameter(names = { "--verbose", "-v" }, description = "Make the operation more talkative", required = false, hidden = true)
	public boolean verbose = false;

    @Parameter(names = {"--help"}, help = true)
    public boolean help = false;

    @Parameter(names = {"--hostname","-h"}, description = "Hostname (Property) referenced inside an akamai configuration.", required = true)
	public String hostname;
    
	@Parameter(names = { "--edgerc"}, description = "Full path to the credentials file. Default: <user.home>/.edgerc", required = false)
	public String edgerc;

	@Parameter(names = { "--section"}, description = "API Client Name inside the edgerc. Default: api-client", required = false)
	public String section;
    
    @Parameter(names = {"--nssection","-n"}, description = "Netstorage Upload Client Name inside the edgerc. Default: api-client-netstorage", required = false)
	public String nssection;

    @Parameter(names = {"--psection","-p"}, description = "Purge API Client Name inside the edgerc. Default: api-client-purge", required = false)
	public String psection;
}

