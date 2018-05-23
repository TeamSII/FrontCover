/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package plugings;

import imgInterface.ImgPlugings;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.swing.JOptionPane;
import main.Tools;

/**
 * 感谢SII应援会Destino提供的封面模板
 * @author 殷泽凌
 */
public class TeamSIIKD2 implements ImgPlugings{
	public TeamSIIKD2(){
		Tools.print("感谢SNH48-TeamSII应援会Destino提供的封面模板");
	}
	private String[] fileName;
	
	private void addTime(Graphics g,String time) throws FontFormatException, IOException{
		Font f=Font.createFont(Font.TRUETYPE_FONT,new File("font/迷你简中倩.ttf"));
		f=f.deriveFont((float)37);
		g.setFont(f);
		g.setColor(Color.WHITE);
		g.drawString(time, 22, 50);
	}
	
	private void addName(Graphics g,String []memberName){		
		
		int x[];
		int y;
		switch(fileName.length){
			case 1:
				x=new int[]{343};
				y=147;
				break;
			case 2:
				x=new int[]{151,534};
				y=147;
				break;
			case 3:
				x=new int[]{35,343,650};
				y=147;
				break;
			case 4:
				x=new int[]{33,262,493,724};
				y=150;
				break;
			default:
				x=new int[]{20,209,398,587,776};
				y=505;
		}
		
		g.setFont(new Font("微软雅黑",Font.PLAIN,x.length<5?28:26));
		g.setColor(new Color(80,80,80));
		
		for(int i=0,j=x.length>memberName.length?memberName.length:x.length;i<j;i++){
			g.drawString(memberName[i], x[i], y);
		}
	}
	
	@Override
	public Graphics modifiedImage(Graphics g) {
		String text=Stream.of(fileName).collect(Collectors.joining(" "));
		
		
		//生成时间
		Calendar calendar = Calendar.getInstance();  
        calendar.setTime(new Date());  
        calendar.add(Calendar.DAY_OF_MONTH, -1); 
        String date=JOptionPane.showInputDialog("请输入直播时间",new SimpleDateFormat("yyyy.MM.dd").format(calendar.getTime()));  
		if(date==null){
			return null;
		}
		try {
			addTime(g,date);
		} catch (FontFormatException|IOException ex) {
			return null;
		}
		
		//生成名字
		String memberName[]=JOptionPane.showInputDialog("请输入成员名单，用空格隔开",text).split("\\s+");
		if(memberName==null){
			return null;
		}
		
		addName(g,memberName);
		
		
		
		
		return g;
	}

	@Override
	public void setFileName(String[] fileName) {
		this.fileName=fileName;
	}

}
