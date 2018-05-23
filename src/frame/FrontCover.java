/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frame;

import java.io.File;
import java.util.Scanner;
import java.util.stream.Stream;
import main.LoadingPlugings;
/**
 *
 * @author 49968
 */
public class FrontCover {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Windows".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		if(args.length>0){
			LoadingPlugings lp=LoadingPlugings.loadingPlugings();
			String list[]=lp.getFunctionList();
			for(int i=0;i<list.length;i++){
				System.out.println(""+(i+1)+'.'+list[i]);
			}
			Scanner a=new Scanner(System.in);
			int num=0;
			while(a.hasNextInt()){
				num=a.nextInt()-1;
				if(num<list.length&&num>=0){
					break;
				}
			}
			lp.createImg(Stream.of(args).map(m->new File(m)).toArray(File[]::new), list[num], ".\\newIMG");
		}else{
			
			new MainFrame(LoadingPlugings.loadingPlugings()).setVisible(true);	
		}
    }
    
}
