package io.javabrains.springsecurityldap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeResource {

    // http://localhost:8080/
    // Use Ben's password to try to login the server that you just created. Username: ben, Password: benspassword
    // If password is wrong, The server will return: ConnectionRefusedException.
    @GetMapping("/")
    public String index() {
        return "Home Page";
    }

}
