import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTabbedPane;

import java.awt.Insets;

import javax.swing.JTextField;
import javax.swing.JPanel;

import java.awt.Label;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.RowSpec;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import java.awt.Component;
import java.awt.Choice;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeSet;


public class Interface {

	private JFrame frame;
	private JTextField lastNameInput;
	private JFormattedTextField formattedTextField;
	private JMenuItem mntmSave;
	private JMenuItem mntmClear;
	private JButton btnSave;
	private JButton btnClear;
	private JPanel componentsTab;
	private JPanel textInputTab;
	
	private ArrayList<Student> studentsList = new ArrayList<Student>();
	private Student tempStudent = new Student();
	private Choice studyYearInput;
	private Choice facultyInput;
	private Label label_3;
	private Choice genderInput;
	private Label label_5;
	private JTextField weightInput;
	private Label label_8;
	private JTextField achievementsInput;
	private Label label_6;
	private Choice birthYearInput;
	private Label label_2;
	private JLabel lblNewLabel_1;
	private JLabel weightError;
	private JLabel lastNameError;
	private JLabel firstNameError;
	private JLabel achievementsError;
	private JLabel studentNumberLabel;
	private Choice firstNameInput;
	
	private int currentTab = 0;
	private JLabel formattedTextFieldError;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Interface window = new Interface();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void saveComponents() {
		int errors_count = 0;
		try {
			tempStudent.setLastName(lastNameInput.getText());
		} catch (StudentExceptions.InvalidLastNameException e) {
			errors_count += 1;
			setLabel(lastNameError, e.toString());
		}
		try {
			tempStudent.setGender(genderInput.getSelectedItem());
			tempStudent.setStudyYear(studyYearInput.getSelectedItem());
			tempStudent.setBirthYear(birthYearInput.getSelectedItem());
		} catch (ValidationExceptions.ValueTooLowException | 
				ValidationExceptions.ValueTooHighException | 
				ValidationExceptions.InvalidEnumException |
				ValidationExceptions.InvalidIntegerException e) {
			// Not gonna happen, user has dropdown and all values are valid
			e.printStackTrace();
		}
		
		try {
			tempStudent.setFirstName(firstNameInput.getSelectedItem());
		} catch (StudentExceptions.InvalidFirstNameException e1) {
			errors_count += 1;
			setLabel(lastNameError, "Gender and name type don't match");
		}
		
		
		try {
			tempStudent.setWeight(weightInput.getText());
		} catch (ValidationExceptions.ValueTooLowException | ValidationExceptions.ValueTooHighException e) {
			errors_count += 1;
			setLabel(weightError, e.toString());
		}
		catch (ValidationExceptions.InvalidDoubleException e) {
			errors_count += 1;
			setLabel(weightError, "Value is required");
		}
		
		tempStudent.setAchievements(achievementsInput.getText());
		
		if(errors_count == 0) {
			studentsList.add(tempStudent);
			clearAllInputs();
			
		}
	}
	
	private void saveTextInput() {
		try {
			studentsList.add(new Student(formattedTextField.getText()));
			clearAllInputs();
		} catch (StudentExceptions.InvalidLastNameException | 
				StudentExceptions.InvalidFirstNameException |
				ValidationExceptions.InvalidEnumException | 
				ValidationExceptions.InvalidIntegerException |
				ValidationExceptions.ValueTooHighException | 
				ValidationExceptions.ValueTooLowException |
				ValidationExceptions.InvalidDoubleException e) {
			setLabel(formattedTextFieldError, e.toString());
		} catch (IllegalArgumentException e) {
			setLabel(formattedTextFieldError, "Make sure that you're using format specified above");
		}
		
	}
	private void save()   {
		clearErrors();
		if(currentTab == 0) {
			saveComponents();
		} else {
			saveTextInput();
		}
		studentNumberLabel.setText(new Integer(studentsList.size()).toString());
	}

	/**
	 * Create the application.
	 */
	public Interface() {
		initialize();
		propageteDropdowns();
	}
	
	
	private void clearAllInputs() {
		clearErrors();
		achievementsInput.setText("");
		lastNameInput.setText("");
		formattedTextField.setText("");
		weightInput.setText("");

		firstNameInput.select(0);
		formattedTextFieldError.setText("");
		
		facultyInput.select(0);
		genderInput.select(0);
		birthYearInput.select(0);
		studyYearInput.select(0);
		
		
		tempStudent = new Student();
	}
	
	private void clearErrors() {
		lastNameError.setText("");
		weightError.setText("");
	}
	
	private void setLabel(JLabel label, String s) {
		int limit = 60;
		String newStr = "";

        int len = s.length();
        for (int i=0; i<len; i+= limit)
        {
        	newStr += s.substring(i, Math.min(len, i + limit)) +  "<br>" ;
        }

		label.setText("<html>" + newStr + "</html>");
	}
	
	private void propageteDropdowns() {
		Student.loadNames();
		
		for(Faculty value : Faculty.values() ) {
			facultyInput.add(value.getPrettyString());
		}
		
		for(Gender value : Gender.values() ) {
			genderInput.add(value.getPrettyString());
		}
		
		TreeSet<String> names = new TreeSet<String>(new Comparator<String>() {
            public int compare(String string, String otherString) {
                return string.compareTo(otherString);
            }
        });
		
		for(String value : Student.availableStudentFemaleNames ) {
			names.add(value);
		}
		
		for(String value : Student.availableStudentMaleNames ) {
			names.add(value);
		}
		
		for(String value : names) {
			firstNameInput.add(value);
		}
		
		for(int i = Student.MIN_BIRTH; i <= Student.MAX_BIRTH; i += 1) {
			birthYearInput.add(new Integer(i).toString());
		}
		
		for(int i = Student.MIN_STUDY_YEAR; i <= Student.MAX_STUDY_YEAR; i += 1) {
			studyYearInput.add(new Integer(i).toString());
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 550, 550);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);
		
