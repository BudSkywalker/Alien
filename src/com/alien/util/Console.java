package com.alien.util;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.io.PrintStream;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class Console extends JFrame implements Runnable, ComponentListener, WindowStateListener, KeyListener, ActionListener {
	static JTextArea outputWindow;
	static JTextField cheatField;
	static JFrame frmConsole;
	static JPanel frmPanel;
	static JScrollPane scrollPane;	
	
	ArrayList<String> previousCommands = new ArrayList<>();
	int previousCommandsInt;
	String code;
	
	
	/**
	 * Launch the application.
	 */
	public void run() {
		frmConsole.setVisible(true);
		PrintStream printStream = new PrintStream(new Redirecter(outputWindow));
        System.setOut(printStream);
        System.setErr(printStream);
	}

	/**
	 * Create the application.
	 */
	public Console() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmConsole = new JFrame();
		Log.element("Console", "frmConsole", "Created");
		frmConsole.setBackground(Color.BLACK);
		Log.element("Console", "frmConsole", "Background set to black");
		frmConsole.setBounds(100, 100, 1295, 757);
		Log.element("Console", "frmConsole", "Bounds set");
		Log.element("Console", "frmConsole", "Layout set");
		frmConsole.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		Log.element("Console", "frmConsole", "Default close operation set");
		frmConsole.setTitle("Console");
		Log.element("Console", "frmConsole", "Title set");
		Log.element("Console", "frmConsole", "Loaded");
		
		
		frmPanel = new JPanel();
		frmPanel.setBorder(null);
		Log.element("Console", "frmPanel", "Created");
		Log.element("Console", "frmPanel", "Bounds set");
		frmPanel.setLayout(new BorderLayout(0, 0));
		Log.element("Console", "frmPanel", "Layout set");
		frmConsole.getContentPane().setLayout(new BorderLayout(0, 0));
		frmConsole.getContentPane().add(frmPanel, BorderLayout.CENTER);
		Log.element("Console", "frmPanel", "Loaded");
		Log.element("Console", "frmPanel", "Added to frmConsole");
		
		scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		Log.element("Console", "scrollPane", "Created");
		frmPanel.add(scrollPane);
		Log.element("Console", "scollPane", "Loaded");
		Log.element("Console", "scollPane", "Added to frmConsole");
		
		outputWindow = new JTextArea();
		Log.element("Console", "outputWindow", "Created");
		outputWindow.setBackground(Color.BLACK);
		Log.element("Console", "outputWindow", "Background set to black");
		outputWindow.setEditable(false);
		Log.element("Console", "outputWindow", "Set to uneditable");
		outputWindow.setFont(new Font("Courier New", Font.PLAIN, 14));
		Log.element("Console", "outputWindow", "Font set");
		outputWindow.setForeground(Color.WHITE);
		Log.element("Console", "outputWindow", "Foreground set to white");
		outputWindow.setLineWrap(true);
		Log.element("Console", "outputWindow", "Line wrap set");
		scrollPane.setViewportView(outputWindow);
		Log.element("Console", "outputWindow", "Loaded");
		Log.element("Console", "outputWindow", "Added to scollPane");
		
		
		cheatField = new JTextField();
		cheatField.setBorder(null);
		frmPanel.add(cheatField, BorderLayout.SOUTH);
		cheatField.setForeground(Color.WHITE);
		cheatField.setBackground(Color.BLACK);
		cheatField.setColumns(10);
		cheatField.setVisible(false);
		
		frmConsole.addComponentListener(this);
		frmConsole.addWindowStateListener(this);
		Log.element("Console", "frmConsole", "Listening");
		
		outputWindow.addKeyListener(this);
		Log.element("Console", "outputWindow", "Listening");
		
		cheatField.addActionListener(this);
		cheatField.addKeyListener(this);
	}
	
	public void keyPressed(KeyEvent event) {
		if(event.getSource().equals(outputWindow)) {
			int keyInt = event.getKeyCode();
			String key = KeyEvent.getKeyText(keyInt);
			code = code + key + ",";
			code = code.toUpperCase();
			if(code.contains("UP,") && cheatField.isVisible() == false) {
				cheatField.setVisible(true);
				Log.out("Developer Console accessed");
				frmConsole.setBounds(frmConsole.getX(), frmConsole.getY(), frmConsole.getWidth()-1, frmConsole.getHeight()-1);
				frmConsole.setBounds(frmConsole.getX(), frmConsole.getY(), frmConsole.getWidth()+1, frmConsole.getHeight()+1);
			} else if(code.contains("BACKSPACE")) {
				code = "";
			}
		} else if(event.getSource().equals(cheatField)) {
			int keyInt = event.getKeyCode();
			String key = KeyEvent.getKeyText(keyInt);
			if(key.equals("Up")) {
				try{
					previousCommandsInt--;
					cheatField.setText(previousCommands.get(previousCommandsInt));	
				} catch(ArrayIndexOutOfBoundsException exc) {
					previousCommandsInt++;
				}
			} else if(key.equals("Down")) {
				try{
					previousCommandsInt++;
					cheatField.setText(previousCommands.get(previousCommandsInt));	
				} catch(IndexOutOfBoundsException exc) {
					previousCommandsInt = previousCommands.size();
					cheatField.setText("");
				}
			}
			key = "";	
		}
	}
	
	public void keyReleased(KeyEvent event) {
		//null
	}

	public void keyTyped(KeyEvent event) {
		//null
	}	
	
	public void actionPerformed(ActionEvent event) {
		previousCommands.add(cheatField.getText());
		previousCommandsInt = previousCommands.size();
		Commands.run(cheatField.getText());	
		cheatField.setText("");
	}
	public void componentResized(ComponentEvent event) {
		frmPanel.setBounds(0, 0, frmConsole.getWidth()-15, frmConsole.getHeight()-37);
	}

	public void componentHidden(ComponentEvent event) {
		//null
	}

	public void componentMoved(ComponentEvent event) {
		//null
	}

	public void componentShown(ComponentEvent event) {
		//null
	}

	public void windowStateChanged(WindowEvent event) {
		frmPanel.setBounds(0, 0, frmConsole.getWidth()-15, frmConsole.getHeight()-37);
	}
}