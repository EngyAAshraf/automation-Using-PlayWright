package org.example;

import com.microsoft.playwright.*;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Example {
    public static void main(String[] args) {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch();
            Page page = browser.newPage();
            page.navigate("http://playwright.dev");
            System.out.println(page.title());

// TODO :persistent authentication

            Path userDataDir = Paths.get("/path/to/directory");
            BrowserContext context = playwright.chromium().launchPersistentContext(userDataDir,
                    new BrowserType.LaunchPersistentContextOptions().setHeadless(false));
            // Execute login steps manually in the browser window
        }
    }
}