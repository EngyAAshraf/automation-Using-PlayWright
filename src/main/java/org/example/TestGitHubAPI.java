package org.example;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class TestGitHubAPI
{
    private static final String REPO = "test-repo-2";
    private static final String USER = System.getenv("GITHUB_USER");
    private static final String API_TOKEN = System.getenv("GITHUB_API_TOKEN");
    private Playwright playwright;
    private APIRequestContext request;


    void createPlaywright() {
        playwright = Playwright.create();
    }

    void createAPIRequestContext() {
        Map<String, String> headers = new HashMap<>();
        // We set this header per GitHub guidelines.
        headers.put("Accept", "application/vnd.github.v3+json");
        // Add authorization token to all requests.
        // Assuming personal access token available in the environment.
        headers.put("Authorization", "token " + API_TOKEN);

        request = playwright.request().newContext(new APIRequest.NewContextOptions()
                // All requests we send go to this API endpoint.
                .setBaseURL("https://api.github.com")
                .setExtraHTTPHeaders(headers));
    }

    @Test
    void shouldCreateBugReport()
    {
        createPlaywright();
        createAPIRequestContext();
        Map<String, String> data = new HashMap<>();
        data.put("title", "[Bug] report 1");
        data.put("body", "Bug description");
        APIResponse newIssue = request.post("/repos/" + USER + "/" + REPO + "/issues",
                RequestOptions.create().setData(data));
        assertTrue(newIssue.ok());

        APIResponse issues = request.get("/repos/" + USER + "/" + REPO + "/issues");
        assertTrue(issues.ok());
        JsonArray json = new Gson().fromJson(issues.text(), JsonArray.class);
        JsonObject issue = null;
        for (JsonElement item : json)
        {
            JsonObject itemObj = item.getAsJsonObject();
            if (!itemObj.has("title"))
            {
                continue;
            }
            if ("[Bug] report 1".equals(itemObj.get("title").getAsString()))
            {
                issue = itemObj;
                break;
            }
        }
        assertNotNull(issue);
        assertEquals("Bug description", issue.get("body").getAsString(), issue.toString());
    }

    @Test
    void shouldCreateFeatureRequest()
    {
        createPlaywright();
        createAPIRequestContext();
        Map<String, String> data = new HashMap<>();
        data.put("title", "[Feature] request 1");
        data.put("body", "Feature description");
        APIResponse newIssue = request.post("/repos/" + USER + "/" + REPO + "/issues",
                RequestOptions.create().setData(data));
        assertTrue(newIssue.ok());
        APIResponse issues = request.get("/repos/" + USER + "/" + REPO + "/issues");
        assertTrue(issues.ok());
        JsonArray json = new Gson().fromJson(issues.text(), JsonArray.class);
        JsonObject issue = null;
        for (JsonElement item : json)
        {
            JsonObject itemObj = item.getAsJsonObject();
            if (!itemObj.has("title"))
            {
                continue;
            }
            if ("[Feature] request 1".equals(itemObj.get("title").getAsString()))
            {
                issue = itemObj;
                break;
            }
        }
        assertNotNull(issue);
        assertEquals("Feature description", issue.get("body").getAsString(), issue.toString());
    }

}
//
//    APIRequestContext requestContext = playwright.request().newContext(
//            new APIRequest.NewContextOptions().setHttpCredentials("user", "passwd"));
//requestContext.get("https://api.example.com/login");
//// Save storage state into a variable.
//        String state = requestContext.storageState();
//
//// Create a new context with the saved storage state.
//        BrowserContext context = browser.newContext(new Browser.NewContextOptions().setStorageState(state));