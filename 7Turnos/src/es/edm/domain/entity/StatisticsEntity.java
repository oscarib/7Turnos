package es.edm.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "statistics")
public class StatisticsEntity {

    @Id
    @Column
    Integer chain;

    @Column
    Integer committedPrayers;

    @Column
    Integer NonCommittedPrayers;

    @Column
    Integer usedTurns;

    @Column
    Integer availableTurns;

    @Column
    Integer emptyTurns;

    @Column
    Integer orphanPrayers;

    @Column
    Integer totalTurns;

    @Column
    Integer visiblePrayers;

    @Column
    Integer hiddenPrayers;

    @Column
    Integer foreignPrayers;

    @Column
    Integer localPrayers;

    @Column
    Integer totalPrayers;

    public Integer getChain() {
        return chain;
    }

    public void setChain(Integer chain) {
        this.chain = chain;
    }

    public Integer getCommittedPrayers() {
        return committedPrayers;
    }

    public void setCommittedPrayers(Integer committedPrayers) {
        this.committedPrayers = committedPrayers;
    }

    public Integer getEmptyTurns() {
        return emptyTurns;
    }

    public void setEmptyTurns(Integer emptyTurns) {
        this.emptyTurns = emptyTurns;
    }

    public Integer getOrphanPrayers() {
        return orphanPrayers;
    }

    public void setOrphanPrayers(Integer orphanPrayers) {
        this.orphanPrayers = orphanPrayers;
    }

    public Integer getUsedTurns() {
        return usedTurns;
    }

    public void setUsedTurns(Integer usedTurns) {
        this.usedTurns = usedTurns;
    }

    public Integer getTotalTurns() {
        return totalTurns;
    }

    public void setTotalTurns(Integer totalTurns) {
        this.totalTurns = totalTurns;
    }

    public Integer getNonCommittedPrayers() {
        return NonCommittedPrayers;
    }

    public void setNonCommittedPrayers(Integer nonCommittedPrayers) {
        NonCommittedPrayers = nonCommittedPrayers;
    }

    public Integer getAvailableTurns() {
        return availableTurns;
    }

    public void setAvailableTurns(Integer availableTurns) {
        this.availableTurns = availableTurns;
    }

    public Integer getVisiblePrayers() {
        return visiblePrayers;
    }

    public void setVisiblePrayers(Integer visiblePrayers) {
        this.visiblePrayers = visiblePrayers;
    }

    public Integer getHiddenPrayers() {
        return hiddenPrayers;
    }

    public void setHiddenPrayers(Integer hiddenPrayers) {
        this.hiddenPrayers = hiddenPrayers;
    }

    public Integer getForeignPrayers() {
        return foreignPrayers;
    }

    public void setForeignPrayers(Integer foreignPrayers) {
        this.foreignPrayers = foreignPrayers;
    }

    public Integer getLocalPrayers() {
        return localPrayers;
    }

    public void setLocalPrayers(Integer localPrayers) {
        this.localPrayers = localPrayers;
    }

    public Integer getTotalPrayers() {
        return totalPrayers;
    }

    public void setTotalPrayers(Integer totalPrayers) {
        this.totalPrayers = totalPrayers;
    }

}
