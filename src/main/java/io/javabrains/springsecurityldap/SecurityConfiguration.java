package io.javabrains.springsecurityldap;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    // Step 1: Enable scope of authentication on all APIs
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().fullyAuthenticated()
                .and().formLogin();
    }

    // Step 2: Select authentication method
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.ldapAuthentication()
                .userDnPatterns("uid={0},ou=people") // Tells Spring Security the ways of how to find the users in test-server.ldif file. Find Ben Alex, and you'll see that uid is actually his username.
                .groupSearchBase("ou=groups")
                .contextSource()
                .url("ldap://localhost:8389/dc=springframework,dc=org") // Points where online LDAP server is. Matches wth the values of your application.properties file.
                .and()
                .passwordCompare()
//                .passwordEncoder(new LdapShaPasswordEncoder()) // Not recommended for production use. For testing is fine. (Commented due to using spring official website data.
                .passwordEncoder(new BCryptPasswordEncoder()) // They updated the file and used BCrypt as the solution to decode the password
                .passwordAttribute("userPassword"); // Tells the password of the user is something called "userPassword" in the .ldif file.

        // The .ldif file uses SHA (not SHA 256) to encrypt the password. This is actually not recommended, but because the code is copied from Spring website itself and it's just a tutorial.
        // Use Ben's password to try to login the server that you just created. Username: ben, Password: benspassword


        // Problem faced in this tutorial: You need to add implementation 'org.springframework.ldap:spring-ldap-core'
        // And implementation 'org.springframework.ldap:spring-ldap-core' (not testImplementation)
        // In order to make it work.
    }
}
