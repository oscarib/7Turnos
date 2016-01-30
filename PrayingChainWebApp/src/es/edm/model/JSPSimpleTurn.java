package es.edm.model;

import es.edm.exceptions.TurnException;

public class JSPSimpleTurn {
	public String uid;
	public String prayer_id;
	public String dow;
	public String turn;
	//For managing turns by its number counterpart
	public int turnInt;
	public String status;
	public String notes;
	public boolean firstCall;
	public String pax;

	public JSPSimpleTurn(SimpleTurn turn) throws TurnException{
		this.uid = Integer.toString(turn.getUid());
		this.prayer_id = Integer.toString(turn.getPrayer_id());
		this.dow = turn.getDow().toString();
		//TODO: Substitute the turn String by and internationalized one
		this.turn = SimpleTurn.getHourByTurn(turn.getTurn());
		this.turnInt = turn.getTurn();
		this.status = turn.getStatus().toString();
		this.notes = turn.getNotes();
		this.pax = Integer.toString(turn.getPax());
		this.firstCall = true;
	}
	
	public int getTurnInt() {
		return turnInt;
	}

	public boolean isFirstCall() {
		return firstCall;
	}

	public void setFirstCall(boolean firstCall) {
		this.firstCall = firstCall;
	}

	public JSPSimpleTurn(){
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getPrayer_id() {
		return prayer_id;
	}

	public void setPrayer_id(String prayer_id) {
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
		if (!turn.equals("") && !turn.equals("NotSelected")){
			this.turnInt = Integer.parseInt(turn);
		}
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

	public String getPax() {
		return pax;
	}

	public void setPax(String pax) {
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
		if (!uid.equals(other.getUid()))
			return false;
		return true;
	}

}
