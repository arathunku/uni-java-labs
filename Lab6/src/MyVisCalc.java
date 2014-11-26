import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.TextField;
import java.awt.List;

import javax.swing.JPanel;

import java.awt.Button;

import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Scanner;
import java.util.Vector;
import java.util.Map.Entry;


public class MyVisCalc {

	private JFrame frame;

	private Button saveBtn = new Button("Save");
	private Button restoreBtn = new Button("Restore");
	private Button evaluateBtn = new Button("Evaluate");
	private Button editBtn = new Button("Edit");
	private Button deleteBtn = new Button("Delete");
	
	private List expressionsListContainer = new List(8);
	private JLabel output = new JLabel("output");
	private TextField expressionField = new TextField();

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
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0.95;
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.ipadx = 0;
		gbc.gridwidth = 2;
		
		container.add(this.expressionField, gbc);
		
		gbc.gridwidth = 1;
		gbc.gridx = 2;
		gbc.weightx = 0.05;
		this.evaluateBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				handleEvaluateClick(e);
			}
		});
		
		container.add(this.evaluateBtn, gbc);
		
		
	    gbc.insets = new Insets(0, 0, 5, 5);
		gbc.gridy = 1;
		gbc.gridx = 0;
		gbc.gridwidth = 3;
		gbc.weightx = 1;
	    container.add(this.output, gbc);

		gbc.gridy = 2;
		gbc.gridx = 0;
		gbc.gridwidth = 3;
		gbc.weightx = 1;
		container.add(this.expressionsListContainer, gbc);
		
		gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.gridwidth = 1;
		
		gbc.gridx = 0;
		this.editBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				handleEditClick(e);
			}
		});		
		container.add(this.editBtn, gbc);

		gbc.gridx = 2;
		this.deleteBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				handleDeleteClick(e);
			}
		});
		container.add(this.deleteBtn, gbc);
		
		gbc.gridy = 4;
		gbc.anchor = GridBagConstraints.PAGE_END;
		gbc.gridwidth = 1;

		gbc.insets = new Insets(10, 0, 0, 0);
		gbc.gridx = 0;
		this.restoreBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				restoreExpressions();
			}
		});
		container.add(this.restoreBtn, gbc);

		gbc.gridx = 2;
		this.saveBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				saveExpressions();
			}
		});
		container.add(this.saveBtn, gbc);
		
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
		for (Entry<String, String> entry : this.calc.getVariables().entrySet()) {
			this.expressionsListContainer.add(entry.getKey() +" = " + entry.getValue());
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
				if(this.editIndex != -1) {
					this.editIndex = -1;
					this.evaluateBtn.setLabel("Evaluate");
				}
				this.renderExpressions();
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
		this.evaluateBtn.setLabel("Save");
	}
	
	private void handleDeleteClick(ActionEvent evt) {
		this.calc.getVariables().remove(getSelectedListElement(true));
		this.renderExpressions();
	}
	
	private String getSelectedListElement(boolean required) {
		String selected = this.expressionsListContainer.getSelectedItem();
		if(required && selected == null) {
			setOutput("You've to select expression");
		} 
		
		return selected;	
	}

	
	private void saveExpressions() {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter("./expressions.txt", "UTF-8");
			for (Entry<String, String> entry : this.calc.getVariables().entrySet()) {
				writer.println(entry.getKey() +" = " + entry.getValue());
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
			}

		} catch (FileNotFoundException e1) {
			setOutput("Nothing has been saved previously.");
		} finally {
			if(fileScanner != null) fileScanner.close();
		}
		this.renderExpressions();
	}
}
