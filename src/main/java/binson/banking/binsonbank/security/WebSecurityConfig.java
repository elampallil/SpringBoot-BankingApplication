/*
package binson.banking.binsonbank.security;

public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        */
/* TODO : Permission given only for dev purposes*//*

        http.cors().and()
                // we don't need CSRF and cors hopefully
                .csrf().disable()

                // don't create session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

                .authorizeRequests()

                // Un-secure urls
                .antMatchers("/v1/**").permitAll()
                .antMatchers("/v2/api-docs/**", "/swagger-resources/configuration/ui", "/swagger-resources",
                        "/swagger-resources/configuration/security", "/swagger-ui.html", "/swagger.json", "/webjars/**").permitAll()
                .anyRequest().authenticated();

    }
}*/
