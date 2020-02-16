package com.reklama.lv.objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Objects;

public class Ad {

    private String link;
    private String text;
    private String img;

    public Ad(WebElement element) {
        link = element.findElement(By.xpath(".//td[@class='img']//a")).getAttribute("href");
        text = element.findElement(By.xpath(".//*[@class='text']")).getText();
        img = element.findElement(By.xpath(".//img")).getAttribute("src");
        System.out.println("link: " + link);
        System.out.println("text: " + text);
        System.out.println("img: " + img);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ad ad = (Ad) o;
        return Objects.equals(link, ad.link);
    }

    @Override
    public int hashCode() {
        return Objects.hash(link);
    }
}
