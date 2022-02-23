package Generic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.google.common.io.Files;

public class Generic_method {
	private static final TimeUnit SECONDS = null;
	private WebDriver driver;
	private Properties propObj;
	private ExtentTest extentLogger;

	private static ExtentReports extent;

	public WebDriver getDriver() {
		return driver;
	}

	public void launchBrowser(String browsername) {
		if (browsername.equalsIgnoreCase("Chrome")) {

			System.setProperty(getPropData("setPath"), getPropData("chromeExe"));
			driver = new ChromeDriver();
			//extentLogger.log(Status.INFO, " chrome browser launch Successfully ");
		} else if (browsername.equalsIgnoreCase("firefox")) {

			System.setProperty("webdriver.chrome.driver",getPropData("fireFoxExe"));
			driver = new FirefoxDriver();
		} else if (browsername.equalsIgnoreCase("InternetExplorer")) {
			System.setProperty("webdriver.chrome.driver", "driver/internetexplorerdriver.exe");
			driver = new InternetExplorerDriver();
			 
		}else {
			headless();
		}
		
		
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		}
		public void headless() {
			System.setProperty(getPropData("setPath"), getPropData("chromeExe"));
			ChromeOptions cop=new ChromeOptions();
			cop.setHeadless(true);
			driver = new ChromeDriver(cop);
		}
		
	private String filepath;

