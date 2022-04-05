package selenium.tasks;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.util.List;

import static org.junit.Assert.*;

public class Task2 {
    WebDriver driver;

    @Before
    public void openPage() {
        String libWithDriversLocation = System.getProperty("user.dir") + File.separator + "lib" + File.separator;
        System.setProperty("webdriver.chrome.driver", libWithDriversLocation + "chromedriver" + new selenium.ChangeToFileExtension().extension());
        driver = new ChromeDriver();
        driver.get("https://kristinek.github.io/site/tasks/provide_feedback");
    }

    @After
    public void closeBrowser() {
        driver.close();
    }

    @Test
    public void initialFeedbackPage() throws Exception {
//         TODO:
//         check that all field are empty and no tick are clicked
        assertEquals("", driver.findElement(By.id("fb_name")).getText());
        assertEquals("", driver.findElement(By.id("fb_age")).getText());

        List<WebElement> checkBoxes = driver.findElements(By.cssSelector(".w3-check[type='checkbox']"));
        for (WebElement checkBox : checkBoxes) {
            assertFalse(checkBox.isSelected());
        }
//         "Don't know" is selected in "Genre"
        List<WebElement> radioButtons = driver.findElements(By.cssSelector(".w3-radio[type='radio']"));
        assertTrue(radioButtons.get(2).isSelected());
        assertFalse(radioButtons.get(2).isEnabled());
//         "Choose your option" in "How do you like us?"
        Select dropdown = new Select(driver.findElement(By.id("like_us")));
        assertEquals("Choose your option", dropdown.getFirstSelectedOption().getText());
        assertEquals("", driver.findElement(By.tagName("textarea")).getText());
//         check that the button send is blue with white letters
        WebElement b1 = driver.findElement(By.className("w3-btn-block"));
        assertEquals("rgba(255, 255, 255, 1)", b1.getCssValue("color"));
        assertEquals("rgba(33, 150, 243, 1)", b1.getCssValue("background-color"));
    }

    @Test
    public void emptyFeedbackPage() throws Exception {
//         TODO:
//         click "Send" without entering any data
        driver.findElement(By.className("w3-btn-block")).click();
//         check fields are empty or null
        //driver.getCurrentUrl();
        assertEquals("", driver.findElement(By.id("name")).getText());
        assertEquals("", driver.findElement(By.id("age")).getText());
        assertEquals("", driver.findElement(By.id("language")).getText());
        assertEquals("null", driver.findElement(By.id("gender")).getText());
        assertEquals("null", driver.findElement(By.id("option")).getText());
        assertEquals("", driver.findElement(By.id("comment")).getText());
//         check button colors
//         (green with white letter and red with white letters)
        WebElement b1 = driver.findElement(By.className("w3-green"));
        WebElement b2 = driver.findElement(By.className("w3-red"));
        assertEquals("rgba(255, 255, 255, 1)", b1.getCssValue("color"));
        assertEquals("rgba(76, 175, 80, 1)", b1.getCssValue("background-color"));
        assertEquals("rgba(255, 255, 255, 1)", b2.getCssValue("color"));
        assertEquals("rgba(244, 67, 54, 1)", b2.getCssValue("background-color"));

    }

    @Test
    public void notEmptyFeedbackPage() throws Exception {
//         TODO:
        String nameToInput = "Carlos";
        String ageToInput = "15";
//         fill the whole form, click "Send"
        driver.findElement(By.id("fb_name")).sendKeys(nameToInput);
        driver.findElement(By.id("fb_age")).sendKeys(ageToInput);
        List<WebElement> checkBoxes = driver.findElements(By.cssSelector(".w3-check[type='checkbox']"));
        checkBoxes.get(1).click();
        checkBoxes.get(2).click();
        List<WebElement> radioButtons = driver.findElements(By.cssSelector(".w3-radio[type='radio']"));
        radioButtons.get(0).click();
        Select dropdown = new Select(driver.findElement(By.id("like_us")));
        dropdown.selectByVisibleText("Good");
        driver.findElement(By.tagName("textarea")).sendKeys("All enough nice!");
        driver.findElement(By.className("w3-btn-block")).click();
//         check fields are filled correctly
        assertEquals(nameToInput, driver.findElement(By.id("name")).getText());
        assertEquals(ageToInput, driver.findElement(By.id("age")).getText());
        assertEquals("French,Spanish", driver.findElement(By.id("language")).getText());
        assertEquals("male", driver.findElement(By.id("gender")).getText());
        assertEquals("Good", driver.findElement(By.id("option")).getText());
        assertEquals("All enough nice!", driver.findElement(By.id("comment")).getText());
//         check button colors
//         (green with white letter and red with white letters)
        WebElement b1 = driver.findElement(By.className("w3-green"));
        WebElement b2 = driver.findElement(By.className("w3-red"));
        assertEquals("rgba(255, 255, 255, 1)", b1.getCssValue("color"));
        assertEquals("rgba(76, 175, 80, 1)", b1.getCssValue("background-color"));
        assertEquals("rgba(255, 255, 255, 1)", b2.getCssValue("color"));
        assertEquals("rgba(244, 67, 54, 1)", b2.getCssValue("background-color"));
    }

