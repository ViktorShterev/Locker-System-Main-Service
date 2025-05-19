package my.projects.lockersystemusermicroservice.dto.kafka;

public class AccessCodeEventDTO {

    private String accessCode;
    private Long packageId;
    private String recipientEmail;
    private String location;

    public AccessCodeEventDTO() {
    }

    public AccessCodeEventDTO(String accessCode, Long packageId, String recipientEmail, String location) {
        this.accessCode = accessCode;
        this.packageId = packageId;
        this.recipientEmail = recipientEmail;
        this.location = location;
    }

    public String getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }

    public Long getPackageId() {
        return packageId;
    }

    public void setPackageId(Long packageId) {
        this.packageId = packageId;
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public void setRecipientEmail(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
