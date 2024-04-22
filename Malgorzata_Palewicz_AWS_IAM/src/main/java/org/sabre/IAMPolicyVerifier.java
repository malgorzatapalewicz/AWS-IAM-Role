package org.sabre;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class IAMPolicyVerifier {

    public void verifyPolicy(String filePath){
        try {
            JsonElement jsonElement = JsonParser.parseReader(new FileReader(filePath));
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            if(!verifyFile(jsonObject)){
                throw new IllegalArgumentException("Provided file does not contain a valid IAM policy.");
            }

            JsonObject policyDocument = jsonObject.getAsJsonObject("PolicyDocument");

            //within AWS IAM service, it's possible to assign multiple statments to a single role or policy
            for (JsonElement statement : policyDocument.getAsJsonArray("Statement")) {
                if (!isValidStatement(statement.getAsJsonObject())) {
                    throw new IllegalStateException("One or more statements in the policy are invalid.");
                }
                if (!verifyResource(statement.getAsJsonObject())) {
                    throw new IllegalStateException("One or more resources in the policy contain a single *.");
                }
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    private boolean verifyResource(JsonObject statement){
        return statement.has("Resource") &&
                !statement.get("Resource").getAsString().isEmpty() &&
                !"*".equals(statement.get("Resource").getAsString());
    }
    private boolean verifyFile(JsonObject object) {
        return object.has("PolicyDocument") &&
                object.get("PolicyDocument").getAsJsonObject().has("Statement") &&
                !object.get("PolicyDocument").getAsJsonObject().get("Statement").getAsJsonArray().isEmpty();
    }

    private boolean isValidStatement(JsonObject statement) {
        return statement.has("Effect") &&
                statement.has("Action") &&
                statement.has("Resource") &&
                !statement.get("Effect").getAsString().isEmpty() &&
                !statement.get("Action").getAsJsonArray().isEmpty() &&
                !statement.get("Resource").getAsString().isEmpty();

    }

    public static void main(String[] args) {
        String path = "src/main/resources/data.json";
        IAMPolicyVerifier verifier = new IAMPolicyVerifier();
        verifier.verifyPolicy(path);
    }
}
