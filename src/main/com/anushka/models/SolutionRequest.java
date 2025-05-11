package main.com.anushka.WebhookSolution.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolutionRequest {
    private String finalQuery;
}