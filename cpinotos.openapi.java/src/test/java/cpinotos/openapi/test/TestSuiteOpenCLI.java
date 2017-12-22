package cpinotos.openapi.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import cpinotos.openapi.test.step.OpenCLITestDeleteFile;
import cpinotos.openapi.test.step.OpenCLITestDeleteFolder;
import cpinotos.openapi.test.step.OpenCLITestDir;
import cpinotos.openapi.test.step.OpenCLITestDirRecursive;
import cpinotos.openapi.test.step.OpenCLITestDu;
import cpinotos.openapi.test.step.OpenCLITestEdgerc;
import cpinotos.openapi.test.step.OpenCLITestEdgercNetStorage;
import cpinotos.openapi.test.step.OpenCLITestEdgercPurge;
import cpinotos.openapi.test.step.OpenCLITestGet;
import cpinotos.openapi.test.step.OpenCLITestMkdir;
import cpinotos.openapi.test.step.OpenCLITestSymLink;
import cpinotos.openapi.test.step.OpenCLITestUpload;

@RunWith(Suite.class)
@SuiteClasses({
	OpenCLITestEdgerc.class,
	OpenCLITestEdgercPurge.class,
	OpenCLITestEdgercNetStorage.class,
	OpenCLITestMkdir.class, 
	OpenCLITestUpload.class, 
	OpenCLITestSymLink.class,
	OpenCLITestGet.class,
	OpenCLITestDu.class, 
	OpenCLITestDir.class, 
	OpenCLITestDirRecursive.class, 
	OpenCLITestDeleteFile.class, 
	OpenCLITestDeleteFolder.class})

public class TestSuiteOpenCLI {
}
