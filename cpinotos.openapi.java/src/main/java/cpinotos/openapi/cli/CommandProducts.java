package cpinotos.openapi.cli;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(commandDescription = "This operation lists the set of products that are available under a given contract under which the hostname is setup.")
public class CommandProducts {
	@Parameter(names = { "--contractId", "-c" }, description = "Contract ID, if not avaiable the Contract ID will be retrieved based on the Hostname.", required = false)
	public String contractId;
}
