package com.qinkuai.homework.dbpractice.hw3.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qinkuai.homework.dbpractice.hw3.model.Routine;
import com.qinkuai.homework.dbpractice.hw3.model.Ticket;
import com.qinkuai.homework.dbpractice.hw3.model.User;

public class TicketsSeller {
	//票对应线路信息
	//初始化数据应从数据库中获取
	private Routine routine;
	//票总数
	private int totalTicketsNumber;
	//已售出票数
	private int soldTicketsNumber;
	//所售票列表
	private List<Ticket> tickets;
	//随机售票所用列表
	private List<Integer> randomTicketsNumberList;
	
	public TicketsSeller(String routineNumber, String dueDate, int totalTicketsNumber) {
		if (routine == null) {
			try {
				routineInit(routineNumber);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		this.totalTicketsNumber = totalTicketsNumber;
		soldTicketsNumber = 0;
		this.tickets = new ArrayList<>();
		randomTicketsNumberList = new ArrayList<>();
		
		for (int i = 0; i < this.totalTicketsNumber; i++) {
			tickets.add(i, new Ticket(null, dueDate, i + 1));
		}
		
		int randomNumber = 0;
		//生成随机售票所用列表
		while(randomTicketsNumberList.size() != this.totalTicketsNumber) {
			Random rand = new Random(System.currentTimeMillis());
			randomNumber = Math.abs(rand.nextInt() % totalTicketsNumber);
			if (!randomTicketsNumberList.contains(randomNumber)) {
				randomTicketsNumberList.add(randomNumber);
			}
		}
		
	}
	
	//初始化代售票面对应的线路信息
	private void routineInit(String routineNumber) throws Exception{
		//从JSON文件加载
		//用JSON做了一个伪数据库
		File fakeDBFile = new File("res/fakeDB.json");
		StringBuffer jsonText = new StringBuffer();
		try {
			BufferedReader bufr = new BufferedReader(new InputStreamReader(new FileInputStream(fakeDBFile)));
			String line = bufr.readLine();
			while(line != null) {
				jsonText.append(line);
				line = bufr.readLine();
			}
			
			bufr.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		Routine tempRoutine = null;
		JSONObject jsonObject = JSON.parseObject(jsonText.toString());
		JSONArray jsonArray = jsonObject.getJSONArray("routines");
		for (int i = 0; i < jsonArray.size(); i++) {
			tempRoutine = jsonArray.getObject(i, Routine.class);
			if (tempRoutine.getRoutineNumber().equals(routineNumber)) {
				routine = tempRoutine;
			}
		}
		if (routine == null) {
			throw new Exception("There is not routineNumber: " + routineNumber);
		}
		
		//System.out.println(routine);
	}
	
	synchronized public Ticket sellTicket(User buyer) {
		if (soldTicketsNumber == totalTicketsNumber) {
			return null;
		}
		int soldSeatNumber = randomTicketsNumberList.get(soldTicketsNumber);
		
		Ticket ticket = tickets.get(soldSeatNumber);
		ticket.setOwner(buyer);
		tickets.get(soldTicketsNumber).setStatus(true);
		soldTicketsNumber++;
		
		return ticket;
	}
	
	synchronized public boolean hasTickets() {
		if (soldTicketsNumber == totalTicketsNumber) {
			return false;
		}
		return true;
	}
	
	public Routine getRoutine() {
		return routine;
	}
	
	synchronized public int getSoldTicketsNumber() {
		return soldTicketsNumber;
	}
	
	synchronized public int getLeftTicketsNumber() {
		return totalTicketsNumber - soldTicketsNumber;
	}
}
