package es.edm.model;

import es.edm.exceptions.TurnException;

public class JSPSimpleTurn {
	public int uid;
	public int prayer_id;
	public String dow;
	public String turn;
	public String status;
	public String notes;
	public int pax;

	public JSPSimpleTurn(SimpleTurn turn) throws TurnException{
		this.uid = turn.getUid();
		this.prayer_id = turn.getPrayer_id();
		this.dow = turn.getDow().toString();
		//TODO: Substitute the turn String by and internationalized one
		this.turn = SimpleTurn.getHourByTurn(turn.getTurn());
		this.status = turn.getStatus().toString();
		this.notes = turn.getNotes();
		this.pax = turn.getPax();
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public int getPrayer_id() {
		return prayer_id;
	}

	public void setPrayer_id(int prayer_id) {
		this.prayer_id = prayer_id;
	}

	public String getDow() {
		return dow;
	}

	public void setDow(String dow) {
		this.dow = dow;
	}

	public String getTurn() {
		return turn;
	}

	public void setTurn(String turn) {
		this.turn = turn;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public int getPax() {
		return pax;
	}

	public void setPax(int pax) {
		this.pax = pax;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JSPSimpleTurn other = (JSPSimpleTurn) obj;
		if (uid != other.uid)
			return false;
		return true;
	}

}
