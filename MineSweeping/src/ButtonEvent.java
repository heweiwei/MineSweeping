import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Queue;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * �����Ӧ�¼�
 * @author wizardwsk
 * ��̽�׹���
 */
public class ButtonEvent {
	Toolkit kit= Toolkit.getDefaultToolkit();//��ȡͼƬ
/*	Image []images={//����JARʱ�õĶ�ȡͼƬ���
			kit.getImage(ClassLoader.getSystemResource("images/wen.png")),
			kit.getImage(ClassLoader.getSystemResource("images/0.png")),
			kit.getImage(ClassLoader.getSystemResource("images/1.png")),
			kit.getImage(ClassLoader.getSystemResource("images/2.png")),
			kit.getImage(ClassLoader.getSystemResource("images/3.png")),
			kit.getImage(ClassLoader.getSystemResource("images/4.png")),
			kit.getImage(ClassLoader.getSystemResource("images/5.png")),
			kit.getImage(ClassLoader.getSystemResource("images/6.png")),
			kit.getImage(ClassLoader.getSystemResource("images/7.png")),
			kit.getImage(ClassLoader.getSystemResource("images/8.png")),
			kit.getImage(ClassLoader.getSystemResource("images/hei.png")),
			kit.getImage(ClassLoader.getSystemResource("images/hong.png")),
			kit.getImage(ClassLoader.getSystemResource("images/restart.png"))
	};*/
	Image []images={
			kit.getImage("images/wen.png"),//0
			kit.getImage("images/0.png"),//1
			kit.getImage("images/1.png"),//2
			kit.getImage("images/2.png"),//3
			kit.getImage("images/3.png"),//4
			kit.getImage("images/4.png"),//5
			kit.getImage("images/5.png"),//6
			kit.getImage("images/6.png"),//7
			kit.getImage("images/7.png"),//8
			kit.getImage("images/8.png"),//9
			kit.getImage("images/hei.png"),
			kit.getImage("images/hong.png"),
			kit.getImage("images/restart.png"),
			kit.getImage("images/x.png")			
	};
	Main m=null;
	MouseEvent e;
	int level;
	List<JButton>buttons=null;
	int [][]mines;
	
