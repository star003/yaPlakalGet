
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

////////////////////////////////////////////////////////////////////////////////////////////////////////
//	��������:
//		����� �������� �� ������������ �������� ���������

public class logger {
		
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	//	��������:
	//		������� ������� � ���
	//	���������:
	//		String a - �������� �������
	//	������:
	//		logger.addRecord("-");
	static public void addRecord(String a){
		
		HideToSystemTray.txTablo.append(a+"\n");
		HideToSystemTray.txTablo.setCaretPosition(mainForm.txTablo.getDocument().getLength());
		
		String str = "[" +java.util.Calendar.getInstance().getTime()+"] "+ a;
		FileWriter writeFile = null;
		try {
		    File logFile 	= new File("log.txt");
		    writeFile 		= new FileWriter(logFile, true);
		    writeFile.append(str+"\r\n");
		} catch (IOException e) {
		    e.printStackTrace();
		} finally {
		    if(writeFile != null) {
		        try {
		            writeFile.close();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		    }
		}
		
	}//static public void addRecord(String a)
	
	
	
}//public class logger

