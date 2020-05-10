package binson.banking.binsonbank.factory;

import binson.banking.binsonbank.aggregate.AccountDetailsAggregate;
import org.springframework.jdbc.core.RowMapper;

public class AccountRowMapper {

    public static RowMapper<AccountDetailsAggregate> FETCH_ACCOUNT_DETAILS_MAPPER = (res, error) -> {
        AccountDetailsAggregate accountDetailsAggregate = new AccountDetailsAggregate();
        accountDetailsAggregate.setAccountId(res.getInt("account_number"));
        accountDetailsAggregate.setFirstName(res.getString("fName"));
        accountDetailsAggregate.setLastName(res.getString("lastName"));
        accountDetailsAggregate.setDeleted(res.getString("isDeleted").equals("Y"));
        accountDetailsAggregate.setAccountBalance(res.getInt("accntBalance"));

        return accountDetailsAggregate;
    };
}
