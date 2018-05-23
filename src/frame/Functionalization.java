package frame;

import java.io.File;

/**
 * 代理
 * 主要界面靠这个来实现业务处理
 * @author 殷泽凌
 */
public interface Functionalization {
	/**
	 * 获取所有功能
	 * @return 所有功能列表
	 */
	String [] getFunctionList();
	
	/**
	 * 创建图片
	 * @param img 文件
	 * @param function 功能全名，XML里面的name
	 * @param savePath 保存路径
	 * @return 是否成功
	 */
	boolean createImg(File[]img,String function,String savePath);
	
}
