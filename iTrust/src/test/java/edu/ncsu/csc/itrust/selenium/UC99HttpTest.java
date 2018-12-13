package edu.ncsu.csc.itrust.selenium;


import edu.ncsu.csc.itrust.model.old.enums.TransactionType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.*;

import com.gargoylesoftware.htmlunit.BrowserVersion;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

public class UC99HttpTest extends iTrustSeleniumTest {


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

    /** 
     * test select the patient and do the report view
     * 
     */
    public void testUC99Edit() throws Exception {
        driver.findElement(By.id("j_username")).sendKeys("9000000012");
        driver.findElement(By.id("j_password")).sendKeys("pw");

        driver.findElement(By.cssSelector("input[type='submit']")).click();
        assertEquals("iTrust - HCP Home", driver.getTitle());
        
        driver.findElement(By.cssSelector("div[anim-target='#info-menu']")).click();
        driver.findElement(By.cssSelector("a[href= '/iTrust/auth/hcp-uap/viewPatientPrescriptions.jsp']")).click();
        assertEquals("iTrust - Please Select a Patient", driver.getTitle());

        assertFalse(driver.getPageSource().contains("HTTP Status 500"));
        assertFalse(driver.getPageSource().contains("java.lang.NumberFormatException"));

        // click on the "Select Patient" searchBox and input 1
        WebElement searchBox = driver.findElement(By.id("searchBox"));
        assertEquals("iTrust - Please Select a Patient", driver.getTitle());
        searchBox.click();
        searchBox.clear();
        searchBox.sendKeys("2");
        
        Thread.sleep(1000);        
        
        // choose the pid = 1
        WebElement target = driver.findElement(By.id("searchTarget"));
        assertEquals("MID",driver.findElement(By.xpath("//*[@id=\"searchTarget\"]/table/tbody/tr[1]/th[1]")).getText());
        assertEquals("First Name", driver.findElement(By.xpath("//*[@id=\"searchTarget\"]/table/tbody/tr[1]/th[2]")).getText());
        assertEquals("Last Name", driver.findElement(By.xpath("//*[@id=\"searchTarget\"]/table/tbody/tr[1]/th[3]")).getText());
        
        WebElement button = target.findElement(By.xpath("//*[@id=\"searchTarget\"]/table/tbody/tr[2]/td[1]/input"));
        assertEquals("2", button.getAttribute("value"));
        button.click();
        
        // enter the report view page
        assertEquals("iTrust - View Patient Prescriptions", driver.getTitle());
        assertFalse(driver.getPageSource().contains("HTTP Status 500"));
        assertFalse(driver.getPageSource().contains("java.lang.NumberFormatException"));
        assertFalse(driver.getPageSource().contains("Viewing information for <b>null</b>"));

        driver.findElement(By.cssSelector("a[href=\"editPatientPrescription.jsp?recordID=2\"]")).click();

        // enter the report view page
        assertEquals("iTrust - Edit Patient Prescription", driver.getTitle());
        assertFalse(driver.getPageSource().contains("HTTP Status 500"));
        assertFalse(driver.getPageSource().contains("java.lang.NumberFormatException"));
        assertFalse(driver.getPageSource().contains("Viewing information for <b>null</b>"));
        
        WebElement table1 = driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/form/table[1]/tbody/tr/td/table[1]"));

        assertEquals("Prescription", table1.findElements(By.tagName("th")).get(0).getText());
        assertEquals("created-date", table1.findElement(By.xpath("//tbody/tr[2]/td[2]/input")).getAttribute("name"));
        assertEquals("Name *", table1.findElement(By.xpath("//tbody/tr[3]/td[1]")).getText());
        assertEquals("2.0", table1.findElement(By.xpath("//tbody/tr[4]/td[2]/input")).getAttribute("value"));
        assertEquals("bad", table1.findElement(By.xpath("//tbody/tr[5]/td[2]/input")).getAttribute("value"));
	    
    }

    /**
     * test select the patient and do the report view
     *
     */
    public void testUC99Add() throws Exception {
        driver.findElement(By.id("j_username")).sendKeys("9000000012");
        driver.findElement(By.id("j_password")).sendKeys("pw");

        driver.findElement(By.cssSelector("input[type='submit']")).click();
        assertEquals("iTrust - HCP Home", driver.getTitle());

        driver.findElement(By.cssSelector("div[anim-target='#info-menu']")).click();
        driver.findElement(By.cssSelector("a[href= '/iTrust/auth/hcp-uap/viewPatientPrescriptions.jsp']")).click();
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

        // choose the pid = 1
        WebElement target = driver.findElement(By.id("searchTarget"));
        assertEquals("MID",driver.findElement(By.xpath("//*[@id=\"searchTarget\"]/table/tbody/tr[1]/th[1]")).getText());
        assertEquals("First Name", driver.findElement(By.xpath("//*[@id=\"searchTarget\"]/table/tbody/tr[1]/th[2]")).getText());
        assertEquals("Last Name", driver.findElement(By.xpath("//*[@id=\"searchTarget\"]/table/tbody/tr[1]/th[3]")).getText());

        WebElement button = target.findElement(By.xpath("//*[@id=\"searchTarget\"]/table/tbody/tr[2]/td[1]/input"));
        assertEquals("1", button.getAttribute("value"));
        button.click();

        // enter the report view page
        assertEquals("iTrust - View Patient Prescriptions", driver.getTitle());
        assertFalse(driver.getPageSource().contains("HTTP Status 500"));
        assertFalse(driver.getPageSource().contains("java.lang.NumberFormatException"));
        assertFalse(driver.getPageSource().contains("Viewing information for <b>null</b>"));

        driver.findElement(By.cssSelector("a[href=\"/iTrust/auth/hcp-uap/addPatientPrescription.jsp?pid=1\"]")).click();

        // enter the report view page
        assertEquals("iTrust - Edit Patient Prescription", driver.getTitle());
        assertFalse(driver.getPageSource().contains("HTTP Status 500"));
        assertFalse(driver.getPageSource().contains("java.lang.NumberFormatException"));
        assertFalse(driver.getPageSource().contains("Viewing information for <b>null</b>"));

        WebElement table1 = driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/form/table[1]/tbody/tr/td/table[1]"));

        assertEquals("Prescription", table1.findElements(By.tagName("th")).get(0).getText());
        assertEquals("Date Prescribed *", table1.findElement(By.xpath("//tbody/tr[2]/td[1]")).getText());
        assertEquals("Name *", table1.findElement(By.xpath("//tbody/tr[3]/td[1]")).getText());
        assertEquals("Dosage*", table1.findElement(By.xpath("//tbody/tr[4]/td[1]")).getText());
        assertEquals("Notes", table1.findElement(By.xpath("//tbody/tr[5]/td[1]")).getText());

    }

    @Override
    @After
    public void tearDown() throws Exception {
        driver.quit();
    }
}

