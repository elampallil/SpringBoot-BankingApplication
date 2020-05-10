package binson.banking.binsonbank.RepoService;

import binson.banking.binsonbank.Request.CreateUserRequest;
import binson.banking.binsonbank.aggregate.UserDetailsAggregate;
import binson.banking.binsonbank.factory.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@PropertySource(value = {"classpath:query.properties"})
public class UserRepoServiceImpl implements UserRepoService {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Value("${save.user.details}")
    public String userDataSave;

    @Value(("${get.user.details}"))
    public String getUSerDetails;

    @Value("${set.password}")
    public String updatedPswd;

    @Value(("${get.user.details.custometId}"))
    public String userDetailsByCustId;

    @Override
    public int userDataSave(CreateUserRequest createUserRequest, String token, String customer_id) {

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("Name", createUserRequest.getName());
        mapSqlParameterSource.addValue("Email", createUserRequest.getEmail());
        mapSqlParameterSource.addValue("PhoneNumber", createUserRequest.getPhoneNumber());
        mapSqlParameterSource.addValue("AccountId", createUserRequest.getAccountNumber());
        mapSqlParameterSource.addValue("Country", createUserRequest.getCountry());
        mapSqlParameterSource.addValue("Password", createUserRequest.getPassword());
        mapSqlParameterSource.addValue("token", token);
        mapSqlParameterSource.addValue("customer_id", customer_id);

        System.out.println(createUserRequest.getEmail());
        int rowCount = jdbcTemplate.update(this.userDataSave, mapSqlParameterSource);
        return rowCount;


    }

    @Override
    public UserDetailsAggregate getUserDetails(int accountNumber) {

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("accountNumber", accountNumber);
        UserDetailsAggregate userDetailsAggregate = new UserDetailsAggregate();
        try{
             userDetailsAggregate = jdbcTemplate.queryForObject
                    (this.getUSerDetails, mapSqlParameterSource, UserRowMapper.FETCH_USER_DETAILS_MAPPER);
        } catch(EmptyResultDataAccessException e){
            userDetailsAggregate = null;
        }

        return userDetailsAggregate;

    }

    @Override
    public int changePswd(int accountNumber, String password) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("accountNumber", accountNumber);
        mapSqlParameterSource.addValue("password", password);
        int rowCount = jdbcTemplate.update(this.updatedPswd, mapSqlParameterSource);
        return rowCount;
    }

    @Override
    public UserDetailsAggregate getUserDetailsByCustomerId(String custId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("custemerId", custId);
        UserDetailsAggregate userDetailsAggregate = new UserDetailsAggregate();

        try{
             userDetailsAggregate=   jdbcTemplate.queryForObject
                    (this.userDetailsByCustId, mapSqlParameterSource, UserRowMapper.FETCH_USER_DETAILS_MAPPER);
        } catch(EmptyResultDataAccessException e){
            userDetailsAggregate = null;
        }

     return  userDetailsAggregate;
    }


}
