package my.projects.lockersystemusermicroservice.dto.restDto;

public class PackageDTO {

    private String location;
    private String description;
    private String accessCode;
    private Long lockerUnitId;
    private String packageSize;
    private String packageStatus;
    private String pickedUpAt;

    public PackageDTO() {
    }

    public PackageDTO(String location, String description, String accessCode, Long lockerUnitId, String packageSize, String packageStatus, String pickedUpAt) {
        this.location = location;
        this.description = description;
        this.accessCode = accessCode;
        this.lockerUnitId = lockerUnitId;
        this.packageSize = packageSize;
        this.packageStatus = packageStatus;
        this.pickedUpAt = pickedUpAt;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }

    public Long getLockerUnitId() {
        return lockerUnitId;
    }

    public void setLockerUnitId(Long lockerUnitId) {
        this.lockerUnitId = lockerUnitId;
    }

    public String getPackageSize() {
        return packageSize;
    }

    public void setPackageSize(String packageSize) {
        this.packageSize = packageSize;
    }

    public String getPackageStatus() {
        return packageStatus;
    }

    public void setPackageStatus(String packageStatus) {
        this.packageStatus = packageStatus;
    }

    public String getPickedUpAt() {
        return pickedUpAt;
    }

    public void setPickedUpAt(String pickedUpAt) {
        this.pickedUpAt = pickedUpAt;
    }
}
