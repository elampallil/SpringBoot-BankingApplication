package binson.banking.binsonbank.Service;

import binson.banking.binsonbank.Request.CreateAccountRequest;
import binson.banking.binsonbank.Response.ResponseMessage;
import binson.banking.binsonbank.aggregate.AccountDetailsAggregate;

import java.util.List;

public interface AccountService {
    public ResponseMessage accountSave(CreateAccountRequest createAccountRequest);
    public AccountDetailsAggregate getCustomerName(int accountNumber);
    public ResponseMessage updateUserName(CreateAccountRequest createAccountRequest);
    public ResponseMessage deleteUserData(CreateAccountRequest createAccountRequest);
    public List<AccountDetailsAggregate> getAccountsList();
    public List<AccountDetailsAggregate> getDeletedAccountList();
}