    @Test
    public void yesOnWithNameFeedbackPage() throws Exception {
//         TODO:
        String nameToInput = "Carlos";
//         enter only name
        driver.findElement(By.id("fb_name")).sendKeys(nameToInput);
//         click "Send"
        driver.findElement(By.className("w3-btn-block")).click();
//         click "Yes"
        driver.findElement(By.className("w3-green")).click();
//         check message text: "Thank you, NAME, for your feedback!"
        assertEquals("Thank you, " + nameToInput + ", for your feedback!", driver.findElement(By.tagName("h2")).getText());
//         color of text is white with green on the background
        assertEquals("rgba(255, 255, 255, 1)", driver.findElement(By.className("w3-green")).getCssValue("color"));
        assertEquals("rgba(76, 175, 80, 1)", driver.findElement(By.className("w3-green")).getCssValue("background-color"));
    }

    @Test
    public void yesOnWithoutNameFeedbackPage() throws Exception {
//         TODO:
//         click "Send" (without entering anything
        driver.findElement(By.className("w3-btn-block")).click();
//         click "Yes"
        driver.findElement(By.className("w3-green")).click();
//         check message text: "Thank you for your feedback!"
        assertEquals("Thank you for your feedback!", driver.findElement(By.tagName("h2")).getText());
//         color of text is white with green on the background
        assertEquals("rgba(255, 255, 255, 1)", driver.findElement(By.className("w3-green")).getCssValue("color"));
        assertEquals("rgba(76, 175, 80, 1)", driver.findElement(By.className("w3-green")).getCssValue("background-color"));
    }

    @Test
    public void noOnFeedbackPage() throws Exception {
//         TODO:
//         fill the whole form
        String nameToInput = "Carlos";
        String ageToInput = "15";
//         fill the whole form, click "Send"
        driver.findElement(By.id("fb_name")).sendKeys(nameToInput);
        driver.findElement(By.id("fb_age")).sendKeys(ageToInput);
        List<WebElement> checkBoxes = driver.findElements(By.cssSelector(".w3-check[type='checkbox']"));
        checkBoxes.get(1).click();
        checkBoxes.get(2).click();
        List<WebElement> radioButtons = driver.findElements(By.cssSelector(".w3-radio[type='radio']"));
        radioButtons.get(0).click();
        Select dropdown = new Select(driver.findElement(By.id("like_us")));
        dropdown.selectByVisibleText("Good");
        driver.findElement(By.tagName("textarea")).sendKeys("All enough nice!");
//         click "Send"
        driver.findElement(By.className("w3-btn-block")).click();
//         click "No"
        driver.findElement(By.className("w3-red")).click();
//         check fields are filled correctly
        assertEquals(nameToInput, driver.findElement(By.id("fb_name")).getAttribute("value"));
        assertEquals(ageToInput, driver.findElement(By.id("fb_age")).getAttribute("value"));
        checkBoxes = driver.findElements(By.cssSelector(".w3-check[type='checkbox']"));
        assertTrue(checkBoxes.get(1).isSelected());
        assertTrue(checkBoxes.get(2).isSelected());
        radioButtons = driver.findElements(By.cssSelector(".w3-radio[type='radio']"));
        assertTrue(radioButtons.get(0).isSelected());
        dropdown = new Select(driver.findElement(By.id("like_us")));
        assertEquals("Good", dropdown.getFirstSelectedOption().getText());
        assertEquals("All enough nice!", driver.findElement(By.tagName("textarea")).getAttribute("value"));
    }
}
