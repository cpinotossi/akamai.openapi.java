package cpinotos.openapi.cli;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;


@Parameters(commandDescription = "Upload files to Netstorage")
public class CommandGetLogLinesByIP {
	@Parameter(names = { "--ipAddress", "-i" }, description = "String	1.1.1.1	The IP address of the server from which to run the diagnostics test.", required = true)
	public String ipAddress;
	@Parameter(names = { "--endTime", "-e" }, description = "2016-02-01T22:23:00Z An ISO 8601 timestamp for a point of time in the past when the log search window ends.", required = true)
	public String endTime;
	
	@Parameter(names = { "--arl"}, description = "www.example.com Filter by the ARL log field value.", required = false)
	public String arl;
	@Parameter(names = { "--clientIp"}, description = "String	123.123.13.123	Filter by IP address specified in the Client-IP log field", required = false)
	public String clientIp;
	@Parameter(names = { "--cpCode" }, description = "Filter by CP code.", required = false)
	public String cpCode;
	@Parameter(names = { "--duration"}, description = "The number of minutes before the endTime for which to collect logs, 30 by default, and a maximum 360 for six hours.", required = false)
	public String duration;
	@Parameter(names = { "--hostHeader"}, description = "www.example.com Filter by the Host header log field value.", required = false)
	public String hostHeader;
	@Parameter(names = { "--httpStatusCode"}, description = "Filter by HTTP status code.", required = false)
	public String httpStatusCode;
	@Parameter(names = { "--logType"}, description = "Filter the type of log lines, either r for incoming client requests, or f for requests to other edge servers or to the origin server. Any f log type specifying a Forward-IP in the 10.x.x.x range means the request was forwarded to another edge server.", required = false)
	public String logType;
	@Parameter(names = { "--maxLogLines"}, description = "The maximum number of log lines to include in the results, 200 by default and no more than 1000.", required = false)
	public String maxLogLines;
	@Parameter(names = { "--objStatus"}, description = "Filter by single-character code that provides details the requested content. See Object Status Codes for details on each code.", required = false)
	public String objStatus;
	@Parameter(names = { "--requestId"}, description = "The end user requestId to filter by. This is the same value that identifies an end users diagnostic URL.", required = false)
	public String requestId;
	@Parameter(names = { "--userAgent"}, description = "Filter by the User-Agent log field.", required = false)
	public String userAgent;

}
