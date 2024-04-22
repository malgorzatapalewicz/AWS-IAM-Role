import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sabre.IAMPolicyVerifier;

import static org.junit.jupiter.api.Assertions.*;

public class IAMPolicyVerifierTest {
    private IAMPolicyVerifier verifier;

    @BeforeEach
    public void set(){
        verifier = new IAMPolicyVerifier();
    }

    @Test
    public void multipleStatements(){
        IAMPolicyVerifier verifier = new IAMPolicyVerifier();
        String validFilePath = "src/main/resources/validTest.json";
        assertDoesNotThrow(() -> verifier.verifyPolicy(validFilePath));
    }

    @Test
    public void invalidResource(){
        String invalidFilePath = "src/main/resources/invalidTest.json";
        assertThrows(IllegalStateException.class, () -> verifier.verifyPolicy(invalidFilePath));
    }

    @Test
    public void isValidStatementInvalid() {
        String filePath = "src/main/resources/invalidStatement.json";
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> verifier.verifyPolicy(filePath));
        assertEquals("One or more statements in the policy are invalid.", exception.getMessage());
    }

    @Test
    public void invalidFilePath() {
        String filePath = "nonexistent.json";
        RuntimeException exception = assertThrows(RuntimeException.class, () -> verifier.verifyPolicy(filePath));
        assertNotNull(exception.getMessage());
    }
}
