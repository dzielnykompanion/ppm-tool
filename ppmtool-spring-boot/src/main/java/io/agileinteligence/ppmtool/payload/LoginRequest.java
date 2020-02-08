package io.agileinteligence.ppmtool.payload;

import javax.validation.constraints.NotBlank;

// becouse when client send to server JSON with username and password. exactly type of request what we will receive
public class LoginRequest {

    @NotBlank(message = "Username cannot be blank")
    private String username; // same as in User.java

    @NotBlank(message = "Password cannot be blank")
    private String password;

    // -----------------
    // getters & setters
    // -----------------
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
