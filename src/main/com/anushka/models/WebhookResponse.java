package com.anushka.webhooksolution.models;

import lombok.Data;

@Data
public class WebhookResponse {
    private boolean status;
    private String webhook;
    private String accessToken;
}