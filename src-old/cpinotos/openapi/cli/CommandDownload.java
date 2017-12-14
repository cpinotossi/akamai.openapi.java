package cpinotos.openapi.cli;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;


@Parameters(commandDescription = "Download files from Netstorage")
public class CommandDownload {
	@Parameter(names = { "--in", "-i" }, description = "Path to local, including file name (e.g. /Users/me/downloads/myfile.txt", required = true)
	public String in;
	@Parameter(names = { "--out", "-o" }, description = "Path to netstorage, including CPCode Folder (e.g. /599907/my/file.txt)", required = true)
	public String out;
}
