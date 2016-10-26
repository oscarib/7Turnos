package es.edm.domain.middle;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import es.edm.util.DayOfWeek;

@Entity
@Table(name="usedTurns")
public class UsedTurns {

	@Id
	@Column
	@Enumerated(EnumType.STRING)
	DayOfWeek day;

	@Column
	int countOfTurns;

	@Column
	String turn;
	
	public int getCountOfTurns() {
		return countOfTurns;
	}
	public void setCountOfTurns(int countOfTurns) {
		this.countOfTurns = countOfTurns;
	}
	public DayOfWeek getDay() {
		return day;
	}
	public void setDay(DayOfWeek day) {
		this.day = day;
	}
	public String getTurn() {
		return turn;
	}
	public void setTurn(String turn) {
		this.turn = turn;
	}
	
	
}
