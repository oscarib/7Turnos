package es.edm.domain;

public class JSPPrayer {

    private String uid;
    private String name;
    private String email;
    private String phone;
    private String ownCountry;
    private String optinDate;
    private String notes;
    private String hidden;
    private String pseudonym;
    private boolean firstCall;
    private String originalEmail;

    public String getOriginalEmail() {
        return originalEmail;
    }

    public void setOriginalEmail(String originalEmail) {
        this.originalEmail = originalEmail;
    }

    public boolean isFirstCall() {
        return firstCall;
    }

    public void setFirstCall(boolean firstCall) {
        this.firstCall = firstCall;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOwnCountry() {
        return ownCountry;
    }

    public void setOwnCountry(String ownCountry) {
        this.ownCountry = ownCountry;
    }

    public String getOptinDate() {
        return optinDate;
    }

    public void setOptinDate(String optinDate) {
        this.optinDate = optinDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getHidden() {
        return hidden;
    }

    public void setHidden(String hidden) {
        this.hidden = hidden;
    }

    public String getPseudonym() {
        return pseudonym;
    }

    public void setPseudonym(String pseudonym) {
        this.pseudonym = pseudonym;
    }
}
