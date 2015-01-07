import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JTextPane;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.AbstractAction;

import java.awt.event.ActionEvent;

import javax.swing.Action;


public class Lab9Client {
	private static Socket socket;
	private static BufferedReader reader;
	private static PrintWriter writer;
	private JFrame frame;
	private JLabel lblStatusConneted;
	private JTextPane input;
	private final Action send = new SendAction();
	private final Action exit = new ExitAction();
	private JLabel output;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Lab9Client window = new Lab9Client();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Lab9Client() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.setAction(exit);
		mnFile.add(mntmExit);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{298, 0, 0};
		gridBagLayout.rowHeights = new int[]{44, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		input = new JTextPane();
		GridBagConstraints gbc_input = new GridBagConstraints();
		gbc_input.insets = new Insets(0, 0, 5, 5);
		gbc_input.fill = GridBagConstraints.BOTH;
		gbc_input.gridx = 0;
		gbc_input.gridy = 0;
		frame.getContentPane().add(input, gbc_input);
		
		JButton btnSend = new JButton("send");
		btnSend.setAction(send);
		GridBagConstraints gbc_btnSend = new GridBagConstraints();
		gbc_btnSend.insets = new Insets(0, 0, 5, 0);
		gbc_btnSend.gridx = 1;
		gbc_btnSend.gridy = 0;
		frame.getContentPane().add(btnSend, gbc_btnSend);
		
		output = new JLabel("<html><br><html>");
		GridBagConstraints gbc_output = new GridBagConstraints();
		gbc_output.anchor = GridBagConstraints.WEST;
		gbc_output.insets = new Insets(0, 0, 5, 5);
		gbc_output.gridx = 0;
		gbc_output.gridy = 1;
		frame.getContentPane().add(output, gbc_output);
		
		lblStatusConneted = new JLabel("status: conneted");
		GridBagConstraints gbc_lblStatusConneted = new GridBagConstraints();
		gbc_lblStatusConneted.anchor = GridBagConstraints.WEST;
		gbc_lblStatusConneted.insets = new Insets(0, 0, 0, 5);
		gbc_lblStatusConneted.gridx = 0;
		gbc_lblStatusConneted.gridy = 7;
		frame.getContentPane().add(lblStatusConneted, gbc_lblStatusConneted);
		startClient();
	}
	
	private void startClient() {
		int port = 4444;
		        
		try {
			socket = new Socket("127.0.0.1", port);
		    reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		    writer = new PrintWriter(socket.getOutputStream(), true);
		    lblStatusConneted.setText("Status: connected");
		} catch (Exception err) {
			lblStatusConneted.setText("Status: disconnected");
			send.setEnabled(false);
			System.err.println(err);
		}
	}
	
	private void submitToServer() {
		send.setEnabled(false);
		try {
			writer.println(input.getText() + "\n");
			String out = reader.readLine();
			if(out != null && !out.trim().equals("")) {
				output.setText(out);
			}
			send.setEnabled(true);
		} catch (IOException e) {
			lblStatusConneted.setText("Status: disconnected");
		}
	}
	
	private void exit() {
		try {
			writer.println("bye");
			socket.close();
			reader.close();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.exit(0);
	}

	private class SendAction extends AbstractAction {
		public SendAction() {
			putValue(NAME, "Send");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
			submitToServer();
		}
	}
	private class ExitAction extends AbstractAction {
		public ExitAction() {
			putValue(NAME, "Exit");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
			exit();
		}
	}
}
