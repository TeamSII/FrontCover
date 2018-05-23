package main;

import imgInterface.ImgPlugings;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.imageio.ImageIO;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * 生成模板
 * 主要是在选择好几张图片与哪个功能之后，生成从xml文件中读取出数据
 * @author 殷泽凌
 */
public class FrontData {
	private File backgroundPath;//放在下面打底的图，在XML中必选
	private File topImagePath=null;//盖在最上面的图，在XML中可选
	private class Record{public int x;public int y;public int high;public int wide;}//记录信息
	private Record point[];
	private ImgPlugings imgPlug=null;//插件
	/**
	 * 实例化模板
	 * @param xmlDoc 主要文档，在LoadingPlugings加载完所有的配置xml文件之后，选择功能，然后加载文档
	 * @param fileNum 这个位置的主要功能是确定有几张照片，与文件本身无关
	 */
	public FrontData(Document xmlDoc,File[] fileNum){
		Element e;
		//载入文档，获取模板，如果获取不到就按照最后的一个模板来设置
		try {
            e= (Element) XPathFactory.newInstance()
					.newXPath()
					.evaluate("/FrontCover/format[@number='"+fileNum.length+"']",xmlDoc.getDocumentElement(), XPathConstants.NODE);
        } catch (XPathExpressionException ex) {
            NodeList temp= xmlDoc.getElementsByTagName("format");
			e=(Element)temp.item(temp.getLength()-1);
        }
		if(e==null){
			NodeList temp= xmlDoc.getElementsByTagName("format");
			e=(Element)temp.item(temp.getLength()-1);
		}
		
		//如果包含插件，那么就将插件加载进来
		if(!"".equals(xmlDoc.getDocumentElement().getAttribute("plugings"))){
			initPlugings(xmlDoc.getDocumentElement().getAttribute("plugings"));
		}
		
		//设置一个模板的参数，包括背景图，每个图的坐标与大小
		point=new Record[Integer.parseInt("0"+e.getAttribute("number"))];
		backgroundPath=new File(e.getAttribute("backgroundPath"));
		if(!"".equals(e.getAttribute("topImagePath"))){
			topImagePath=new File(e.getAttribute("topImagePath")) ;
		}
		
		//查找每一个模板是否有设置默认的高宽xy点，没有为0
		int parentHigh=Integer.parseInt("0"+e.getAttribute("high"));
		int parentWide=Integer.parseInt("0"+e.getAttribute("wide"));  
		int parentX=Integer.parseInt("0"+e.getAttribute("x"));
		int parentY=Integer.parseInt("0"+e.getAttribute("y"));
		NodeList pointList= e.getElementsByTagName("point");
		
		//将所有参数记录
		for (int i=0;i<point.length;i++) {
			point[i]=new Record();
			Element eTemp=(Element)pointList.item(i);
			if(eTemp==null){
				continue;
			}
			//如果参数为空就继承
			String attribute=eTemp.getAttribute("x");
			if("".equals(attribute)){
				point[i].x=parentX;
			}else{
				point[i].x=Integer.parseInt(attribute);
			}
			attribute=eTemp.getAttribute("y");
			if("".equals(attribute)){
				point[i].y=parentY;
			}else{
				point[i].y=Integer.parseInt(attribute);
			}
			attribute=eTemp.getAttribute("high");
			if("".equals(attribute)){
				point[i].high=parentHigh;
			}else{
				point[i].high=Integer.parseInt(attribute);
			}
			attribute=eTemp.getAttribute("wide");
			if("".equals(attribute)){
				point[i].wide=parentWide;
			}else{
				point[i].wide=Integer.parseInt(attribute);
			}
		}
	}
	
