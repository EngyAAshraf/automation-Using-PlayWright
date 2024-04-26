package org.example;

import com.microsoft.playwright.*;

import java.nio.file.Paths;


public class RecordingTrace {
    public static void main(String[] args) {
        try (Playwright playwright = Playwright.create()) {

    Browser browser = playwright.chromium().launch();
    BrowserContext context = browser.newContext();

    // start tracking before creating / navigating a page.
    context.tracing().start(new Tracing.StartOptions()
            .setScreenshots(true)
            .setSnapshots(true)
            .setSources(true));
    Page page= context.newPage();
    page.navigate("https://playwright.dev");
            page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("example.png")));

    // Stop tracing and export it into a zip archive.

    context.tracing().stop(new Tracing.StopOptions()
            .setPath(Paths.get("trace.zip")));


        }}}

//Opening the trace
//mvn exec:java -e -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.args="show-trace trace.zip"