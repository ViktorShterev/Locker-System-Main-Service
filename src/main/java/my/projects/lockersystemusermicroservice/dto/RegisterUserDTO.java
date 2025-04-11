package my.projects.lockersystemusermicroservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import my.projects.lockersystemusermicroservice.validation.UniqueUserEmail;

public class RegisterUserDTO {

    @NotEmpty(message = "Name must be between 2 and 60 characters!")
    @Size(min = 2, max = 60, message = "")
    private String fullName;

    @NotEmpty(message = "Email cannot be empty!")
    @Email
    @UniqueUserEmail
    private String email;

    @NotEmpty(message = "Password must be between 4 and 30 characters!")
    @Size(min = 4, max = 30, message = "")
    private String password;

    @NotNull(message = "Role cannot be empty!")
    private String role;

    public RegisterUserDTO(String fullName, String email, String password, String role) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String username) {
        this.fullName = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
