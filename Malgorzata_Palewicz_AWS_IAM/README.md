## IAM Policy Verifier

This Java application verifies the correctness of IAM (Identity and Access Management) policies in JSON format.

### Features

- Verifies if the provided JSON file contains a valid IAM policy.
- Checks if each statement in the policy is valid.
- Ensures that resources in the policy do not contain a single asterisk.
- Includes unit tests to cover various scenarios and edge cases.

### Additional Notes
When running the application, you may encounter an error message:
"One or more resources in the policy contain a single *." because 'Resource' field contains a single asterisk

### Usage

1. Navigate to the project directory:

```bash
cd "path"
```

2. Run the project:

```bash
java -jar Malgorzata_Palewicz_AWS_IAM-1.0-SNAPSHOT.jar
```

### Requirements

- Java Development Kit (JDK) installed on your system.


### Contact

For any inquiries or issues, please contact [malgorzata.maria.palewicz@gmail.com](malgorzata.maria.palewicz@gmail.com).