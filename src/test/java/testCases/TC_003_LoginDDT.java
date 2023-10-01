package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageobject.HomePage;
import pageobject.LoginPage;
import pageobject.MyAccountPage;
import testBase.BaseClass;
import utilities.Dataproviders;

public class TC_003_LoginDDT extends BaseClass{
	//@Test(dataProvider = "LoginData") //this will be used if dataprovider also exists in the same class 
	//but here it is in another class for the purpose of multiple testcases here below format must be used
	@Test(dataProvider = "LoginData", dataProviderClass = Dataproviders.class)
	public void test_LoginDDT(String email, String pwd, String exp)
	//the num of parameters we should pass depends on the number of values dataprovider is  passing
	{
		logger.info(" Starting TC_003_LoginDataDrivenTest ");

		try {
			HomePage hp = new HomePage(driver);
			hp.clickMyAccount();
			hp.clickLogin();

			LoginPage lp = new LoginPage(driver);
			lp.setEmail(email);
			lp.setPassword(pwd);
			lp.clickLogin();

			MyAccountPage macc=new MyAccountPage(driver);
			boolean targetpage = macc.isMyAccountPageExists();// this method is created MyAccountPage
            //positive testing - taking valid data
			if (exp.equals("Valid")) {
				//1.valid credentials and target page appears= test passed case
				if (targetpage == true) {
					macc.clickLogout();
					Assert.assertTrue(true);
				} 
				//2.valid credentials and target page  not appears= test failed case
				else {
					Assert.assertTrue(false);
				}
			}
			
			//negative testing - taking invalid data

			if (exp.equals("Invalid")) {
				//3.invalid credentials and target page appears= test failed case
				if (targetpage == true) {
					MyAccountPage myaccpage = new MyAccountPage(driver);
					myaccpage.clickLogout();
					Assert.assertTrue(false);
				} 
				//4.invalid credentials and target page  not appears= test passed case
				else {
					Assert.assertTrue(true);
				}
			}

		} catch (Exception e) {
			Assert.fail();
		}

		logger.info(" Finished TC_003_LoginDataDrivenTest");

	}

}
