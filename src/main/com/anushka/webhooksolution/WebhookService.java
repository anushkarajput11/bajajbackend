package com.anushka.webhooksolution;

import com.anushka.webhooksolution.models.SolutionRequest;
import com.anushka.webhooksolution.models.WebhookRequest;
import com.anushka.webhooksolution.models.WebhookResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class WebhookService {
    
    private static final Logger logger = LoggerFactory.getLogger(WebhookService.class);
    private static final String WEBHOOK_GENERATE_URL = "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA";
    private final String NAME = "Anushka Rajput";
    private final String REG_NO = "0827CY221014";
    private final String EMAIL = "anushkarajput22108@acropolis.in";
    
    private final WebClient webClient;
    private final SqlProblemSolver sqlProblemSolver;
    
    public WebhookService(WebClient.Builder webClientBuilder, SqlProblemSolver sqlProblemSolver) {
        this.webClient = webClientBuilder.build();
        this.sqlProblemSolver = sqlProblemSolver;
    }
    
    public void processWebhookFlow() {
        logger.info("Starting webhook flow process");
        
        // Step 1: Generate webhook
        WebhookResponse webhookResponse = generateWebhook();
        if (webhookResponse == null) {
            logger.error("Failed to generate webhook");
            return;
        }
        
        logger.info("Webhook generated successfully: {}", webhookResponse.getWebhook());
        
        // Step 2: Solve SQL problem based on registration number
        String sqlSolution = sqlProblemSolver.solveSqlProblem(REG_NO);
        
        // Step 3: Submit solution to webhook
        submitSolution(webhookResponse.getWebhook(), webhookResponse.getAccessToken(), sqlSolution);
    }
    
    private WebhookResponse generateWebhook() {
        logger.info("Generating webhook with details - Name: {}, RegNo: {}, Email: {}", NAME, REG_NO, EMAIL);
        
        try {
            WebhookResponse response = webClient.post()
                    .uri(WEBHOOK_GENERATE_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(new WebhookRequest(NAME, REG_NO, EMAIL))
                    .retrieve()
                    .bodyToMono(WebhookResponse.class)
                    .block();
                    
            logger.info("Webhook response received: {}", response);
            return response;
        } catch (Exception e) {
            logger.error("Error generating webhook: {}", e.getMessage(), e);
            return null;
        }
    }
    
    private void submitSolution(String webhookUrl, String accessToken, String sqlSolution) {
        logger.info("Submitting solution to webhook URL: {}", webhookUrl);
        
        try {
            SolutionRequest solutionRequest = new SolutionRequest(sqlSolution);
            
            String response = webClient.post()
                    .uri(webhookUrl)
                    .header(HttpHeaders.AUTHORIZATION, accessToken)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(solutionRequest)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
                    
            logger.info("Solution submission response: {}", response);
        } catch (Exception e) {
            logger.error("Error submitting solution: {}", e.getMessage(), e);
        }
    }
}