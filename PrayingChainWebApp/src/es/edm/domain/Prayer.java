package es.edm.domain;

import java.util.Date;

public class Prayer {

	private int uid;
	private String name;
	private String email;
	private String phone;
	private boolean ownCountry;
	private Date optinDate;
	private String notes;
	private boolean hidden;
	private String pseudonym;
	
	/*Added for Spring Form handling compatibility
	 *TODO: Add a security Layer to avoid the app to use this empty constructor where it should be
	 * filled in with correct values, since before Spring hadling usage, there were not needed
	 * a blank constructor
	 */
	public Prayer(){
	}
	
	public Prayer(int uid, String name, String email, String phoneNumber, boolean ownCountry, Date optinDate, String notes, boolean hidden, String pseudonym) {
		super();
		this.uid = uid;
		this.name = name;
		this.email = email;
		this.phone = phoneNumber;
		this.ownCountry = ownCountry;
		this.optinDate = optinDate;
		this.notes = notes;
		this.hidden = hidden;
		this.pseudonym = pseudonym;
	}

	//Gets
	public int getUid() {
		return uid;
	}

	public boolean isHidden() {
		return hidden;
	}

	public String getPseudonym() {
		return pseudonym;
	}

	public boolean isOwnCountry() {
		return ownCountry;
	}

	public String getNotes() {
		return notes;
	}

	public Date getOptinDate() {
		return optinDate;
	}

	public String getName() {
		return name;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getPhoneNumber() {
		return phone;
	}

	public String getPhone() {
		return phone;
	}

	//Sets
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public void setPseudonym(String pseudonym) {
		this.pseudonym = pseudonym;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setOwnCountry(boolean ownCountry) {
		this.ownCountry = ownCountry;
	}

	public void setOptinDate(Date optinDate) {
		this.optinDate = optinDate;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	public boolean isEmpty(){
		if (name==null){
			return true;
		} else return false;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + uid;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Prayer other = (Prayer) obj;
		if (uid != other.uid)
			return false;
		return true;
	}

	@Override
	public String toString(){
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Prayer: ");
		stringBuilder.append(name);
		stringBuilder.append(". ");
		stringBuilder.append("eMail: ");
		stringBuilder.append(email);
		stringBuilder.append(". ");
		stringBuilder.append("Phone Number: ");
		stringBuilder.append(phone);
		stringBuilder.append(". ");
		return stringBuilder.toString();
	}

}
