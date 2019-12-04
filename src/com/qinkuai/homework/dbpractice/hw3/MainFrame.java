package com.qinkuai.homework.dbpractice.hw3;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qinkuai.homework.dbpractice.hw3.model.Routine;
import com.qinkuai.homework.dbpractice.hw3.service.ThreadControler;

public class MainFrame {
	private Font font = new Font("黑体", Font.PLAIN, 14);

	public static void main(String[] args) {
		new MainFrame();
	}

	public MainFrame() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.init();
	}

	private void init() {
		JFrame mainFrame = new JFrame("Theater Ticketing System");

		// 头部
		JPanel headPart = new JPanel();
		JLabel jLabel = new JLabel("Routine Number:");
		JComboBox<String> comboBox = new JComboBox<>();
		// 从JSON文件加载
		// 用JSON做了一个伪数据库
		File fakeDBFile = new File("res/fakeDB.json");
		StringBuffer jsonText = new StringBuffer();
		try {
			BufferedReader bufr = new BufferedReader(new InputStreamReader(new FileInputStream(fakeDBFile)));
			String line = bufr.readLine();
			while (line != null) {
				jsonText.append(line);
				line = bufr.readLine();
			}

			bufr.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Routine tempRoutine = null;
		JSONObject jsonObject = JSON.parseObject(jsonText.toString());
		JSONArray jsonArray = jsonObject.getJSONArray("routines");
		for (int i = 0; i < jsonArray.size(); i++) {
			tempRoutine = jsonArray.getObject(i, Routine.class);
			comboBox.addItem(tempRoutine.getRoutineNumber());
		}

		JLabel jLabel1 = new JLabel("Total Ticket Number");
		jLabel1.setFont(font);
		JTextField jtx1 = new JTextField();
		jtx1.setPreferredSize(new Dimension(100, 30));
		jtx1.setFont(font);
		JButton button1 = new JButton("Start Selling");
		button1.setFont(font);
		JLabel jLabel2 = new JLabel("Cuurent Number:");
		jLabel2.setFont(font);
		JLabel jLabel3 = new JLabel("0");
		jLabel3.setFont(font);

		// 头部添加元素
		headPart.add(jLabel);
		headPart.add(comboBox);

		headPart.add(jLabel1);
		headPart.add(jtx1);
		headPart.add(button1);
		headPart.add(jLabel2);
		headPart.add(jLabel3);

		// 左部
		JPanel leftPart = new JPanel();
		JLabel jLabel4 = new JLabel("No.1 Window");
		jLabel4.setFont(font);

		JTextArea jta1 = new JTextArea();
		jta1.setEditable(false);
		JScrollPane scrollPane1 = new JScrollPane(jta1);
		scrollPane1.setPreferredSize(new Dimension(650, 400));

		// 左部添加元素
		leftPart.setLayout(new BorderLayout());
		leftPart.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		leftPart.add(jLabel4, BorderLayout.NORTH);
		leftPart.add(scrollPane1, BorderLayout.CENTER);

		// 右部
		JPanel rightPart = new JPanel();
		JLabel jLabel5 = new JLabel("No.2 Window");
		jLabel5.setFont(font);

		JTextArea jta2 = new JTextArea();
		jta2.setEditable(false);
		JScrollPane scrollPane2 = new JScrollPane(jta2);
		scrollPane2.setPreferredSize(new Dimension(650, 400));

		// 右部添加元素
		rightPart.setLayout(new BorderLayout());
		rightPart.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		rightPart.add(jLabel5, BorderLayout.NORTH);
		rightPart.add(scrollPane2, BorderLayout.CENTER);

		mainFrame.add(headPart, BorderLayout.NORTH);
		mainFrame.add(leftPart, BorderLayout.WEST);
		mainFrame.add(rightPart, BorderLayout.EAST);

		// 为头部的按钮添加监听器
		button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String totalTicketsString = jtx1.getText();
				int totalTicketNumber = Integer.parseInt(totalTicketsString);
				String routuineNumber = (String) comboBox.getSelectedItem();
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						new ThreadControler().start(routuineNumber, totalTicketNumber, jta1, jta2, jLabel3);
					}
				}).start();
			}
		});

		// 总窗口缩紧
		mainFrame.pack();
		// 居中
		mainFrame.setLocationRelativeTo(null);
		// 关闭时退出
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 不可缩放
		mainFrame.setResizable(false);
		// 窗口可见
		mainFrame.setVisible(true);
	}

}
