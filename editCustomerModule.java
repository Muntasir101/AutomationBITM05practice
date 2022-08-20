package Project_DemoGuru99Banking;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.Objects;
import java.util.Random;


public class editCustomerModule {
    WebDriver driverObj;

    ExtentHtmlReporter htmlReporter;
    ExtentReports reports;
    ExtentTest logger;

    //Random string and numbers for varying inputs

    String upperAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    String lowerAlphabet = "abcdefghijklmnopqrstuvwxyz";
    String alphaBets = upperAlphabet + lowerAlphabet;

    StringBuilder sb = new StringBuilder();

    Random random = new Random();


    int rndNum1 = (int) (Math.random() * 101);
    String editMail = "editMail" + rndNum1 + "@ymail.com";

    int rndNum2 = (int) (Math.random() * 101);
    String num = String.valueOf(rndNum2);

    int rndNum3 = (int) (Math.random() * 1000001);
    String pinNum = String.valueOf(rndNum3);

    int rndNum4 = (int) (Math.random() * 1000001);
    String mobNum = "8801109" + String.valueOf(rndNum4);

    @BeforeClass
    @Parameters("Browser")
    public void init_setup(String browser_name) {
        if (browser_name.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driverObj = new FirefoxDriver();
        } else if (browser_name.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            driverObj = new ChromeDriver();
            driverObj.manage().window().maximize();
        } else if (browser_name.equalsIgnoreCase("edge")) {
            WebDriverManager.edgedriver().setup();
            driverObj = new EdgeDriver();
        } else if (browser_name.equalsIgnoreCase("opera")) {
            WebDriverManager.operadriver().setup();
            driverObj = new OperaDriver();
        } else {
            System.out.println("Please provide defined browser name (chrome, firefox, edge, opera).");
        }
    }

    @AfterClass
    public void tearDown() {
        driverObj.quit();
    }

