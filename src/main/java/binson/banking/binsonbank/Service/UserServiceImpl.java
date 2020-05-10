package binson.banking.binsonbank.Service;

import binson.banking.binsonbank.RepoService.UserRepoService;
import binson.banking.binsonbank.Request.CreateUserRequest;
import binson.banking.binsonbank.Response.ResponseMessage;
import binson.banking.binsonbank.aggregate.UserDetailsAggregate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;


@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepoService userRepoService;


    @Autowired
    private TokenService tokenService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Override
    public ResponseMessage userDataSave(CreateUserRequest createUserRequest) {
        ResponseMessage responseMessage = new ResponseMessage();
        String customer_id = UUID.randomUUID().toString();
        String token = tokenService.generateToken(customer_id, createUserRequest.getPassword());

        responseMessage.setToken(token);

        int rowCount = userRepoService.userDataSave(createUserRequest, token, customer_id);

        if (rowCount < 1) {
            responseMessage.setResponseMessage("something went wrong");

        } else {
            responseMessage.setResponseMessage("successfully updated");
        }


        return responseMessage;
    }

    @Override
    public UserDetailsAggregate getUserDetails(int accountNumber) {
        if (isThisRequestValid(accountNumber)) {
            UserDetailsAggregate userDetailsAggregate = userRepoService.getUserDetails(accountNumber);
            return userDetailsAggregate;
        } else {
            return null;
        }

    }

    @Override
    public ResponseMessage changePswd(CreateUserRequest createUserRequest) {
        int accountNumber = createUserRequest.getAccountNumber();
        String password = createUserRequest.getPassword();
        ResponseMessage responseMessage = new ResponseMessage();
        int rowCount = userRepoService.changePswd(accountNumber, password);
        if (rowCount < 1) {


            responseMessage.setResponseMessage("something went wrong");
        } else {
            responseMessage.setResponseMessage(" password updated successfully");

        }
        return responseMessage;
    }

    @Override
    public ResponseMessage userLogin(int accountNumber, String password) {
        ResponseMessage responseMessage = new ResponseMessage();
        UserDetailsAggregate userDetailsAggregate = userRepoService.getUserDetails(accountNumber);
        if (userDetailsAggregate != null) {
            if (userDetailsAggregate.getPassword().equals(password)) {
                String token = tokenService.generateToken(userDetailsAggregate.getCustomerId(),
                        userDetailsAggregate.getPassword());
                responseMessage.setToken(token);
                responseMessage.setResponseMessage("success");

            }
            else{
                responseMessage.setResponseMessage("in correct password");
            }

        }
        else{
            responseMessage.setResponseMessage("invalid account number");
        }
        return  responseMessage;

    }

    private boolean isThisRequestValid(int accountNumber) {

        boolean isValidRequest = false;

        //if no token provided. return false
        String token = httpServletRequest.getHeader("Authorization");
        if (null == token || token.isEmpty()) {
            return isValidRequest;
        }

        // Decrypt cusomterId from token
        String customerIdFromToken = tokenService.getcustomteridfromtoken(token);

        //check if this customer ID exist or not? ID exist, fetch the customer details
        UserDetailsAggregate userDetailsAggregate = userRepoService.getUserDetailsByCustomerId(customerIdFromToken);

        if (null != userDetailsAggregate && 0 != userDetailsAggregate.getAccountNumber()) {
            // this is a valid account number
            if (accountNumber == Integer.valueOf(userDetailsAggregate.getAccountNumber())) {
                isValidRequest = true;
            }
        }
        return isValidRequest;
    }


}
