/*
 * cashSuper.java
 *
 * Created on __DATE__, __TIME__
 */

package CashSuper;

/**
 *
 * @author  __USER__
 */
public class cashSuper extends javax.swing.JFrame {

	/** Creates new form cashSuper */
	public cashSuper() {
		initComponents();
	}

	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	//GEN-BEGIN:initComponents
	// <editor-fold defaultstate="collapsed" desc=" Generated Code ">
	private void initComponents() {
		jLabel1 = new javax.swing.JLabel();
		jLabel2 = new javax.swing.JLabel();
		jLabel3 = new javax.swing.JLabel();
		jButton1 = new javax.swing.JButton();
		jButton2 = new javax.swing.JButton();
		jTextField1 = new javax.swing.JTextField();
		jTextField2 = new javax.swing.JTextField();
		jScrollPane1 = new javax.swing.JScrollPane();
		jTextArea1 = new javax.swing.JTextArea();
		jLabel4 = new javax.swing.JLabel();
		jComboBox1 = new javax.swing.JComboBox();
		jLabel5 = new javax.swing.JLabel();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setBackground(new java.awt.Color(204, 255, 153));
		jLabel1.setText("\u5355\u4ef7\uff1a");

		jLabel2.setText("\u6570\u76ee\uff1a");

		jLabel3.setText("\u4f18\u60e0\uff1a");

		jButton1.setText("\u8ba1\u7b97");
		jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				jButton1MouseClicked(evt);
			}
		});

		jButton2.setText("\u6e05\u9664");
		jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				jButton2MouseClicked(evt);
			}
		});

		jTextArea1.setColumns(20);
		jTextArea1.setRows(5);
		jScrollPane1.setViewportView(jTextArea1);

		jLabel4.setText("\u5408\u8ba1\uff1a");

		jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] {
				"\u6b63\u5e38", "\u516b\u6298", "\u4e94\u6298",
				"\u6ee1200\u900150" }));

		org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout
				.setHorizontalGroup(layout
						.createParallelGroup(
								org.jdesktop.layout.GroupLayout.LEADING)
						.add(
								layout
										.createSequentialGroup()
										.add(39, 39, 39)
										.add(
												layout
														.createParallelGroup(
																org.jdesktop.layout.GroupLayout.LEADING)
														.add(
																layout
																		.createSequentialGroup()
																		.add(
																				jLabel3)
																		.addPreferredGap(
																				org.jdesktop.layout.LayoutStyle.RELATED,
																				62,
																				Short.MAX_VALUE)
																		.add(
																				jComboBox1,
																				org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
																				org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																				org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
														.add(
																layout
																		.createSequentialGroup()
																		.add(
																				layout
																						.createParallelGroup(
																								org.jdesktop.layout.GroupLayout.LEADING)
																						.add(
																								jLabel1)
																						.add(
																								jLabel2))
																		.add(
																				48,
																				48,
																				48)
																		.add(
																				layout
																						.createParallelGroup(
																								org.jdesktop.layout.GroupLayout.LEADING,
																								false)
																						.add(
																								jTextField2)
																						.add(
																								jTextField1,
																								org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																								91,
																								Short.MAX_VALUE))))
										.addPreferredGap(
												org.jdesktop.layout.LayoutStyle.RELATED,
												75, Short.MAX_VALUE)
										.add(
												layout
														.createParallelGroup(
																org.jdesktop.layout.GroupLayout.LEADING)
														.add(
																org.jdesktop.layout.GroupLayout.TRAILING,
																jButton1)
														.add(
																org.jdesktop.layout.GroupLayout.TRAILING,
																jButton2)).add(
												54, 54, 54))
						.add(
								layout
										.createSequentialGroup()
										.add(80, 80, 80)
										.add(
												jScrollPane1,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
												org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
										.addContainerGap(100, Short.MAX_VALUE))
						.add(
								layout
										.createSequentialGroup()
										.add(103, 103, 103)
										.add(jLabel4)
										.add(20, 20, 20)
										.add(
												jLabel5,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
												62,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
										.addContainerGap(197, Short.MAX_VALUE)));
		layout
				.setVerticalGroup(layout
						.createParallelGroup(
								org.jdesktop.layout.GroupLayout.LEADING)
						.add(
								layout
										.createSequentialGroup()
										.add(38, 38, 38)
										.add(
												layout
														.createParallelGroup(
																org.jdesktop.layout.GroupLayout.BASELINE)
														.add(jLabel1)
														.add(jButton1)
														.add(
																jTextField1,
																org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
																org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
										.add(23, 23, 23)
										.add(
												layout
														.createParallelGroup(
																org.jdesktop.layout.GroupLayout.LEADING)
														.add(
																layout
																		.createSequentialGroup()
																		.add(
																				layout
																						.createParallelGroup(
																								org.jdesktop.layout.GroupLayout.BASELINE)
																						.add(
																								jLabel2)
																						.add(
																								jTextField2,
																								org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
																								org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																								org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
																		.add(
																				23,
																				23,
																				23)
																		.add(
																				layout
																						.createParallelGroup(
																								org.jdesktop.layout.GroupLayout.BASELINE)
																						.add(
																								jLabel3)
																						.add(
																								jComboBox1,
																								org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
																								org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																								org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
														.add(jButton2))
										.addPreferredGap(
												org.jdesktop.layout.LayoutStyle.RELATED,
												43, Short.MAX_VALUE)
										.add(
												jScrollPane1,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
												83,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												org.jdesktop.layout.LayoutStyle.RELATED)
										.add(
												layout
														.createParallelGroup(
																org.jdesktop.layout.GroupLayout.BASELINE)
														.add(jLabel4)
														.add(
																jLabel5,
																org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
																13,
																org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
										.add(21, 21, 21)));
		pack();
	}// </editor-fold>//GEN-END:initComponents

	//GEN-FIRST:event_jButton2MouseClicked
	private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {
		jTextField2.setText("");
		jTextField1.setText("");
		jTextArea1.setText("");
		jLabel5.setText("");
	}//GEN-LAST:event_jButton2MouseClicked

	
	//GEN-FIRST:event_jButton1MouseClicked
	private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {
		jTextField2.setText("");
		jTextField1.setText("");
		jTextArea1.setText("");
		jLabel5.setText("");
	}
	
	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new cashSuper().setVisible(true);
			}
		});
	}

	//GEN-BEGIN:variables
	// Variables declaration - do not modify
	private javax.swing.JButton jButton1;

	private javax.swing.JButton jButton2;

	private javax.swing.JComboBox jComboBox1;

	private javax.swing.JLabel jLabel1;

	private javax.swing.JLabel jLabel2;

	private javax.swing.JLabel jLabel3;

	private javax.swing.JLabel jLabel4;

	private javax.swing.JLabel jLabel5;

	private javax.swing.JScrollPane jScrollPane1;

	private javax.swing.JTextArea jTextArea1;

	private javax.swing.JTextField jTextField1;

	private javax.swing.JTextField jTextField2;
	// End of variables declaration//GEN-END:variables

}