import java.awt.BorderLayout;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

	/**
	 * ��Ϸ�ɹ�&&ʧ��
	 */
public class GameOver {
	Main m;
	GameOver(Main m){
		this.m=m;
	}


	/**
	 * ��Ϸ����
	 */
	public void gameOver(){
		m.beWin=false;
		System.out.println("Game over!");
		JOptionPane.showMessageDialog(m,"��Ϸ����","����",JOptionPane.ERROR_MESSAGE);
		new Refresh(m).refresh();
	}
	
	JFrame frame;
	TextField tf;
	String name="wizard";//����
	String level=null; //����
	Properties  properties=null;
	/**
	 * ʤ��
	 */
	public  void win() {
		
		int score=Integer.parseInt(m.timeLabel.getText());//�ɼ�
		
//�û���������
		frame= new JFrame("�����¸��ֵ�����");
		frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);
		tf = new TextField();
		JButton b= new JButton("�ύ");
		b.addActionListener(new ButtonMonitor());
		Label lbl = new Label("����������������");
		frame.add(lbl,BorderLayout.NORTH);
		frame.add(tf,BorderLayout.CENTER);
		frame.add(b,BorderLayout.SOUTH);
		frame.setSize(200,150);
		frame.setVisible(true);
		
//����Ӣ�۰�
		File file=new File("./score.txt");
		InputStream is=null;
		OutputStream os=null;
		String firstLevel=null;
		String secondLevel=null;
		String thirdLevel=null;
		try {
			is = new FileInputStream(file);
			BufferedInputStream bis = new BufferedInputStream(is);
			properties = new Properties();
			properties.load(bis);
			
			switch(m.getLevel()){
			case 9:
				level="firstLevel";
				firstLevel=properties.getProperty("firstLevel");
				if(score<Integer.parseInt(firstLevel)){
					properties.setProperty("firstLevel", String.valueOf(score));
					//properties.setProperty("firstLevelName", name);
				}else return;
				break;

			case 16:
				level="secondLevel";
				secondLevel=properties.getProperty("secondLevel");
				if(score<Integer.parseInt(secondLevel)){
					properties.setProperty("secondLevel", String.valueOf(score));
					//properties.setProperty("secondLevelName", name);
				}else return;
				break;
			case 20:
				level="thirdLevel";
				thirdLevel=properties.getProperty("thirdLevel");
				if(score<Integer.parseInt(thirdLevel)){
					properties.setProperty("thirdLevel", String.valueOf(score));
					//properties.setProperty("thirdLevelName", name);
				}else return;
				break;
			}

			//-------------------------
			os= new FileOutputStream(file);//д��
			properties.store(os, null);
			os.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(os!=null){
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				is=null;
			}
			if(is!=null){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				is=null;
			}
		}
		System.out.println("ɨ�׳ɹ���");
		m.timeOn=false;
		m.beWin=false;
	}
	
	class ButtonMonitor implements ActionListener{
		public void actionPerformed(ActionEvent e) {			
			name=tf.getText();		
			frame.dispose();
			System.out.println(name);
			properties.setProperty(level+"Name", name);
		}
	}

}
