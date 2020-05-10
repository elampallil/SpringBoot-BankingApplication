package binson.banking.binsonbank.RepoService;

import binson.banking.binsonbank.Request.CreateUserRequest;
import binson.banking.binsonbank.aggregate.UserDetailsAggregate;

public interface UserRepoService {

    public int userDataSave(CreateUserRequest createUserRequest, String token, String cust_id);

    public UserDetailsAggregate getUserDetails(int accountNumber);

    public int changePswd(int accountNumber, String password);

    public UserDetailsAggregate getUserDetailsByCustomerId(String custId);


}
