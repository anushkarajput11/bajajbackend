package com.anushka.webhooksolution;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class WebhookSolutionApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebhookSolutionApplication.class, args);
    }
}

@Component
class WebhookInitializer {

    private final WebhookService webhookService;

    public WebhookInitializer(WebhookService webhookService) {
        this.webhookService = webhookService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initializeWebhook() {
        webhookService.processWebhookFlow();
    }
}