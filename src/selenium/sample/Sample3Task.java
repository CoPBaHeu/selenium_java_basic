package selenium.sample;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.util.List;

import static org.junit.Assert.*;

public class Sample3Task {
    WebDriver driver;

    // method which is being run before each test
    @Before
    public void startingTests() throws Exception {
        // from Sample 1:
        String libWithDriversLocation = System.getProperty("user.dir") + File.separator + "lib" + File.separator;
        System.setProperty("webdriver.chrome.driver", libWithDriversLocation + "chromedriver" + new selenium.ChangeToFileExtension().extension());
        // declaration above:
        driver = new ChromeDriver();

        //open page:
        driver.get("https://kristinek.github.io/site/examples/locators");
    }

    // method which is being run after each test
    @After
    public void endingTests() throws Exception {
        driver.quit();
    }

    @Test
    public void assertEqualsTask() throws Exception {
//         TODO:
//         check how many element with class "test" there are on page (5)
//         check that value of second button is "This is also a button"
        int expSize = 5;
        int actSize = driver.findElements(By.className("test")).size();
        assertEquals(expSize,actSize);

        String expButton = "This is also a button";
        String actButton = driver.findElement(By.name("randomButton2")).getAttribute("value");
        assertEquals(expButton,actButton);
    }

    @Test
    public void assertTrueTask() throws Exception {
//         TODO:
//         check that it is True that value of second button is
//         "this is Also a Button" if you ignore Caps Locks
//         fail with custom error message:
        String expButton = "this is also a button";
        String actButton = driver.findElement(By.name("randomButton2")).getAttribute("value");
        assertTrue(actButton.equalsIgnoreCase(expButton));
    }

    @Test
    public void assertFalseTask() throws Exception {
//         TODO:
//        check that it is False that value of second button is "This is a button"
        String expButton = "This is a button";
        String actButton = driver.findElement(By.name("randomButton2")).getAttribute("value");
        assertFalse(actButton.equals(expButton));
    }

    @Test
    public void failTask() throws Exception {
//        TODO:
//        check that none of items with class "test"
//        contain number 190
        String expNum = "190";
        List<WebElement> allEl = driver.findElements(By.className("test"));
        for (WebElement el : allEl){
            if (el.getText().contains(expNum)){
                fail();
            }
        }
    }
}
