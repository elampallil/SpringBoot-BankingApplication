package binson.banking.binsonbank.Service;

import binson.banking.binsonbank.Request.CreateUserRequest;
import binson.banking.binsonbank.Request.EmailInfo;
import binson.banking.binsonbank.Response.ResponseMessage;
import binson.banking.binsonbank.aggregate.UserDetailsAggregate;

import javax.mail.MessagingException;
import java.io.IOException;

public interface UserService {
    public ResponseMessage userDataSave(CreateUserRequest createUserRequest);
    public UserDetailsAggregate getUserDetails( int accountNumber);
    public ResponseMessage changePswd ( CreateUserRequest createUserRequest);
    public ResponseMessage userLogin ( CreateUserRequest createUserRequest);
    public ResponseMessage sendEmail (EmailInfo emailInfo) throws IOException, MessagingException;
}
