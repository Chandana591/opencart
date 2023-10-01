package testBase;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

//import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {
	//here we are making it as static coz we are creating object for base class in a method in extent report manager hence 2 
	//drivers will be created one for this base class and another for that object of base class hence there will be conflict 
	
	public static WebDriver driver;
//every method in this class should be made public as the test cases are in different package
	public ResourceBundle rb;  // to read config.properties
	public Logger logger; //we have to import "Logger" from apache.lg4j2 package
	@BeforeClass(groups = { "Master", "Sanity", "Regression" }) //Step8 groups added
	@Parameters("browser")  // getting browser parameter from testng.xml
	public void setup(String br)
	{
		
		rb = ResourceBundle.getBundle("config");// Load config.properties
		//config is the name of the properties file
		
		logger = LogManager.getLogger(this.getClass());// for Logger  
		
		//launch right browser based on parameter
		//ChromeOptions options=new ChromeOptions();
		//options.setExperimentalOption("excludeSwitches",new String[] {"enable-automation"});
		
		//WebDriverManager.chromedriver().setup();
		if(br.equals("chrome")) {
			driver=new ChromeDriver();
		}
		else if(br.equals("edge")) {
			driver=new EdgeDriver();
		}
		else {
			driver=new FirefoxDriver();
		}
		
		
		
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		
		driver.get(rb.getString("appURL")); // get url from config.properties file
		
		driver.manage().window().maximize();
	}
	
	@AfterClass(groups = { "Master", "Sanity", "Regression" }) //Step8 groups added
	public void tearDown()
	{
		driver.quit();
	}
	//these 3 are normal java methods so we should not use any annotation of testng
	public String randomeString() {
		String generatedString = RandomStringUtils.randomAlphabetic(5);
		return (generatedString);
	}

	public String randomeNumber() {
		String generatedString2 = RandomStringUtils.randomNumeric(10);
		return (generatedString2);
	}
	
	public String randomAlphaNumeric() {
		String st = RandomStringUtils.randomAlphabetic(4);
		String num = RandomStringUtils.randomNumeric(3);
		
		return (st+"@"+num);
	}
	
	//This is a user defined method so as to capture screen shot along with time stamp in the report 
	public String captureScreen(String tname) throws IOException {
//tname is the name of th method which is failed
		String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
				
		TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
		File source = takesScreenshot.getScreenshotAs(OutputType.FILE);
		String destination = System.getProperty("user.dir") + "\\screenshots\\" + tname + "_" + timeStamp + ".png";

		try {
			FileUtils.copyFile(source, new File(destination));
		} catch (Exception e) {
			e.getMessage();
		}
		return destination;

	}

}
