package com.qinkuai.homework.dbpractice.hw3.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

import javax.swing.JLabel;
import javax.swing.JTextArea;

import com.qinkuai.homework.dbpractice.hw3.model.Ticket;
import com.qinkuai.homework.dbpractice.hw3.model.User;

public class ThreadControler {
	//线程二
	private Thread thread;
	//线程一
	private Thread otherThread;
	//日志文件
	private File logFile = new File("log/sale.txt");
	//日志写入流
	private BufferedWriter bufw;
	//随机休眠器
	private Random rand = new Random(System.currentTimeMillis());
	
	public ThreadControler() {
		try {
			bufw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(logFile)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void start(String routineNumber, int totalTicketsNumber, JTextArea leftTextArea, JTextArea rightTextArea, JLabel leftTicketNumber) {
		SellerManager sellerManager = SellerManager.getInstance();
		if (sellerManager.containsSeller(routineNumber)) {
			sellerManager.deleteSeller(routineNumber);
		}
		
		leftTextArea.setText("");
		rightTextArea.setText("");
		
		CountDownLatch latch = new CountDownLatch(2);
		
		sellerManager.addSeller(routineNumber, "2019-11-11", totalTicketsNumber);
		otherThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				User user = new User("OtherThread", "anything");
				TicketsSeller seller = sellerManager.geTicketsSeller(routineNumber);
				String line = "";
				while(seller.hasTickets()) {
					int randomNumber = rand.nextInt(400) + 100;
					Ticket ticket = seller.sellTicket(user);
					line = ticket.getOwner().getUsername() + " buys " + seller.getRoutine().toString() 
							+ " SeatNumber：" + ticket.getSeatNumber();
					rightTextArea.append(line + "\n");
					leftTicketNumber.setText(seller.getLeftTicketsNumber() + "");
					synchronized (bufw) {
						try {
							bufw.write(String.format("No.2 window has sold a ticket: %03d\n", ticket.getSeatNumber()));
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					
//					System.out.println(Thread.currentThread().getName() + randomNumber);
					try {
						Thread.sleep(randomNumber);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				latch.countDown();
			}
		});
		thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				User user = new User("MainThread", "anything");
				TicketsSeller seller = sellerManager.geTicketsSeller(routineNumber);
				String line = "";
				while(seller.hasTickets()) {
					int randomNumber = rand.nextInt(400) + 100;
					Ticket ticket = seller.sellTicket(user);
					line = ticket.getOwner().getUsername() + " buys " + seller.getRoutine().toString() 
							+ "   SeatNumber：" + ticket.getSeatNumber();
					leftTextArea.append(line + "\n");
					leftTicketNumber.setText(seller.getLeftTicketsNumber() + ""); 
					synchronized (bufw) {
						try {
							bufw.write(String.format("No.1 window has sold a ticket: %03d\n", ticket.getSeatNumber()));
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
//					System.out.println(Thread.currentThread().getName() + randomNumber);
					try {
						Thread.sleep(randomNumber);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				latch.countDown();
			}
		});
		
		otherThread.start();
		thread.start();

		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		try {
			bufw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
