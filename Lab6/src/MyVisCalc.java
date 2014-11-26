import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.TextField;
import java.awt.List;

import javax.swing.JPanel;


import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;
import java.util.Vector;


public class MyVisCalc {

	private JFrame frame;

	private JMenuItem saveItem = new JMenuItem("Save");
	private JMenuItem restoreItem = new JMenuItem("Restore");
	private JButton evaluateBtn = new JButton("Evaluate");
	private JButton editBtn = new JButton("Edit Selected");
	private JButton deleteBtn = new JButton("Delete Selected");
	
	private JToolBar toolbar = new JToolBar("Toolbar");

	private List expressionsListContainer = new List(8);
	private JLabel output = new JLabel("output");
	private TextField expressionField = new TextField();
	
	private Vector<String> expressionsList = new Vector<String>(10);

	private MyCalc calc = new MyCalc();
	
	private int editIndex = -1;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MyVisCalc window = new MyVisCalc();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public MyVisCalc() {
		initialize();
	}


	private void initialize() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setSize(450, 350);
	    frame.setBounds(0, 0,  450,  350);
	    frame.setResizable(false);
	    frame.setLayout(new BorderLayout());
	    
	    JPanel gb = new JPanel(new GridBagLayout());
	    
	    GridBagConstraints gbc = new GridBagConstraints();
	    gbc.anchor = GridBagConstraints.NORTH;
	    gbc.weighty = 1;

	    createElements(gb);
	    
	    frame.add(gb, BorderLayout.NORTH);
	    frame.setVisible(true);
	}
	
	private void createElements (Container container) {

	    GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.insets = new Insets(0, 0, 5, 5);
		gbc.gridy = 0;
		gbc.gridx = 1;
		gbc.weightx = 0.5;
		JMenuBar menuBar = new JMenuBar();
		container.add(menuBar, gbc);
		gbc.gridy = 1;
		gbc.weightx = 1;
		container.add(toolbar, gbc);
		toolbar.setFloatable(false);
		
		gbc.gridy = 2;
		container.add(expressionField, gbc);
		
		this.evaluateBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				handleEvaluateClick(e);
			}
		});
		
		toolbar.add(evaluateBtn, gbc);
		
	    
	    gbc.gridy = 3;
	    container.add(this.output, gbc);

		gbc.gridy = 4;
		container.add(this.expressionsListContainer, gbc);
		

		this.editBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				handleEditClick(e);
			}
		});	
		toolbar.add(this.editBtn);


		this.deleteBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				handleDeleteClick(e);
			}
		});
		toolbar.add(this.deleteBtn);
		
		

		JMenu menu = new JMenu("File");
		
		menuBar.add(menu);
		
		this.restoreItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				restoreExpressions();
			}
		});
		menu.add(restoreItem);

		saveItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				saveExpressions();
			}
		});
		menu.add(saveItem);
		
	}

	private void setOutput(String s) {
		int limit = 60;
		String newStr = "";

        int len = s.length();
        for (int i=0; i<len; i+= limit)
        {
        	newStr += "<br>" + s.substring(i, Math.min(len, i + limit));
        }

		this.output.setText("<html> output: " + newStr + "</html>");
	}
	
	private void renderExpressions() {
		this.expressionsListContainer.removeAll();
		int i = this.expressionsList.size() - 1;
		for(; i >= 0; i -= 1) {
			this.expressionsListContainer.add(this.expressionsList.get(i));
		}
	}
	
	private void handleEvaluateClick(ActionEvent evt) {

			String input = this.expressionField.getText().trim();
			if(input.equals("")) {
				input = getSelectedListElement(false);
				if(input == null) {
					return;
				}
			}
			
			try {
				String output = this.calc.processExpression(input);
				setOutput(output);
				if(this.editIndex == -1) {
					this.storeExpression(input);
				} else {
					this.expressionsList.set(
						this.expressionsList.size() - 1 - this.editIndex
						, input
					);
					this.editIndex = -1;
					this.evaluateBtn.setText("Evaluate");
					this.renderExpressions();
				}
				this.expressionField.setText("");
			} catch (MyCalcExceptions.UndefinedVariable | 
					MyCalcExceptions.VariablesMaxReached |
					MyCalcExceptions.InvalidExpressionsString e) {
				setOutput(e.toString());
			}
	}
	
	private void handleEditClick(ActionEvent evt) {
		String input = getSelectedListElement(true);
		if(input == null) return;

		expressionField.setText(input);
		expressionField.requestFocus();
		this.editIndex = this.expressionsListContainer.getSelectedIndex();
		this.evaluateBtn.setText("Save");
		this.setOutput("CAUTION! If you change variable - there might be unexpected behaviour after restore.");
	}
	
	private void handleDeleteClick(ActionEvent evt) {
		if(this.expressionsListContainer.getSelectedIndex() == -1) return;
		this.expressionsList.remove(
			this.expressionsList.size() - 1 - this.expressionsListContainer.getSelectedIndex()
		);
		this.renderExpressions();
	}
	
	
	private String getSelectedListElement(boolean required) {
		String selected = this.expressionsListContainer.getSelectedItem();
		if(required && selected == null) {
			setOutput("You've to select expression");
		} 
		
		return selected;	
	}
	
	private void storeExpression(String s) {
		this.expressionsList.add(s);
		this.renderExpressions();
	}
	
	private void saveExpressions() {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter("./expressions.txt", "UTF-8");
			for(String expression : this.expressionsList) {
				writer.println(expression);
			}
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			setOutput("There were problems with saving the file.");
		} finally {
			if(writer != null) writer.close();
		}
	}
	
	private void restoreExpressions() {
		Scanner fileScanner = null;
		try {
			fileScanner = new Scanner(new File("./expressions.txt"));

			while (fileScanner.hasNextLine()) {
				String s = fileScanner.nextLine().toString();
				try {
					this.calc.processExpression(s);
				} catch (Exception e) { 
					System.out.println("aaaaa!");
				}
				this.storeExpression(s);
			}

		} catch (FileNotFoundException e1) {
			setOutput("Nothing has been saved previously.");
		} finally {
			if(fileScanner != null) fileScanner.close();
		}
		this.renderExpressions();
	}
}
