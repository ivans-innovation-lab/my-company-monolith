package com.idugalic;

import com.idugalic.commandside.blog.web.CreateBlogPostRequest;
import com.idugalic.commandside.project.web.CreateProjectRequest;
import com.idugalic.common.blog.model.BlogPostCategory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration test for {@link Application} starting on a random port.
 *
 * @author idugalic
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ApplicationIntegrationTest {

    @Value("${local.server.port}")
    protected int port;

    private OAuth2RestTemplate outh2RestTemplate;

    private CreateBlogPostRequest createBlogPostRequest;
    private CreateProjectRequest createProjectRequest;
    private Calendar future;


    @Before
    public void setup() {

        //Some test data
        future = Calendar.getInstance();
        future.add(Calendar.DAY_OF_YEAR, 1);

        createBlogPostRequest = new CreateBlogPostRequest();
        createBlogPostRequest.setTitle("title");
        createBlogPostRequest.setCategory(BlogPostCategory.ENGINEERING);
        createBlogPostRequest.setBroadcast(Boolean.FALSE);
        createBlogPostRequest.setDraft(Boolean.TRUE);
        createBlogPostRequest.setPublicSlug("publicurl");
        createBlogPostRequest.setRawContent("rawContent");
        createBlogPostRequest.setPublishAt(future.getTime());

        createProjectRequest = new CreateProjectRequest();
        createProjectRequest.setCategory("category");
        createProjectRequest.setDescription("description");
        createProjectRequest.setName("name");
        createProjectRequest.setRepoUrl("repoUrl");
        createProjectRequest.setSiteUrl("siteUrl");

        //Configure RestTemplate

        ResourceOwnerPasswordResourceDetails resourceDetails = new ResourceOwnerPasswordResourceDetails();
        resourceDetails.setUsername("john.doe");
        resourceDetails.setPassword("jwtpass");
        resourceDetails.setAccessTokenUri(format("http://localhost:%d/oauth/token", port));
        resourceDetails.setClientId("testjwtclientid");
        resourceDetails.setClientSecret("MaYzkSjmkzPC57L");
        resourceDetails.setGrantType("password");
        resourceDetails.setScope(asList("read", "write"));

        DefaultOAuth2ClientContext clientContext = new DefaultOAuth2ClientContext();

        outh2RestTemplate = new OAuth2RestTemplate(resourceDetails, clientContext);
    }

    @Test
    public void createBlogPostWithSuccess() throws Exception {

        HttpEntity<CreateBlogPostRequest> request = new HttpEntity<>(createBlogPostRequest);

        ResponseEntity<String> response = outh2RestTemplate.exchange(format("http://localhost:%d/api/blogpostcommands", port), HttpMethod.POST, request, String.class);
        System.out.println("################ " + response.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

    }

    @Test
    public void createProjectWithSuccess() throws Exception {

        HttpEntity<CreateProjectRequest> request = new HttpEntity<>(createProjectRequest);

        ResponseEntity<String> response = outh2RestTemplate.exchange(format("http://localhost:%d/api/projectcommands", port), HttpMethod.POST, request, String.class);
        System.out.println("################ " + response.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

    }
//
//    @Test
//    public void createBlogPostWithValidationErrorTitleIsMandatory() throws Exception {
//
//        createBlogPostRequest.setTitle(null);
//        HttpEntity<CreateBlogPostRequest> request = new HttpEntity<>(createBlogPostRequest);
//
//        ResponseEntity<String> response = outh2RestTemplate.exchange(format("http://localhost:%d/api/blogpostcommands", port), HttpMethod.POST, request, String.class);
//        System.out.println("1################ "+response.getBody());
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
//
//    }
//
//    @Test
//    public void createProjectWithValidationErrorNameIsMandatory() throws Exception {
//
//        createProjectRequest.setName(null);
//        HttpEntity<CreateProjectRequest> request = new HttpEntity<>(createProjectRequest);
//
//        ResponseEntity<String> response = outh2RestTemplate.exchange(format("http://localhost:%d/api/projectcommands", port), HttpMethod.POST, request, String.class);
//        System.out.println("2################ "+response.getBody());
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
//
//    }

}