	/**
	 * 生成图片
	 * @param imgFile 图片文件的实例
	 * @param savePath 保存路径
	 * @return 是否生成成功
	 */
	public boolean createImg(File[] imgFile,String savePath){
		try{
            BufferedImage tag =ImageIO.read(backgroundPath);
			Graphics g= tag.getGraphics();
			
			//如果有插件，那么先运行一次插件的初始化函数，并且将文件名传输给插件
			//设计这个功能是考虑到SII的封面上面的一排名字
			if(imgPlug!=null){
				imgPlug.setFileName(Stream.of(imgFile).map(mapper->{
					String temp=mapper.getName();
					return temp.substring(0, temp.lastIndexOf('.'));
				}).toArray(String[]::new));
				g=imgPlug.initGraphics(g);
			}
			
			int j=imgFile.length<point.length?imgFile.length:point.length;//防止数组越界
			for(int i=0;i<j;i++){
				g.drawImage(changeImg(imgFile[i],point[i].high,point[i].wide),point[i].x,point[i].y,null);
			}
			if(topImagePath!=null){
				g.drawImage(ImageIO.read(topImagePath),0,0,null);
			}
			if(imgPlug!=null){
				g=imgPlug.modifiedImage(g);
			}
            g.dispose();
            ImageIO.write(tag, "PNG", new File(savePath+".png"));
			return true;
        }catch (java.io.IOException e){
            e.printStackTrace();
			return false;
        }catch (NullPointerException e){
			//这个位置是插件中断之后，返回null可以让制作停止
			return false;
		}
	}
	
	
	
	
	/**
	 * 初始化插件功能
	 * @param plugingsName 插件名字
	 */
	private void initPlugings(String plugingsName){
		try {
			Class interF=ImgPlugings.class;
			Class plug=Class.forName("plugings."+plugingsName);
			if(Stream.of(plug.getInterfaces())
					.filter(predicate->interF.equals(predicate))
					.collect(Collectors.counting())==0){
				Tools.print("该插件没有实现ImgPlugings接口");
			}else{
				imgPlug=(ImgPlugings)plug.newInstance();
			}
		} catch (ClassNotFoundException ex) {
			Tools.print("无法查找到该插件");
		} catch (InstantiationException|IllegalAccessException ex) {
			Tools.print("创建"+plugingsName+"插件实例时发生错误,可能是插件没有公共的无参构造方法");
		}
	}
	
	/**
	 * 图片等比变换，来源百度
	 * @param img 文件实例
	 * @param wide 要切多宽
	 * @param high 要切多高
	 * @return BufferedImage
	 */
	private static BufferedImage changeImg(File img,int high,int wide){
		try{
			BufferedImage buffer = ImageIO.read(img);  
	        int w= buffer.getWidth();  
	        int h=buffer.getHeight();  
	        double ratiox = 1.0d;  
	        double ratioy = 1.0d;  
	        ratiox= w * ratiox / wide;  
	        ratioy= h * ratioy / high;  
	          
	        if( ratiox >= 1){  
	            if(ratioy < 1){  
	                ratiox = high * 1.0 / h;  
	            }else{  
	                if(ratiox > ratioy){  
	                    ratiox = high * 1.0 / h;  
	                }else{  
	                    ratiox = wide * 1.0 / w;  
	                }  
	            }  
	        }else{  
	            if(ratioy < 1){  
	                if(ratiox > ratioy){  
	                    ratiox = high * 1.0 / h;  
	                }else{  
	                    ratiox = wide * 1.0 / w;  
	                }  
	            }else{  
	                ratiox = wide * 1.0 / w;  
	            }  
	        }  
	        /* 
	         * 对于图片的放大或缩小倍数计算完成，ratiox大于1，则表示放大，否则表示缩小 
	         */  
	        AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(ratiox, ratiox), null);  
	        buffer = op.filter(buffer, null);  
	        //从放大的图像中心截图  
	        buffer = buffer.getSubimage((buffer.getWidth()-wide)/2, (buffer.getHeight() - high) / 2, wide, high);  
	        return buffer;
        }catch (java.io.IOException e){
            e.printStackTrace();
        }
		return null;
	}
	
}
