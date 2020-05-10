package binson.banking.binsonbank.Service;

import binson.banking.binsonbank.RepoService.AccountRepoService;
import binson.banking.binsonbank.Request.CreateAccountRequest;
import binson.banking.binsonbank.Response.ResponseMessage;
import binson.banking.binsonbank.aggregate.AccountDetailsAggregate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    ResponseMessage responseMessage= new ResponseMessage();
    String message = "";


    @Autowired
    private AccountRepoService accountRepoService;

    public ResponseMessage accountSave(CreateAccountRequest createAccountRequest) {

        int result = accountRepoService.saveAccountDetails(createAccountRequest);
        if(result<1){
           responseMessage.setResponseMessage("this account does not exist ");
        }
        else
        {
            responseMessage.setResponseMessage("successfully updated");
        }
        return  responseMessage;

    }

    @Override
    public AccountDetailsAggregate getCustomerName(int accountNumber) {
        AccountDetailsAggregate accountDetailsAggregate = accountRepoService.getCustmerDetails(accountNumber);
       return  accountDetailsAggregate;
    }

    @Override
    public ResponseMessage updateUserName(CreateAccountRequest createAccountRequest) {
        int accId = createAccountRequest.getAccountNumber();
        String updatedName = createAccountRequest.getFname();
        int deletedResult= accountRepoService.updateUserName(accId, updatedName);
        if(deletedResult<1){
            responseMessage.setResponseMessage("this account does not exist ");
        }
        else
        {
            responseMessage.setResponseMessage("successfully updated");
        }
        return  responseMessage;

    }

    @Override
    public ResponseMessage deleteUserData(CreateAccountRequest createAccountRequest) {
        int accntNumber = createAccountRequest.getAccountNumber();
       int deletedResult= accountRepoService.deleteUserData(accntNumber);

        if(deletedResult<1){
            responseMessage.setResponseMessage("this account does not exist ");
        }
        else
        {
            responseMessage.setResponseMessage("successfully updated");
        }
        return  responseMessage;
    }

    @Override
    public List<AccountDetailsAggregate> getAccountsList() {

        return accountRepoService.getAccountsList();

    }

    @Override
    public List<AccountDetailsAggregate> getDeletedAccountList() {
        return  accountRepoService.getDeletedAccountList();
    }
}
