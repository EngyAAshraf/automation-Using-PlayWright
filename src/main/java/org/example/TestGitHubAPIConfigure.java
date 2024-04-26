package org.example;


import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import java.util.HashMap;
import java.util.Map;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestGitHubAPIConfigure
{
    private static final String API_TOKEN= System.getenv("GITHUB_API_TOKEN");
    private Playwright playwright;
    private APIRequestContext request;

    void createPlaywright(){
        playwright=Playwright.create();
    }
    void createAPIRequestContext (){
        Map<String,String>headers= new HashMap<>();
        headers.put("Accept", "application/vnd.github.v3+json");
        headers.put("Authorization", "token " + API_TOKEN);

        request=playwright.request().newContext(new APIRequest.NewContextOptions()
                .setBaseURL("https://api.github.com")
                .setExtraHTTPHeaders(headers));
    }

    @BeforeAll
    void beforeAll(){
        createPlaywright();
        createAPIRequestContext();
    }

    void  disposeAPIRequestContext(){
        if (request!=null){
            request.dispose();
            request =null;
        }
    }

    void closePlaywright(){
        if (playwright!=null){
            playwright.close();
            playwright=null;
        }
    }

    @AfterAll
    void afterAll(){
        disposeAPIRequestContext();
        closePlaywright();
    }

}
