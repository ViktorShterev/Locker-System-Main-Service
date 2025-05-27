package my.projects.lockersystemusermicroservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import my.projects.lockersystemusermicroservice.validation.ExistingUserEmail;

public class ReceivePackageDTO {

    @NotBlank
    @Email
    @ExistingUserEmail
    private String recipientEmail;

    @NotBlank
    @Size(min = 6, max = 6)
    private String accessCode;

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public void setRecipientEmail(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }

    public String getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }
}