	public void loadProperties(String filePath) {
		this.filepath = filePath;
		propObj = new Properties();

		try {
			propObj.load(new FileInputStream(filePath));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getPropData(String keyName) {
		if(propObj==null) {
			loadProperties(filepath);
		}
		return propObj.getProperty(keyName);
	}
	
	public void holdOn(int timeInSecond) {
		try {
			Thread.sleep(1000*timeInSecond);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*
	 * public void fluentWait() { Wait wait = new FluentWait(driver)
	 * .withTimeout(20, SECONDS) .pollingEvery(2, SECONDS)
	 * .ignoring(Exception.class); }
	 */

	/*
	 * public void explicitWait(String value) { WebDriverWait wait = new
	 * WebDriverWait(driver,30);
	 * wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(value))); }
	 */
	public void getUrl(String url) {
		driver.get(url);
		
	}

	public void sendkeys(WebElement we, String value) {
		we.sendKeys(value);
	}

	public void sendkeysFromProp(WebElement we, String keyname) {
		we.sendKeys(getPropData(keyname));
		extentLogger.log(Status.PASS,"Data inputed SuccesFully from PropertiesFile in"+we.toString()+"("+keyname+")");
	}

	public void click(WebElement we) {
	try {
		we.click();
		extentLogger.log(Status.PASS,"Click Action Performed SuccesFully on "+we.toString());

	}catch (StaleElementReferenceException  e) {
		holdOn(4);
		we.click();
		extentLogger.log(Status.PASS,"Click Action Performed SuccesFully on "+we.toString());

	}catch (Exception e) {
		extentLogger.log(Status.FAIL,"Click Action  Performed UnSuccessFully on "+we.toString());

	}
	}

	public void clear(WebElement we) {
		we.clear();
	}
    public String getWindowHandle() {
    	 return driver.getWindowHandle();
    }
    public void getWindowHandles(String expWindowTitle) {
    Set<String> windowhandles=driver.getWindowHandles();
    for(String winvalue:windowhandles) {
    	driver.switchTo().window(winvalue);
    	String title=driver.getTitle();
    	if(title.equalsIgnoreCase(expWindowTitle)) {
    		break;
    	}
    }
    }
    public String getTitle() {
    	String title=driver.getTitle();
    return title;
    }
	public void selectByIndex(WebElement we, int num) {
		Select sel = new Select(we);
		sel.selectByIndex(num);
	}

	public void selectByVisibleText(WebElement we, String textName) {
		Select sel = new Select(we);
		sel.selectByVisibleText(textName);
	}

	public void selectByValue(WebElement we, String value) {
		Select sel = new Select(we);
		sel.selectByValue(value);
	}

	public void doubleClick(WebElement target) {
		Actions act = new Actions(driver);
		act.moveToElement(target).doubleClick(target).build().perform();
	}

	public void rightClick(WebElement target) {
		Actions act = new Actions(driver);
		act.contextClick(target).build().perform();
	}

	public void actionsClick(WebElement target) {
		Actions act = new Actions(driver);
		act.click(target).build().perform();
	}

	public void actionsSendKeys(WebElement target) {
		Actions act = new Actions(driver);
		act.sendKeys(target).build().perform();
	}

	public void moveToElement(WebElement target) {
		Actions act = new Actions(driver);
		act.moveToElement(target).build().perform();
	}

	public void clickAndHold(WebElement target) {
		Actions act = new Actions(driver);
		act.clickAndHold(target).build().perform();
	}

	public void dragAndDrop(WebElement source, WebElement target) {
		Actions act = new Actions(driver);
		act.dragAndDrop(source, target).build().perform();
	}

	public void readData(File file, String sheetName) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Workbook wk = null;
		try {
			wk = new XSSFWorkbook(fis);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		wk.getSheet(sheetName);
	}
	public  String getPageSource() {
		return driver.getPageSource();
	}
	public boolean isDisplayed(WebElement we) {
	return	we.isDisplayed();
	}
	public boolean isEnabled(WebElement we) {
		return we.isEnabled();
	}
	public boolean isSelected(WebElement we) {
		 return we.isSelected();
	}
	public void ScreenShot() {
		TakesScreenshot takesshot=(TakesScreenshot)driver;
		File srcfile=takesshot.getScreenshotAs(OutputType.FILE);
		File file=new File(Math.random()+"screenshot.png");
		try {
			Files.copy(srcfile, file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void size(WebElement we) {
		Dimension size=we.getSize();
		size.getHeight();
		size.getWidth();
	}
	public void StringiFrame(String value) {
		driver.switchTo().frame(value);
	}
	public void webElementiFrame(WebElement value) {
		driver.switchTo().frame(value);
	}
	public void iFrame(int num) {
		driver.switchTo().frame(num);
	}
	public void alertAccept() {
		driver.switchTo().alert().accept();
	}
	public void defaultcontent() {
		driver.switchTo().defaultContent();
	}
	public void alertDismiss() {
		driver.switchTo().alert().dismiss();
	}
	public void alertgettext() {
		driver.switchTo().alert().getText();
	}
	public void close() {
		driver.close();
	}
	public void quit() {
		driver.quit();
	}
	public void getText(WebElement we) {
		we.getText();
	}
	
	public void jsClick(WebElement we) {
		JavascriptExecutor jse=(JavascriptExecutor)driver;
		jse.executeScript("arguments[0].click()",we);
	}
    public void jsSendkeys(WebElement we,String val) {
    	JavascriptExecutor jse=(JavascriptExecutor)driver;
		jse.executeScript("arguments[0].value='val';",we);
    }
	
	 public void verify(WebElement we,String expResult) { 
	 String actualResult=we.getText(); 
	 Assert.assertEquals(actualResult,expResult); 
	 }

		 public void setExtentLogger(String TestCaseName) {
				extentLogger = extent.createTest(TestCaseName);
			}

			public ExtentTest getExtentLogger() {
				return extentLogger;
			}

			public void initHtmlReport() {
				// specify location of the report
				 ExtentHtmlReporter	htmlReporter = new ExtentHtmlReporter("Reports/extendReports" + getSimpleDateTimeFormate() + ".html");

				htmlReporter.config().setDocumentTitle("Automation Report"); // Title of report

				htmlReporter.config().setReportName("Functional Testing"); // Name of the report
				htmlReporter.config().setTheme(Theme.STANDARD);

				extent = new ExtentReports();
				extent.attachReporter(htmlReporter);

				// Passing General information
				extent.setSystemInfo("ProjectName", "HumanFirewall");
				extent.setSystemInfo("Environemnt", "QA");
				extent.setSystemInfo("user", "Anchal");
			}

			public String getSimpleDateTimeFormate() {
				SimpleDateFormat timestamp = new SimpleDateFormat("yyyy_MM_dd__hh_mm_ss");
				Date date = new Date();
				return timestamp.format(date);
			}
			public void flushExtentsReport() {
				extent.flush();
			}
	        public void maximizeWindow() {
	        	driver.manage().window().maximize();
	        }
}
