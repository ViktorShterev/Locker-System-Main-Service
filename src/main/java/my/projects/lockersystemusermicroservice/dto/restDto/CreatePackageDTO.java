package my.projects.lockersystemusermicroservice.dto.restDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import my.projects.lockersystemusermicroservice.validation.ExistingUserEmail;

public class CreatePackageDTO {

    @NotBlank
    private String lockerLocation;

    @NotNull
    private String lockerSize;

    @Email
    @NotBlank
    @ExistingUserEmail
    private String recipientEmail;

    private String description;

    private String courierEmail;

    private Long lockerUnitId;

    public String getLockerLocation() {
        return lockerLocation;
    }

    public void setLockerLocation(String lockerLocation) {
        this.lockerLocation = lockerLocation;
    }

    public String getLockerSize() {
        return lockerSize;
    }

    public void setLockerSize(String lockerSize) {
        this.lockerSize = lockerSize;
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public void setRecipientEmail(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCourierEmail() {
        return courierEmail;
    }

    public void setCourierEmail(String courierEmail) {
        this.courierEmail = courierEmail;
    }

    public Long getLockerUnitId() {
        return lockerUnitId;
    }

    public CreatePackageDTO setLockerUnitId(Long lockerUnitId) {
        this.lockerUnitId = lockerUnitId;
        return this;
    }
}
