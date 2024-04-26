package org.example;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

//Running Tests in Parallel

// Subclasses will inherit PER_CLASS behavior.
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class TestFixtures {
    // shared between all tests in the class
    Playwright playwright;
    Browser browser;

    @BeforeAll
    void launchBrowser(){
        playwright=Playwright.create();
        browser= playwright.chromium().launch();
    }

    @AfterAll
    void closeBrowser(){
        playwright.close();
    }
    // New instance for each test method
    BrowserContext context;
    Page page;

    @BeforeEach
    void createContextAndPage(){
        context=browser.newContext();
        page= context.newPage();
    }
    @AfterEach
    void closeContext(){
        context.close();
    }

}
 class Test1 extends TestFixtures{

    @Test
    void shouldClickButton(){
        page.navigate("data:text/html,<script>var result;</script><button onclick='result=\"Clicked\"'>Go</button>");
        page.locator("button").click();
        assertEquals("Clicked",page.evaluate("result"));
    }

    @Test
    void shouldCheckTheBox(){
        page.setContent("<input id='checkbox' type='checkbox'></input>");
        page.locator("input").check();
        assertTrue((Boolean) page.evaluate("() => window['checkbox'].checked"));
    }

    @Test
    void shouldSearchWiki(){
        page.navigate("https://www.wikipedia.org/");
        page.locator("input[name=\"search\"]").click();
        page.locator("input[name=\"search\"]").fill("playwright");
        page.locator("input[name=\"search\"]").press("Enter");
        assertEquals("https://en.wikipedia.org/wiki/Playwright",page.url());
    }
}

class Test2 extends TestFixtures{

    @Test
    void shouldReturnInnerHTML(){
        page.setContent("<div>hello</div>");
        assertEquals("hello",page.innerHTML("css=div"));
    }

    @Test
    void shouldClickButton(){
        Page popup= page.waitForPopup(()->{
            page.evaluate("window.open('about:blank');");
        });
        assertEquals("about:blank",popup.url());
    }
}
// configure Junit
/*
junit.jupiter.execution.parallel.enabled = true
junit.jupiter.execution.parallel.mode.default = same_thread
junit.jupiter.execution.parallel.mode.classes.default = concurrent
junit.jupiter.execution.parallel.config.strategy=dynamic
junit.jupiter.execution.parallel.config.dynamic.factor=0.5
 */