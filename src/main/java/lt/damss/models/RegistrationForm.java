package lt.damss.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by paulius on 17.3.12.
 */
@Entity
public class RegistrationForm {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;
    private String lastName;
    private String educationalDegree;
    private String institution;
    private String email;
    private String phoneNumber;

    private String messageName;
    private String messageAuthorsAndAffiliations;
    private String messageSummary;

    private Long needsRoom;
    private String roomType;

    private Long hasEscort;
    private Long escortWillParticipateInEvents;

    private Long needsBill;
    private String billInstitution;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEducationalDegree() {
        return educationalDegree;
    }

    public void setEducationalDegree(String educationalDegree) {
        this.educationalDegree = educationalDegree;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMessageName() {
        return messageName;
    }

    public void setMessageName(String messageName) {
        this.messageName = messageName;
    }

    public String getMessageAuthorsAndAffiliations() {
        return messageAuthorsAndAffiliations;
    }

    public void setMessageAuthorsAndAffiliations(String messageAuthorsAndAffiliations) {
        this.messageAuthorsAndAffiliations = messageAuthorsAndAffiliations;
    }

    public String getMessageSummary() {
        return messageSummary;
    }

    public void setMessageSummary(String messageSummary) {
        this.messageSummary = messageSummary;
    }

    public Long getNeedsRoom() {
        return needsRoom;
    }

    public void setNeedsRoom(Long needsRoom) {
        this.needsRoom = needsRoom;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public Long getHasEscort() {
        return hasEscort;
    }

    public void setHasEscort(Long hasEscort) {
        this.hasEscort = hasEscort;
    }

    public Long getEscortWillParticipateInEvents() {
        return escortWillParticipateInEvents;
    }

    public void setEscortWillParticipateInEvents(Long escortWillParticipateInEvents) {
        this.escortWillParticipateInEvents = escortWillParticipateInEvents;
    }

    public Long getNeedsBill() {
        return needsBill;
    }

    public void setNeedsBill(Long needsBill) {
        this.needsBill = needsBill;
    }

    public String getBillInstitution() {
        return billInstitution;
    }

    public void setBillInstitution(String billInstitution) {
        this.billInstitution = billInstitution;
    }
}
