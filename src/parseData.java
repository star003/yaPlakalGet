import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//	описание:
//		парсим картинки с yaplakal.com
//		!!адрес страницы версия для печати только !!!
//		АА Рыбинск , 26 февраля 2015 года

public class parseData {
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//	описание:
	//		переменные для получения информации о текущем действии
	static String currentProcessingFolder;
	static String currentProcessingFile;
	static String currentProcessingAdres;
	static String currentEvent;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//	описание:
	//		читает текстовый файл из интернета (ответ php страницы)
	//	параметры:
	//		String lnk - путь к файлу
	//	результат:
	//		String[] с результатом чтения файла
	//	пример:
	//		String[] x = parseData.convToList("www.yaplakal.com/print/forum2/topic1044887.html");
	public static String[] convToList(String lnk) throws IOException {
		URL url 				= new URL(lnk);
		URLConnection conn 		= url.openConnection();
		InputStreamReader rd 	= new InputStreamReader(conn.getInputStream(),"windows-1251");
		StringBuilder allpage 	= new StringBuilder();
		int n 					= 0;
		char[] buffer 			= new char[40000];
		while (n >= 0) {
			n = rd.read(buffer, 0, buffer.length);
			if (n > 0) {
				allpage.append(buffer, 0, n).append("\n");
			}
		}
		String[] x = allpage.toString().split("\\s+");
		return x;
	}//public static String[] convToList(String[] x)
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//	описание:
	//		читает текстовый файл из интернета , разбор идет построчно
	//	параметры:
	//		String lnk - путь к файлу
	//	результат:
	//		String[] с результатом чтения файла
	//	пример:
	//		String[] x = parseData.convToListStringRead("www.yaplakal.com/print/forum2/topic1044887.html");
	public static String[] convToListStringRead(String lnk) throws IOException {
		URL url 				= new URL(lnk);
		URLConnection conn 		= url.openConnection();
		InputStreamReader rd 	= new InputStreamReader(conn.getInputStream(),"windows-1251");
		StringBuilder allpage 	= new StringBuilder();
		int n 					= 0;
		char[] buffer 			= new char[40000];
		while (n >= 0) {
			n = rd.read(buffer, 0, buffer.length);
			if (n > 0) {
				allpage.append(buffer, 0, n).append("\n");
			}
		}
		String[] x = allpage.toString().split("\\n");
		return x;
	}//public static String[] convToListStringRead(String[] x)
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//	описание:
	//		читает текстовый файл c lbcrf
	//	параметры:
	//		String lnk - путь к файлу
	//	результат:
	//		String[] с результатом чтения файла
	static String[] loadData(String lnk) throws Exception{
		String[] everything=null;
		BufferedReader br = new BufferedReader(new FileReader(lnk));
	    try {
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();
	        while (line != null) {
	            sb.append(line);
	            sb.append("\n");
	            line = br.readLine();
	        }
	        everything = sb.toString().split("\\s+");
	    } finally {
	        br.close();
	    }
	    return everything;
	} //static String[] loadData(String lnk) throws Exception
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//	описание:
	//		сохранит файл лежащий по ссылке на диске
	//	параметры:
	//		String filename   - путь и имя файла как сохраняем
	//		String urlString  - адрес откуда тянем	
	public static void saveUrl(String filename, String urlString) throws MalformedURLException, IOException {
    	BufferedInputStream in 	= null;
    	FileOutputStream fout 	= null;
    	try {
    		in 		= new BufferedInputStream(new URL(urlString).openStream());
    		fout 	= new FileOutputStream(filename);

    		byte data[] = new byte[1024];
    		int count;
    		while ((count = in.read(data, 0, 1024)) != -1) {
    			fout.write(data, 0, count);
    		}
    	}
    	finally {
    		if (in != null)
    			in.close();
    		if (fout != null)
    			fout.close();
    	}
    } //saveUrl(String filename, String urlString)
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//	описание:
	//		проверяет есть ли папка и создает ее по необходимости
	//	параметры:
	//		String adr - путь
	static void createDir(String adr){
		File theDir = new File(adr);
		  // if the directory does not exist, create it
		if (!theDir.exists()) {
			System.out.println("-creating directory: " + adr);
			
			logger.addRecord("-creating directory: " + adr);
			currentEvent = "-creating directory: " + adr;
			boolean result = theDir.mkdir();  
			if(result){    
				System.out.println("-DIR created"); 
				logger.addRecord("-DIR created");
				currentEvent = "-DIR created";
			}//if
			else {
				System.out.println("-DIR NOT created"); 
				logger.addRecord("-DIR NOT created");
				currentEvent = "-DIR NOT created";
			}
		}
	}//static void createDir(String adr)
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//	описание:
	//		парсит страницу
	//	параметры:
	//		String pageAdres - адрес в инете откуда сливаем
	//		String saveFolder- адрес папки на диске , куда сливаем
	static public void goParse(String pageAdres,String saveFolder) throws IOException {
		/*
		Document doc  = Jsoup.connect(pageAdres).get();
		Elements metaElements = doc.select("a");
		for (Element x:metaElements) {
			if (x.attr("target").equals("_blank")){
				if(x.attr("href").indexOf("pics_original")>0) {
					System.out.println(x.attr("href"));
				}	
				//itemVal.add(x.text());
			}	
		}
		 */
		String lnk 			= pageAdres;
		String saveSubDir 	= (lnk.split("\\/")[lnk.split("\\/").length-1]).split("\\.")[0]; //**получим имя субдиректории для сохранения
		parseData.createDir(saveFolder+saveSubDir); //**проверим еслить ли папка , если надо - создадим
		String[] x 			= convToList(lnk); //**читаем топик и свалим все в список
		fileData.addFileItemName(readTopicName(pageAdres), saveFolder+saveSubDir); //**пишем файл с названием темы в папку
		currentProcessingAdres = pageAdres;
		currentProcessingFolder= saveSubDir;
		
		int numPP = 0; //**счетчик новых файлов
		for (String h:x) {
			if(h.indexOf("pics_original")>0 & h.split("'").length >= 2) {
				String loadAdr 	= h.split("'")[1]; //**вычленим адрес для загрузки фото
				String[] prom 	= loadAdr.split("\\/");
				String fileName = prom[prom.length-1];//**имя файла для сохранения
				if(fileName.indexOf(".jpg") > 0 | fileName.indexOf(".gif") > 0) { //**нас интересуют только эти типы для загрузки jpg,gif
					currentProcessingFile = fileName;
					
					if (fileExist(saveFolder+saveSubDir+"/"+fileName) == false) { //**этого файла нет , мы его запишем
						if (numPP==0) {
							logger.addRecord("-"+saveSubDir);
						}
						parseData.saveUrl(saveFolder+saveSubDir+"/"+fileName, loadAdr);//**сохраним файл
						System.out.println("--"+fileName);//**сообщим об этом всему миру
						logger.addRecord("--"+fileName);
						numPP++;
					}	
				}
			}
		}
		if(numPP>0){
			logger.addRecord("--write "+String.valueOf(numPP)+" new files in folder "+saveSubDir); //**были новые файлы - сообщим об этом
		}
	}//static public void goParse() throws IOException
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//	описание:
	//		проверит существование файла
	//	параметры:
	//		String lnk - адрес
	//	результат:
	//		есть/нет
	static public boolean fileExist(String lnk) {
		File f = new File(lnk);
		if(f.exists()){
			return true;
		}
		else {
			return false;
		}
	}//static public boolean fileExist(String lnk)
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//	описание:
	//		читает заголовок темы (устаревшая)
	//	параметры:
	//		String lnk - адрес
	//	результат:
	//		заголовок топика
	static public String readTopicNameOld(String lnk) throws IOException{
		String[] x = parseData.convToListStringRead(lnk);
		String a = "";
		try{
			a = (x[2].split(">")[1]).split("<")[0]; //**если что то пойдет не так в чтении заголовка то вернем прочерк
		}
		catch (ArrayIndexOutOfBoundsException e) {
			a = "-";
		}
		//System.out.println(a);
		return a;
	}//static public String readTopicNameOld(String lnk) throws IOException
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//	описание:
	//		читает заголовок темы
	//	параметры:
	//		String lnk - адрес
	//	результат:
	//		заголовок топика
	static public String readTopicName(String lnk) throws IOException {
		Document doc  = Jsoup.connect(lnk).get();
		Elements metaElements = doc.select("title");
		for (Element x:metaElements) {
			//System.out.println();
			return x.text();
			//itemVal.add(x.text());
		}
		return "";
	}//static public String readTopicName(String lnk)
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//	описание:
	//		
	//	параметры:
	//		String pageAdres - адрес
	//		String saveFolder- адрес папки на диске , куда сливаем
	//	результат:
	//		
	static public void goParseJSOUP(String pageAdres,String saveFolder) throws IOException {
		
		FileWriter writeFile = null;
		try {
		    File logFile 	= new File("ind.html");
		    writeFile 		= new FileWriter(logFile, true);
		    writeFile.append("<html>\r\n");
		    writeFile.append("<head>\r\n");
		    writeFile.append("<meta http-equiv='content-type' content='text/html; charset=utf-8'>\r\n");
		    writeFile.append("</head>\r\n");
		    writeFile.append(" <body>\r\n");
		    writeFile.append("  <table>\r\n");
			
			Document doc  = Jsoup.connect(pageAdres).get();
			Elements metaElements = doc.select("font,a");
			//Elements metaElements = doc.select("a");
			for (Element x:metaElements) {
				writeFile.append("     <td>\r\n");
				writeFile.append("    <tr>\r\n");
				writeFile.append(x.text());
				if(x.attr("href").indexOf("pics_original")>0) {
					String[] prom 	= x.attr("href").split("\\/");
					String fileName = prom[prom.length-1];
					writeFile.append("      <img src= '"+fileName+"'>");
				}
				writeFile.append("     </td>\r\n");
				writeFile.append("    </tr>\r\n");
			}
			writeFile.append("   </table>\r\n");
			writeFile.append(" </body>\r\n");
			writeFile.append("</html>\r\n");
			
		    //writeFile.append(str+"\r\n");
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
}//public class parseDasta
