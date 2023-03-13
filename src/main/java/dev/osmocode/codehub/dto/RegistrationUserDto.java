package dev.osmocode.codehub.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public class RegistrationUserDto {

    @Size(min = 8, max = 20, message = "Username should have between 8 and 20 characters")
    private String username;
    @Size(min = 8, max = 20, message = "Password should have between 8 and 20 characters")
    private String password;
    @Size(min = 8, max = 20, message = "Password should have between 8 and 20 characters")
    private String confirmation;
    @Email(message = "Wrong email format")
    private String email;

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

    public String getConfirmation() {
        return confirmation;
    }

    public void setConfirmation(String confirmation) {
        this.confirmation = confirmation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