    @Test(description = "Edit information of existing customer with valid data input")
    public void newCustomer_Module_Test_valid_data() throws InterruptedException {

        //Generating info

        for(int i = 0; i < 2; i++) {

            int index = random.nextInt(alphaBets.length());

            char randomChar = alphaBets.charAt(index);

            sb.append(randomChar);
        }

        String randomString = sb.toString();

        Thread.sleep(2000);


        htmlReporter = new ExtentHtmlReporter(".\\Reports\\editCustomerModule_valid_data_input_Test_Report.html");
        reports = new ExtentReports();
        reports.attachReporter(htmlReporter);

        reports.setSystemInfo("Environment: ", "Java_Selenium_TestNG");
        reports.setSystemInfo("CPU", "64bit_2.00 GHz");
        reports.setSystemInfo("OS", "Windows 10");
        reports.setSystemInfo("", "");
        reports.setSystemInfo("Project: ", "Guru99Banking_V4");
        reports.setSystemInfo("Module: ", "New Customer");
        reports.setSystemInfo("Testing type: ", "Functional Testing");
        reports.setSystemInfo("Testing case: ", "Valid customer creation");
        reports.setSystemInfo("Test requirements: ", "Manager ID: mngr431045 ;  Password: gUnYqur");
        reports.setSystemInfo("Test requirements: ", "Existing Customer ID: 93196");
        reports.setSystemInfo("ManagerCredential Expire date: ", "28-August-2022");
        reports.setSystemInfo("Data source", "Directly defined data within");
        reports.setSystemInfo("Additional Attachments", "Manager credential file_filename: demoguru99bankCredential_Screenshot.jpg");
        reports.setSystemInfo("Additional Attachments", "Existing customer credential file_filename: demoGuruBank_validCustomer_credential.jpg");
        reports.setSystemInfo("", "");
        reports.setSystemInfo("Test by: ", "Kumar Shimanto");
        reports.setSystemInfo("Comment: ","Only simple functional testing is done as new customer module is done in details");
        reports.setSystemInfo("Comment","Same procedure can be used to test for invalid data, reset etc.");
        reports.setSystemInfo("Additional Info.: ","Customer name, gender and date of birth can not be changed.");

        logger = reports.createTest("Demo_Guru99_Bank_Edit_Customer_Module_Test_Valid_Data");

        driverObj.get("https://demo.guru99.com/v4/");
        logger.log(Status.INFO, "Site opened");

        //Manager_ID Log
        WebElement userId = driverObj.findElement(By.name("uid"));
        WebElement password = driverObj.findElement(By.name("password"));
        WebElement lgnBtn = driverObj.findElement(By.name("btnLogin"));

        userId.sendKeys("mngr431045");
        logger.log(Status.INFO, "Manager UserID typed");
        password.sendKeys("gUnYqur");
        logger.log(Status.INFO, "Manager Password typed");
        Thread.sleep(1000);
        lgnBtn.click();
        logger.log(Status.INFO, "Login button clicked");

        String currentPageTitle = driverObj.getTitle();
        Thread.sleep(1000);

        WebElement editCustomerModule = driverObj.findElement(By.xpath("/html/body/div[3]/div/ul/li[3]/a"));
        editCustomerModule.click();
        logger.log(Status.INFO,"Edit Customer module clicked");
        Thread.sleep(1000);

        driverObj.get("https://demo.guru99.com/v4/manager/EditCustomer.php");

        WebElement editCustomerForm = driverObj.findElement(By.xpath("/html/body/div[2]/table/tbody/tr/td/table/tbody/tr[1]/td/p"));
        Thread.sleep(1000);

        if (Objects.equals(currentPageTitle, "Guru99 Bank Manager HomePage")) {
            logger.log(Status.PASS, "Manager logged in");
            System.out.println("Manager Logged In.");
        } else {
            logger.log(Status.FAIL, "Manager log in failed");
            System.out.println("Manager did not log in, try again with valid credential");
        }

        Thread.sleep(1000);

        if (Objects.equals(editCustomerForm.getText(), "Edit Customer Form")) {
            logger.log(Status.PASS, "Edit Customer entry module opened");
            System.out.println("Edit Customer entry module opened");
        } else {
            logger.log(Status.FAIL, "Edit Customer entry module did not open");
            System.out.println("Edit Customer entry Module did not open, try again.");
        }

        Thread.sleep(1000);

        WebElement cusId = driverObj.findElement(By.xpath("/html/body/div[2]/table/tbody/tr/td/table/tbody/tr[6]/td[2]/input"));
        WebElement submitBtn1 = driverObj.findElement(By.name("AccSubmit"));
        WebElement resetBtn1 = driverObj.findElement(By.xpath("/html/body/div[2]/table/tbody/tr/td/table/tbody/tr[11]/td[2]/input[2]"));
        WebElement HomeBtn1 = driverObj.findElement(By.xpath("/html/body/div[2]/table/tbody/tr/td/p/a"));

        Thread.sleep(1000);

        cusId.sendKeys("93196");
        logger.log(Status.INFO,"Existing Customer ID typed");
        Thread.sleep(2000);
        submitBtn1.click();
        logger.log(Status.INFO, "Submit button clicked");

        Thread.sleep(2000);

        WebElement editingPage = driverObj.findElement(By.xpath("/html/body/table/tbody/tr/td/table/tbody/tr[1]/td/p"));

        if (Objects.equals(editingPage.getText(), "Edit Customer")) {
            logger.log(Status.PASS, "Customer info. editing page opened_test_ok");
            System.out.println("Customer info. editing page opened_test_ok");
        } else {
            logger.log(Status.FAIL, "Customer info. editing page did not open_test_fail");
            System.out.println("Customer info. editing page did not open_test_fail");
        }

        Thread.sleep(2000);

        //Edit information
        WebElement address = driverObj.findElement(By.name("addr"));
        WebElement city = driverObj.findElement(By.name("city"));
        WebElement state = driverObj.findElement(By.name("state"));
        WebElement pin = driverObj.findElement(By.name("pinno"));
        WebElement mobileNo = driverObj.findElement(By.name("telephoneno"));
        WebElement email = driverObj.findElement(By.name("emailid"));
        WebElement submitBtn = driverObj.findElement(By.xpath("/html/body/table/tbody/tr/td/table/tbody/tr[13]/td[2]/input[1]"));
        WebElement resetBtn = driverObj.findElement(By.xpath("/html/body/table/tbody/tr/td/table/tbody/tr[13]/td[2]/input[2]"));

        Thread.sleep(2000);

        String Address = address.getText();
        String City = city.getText();
        String State = state.getText();
        String PIN = pin.getText();
        String Contact = mobileNo.getText();
        String Email = email.getText();

        Thread.sleep(2000);

        address.clear();
        city.clear();
        state.clear();
        pin.clear();
        mobileNo.clear();
        email.clear();

        Thread.sleep(2000);

        //Putting editing information

        //WebElement address2 = driverObj.findElement(By.name("addr"));
        //WebElement city2 = driverObj.findElement(By.name("city"));
        //WebElement state2 = driverObj.findElement(By.name("state"));
        //WebElement pin2 = driverObj.findElement(By.name("pinno"));
        //WebElement mobileNo2 = driverObj.findElement(By.name("telephoneno"));
        //WebElement email2 = driverObj.findElement(By.name("emailid"));
        //WebElement pass2 = driverObj.findElement(By.xpath("/html/body/table/tbody/tr/td/table/tbody/tr[13]/td[2]/input"));
        //WebElement submitBtn2 = driverObj.findElement(By.xpath("/html/body/table/tbody/tr/td/table/tbody/tr[13]/td[2]/input[1]"));

        address.sendKeys("Mirpur " + num);
        logger.log(Status.INFO,"Address typed");
        System.out.println("Address typed..");
        city.sendKeys("Dhaka North" + randomString);
        logger.log(Status.INFO,"City typed");
        System.out.println("City name typed..");
        state.sendKeys("Dhaka Bangladesh" + randomString);
        logger.log(Status.INFO,"State name typed");
        System.out.println("State name typed..");
        pin.sendKeys(pinNum);
        logger.log(Status.INFO,"Pin number typed");
        System.out.println("Pin typed..");
        mobileNo.sendKeys(mobNum);
        logger.log(Status.INFO,"Mobile number typed");
        System.out.println("Contact number typed..");
        email.sendKeys(editMail);
        logger.log(Status.INFO,"Edited email typed");
        System.out.println("Edited email typed..");

        Thread.sleep(2000);

        submitBtn.click();

        Thread.sleep(1000);

        String Notif = driverObj.switchTo().alert().getText();
        driverObj.switchTo().alert().accept();

        String newURL = driverObj.getCurrentUrl();

        Thread.sleep(1000);

        if(Objects.equals(Notif,"No Changes made to Customer records")){
            logger.log(Status.PASS, "Customer information edited_notification message incorrect");
            System.out.println("Customer information edited_notification message wrong_PASS");
        }else{
            logger.log(Status.FAIL,"Customer information not edited");
            System.out.println("Customer information not edited_Fail");
        }

        Thread.sleep(1000);

        if(Objects.equals(newURL,"https://demo.guru99.com/v4/manager/EditCustomer.php")){
            logger.log(Status.PASS,"JS alert OK");
            System.out.println("JS alert OK_PASS");
        }else{
            logger.log(Status.FAIL, "JS alert not OK");
            System.out.println("JS alert not OK_FAIL");
        }

        Thread.sleep(1000);

        //Checking edited information

        logger.log(Status.INFO,"Checking edited information");

        WebElement cusId2 = driverObj.findElement(By.xpath("/html/body/div[2]/table/tbody/tr/td/table/tbody/tr[6]/td[2]/input"));
        WebElement submitBtn22 = driverObj.findElement(By.name("AccSubmit"));

        Thread.sleep(1000);

        cusId2.sendKeys("93196");
        logger.log(Status.INFO,"Existing Customer ID typed again");
        submitBtn22.click();
        logger.log(Status.INFO, "Submit button clicked again");

        Thread.sleep(1000);

        //Edit information
        WebElement address3 = driverObj.findElement(By.name("addr"));
        WebElement city3 = driverObj.findElement(By.name("city"));
        WebElement state3 = driverObj.findElement(By.name("state"));
        WebElement pin3 = driverObj.findElement(By.name("pinno"));
        WebElement mobileNo3 = driverObj.findElement(By.name("telephoneno"));
        WebElement email3 = driverObj.findElement(By.name("emailid"));

        Thread.sleep(1000);

        String Address3 = address3.getText();
        String City3 = city3.getText();
        String State3 = state3.getText();
        String PIN3 = pin3.getText();
        String Contact3 = mobileNo3.getText();
        String Email3 = email3.getText();

        Thread.sleep(1000);

        if(Address != Address3){
            logger.log(Status.PASS,"Address is edited");
            System.out.println("Address is edited_PASS");
        }else{
            logger.log(Status.FAIL,"Address is not edited");
            System.out.println("Address is not edited_FAIL");
        }

        if(City != City3){
            logger.log(Status.PASS,"City name is edited");
            System.out.println("City name is edited_PASS");
        }else{
            logger.log(Status.FAIL,"City name is not edited");
            System.out.println("City name is not edited_FAIL");
        }

        if(State != State3){
            logger.log(Status.PASS,"State name is edited");
            System.out.println("State name is edited_PASS");
        }else{
            logger.log(Status.FAIL,"State is not edited");
            System.out.println("State is not edited_FAIL");
        }

        if(PIN != PIN3){
            logger.log(Status.PASS,"Pin code is edited");
            System.out.println("Pin code is edited_PASS");
        }else{
            logger.log(Status.FAIL,"Pin code is not edited");
            System.out.println("Pin code is not edited_FAIL");
        }

        if(Contact != Contact3){
            logger.log(Status.PASS,"Mobile number is edited");
            System.out.println("Mobile number is edited_PASS");
        }else{
            logger.log(Status.FAIL,"Mobile number is not edited");
            System.out.println("Mobile number is not edited_FAIL");
        }

        if(Email != Email3){
            logger.log(Status.PASS,"Email address is edited");
            System.out.println("Email address is edited_PASS");
        }else{
            logger.log(Status.FAIL,"Email address is not edited");
            System.out.println("Email address is not edited_FAIL");
        }

        reports.flush();
    }

}