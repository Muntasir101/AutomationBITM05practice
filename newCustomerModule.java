package Project_DemoGuru99Banking;

import Project_DemoGuru99Banking.Utils.ExcellMethods;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class newCustomerModule {
    WebDriver driverObj;

    ExtentHtmlReporter htmlReporter;
    ExtentReports reports;
    ExtentTest logger;

    int rndNum = (int) (Math.random() * 1001);
    String EmailValid = "validMail" + rndNum + "@ymail.com";

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


    @Test(priority=3, description="New customer creation with invalid data inputs_blank,chars,special chars,numbers")
    public void newCustomer_Module_Test_invalid_data_input() throws InterruptedException {

        htmlReporter = new ExtentHtmlReporter(".\\Reports\\newCustomerModule_Invalid_data(blank,char,sp.char,no.)_Test_Report.html");
        reports = new ExtentReports();
        reports.attachReporter(htmlReporter);

        //Info. added to report

        reports.setSystemInfo("Environment: ", "Java_Selenium_TestNG");
        reports.setSystemInfo("CPU", "64bit_2.00 GHz");
        reports.setSystemInfo("OS", "Windows 10");
        reports.setSystemInfo("", "");
        reports.setSystemInfo("Project: ", "Guru99Banking_V4");
        reports.setSystemInfo("Module: ", "New Customer");
        reports.setSystemInfo("", "");
        reports.setSystemInfo("Testing type: ", "Unit and Functional Testing");
        reports.setSystemInfo("Testing case: ", "Invalid data type input");
        reports.setSystemInfo("Test requirements: ", "Manager ID: mngr431045;  Password: gUnYqur");
        reports.setSystemInfo("", "");
        reports.setSystemInfo("ManagerCredential Expire date: ", "28-August-2022");
        reports.setSystemInfo("Data source", "Both external Excell data sheet and directly defined data within");
        reports.setSystemInfo("Additional Attachments", "Manager credential file_filename: demoGuruBank_manager_credential.jpg");
        reports.setSystemInfo("", "");
        reports.setSystemInfo("Test by: ", "Kumar Shimanto");

        logger = reports.createTest("Demo_Guru99_Bank_New_Customer_Module_Test_Invalid_Data_Type(blank,char,sp.char,no.)_Input");

        //Excel Implementation
        ExcellMethods reader = new ExcellMethods("./src/main/java/Project_DemoGuru99Banking/Utils/Guru99Banking_NewCustomer_Data.xlsx");
        String sheetName = "New_Customer_Create_Data";

        //Site open

        driverObj.get("https://demo.guru99.com/v4/");
        logger.log(Status.INFO, "Site opened");

        //Manager_ID Log
        WebElement userId = driverObj.findElement(By.name("uid"));
        WebElement password = driverObj.findElement(By.name("password"));
        WebElement lgnBtn = driverObj.findElement(By.name("btnLogin"));

        userId.sendKeys("mngr431045");
        logger.log(Status.INFO, "Manager ID typed");
        password.sendKeys("gUnYqur");
        logger.log(Status.INFO, "Manager password typed");;
        Thread.sleep(2000);
        lgnBtn.click();
        logger.log(Status.INFO, "Login button clicked");

        String currentPageTitle1 = driverObj.getTitle();
        Thread.sleep(2000);

        WebElement newCustomerModule = driverObj.findElement(By.xpath("/html/body/div[3]/div/ul/li[2]/a"));
        newCustomerModule.click();
        logger.log(Status.INFO, "New customer module clicked");
        Thread.sleep(2000);

        driverObj.get("https://demo.guru99.com/v4/manager/addcustomerpage.php");

        String currentPageTitle2 = driverObj.getTitle();
        Thread.sleep(2000);

        if (Objects.equals(currentPageTitle1, "Guru99 Bank Manager HomePage")) {
            logger.log(Status.PASS, "Manager logged in");
            System.out.println("Manager logged in");
        } else {
            logger.log(Status.FAIL, "Manager log in failed");
            System.out.println("Manager did not log in, try again with valid credential");
        }

        if (Objects.equals(currentPageTitle2, "Guru99 Bank New Customer Entry Page")) {
            logger.log(Status.PASS, "New customer module opened");
            System.out.println("New customer module opened");
        } else {
            logger.log(Status.FAIL, "New customer module did not open");
            System.out.println("New Customer Module did not open, try again.");
        }

        //Add new customer module log

        int rowCount = reader.getRowCount(sheetName);

        for (int rowNum = 2; rowNum <= 7; rowNum++) {
            String CustomerName = reader.getCellData(sheetName, "Customer_Name", rowNum);
            String BirthDate = reader.getCellData(sheetName, "DateOfBirth", rowNum);
            String AddressInfo = reader.getCellData(sheetName, "Address", rowNum);
            String City = reader.getCellData(sheetName, "City", rowNum);
            String State = reader.getCellData(sheetName, "State", rowNum);
            String Pin = reader.getCellData(sheetName, "Pin", rowNum);
            String Contact = reader.getCellData(sheetName, "Contact", rowNum);
            String Email = reader.getCellData(sheetName, "Email", rowNum);
            String Password = reader.getCellData(sheetName, "Password", rowNum);

            driverObj.get("https://demo.guru99.com/v4/manager/addcustomerpage.php");
            logger.log(Status.INFO, "Add customer module opened");

            WebElement customerName = driverObj.findElement(By.xpath("/html/body/table/tbody/tr/td/table/tbody/tr[4]/td[2]/input"));
            WebElement maleBtn = driverObj.findElement(By.name("rad1"));
            WebElement femaleBtn = driverObj.findElement(By.xpath("/html/body/table/tbody/tr/td/table/tbody/tr[5]/td[2]/input[2]"));
            WebElement dateBirth = driverObj.findElement(By.name("dob"));
            WebElement address = driverObj.findElement(By.name("addr"));
            WebElement city = driverObj.findElement(By.name("city"));
            WebElement state = driverObj.findElement(By.name("state"));
            WebElement pin = driverObj.findElement(By.name("pinno"));
            WebElement mobileNo = driverObj.findElement(By.name("telephoneno"));
            WebElement email = driverObj.findElement(By.name("emailid"));
            WebElement pass = driverObj.findElement(By.xpath("/html/body/table/tbody/tr/td/table/tbody/tr[13]/td[2]/input"));
            WebElement submitBtn = driverObj.findElement(By.xpath("/html/body/table/tbody/tr/td/table/tbody/tr[14]/td[2]/input[1]"));
            WebElement addNewCustomerTitle = driverObj.findElement(By.xpath("/html/body/table/tbody/tr/td/table/tbody/tr[1]/td/p"));

            customerName.sendKeys(CustomerName);
            logger.log(Status.INFO, "Customer name typed");
            femaleBtn.click();
            logger.log(Status.INFO, "Gender selected female");
            dateBirth.sendKeys(BirthDate);
            logger.log(Status.INFO, "Birthdate typed");
            address.sendKeys(AddressInfo);
            logger.log(Status.INFO, "Address typed");
            city.sendKeys(City);
            logger.log(Status.INFO, "City name typed");
            state.sendKeys(State);
            logger.log(Status.INFO, "State name typed");
            pin.sendKeys(Pin);
            logger.log(Status.INFO, "Pin typed");
            mobileNo.sendKeys(Contact);
            logger.log(Status.INFO, "Contact number typed");
            email.sendKeys(Email);
            logger.log(Status.INFO, "Email typed");
            pass.sendKeys(Password);
            logger.log(Status.INFO, "Password typed");

            addNewCustomerTitle.click();

            if (Objects.equals(Email, "")) {

                logger.log(Status.INFO, "Email: " + Email);
                System.out.println("Email: " + Email);

                WebElement blankCustomer_JS_notif = driverObj.findElement(By.xpath("//*[@id=\"message\"]"));
                String notif1 = blankCustomer_JS_notif.getText();

                WebElement blankDate_JS_notif = driverObj.findElement(By.id("message24"));
                String notif2 = blankDate_JS_notif.getText();

                WebElement blankAddress_JS_notif = driverObj.findElement(By.id("message3"));
                String notif3 = blankAddress_JS_notif.getText();

                WebElement blankCity_JS_notif = driverObj.findElement(By.id("message4"));
                String notif4 = blankCity_JS_notif.getText();

                WebElement blankState_JS_notif = driverObj.findElement(By.id("message5"));
                String notif5 = blankState_JS_notif.getText();

                WebElement blankPIN_JS_notif = driverObj.findElement(By.id("message6"));
                String notif6 = blankPIN_JS_notif.getText();

                WebElement blankMobile_JS_notif = driverObj.findElement(By.id("message7"));
                String notif7 = blankMobile_JS_notif.getText();

                WebElement blankEmail_JS_notif = driverObj.findElement(By.id("message9"));
                String notif8 = blankEmail_JS_notif.getText();

                WebElement blankPass_JS_notif = driverObj.findElement(By.id("message18"));
                String notif9 = blankPass_JS_notif.getText();

                submitBtn.click();
                logger.log(Status.INFO, "Submit button clicked");
                Thread.sleep(2000);

                String alertPop = driverObj.switchTo().alert().getText();
                Thread.sleep(2000);

                if (Objects.equals(alertPop, "please fill all fields")) {

                    driverObj.switchTo().alert().accept();

                    logger.log(Status.INFO, alertPop);
                    System.out.println(alertPop);

                    logger.log(Status.PASS, "New customer creation failed with blank input fields_test_passed");
                    System.out.println("New customer creation failed with blank input fields_test_passed");

                    String initUrl = driverObj.getCurrentUrl();

                    if (Objects.equals(initUrl, "https://demo.guru99.com/v4/manager/addcustomerpage.php")) {
                        logger.log(Status.PASS, "JS alert OK_test_passed");
                        System.out.println("JS alert OK_test_passed");
                    } else {
                        logger.log(Status.FAIL, "JS alert not OK_test_failed");
                        System.out.println("JS alert not OK_test_failed");
                    }

                    if (Objects.equals(notif1, "Customer name must not be blank")) {
                        logger.log(Status.PASS, "Blank customer name field JS alert Ok_test_passed");
                        System.out.println("Blank customer name field JS alert Ok_test_passed");
                    } else {
                        logger.log(Status.FAIL, "Blank customer name field JS alert not Ok_test_failed");
                        System.out.println("Blank customer name field JS alert not Ok_test_failed");
                    }

                    if (Objects.equals(notif2, "Date Field must not be blank")) {
                        logger.log(Status.PASS, "Blank date field JS alert Ok_test_passed");
                        System.out.println("Blank date field JS alert Ok_test_passed");
                    } else {
                        logger.log(Status.FAIL, "Blank date field JS alert not Ok_test_failed");
                        System.out.println("Blank date field JS alert not Ok_test_failed");
                    }

                    if (Objects.equals(notif3, "Address Field must not be blank")) {
                        logger.log(Status.PASS, "Blank address field JS alert Ok_test_passed");
                        System.out.println("Blank address field JS alert Ok_test_passed");
                    } else {
                        logger.log(Status.FAIL, "Blank address field JS alert not Ok_test_failed");
                        System.out.println("Blank address field JS alert not Ok_test_failed");
                    }

                    if (Objects.equals(notif4, "City Field must not be blank")) {
                        logger.log(Status.PASS, "Blank city field JS alert Ok_test_passed");
                        System.out.println("Blank city field JS alert Ok_test_passed");
                    } else {
                        logger.log(Status.FAIL, "Blank city field JS alert not Ok_test_failed");
                        System.out.println("Blank city field JS alert not Ok_test_failed");
                    }

                    if (Objects.equals(notif5, "State must not be blank")) {
                        logger.log(Status.PASS, "Blank state field JS alert Ok_test_passed");
                        System.out.println("Blank state field JS alert Ok_test_passed");
                    } else {
                        logger.log(Status.FAIL, "Blank state field JS alert not Ok_test_failed");
                        System.out.println("Blank state field JS alert not Ok_test_failed");
                    }

                    if (Objects.equals(notif6, "PIN Code must not be blank")) {
                        logger.log(Status.PASS, "Blank PIN field JS alert Ok_test_passed");
                        System.out.println("Blank PIN field JS alert Ok_test_passed");
                    } else {
                        logger.log(Status.FAIL, "Blank PIN field JS alert not Ok_test_failed");
                        System.out.println("Blank PIN field JS alert not Ok_test_failed");
                    }

                    if (Objects.equals(notif7, "Mobile no must not be blank")) {
                        logger.log(Status.PASS, "Blank mobile no. field JS alert Ok_test_passed");
                        System.out.println("Blank mobile no. field JS alert Ok_test_passed");
                    } else {
                        logger.log(Status.FAIL, "Blank mobile no. field JS alert not Ok_test_failed");
                        System.out.println("Blank mobile no. field JS alert not Ok_test_failed");
                    }

                    if (Objects.equals(notif8, "Email-ID must not be blank")) {
                        logger.log(Status.PASS, "Blank email field JS alert Ok_test_passed");
                        System.out.println("Blank email field JS alert Ok_test_passed");
                    } else {
                        logger.log(Status.FAIL, "Blank email field JS alert not Ok_test_failed");
                        System.out.println("Blank email field JS alert not Ok_test_failed");
                    }

                    if (Objects.equals(notif9, "Password must not be blank")) {
                        logger.log(Status.PASS, "Blank password field JS alert Ok_test_passed");
                        System.out.println("Blank password field JS alert Ok_test_passed");
                    } else {
                        logger.log(Status.FAIL,"Blank password field JS alert not Ok_test_failed");
                        System.out.println("Blank password field JS alert not Ok_test_failed");
                    }
                } else {
                    logger.log(Status.FAIL, "New customer creation with blank input fields_test_failed");
                    System.out.println("New customer creation with blank input fields_unit_test_failed");
                }

            } else if (!Email.equals("")) {
                logger.log(Status.INFO, "Email: " + Email);
                System.out.println("Email: " + Email);

                Pattern p = Pattern.compile("[^A-Za-z0-9 ]");
                Matcher m = p.matcher(CustomerName);
                boolean b = m.find();

                if (b && !Pin.matches(".*[a-zA-Z].*")) {
                    WebElement Customer_JS_notif = driverObj.findElement(By.xpath("//*[@id=\"message\"]"));
                    String notif1 = Customer_JS_notif.getText();

                    WebElement Date_JS_notif = driverObj.findElement(By.id("message24"));
                    String notif2 = Date_JS_notif.getText();

                    WebElement Address_JS_notif = driverObj.findElement(By.id("message3"));
                    String notif3 = Address_JS_notif.getText();

                    WebElement City_JS_notif = driverObj.findElement(By.id("message4"));
                    String notif4 = City_JS_notif.getText();

                    WebElement State_JS_notif = driverObj.findElement(By.id("message5"));
                    String notif5 = State_JS_notif.getText();

                    WebElement PIN_JS_notif = driverObj.findElement(By.id("message6"));
                    String notif6 = PIN_JS_notif.getText();

                    WebElement Mobile_JS_notif = driverObj.findElement(By.id("message7"));
                    String notif7 = Mobile_JS_notif.getText();

                    submitBtn.click();
                    logger.log(Status.INFO, "Submit button clicked..");
                    Thread.sleep(2000);

                    String alertPop = driverObj.switchTo().alert().getText();
                    Thread.sleep(2000);

                    if (Objects.equals(alertPop, "please fill all fields")) {
                        driverObj.switchTo().alert().accept();

                        logger.log(Status.INFO, alertPop);
                        logger.log(Status.PASS, "New customer creation failed with special char. input at associated fields_test_passed");

                        System.out.println(alertPop);
                        System.out.println("New customer creation failed with special char. input at associated fields_test_passed");

                        String initUrl = driverObj.getCurrentUrl();

                        if (Objects.equals(initUrl, "https://demo.guru99.com/v4/manager/addcustomerpage.php")) {
                            logger.log(Status.PASS, "JS alert OK_test_passed");
                            System.out.println("JS alert OK_test_passed");
                        } else {
                            logger.log(Status.FAIL, "JS alert not OK_test_failed");
                            System.out.println("JS alert not OK_test_failed");
                        }

                        if (Objects.equals(notif1, "Special characters are not allowed")) {
                            logger.log(Status.PASS, "Special character input at customer name field JS alert Ok_test_passed");
                            System.out.println("Special character input at customer name field JS alert Ok_test_passed");
                        } else {
                            logger.log(Status.FAIL, "Special character input at customer name field JS alert not Ok_test_failed");
                            System.out.println("Special character input at customer name field JS alert not Ok_test_failed");
                        }

                        if (Objects.equals(notif2,"Date Field must not be blank")) {
                             logger.log(Status.PASS,"Special char input at date field JS alert Ok_test_passed");
                             System.out.println("Special char input at date field JS alert Ok_test_passed");
                          } else {
                              logger.log(Status.PASS,"Special char input at date field JS alert not Ok_test_failed");
                              System.out.println("Special char input at date field JS alert not Ok_test_failed");
                        }

                        if (Objects.equals(notif3, "Special characters are not allowed")) {
                            logger.log(Status.PASS, "Special characters at address field JS alert Ok_test_passed");
                            System.out.println("Special characters at address field JS alert Ok_test_passed");
                        } else {
                            logger.log(Status.FAIL, "Special characters at address field JS alert not Ok_test_failed");
                            System.out.println("Special characters at address field JS alert not Ok_test_failed");
                        }

                        if (Objects.equals(notif4, "Special characters are not allowed")) {
                            logger.log(Status.PASS, "Special characters at city field JS alert Ok_test_passed");
                            System.out.println("Special characters at city field JS alert Ok_test_passed");
                        } else {
                            logger.log(Status.FAIL, "Special characters at city field JS alert not Ok_test_failed");
                            System.out.println("Special characters at city field JS alert not Ok_test_failed");
                        }

                        if (Objects.equals(notif5, "Special characters are not allowed")) {
                            logger.log(Status.PASS, "Special characters at state field JS alert Ok_test_passed");
                            System.out.println("Special characters at state field JS alert Ok_test_passed");
                        } else {
                            logger.log(Status.FAIL, "Special characters at state field JS alert not Ok_test_failed");
                            System.out.println("Special characters at state field JS alert not Ok_test_failed");
                        }

                        if (Objects.equals(notif6, "Special characters are not allowed")) {
                            logger.log(Status.PASS, "Special characters at PIN field JS alert Ok_test_passed");
                            System.out.println("Special characters at PIN field JS alert Ok_test_passed");
                        } else {
                            logger.log(Status.FAIL, "Special characters at PIN field JS alert not Ok_test_failed");
                            System.out.println("Special characters at PIN field JS alert not Ok_test_failed");
                        }

                        if (Objects.equals(notif7, "Special characters are not allowed")) {
                            logger.log(Status.PASS, "Special characters at mobile no. field JS alert Ok_test_passed");
                            System.out.println("Special characters at mobile no. field JS alert Ok_test_passed");
                        } else {
                            logger.log(Status.FAIL, "Special characters at mobile no field JS alert not Ok_test_failed");
                            System.out.println("Special characters at mobile no field JS alert not Ok_test_failed");
                        }

                    } else {
                        logger.log(Status.FAIL, "Special char input restriction functionality not ok_test_failed");
                        System.out.println("Special char input restriction functionality not ok_test_failed");
                    }

                } else if ((CustomerName.matches(".*[0-9].*"))) {

                    WebElement Customer_JS_notif = driverObj.findElement(By.xpath("//*[@id=\"message\"]"));
                    String notif1 = Customer_JS_notif.getText();

                    WebElement City_JS_notif = driverObj.findElement(By.id("message4"));
                    String notif4 = City_JS_notif.getText();

                    WebElement State_JS_notif = driverObj.findElement(By.id("message5"));
                    String notif5 = State_JS_notif.getText();

                    submitBtn.click();
                    logger.log(Status.INFO, "Submit button clicked");
                    System.out.println("Submit button clicked");
                    Thread.sleep(2000);

                    String alertPop = driverObj.switchTo().alert().getText();
                    Thread.sleep(2000);

                    if (Objects.equals(alertPop, "please fill all fields")) {
                        driverObj.switchTo().alert().accept();

                        logger.log(Status.INFO, alertPop);
                        logger.log(Status.PASS, "New customer creation failed with number input at associated fields_test_passed");

                        System.out.println(alertPop);
                        System.out.println("New customer creation failed with number input at associated fields_test_passed");

                        String initUrl = driverObj.getCurrentUrl();

                        if (Objects.equals(initUrl, "https://demo.guru99.com/v4/manager/addcustomerpage.php")) {
                            logger.log(Status.PASS, "JS alert OK_test_passed");
                            System.out.println("JS alert OK_test_passed");
                        } else {
                            logger.log(Status.FAIL, "JS alert not OK_test_failed");
                            System.out.println("JS alert not OK_test_failed");
                        }

                        if (Objects.equals(notif1, "Numbers are not allowed")) {
                            logger.log(Status.PASS, "Number input at customer name field JS alert Ok_test_passed");
                            System.out.println("Number input at customer name field JS alert Ok_test_passed");
                        } else {
                            logger.log(Status.FAIL, "Number input at customer name field JS alert not Ok_test_failed");
                            System.out.println("Number input at customer name field JS alert not Ok_test_failed");
                        }

                        if (Objects.equals(notif4, "Numbers are not allowed")) {
                            logger.log(Status.PASS, "Numbers at city field JS alert Ok_test_passed");
                            System.out.println("Numbers at city field JS alert Ok_test_passed");
                        } else {
                            logger.log(Status.FAIL, "Numbers at city field JS alert not Ok_test_failed");
                            System.out.println("Numbers at city field JS alert not Ok_test_failed");
                        }

                        if (Objects.equals(notif5, "Numbers are not allowed")) {
                            logger.log(Status.PASS, "Numbers at state field JS alert Ok_test_passed");
                            System.out.println("Numbers at state field JS alert Ok_test_passed");
                        } else {
                            logger.log(Status.FAIL, "Numbers at state field JS alert not Ok_test_failed");
                            System.out.println("Numbers at state field JS alert not Ok_test_failed");
                        }

                    } else {
                        logger.log(Status.FAIL, "Number input restriction at associated fields functionality not OK.");
                        System.out.println("Number input restriction at associated fields functionality not OK.");
                    }

                } else if (Pin.matches(".*[a-zA-Z].*")) {
                    WebElement PIN_JS_notif = driverObj.findElement(By.id("message6"));
                    String notif6 = PIN_JS_notif.getText();

                    submitBtn.click();
                    logger.log(Status.INFO, "Submit button clicked");
                    Thread.sleep(2000);

                    String alertPop = driverObj.switchTo().alert().getText();
                    Thread.sleep(2000);

                    if (Objects.equals(alertPop, "please fill all fields")) {
                        driverObj.switchTo().alert().accept();

                        logger.log(Status.INFO, alertPop);
                        logger.log(Status.PASS, "New customer creation failed with character input at PIN field_test_passed");

                        System.out.println(alertPop);
                        System.out.println("New customer creation failed with character input at PIN field_test_passed");

                        String initUrl = driverObj.getCurrentUrl();

                        if (Objects.equals(initUrl, "https://demo.guru99.com/v4/manager/addcustomerpage.php")) {
                            logger.log(Status.PASS, "JS alert OK_test_passed");
                            System.out.println("JS alert OK_test_passed");
                        } else {
                            logger.log(Status.FAIL, "JS alert not OK_test_failed");
                            System.out.println("JS alert not OK_test_failed");
                        }

                        if (Objects.equals(notif6, "Characters are not allowed")) {
                            logger.log(Status.PASS, "Character input at PIN field JS alert Ok_test_passed");
                            System.out.println("Character input at PIN field JS alert Ok_test_passed");
                        } else {
                            logger.log(Status.FAIL, "Character input at PIN field JS alert not Ok_test_failed");
                            System.out.println("Character input at PIN field JS alert not Ok_test_failed");
                        }
                    } else {
                        logger.log(Status.FAIL, "Character input restriction at PIN field functionality not OK_test_failed");
                        System.out.println("Character input restriction at PIN field functionality not OK_test_failed");
                    }

                } else if ((CustomerName.matches(".*[a-zA-Z].*")) && Pin.length() != 6) {
                    WebElement PIN_JS_notif = driverObj.findElement(By.id("message6"));
                    String notif6 = PIN_JS_notif.getText();

                    submitBtn.click();
                    logger.log(Status.INFO, "Submit button clicked");
                    Thread.sleep(1000);

                    String alertPop = driverObj.switchTo().alert().getText();
                    Thread.sleep(1000);

                    if (Objects.equals(alertPop, "please fill all fields")) {
                        driverObj.switchTo().alert().accept();

                        logger.log(Status.INFO, alertPop);
                        logger.log(Status.PASS, "New customer creation failed with not 6 digit at PIN field_test_passed");

                        System.out.println(alertPop);
                        System.out.println("New customer creation failed with not 6 digit at PIN field_test_passed");

                        String initUrl = driverObj.getCurrentUrl();

                        if (Objects.equals(initUrl, "https://demo.guru99.com/v4/manager/addcustomerpage.php")) {
                            logger.log(Status.PASS, "JS alert OK_test_passed");
                            System.out.println("JS alert OK_test_passed");
                        } else {
                            logger.log(Status.FAIL, "JS alert not OK_test_failed");
                            System.out.println("JS alert not OK_test_failed");
                        }

                        if (Objects.equals(notif6, "PIN Code must have 6 Digits")) {
                            logger.log(Status.PASS, "Not 6 digits at PIN field JS alert Ok_test_passed");
                            System.out.println("Not 6 digits at PIN field JS alert Ok_test_passed");
                        } else {
                            logger.log(Status.FAIL, "Not 6 digits at PIN field JS alert not Ok_test_failed");
                            System.out.println("Not 6 digits at PIN field JS alert not Ok_test_failed");
                        }
                    } else {
                        logger.log(Status.FAIL, "6 digits restriction at PIN field functionality not OK_test_failed");
                        System.out.println("6 digits restriction at PIN field functionality not OK_test_failed");
                    }

                } else if ((CustomerName.matches(".*[A-Za-z].*")) && Contact.matches(".*[a-zA-Z].*")) {
                    WebElement Mobile_JS_notif = driverObj.findElement(By.id("message7"));
                    String notif7 = Mobile_JS_notif.getText();

                    submitBtn.click();
                    logger.log(Status.INFO, "Submit button clicked");
                    Thread.sleep(1000);

                    String alertPop = driverObj.switchTo().alert().getText();
                    Thread.sleep(1000);

                    if (Objects.equals(alertPop, "please fill all fields")) {
                        driverObj.switchTo().alert().accept();

                        logger.log(Status.INFO, alertPop);
                        logger.log(Status.PASS, "New customer creation failed with character input at mobile no. field_test_passed");

                        System.out.println(alertPop);
                        System.out.println("New customer creation failed with character input at mobile no. field_test_passed");

                        String initUrl = driverObj.getCurrentUrl();

                        if (Objects.equals(initUrl, "https://demo.guru99.com/v4/manager/addcustomerpage.php")) {
                            logger.log(Status.PASS, "JS alert OK_test_passed");
                            System.out.println("JS alert OK_test_passed");
                        } else {
                            logger.log(Status.FAIL, "JS alert not OK_test_failed");
                            System.out.println("JS alert not OK_test_failed");
                        }

                        if (Objects.equals(notif7, "Characters are not allowed")) {
                            logger.log(Status.PASS, "Character input at mobile no. field JS alert Ok_test_passed");
                            System.out.println("Character input at mobile no. field JS alert Ok_test_passed");
                        } else {
                            logger.log(Status.FAIL, "Character input at mobile no. field JS alert not Ok_test_failed");
                            System.out.println("Character input at mobile no. field JS alert not Ok_test_failed");
                        }
                    } else {
                        logger.log(Status.FAIL, "Character input restriction at mobile no. field functionality not ok.");
                        System.out.println("Character input restriction at mobile no. field functionality not ok.");
                    }

                }

            }
        }

        reports.flush();
    }

    @Test(priority=4, description="New customer creation with invalid email data type input")
    public void newCustomer_Module_Test_invalid_email_input() throws InterruptedException {

        htmlReporter = new ExtentHtmlReporter(".\\Reports\\newCustomerModule_Invalid_email_data_Test_Report.html");
        reports = new ExtentReports();
        reports.attachReporter(htmlReporter);

        //Info. added to report

        reports.setSystemInfo("Environment: ", "Java_Selenium_TestNG");
        reports.setSystemInfo("CPU", "64bit_2.00 GHz");
        reports.setSystemInfo("OS", "Windows 10");
        reports.setSystemInfo("", "");
        reports.setSystemInfo("Project: ", "Guru99Banking_V4");
        reports.setSystemInfo("Module: ", "New Customer");
        reports.setSystemInfo("Testing type: ", "Unit and Functional Testing");
        reports.setSystemInfo("Testing case: ", "Invalid data type input_invalid email");
        reports.setSystemInfo("Test requirements: ", "Manager ID: mngr431045 ;  Password: gUnYqur");
        reports.setSystemInfo("ManagerCredential Expire date: ", "28-August-2022");
        reports.setSystemInfo("Data source", "Both external Excell data sheet and directly defined data within");
        reports.setSystemInfo("Additional Attachments", "Manager credential file_filename: demoguru99bankCredential_Screenshot.jpg");
        reports.setSystemInfo("", "");
        reports.setSystemInfo("Test by: ", "Kumar Shimanto");

        logger = reports.createTest("Demo_Guru99_Bank_New_Customer_Module_Test_Invalid_Email_Data");

        //Excel Implementation
        ExcellMethods reader = new ExcellMethods("./src/main/java/Project_DemoGuru99Banking/Utils/Guru99Banking_NewCustomer_Data.xlsx");
        String sheetName = "New_Customer_Create_Data";

        //Site open

        driverObj.get("https://demo.guru99.com/v4/");
        logger.log(Status.INFO,"Site opened");

        //Manager_ID Log
        WebElement userId = driverObj.findElement(By.name("uid"));
        WebElement password = driverObj.findElement(By.name("password"));
        WebElement lgnBtn = driverObj.findElement(By.name("btnLogin"));

        userId.sendKeys("mngr431045");
        logger.log(Status.INFO, "Manager ID typed");
        password.sendKeys("gUnYqur");
        logger.log(Status.INFO, "Manager Password typed..");
        Thread.sleep(1000);
        lgnBtn.click();
        logger.log(Status.INFO, "Login button clicked..");

        String currentPageTitle1 = driverObj.getTitle();
        Thread.sleep(1000);

        WebElement newCustomerModule = driverObj.findElement(By.xpath("/html/body/div[3]/div/ul/li[2]/a"));
        newCustomerModule.click();
        logger.log(Status.INFO, "New Customer module clicked");
        Thread.sleep(2000);

        driverObj.get("https://demo.guru99.com/v4/manager/addcustomerpage.php");
        logger.log(Status.INFO,"New customer module opened");

        String currentPageTitle2 = driverObj.getTitle();
        Thread.sleep(1000);

        if (Objects.equals(currentPageTitle1, "Guru99 Bank Manager HomePage")) {
            logger.log(Status.PASS, "Manager logged in");
            System.out.println("Manager Logged In.");
        } else {
            logger.log(Status.FAIL, "Manager log in failed");
            System.out.println("Manager did not log in, try again with valid credential");
        }

        if (Objects.equals(currentPageTitle2, "Guru99 Bank New Customer Entry Page")) {
            logger.log(Status.PASS, "New Customer module opened");
            System.out.println("New customer module opened");
        } else {
            logger.log(Status.FAIL, "New customer module did not open");
            System.out.println("New Customer Module did not open, try again.");
        }


        //Add new customer module log

        int rowCount = reader.getRowCount(sheetName);

        for (int rowNum = 9; rowNum <= 11; rowNum++) {
            String CustomerName = reader.getCellData(sheetName, "Customer_Name", rowNum);
            String BirthDate = reader.getCellData(sheetName, "DateOfBirth", rowNum);
            String AddressInfo = reader.getCellData(sheetName, "Address", rowNum);
            String City = reader.getCellData(sheetName, "City", rowNum);
            String State = reader.getCellData(sheetName, "State", rowNum);
            String Pin = reader.getCellData(sheetName, "Pin", rowNum);
            String Contact = reader.getCellData(sheetName, "Contact", rowNum);
            String Email = reader.getCellData(sheetName, "Email", rowNum);
            String Password = reader.getCellData(sheetName, "Password", rowNum);

            driverObj.get("https://demo.guru99.com/v4/manager/addcustomerpage.php");
            logger.log(Status.INFO, "Add customer site opened");

            WebElement customerName = driverObj.findElement(By.xpath("/html/body/table/tbody/tr/td/table/tbody/tr[4]/td[2]/input"));
            WebElement maleBtn = driverObj.findElement(By.name("rad1"));
            WebElement femaleBtn = driverObj.findElement(By.xpath("/html/body/table/tbody/tr/td/table/tbody/tr[5]/td[2]/input[2]"));
            WebElement dateBirth = driverObj.findElement(By.name("dob"));
            WebElement address = driverObj.findElement(By.name("addr"));
            WebElement city = driverObj.findElement(By.name("city"));
            WebElement state = driverObj.findElement(By.name("state"));
            WebElement pin = driverObj.findElement(By.name("pinno"));
            WebElement mobileNo = driverObj.findElement(By.name("telephoneno"));
            WebElement email = driverObj.findElement(By.name("emailid"));
            WebElement pass = driverObj.findElement(By.xpath("/html/body/table/tbody/tr/td/table/tbody/tr[13]/td[2]/input"));
            WebElement submitBtn = driverObj.findElement(By.xpath("/html/body/table/tbody/tr/td/table/tbody/tr[14]/td[2]/input[1]"));
            WebElement addNewCustomerTitle = driverObj.findElement(By.xpath("/html/body/table/tbody/tr/td/table/tbody/tr[1]/td/p"));

            customerName.sendKeys(CustomerName);
            logger.log(Status.INFO, "Customer name typed");
            femaleBtn.click();
            logger.log(Status.INFO, "Gender selected_female");
            dateBirth.sendKeys(BirthDate);
            logger.log(Status.INFO, "Birth date typed");
            address.sendKeys(AddressInfo);
            logger.log(Status.INFO, "Address typed");
            city.sendKeys(City);
            logger.log(Status.INFO, "City name typed");
            state.sendKeys(State);
            logger.log(Status.INFO, "State name typed");
            pin.sendKeys(Pin);
            logger.log(Status.INFO, "Pin typed");
            mobileNo.sendKeys(Contact);
            logger.log(Status.INFO, "Contact number typed..");
            email.sendKeys(Email);
            logger.log(Status.INFO, "Email typed");
            pass.sendKeys(Password);
            logger.log(Status.INFO, "Password typed");

            addNewCustomerTitle.click();

            WebElement emailNtf = driverObj.findElement(By.id("message9"));

            String emailNotif = emailNtf.getText();

            submitBtn.click();
            logger.log(Status.INFO, "Submit button clicked");

            Thread.sleep(1000);

            driverObj.switchTo().alert().accept();

            if (Objects.equals(emailNotif, "Email-ID is not valid")) {
                System.out.println("Email: " + Email);
                logger.log(Status.INFO, "Email: " + Email);

                System.out.println("Invalid email data input alert_test_passed");
                logger.log(Status.PASS, "Invalid email data input alert_test_passed");
            } else {
                logger.log(Status.FAIL, "Invalid email data input alert_test_failed");
                System.out.println("Invalid email data input alert_test_failed");
            }

            if (Objects.equals(driverObj.getCurrentUrl(), "https://demo.guru99.com/v4/manager/addcustomerpage.php")) {
                logger.log(Status.PASS, "JS alert OK_test_passed");
                System.out.println("JS alert OK_test_passed");
            } else {
                logger.log(Status.FAIL, "JS alert not OK_test_failed");
                System.out.println("JS alert not OK_test_failed");
            }
        }
        Thread.sleep(2000);
        reports.flush();
    }


    @Test(priority=1, description="New customer creation with valid data and existing email input")
    public void newCustomer_Module_Test_valid_data() throws InterruptedException {

        htmlReporter = new ExtentHtmlReporter(".\\Reports\\newCustomerModule_Valid_data_and_Existing_Email_Test_Report.html");
        reports = new ExtentReports();
        reports.attachReporter(htmlReporter);

        reports.setSystemInfo("Environment: ", "Java_Selenium_TestNG");
        reports.setSystemInfo("CPU", "64bit_2.00 GHz");
        reports.setSystemInfo("OS", "Windows 10");
        reports.setSystemInfo("", "");
        reports.setSystemInfo("Project: ", "Guru99Banking_V4");
        reports.setSystemInfo("Module: ", "New Customer");
        reports.setSystemInfo("Testing type: ", "Unit and Functional Testing");
        reports.setSystemInfo("Testing case: ", "Valid customer creation");
        reports.setSystemInfo("Test requirements: ", "Manager ID: mngr431045 ;  Password: gUnYqur");
        reports.setSystemInfo("ManagerCredential Expire date: ", "28-August-2022");
        reports.setSystemInfo("Data source", "External code generated random email and directly defined data within");
        reports.setSystemInfo("Additional Attachments", "Manager credential file_filename: demoguru99bankCredential_Screenshot.jpg");
        reports.setSystemInfo("", "");
        reports.setSystemInfo("Test by: ", "Kumar Shimanto");

        logger = reports.createTest("Demo_Guru99_Bank_New_Customer_Module_Test_Valid_Data_Existing_Email");

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
        Thread.sleep(2000);
        lgnBtn.click();
        logger.log(Status.INFO, "Login button clicked");

        String currentPageTitle1 = driverObj.getTitle();
        Thread.sleep(2000);

        WebElement newCustomerModule = driverObj.findElement(By.xpath("/html/body/div[3]/div/ul/li[2]/a"));
        newCustomerModule.click();
        logger.log(Status.INFO, "New Customer module clicked");
        Thread.sleep(2000);

        driverObj.get("https://demo.guru99.com/v4/manager/addcustomerpage.php");

        String currentPageTitle2 = driverObj.getTitle();
        Thread.sleep(2000);

        if (Objects.equals(currentPageTitle1, "Guru99 Bank Manager HomePage")) {
            logger.log(Status.PASS, "Manager logged in");
            System.out.println("Manager Logged In.");
        } else {
            logger.log(Status.FAIL, "Manager log in failed");
            System.out.println("Manager did not log in, try again with valid credential");
        }

        if (Objects.equals(currentPageTitle2, "Guru99 Bank New Customer Entry Page")) {
            logger.log(Status.PASS, "New Customer module opened");
            System.out.println("New customer module opened");
        } else {
            logger.log(Status.FAIL, "New customer module did not open");
            System.out.println("New Customer Module did not open, try again.");
        }

        //Add new customer module log

            driverObj.get("https://demo.guru99.com/v4/manager/addcustomerpage.php");
            logger.log(Status.INFO, "Add customer site opened");

            WebElement customerName = driverObj.findElement(By.xpath("/html/body/table/tbody/tr/td/table/tbody/tr[4]/td[2]/input"));
            WebElement maleBtn = driverObj.findElement(By.name("rad1"));
            WebElement femaleBtn = driverObj.findElement(By.xpath("/html/body/table/tbody/tr/td/table/tbody/tr[5]/td[2]/input[2]"));
            WebElement dateBirth = driverObj.findElement(By.name("dob"));
            WebElement address = driverObj.findElement(By.name("addr"));
            WebElement city = driverObj.findElement(By.name("city"));
            WebElement state = driverObj.findElement(By.name("state"));
            WebElement pin = driverObj.findElement(By.name("pinno"));
            WebElement mobileNo = driverObj.findElement(By.name("telephoneno"));
            WebElement email = driverObj.findElement(By.name("emailid"));
            WebElement pass = driverObj.findElement(By.xpath("/html/body/table/tbody/tr/td/table/tbody/tr[13]/td[2]/input"));
            WebElement submitBtn = driverObj.findElement(By.xpath("/html/body/table/tbody/tr/td/table/tbody/tr[14]/td[2]/input[1]"));
            WebElement resetBtn = driverObj.findElement(By.xpath("/html/body/table/tbody/tr/td/table/tbody/tr[14]/td[2]/input[2]"));
            WebElement addNewCustomerTitle = driverObj.findElement(By.xpath("/html/body/table/tbody/tr/td/table/tbody/tr[1]/td/p"));

        //int rndNum = (int) (Math.random() * 1001);
        //String EmailValid = "validMail" + rndNum + "@ymail.com";

            customerName.sendKeys("John Cinatra");
            logger.log(Status.INFO, "Customer name typed");
            maleBtn.click();
            logger.log(Status.INFO, "Gender selected_male");
            dateBirth.sendKeys("02021999");
            logger.log(Status.INFO, "Birth date typed");
            address.sendKeys("36 Rt Street");
            logger.log(Status.INFO, "Address typed");
            city.sendKeys("Dhaka North");
            logger.log(Status.INFO, "City name typed");
            state.sendKeys("Dhaka Division");
            logger.log(Status.INFO, "State name typed");
            pin.sendKeys("234567");
            logger.log(Status.INFO, "Pin typed");
            mobileNo.sendKeys("013986489");
            logger.log(Status.INFO, "Contact number typed");
            email.sendKeys(EmailValid);
            logger.log(Status.INFO, "Email typed");
            pass.sendKeys("PassValiid22#");
            logger.log(Status.INFO, "Password typed..");

            submitBtn.click();
            logger.log(Status.INFO, "Submit button clicked");

            WebElement notif1 = driverObj.findElement(By.xpath("//*[@id=\"customer\"]/tbody/tr[1]/td/p"));

            if (Objects.equals(notif1.getText(), "Customer Registered Successfully!!!")) {
                logger.log(Status.PASS, "New customer creation with valid data_test_passed");
                System.out.println("New customer creation with valid data_test_passed");
            } else {
                logger.log(Status.FAIL, "New customer creation with valid data_test_failed");
                System.out.println("New customer creation with valid data_test_failed");
            }

            Thread.sleep(2000);

            WebElement contBtn = driverObj.findElement(By.xpath("//*[@id=\"customer\"]/tbody/tr[14]/td/a"));
            contBtn.click();
            logger.log(Status.INFO, "Continue button clicked");
            Thread.sleep(2000);

            if (Objects.equals(driverObj.getCurrentUrl(), "https://demo.guru99.com/v4/manager/Managerhomepage.php")) {
                logger.log(Status.PASS, "Countinue button OK_test_passed");
                System.out.println("Continue button OK_test_passed");
            } else {
                logger.log(Status.FAIL, "Continue button not OK_test_failed");
                System.out.println("Continue button not OK_test_failed");
            }

            driverObj.navigate().back();

            WebElement homeBtn = driverObj.findElement(By.xpath("/html/body/p/a"));
            homeBtn.click();
            logger.log(Status.INFO, "Home button clicked");
            Thread.sleep(2000);

            if (Objects.equals(driverObj.getCurrentUrl(), "https://demo.guru99.com/v4/manager/Managerhomepage.php")) {
                logger.log(Status.PASS, "Home button OK_test_passed");
                System.out.println("Home button Ok_test_passed");
            } else {
                logger.log(Status.FAIL, "Home button not OK_test_failed");
                System.out.println("Home button Ok_test_failed");
            }

            Thread.sleep(2000);

            logger.log(Status.INFO, "Existing email input tryout starts here");

        driverObj.get("https://demo.guru99.com/v4/manager/addcustomerpage.php");
        logger.log(Status.INFO,"Add customer site opened");

        Thread.sleep(2000);

        WebElement customerName2 = driverObj.findElement(By.xpath("/html/body/table/tbody/tr/td/table/tbody/tr[4]/td[2]/input"));
        WebElement maleBtn2 = driverObj.findElement(By.name("rad1"));
        WebElement femaleBtn2 = driverObj.findElement(By.xpath("/html/body/table/tbody/tr/td/table/tbody/tr[5]/td[2]/input[2]"));
        WebElement dateBirth2 = driverObj.findElement(By.name("dob"));
        WebElement address2 = driverObj.findElement(By.name("addr"));
        WebElement city2 = driverObj.findElement(By.name("city"));
        WebElement state2 = driverObj.findElement(By.name("state"));
        WebElement pin2 = driverObj.findElement(By.name("pinno"));
        WebElement mobileNo2 = driverObj.findElement(By.name("telephoneno"));
        WebElement email2 = driverObj.findElement(By.name("emailid"));
        WebElement pass2 = driverObj.findElement(By.xpath("/html/body/table/tbody/tr/td/table/tbody/tr[13]/td[2]/input"));
        WebElement submitBtn2 = driverObj.findElement(By.xpath("/html/body/table/tbody/tr/td/table/tbody/tr[14]/td[2]/input[1]"));

        Thread.sleep(2000);

        customerName2.sendKeys("John Geovanni");
        logger.log(Status.INFO, "Customer name typed");
        maleBtn2.click();
        logger.log(Status.INFO, "Gender selected_male");
        dateBirth2.sendKeys("05081994");
        logger.log(Status.INFO, "Birth date typed");
        address2.sendKeys("36 Rt Street");
        logger.log(Status.INFO, "Address typed");
        city2.sendKeys("Dhaka North");
        logger.log(Status.INFO, "City name typed");
        state2.sendKeys("Dhaka Division");
        logger.log(Status.INFO, "State name typed");
        pin2.sendKeys("234567");
        logger.log(Status.INFO, "Pin typed");
        mobileNo2.sendKeys("013986489");
        logger.log(Status.INFO, "Contact number typed");
        email2.sendKeys(EmailValid);
        logger.log(Status.INFO, "Email typed");
        pass2.sendKeys("PassValiid22#");
        logger.log(Status.INFO, "Password typed");

        Thread.sleep(2000);
        submitBtn2.click();
        logger.log(Status.INFO, "Submit button clicked");

        Thread.sleep(2000);

        String notif2 = driverObj.switchTo().alert().getText();
        driverObj.switchTo().alert().accept();

        if (Objects.equals(notif2, "Email Address Already Exist !!")) {
            logger.log(Status.PASS, "New customer creation failed with existing email_test_passed");
        } else {
            logger.log(Status.FAIL, "New customer creation failed with existing email_test_failed");
        }

        Thread.sleep(2000);
        reports.flush();
    }

    @Test(priority=2, description ="Resetting the new customer input fields")
    public void newCustomer_Module_Test_Reset() throws InterruptedException {

        htmlReporter = new ExtentHtmlReporter(".\\Reports\\newCustomerModule_Reset_Button_Functionality_Test_Report.html");
        reports = new ExtentReports();
        reports.attachReporter(htmlReporter);

        //Info. added to report

        reports.setSystemInfo("Environment: ", "Java_Selenium_TestNG");
        reports.setSystemInfo("CPU", "64bit_2.00 GHz");
        reports.setSystemInfo("OS", "Windows 10");
        reports.setSystemInfo("", "");
        reports.setSystemInfo("Project: ", "Guru99Banking_V4");
        reports.setSystemInfo("Module: ", "New Customer");
        reports.setSystemInfo("Testing type: ", "Unit and Functional Testing");
        reports.setSystemInfo("Testing case: ", "Reset button functionality test");
        reports.setSystemInfo("Test requirements: ", "Manager ID: mngr431045 ;  Password: gUnYqur");
        reports.setSystemInfo("ManagerCredential Expire date: ", "28-August-2022");
        reports.setSystemInfo("Data source", "Directly defined data within");
        reports.setSystemInfo("Additional Attachments", "Manager credential file_filename: demoguru99bankCredential_Screenshot.jpg");
        reports.setSystemInfo("", "");
        reports.setSystemInfo("Test by: ", "Kumar Shimanto");

        logger = reports.createTest("Demo_Guru99_Bank_New_Customer_Module_Reset_Button_Test");

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
        Thread.sleep(2000);
        lgnBtn.click();
        logger.log(Status.INFO, "Login button clicked");

        String currentPageTitle1 = driverObj.getTitle();
        Thread.sleep(2000);

        WebElement newCustomerModule = driverObj.findElement(By.xpath("/html/body/div[3]/div/ul/li[2]/a"));
        newCustomerModule.click();
        logger.log(Status.INFO, "New Customer module clicked");
        Thread.sleep(2000);

        driverObj.get("https://demo.guru99.com/v4/manager/addcustomerpage.php");

        String currentPageTitle2 = driverObj.getTitle();
        Thread.sleep(2000);

        if (Objects.equals(currentPageTitle1, "Guru99 Bank Manager HomePage")) {
            logger.log(Status.PASS, "Manager logged in");
            System.out.println("Manager Logged In.");
        } else {
            logger.log(Status.FAIL, "Manager log in failed");
            System.out.println("Manager did not log in, try again with valid credential");
        }

        if (Objects.equals(currentPageTitle2, "Guru99 Bank New Customer Entry Page")) {
            logger.log(Status.PASS, "New Customer module opened");
            System.out.println("New customer module opened");
        } else {
            logger.log(Status.FAIL, "New customer module did not open");
            System.out.println("New Customer Module did not open, try again.");
        }

        driverObj.get("https://demo.guru99.com/v4/manager/addcustomerpage.php");
        logger.log(Status.INFO, "Add customer site opened");

        WebElement customerName = driverObj.findElement(By.xpath("/html/body/table/tbody/tr/td/table/tbody/tr[4]/td[2]/input"));
        WebElement maleBtn = driverObj.findElement(By.name("rad1"));
        WebElement femaleBtn = driverObj.findElement(By.xpath("/html/body/table/tbody/tr/td/table/tbody/tr[5]/td[2]/input[2]"));
        WebElement dateBirth = driverObj.findElement(By.name("dob"));
        WebElement address = driverObj.findElement(By.name("addr"));
        WebElement city = driverObj.findElement(By.name("city"));
        WebElement state = driverObj.findElement(By.name("state"));
        WebElement pin = driverObj.findElement(By.name("pinno"));
        WebElement mobileNo = driverObj.findElement(By.name("telephoneno"));
        WebElement email = driverObj.findElement(By.name("emailid"));
        WebElement pass = driverObj.findElement(By.xpath("/html/body/table/tbody/tr/td/table/tbody/tr[13]/td[2]/input"));
        WebElement resetBtn = driverObj.findElement(By.xpath("/html/body/table/tbody/tr/td/table/tbody/tr[14]/td[2]/input[2]"));

        customerName.sendKeys("Agnes Smedly");
        logger.log(Status.INFO, "Customer name typed");
        femaleBtn.click();
        logger.log(Status.INFO, "Gender selected_female");
        dateBirth.sendKeys("02231892");
        logger.log(Status.INFO, "Birth date typed");
        address.sendKeys("Unknown Street 123");
        logger.log(Status.INFO, "Address typed");
        city.sendKeys("Osgood");
        logger.log(Status.INFO, "City name typed");
        state.sendKeys("Missouri");
        logger.log(Status.INFO, "State name typed");
        pin.sendKeys("123456");
        logger.log(Status.INFO, "Pin typed");
        mobileNo.sendKeys("1939195043");
        logger.log(Status.INFO, "Contact number typed");
        email.sendKeys("resetmail@ymail.com");
        logger.log(Status.INFO, "Email typed");
        pass.sendKeys("REsetPass123");
        logger.log(Status.INFO, "Password typed");

        Boolean GenStat1 = maleBtn.isSelected();
        Boolean GenStat2 = femaleBtn.isSelected();
        if (GenStat1 == Boolean.TRUE) {
            logger.log(Status.INFO, "Initial gender selected_Male");
            System.out.println("Initial gender selected_Male");
        } else if (GenStat2 == Boolean.TRUE) {
            logger.log(Status.INFO, "Initial gender selected Female");
            System.out.println("Initial gender selected_Female");
        } else {
            logger.log(Status.INFO, "Gender status not selected");
            System.out.println("Gender status not selected");
        }

        resetBtn.click();
        logger.log(Status.INFO, "Reset button clicked");
        System.out.println("Reset button clicked");

        logger.log(Status.INFO, "Email: resetmail@ymail.com");
        System.out.println("Email: resetmail@ymail.com");

        Thread.sleep(2000);

        if (Objects.equals(customerName.getText(), "")) {
            logger.log(Status.PASS, "Customer name field reset_test_pass");
            System.out.println("Customer name field reset_test_pass");
        } else {
            logger.log(Status.FAIL, "Customer name field reset_test_fail");
            System.out.println("Customer name field reset_test_fail");
        }

        if (maleBtn.isSelected() == Boolean.TRUE) {
            logger.log(Status.PASS, "Gender button reset_test_pass");
            System.out.println("Gender button reset_test_pass");
        } else {
            logger.log(Status.FAIL, "Gender button reset_test_fail");
            System.out.println("Gender button reset_test_fail");
        }

        if (Objects.equals(dateBirth.getText(), "")) {
            logger.log(Status.PASS, "Date field reset_test_pass");
            System.out.println("Date field reset_test_pass");
        } else {
            logger.log(Status.FAIL, "Date field reset_test_fail");
            System.out.println("Date field reset_test_fail");
        }

        if (Objects.equals(address.getText(), "")) {
            logger.log(Status.PASS, "Address field reset_test_pass");
            System.out.println("Address field reset_test_pass");
        } else {
            logger.log(Status.FAIL, "Address field reset_test_fail");
            System.out.println("Address field reset_test_failed");
        }

        if (Objects.equals(city.getText(), "")) {
            logger.log(Status.PASS, "City field reset_test_pass");
            System.out.println("City field reset_test_pass");
        } else {
            logger.log(Status.FAIL, "City field reset_test_fail");
            System.out.println("City filed reset_test_fail");
        }

        if (Objects.equals(state.getText(), "")) {
            logger.log(Status.PASS, "State field reset_test_pass");
            System.out.println("State field reset_test_pass");
        } else {
            logger.log(Status.FAIL, "State field reset_test_fail");
            System.out.println("State field reset_test_fail");
        }

        if (Objects.equals(pin.getText(), "")) {
            logger.log(Status.PASS, "PIN field reset_test_pass");
            System.out.println("PIN field reset_test_pass");
        } else {
            logger.log(Status.FAIL, "PIN field reset_test_fail");
            System.out.println("PIN filed reset_test_fail");
        }

        if (Objects.equals(mobileNo.getText(), "")) {
            logger.log(Status.PASS, "Mobile no field reset_test_pass");
            System.out.println("Mobile no field reset_test_pass");
        } else {
            logger.log(Status.FAIL, "Mobile No. field reset_test_fail");
            System.out.println("Mobile no field reset_test_fail");
        }

        if (Objects.equals(email.getText(), "")) {
            logger.log(Status.PASS, "Email field reset_test_pass");
            System.out.println("Email field reset_test_pass");
        } else {
            logger.log(Status.FAIL, "Email field reset_test_fail");
            System.out.println("Email field reset_test_fail");
        }

        if (Objects.equals(pass.getText(), "")) {
            logger.log(Status.PASS, "Password field reset_test_pass");
            System.out.println("Password field reset_test_pass");
        } else {
            logger.log(Status.FAIL, "Password field reset_test_fail");
            System.out.println("Password field reset_test_fail");
        }

        reports.flush();
    }
}







