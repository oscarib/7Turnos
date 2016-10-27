package es.edm.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import es.edm.util.DayOfWeek;
import es.edm.util.TurnStatus;

@Entity
@Table(name="edm_turns")
public class TurnEntity {

	@Id
	@Column
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer uid;
	
	@Column(name="day")
	@Enumerated(EnumType.STRING)
	private DayOfWeek dow;
	
	@Column(name="hour")
	private String turn;
	
	@Enumerated(EnumType.STRING)
	private TurnStatus status;
	
	@Column
	private String notes;
	
	@Column
	private Integer pax;
	
	@ManyToOne
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "prayer_id")
	@JsonManagedReference //Para evitar que la serializaci�n a JSON entre en un bucle infinito
	private PrayerEntity prayer;

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
	 * @return the dow
	 */
	public DayOfWeek getDow() {
		return dow;
	}

	/**
	 * @param dow the dow to set
	 */
	public void setDow(DayOfWeek dow) {
		this.dow = dow;
	}

	/**
	 * @return the turn
	 */
	public String getTurn() {
		return turn;
	}

	/**
	 * @param turn the turn to set
	 */
	public void setTurn(String turn) {
		this.turn = turn;
	}

	/**
	 * @return the status
	 */
	public TurnStatus getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(TurnStatus status) {
		this.status = status;
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
	 * @return the pax
	 */
	public Integer getPax() {
		return pax;
	}

	/**
	 * @param pax the pax to set
	 */
	public void setPax(Integer pax) {
		this.pax = pax;
	}

	/**
	 * @return the prayer
	 */
	public PrayerEntity getPrayer() {
		return prayer;
	}

	/**
	 * @param prayer the prayer to set
	 */
	public void setPrayer(PrayerEntity prayer) {
		this.prayer = prayer;
	}

}
