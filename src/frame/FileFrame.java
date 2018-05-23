/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frame;

import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * 选择添加文件
 * @author 殷泽凌
 */
public class FileFrame extends javax.swing.JFrame {
	private MainFrame mf;//主窗体

	public FileFrame(MainFrame mf) {
		this.mf=mf;
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooser1 = new javax.swing.JFileChooser(){
            @Override
            public void approveSelection(){
                jFileChooserApproveSelection();
            }
            @Override
            public void cancelSelection(){
                jFileChooserCancelSelection();
            }
        };

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("选择图片");
        setMinimumSize(new java.awt.Dimension(600, 400));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jFileChooser1.setApproveButtonText("确定(s)");
        jFileChooser1.setApproveButtonToolTipText("确认图片");
        jFileChooser1.setBackground(new java.awt.Color(0, 153, 255));
        jFileChooser1.setCurrentDirectory(new java.io.File("D:\\Program Files\\NetBeans 8.2\\."));
        jFileChooser1.setFileFilter(new FileNameExtensionFilter("成员图片文件","jpg", "jpeg","png"));
        jFileChooser1.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N
        jFileChooser1.setToolTipText("");
        jFileChooser1.setMultiSelectionEnabled(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jFileChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jFileChooser1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        mf.componentsEnabled(true);
    }//GEN-LAST:event_formWindowClosed

	/**
	 * 按下确定按钮事件
	 */
	private void jFileChooserApproveSelection(){
		mf.addImgFile(jFileChooser1.getSelectedFiles());
		jFileChooserCancelSelection();
	}
	/**
	 * 取消按钮事件
	 */
	private void jFileChooserCancelSelection(){
		mf.componentsEnabled(true);
		dispose();
	}
	
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFileChooser jFileChooser1;
    // End of variables declaration//GEN-END:variables
}