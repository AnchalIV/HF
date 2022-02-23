package Generic;

import java.lang.reflect.Method;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

public class TestBase {

	protected Generic_method gm;

	@BeforeSuite
	public void beforeSuite() {
		gm = new Generic_method();
		gm.initHtmlReport();
		gm.loadProperties("src\\main\\resources\\config.properties");

	}

	@BeforeMethod()
	public void launchbrowser(Method method) {

		gm.setExtentLogger(method.getName());
		
		gm.launchBrowser("Chro");
		gm.getUrl(gm.getPropData("url"));
		gm.maximizeWindow();
		
	}

	@AfterSuite
	public void afterSuite() {
		gm.flushExtentsReport();
	}
}
