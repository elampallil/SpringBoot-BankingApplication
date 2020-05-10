package binson.banking.binsonbank.factory;

import binson.banking.binsonbank.aggregate.UserDetailsAggregate;
import org.springframework.jdbc.core.RowMapper;

public class UserRowMapper {
    public static RowMapper<UserDetailsAggregate> FETCH_USER_DETAILS_MAPPER = (res, error) ->{

            UserDetailsAggregate userDetailsAggregate = new UserDetailsAggregate();
            userDetailsAggregate.setCustomerId(res.getString("customer_id"));
            userDetailsAggregate.setName(res.getString("Name"));
            userDetailsAggregate.setEmail(res.getString("Email"));
            userDetailsAggregate.setPhoneNumber(res.getString("PhoneNumber"));
            userDetailsAggregate.setAccountNumber(res.getInt("AccountId"));
            userDetailsAggregate.setCountry(res.getString("Country"));
            userDetailsAggregate.setPassword(res.getString("Password"));
            return userDetailsAggregate;

    };
}
