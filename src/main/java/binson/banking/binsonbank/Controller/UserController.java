package binson.banking.binsonbank.Controller;

import binson.banking.binsonbank.Request.CreateUserRequest;
import binson.banking.binsonbank.Request.EmailInfo;
import binson.banking.binsonbank.Response.ResponseBody;
import binson.banking.binsonbank.Response.ResponseMessage;
import binson.banking.binsonbank.Service.UserService;
import binson.banking.binsonbank.aggregate.UserDetailsAggregate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/user/save", method = RequestMethod.POST)
    public ResponseMessage userDataSave(@RequestBody CreateUserRequest createUserRequest) {
        return userService.userDataSave(createUserRequest);
    }

    @RequestMapping(value = "/user/details/{accountNumber}", method = RequestMethod.GET)
    public ResponseBody getUserDetails(@PathVariable int accountNumber) {
        UserDetailsAggregate userDetailsAggregate = userService.getUserDetails(accountNumber);
        ResponseBody responseBody = new ResponseBody();
        if (null != userDetailsAggregate) {
            responseBody.setStatusMessage("Success");
            responseBody.setData(userDetailsAggregate);
        } else {
            responseBody.setStatusMessage("Error ! invalid auth header");
            responseBody.setData(userDetailsAggregate);
        }
        return responseBody;
    }

    @RequestMapping(value = "/user/password/change", method = RequestMethod.PUT)
    public ResponseMessage changePswd(@RequestBody CreateUserRequest createUserRequest) {
        ResponseMessage responseMessage = userService.changePswd(createUserRequest);
        return responseMessage;

    }

    @RequestMapping(value = "/user/login", method = RequestMethod.GET)
    public ResponseMessage userLogin(@RequestBody CreateUserRequest createUserRequest) {
        ResponseMessage responseMessage = userService.userLogin(createUserRequest);
        return responseMessage;
    }

    @RequestMapping(value = "/email", method = RequestMethod.POST)
    public ResponseMessage sendEmail(@RequestBody EmailInfo emailInfo) throws IOException, MessagingException {
        ResponseMessage responseMessage = userService.sendEmail(emailInfo);
        return responseMessage;


    }
}
