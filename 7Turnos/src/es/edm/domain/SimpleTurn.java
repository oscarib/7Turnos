package es.edm.domain;

import es.edm.exceptions.DayOfWeekException;
import es.edm.exceptions.TurnException;
import es.edm.util.DayOfWeek;
import es.edm.util.TurnStatus;

public class SimpleTurn {

    int uid;
    int prayer_id;
    DayOfWeek dow;
    int turn;
    TurnStatus status;
    String notes;
    int pax;

    /*Added for Spring Form handling compatibility
     *TODO: Add a security Layer to avoid the app to use this empty constructor where it should be
     * filled in with correct values, since before Spring hadling usage, there were not needed
     * a blank constructor
     */
    public SimpleTurn() {
    }

    public SimpleTurn(int uid, int prayer_id, DayOfWeek dow, int turn, TurnStatus status, String notes, int pax) {
        this.dow = dow;
        this.uid = uid;
        this.prayer_id = prayer_id;
        this.turn = turn;
        this.status = status;
        this.notes = notes;
        this.pax = pax;
    }

    public static TurnStatus getTurnStatus(String status) throws TurnException {
        switch (status) {
            case "received":
                return TurnStatus.received;
            case "accepted":
                return TurnStatus.accepted;
            case "rejected":
                return TurnStatus.rejected;
            case "cancelled":
                return TurnStatus.cancelled;
            case "NotCommitted":
                return TurnStatus.NotCommitted;
            default:
                throw new TurnException("Status provided does not match valid options (" + status + ")");
        }

    }

    public static DayOfWeek getDayOfWeek(String day) throws DayOfWeekException {
        switch (day) {
            case "Lunes":
                return DayOfWeek.monday;
            case "Martes":
                return DayOfWeek.tuesday;
            case "Miércoles":
                return DayOfWeek.wednesday;
            case "Jueves":
                return DayOfWeek.thursday;
            case "Viernes":
                return DayOfWeek.friday;
            case "Sábado":
                return DayOfWeek.saturday;
            case "Domingo":
                return DayOfWeek.sunday;
            default:
                throw new DayOfWeekException("provided day is not valid (" + day + ")");
        }
    }

    public static int getTurnByHour(String hour) throws TurnException {
        switch (hour) {
            case "00:00am":
                return 0;
            case "00:30am":
                return 1;
            case "01:00am":
                return 2;
            case "01:30am":
                return 3;
            case "02:00am":
                return 4;
            case "02:30am":
                return 5;
            case "03:00am":
                return 6;
            case "03:30am":
                return 7;
            case "04:00am":
                return 8;
            case "04:30am":
                return 9;
            case "05:00am":
                return 10;
            case "05:30am":
                return 11;
            case "06:00am":
                return 12;
            case "06:30am":
                return 13;
            case "07:00am":
                return 14;
            case "07:30am":
                return 15;
            case "08:00am":
                return 16;
            case "08:30am":
                return 17;
            case "09:00am":
                return 18;
            case "09:30am":
                return 19;
            case "10:00am":
                return 20;
            case "10:30am":
                return 21;
            case "11:00am":
                return 22;
            case "11:30am":
                return 23;
            case "12:00pm":
                return 24;
            case "12:30pm":
                return 25;
            case "13:00pm":
                return 26;
            case "13:30pm":
                return 27;
            case "14:00pm":
                return 28;
            case "14:30pm":
                return 29;
            case "15:00pm":
                return 30;
            case "15:30pm":
                return 31;
            case "16:00pm":
                return 32;
            case "16:30pm":
                return 33;
            case "17:00pm":
                return 34;
            case "17:30pm":
                return 35;
            case "18:00pm":
                return 36;
            case "18:30pm":
                return 37;
            case "19:00pm":
                return 38;
            case "19:30pm":
                return 39;
            case "20:00pm":
                return 40;
            case "20:30pm":
                return 41;
            case "21:00pm":
                return 42;
            case "21:30pm":
                return 43;
            case "22:00pm":
                return 44;
            case "22:30pm":
                return 45;
            case "23:00pm":
                return 46;
            case "23:30pm":
                return 47;
            default:
                throw new TurnException("provided hour is not valid (" + hour + ")");
        }
    }

    public static String getHourByTurn(int turn) throws TurnException {
        switch (turn) {
            case 0:
                return "00:00am";
            case 1:
                return "00:30am";
            case 2:
                return "01:00am";
            case 3:
                return "01:30am";
            case 4:
                return "02:00am";
            case 5:
                return "02:30am";
            case 6:
                return "03:00am";
            case 7:
                return "03:30am";
            case 8:
                return "04:00am";
            case 9:
                return "04:30am";
            case 10:
                return "05:00am";
            case 11:
                return "05:30am";
            case 12:
                return "06:00am";
            case 13:
                return "06:30am";
            case 14:
                return "07:00am";
            case 15:
                return "07:30am";
            case 16:
                return "08:00am";
            case 17:
                return "08:30am";
            case 18:
                return "09:00am";
            case 19:
                return "09:30am";
            case 20:
                return "10:00am";
            case 21:
                return "10:30am";
            case 22:
                return "11:00am";
            case 23:
                return "11:30am";
            case 24:
                return "12:00pm";
            case 25:
                return "12:30pm";
            case 26:
                return "13:00pm";
            case 27:
                return "13:30pm";
            case 28:
                return "14:00pm";
            case 29:
                return "14:30pm";
            case 30:
                return "15:00pm";
            case 31:
                return "15:30pm";
            case 32:
                return "16:00pm";
            case 33:
                return "16:30pm";
            case 34:
                return "17:00pm";
            case 35:
                return "17:30pm";
            case 36:
                return "18:00pm";
            case 37:
                return "18:30pm";
            case 38:
                return "19:00pm";
            case 39:
                return "19:30pm";
            case 40:
                return "20:00pm";
            case 41:
                return "20:30pm";
            case 42:
                return "21:00pm";
            case 43:
                return "21:30pm";
            case 44:
                return "22:00pm";
            case 45:
                return "22:30pm";
            case 46:
                return "23:00pm";
            case 47:
                return "23:30pm";
            default:
                throw new TurnException("provided turn is not valid (" + turn + ")");
        }
    }

    /**
     * @return the uid
     */
    public int getUid() {
        return uid;
    }

    /**
     * @param uid the uid to set
     */
    public void setUid(int uid) {
        this.uid = uid;
    }

    /**
     * @return the prayer_id
     */
    public int getPrayer_id() {
        return prayer_id;
    }

    /**
     * @param prayer_id the prayer_id to set
     */
    public void setPrayer_id(int prayer_id) {
        this.prayer_id = prayer_id;
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
     * @return the hour
     */
    public int getTurn() {
        return turn;
    }

    /**
     * @param hour the hour to set
     */
    public void setTurn(int turn) {
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
    public int getPax() {
        return pax;
    }

    /**
     * @param pax the pax to set
     */
    public void setPax(int pax) {
        this.pax = pax;
    }

    public boolean isEmpty() {
        if (dow == null || prayer_id <= 0 || turn <= 0 || status == null) {
            return true;
        } else return false;
    }
}
