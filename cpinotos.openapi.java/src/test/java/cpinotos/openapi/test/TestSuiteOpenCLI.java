package cpinotos.openapi.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import cpinotos.openapi.test.step.OpenCLITestCPCodeList;
import cpinotos.openapi.test.step.OpenCLITestDebugUrl;
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
//TODO add OpenCLITestDebugUrl.class as soon as i understood why it is failing with ota.edgegate.de but not with dd.edgegate.de
//TODO add OpenCLITestGet.class Test 
@RunWith(Suite.class)
@SuiteClasses({
	OpenCLITestEdgerc.class,
	OpenCLITestEdgercPurge.class,
	OpenCLITestEdgercNetStorage.class,
	OpenCLITestMkdir.class, 
	OpenCLITestUpload.class, 
	OpenCLITestSymLink.class,
	OpenCLITestDu.class, 
	OpenCLITestDir.class, 
	OpenCLITestDirRecursive.class, 
	OpenCLITestDeleteFile.class, 
	OpenCLITestDeleteFolder.class,
	OpenCLITestCPCodeList.class})

public class TestSuiteOpenCLI {
}
