import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

/**
 * �����򣬻���������
 * @author wizardwsk
 *
 */
public class Main extends JFrame{
	
	private int level=9;//�������м����߼����Զ���
	private int mineNum=10;//������
	private int mineLast=10;
	private int timeCount=0;
	static boolean timeOn=false;
	boolean beWin=true;
	
	JFrame frame=null;
	JPanel p=null;
	JPanel countNum=null;
	JLabel mineLastLabel=null;
	JButton restart=null;
	JLabel timeLabel=null;
	ButtonEvent be=null;
	
	Toolkit kit= Toolkit.getDefaultToolkit();//��ȡͼƬ
	Image []images={
			kit.getImage("images/restart.png")
			//kit.getImage(ClassLoader.getSystemResource("images/restart.png"))
	};
	
	List<JButton> buttons=null;//��¼δ̽�������button
	//����һ����ΧΪ0�Ķ�ά����
	int[][] mines= new Mine(level,mineNum).getMines(); 

	//ʹ�ڲ������ⲿ�������
	public Main outer(){
		return Main.this;
	}
	//������
/*	Main(ButtonEvent be){
		
	}*/
	//geter and setter
	public int[][] getMines() {
		return mines;
	}
	public void setMines(int[][] mines) {
		this.mines = mines;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getMineNum() {
		return mineNum;
	}
	public void setMineNum(int mineNum) {
		this.mineNum = mineNum;
	}
	public int getMineLast() {
		return mineLast;
	}
	public void setMineLast(int mineLast) {
		this.mineLast = mineLast;
	}
	public int getTimeCount() {
		return timeCount;
	}
	public void setTimeCount(int timeCount) {
		this.timeCount = timeCount;
	}
	
	//��������
	public void launchFrame(){
		frame = new JFrame();

		//-----------�˵�------------
		JMenuBar menubar = new JMenuBar();
		frame.setJMenuBar(menubar);
		JMenu gameFile = new JMenu("��Ϸ");
		menubar.add(gameFile);
			JMenuItem startItem = new JMenuItem("����");
			gameFile.add(startItem);
			gameFile.insertSeparator(1);
			JMenuItem firstLevel= new JMenuItem("����");
			gameFile.add(firstLevel);
			JMenuItem secondLevel= new JMenuItem("�м�");
			gameFile.add(secondLevel);
			JMenuItem thirdLevel= new JMenuItem("�߼�");
			gameFile.add(thirdLevel);
			JMenuItem userDefined= new JMenuItem("�Զ���");
			gameFile.add(userDefined);
			gameFile.insertSeparator(6);
			JMenuItem rank= new JMenuItem("ɨ��Ӣ�۰�");
			gameFile.add(rank);
			gameFile.insertSeparator(9);
			JMenuItem close= new JMenuItem("�ر�");
			gameFile.add(close);
		JMenu helpFile = new JMenu("����");
			JMenuItem aboutItem = new JMenuItem("����");
			helpFile.add(aboutItem);
		menubar.add(helpFile);
		//-----------������------------
		mineLastLabel= new JLabel(String.valueOf(mineLast));
		timeLabel = new JLabel(String.valueOf(timeCount));
		restart = new JButton(new ImageIcon(images[0]));
		countNum = new JPanel(new FlowLayout());
		countNum.add(mineLastLabel);
		countNum.add(restart);
		countNum.add(timeLabel);
		frame.add(countNum,BorderLayout.NORTH);
		//-----------����-------------
		p = new JPanel(new GridLayout(level,level));
		buttons = new ArrayList<JButton>();
		for(int i=0;i<level*level;i++){
			JButton b= new JButton();
			b.setName(String.valueOf(i));
			//b.setBackground(Color.green);
			buttons.add(b);
			p.add(b);
			b.addMouseListener(new ButtonMonitor());
		}
		frame.add(p,BorderLayout.CENTER);
		frame.setLocation(300,200);
	    frame.setSize(171,275);
	    frame.setResizable(false);
		frame.setVisible(true);
		frame.addWindowListener(new WindowAdapter(){//ϵͳ�ر�
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		//-----------�˵������¼�---------------
		startItem.addMouseListener(new MouseAdapter() {//����
			public void mousePressed(MouseEvent e) {
				new MenuClass(outer()).start();
			}
		});
		firstLevel.addMouseListener(new MouseAdapter() {//����
			public void mousePressed(MouseEvent e) {
				new MenuClass(outer()).firstLevel();
			}
		});
		secondLevel.addMouseListener(new MouseAdapter() {//�м�
			public void mousePressed(MouseEvent e) {
				new MenuClass(outer()).secondLevel();
			}
		});
		thirdLevel.addMouseListener(new MouseAdapter() {//�߼�
			public void mousePressed(MouseEvent e) {
				new MenuClass(outer()).thirdLevel();
			}
		});
		userDefined.addMouseListener(new MouseAdapter() {//�߼�
			public void mousePressed(MouseEvent e) {
				new MenuClass(outer()).userDefined();
			}
		});
		rank.addMouseListener(new MouseAdapter() {//ɨ��Ӣ�۰�
			public void mousePressed(MouseEvent e) {
				new MenuClass(outer()).rankList();
			}
		});
		close.addMouseListener(new MouseAdapter() {//�ر�
			public void mousePressed(MouseEvent e) {
				new MenuClass(outer()).close();
			}
		}); 
		aboutItem.addMouseListener(new MouseAdapter() {//����
			public void mousePressed(MouseEvent e) {
				new MenuClass(outer()).about();
			}
		}); 
		
		restart.addMouseListener(new MouseAdapter() {//���¿�ʼ
			public void mousePressed(MouseEvent e) {
				new Refresh(outer()).refresh();
			}
		}); 

	}
	class ButtonMonitor extends MouseAdapter{//����button�ļ���
		
		public void mouseReleased(MouseEvent e) {
			if(e.getButton()==e.BUTTON1)//����ļ���
				new ButtonEvent(outer(),e).LeftMouse();
			if(e.getButton()==e.BUTTON3)//�Ҽ��ļ���
				new ButtonEvent(outer(),e).RightMouse();
		}
	}
	
	public static void main(String [] args){
		new Main().launchFrame();
	}
}
