import Pages.LeviMainPage;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class LeviMainPageTest {
    WebDriver driver;
    private final int implicitTimeout = 10000;
    private final int explicitTimeout = 10000;

    @BeforeClass
    @Parameters({"app", "browser"})
    public void setup(String app, String browser) {

        if(browser.equals("firefox")){
            System.setProperty("webdriver.gecko.driver", ".\\bin\\geckodriver.exe");
            driver = new FirefoxDriver();
        } else if (browser.equals("chrome")) {
            System.setProperty("webdriver.chrome.driver", ".\\bin\\chromedriver.exe");
            driver = new ChromeDriver();
        }

        driver.manage().timeouts().implicitlyWait(implicitTimeout, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get(app);

    }

    @Test(priority = 1)
    public void saleMenuTest() {

        // Links and buttons from Sale menu
        String[] expectedButtonNames = {
                "HOMBRE","Jeans","Pantalones","Bermudas","Remeras y Polos","Camperas","Camisas","Sweaters y Buzos","Trajes de baño","Accesorios",
                "MUJER","Jeans","Shorts","Faldas y Vestidos","Remeras","Camperas","Camisas","Sweaters y Buzos","Accesorios",
                "KIDS","Girls (4 - 7 años)","Boys (4 - 7 años)","Teen Girls (8 - 16 años)","Teen Boys (8 - 16 años)"
        };

        LeviMainPage leviMainPage = new LeviMainPage(driver);
        // Get links and button names
        leviMainPage.hoverToWebElement(driver, leviMainPage.btn_sale);
        List<WebElement> actualLinksAndButtonNames = leviMainPage.getDataFromComponent(driver, leviMainPage.lnk_sale);

        for (int i = 0; i < expectedButtonNames.length; i++) {
            // Print actual button names and its links
            System.out.println(actualLinksAndButtonNames.get(i).getText() + " - " + actualLinksAndButtonNames.get(i).getAttribute("href"));
            // Assert button names
            Assert.assertEquals(actualLinksAndButtonNames.get(i).getText(), expectedButtonNames[i], "Button names doesn't match");
        }

    }

    @Test(priority = 2)
    public void footerTest() {
        LeviMainPage leviMainPage = new LeviMainPage(driver);
        String[] expectedFooterLinks = {
                "https://www.levi.com.ar/ayuda/#faq","https://www.levi.com.ar/ayuda/#faq","https://www.levi.com.ar/ayuda/", "https://www.levi.com.ar/ayuda/#envios",
                "https://www.levi.com.ar/ayuda/#pagos","https://www.levi.com.ar/ayuda/#cambios","https://www.levi.com.ar/promociones/","https://www.levi.com.ar/centro-de-ayuda/",
                "https://www.levi.com.ar/contacts/","tel:(011) 5368 - 2373","tel:(011) 5368 - 2373","mailto:ventaonline@levi.com.ar","https://www.levi.com.ar/contacts",
                "https://www.levi.com.ar/sobre-levis/","https://www.levi.com.ar/puntos-de-venta/",
                "https://www.levi.com.ar/trabajo/","https://www.levi.com.ar/contacts2/","https://www.levi.com.ar/terminos/",
                "https://www.levi.com.ar/politica/","https://twitter.com/levis_ar",
                "https://www.youtube.com/playlist?list=PLv2owRmD8gH84YXDaqCtNa0EnH2dC1Knr","https://www.facebook.com/Levis.Argentina/",
                "https://www.instagram.com/levis_ar/","https://www.levi.com.ar/live-in-levis","http://qr.afip.gob.ar/?qr=uhts28dHsraazzzYTHn4zw,,",
        };
        List<WebElement> actualFooterLinks = leviMainPage.getDataFromComponent(driver, leviMainPage.lnk_footer);

        for (int i = 0; i < expectedFooterLinks.length; i++) {
            // Print footer links
            System.out.println(actualFooterLinks.get(i).getAttribute("href"));
            // Assert footer links
            Assert.assertEquals(actualFooterLinks.get(i).getAttribute("href"), expectedFooterLinks[i], "Footer links doesn't match");
        }
    }

    @Test(priority = 3)
    public void closePopups() {
        LeviMainPage leviMainPage = new LeviMainPage(driver);
        try {
            // Close newsletter
            WebDriverWait wait = new WebDriverWait(driver, explicitTimeout);
            wait.until(ExpectedConditions.elementToBeClickable(leviMainPage.popup_newsletter));
            leviMainPage.btn_closeNewsletter.click();
            wait.until(ExpectedConditions.invisibilityOf(leviMainPage.btn_closeNewsletter));

            // Close promo-header
            leviMainPage.btn_closePromoHeader.click();
            wait.until(ExpectedConditions.invisibilityOf(leviMainPage.promo_header));
        } catch (Exception e) {
            System.out.println("Popup didn't show up");
        }
        // Check if popups aren't in the page any more
        Assert.assertFalse(leviMainPage.popup_newsletter.isDisplayed());
        Assert.assertFalse(leviMainPage.promo_header.isDisplayed());
    }

    @Test(priority = 4)
    public void jsExecutorTest() {
        LeviMainPage leviMainPage = new LeviMainPage(driver);
        JavascriptExecutor js = (JavascriptExecutor)driver;

        // Fetching the Domain Name of the site
        String expectedDomain = "www.levi.com.ar";
        String actualDomainName = js.executeScript("return document.domain;").toString();
        Assert.assertEquals(actualDomainName, expectedDomain, "Invalid domain name");

        // Scroll down
        js.executeScript("window.scrollBy(0,2000)");
        WebDriverWait wait = new WebDriverWait(driver, explicitTimeout);
        wait.until(ExpectedConditions.elementToBeClickable(leviMainPage.btn_toTop));

        // Click on web element
        js.executeScript("arguments[0].click();", leviMainPage.btn_toTop);
        // Wait until toTop button disappear again
        wait.until(ExpectedConditions.invisibilityOf(leviMainPage.btn_toTop));
        Assert.assertFalse(leviMainPage.btn_toTop.isDisplayed());

        // Type some text
        js.executeScript ("document.getElementById('search').innerText='Jeans';" +
                            "document.getElementById('search').value='Jeans';");
        Assert.assertEquals(leviMainPage.inp_search.getText(), "Jeans", "Wrong value in search input");

        // Clear input text
        js.executeScript ("document.getElementById('search').innerText='';" +
                            "document.getElementById('search').value='';");
        Assert.assertEquals(leviMainPage.inp_search.getText(), "", "Search input is not clear");

        //Navigate to new Page
        js.executeScript("window.location = 'https://www.levi.com.ar/e-shop/hombres.html'");
        // Fetching the URL of the page
        String expectedNewUrl = "https://www.levi.com.ar/e-shop/hombres.html";
        String actualNewUrl = js.executeScript("return document.URL;").toString();
        Assert.assertEquals(actualNewUrl, expectedNewUrl, "Invalid new URL");

    }

    @AfterClass
    public void close() {
        driver.close();

    }
}
