import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//	��������:
//		���������� �������� �������� �� �������� ���������� �����
//		� ��� �� � ������� �����

public class fileData {
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//	��������:
	//		������ ������ ��� ������� - �������� ���������
	//	���������:
	//		���
	//	���������:
	//		List<setData> � �����������.
	static public List<setData> loadParametrs() throws Exception{
		String[] x = parseData.loadData(setting.loadList); //**������ ������ ini �����
		List<setData> a1 = new ArrayList<setData>();
		for(String a:x) {
			if (a.indexOf("=") >0) { //**���� ���� = �� ��� ����� ��������� � �� �����������
				setData a2 	= new setData();
				a2.parametr = a.split("=")[0]; //**�������� ��� � ������
				a2.value	= a.split("=")[1];
				a1.add(a2);
			}	
		}
		return a1;
	}//static public List<setData> loadParametrs() throws Exception
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//	��������:
	//		������ ���� � ����� ��� ����������
	//	���������:
	//		���
	//	���������:
	//		String � �����
	//	������:
	//		fileData.getPathForSave()
	static public String getPathForSave() throws Exception {
		List<setData> x = loadParametrs();
		for(setData i:x){
			if (i.parametr.indexOf("ath")>0) { //**���� �������� path , ������� ������ �� ������ ������ ��� ath , ��� ������ �����
				return i.value;
			}
		}
		return "";
	}//static public String getPathForSave() throws Exception
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//	��������:
	//		������ ������ ��� �������� 
	//	���������:
	//		���
	//	���������:
	//		List<String> �� ������� �������
	//	������:
	//		List<String> x = fileData.getListForLoad();
	//		for(String i:x){
	//			System.out.println(i);
	//		}
	static public List<String> getListForLoad() throws Exception {
		List<setData> x = loadParametrs();
		List<String> listLoad = new ArrayList<String>();
		for(setData i:x){
			if (i.parametr.indexOf("oad")>0) { //**�� �� ����� , ���� ��� �������� ��� ������ �����
				listLoad.add(i.value);
			}
		}
		return listLoad;
	} //static public List<String> getListForLoad() throws Exception
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//	��������:
	//		������ ���� � ����� �� ������� �������� 
	//		��������� ���������� expath
	//	���������:
	//		���
	//	���������:
	//		String � �����
	//	������:
	//		fileData.getPathForExternalLoad()
	static public String getPathForExternalLoad() throws Exception {
		List<setData> x = loadParametrs();
		for(setData i:x){
			if (i.parametr.indexOf("xpath")>0) { //**���� �������� expath , ������� ������ �� ������ ������ ��� ath , ��� ������ �����
				return i.value;
			}
		}
		return "";
	}//static public String getPathForExternalLoad() throws Exception
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	//	��������:
	//		������� ���� � ��������� ���� � ����� ��������
	//	���������:
	//		String topicName - ������ �� ����, 
	//		String fld - ����� 
	//	������:
	//		fileData.addFileItemName("", saveFolder+saveSubDir);
	static public void addFileItemName(String topicName ,String fld){
		if (parseData.fileExist(fld+"/index.txt") == false) { //**�������� �� ������� ��� ���������� �����, ��� ������ ����� �����....
			logger.addRecord("--write index file to folder");
			FileWriter writeFile = null;
			try {
				File logFile 	= new File(fld+"/index.txt");
				writeFile 		= new FileWriter(logFile);
				writeFile.append(topicName+"\r\n");
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
		}
	}//static public void addFileItemName(String a)
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	//	��������:
	//		������ ������ �� ������ ������ � �/�
	//	���������:
	//		String lnk - ����� ����� � ������� 
	//	���������:
	//		List<String> - � �������� ��� ��������
	//
	static public List<String> findAllFilesFromFolder(String lnk) throws Exception {
		List<String> listURL 	= new ArrayList<String>();
		//final String for_Load = "http://www.yaplakal.com/print/[forumName]/[topicName].html";
		String curForum = "";
		String curTopic = "";
		File directory 			= new File(lnk);
		File[] fList 			= directory.listFiles();
		for (File file : fList){
			if (file.getName().indexOf(".txt") >0 ) {
				String[] x = parseData.loadData(lnk+"/"+file.getName());
				for (String a:x) {
					//System.out.println(a);
					String[] path1 = a.split("\\/");
					for (String a1:path1) {
						if(a1.indexOf("orum")>0) {
							curForum = a1;
						}
						try{
							if(a1.indexOf("opic")>0) {
								curTopic =a1.split("\\.")[0];
							}
						}	
						catch (ArrayIndexOutOfBoundsException e) {
							curTopic ="";
						}	
					}
					String curURL = "http://www.yaplakal.com/print/"+curForum+"/"+curTopic+".html";
					listURL.add(curURL);
				}
			}
		}
		return listURL;
	}//static public List<String> findAllFilesFromFolder(String lnk)
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	//	��������:
	//		������ ������ �� ������ ������ � �/�
	//	���������:
	//		String lnk - ����� ����� � ������� 
	//		List<String> exitList ������ � ������� ������� ������ 
	//	���������:
	//		List<String> - � �������� ��� ��������
	//
	static public List<String> findAllFilesFromFolderQuenie(String lnk,List<String> exitList) throws Exception {
		List<String> listURL 	= new ArrayList<String>();
		//final String for_Load = "http://www.yaplakal.com/print/[forumName]/[topicName].html";
		String curForum = "";
		String curTopic = "";
		File directory 			= new File(lnk);
		File[] fList 			= directory.listFiles();
		for (File file : fList){
			if (file.getName().indexOf(".txt") >0 ) {
				String[] x = parseData.loadData(lnk+"/"+file.getName());
				exitList.add(file.getName());
				for (String a:x) {
					//System.out.println(a);
					String[] path1 = a.split("\\/");
					for (String a1:path1) {
						if(a1.indexOf("orum")>0) {
							curForum = a1;
						}
						try{
							if(a1.indexOf("opic")>0) {
								curTopic =a1.split("\\.")[0];
							}
						}	
						catch (ArrayIndexOutOfBoundsException e) {
							curTopic ="";
						}	
					}
					String curURL = "http://www.yaplakal.com/print/"+curForum+"/"+curTopic+".html";
					listURL.add(curURL);
				}
			}
		}
		return listURL;
	}//static public List<String> findAllFilesFromFolderQuenie(String lnk)

	////////////////////////////////////////////////////////////////////////////////////////////////////////
	//	��������:
	//		������ ���� � �����
	//	���������:
	//		String lnk ���� � ����� 
	//		 
	//	������:
	//		

	static public void deleteFile(String lnk) {
		try{
			 
    		File file = new File(lnk);
 
    		if(file.delete()){
    			System.out.println(file.getName() + " is deleted!");
    		}else{
    			System.out.println("Delete operation is failed.");
    		}
 
    	}catch(Exception e){
 
    		e.printStackTrace();
 
    	}
	}//static public void deleteFile(String lnk)
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	//	��������:
	//		������ ������ �� ������ ������ � �/�
	//	���������:
	//		 
	//		 
	//	������:
	//		
	static public void loadFromExternalSourceURL(){
		//String forLoad = "http://www.yaplakal.com/print/[forumName]/[topicName].html";
		//http://www.yaplakal.com/forum2/st/0/topic1053119.html?hl=#entry32806108
		
	} //static public void loadFromExternalSourceURL()
	
	///////////////////////////////////////////////////////////////////////////////////
	
	public static String getTimeCreateFile( String adr) throws IOException {
		/*
		 * ������ ����� �������� ����� 
		 * ������� ����� �� ������ @adr
		 */
		Path path = Paths.get(adr);
		BasicFileAttributes attributes = 
				Files.readAttributes(path, BasicFileAttributes.class);
		FileTime creationTime = attributes.creationTime();
		return creationTime.toString();
	} //public static String getTimeCreateFile( ) throws IOException

	///////////////////////////////////////////////////////////////////////////////////

	public static String getDir(String ops){
		/*
		 * ������ ������ ���������� 
		 * @ops - ��������� ���� ������
		 * ������ ���� � ��������� ����������
		 */
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle(ops);
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		//chooser.setCurrentDirectory(new File(dr1));
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			String r = chooser.getSelectedFile().getAbsolutePath();
			return r;
		} 
		else {
			return null;
		}
	}//public String getDir()

///////////////////////////////////////////////////////////////////////////////////
} //public class fileData

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//	��������:
//		������ ����� ������ �������� �������� ������� �� ������ �� �����
class setData {
	String parametr;
	String value;
}//class setData


