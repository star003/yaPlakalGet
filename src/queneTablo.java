import java.awt.Component;
import java.awt.Dimension;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JProgressBar;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;
import javax.swing.JLabel;


public class queneTablo extends JFrame{

	private static final long serialVersionUID = 20150310;

	private JFrame editPoints;
	
	public static JTable tblEdit;
	
	public static JProgressBar progressBar = new JProgressBar();
	
	public static int itr = 0;
	
	public static JLabel lblNewLabel = new JLabel("");
	
	public static void main(String[] args) throws Exception {
		queneTablo dialog = new queneTablo();
		dialog.setPreferredSize(new Dimension(450, 300));
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
	}//public static void main(String[] args)
	
	public queneTablo() throws Exception {
		setTitle("работа с списком файлов для загрузки");
		initComponents();
	}//public queneTablo()
	
	private void initComponents() throws Exception {
		editPoints = new JFrame();
		setBounds(0, 0, 1037, 559);
		editPoints.getContentPane().setLayout(null);
		
		getContentPane().setLayout(null);
		tblEdit = new JTable();
		tblEdit.setBounds(20, 41, 700, 355);
		tblEdit = new JTable(buildTableModel(null,true));
		tblEdit.setAlignmentX(Component.LEFT_ALIGNMENT);
		tblEdit.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblEdit.setBounds(10, 10, 337, 208);
		JScrollPane scrollPane = new JScrollPane(tblEdit);
		scrollPane.setBounds(20, 64, 975, 376);
		getContentPane().add(scrollPane);
		progressBar.setStringPainted(true);
		progressBar.setBounds(20, 16, 975, 37);
		getContentPane().add(progressBar);
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		panel.setBounds(20, 470, 975, 40);
		getContentPane().add(panel);
		
		JButton btnNewButton = new JButton("обновить ...");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fillTable r1 = new fillTable();
				r1.start();
			}
		});
		
		JButton btnNewButton_1 = new JButton("delete file");
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int[] selectedRows = tblEdit.getSelectedRows();
		            int i 			= 0;
	                int selIndex 	= selectedRows[i];
	                TableModel model= tblEdit.getModel();
					fileData.deleteFile(fileData.getPathForExternalLoad()+"/"+model.getValueAt(selIndex	, 2).toString());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		panel.add(btnNewButton_1);
		panel.add(btnNewButton);
		
		
		lblNewLabel.setBounds(20, 451, 661, 14);
		getContentPane().add(lblNewLabel);
		tblEdit.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
	            int[] selectedRows = tblEdit.getSelectedRows();
	            int i 			= 0;
                int selIndex 	= selectedRows[i];
                TableModel model= tblEdit.getModel();
                lblNewLabel.setText(model.getValueAt(selIndex	, 2).toString());
			} //public void mouseClicked(MouseEvent arg0)   
		});
		fillTable r1 = new fillTable();
		r1.start();
		//pack();
	}//private void initComponents()
	
	public static DefaultTableModel buildTableModel(ResultSet rs,boolean emtyModel) throws Exception  {
		if (emtyModel == true) {
			//**случай , когда нужно получить просто пустую таблицу
			Vector<String> columnNames = new Vector<String>();
			columnNames.add("описание");
			columnNames.add("адрес");
			Vector<Vector<Object>> data = new Vector<Vector<Object>>();
			return new DefaultTableModel(data, columnNames);
		}
	    Vector<String> columnNames = new Vector<String>();
	    columnNames.add("описание");
	    columnNames.add("адрес");
	    columnNames.add("файл");
	    
	    // data of the table
	    Vector<Vector<Object>> data = new Vector<Vector<Object>>();
	    List<String> exitList = new ArrayList<String>();
	    
	    List<String> x = fileData.findAllFilesFromFolderQuenie(fileData.getPathForExternalLoad(),exitList);//**получим список адресов для сохранения
	    int i =0;
	    queneTablo.progressBar.setMaximum(x.size()-1);
	    for (String a:x) {
	    	queneTablo.progressBar.setValue(i);
	    	Vector<Object> vector = new Vector<Object>();
	    	vector.add(parseData.readTopicName(a)); //**колонка 2
	    	vector.add(a); //**колонка 1
	    	vector.add(exitList.get(i));
	    	data.add(vector);
	    	i++;
	    }
	    return new DefaultTableModel(data, columnNames);
	}//public static DefaultTableModel buildTableModel(ResultSet rs,boolean emtyModel)
	
} //public class queneTablo

class fillTable extends Thread {
	@Override
    public void run() {
		try {
			queneTablo.tblEdit.setModel(queneTablo.buildTableModel(null,false));
			//queneTablo.progressBar.setString("complette....");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}//public void run()
}//class fillTable extends Thread