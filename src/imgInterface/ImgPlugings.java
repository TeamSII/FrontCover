package imgInterface;

import java.awt.Graphics;

/**
 * 所有的插件必须实现这个接口
 * @author 殷泽凌
 */
public interface ImgPlugings {
	/**
	 * 在背景图贴上去之后调用
	 * @param g 实例
	 * @return 随意
	 */
	default Graphics initGraphics(Graphics g){
		return g;
	}
	/**
	 * 在模板完成之后，在即将导出之前调用
	 * 这个位置顶部的图已经盖上去了
	 * @param g 实例
	 * @return 随意，这个位置返回之后就会导出
	 */
	Graphics modifiedImage(Graphics g);
	
	/**
	 * 在图片制作之前会调用一次
	 * @param fileName 文件名
	 */
	void setFileName(String []fileName);
}
