
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JSlider;
import javax.swing.JLabel;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;


public class mainForm extends javax.swing.JFrame {
	
	private static final long serialVersionUID = 20140304;
	
	static boolean goReading = false;
	
	readData r = new readData();
	
	static String txt = "";
	
	static public JTextArea txTablo = new JTextArea();
	
	static public JLabel lbInterval = new JLabel("интервал минуты (15)");
	
	static public JSlider slider = new JSlider();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			mainForm dialog = new mainForm();
			dialog.setPreferredSize(new Dimension(400, 80));;
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dial509
	 */
	public mainForm() {
		setBounds(100, 100, 442, 350);
		getContentPane().setLayout(null);
		
		txTablo.setRows(17);
		txTablo.setColumns(80);
		
		txTablo.setBounds(39, 30, 385, 174);
		JScrollPane outScroll = new JScrollPane(txTablo);
		outScroll.setBounds(10, 10, 400, 200);
		getContentPane().add(outScroll);
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		panel.setBounds(10, 265, 400, 39);
		getContentPane().add(panel);
		
		JButton btnNewButton = new JButton("lonely read");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				r.start();
			}
		});
		
		JButton btnFileList = new JButton("file list");
		btnFileList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					queneTablo.main(null);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		panel.add(btnFileList);
		panel.add(btnNewButton);
		//panel.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{btnNewButton,btnNewButton_1,btnNewButton_3 }));
		
		JButton btnNewButton_1 = new JButton("robot");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				forLoad(null);
			}
		});
		panel.add(btnNewButton_1);
		
		JButton btnNewButton_3 = new JButton("exit");
		panel.add(btnNewButton_3);
		
		
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				lbInterval.setText(String.valueOf(slider.getValue()));
			}
		});
		slider.setValue(15);
		slider.setMaximum(120);
		slider.setMinimum(1);
		slider.setBorder(null);
		slider.setBounds(176, 221, 234, 23);
		getContentPane().add(slider);
		
		
		lbInterval.setBounds(10, 221, 114, 14);
		getContentPane().add(lbInterval);
		btnNewButton_3.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				r.stop();
				System.exit(0);
			}
		});
		//getContentPane().add(txTablo);
		
		
	}//public mainForm()
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//	описание:
	//		инициализация таймера
	private void forLoad(java.awt.event.WindowEvent evt) {                         
        Timer timer = new Timer(0, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            txTablo.append(String.valueOf(java.util.Calendar.getInstance().getTime())+"\n");
            mainForm.txTablo.append(String.valueOf(mainForm.goReading)+"\n");
            if (mainForm.goReading ==false) {
            	mainForm.txTablo.append(" старт потока \n");
            	try {
            		readData r = new readData();
            		r.start();
            	}
            	catch (IllegalThreadStateException e1) {
            		e1.printStackTrace();
            		mainForm.txTablo.append(" проблема с запуском потока \n");
            	}
            	
            }
            else {
            	txTablo.append("!!! процесс уже работает , пропуск!!! \n");
            }
        }
        });
        timer.setDelay(slider.getValue()*60000); 
        timer.start();
    } //private void forLoad(java.awt.event.WindowEvent evt) 
}//public class mainForm extends JDialog

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

class readData extends Thread {
	@Override
	
    public void run() {
		mainForm.goReading = true;
		logger.addRecord("---start---");
		String sourceFolder = null;
		List<String> x = null;
		try {
			sourceFolder = fileData.getPathForSave();
		} catch (Exception e3) {
			logger.addRecord("ошибка получения данных об папке для сохранения");
			e3.printStackTrace();
		} //***получим список папок для сохранения
		
		try {
			x = fileData.findAllFilesFromFolder(fileData.getPathForExternalLoad());
		} catch (Exception e2) {
			logger.addRecord("ошибка чтения тхт файлов источников ссылок");
		}//**получим список адресов для сохранения 
		for(String i:x){
			System.out.println(i);
			try {
				parseData.goParse(i,sourceFolder);
			} catch (IOException e) {
				logger.addRecord("ошибка парсера данных");
			}//**сама разборка 
		}
		try {
			x = fileData.getListForLoad();
		} catch (Exception e1) {
			logger.addRecord("ошибка парсера с ini файла");
		}
		for(String i:x){
			System.out.println(i);
			try {
				parseData.goParse(i,sourceFolder);
			} catch (IOException e) {
				logger.addRecord("ошибка парсера");
			}//**сама разборка 
		}
		logger.addRecord("END.");
		mainForm.goReading = false;
		//mainForm.txTablo.append(" остановка потока \n");
		//mainForm.txTablo.append(String.valueOf(mainForm.goReading)+"\n");
    }//run()
}//class readData extends Thread

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


