import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.util.List;
import java.util.Scanner;


public class Selenium {

    static WebDriver driver;
    static WebDriverWait driverWait;
    public static Scanner scanner = new Scanner(System.in);
    public static final String CAMPUS_URL = "https://www.aac.ac.il/";
    public static final String PORTAL_URL_CSS_PATTERN = "a[href='https://portal.aac.ac.il']";
    public static final String USER_ID_ATTRIBUTE = "#Ecom_User_ID";
    public static final String PASSWORD_ID_ATTRIBUTE = "#Ecom_Password";
    public static final String SUBMIT_BUTTON_ID_ATTRIBUTE = "#wp-submit";
    public static final String MENU_ID_ATTRIBUTE = "#action-menu-toggle-1";
    public static final String MOODLE_LOGOUT_CSS_PATTERN = "a[data-title = 'logout,moodle']";
    public static final String PORTAL_LOGOUT_CSS_PATTERN = "a[href='https://portal.aac.ac.il/AGLogout']";
    public static final String COURSE_LINK_CSS_PATTERN = "a[class ='aalink coursename']";
    public static final String MOODLE_LOGIN_CSS_PATTERN = "a[href='https://moodle.aac.ac.il/login/index.php']";

    public static void main(String[] args){
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\יוסי\\Downloads\\chromedriver_win32\\chromedriver.exe");

        System.out.println("Enter a username");
        String userName = scanner.next();
        System.out.println("Enter a password");
        String password = scanner.next();

        driver = new ChromeDriver();
        driver.get(CAMPUS_URL);
        driver.manage().window().maximize();
        driverWait = new WebDriverWait(driver, 10);
        logIn(userName, password);
        driver.findElement(By.cssSelector(MOODLE_LOGIN_CSS_PATTERN)).click();
        //driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(COURSE_LINK_CSS_PATTERN)));
        List<WebElement> coursesList = (driver.findElements(By.cssSelector(COURSE_LINK_CSS_PATTERN)));
        getCoursesList(coursesList);
        accessCourse(coursesList);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logout();
    }


    private static void accessCourse(List<WebElement> coursesList) {
        System.out.println("which course do you want?");
        int answer = scanner.nextInt();
        coursesList.get(answer - 1).click();
    }

    private static void getCoursesList(List<WebElement> coursesList) {
        System.out.println("your course list:");
        for (int i = 0; i < coursesList.size(); i++) {
            System.out.println("\t" + (i + 1) + ")" + getTextNode(coursesList.get(i)));
        }
    }

    private static void logIn(String userName, String password) {
        driver.findElement(By.cssSelector(PORTAL_URL_CSS_PATTERN)).click();
        WebElement usernameInput, passwordInput;
        usernameInput = driver.findElement(By.cssSelector(USER_ID_ATTRIBUTE));
        usernameInput.sendKeys(userName);
        passwordInput = driver.findElement(By.cssSelector(PASSWORD_ID_ATTRIBUTE));
        passwordInput.sendKeys(password);
        driver.findElement(By.cssSelector(SUBMIT_BUTTON_ID_ATTRIBUTE)).click();

    }

    private static void logout() {
        driver.findElement(By.cssSelector(MENU_ID_ATTRIBUTE)).click();
        WebElement logOut = driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(MOODLE_LOGOUT_CSS_PATTERN)));
        logOut.click();
        driver.findElement(By.cssSelector(PORTAL_LOGOUT_CSS_PATTERN)).click();

    }

    public static String getTextNode(WebElement e) {
        String text = e.getText().trim();
        List<WebElement> children = e.findElements(By.xpath("./*"));
        for (WebElement child : children) {
            text = text.replaceFirst(child.getText(), "").trim();
        }
        return text;
    }

}
