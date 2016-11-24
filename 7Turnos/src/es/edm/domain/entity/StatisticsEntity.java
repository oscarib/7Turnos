package es.edm.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class StatisticsEntity {

	@Id
	@Column
	Integer chain;
	
	@Column
	Integer committedPrayers;
	
	@Column
	Integer emptyTurns;
	
	@Column
	Integer orphanPrayers;
	
	@Column(name = "sumOfusedTurns")
	Integer usedTurns;
	
	@Column
	Integer totalTurns;

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
}
