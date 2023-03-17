package com.klub.entrypoint.api.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Configuration
@EnableWebSecurity
@SuppressWarnings("deprecated")
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    //private final UserAuthenticationServiceInterface userAccountService;
    private final BCryptPasswordEncoder passwordEncoder;

    //private final JwtHandlerInterface jwtHandler;
    //private final JwtTokenHelperInterface jwtTokenHelper;

    @Autowired
    public SecurityConfiguration(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    /*@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userAccountService).passwordEncoder(passwordEncoder);
    }*/

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*IdentifierPasswordAuthFilter identifierPasswordFilter = new IdentifierPasswordAuthFilter(
                authenticationManagerBean(), jwtHandler,
                jwtTokenHelper);
        identifierPasswordFilter.setFilterProcessesUrl(AUTH_LOGIN_URL);
        */

        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers(AUTH_LOGIN_URL, AUTH_REGISTER_URL, AUTH_REFRESH_TOKEN_URL).permitAll();

        http.authorizeRequests().antMatchers(
                "/api/**", "/api_test/**")
                .permitAll();

        /*http.authorizeRequests().antMatchers(
                        "/api_keys",
                        "/api_keys/**") //TODO this points is reserved for root user
                .hasAuthority(UserRoleEnum.ROLE_USER.getLabel());

        http.authorizeRequests().antMatchers(
                "/accounts",
                "/accounts/**")
                .hasAuthority(UserRoleEnum.ROLE_USER.getLabel());
         */

        http.authorizeRequests().anyRequest().authenticated();
        //http.addFilter(identifierPasswordFilter);
        //http.addFilterBefore(new JwtAuthorizationFilter(jwtHandler), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManager();
    }

    public static final String AUTH_LOGIN_URL = "/auth/login";
    public static final String AUTH_REGISTER_URL = "/auth/register";
    public static final String AUTH_REFRESH_TOKEN_URL = "/auth/refresh_token";

    public static final List<String> AUTH_URLS = List.of(AUTH_LOGIN_URL, AUTH_REGISTER_URL, AUTH_REFRESH_TOKEN_URL);
}
