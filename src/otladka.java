import java.util.List;



public class otladka {

	public static void main(String[] args) throws Exception {
		
		logger.addRecord("---start---");
		String sourceFolder = fileData.getPathForSave(); //** ������� ������ ����� ��� ����������
		List<String> x = fileData.findAllFilesFromFolder(fileData.getPathForExternalLoad());//**������� ������ ������� ��� ���������� 
		for(String i:x){
			System.out.println(i);
			parseData.goParse(i,sourceFolder);//**���� �������� 
		}
		x = fileData.getListForLoad();
		for(String i:x){
			System.out.println(i);
			parseData.goParse(i,sourceFolder);//**���� �������� 
		}
		logger.addRecord("END.");
		
		parseData.goParseJSOUP("http://www.yaplakal.com/print/forum46/topic1075895.html","");
		
	}//public static void main(String[] args) throws IOException
	
}//public class otladka
