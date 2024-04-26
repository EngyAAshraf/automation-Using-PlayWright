package org.example;
import java.nio.file.Paths;
import com.microsoft.playwright.*;
//import { test, expect } from '@playwright/test';
public class WebKitScreenshot {

    public static void main (String[] args){
        try (Playwright playwright= Playwright.create()){
           Browser browser=
                   playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(50));
        //    Playwright.webkit().launch();
           Page page = browser.newPage();
           page.navigate("https://playwright.dev/");
            
           page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("example.png")));

        }

    }
}
