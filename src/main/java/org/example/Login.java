package org.example;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

public class Login
{
    Playwright playwright=Playwright.create();
    Browser browser= playwright.chromium().launch();
    BrowserContext context=browser.newContext();
    Page page = context.newPage();
    @Test
            public void loginCredential()
    {
        page.navigate("https://github.com/login");
        page.locator("text=Login").click();
        page.locator("input[name='login']").fill("USERNAME");
        page.locator("input[name='password']").fill("PASSWORD");
        page.locator("text=Submit").click();
        context.storageState(new BrowserContext.StorageStateOptions().setPath(Paths.get("state.json")));
        BrowserContext context= browser.newContext(
          new Browser.NewContextOptions().setStorageStatePath(Paths.get("state.json")));

        // Get session storage and store as env variable
        String sessionStorage = (String) page.evaluate("JSON.stringify(sessionStorage)");
        System.getenv().put("SESSION_STORAGE", sessionStorage);

// Set session storage in a new context
         sessionStorage = System.getenv("SESSION_STORAGE");
        context.addInitScript("(storage => {\n" +
                "  if (window.location.hostname === 'example.com') {\n" +
                "    const entries = JSON.parse(storage);\n" +
                "     for (const [key, value] of Object.entries(entries)) {\n" +
                "      window.sessionStorage.setItem(key, value);\n" +
                "    };\n" +
                "  }\n" +
                "})('" + sessionStorage + "')");
    }
}
