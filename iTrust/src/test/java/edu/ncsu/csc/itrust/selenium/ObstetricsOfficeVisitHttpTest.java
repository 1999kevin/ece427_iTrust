package edu.ncsu.csc.itrust.selenium;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class ObstetricsOfficeVisitHttpTest extends iTrustSeleniumTest{
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


    public void testobstetricofficevisit() throws Exception {
        driver.findElement(By.id("j_username")).sendKeys("9000000012");
        driver.findElement(By.id("j_password")).sendKeys("pw");

        driver.findElement(By.cssSelector("input[type='submit']")).click();
        assertEquals("iTrust - HCP Home", driver.getTitle());

        driver.findElement(By.cssSelector("div[anim-target='#ov-menu']")).click();
        driver.findElement(By.cssSelector("a[href= '/iTrust/auth/hcp-uap/viewObsVisit.jsp']")).click();
        assertEquals("iTrust - Please Select a Patient", driver.getTitle());

        assertFalse(driver.getPageSource().contains("HTTP Status 500"));
        assertFalse(driver.getPageSource().contains("java.lang.NumberFormatException"));

        // click on the "Select Patient" searchBox and input 1
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

        WebElement table1 = driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/table[1]"));
        assertEquals("Obstetrics Visits", table1.findElements(By.tagName("th")).get(0).getText());
        assertEquals("1", table1.findElement(By.xpath("//tbody/tr[3]/td[1]")).getText());
        assertEquals("00-0", table1.findElement(By.xpath("//tbody/tr[5]/td[4]")).getText());


        WebElement target2 = driver.findElement(By.id("iTrustContent"));
        WebElement add_button = target2.findElement(By.xpath("//*[@id=\"iTrustContent\"]/div/a"));
        add_button.click();
        assertEquals("iTrust - Add Obstetrics Visit", driver.getTitle());

        driver.findElement(By.name("fhr")).sendKeys("123");
        driver.findElement(By.name("blood-pressure")).sendKeys("80/120");
        driver.findElement(By.name("weight-in-pounds")).sendKeys("50");
        driver.findElement(By.name("notes")).sendKeys("good");
        WebElement select1 = driver.findElement(By.xpath("//select[@name='send-bill']/option[@value='Yes']"));
        select1.click();
        WebElement select2 = driver.findElement(By.xpath("//select[@name='low-lying-placenta']/option[@value='No']"));
        select2.click();
        driver.findElement(By.name("number-of-baby")).sendKeys("1");

        WebElement target3 = driver.findElement(By.id("editForm"));
        WebElement summit = target3.findElement(By.xpath("//*[@id=\"editForm\"]/div/input"));
        summit.click();
        assertTrue(driver.getPageSource().contains("New Obstetrcis Visit added"));

        driver.findElement(By.cssSelector("div[anim-target='#ov-menu']")).click();
        driver.findElement(By.cssSelector("a[href= '/iTrust/auth/hcp-uap/viewObsVisit.jsp']")).click();
        assertEquals("iTrust - Please Select a Patient", driver.getTitle());

        assertFalse(driver.getPageSource().contains("HTTP Status 500"));
        assertFalse(driver.getPageSource().contains("java.lang.NumberFormatException"));

        // click on the "Select Patient" searchBox and input 1
        WebElement searchBox1 = driver.findElement(By.id("searchBox"));
        assertEquals("iTrust - Please Select a Patient", driver.getTitle());
        searchBox1.click();
        searchBox1.clear();
        searchBox1.sendKeys("1");
//
        Thread.sleep(1000);
        WebElement target4 = driver.findElement(By.id("searchTarget"));
        WebElement button1 = target4.findElement(By.xpath("//*[@id=\"searchTarget\"]/table/tbody/tr[2]/td[5]/input"));
        assertEquals("View", button1.getAttribute("value"));
        button1.click();

        WebElement table2 = driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/table[1]"));
        assertEquals("Obstetrics Visits", table2.findElements(By.tagName("th")).get(0).getText());
//        assertEquals("50", table2.findElement(By.xpath("//tbody/tr[28]/td[1]")).getText());
////        assertEquals("50.0", table2.findElement(By.xpath("//tbody/tr[28]/td[5]")).getText());
////        assertEquals("80/120", table2.findElement(By.xpath("//tbody/tr[28]/td[6]")).getText());
////        assertEquals("123", table2.findElement(By.xpath("//tbody/tr[28]/td[7]")).getText());
////        assertEquals("1", table2.findElement(By.xpath("//tbody/tr[28]/td[8]")).getText());
////        assertEquals("Yes", table2.findElement(By.xpath("//tbody/tr[28]/td[9]")).getText());
////        assertEquals("No", table2.findElement(By.xpath("//tbody/tr[28]/td[10]")).getText());
        assertTrue(driver.getPageSource().contains("80/120"));
        assertTrue(driver.getPageSource().contains("123"));
        assertTrue(driver.getPageSource().contains("50.0"));


        WebElement edit_button = table2.findElement(By.xpath("//tbody/tr[3]/td[12]/a"));
        assertEquals("Edit", table2.findElement(By.xpath("//tbody/tr[3]/td[12]")).getText());
        edit_button.click();
        assertEquals("iTrust - Edit Obstetrics Visit", driver.getTitle());

        driver.findElement(By.name("fhr")).clear();
        driver.findElement(By.name("fhr")).sendKeys("80");
        driver.findElement(By.name("blood-pressure")).clear();
        driver.findElement(By.name("blood-pressure")).sendKeys("60/105");
        driver.findElement(By.name("weight-in-pounds")).clear();
        driver.findElement(By.name("weight-in-pounds")).sendKeys("11");
        driver.findElement(By.name("notes")).clear();
        driver.findElement(By.name("notes")).sendKeys("nice");
        WebElement select3 = driver.findElement(By.xpath("//select[@name='send-bill']/option[@value='Yes']"));
        select3.click();
        WebElement select4 = driver.findElement(By.xpath("//select[@name='low-lying-placenta']/option[@value='No']"));
        select4.click();
        driver.findElement(By.name("number-of-baby")).clear();
        driver.findElement(By.name("number-of-baby")).sendKeys("2");

        WebElement target5 = driver.findElement(By.id("editForm"));
        WebElement summit2 = target5.findElement(By.xpath("//*[@id=\"editForm\"]/div/input"));
        summit2.click();
        assertTrue(driver.getPageSource().contains("Edit Finished"));

    }

    public void testultrasound() throws Exception {
        driver.findElement(By.id("j_username")).sendKeys("9000000012");
        driver.findElement(By.id("j_password")).sendKeys("pw");

        driver.findElement(By.cssSelector("input[type='submit']")).click();
        assertEquals("iTrust - HCP Home", driver.getTitle());

        driver.findElement(By.cssSelector("div[anim-target='#ov-menu']")).click();
        driver.findElement(By.cssSelector("a[href= '/iTrust/auth/hcp-uap/viewObsVisit.jsp']")).click();
        assertEquals("iTrust - Please Select a Patient", driver.getTitle());

        assertFalse(driver.getPageSource().contains("HTTP Status 500"));
        assertFalse(driver.getPageSource().contains("java.lang.NumberFormatException"));

        // click on the "Select Patient" searchBox and input 1
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

        WebElement table1 = driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/table[1]"));
        assertEquals("Obstetrics Visits", table1.findElements(By.tagName("th")).get(0).getText());
        assertEquals("1", table1.findElement(By.xpath("//tbody/tr[3]/td[1]")).getText());
        assertEquals("00-0", table1.findElement(By.xpath("//tbody/tr[5]/td[4]")).getText());

        WebElement table2 = driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/table[1]"));
        WebElement edit_button = table2.findElement(By.xpath("//tbody/tr[3]/td[12]/a"));
        assertEquals("Edit", table2.findElement(By.xpath("//tbody/tr[3]/td[12]")).getText());
        edit_button.click();
        assertEquals("iTrust - Edit Obstetrics Visit", driver.getTitle());

        WebElement target2 = driver.findElement(By.id("iTrustContent"));
        WebElement add_button = target2.findElement(By.xpath("//*[@id=\"iTrustContent\"]/div/a"));
        add_button.click();
        assertEquals("iTrust - Add Obstetrics Visit", driver.getTitle());

        driver.findElement(By.name("crl")).sendKeys("1.1");
        driver.findElement(By.name("bpd")).sendKeys("2.2");
        driver.findElement(By.name("hc")).sendKeys("3.3");
        driver.findElement(By.name("fl")).sendKeys("4.4");
        driver.findElement(By.name("ofd")).sendKeys("5.5");
        driver.findElement(By.name("ac")).sendKeys("6.6");
        driver.findElement(By.name("hl")).sendKeys("7.7");
        driver.findElement(By.name("efw")).sendKeys("8.8");

        WebElement target3 = driver.findElement(By.id("editForm"));
        WebElement summit = target3.findElement(By.xpath("//*[@id=\"editForm\"]/div/input"));
        summit.click();
        assertTrue(driver.getPageSource().contains("Ultrasound record added"));
    }





    @Override
    @After
    public void tearDown() throws Exception {
        driver.quit();
    }
}
