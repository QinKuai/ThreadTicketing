package com.qinkuai.homework.dbpractice.hw3.model;

import java.math.BigDecimal;

import com.alibaba.fastjson.annotation.JSONField;

//列车线路模型
public class Routine {
	//起始站
	@JSONField
	private String from;
	//终点站
	@JSONField
	private String to;
	//列车号
	//核心主Key
	//唯一区分一辆车辆
	@JSONField
	private String routineNumber;
	//线路及车型共同决定的价格
	@JSONField
	private BigDecimal price;
	
	public Routine() {}
	
	public Routine(String from, String to, String routineNumber, BigDecimal price) {
		this.from = from;
		this.to = to;
		this.routineNumber = routineNumber;
		this.price = price;
	}
	
	public void setFrom(String from) {
		this.from = from;
	}
	
	public String getFrom() {
		return from;
	}
	
	public void setTo(String to) {
		this.to = to;
	}
	
	public String getTo() {
		return to;
	}
	
	public void setRoutineNumber(String routineNumber) {
		this.routineNumber = routineNumber;
	}
	
	public String getRoutineNumber() {
		return routineNumber;
	}
	
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	public BigDecimal getPrice() {
		return price;
	}
	
	@Override
	public boolean equals(Object obj) {
		Routine other = (Routine)obj;
		if (routineNumber == null) {
			if (other.routineNumber != null) {
				return false;
			}else {
				return true;
			}
		}
		return routineNumber.equals(other.routineNumber);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((routineNumber == null) ? 0 : routineNumber.hashCode());
		return result;
	}
	
	@Override
	public String toString() {
		return routineNumber + ": " + from + "->" + to + " price:" + price;
	}
	
}
