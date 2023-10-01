package pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
//Base page is having only constructor which is common in all the page object classes
public class BasePage {
	

	 WebDriver driver;
	    
	   public BasePage(WebDriver driver)
	     {
		     this.driver=driver;
		     PageFactory.initElements(driver,this);
	     }

}
