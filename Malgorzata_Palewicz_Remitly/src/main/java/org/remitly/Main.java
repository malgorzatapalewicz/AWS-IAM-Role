package org.remitly;

public class Main {

    public static void main(String[] args) {
        String filePath = "src/main/resources/data.json";
        IAMPolicyVerifier verifier = new IAMPolicyVerifier();
        verifier.verifyPolicy(filePath);
    }
}
