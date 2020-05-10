package binson.banking.binsonbank.Service;

import binson.banking.binsonbank.Request.CreateUserRequest;
import binson.banking.binsonbank.Response.ResponseMessage;
import binson.banking.binsonbank.aggregate.UserDetailsAggregate;

public interface UserService {
    public ResponseMessage userDataSave(CreateUserRequest createUserRequest);
    public UserDetailsAggregate getUserDetails( int accountNumber);
    public ResponseMessage changePswd ( CreateUserRequest createUserRequest);
    public ResponseMessage userLogin ( int accountNumber,String password);
}
