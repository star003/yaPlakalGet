import javax.swing.JFrame;
import javax.swing.JLabel;


import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

import javax.swing.JSeparator;


public class setupForm extends JFrame {

	private static final long serialVersionUID = 20150310;
	private JTextField fldSourcePath;
	private JTextField fldCommandFilePath;
	
	public setupForm() throws Exception {
		setBounds(0, 0, 809, 292);
		setTitle("настроки программы");
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("1. настройки путей сохранения");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel.setBounds(20, 21, 751, 19);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("путь к папке для сохранения");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1.setBounds(129, 77, 175, 14);
		getContentPane().add(lblNewLabel_1);
		
		fldSourcePath = new JTextField();
		fldSourcePath.setBounds(314, 74, 299, 20);
		getContentPane().add(fldSourcePath);
		fldSourcePath.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("путь к папке с файлами комманд");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2.setBounds(81, 102, 223, 14);
		getContentPane().add(lblNewLabel_2);
		
		fldCommandFilePath = new JTextField();
		fldCommandFilePath.setBounds(314, 99, 299, 20);
		getContentPane().add(fldCommandFilePath);
		fldCommandFilePath.setColumns(10);
		
		JButton btnNewButton = new JButton("обзор...");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String aa = String.valueOf(fileData.getDir("укажите папку куда сохранять"));
				System.out.println(aa);
				fldSourcePath.setText(aa);
			}
		});
		btnNewButton.setBounds(623, 73, 89, 23);
		getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("обзор...");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fldCommandFilePath.setText(String.valueOf(fileData.getDir("укажите папку с файлами комманд")));
			}
		});
		btnNewButton_1.setBounds(623, 98, 89, 23);
		getContentPane().add(btnNewButton_1);
		
		fldSourcePath .setText(fileData.getPathForSave());
		fldCommandFilePath.setText(fileData.getPathForExternalLoad());
		
		JSeparator separator = new JSeparator();
		separator.setBounds(30, 51, 751, 2);
		getContentPane().add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(30, 143, 751, 2);
		getContentPane().add(separator_1);
	}//public setupForm()

	public static void main(String[] args) throws Exception {
		setupForm dialog = new setupForm();
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
	}//public static void main(String[] args)
}//public class setupForm
