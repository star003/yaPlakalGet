import java.util.List;



public class otladka {

	public static void main(String[] args) throws Exception {
		
		logger.addRecord("---start---");
		String sourceFolder = fileData.getPathForSave(); //** получим список папок для сохранения
		List<String> x = fileData.findAllFilesFromFolder(fileData.getPathForExternalLoad());//**получим список адресов для сохранения 
		for(String i:x){
			System.out.println(i);
			parseData.goParse(i,sourceFolder);//**сама разборка 
		}
		x = fileData.getListForLoad();
		for(String i:x){
			System.out.println(i);
			parseData.goParse(i,sourceFolder);//**сама разборка 
		}
		logger.addRecord("END.");
		
		parseData.goParseJSOUP("http://www.yaplakal.com/print/forum46/topic1075895.html","");
		
	}//public static void main(String[] args) throws IOException
	
}//public class otladka
