//package org.example;
//import com.deque.html.axecore.playwright.*; // 1
//import com.deque.html.axecore.utilities.axeresults.*;
//import com.microsoft.playwright.*;
//import org.junit.jupiter.api.*;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//import java.util.stream.Collectors;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class HomepageTests //Example accessibility tests
//{
//    @Test
//    void shouldNotHaveAutomaticallyDetectableAccessibilityIssues()throws Exception{
//        Playwright playwright= Playwright.create();
//        Browser browser= playwright.chromium().launch();
//        BrowserContext context= browser.newContext();
//        Page page= context.newPage();
//
//        page.navigate("https://your-site.com/");
//        AxeResults  accessibilityScanResults= new AxeBuilder(page).analyze();
//      assertEquals(Collections.emptyList(), accessibilityScanResults.getViolations());
//    }
//    @Test
//    void navigationMenuFlyoutShouldNotHaveAutomaticallyDetectableAccessibilityViolations()throws Exception{
//        Playwright playwright= Playwright.create();
//        Browser browser= playwright.chromium().launch();
//        BrowserContext context= browser.newContext();
//        Page page= context.newPage();
//
//        page.navigate("https://your-site.com");
//        try
//        {
//            page.locator("button[aria-label=\"Navigation Menu\"]").click(new Locator.ClickOptions().setTimeout(100));
//        }
//        catch(TimeoutError e)
//        {
//            System.out.println("Timeout! ");
//        }
//        page.locator("#navigation-menu-flyout").waitFor();
//        AxeResults accessibilityScanResults = new AxeBuilder(page)
//                .include(Arrays.asList("#navigation-menu-flyout"))
//                .analyze();
//        // if you need exclude any elements
//        // for example
//        // AxeResults accessibilityScanResults = new AxeBuilder(page)
//        //         .exclude(Arrays.asList("#element-with-known-issue"))
//        //         .analyze();
//        // if you need disable el
////        AxeResults accessibilityScanResults = new AxeBuilder(page)
////                .disableRules(Arrays.asList("duplicate-id"))
////                .analyze();
//        assertEquals(Collections.emptyList(), accessibilityScanResults.getViolations());
//    }
//
//    @Test
//    void shouldOnlyHaveAccessibilityViolationsMatchingKnownFingerprints()throws Exception
//    {
//        Playwright playwright = Playwright.create();
//        Browser browser = playwright.chromium().launch();
//        BrowserContext context = browser.newContext();
//        Page page = context.newPage();
//        page.navigate("https://your-site.com/");
//        AxeResults accessibilityScanResults = new AxeBuilder(page).analyze();
//
//        List<ViolationFingerprint> violationFingerprints = fingerprintsFromScanResults(accessibilityScanResults);
//        assertEquals(Arrays.asList(
//                new ViolationFingerprint("aria-roles", "[span[role=\"invalid\"]]"),
//                new ViolationFingerprint("color-contrast", "[li:nth-child(2) > span]"),
//                new ViolationFingerprint("label", "[input]")
//        ), ViolationFingerprint);
//    }
//    // You can make your "fingerprint" as specific as you like. This one considers a violation to be
//// "the same" if it corresponds the same Axe rule on the same element.
////
//// Using a record type makes it easy to compare fingerprints with assertEquals
//
//    public record ViolationFingerprint(String ruleId, String target){ }
//
//    public List<ViolationFingerprint> fingerprintsFromScanResults(AxeResults results){
//        return results.getViolations().stream()
//                .flatMap(violation -> violation.getNodes().stream())
//                .map(node-> new ViolationFingerprint(
//                        violation.getId(),
//                        node.getTarget().toString()
//                ))
//                .collect(Collectors.toList());
//    }
//
//    @Test
//    void exampleUsingCustomFixture() throws Exception {
//        Playwright playwright = Playwright.create();
//        Browser browser = playwright.chromium().launch();
//        BrowserContext context = browser.newContext();
//        Page page = context.newPage();
//        page.navigate("https://your-site.com/");
//
//        AxeResults accessibilityScanResults = makeAxeBuilder()
//                // Automatically uses the shared AxeBuilder configuration,
//                // but supports additional test-specific configuration too
//                .include("#specific-element-under-test")
//                .analyze();
//
//        assertEquals(Collections.emptyList(), accessibilityScanResults.getViolations());
//    }
//
//    }
//