		mntmSave = new JMenuItem("Save");
		mntmSave.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				save();
			}
		});
		mnEdit.add(mntmSave);
		
		mntmClear = new JMenuItem("Clear");
		mntmClear.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				clearAllInputs();
			}
		});
		mnEdit.add(mntmClear);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {100, 150, 0, 0, 50, 50};
		gridBagLayout.rowHeights = new int[] {10, 400, 50};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 0.0};
		frame.getContentPane().setLayout(gridBagLayout);
		
		Label label_4 = new Label("Number of students:");
		GridBagConstraints gbc_label_4 = new GridBagConstraints();
		gbc_label_4.anchor = GridBagConstraints.WEST;
		gbc_label_4.gridwidth = 5;
		gbc_label_4.insets = new Insets(0, 0, 5, 5);
		gbc_label_4.gridx = 0;
		gbc_label_4.gridy = 0;
		frame.getContentPane().add(label_4, gbc_label_4);
		
		studentNumberLabel = new JLabel("");
		GridBagConstraints gbc_studentNumberLabel = new GridBagConstraints();
		gbc_studentNumberLabel.insets = new Insets(0, 0, 5, 5);
		gbc_studentNumberLabel.gridx = 5;
		gbc_studentNumberLabel.gridy = 0;
		frame.getContentPane().add(studentNumberLabel, gbc_studentNumberLabel);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.insets = new Insets(0, 0, 5, 0);
		gbc_tabbedPane.gridwidth = 10;
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 0;
		gbc_tabbedPane.gridy = 1;
		frame.getContentPane().add(tabbedPane, gbc_tabbedPane);
		
		componentsTab = new JPanel();
		componentsTab.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				currentTab = 1;
			}
		});
		tabbedPane.addTab("Components", null, componentsTab, null);
		componentsTab.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("60px"),
				ColumnSpec.decode("right:90px"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("134px:grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("28px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		Label label = new Label("First name");
		componentsTab.add(label, "2, 2");
		
		firstNameInput = new Choice();
		componentsTab.add(firstNameInput, "4, 2");
		
		firstNameError = new JLabel("");
		componentsTab.add(firstNameError, "4, 4");
		
		Label label_1 = new Label("Last Name");
		componentsTab.add(label_1, "2, 6");

		lastNameInput = new JTextField();
		lastNameInput.setColumns(10);
		componentsTab.add(lastNameInput, "4, 6, fill, default");
		
		lastNameError = new JLabel("");
		componentsTab.add(lastNameError, "4, 8");
		
		label_8 = new Label("Weight");
		componentsTab.add(label_8, "2, 10");
		
		weightInput = new JTextField();
		weightInput.setColumns(10);
		componentsTab.add(weightInput, "4, 10, fill, default");
		
		weightError = new JLabel("");
		componentsTab.add(weightError, "4, 12");
		
		label_6 = new Label("Achievements");
		componentsTab.add(label_6, "2, 14");
		
		achievementsInput = new JTextField();
		achievementsInput.setColumns(10);
		componentsTab.add(achievementsInput, "4, 14, fill, default");
		
		achievementsError = new JLabel("");
		componentsTab.add(achievementsError, "4, 16");
		
		lblNewLabel_1 = new JLabel("Birth Year");
		componentsTab.add(lblNewLabel_1, "2, 18");
		
		birthYearInput = new Choice();
		componentsTab.add(birthYearInput, "4, 18");
		
		label_5 = new Label("Gender");
		componentsTab.add(label_5, "2, 20");
		
		genderInput = new Choice();
		componentsTab.add(genderInput, "4, 20");
		
		label_3 = new Label("Faculty");
		componentsTab.add(label_3, "2, 22");
		
		facultyInput = new Choice();
		componentsTab.add(facultyInput, "4, 22");
		
		label_2 = new Label("Study Year:");
		componentsTab.add(label_2, "2, 24");
		
		studyYearInput = new Choice();
		componentsTab.add(studyYearInput, "4, 24");

		textInputTab = new JPanel();
		textInputTab.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				currentTab = 1;
			}
		});
		tabbedPane.addTab("Text input", null, textInputTab, null);
		textInputTab.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,
				RowSpec.decode("15dlu"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblNewLabel = new JLabel("lastName;firstName;gender;birthYear;weight;studyYear;achievements;faculty");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		textInputTab.add(lblNewLabel, "2, 1");
		
		formattedTextField = new JFormattedTextField();
		textInputTab.add(formattedTextField, "2, 2, fill, top");
		
		formattedTextFieldError = new JLabel("");
		textInputTab.add(formattedTextFieldError, "2, 4, fill, default");
		
		btnSave = new JButton("Save");
		btnSave.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				save();
			}
		});
		btnClear = new JButton("Clear");
		btnClear.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				clearAllInputs();
			}
		});
		GridBagConstraints gbc_btnClear = new GridBagConstraints();
		gbc_btnClear.insets = new Insets(0, 0, 0, 5);
		gbc_btnClear.gridx = 8;
		gbc_btnClear.gridy = 2;
		frame.getContentPane().add(btnClear, gbc_btnClear);
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		GridBagConstraints gbc_btnSave = new GridBagConstraints();
		gbc_btnSave.gridx = 9;
		gbc_btnSave.gridy = 2;
		frame.getContentPane().add(btnSave, gbc_btnSave);
		
		frame.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{frame.getContentPane(), btnSave, btnClear, tabbedPane, componentsTab, label_1, menuBar, mnEdit, mntmSave, mntmClear}));
		
	}

}
