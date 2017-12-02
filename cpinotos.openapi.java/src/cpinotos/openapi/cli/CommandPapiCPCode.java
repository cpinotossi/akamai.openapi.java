package cpinotos.openapi.cli;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;




@Parameters(commandDescription = "CP codes operations.")
public class CommandPapiCPCode {
	@Parameter(names = { "--action", "-a" }, description = "list|create|search", required = true)
	public String action;
	@Parameter(names = { "--contractId", "-c" }, description = "Unique identifier for the contract.", required = false)
	public String contractId;
	@Parameter(names = { "--groupId", "-g" }, description = "Unique identifier for the group.", required = false)
	public String groupId;
	@Parameter(names = { "--cpcode"}, description = "CPCode to get details for.", required = false)
	public String cpcode;
	@Parameter(names = { "--name", "-n"}, description = "Name of the new CPCode.", required = false)
	public String name;
	@Parameter(names = { "--productId", "-p"}, description = "Product ID to be used with the new CPCode.", required = false)
	public String productId;
	
}
