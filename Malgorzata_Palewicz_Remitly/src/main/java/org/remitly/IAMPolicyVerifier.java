package org.remitly;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class IAMPolicyVerifier {

    public void verifyPolicy(JsonNode rootNode) {
        if (!verifyFile(rootNode)) {
            throw new IllegalArgumentException("Provided file does not contain a valid IAM policy.");
        }

        JsonNode policyDocument = rootNode.get("PolicyDocument");

        // W obrębie usługi AWS IAM możliwe jest przypisanie wielu instrukcji do pojedynczej roli lub polisy
        for (JsonNode statement : policyDocument.get("Statement")) {
            if (!isValidStatement(statement)) {
                throw new IllegalStateException("One or more statements in the policy are invalid.");
            }
            if (!verifyResource(statement)) {
                throw new IllegalStateException("One or more resources in the policy contain a single *.");
            }
        }
    }

    private boolean verifyResource(JsonNode statement) {
        return statement.has("Resource") &&
                !statement.get("Resource").asText().isEmpty() &&
                !"*".equals(statement.get("Resource").asText());
    }

    private boolean verifyFile(JsonNode rootNode) {
        return rootNode.has("PolicyDocument") &&
                rootNode.get("PolicyDocument").has("Statement") &&
                !rootNode.get("PolicyDocument").get("Statement").isEmpty();
    }

    private boolean isValidStatement(JsonNode statement) {
        return statement.has("Effect") &&
                statement.has("Action") &&
                statement.has("Resource") &&
                !statement.get("Effect").asText().isEmpty() &&
                !statement.get("Action").isEmpty() &&
                !statement.get("Resource").asText().isEmpty();
    }
}
