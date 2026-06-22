package com.test.cph.utility;

import org.springframework.context.annotation.Configuration;

import java.security.SecureRandom;
import java.util.UUID;

@Configuration
public class AccountNumberGenerator {

    public String generateAccountNumber() {
        SecureRandom random = new SecureRandom();
        StringBuilder number = new StringBuilder();

//        number.append("ACC-");
//        number.append(branchCode); // e.g., NAG001

        for (int i = 0; i < 12; i++) {
            number.append(random.nextInt(10));
        }
        return number.toString();
    }


}
