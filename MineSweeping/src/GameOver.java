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
	 * 游戏成功&&失败
	 */
public class GameOver {
	Main m;
	GameOver(Main m){
		this.m=m;
	}


	/**
	 * 游戏结束
	 */
	public void gameOver(){
		m.beWin=false;
		System.out.println("Game over!");
		JOptionPane.showMessageDialog(m,"游戏结束","触雷",JOptionPane.ERROR_MESSAGE);
		new Refresh(m).refresh();
	}
	
	JFrame frame;
	TextField tf;
	String name="wizard";//姓名
	String level=null; //级别
	Properties  properties=null;
	/**
	 * 胜利
	 */
	public  void win() {
		
		int score=Integer.parseInt(m.timeLabel.getText());//成绩
		
//用户输入姓名
		frame= new JFrame("请留下高手的名字");
		frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);
		tf = new TextField();
		JButton b= new JButton("提交");
		b.addActionListener(new ButtonMonitor());
		Label lbl = new Label("高手请留下姓名：");
		frame.add(lbl,BorderLayout.NORTH);
		frame.add(tf,BorderLayout.CENTER);
		frame.add(b,BorderLayout.SOUTH);
		frame.setSize(200,150);
		frame.setVisible(true);
		
//更新英雄榜
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
			os= new FileOutputStream(file);//写回
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
		System.out.println("扫雷成功！");
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
