package binson.banking.binsonbank.RepoService;

import binson.banking.binsonbank.Request.CreateAccountRequest;
import binson.banking.binsonbank.aggregate.AccountDetailsAggregate;

import java.util.List;


public interface AccountRepoService {

    public int saveAccountDetails(CreateAccountRequest createAccountRequest);
    public AccountDetailsAggregate getCustmerDetails(int accountNumber);
    public int updateUserName(int accId, String name);
    public int deleteUserData(int accountNumber);
    public List<AccountDetailsAggregate> getAccountsList();
    public List<AccountDetailsAggregate> getDeletedAccountList();

}
