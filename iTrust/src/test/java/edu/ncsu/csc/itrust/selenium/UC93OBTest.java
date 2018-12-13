package edu.ncsu.csc.itrust.selenium;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;


public class UC93OBTest extends iTrustSeleniumTest {
    private HtmlUnitDriver driver;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        gen.clearAllTables();
        gen.standardData();
        driver = new HtmlUnitDriver(BrowserVersion.CHROME);
        driver.setJavascriptEnabled(true);
        driver.get("http://localhost:8080/iTrust/");
    }

    public void testViewOBRecords() throws Exception {
        driver.findElement(By.id("j_username")).sendKeys("9000000012");
        driver.findElement(By.id("j_password")).sendKeys("pw");

        driver.findElement(By.cssSelector("input[type='submit']")).click();
        assertEquals("iTrust - HCP Home", driver.getTitle());

        driver.findElement(By.cssSelector("div[anim-target='#info-menu']")).click();
        driver.findElement(By.cssSelector("a[href= '/iTrust/auth/hcp-uap/viewObstetricsRecords.jsp']")).click();
        assertEquals("iTrust - Please Select a Patient", driver.getTitle());

        WebElement searchBox = driver.findElement(By.id("searchBox"));
        assertEquals("iTrust - Please Select a Patient", driver.getTitle());
        searchBox.click();
        searchBox.clear();
        searchBox.sendKeys("1");

        Thread.sleep(1000);

        WebElement target = driver.findElement(By.id("searchTarget"));

        WebElement button = target.findElement(By.xpath("//*[@id=\"searchTarget\"]/table/tbody/tr[2]/td[5]/input"));
        assertEquals("View", button.getAttribute("value"));
        button.click();

        assertEquals("iTrust - View Obstetrics Records", driver.getTitle());

        WebElement table1 = driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/table[1]"));
        assertEquals("Obstetrics Care Records", table1.findElements(By.tagName("th")).get(0).getText());
        assertEquals("Record Date", table1.findElement(By.xpath("//tbody/tr[2]/td[2]")).getText());
        assertEquals("2019", table1.findElement(By.xpath("//tbody/tr[3]/td[4]")).getText());

        WebElement target2 = driver.findElement(By.id("iTrustContent"));
        WebElement button2 = target2.findElement(By.xpath("//*[@id=\"iTrustContent\"]/table/tbody/tr[3]/td[7]/a"));
        button2.click();
        Thread.sleep(1000);
        assertEquals("iTrust - View Obstetrics Record", driver.getTitle());

        WebElement table2 = driver.findElement(By.xpath("//*[@id=\"editForm\"]/table[1]"));
        assertEquals("Obstetrics Record", table2.findElements(By.tagName("th")).get(0).getText());
        assertEquals("12/05/2018", table2.findElement(By.xpath("//tbody/tr[2]/td[2]")).getText());
        assertEquals("2019", table2.findElement(By.xpath("//tbody/tr[3]/td[2]")).getText());

    }

    public void testAddOBRecords() throws Exception {
        driver.findElement(By.id("j_username")).sendKeys("9000000012");
        driver.findElement(By.id("j_password")).sendKeys("pw");

        driver.findElement(By.cssSelector("input[type='submit']")).click();
        assertEquals("iTrust - HCP Home", driver.getTitle());

        driver.findElement(By.cssSelector("div[anim-target='#info-menu']")).click();
        driver.findElement(By.cssSelector("a[href= '/iTrust/auth/hcp-uap/viewObstetricsRecords.jsp']")).click();
        assertEquals("iTrust - Please Select a Patient", driver.getTitle());

        WebElement searchBox = driver.findElement(By.id("searchBox"));
        assertEquals("iTrust - Please Select a Patient", driver.getTitle());
        searchBox.click();
        searchBox.clear();
        searchBox.sendKeys("1");

        Thread.sleep(1000);

        WebElement target = driver.findElement(By.id("searchTarget"));

        WebElement button = target.findElement(By.xpath("//*[@id=\"searchTarget\"]/table/tbody/tr[2]/td[5]/input"));
        assertEquals("View", button.getAttribute("value"));
        button.click();

        assertEquals("iTrust - View Obstetrics Records", driver.getTitle());
        WebElement target2 = driver.findElement(By.id("iTrustContent"));
        WebElement button2 = target2.findElement(By.xpath("//*[@id=\"iTrustContent\"]/div/a"));
        button2.click();
        assertEquals("iTrust - Add new Obstetrics Record", driver.getTitle());

        driver.findElement(By.name("conception-year")).sendKeys("2019");
        driver.findElement(By.name("weight-gained")).sendKeys("1");
        driver.findElement(By.name("hours-in-labor")).sendKeys("1");
        WebElement select = driver.findElement(By.xpath("//select[@name='delivery-type']/option[@value='vaginal delivery']"));
        select.click();
        driver.findElement(By.name("number-of-birth")).sendKeys("1");
        driver.findElement(By.name("LMP")).sendKeys("12/02/2018");

        WebElement summit = driver.findElement(By.name("submitButton"));
        summit.click();
        assertTrue(driver.getPageSource().contains("The record has been added"));

//
//        WebElement target4 = driver.findElement(By.id("iTrustFooter"));
//        WebElement log = target4.findElement(By.xpath("//*[@id=\"iTrustFooter\"]/div[1]/a[1]"));
//        log.click();
//        assertFalse(driver.getPageSource().contains("Create Inital Obstetric Record"));

    }
    @Override
    @After
    public void tearDown() throws Exception {
        driver.quit();
    }
}
