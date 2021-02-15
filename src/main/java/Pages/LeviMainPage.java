package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class LeviMainPage {

    public LeviMainPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//li[@id='cat-sale']")
    public WebElement btn_sale;

    @FindBy(css = "#popup-newsletter")
    public WebElement popup_newsletter;

    @FindBy(xpath = "//button[@class='cerrar no-boton']")
    public WebElement btn_closeNewsletter;

    @FindBy(css = "#promo-header")
    public WebElement promo_header;

    @FindBy(xpath = "//*[@id='promo-header']//i")
    public WebElement btn_closePromoHeader;

    @FindBy(id = "toTop")
    public WebElement btn_toTop;

    @FindBy(id = "search")
    public WebElement inp_search;

    public String lnk_sale = "//li[@id='cat-sale']//div//a";
    public String lnk_footer = "//footer//li//a";

    public void hoverToWebElement(WebDriver driver, WebElement webElement){
        Actions builder = new Actions(driver);
        Action mouseOver = builder
                .moveToElement(webElement)
                .build();
        mouseOver.perform();
    }

    public List<WebElement> getDataFromComponent(WebDriver driver, String locator) {
        List<WebElement> data = driver.findElements(By.xpath(locator));
        return data;
    }
}

