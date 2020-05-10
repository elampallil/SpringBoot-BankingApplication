package binson.banking.binsonbank.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TokenServiceImpl implements TokenService{

    @Value("${my.secret.key}")
    private String mySecretKey;

    @Override
    public String generateToken(String customerId, String password) {

        return customerId+this.mySecretKey+password;
    }

    @Override
    public String getcustomteridfromtoken(String token) {
        String [] result = token.split(this.mySecretKey);
        return result[0];
    }
}
