import Pages.LeviMainPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class LeviMainPageTest {
    WebDriver driver;
    private final int implicitTimeout = 20000;
    private final int explicitTimeout = 10000;

    @BeforeClass
    public void setup() {
        System.setProperty("webdriver.chrome.driver", ".\\bin\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(implicitTimeout, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("https://www.levi.com.ar/");
    }

    @Test
    public void InteractionsOverLeviMainPage() {

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

        // Footer test
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

        // Close popups
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

    @AfterClass
    public void close() {
        driver.quit();
    }
}
