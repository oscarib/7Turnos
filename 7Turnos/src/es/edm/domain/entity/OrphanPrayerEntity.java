package es.edm.domain.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "detailedorphanprayers_prayers")
public class OrphanPrayerEntity {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer uid;

    @Column
    private boolean erased;

    @Column
    private String name;

    @Column
    private Integer chain;

    @Column
    private String email;

    @Column
    private String phone;

    @Column(name = "own_country")
    private Boolean ownCountry;

    @Column(name = "optin_date")
    private Date optinDate;

    @Column(name = "Notes")
    private String notes;

    @Column
    private Boolean hidden;

    @Column
    private String pseudonym;

    /**
     * @return the uid
     */
    public Integer getUid() {
        return uid;
    }

    /**
     * @param uid the uid to set
     */
    public void setUid(Integer uid) {
        this.uid = uid;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the ownCountry
     */
    public Boolean getOwnCountry() {
        return ownCountry;
    }

    /**
     * @param ownCountry the ownCountry to set
     */
    public void setOwnCountry(Boolean ownCountry) {
        this.ownCountry = ownCountry;
    }

    /**
     * @return the optinDate
     */
    public Date getOptinDate() {
        return optinDate;
    }

    /**
     * @param optinDate the optinDate to set
     */
    public void setOptinDate(Date optinDate) {
        this.optinDate = optinDate;
    }

    /**
     * @return the notes
     */
    public String getNotes() {
        return notes;
    }

    /**
     * @param notes the notes to set
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * @return the hidden
     */
    public Boolean getHidden() {
        return hidden;
    }

    /**
     * @param hidden the hidden to set
     */
    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    /**
     * @return the pseudonym
     */
    public String getPseudonym() {
        return pseudonym;
    }

    /**
     * @param pseudonym the pseudonym to set
     */
    public void setPseudonym(String pseudonym) {
        this.pseudonym = pseudonym;
    }

    public boolean isErased() {
        return erased;
    }

    public void setErased(boolean erased) {
        this.erased = erased;
    }

    public Integer getChain() {
        return chain;
    }

    public void setChain(Integer chain) {
        this.chain = chain;
    }
}
