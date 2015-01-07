import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.GridBagLayout;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;

import java.awt.GridBagConstraints;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.BoxLayout;

import java.awt.Insets;
import java.awt.Component;

import javax.swing.JEditorPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.awt.event.KeyAdapter;

public class gui {

	private JFrame frame = new JFrame();
	private JTextField webBrowserAddress;
	private final Action removeLastWebsite = new RemoveLastWebsite();
	JPanel scrolledPanel = new JPanel();
	private ArrayList<WebsiteListElement> websiteList = new ArrayList<WebsiteListElement>();
	private final Action addWebsite = new AddWebsite();
	private final Action downloadAll = new DownloadAll();
	private List<Future<WebsiteDownloader>> results = new ArrayList<Future<WebsiteDownloader>>();
	private final DisplayWebpage displayWebpage = new DisplayWebpage();
	private JEditorPane webBrowserDisplay;
	private final ExitAction exitAction = new ExitAction();

	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					gui window = new gui();
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
	public gui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frame.setBounds(100, 100, 850, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 0;
		gbc_tabbedPane.gridy = 0;
		frame.getContentPane().add(tabbedPane, gbc_tabbedPane);
		
		JPanel downloader = new JPanel();
		tabbedPane.addTab("Downloader", null, downloader, null);
		GridBagLayout gbl_downloader = new GridBagLayout();
		gbl_downloader.columnWidths = new int[]{829, 0};
		gbl_downloader.rowHeights = new int[]{42, 0};
		gbl_downloader.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_downloader.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		downloader.setLayout(gbl_downloader);
		
		
		JPanel buttonActions = new JPanel();
		buttonActions.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("100px:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(123dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("176px"),
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				RowSpec.decode("max(15dlu;default)"),}));
		
		JButton btnRemoveLastWebsite = new JButton("Remove last website");
		btnRemoveLastWebsite.setAction(removeLastWebsite);
		
				buttonActions.add(btnRemoveLastWebsite, "5, 2");
				
				JButton btnAddWebsite = new JButton("Add website");
				btnAddWebsite.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
					}
				});
				btnAddWebsite.setAction(addWebsite);
				buttonActions.add(btnAddWebsite, "6, 2");
				
				JButton btnDownloadAll = new JButton("Download All");
				btnDownloadAll.setAction(downloadAll);
				buttonActions.add(btnDownloadAll, "7, 2");
				
				
				GridBagConstraints gbc_downloaderConst = new GridBagConstraints();
				gbc_downloaderConst.fill = GridBagConstraints.BOTH;
				gbc_downloaderConst.gridx = 0;
				gbc_downloaderConst.gridy = 0;
				downloader.add(buttonActions, gbc_downloaderConst);
				
				
				GridBagConstraints gbc_scrolledPanelConst = new GridBagConstraints();
				gbc_scrolledPanelConst.fill = GridBagConstraints.BOTH;
				gbc_scrolledPanelConst.gridx = 0;
				gbc_scrolledPanelConst.gridy = 1;
				scrolledPanel.setLayout(new BoxLayout(scrolledPanel, BoxLayout.Y_AXIS));
				downloader.add(new JScrollPane(scrolledPanel), gbc_scrolledPanelConst);
		
		JPanel browser = new JPanel();
		tabbedPane.addTab("Browser", null, browser, null);
		browser.setLayout(new BoxLayout(browser, BoxLayout.X_AXIS));
		
		JPanel panel = new JPanel();
		panel.setAlignmentX(Component.LEFT_ALIGNMENT);
		browser.add(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] {156, 30, 30};
		gbl_panel.rowHeights = new int[]{0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, 0.0};
		gbl_panel.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblAddress = new JLabel("address:");
		GridBagConstraints gbc_lblAddress = new GridBagConstraints();
		gbc_lblAddress.insets = new Insets(0, 0, 5, 5);
		gbc_lblAddress.gridx = 0;
		gbc_lblAddress.gridy = 0;
		panel.add(lblAddress, gbc_lblAddress);
		
		webBrowserAddress = new JTextField();
		webBrowserAddress.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.equals(KeyEvent.VK_ENTER)) {
					displaySingleWebpage();
				}
			}
		});
		GridBagConstraints gbc_webBrowserAddress = new GridBagConstraints();
		gbc_webBrowserAddress.insets = new Insets(0, 0, 5, 5);
		gbc_webBrowserAddress.fill = GridBagConstraints.HORIZONTAL;
		gbc_webBrowserAddress.gridx = 1;
		gbc_webBrowserAddress.gridy = 0;
		panel.add(webBrowserAddress, gbc_webBrowserAddress);
		webBrowserAddress.setColumns(10);
		
		JButton btnDownload = new JButton("download");
		btnDownload.setAction(displayWebpage);
		GridBagConstraints gbc_btnDownload = new GridBagConstraints();
		gbc_btnDownload.insets = new Insets(0, 0, 5, 0);
		gbc_btnDownload.gridx = 2;
		gbc_btnDownload.gridy = 0;
		panel.add(btnDownload, gbc_btnDownload);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridwidth = 3;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		panel.add(scrollPane, gbc_scrollPane);
		
		webBrowserDisplay = new JEditorPane();
		scrollPane.setViewportView(webBrowserDisplay);
				
		addElement();

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.setAction(exitAction);
		mnFile.add(mntmExit);
	}
	
	public void addElement() {
		WebsiteListElement websiteListElement = new WebsiteListElement();
		scrolledPanel.add(websiteListElement.panel);
		
		websiteList.add(websiteListElement);
		websiteListElement.setWebsiteOutput("");
		frame.repaint();
	}
	
	public void removeLastElement() {
		if(websiteList.size() <= 1) return;
		
		WebsiteListElement lastElement = websiteList.get(websiteList.size() - 1);
		scrolledPanel.remove(lastElement.panel);
		websiteList.remove(lastElement);
		frame.repaint();
	}

	
	public void downloadAllWebsites() {
		// Don't start with websites if there are any threads still working.
		if(results.size() > 0) {
			return;
		}
		
		ExecutorService exec = Executors.newFixedThreadPool(5);
		
		for(WebsiteListElement wle : websiteList) {
			WebsiteDownloader websiteDownloader = new WebsiteDownloader (wle.getWebsite());
			wle.setWebsiteOutput("Loading...");
			wle.repaint();
			results.add(exec.submit(websiteDownloader));
		}
		
		 try {
			int i = 0;
			for(Future<WebsiteDownloader> result: results) {
				websiteList.get(i++).setWebsiteOutput(result.get().output);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 results.clear();
	}
	
	public void displaySingleWebpage() {
		if(results.size() > 0) {
			return;
		}

		ExecutorService exec = Executors.newFixedThreadPool(5);
		
		WebsiteDownloader websiteDownloader = new WebsiteDownloader (webBrowserAddress.getText());
		webBrowserDisplay.setText("Loading...");
		webBrowserDisplay.repaint();
		results.add(exec.submit(websiteDownloader));
		
		 try {
			Future<WebsiteDownloader> result = results.get(0);
			webBrowserDisplay.setContentType( "text/html" );
			if(result.get().html != null) {
				webBrowserDisplay.setText(result.get().html);
			} else {
				webBrowserDisplay.setText(result.get().output);
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 results.clear();
	}

	private class RemoveLastWebsite extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = -8001832585646728223L;

		public RemoveLastWebsite() {
			putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
			putValue(NAME, "Remove last website");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		
		public void actionPerformed(ActionEvent e) {
			removeLastElement();
		}
	}
	private class AddWebsite extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = -1687367820250321776L;
		public AddWebsite() {
			putValue(NAME, "Add website");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
			addElement();
		}
	}
	private class DownloadAll extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public DownloadAll() {
			putValue(NAME, "Download All");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
			downloadAllWebsites();
		}
	}
	
	
	private class DisplayWebpage extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public DisplayWebpage() {
			putValue(NAME, "Download");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
			displaySingleWebpage();
		}
	}
	private class ExitAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public ExitAction() {
			putValue(NAME, "Exit");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
			System.exit(1);
		}
	}
}
