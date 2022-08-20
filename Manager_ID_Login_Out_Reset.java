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

public class Manager_ID_Login_Out_Reset {
    WebDriver driverObj;

    ExtentHtmlReporter htmlReporter;
    ExtentReports reports;
    ExtentTest logger;

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

    @Test
    public void ManagerID_Login_Out_Test_valid_invalid_data_input() throws InterruptedException {

        htmlReporter = new ExtentHtmlReporter(".\\Reports\\ManagerLogin_Out_Valid_Invalid_data_input_Reset_Test_Report.html");
        reports = new ExtentReports();
        reports.attachReporter(htmlReporter);

        //Info. added to report

        reports.setSystemInfo("Environment: ", "Java_Selenium_TestNG");
        reports.setSystemInfo("CPU", "64bit_2.00 GHz");
        reports.setSystemInfo("OS", "Windows 10");
        reports.setSystemInfo("", "");
        reports.setSystemInfo("Project: ", "Guru99Banking_V4");
        reports.setSystemInfo("Module: ", "Manager Login_Out");
        reports.setSystemInfo("", "");
        reports.setSystemInfo("Testing type: ", "Unit and Functional Testing");
        reports.setSystemInfo("Testing case: ", "Valid and Invalid data type input");
        reports.setSystemInfo("Test requirements: ", "Manager ID: mngr431045;  Password: gUnYqur");
        reports.setSystemInfo("", "");
        reports.setSystemInfo("ManagerCredential Expire date: ", "28-August-2022");
        reports.setSystemInfo("Data source", "Directly defined data within");
        reports.setSystemInfo("Additional Attachments", "Manager credential file_filename: demoGuruBank_manager_credential.jpg");
        reports.setSystemInfo("", "");
        reports.setSystemInfo("Test by: ", "Kumar Shimanto");

        logger = reports.createTest("Demo_Guru99_Bank_ManagerID_Login_Out_Valid_Invalid_Data_Type_Input");

        //Site open

        logger.log(Status.INFO,"Both manager UserID and passwords are valid");

        driverObj.get("https://demo.guru99.com/v4/");
        logger.log(Status.INFO, "Site opened");

        //Manager_ID Log
        WebElement userId = driverObj.findElement(By.name("uid"));
        WebElement password = driverObj.findElement(By.name("password"));
        WebElement lgnBtn = driverObj.findElement(By.name("btnLogin"));

        userId.sendKeys("mngr431045");
        logger.log(Status.INFO, "Manager UserID typed");
        password.sendKeys("gUnYqur");
        logger.log(Status.INFO, "Manager password typed");

        Thread.sleep(1000);
        lgnBtn.click();
        logger.log(Status.INFO, "Login button clicked");

        String currentPageTitle = driverObj.getTitle();
        Thread.sleep(1000);

        if (Objects.equals(currentPageTitle, "Guru99 Bank Manager HomePage")) {
            logger.log(Status.INFO, "Manager logged in_test_pass");
            System.out.println("Manager valid login test passed.");

            Thread.sleep(1000);

            WebElement LogOutBtn = driverObj.findElement(By.xpath("/html/body/div[3]/div/ul/li[15]/a"));
            LogOutBtn.click();
            String alert = driverObj.switchTo().alert().getText();
            driverObj.switchTo().alert().accept();

            String currentURL = driverObj.getCurrentUrl();

            Thread.sleep(1000);

            if ((Objects.equals(currentURL, "https://demo.guru99.com/v4/index.php")) && (Objects.equals(alert, "You Have Succesfully Logged Out!!"))) {
                logger.log(Status.PASS, "Log out_test_pass. JS alert OK_test_pass");
                System.out.println("Log out_test_pass. JS alert OK_test_pass.");
            } else if (Objects.equals(currentURL, "https://demo.guru99.com/V4/index.php") && (alert != "You Have Succesfully Logged Out!!")) {
                logger.log(Status.ERROR,"Log out_test_pass. JS alert popped up_test_fail");
                System.out.println("Log out_test_pass. JS alert popped up_test_fail.");
            } else if (!currentURL.equals("https://demo.guru99.com/V4/index.php") && (alert == "You Have Succesfully Logged Out!!")) {
                logger.log(Status.ERROR,"Log out_test_pass. JS alert popped up_test_pass_Home url is not reached");
                System.out.println("Log out_test_pass. JS alert popped up_test_pass_Home url is not reached.");
            } else {
                logger.log(Status.FAIL,"Did not log out_test_fail");
                System.out.println("Log out_test_fail");
            }
        }else{
            logger.log(Status.FAIL,"Did not login_valid login test_fail");
            System.out.println("Valid login test fail.");
            driverObj.switchTo().alert().accept();
            Thread.sleep(1000);

            if (Objects.equals(driverObj.getCurrentUrl(), "https://demo.guru99.com/V4/index.php")){
                logger.log(Status.PASS, "Valid login test_fail, JS alert OK_test_pass");
                System.out.println("Valid login_test_failed, JS alert OK_test_pass.");
            }else{
                logger.log(Status.FAIL, "Valid login test_fail, JS alert not OK_test_fail");
                System.out.println("Valid login_test_failed, JS alert OK_test_fail.");
            }
        }


        logger.log(Status.INFO,"Both userID and Password are invalid:");

        driverObj.get("https://demo.guru99.com/v4/");
        logger.log(Status.INFO, "Site opened");

        WebElement userId2 = driverObj.findElement(By.name("uid"));
        WebElement password2 = driverObj.findElement(By.name("password"));
        WebElement lgnBtn2 = driverObj.findElement(By.name("btnLogin"));

            userId2.sendKeys("mng4");
            logger.log(Status.INFO, "Manager userID typed");
            password2.sendKeys("1234");
            logger.log(Status.INFO, "Manager Password typed");
            Thread.sleep(1000);
            lgnBtn2.click();
            logger.log(Status.INFO, "Login button clicked");
            Thread.sleep(1000);

            String alertPop2 = driverObj.switchTo().alert().getText();
            driverObj.switchTo().alert().accept();

            String currentURL2 = driverObj.getCurrentUrl();

            Thread.sleep(1000);

            if (Objects.equals(driverObj.getCurrentUrl(),"https://demo.guru99.com/V4/manager/Managerhomepage.php")){
                logger.log(Status.FAIL,"Logged in when UserID and password both are invalid_test_fail");
                System.out.println("UserID and password both are invalid_test_fail");
            }else if (Objects.equals(alertPop2, "User or Password is not valid")) {
                logger.log(Status.PASS,"Did not log in when UserID and password both are invalid_test_pass, JS alert popped up_test_pass");
                System.out.println("UserID and password both are invalid_test_pass, JS alert popped up_test_pass");

                if(Objects.equals(currentURL2,"https://demo.guru99.com/v4/index.php")){
                    logger.log(Status.PASS,"JS alert OK_test_pass");
                    System.out.println("JS alert OK_test_pass");
                }
                else{
                    logger.log(Status.FAIL,"JS alert not OK_test_fail");
                    System.out.println("JS alert not OK_test_fail");
                }

            }else{
                logger.log(Status.ERROR,"Did not log in when UserID and password both are invalid_test_pass, JS alert popped up_test_fail");
                System.out.println("UserID and password both are invalid_test_pass, JS alert popped up_test_fail");
            }


        logger.log(Status.INFO,"UserID is invalid, password is valid");

        driverObj.get("https://demo.guru99.com/v4/");
        logger.log(Status.INFO, "Site opened");

        WebElement userId3 = driverObj.findElement(By.name("uid"));
        WebElement password3 = driverObj.findElement(By.name("password"));
        WebElement lgnBtn3 = driverObj.findElement(By.name("btnLogin"));

            userId3.sendKeys("mng4");
            logger.log(Status.INFO,"UserID typed");
            password3.sendKeys("gUnYqur");
            logger.log(Status.INFO, "Password typed");
            Thread.sleep(1000);
            lgnBtn3.click();
            logger.log(Status.INFO,"Login button clicked");
            Thread.sleep(1000);

            String alertPop3 = driverObj.switchTo().alert().getText();
            driverObj.switchTo().alert().accept();

            String currentURL3 = driverObj.getCurrentUrl();

            Thread.sleep(1000);

            if (Objects.equals(driverObj.getCurrentUrl(),"https://demo.guru99.com/V4/manager/Managerhomepage.php")){
                logger.log(Status.FAIL,"Logged in when UserID invalid, password valid_test_fail");
                System.out.println("UserID invalid, password valid_test_fail");
            }else if (Objects.equals(alertPop3, "User or Password is not valid")) {
                logger.log(Status.PASS,"Did not log in when UserID is invalid, password valid_test_pass");
                System.out.println("UserID invalid, password valid_test_pass, JS alert popped up_test_pass");

                if(Objects.equals(currentURL3,"https://demo.guru99.com/v4/index.php")){
                    logger.log(Status.PASS,"JS alert OK_test_pass");
                    System.out.println("JS alert OK_test_pass");
                }
                else{
                    logger.log(Status.FAIL,"JS alert not OK_test_fail");
                    System.out.println("JS alert OK_test_fail");
                }

            }else{
                logger.log(Status.ERROR,"UserID invalid, password valid_test_pass, JS alert popped up not OK_test_fail");
                System.out.println("UserID invalid, password valid_test_pass, JS alert popped up not OK_test_fail");
            }


        logger.log(Status.INFO,"UserID is valid, Password invalid :");

        driverObj.get("https://demo.guru99.com/v4/");
        logger.log(Status.INFO, "Site opened");

        WebElement userId4 = driverObj.findElement(By.name("uid"));
        WebElement password4 = driverObj.findElement(By.name("password"));
        WebElement lgnBtn4 = driverObj.findElement(By.name("btnLogin"));

        userId4.sendKeys("mngr431045");
        logger.log(Status.INFO,"UserID typed");
        password4.sendKeys("gUnY98");
        logger.log(Status.INFO, "Password typed");
        Thread.sleep(1000);
        lgnBtn4.click();
        logger.log(Status.INFO,"Login button clicked");
        Thread.sleep(1000);

        String alertPop4 = driverObj.switchTo().alert().getText();
        driverObj.switchTo().alert().accept();
        String currentURL4 = driverObj.getCurrentUrl();

        Thread.sleep(1000);

        if (Objects.equals(driverObj.getCurrentUrl(),"https://demo.guru99.com/V4/manager/Managerhomepage.php")){
            logger.log(Status.FAIL,"Logged in when UserID ia valid, password invalid_test_fail");
            System.out.println("UserID valid, password invalid_test_fail");
        }else if (Objects.equals(alertPop4, "User or Password is not valid")) {
            logger.log(Status.PASS,"Did not log in when UserID is valid, password is invalid_test_pass, JS alert popped up_test_pass");
            System.out.println("UserID valid, password invalid_test_pass, JS alert popped up_test_pass");

            if(Objects.equals(currentURL4,"https://demo.guru99.com/v4/index.php")){
                logger.log(Status.PASS,"JS alert OK_test_pass");
                System.out.println("JS alert OK_test_pass");
            }
            else{
                logger.log(Status.FAIL,"JS alert not OK_test_pass");
                System.out.println("JS alert not OK_test_fail");
            }

            }else{
                logger.log(Status.ERROR,"Did not log in when UserID is valid, password invalid_test_pass, JS alert popped up_test_fail");
                System.out.println("UserID valid, password invalid_test_pass, JS alert popped up_test_fail");
            }


        logger.log(Status.INFO,"UserID field is blank, Password is valid : ");

        driverObj.get("https://demo.guru99.com/v4/");
        logger.log(Status.INFO, "Site opened");

        WebElement userId5 = driverObj.findElement(By.name("uid"));
        WebElement password5 = driverObj.findElement(By.name("password"));
        WebElement lgnBtn5 = driverObj.findElement(By.name("btnLogin"));
        WebElement alertBlankUserId = driverObj.findElement(By.xpath("//*[@id=\"message23\"]"));

            userId5.sendKeys("");
            logger.log(Status.INFO, "UserID field kept blank");
            password5.click();
            String alertBlankUserText = alertBlankUserId.getText();
            password5.sendKeys("gUnYqur");
            logger.log(Status.INFO, "Valid password is typed");
            Thread.sleep(1000);
            lgnBtn5.click();
            logger.log(Status.INFO, "Login button is clicked");
            Thread.sleep(1000);

            String alertPop5 = driverObj.switchTo().alert().getText();
            driverObj.switchTo().alert().accept();
            String currentURL5 = driverObj.getCurrentUrl();

            Thread.sleep(1000);

            if (Objects.equals(driverObj.getCurrentUrl(),"https://demo.guru99.com/V4/manager/Managerhomepage.php")){
                logger.log(Status.FAIL,"Logged in when UserID field is blank, password valid_test_fail");
                System.out.println("UserID field blank, password valid_test_fail");
            }else if (Objects.equals(alertPop5, "User or Password is not valid")) {
                logger.log(Status.PASS,"Did not log in when UserID field blank, password valid_test_pass");
                System.out.println("UserID field blank, password valid_test_passed, JS alert popped up_test_pass");

                if(Objects.equals(currentURL5,"https://demo.guru99.com/v4/index.php")){
                    logger.log(Status.PASS,"JS alert OK_test_pass");
                    System.out.println("JS alert OK_test_pass");
                }
                else{
                    logger.log(Status.FAIL,"JS alert not OK_test_fail");
                    System.out.println("JS alert not OK_test_fail");
                }

            }else{
                logger.log(Status.ERROR,"Did not log in when UserID field is blank, password valid_test_pass, JS alert popped up_test_fail");
                System.out.println("UserID field blank, password valid_test_pass, JS alert popped up_test_fail");
            }

            if(Objects.equals(alertBlankUserText,"User-ID must not be blank")){
                logger.log(Status.PASS,"Blank userID JS alert OK_test_pass");
                System.out.println("Blank userID JS alert OK_test_pass");
            }else{
                logger.log(Status.FAIL,"Blank userID JS alert not OK_test_fail");
                System.out.println("Blank userID JS alert not Ok_test_fail");
            }


        logger.log(Status.INFO,"UserID is valid, Password field is blank : ");

        driverObj.get("https://demo.guru99.com/v4/");
        logger.log(Status.INFO, "Site opened");

        WebElement userId6 = driverObj.findElement(By.name("uid"));
        WebElement password6 = driverObj.findElement(By.name("password"));
        WebElement lgnBtn6 = driverObj.findElement(By.name("btnLogin"));
        WebElement alertBlankPass = driverObj.findElement(By.xpath("//*[@id=\"message18\"]"));
        WebElement body6 = driverObj.findElement(By.xpath("/html/body"));

            userId6.sendKeys("mngr431045");
            logger.log(Status.INFO, "Valid userID is typed");
            password6.sendKeys("");
            logger.log(Status.INFO, "Password field is kept blank");
            body6.click();
            String alertBlankPassText = alertBlankPass.getText();
            Thread.sleep(1000);
            lgnBtn6.click();
            logger.log(Status.INFO, "Login button is clicked");
            Thread.sleep(1000);

            String alertPop6 = driverObj.switchTo().alert().getText();
            driverObj.switchTo().alert().accept();
            String currentURL6 = driverObj.getCurrentUrl();

            Thread.sleep(1000);

            if (Objects.equals(driverObj.getCurrentUrl(),"https://demo.guru99.com/V4/manager/Managerhomepage.php")){
                logger.log(Status.FAIL,"Logged in when UserID is valid, password field is blank_test_fail");
                System.out.println("UserID valid, password field blank_test_fail");
            }else if (Objects.equals(alertPop6, "User or Password is not valid")) {
                logger.log(Status.PASS,"Did not log in when UserID field is valid, password field is blank_test_pass");
                System.out.println("UserID valid, password field blank_test_passed, JS alert popped up_test_pass");

                if(Objects.equals(currentURL6,"https://demo.guru99.com/v4/index.php")){
                    logger.log(Status.PASS,"JS alert OK_test_pass");
                    System.out.println("JS alert OK_test_pass");
                }
                else{
                    logger.log(Status.FAIL,"JS alert not OK_test_fail");
                    System.out.println("JS alert OK_test_fail");
                }
            }else{
                logger.log(Status.ERROR,"Did not log in when UserID field is blank, password valid_test_pass, JS alert popped up_test_fail");
                System.out.println("UserID valid, password field blank_test_pass, JS alert popped up_test_fail");
            }

            if(Objects.equals(alertBlankPassText,"Password must not be blank")){
                logger.log(Status.PASS,"Blank password JS alert_test_pass");
                System.out.println("Blank password JS alert_test_pass");
            }else{
                logger.log(Status.FAIL,"Blank password JS alert_test_fail");
                System.out.println("Blank password JS alert_test_fail");
            }


        logger.log(Status.INFO,"UserID and password both fields are blank: ");

        driverObj.get("https://demo.guru99.com/v4/");
        logger.log(Status.INFO, "Site opened");

        WebElement userId7 = driverObj.findElement(By.name("uid"));
        WebElement password7 = driverObj.findElement(By.name("password"));
        WebElement lgnBtn7 = driverObj.findElement(By.name("btnLogin"));
        WebElement alertBlankUserId2 = driverObj.findElement(By.xpath("//*[@id=\"message23\"]"));
        WebElement alertBlankPass2 = driverObj.findElement(By.xpath("//*[@id=\"message18\"]"));
        WebElement body7 = driverObj.findElement(By.xpath("/html/body"));

            userId7.sendKeys("");
            logger.log(Status.INFO, "UserID field is kept blank");
            password7.click();
            String alertBlankUserIdText2 = alertBlankUserId2.getText();
            password7.sendKeys("");
            logger.log(Status.INFO, "Password field is kept blank");
            body7.click();
            String alertBlankPassText2 = alertBlankPass2.getText();
            lgnBtn7.click();
            logger.log(Status.INFO, "Login button clicked");
            Thread.sleep(1000);

            String alertPop7 = driverObj.switchTo().alert().getText();
            driverObj.switchTo().alert().accept();
            String currentURL7 = driverObj.getCurrentUrl();

            Thread.sleep(1000);

            if (Objects.equals(driverObj.getCurrentUrl(),"https://demo.guru99.com/V4/manager/Managerhomepage.php")){
                logger.log(Status.FAIL,"Logged in when UserID and password both fields are blank_test_fail");
                System.out.println("UserID and password both fields blank_test_fail");
            }else if (Objects.equals(alertPop7, "User or Password is not valid")) {
                logger.log(Status.PASS,"Did not log in when UserID and password both fields are blank_test_pass");
                System.out.println("UserID and password both fields blank_test_passed, JS alert popped up_test_pass");

                if(Objects.equals(currentURL7,"https://demo.guru99.com/v4/index.php")){
                    logger.log(Status.PASS,"JS alert OK_test_pass");
                    System.out.println("JS alert OK_test_pass");
                } else{
                    logger.log(Status.FAIL,"JS alert not OK_test_fail");
                    System.out.println("JS alert not OK_test_fail");
                }
            }else{
                logger.log(Status.ERROR,"Did not log in when UserID and password both fields are blank_test_pass, JS alert popped up_test_fail");
                System.out.println("UserID and password both fields blank_test_pass, JS alert popped up_test_fail");
            }

            if(Objects.equals(alertBlankUserIdText2,"User-ID must not be blank") && Objects.equals(alertBlankPassText,"Password must not be blank")){
                logger.log(Status.PASS,"Blank userId and password fileds_JS alerts OK_test_pass");
                System.out.println("Blank userId and password fileds_JS alerts OK_test_pass");
            }else if (Objects.equals(alertBlankUserIdText2,"User-ID must not be blank") || Objects.equals(alertBlankPassText,"Password must not be blank")){
                logger.log(Status.FAIL,"Blank userId and password fileds_JS alerts not OK_test_fail_for one field:");
                System.out.println("Blank userId and password fileds_JS alerts not OK_test_fail_for one field:");

                if(Objects.equals(alertBlankPassText2,"Password must not be blank")){
                    logger.log(Status.INFO,"Blank userId_JS alert not OK_test_fail");
                    System.out.println("Blank userId_JS alert_test_fail");
                }else{
                    logger.log(Status.INFO,"Blank password_JS alert not OK_test_fail");
                    System.out.println("Blank password_JS alert_test_fail");
                }
            }else{
                logger.log(Status.FAIL,"For both blank userId and password fileds_JS alerts not OK_test_fail");
                System.out.println("For both blank userId and password fileds_JS alerts not OK_test_fail");
            }



        logger.log(Status.INFO,"Reset functionality on UserID and password fileds: ");

        driverObj.get("https://demo.guru99.com/v4/");
        logger.log(Status.INFO, "Site opened");

        WebElement userId8 = driverObj.findElement(By.name("uid"));
        WebElement password8 = driverObj.findElement(By.name("password"));
        WebElement ResetBtn8 = driverObj.findElement(By.name("btnReset"));

            userId8.sendKeys("mngr431045");
            logger.log(Status.INFO, "UserID typed");
            password8.sendKeys("gUnYqur");
            logger.log(Status.INFO, "Password field typed");
            Thread.sleep(1000);
            ResetBtn8.click();
            logger.log(Status.INFO, "Reset button clicked");
            Thread.sleep(1000);


            if (Objects.equals(userId8.getText(), "") && Objects.equals(password8.getText(), "")) {
                logger.log(Status.PASS,"Login fields are reset_test_pass");
                System.out.println("Login fields reset_test_pass");
            }else if(Objects.equals(userId8.getText(), "") || Objects.equals(password8.getText(), "")) {
                logger.log(Status.FAIL,"Login fields are not reset_test_fail");
                System.out.println("Login fields reset_test_fail");

                if (Objects.equals(userId8.getText() , "")) {
                    logger.log(Status.INFO,"Login password field not reset");
                    System.out.println("Login password field not reset");
                }else {
                    logger.log(Status.INFO,"Login userID field not reset");
                    System.out.println("Login userID field not reset");
                }

            } else {
                logger.log(Status.INFO,"Login fields reset_test_fail_for both userID and password fields");
                System.out.println("Login fields reset_test_fail_for both userID and password fields");
            }

        reports.flush();
    }
}