	ButtonEvent(Main m,MouseEvent e){ //���캯��
		this.m=m;
		this.e=e;
		level=m.getLevel();
		buttons=m.buttons;
		mines=m.getMines();
	}
	/**
	 * �������Ķ���
	 * 
	 */
	public void LeftMouse(){ 
		if(buttons.size()==level*level){//��һ�ΰ��£���ʼ��ʱ
			time();
		}
		if(buttons.size()==m.getMineNum()&&m.beWin){// �ɹ�������
			new GameOver(m).win();
		}
		JButton b=(JButton)e.getSource();
		if(!b.getLabel().equals(""))return;//������ť��̽�⣬�򷵻�
		if(b.getIcon()!=null) return;
		int i=Integer.valueOf(b.getName());//�õ���ť������
		int x=i/level;
		int y=i%level;
		
		if(mines[x+1][y+1]==0){//����ťΪ�յأ���ʼBFS����		
				//b.setLabel("0");
				//b.setIcon(new ImageIcon(images[1]));
				b.setEnabled(false);
				buttons.remove(b);
				Queue<Integer> s = new LinkedList<Integer>(); //����ʵ����
				s.add(x*level+y);
				
				for(;!s.isEmpty();){//BFS
					int tmp=(Integer)s.poll();
					int tmpX=tmp/level; //��ʵ����
					int tmpY=tmp%level;
					
					if(mines[tmpX+2][tmpY+1]==0){//button��ʵ����ֱ��1�������     1��0 ����
						JButton tmpB=findOutButton((tmpX+2)*level+tmpY+1);
						if(tmpB!=null){
							if(tmpB.getIcon()==null){
								//tmpB.setIcon(new ImageIcon(images[1]));
								tmpB.setEnabled(false);
							}
							buttons.remove(tmpB);
							s.add(Integer.parseInt(tmpB.getName()));
						}
					}
					if(mines[tmpX+1][tmpY+2]==0){ //  0,1 ����
						JButton tmpB=findOutButton((tmpX+1)*level+tmpY+2);
						if(tmpB!=null){
							if(tmpB.getIcon()==null){
								//tmpB.setIcon(new ImageIcon(images[1]));
								tmpB.setEnabled(false);
							}
							buttons.remove(tmpB);
							s.add(Integer.parseInt(tmpB.getName()));
						}
					}
				if(mines[tmpX][tmpY+1]==0){//    -1,0 ����
						JButton tmpB=findOutButton((tmpX)*level+tmpY+1);
						if(tmpB!=null){
							if(tmpB.getIcon()==null){
								//tmpB.setIcon(new ImageIcon(images[1]));
								tmpB.setEnabled(false);
							}
							buttons.remove(tmpB);
							s.add(Integer.parseInt(tmpB.getName()));
						}
					}
					if(mines[tmpX+1][tmpY]==0){//   0��-1 ����
						JButton tmpB=findOutButton((tmpX+1)*level+tmpY);
						if(tmpB!=null){
							if(tmpB.getIcon()==null){
								//tmpB.setIcon(new ImageIcon(images[1]));
								tmpB.setEnabled(false);
							}
							buttons.remove(tmpB);
							s.add(Integer.parseInt(tmpB.getName()));
						}
					}
				}
		}
		
		else if(mines[x+1][y+1]==-1){//����
			
			m.timeOn=false;//�رռ�ʱ��
			b.setIcon(new ImageIcon(images[11]));
			m.restart.setIcon(new ImageIcon(images[13]));
			m.countNum.updateUI();
			for(int k=1;k<level+1;k++){
				for(int j=1;j<level+1;j++){ //������ʾ���е���
					if(mines[k][j]==-1){
						JButton lei = findOutButton(k*level+j);
						if(!lei.equals(b)){
							lei.setIcon(new ImageIcon(images[10]));
							buttons.remove(lei);
						}
					}
				}
			}
			buttons.remove(b);
			new GameOver(m).gameOver();
		}
		else {//��������
			switch(mines[x+1][y+1]){
			case 1:b.setIcon(new ImageIcon(images[2]));break;
			case 2:b.setIcon(new ImageIcon(images[3]));break;
			case 3:b.setIcon(new ImageIcon(images[4]));break;
			case 4:b.setIcon(new ImageIcon(images[5]));break;
			case 5:b.setIcon(new ImageIcon(images[6]));break;
			case 6:b.setIcon(new ImageIcon(images[7]));break;
			case 7:b.setIcon(new ImageIcon(images[8]));break;
			case 8:b.setIcon(new ImageIcon(images[9]));break;
			}
			//b.setLabel(String.valueOf(mines[x+1][y+1]));
			//b.setEnabled(false);
			buttons.remove(b);
		}
	}
	/**
	 * 
	 * ����Ҽ��Ķ���
	 */
	public void RightMouse() {
		JButton b=(JButton)e.getSource();
		int tmpName=Integer.parseInt(b.getName());
		JButton tmpB=findOutButton(tmpName+level+1);
		if(tmpB==null)return;
		//if(!b.getLabel().equals(""))return;//������ť��̽�⣬�򷵻�
		if(b.getIcon()==null){
			int mineLast=m.getMineLast();
			if(mineLast>0)m.setMineLast(--mineLast);
			else return;
			ImageIcon icon = new ImageIcon("images\\wen.png");
			b.setIcon(icon);
			m.mineLastLabel.setText(String.valueOf(m.getMineLast()));
			m.countNum.updateUI();
		}
		else{
			b.setIcon(null);
			int mineLast=m.getMineLast();
			m.setMineLast(++mineLast);
			m.mineLastLabel.setText(String.valueOf(m.getMineLast()));
			m.countNum.updateUI();
		}
			
	}

	public  JButton findOutButton(int tmp){//����button������,�ҵ���Ӧ��button
		JButton tmpB=null;
		boolean flag=false;
		int tmpX=tmp/level;//��
		int tmpY=tmp%level;
		for(Iterator<JButton> it=buttons.iterator();it.hasNext();){
			tmpB=it.next();
			if(tmpB.getName().equals(String.valueOf((tmpX-1)*level+tmpY-1))){
				flag=true;
				break;
			}
		}
		if(flag)return tmpB;
		else return null;
	}
	
	/**
	 * ��ʱ��
	 */
	public void time(){
		m.timeOn=true;
		new Thread(new TimeCount()).start();
	}
	
	
	public class TimeCount implements Runnable{ //��ʱ��
		public void run() {
			int count=0;
			while(m.timeOn){
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				m.timeLabel.setText(String.valueOf(++count));
				m.p.updateUI();			
			}
		}
	}

}
