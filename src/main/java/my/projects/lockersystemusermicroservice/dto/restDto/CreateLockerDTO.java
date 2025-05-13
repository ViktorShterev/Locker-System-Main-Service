package my.projects.lockersystemusermicroservice.dto.restDto;

import jakarta.validation.constraints.*;

public class CreateLockerDTO {

    @NotBlank(message = "")
    @Size(min = 4, max = 60, message = "Location name must be between 4 and 60 symbols!")
    private String location;

    @NotNull(message = "Units size should be between 4 and 30!")
    @Min(value = 4, message = "")
    @Max(value = 30, message = "")
    private int smallUnits;

    @NotNull(message = "Units size should be between 4 and 30!")
    @Min(value = 4, message = "")
    @Max(value = 30, message = "")
    private int mediumUnits;

    @NotNull(message = "Units size should be between 4 and 30!")
    @Min(value = 4, message = "")
    @Max(value = 30, message = "")
    private int largeUnits;

    public CreateLockerDTO() {
    }

    public CreateLockerDTO(String location, int smallUnits, int mediumUnits, int largeUnits) {
        this.location = location;
        this.smallUnits = smallUnits;
        this.mediumUnits = mediumUnits;
        this.largeUnits = largeUnits;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getSmallUnits() {
        return smallUnits;
    }

    public void setSmallUnits(int smallUnits) {
        this.smallUnits = smallUnits;
    }

    public int getMediumUnits() {
        return mediumUnits;
    }

    public void setMediumUnits(int mediumUnits) {
        this.mediumUnits = mediumUnits;
    }

    public int getLargeUnits() {
        return largeUnits;
    }

    public void setLargeUnits(int largeUnits) {
        this.largeUnits = largeUnits;
    }
}
