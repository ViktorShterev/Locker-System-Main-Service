package my.projects.lockersystemusermicroservice.dto.restDto;

public class LockerLocationDTO {

    private String location;

    public LockerLocationDTO(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
