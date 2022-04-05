package selenium.sample;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.concurrent.TimeUnit;

import selenium.pages.ColorPage;

import static org.junit.Assert.*;

public class Sample9Task {
    WebDriver driver;
    private static WebDriverWait wait;
    static ColorPage colorPage;

    @Before
    public void openPage() {
        String libWithDriversLocation = System.getProperty("user.dir") + File.separator + "lib" + File.separator;
        System.setProperty("webdriver.chrome.driver", libWithDriversLocation + "chromedriver" + new selenium.ChangeToFileExtension().extension());
        driver = new ChromeDriver();
        driver.get("https://kristinek.github.io/site/examples/loading_color");
        colorPage = PageFactory.initElements(driver, ColorPage.class);
    }

    @After
    public void closeBrowser() {
        driver.close();
    }

    @Test
    public void loadGreenSleep() throws Exception {
//         TODO:
//         * 1) click on start loading green button
        driver.findElement(By.id("start_green")).click();
//         * 2) check that button does not appear,
        assertFalse(driver.findElement(By.id("start_green")).isDisplayed());
//         * but loading text is seen instead   "Loading green..."
        assertTrue(driver.findElement(By.id("loading_green")).isDisplayed());
        assertEquals("Loading green...", driver.findElement(By.id("loading_green")).getText());
//         * 3) check that both button
        Thread.sleep(5000);
//         * and loading text is not seen,
        assertFalse(driver.findElement(By.id("start_green")).isDisplayed());
        assertFalse(driver.findElement(By.id("loading_green")).isDisplayed());
//         * success is seen instead "Green Loaded"
        assertTrue(driver.findElement(By.id("finish_green")).isDisplayed());
        assertEquals("Green Loaded", driver.findElement(By.id("finish_green")).getText());
    }

    @Test
    public void loadGreenImplicit() throws Exception {
//         TODO:
//         * 1) click on start loading green button
        driver.findElement(By.id("start_green")).click();
//         * 2) check that button does not appear,
        assertFalse(driver.findElement(By.id("start_green")).isDisplayed());
//         * but loading text is seen instead   "Loading green..."
        assertTrue(driver.findElement(By.id("loading_green")).isDisplayed());
        assertEquals("Loading green...", driver.findElement(By.id("loading_green")).getText());
//         * 3) check that both button
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(By.id("finish_green")).isDisplayed();
//         * and loading text is not seen,
        assertFalse(driver.findElement(By.id("start_green")).isDisplayed());
        assertFalse(driver.findElement(By.id("loading_green")).isDisplayed());
//         * success is seen instead "Green Loaded"
        assertTrue(driver.findElement(By.id("finish_green")).isDisplayed());
        assertEquals("Green Loaded", driver.findElement(By.id("finish_green")).getText());
    }

    @Test
    public void loadGreenExplicitWait() throws Exception {
//         TODO:
        wait = (WebDriverWait) new WebDriverWait(driver, 10).ignoring(StaleElementReferenceException.class);
//         * 1) click on start loading green button
        driver.findElement(By.id("start_green")).click();
//         * 2) check that button does not appear,
        assertFalse(driver.findElement(By.id("start_green")).isDisplayed());
//         * but loading text is seen instead   "Loading green..."
        assertTrue(driver.findElement(By.id("loading_green")).isDisplayed());
        assertEquals("Loading green...", driver.findElement(By.id("loading_green")).getText());
//         * 3) check that both button
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("finish_green")));
//         * and loading text is not seen,
        assertFalse(driver.findElement(By.id("start_green")).isDisplayed());
        assertFalse(driver.findElement(By.id("loading_green")).isDisplayed());
//         * success is seen instead "Green Loaded"
        assertTrue(driver.findElement(By.id("finish_green")).isDisplayed());
        assertEquals("Green Loaded", driver.findElement(By.id("finish_green")).getText());
    }

    @Test
    public void loadGreenAndBlueBonus() {
        /* TODO:
         * 0) wait until button to load green and blue appears
         * 1) click on start loading green and blue button
         * 2) check that button does not appear, but loading text is seen instead for green
         * 3) check that button does not appear, but loading text is seen instead for green and blue
         * 4) check that button and loading green does not appear,
         * 		but loading text is seen instead for blue and success for green is seen
         * 5) check that both button and loading text is not seen, success is seen instead
         */
        By button = By.id("start_green_and_blue");
        By green = By.id("loading_green_without_blue");
        By blue = By.id("loading_blue_without_green");
        By greenAndBlue = By.id("loading_green_with_blue");
        By finish = By.id("finish_green_and_blue");

        wait = (WebDriverWait) new WebDriverWait(driver, 10).ignoring(StaleElementReferenceException.class);
        wait.until(ExpectedConditions.elementToBeClickable(button));
        //1)
        driver.findElement(button).click();
        //2)
        assertFalse(driver.findElement(button).isDisplayed());
        wait.until(ExpectedConditions.presenceOfElementLocated(green));
        assertTrue(driver.findElement(green).isDisplayed());
        assertEquals("Loading green...", driver.findElement(green).getText());
        //3)
        wait.until(ExpectedConditions.presenceOfElementLocated(greenAndBlue));
        assertFalse(driver.findElement(button).isDisplayed());
        assertTrue(driver.findElement(green).isDisplayed());
        assertEquals("Loading green...", driver.findElement(green).getText());
        assertTrue(driver.findElement(greenAndBlue).isDisplayed());
        assertEquals("Loading blue...", driver.findElement(greenAndBlue).getText());
        //4)
        wait.until(ExpectedConditions.presenceOfElementLocated(blue));
        assertFalse(driver.findElement(button).isDisplayed());
        assertFalse(driver.findElement(green).isDisplayed());
        assertTrue(driver.findElement(greenAndBlue).isDisplayed());
        assertEquals("Loading blue...", driver.findElement(greenAndBlue).getText());
        assertTrue(driver.findElement(blue).isDisplayed());
        assertEquals("Green finished waiting for blue", driver.findElement(blue).getText());
        //5)
        wait.until(ExpectedConditions.presenceOfElementLocated(finish));
        assertFalse(driver.findElement(button).isDisplayed());
        assertFalse(driver.findElement(green).isDisplayed());
        assertFalse(driver.findElement(blue).isDisplayed());
        assertTrue(driver.findElement(finish).isDisplayed());
        assertEquals("Green and Blue Loaded", driver.findElement(finish).getText());
    }

    //Task 10
    @Test
    public void loadColorGreen() throws Exception {
//         TODO:

//         * 1) click on start loading green button
        colorPage.clickStartGreen();
//         * 2) check that button does not appear,
        assertFalse(colorPage.isStartGreenDisplayed());
//         * but loading text is seen instead   "Loading green..."
        assertTrue(colorPage.isLoadingGreenDisplayed());
        assertEquals("Loading green...", colorPage.getLoadingGreenText());
//         * 3) check that both button
        Thread.sleep(5000);
//         * and loading text is not seen,
        assertFalse(colorPage.isStartGreenDisplayed());
        assertFalse(colorPage.isLoadingGreenDisplayed());
//         * success is seen instead "Green Loaded"
        assertTrue(colorPage.isFinishedGreenDisplayed());
        assertEquals("Green Loaded", colorPage.getFinishGreenText());
    }

}