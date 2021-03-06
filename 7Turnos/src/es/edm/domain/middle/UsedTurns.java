package es.edm.domain.middle;

import es.edm.util.DayOfWeek;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "usedTurns")
public class UsedTurns implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1113036870663039268L;

    @Column
    int countOfTurns;

    @Id
    @Column
    @Enumerated(EnumType.STRING)
    DayOfWeek day;

    @Id
    @Column
    String turn;

    @Id
    @Column
    Integer chain;

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

    public Integer getChain() {
        return chain;
    }

    public void setChain(Integer chain) {
        this.chain = chain;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UsedTurns other = (UsedTurns) obj;
        if (day != other.day)
            return false;
        if (turn == null) {
            if (other.turn != null)
                return false;
        } else if (!turn.equals(other.turn))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((day == null) ? 0 : day.hashCode());
        result = prime * result + ((turn == null) ? 0 : turn.hashCode());
        return result;
    }
}
