package com.qinkuai.homework.dbpractice.hw3.model;

public class Ticket {
	//票所有者
	private User owner;
	//票有效日期
	private String dueDate;
	//座位编号
	private int seatNumber;
	//出售状态
	private boolean status;
	
	public Ticket() {}
	
	public Ticket(User owner, String dueDate, int seatNumber){
		this.owner = owner;
		this.dueDate = dueDate;
		this.seatNumber = seatNumber;
	}
	
	public void setOwner(User owner) {
		this.owner = owner;
	}
	
	public User getOwner() {
		return owner;
	}
	
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	
	public String getDueDate() {
		return dueDate;
	}
	
	public void setSeatNumber(int seatNumber) {
		this.seatNumber = seatNumber;
	}
	
	public int getSeatNumber() {
		return seatNumber;
	}
	
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	public boolean getStatus() {
		return status;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dueDate == null) ? 0 : dueDate.hashCode());
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		result = prime * result + seatNumber;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Ticket other = (Ticket) obj;
		if (dueDate == null) {
			if (other.dueDate != null) {
				return false;
			}
		} else if (!dueDate.equals(other.dueDate)) {
			return false;
		}
		if (owner == null) {
			if (other.owner != null) {
				return false;
			}
		} else if (!owner.equals(other.owner)) {
			return false;
		}
		if (seatNumber != other.seatNumber) {
			return false;
		}
		return true;
	}
}
