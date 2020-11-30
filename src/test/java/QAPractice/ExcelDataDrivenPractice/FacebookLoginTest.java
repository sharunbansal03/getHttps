package QAPractice.ExcelDataDrivenPractice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class FacebookLoginTest {
	WebDriver driver;

	@BeforeMethod
	public void configDriver() {
		System.setProperty("webdriver.chrome.driver", "C:/drivers/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test(dataProvider = "getUserCredentials")
	public void testLogin(String userName, String password) {
		driver.get("https://www.facebook.com/");
		WebElement emailField = driver.findElement(By.name("email"));
		emailField.sendKeys(userName);
		driver.findElement(By.name("pass")).sendKeys(password);
		driver.findElement(By.xpath("//input[@value='Log In']")).click();
		Assert.assertTrue(
				driver.findElement(By.xpath("//div[@aria-label='Facebook' and @role='navigation']")).isDisplayed(),
				"Login failed");
		System.out.println("Login successful");
		
	}
	
	@AfterMethod
	public void closeBrowser() {
		driver.close();
	}

	@DataProvider
	public Object[][] getUserCredentials() throws IOException {
		ExcelReader obj = new ExcelReader();
		obj.getSheet("myworkbook.xlsx", "RealTestData");
		String[] getCols = { "User name", "Password" };
		ArrayList<ArrayList<String>> userCredsFromExcel = obj.getMatchingRecords("Run Test", "Y",
				Arrays.asList(getCols));
		int numberOfTests = userCredsFromExcel.size();
		Object[][] data = new Object[numberOfTests][getCols.length];
		for (int i = 0; i < userCredsFromExcel.size(); i++) {
			int j = 0;
			ArrayList<String> record = userCredsFromExcel.get(i);
			for (String value : record) {
				data[i][j] = value;
				j++;
			}
		}
		return data;

	}
}
