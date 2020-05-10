package binson.banking.binsonbank.Service;

public interface TokenService {

    public String generateToken(String customerId,String password);

    public String getcustomteridfromtoken(String token);
}
