package main;

import frame.Functionalization;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
/**
 * 加载所有的xml文件配置
 * @author 殷泽凌
 */
public class LoadingPlugings implements Functionalization{
	private HashMap<String,Document> plugingMap=new HashMap<>();
	
	private static final LoadingPlugings onlyObject=new LoadingPlugings();
	/**
	 * 单例模式
	 * @return 
	 */
	public static LoadingPlugings loadingPlugings(){
		return onlyObject;
	}
	private LoadingPlugings(){
		File a=new File("./plugings");
		//这个坑是用来保证在IDE中能用的，IDE路径与导出来的路径不一样出现的错误
		//还有敏儿这种路径乱飞的
		if(!a.exists()){
			try {
				a=new File(LoadingPlugings.class.getResource("../plugings").toURI());
			} catch (URISyntaxException ex) {
				Logger.getLogger(LoadingPlugings.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		File fileList[]=a.listFiles((File pathname) -> {
			String fileName=pathname.getPath();
			return "XML".equals(fileName.substring(fileName.lastIndexOf(".")+1).toUpperCase());
		});
		//加载所有xml文件的name属性，并且将name作为key，将文档作为value写入plugingMap
		for(File xmlFile:fileList){
			try {
				Document xmlDoc=DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlFile);
				plugingMap.put(xmlDoc.getDocumentElement().getAttribute("name"),xmlDoc);
			} catch (ParserConfigurationException | SAXException | IOException ex) {
				ex.printStackTrace();
				Tools.print("文档读取失败");
			}
		}
	}
	
	@Override
	public String [] getFunctionList(){
		return plugingMap.keySet().stream().toArray(String[]::new);
	}
	
	@Override
	public boolean createImg(File[] img, String function,String savePath) {
		return new FrontData(plugingMap.get(function),img).createImg(img, savePath);
	}
	
}
