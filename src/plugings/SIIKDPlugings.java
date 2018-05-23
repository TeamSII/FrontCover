package plugings;

import imgInterface.ImgPlugings;
import java.awt.Graphics;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.swing.JOptionPane;

/**
 * SII口袋封面的插件
 * 因为SII的封面的最上面需要加时间与成员信息
 * @author 殷泽凌
 */
public class SIIKDPlugings implements ImgPlugings{
	String[] fileName;
	public SIIKDPlugings(){}
	
	/**
	 * 
	 * @param g 图片
	 * @param date 时间
	 * @param text 成员名单
	 */
	private void ds(Graphics g,String date,String text){
		int strLong=0;
		int space=text.split("\\s+").length;
		strLong=(text.length()-space+1)*40+(space-1)*12;//一个汉字40像素，一个空格12像素
		strLong=(960-strLong)/2;//居中
		g.setFont(new java.awt.Font("微软雅黑",0,40));
		g.drawString(date+" 口袋直播",291,90);
		g.drawString(text,strLong,135);
	}
	
	@Override
	public Graphics modifiedImage(Graphics g) {
		String text=Stream.of(fileName).collect(Collectors.joining(" "));
		
		//网上找来的时间生成
		Calendar calendar = Calendar.getInstance();  
        calendar.setTime(new Date());  
        calendar.add(Calendar.DAY_OF_MONTH, -1); 
        String date=JOptionPane.showInputDialog("请输入直播时间",new SimpleDateFormat("yyyy.MM.dd").format(calendar.getTime()));  
		if(date==null){
			return null;
		}
		String memberName=JOptionPane.showInputDialog("请输入成员名单，用空格隔开",text);
		if(memberName==null){
			return null;
		}
		ds(g,date,memberName);
		return g;
	}

	@Override
	public void setFileName(String[] fileName) {
		this.fileName=fileName;
	}
	
}
