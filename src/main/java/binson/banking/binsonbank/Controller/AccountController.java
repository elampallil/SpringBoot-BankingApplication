package binson.banking.binsonbank.Controller;

import binson.banking.binsonbank.Request.CreateAccountRequest;
import binson.banking.binsonbank.Response.ResponseMessage;
import binson.banking.binsonbank.Service.AccountService;
import binson.banking.binsonbank.aggregate.AccountDetailsAggregate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/account/data", method = RequestMethod.POST)
    public ResponseMessage saveAccountDetails(@RequestBody CreateAccountRequest createAccountRequest) {
        ResponseMessage responseMessage = accountService.accountSave(createAccountRequest);
        return responseMessage;

    }

    @RequestMapping(value = "/account/data/name/{accountNumber}", method = RequestMethod.GET)
    public AccountDetailsAggregate getCustmerName(@PathVariable int accountNumber) {

        return accountService.getCustomerName(accountNumber);

    }

    @RequestMapping(value = "/account/data/update/name", method = RequestMethod.PUT)
    public ResponseMessage updatCustomerName(@RequestBody CreateAccountRequest createAccountRequest) {
        ResponseMessage responseMessage = accountService.updateUserName(createAccountRequest);
        return responseMessage;
    }

    @RequestMapping(value = "/account/data/delete", method = RequestMethod.PUT)
    public ResponseMessage deleteUserData(@RequestBody CreateAccountRequest createAccountRequest) {
        ResponseMessage responseMessage = accountService.deleteUserData(createAccountRequest);
        return responseMessage;

    }

    @RequestMapping(value = "/account/list", method = RequestMethod.GET)
    public List<AccountDetailsAggregate> getAccountsList() {
        return accountService.getAccountsList();
    }


    @RequestMapping(value = "/account/deleted/list",method = RequestMethod.GET)
    public List<AccountDetailsAggregate> getDeletedAccountList(){
        return  accountService.getDeletedAccountList();
    }

}

