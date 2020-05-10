package binson.banking.binsonbank.RepoService;

import binson.banking.binsonbank.Request.CreateAccountRequest;
import binson.banking.binsonbank.aggregate.AccountDetailsAggregate;
import binson.banking.binsonbank.factory.AccountRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@PropertySource(value = {"classpath:query.properties"})
public class AccountRepoServiceImpl implements AccountRepoService {

    @Value("${save.bank.details}")
    public String saveBankDetails;

    @Value("${customer.get.user.name}")
    public String getUsername;

    @Value("${customer.name.updated}")
    public String updatedName;

    @Value(("${customer.data.deleted}"))
    public String deletedData;

    @Value(("${account.list}"))
    public String accountList;

    @Value("${get.deleted.list}")
    public String deletedList;


    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public int saveAccountDetails(CreateAccountRequest createAccountRequest) {


        //String query = "INSERT INTO bankdb.account_details VALUES(:accountID,:fname,:lastname,:balance,:isdeleted)";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("accountID", createAccountRequest.getAccountNumber());
        mapSqlParameterSource.addValue("fname", createAccountRequest.getFname());
        mapSqlParameterSource.addValue("lastname", createAccountRequest.getLastName());
        mapSqlParameterSource.addValue("balance", createAccountRequest.getAccountBalance());
        mapSqlParameterSource.addValue("isdeleted", "N");

        int result = jdbcTemplate.update(this.saveBankDetails, mapSqlParameterSource);
        return result;

    }

    @Override
    public AccountDetailsAggregate getCustmerDetails(int accountNumber) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("accountNumber", accountNumber);

        AccountDetailsAggregate accountDetailsAggregate = jdbcTemplate.queryForObject(this.getUsername,
                mapSqlParameterSource, AccountRowMapper.FETCH_ACCOUNT_DETAILS_MAPPER);
        return accountDetailsAggregate;
    }

    @Override
    public int updateUserName(int accId, String name) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("accountNUmber", accId);
        mapSqlParameterSource.addValue("UpdatedName", name);
        int columCount = jdbcTemplate.update(this.updatedName, mapSqlParameterSource);
        return columCount;

    }

    @Override
    public int deleteUserData(int accountNumber) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("accountNumber", accountNumber);
        int columCount = jdbcTemplate.update(this.deletedData, mapSqlParameterSource);
        return columCount;

    }

    @Override
    public List<AccountDetailsAggregate> getAccountsList() {

        List<AccountDetailsAggregate> accountDetailsAggregates = jdbcTemplate.query(this.accountList,
                AccountRowMapper.FETCH_ACCOUNT_DETAILS_MAPPER);
        return accountDetailsAggregates;
    }

    @Override
    public List<AccountDetailsAggregate> getDeletedAccountList() {
        List<AccountDetailsAggregate> accountDetailsAggregates = jdbcTemplate.query(this.deletedList,
                AccountRowMapper.FETCH_ACCOUNT_DETAILS_MAPPER);
        return accountDetailsAggregates;
    }


}


