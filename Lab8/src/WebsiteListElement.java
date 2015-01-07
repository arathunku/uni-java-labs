import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;


public class WebsiteListElement {
	public JPanel panel = new JPanel();
	private JTextField websiteAddress = new JTextField();
	private JLabel websiteOutput = new JLabel();
	private JScrollPane scrollPanel = new JScrollPane();
	
	public WebsiteListElement() {
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("100px"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(143dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("176px"),
				ColumnSpec.decode("default:grow"),
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(min;45dlu)"),}));
		
		
		JLabel lblWebsite = new JLabel("website:");
		panel.add(lblWebsite, "1, 4, left, default");
		
		panel.add(websiteAddress, "3, 4, 4, 1, fill, default");
		websiteAddress.setColumns(10);
		
		panel.add(scrollPanel, "1, 6, 3, 1, fill, fill");
		
		scrollPanel.setViewportView(websiteOutput);
	}
	
	public void setWebsiteOutput(String str) {
		if(str == null || str.trim().equals("")) {
			scrollPanel.setVisible(false);
		} else {
			scrollPanel.setVisible(true);
		}
		
		websiteOutput.setText(str);
	}
	
	public String getWebsite() {
		return websiteAddress.getText();
	}
	
	public void repaint() {
		websiteOutput.repaint();
	}
}
