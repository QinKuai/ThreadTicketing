package com.qinkuai.homework.dbpractice.hw3.service;

import java.util.HashMap;
import java.util.Map;

public class SellerManager {
	//单实例
	private static SellerManager instance;
	//管理所有的买票器
	private Map<String, TicketsSeller> sellerPool;
	
	private SellerManager() {
		sellerPool = new HashMap<String, TicketsSeller>();
	}
	
	public static SellerManager getInstance() {
		if (instance == null) {
			instance = new SellerManager();
		}
		return instance;
	}
	
	synchronized public void addSeller(String routineNumber, String dueDate, int totalTicketsNumber) {
		if (!sellerPool.containsKey(routineNumber)) {
			sellerPool.put(routineNumber, new TicketsSeller(routineNumber, dueDate, totalTicketsNumber));
		}
	}
	
	public TicketsSeller geTicketsSeller(String routineNumber) {
		if (sellerPool.containsKey(routineNumber)) {
			return sellerPool.get(routineNumber);
		}
		return null;
	}
	
	synchronized public void deleteSeller(String routineNumber) {
		if (sellerPool.containsKey(routineNumber)) {
			sellerPool.remove(routineNumber);
		}
	}
	
	public boolean containsSeller(String routineNumber) {
		if (sellerPool.containsKey(routineNumber)) {
			return true;
		}
		return false;
	}
}
