package cpinotos.openapi.cli;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;


@Parameters(commandDescription = "List Disk Usage for a single Netstorage folder")
public class CommandDU {
	@Parameter(names = { "--out", "-o" }, description = "Path to folder on Netstorage, including the CPCode Folder (e.g. /599907/my/folder)", required = true)
	public String out;
}
