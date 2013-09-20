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
 * 主程序，画出主窗口
 * @author wizardwsk
 *
 */
public class Main extends JFrame{
	
	private int level=9;//初级，中级，高级，自定义
	private int mineNum=10;//地雷数
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
	
	Toolkit kit= Toolkit.getDefaultToolkit();//获取图片
	Image []images={
			kit.getImage("images/restart.png")
			//kit.getImage(ClassLoader.getSystemResource("images/restart.png"))
	};
	
	List<JButton> buttons=null;//记录未探测区域的button
	//返回一个外围为0的二维数组
	int[][] mines= new Mine(level,mineNum).getMines(); 

	//使内部类获得外部类的引用
	public Main outer(){
		return Main.this;
	}
	//构造器
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
	
	//画出界面
	public void launchFrame(){
		frame = new JFrame();

		//-----------菜单------------
		JMenuBar menubar = new JMenuBar();
		frame.setJMenuBar(menubar);
		JMenu gameFile = new JMenu("游戏");
		menubar.add(gameFile);
			JMenuItem startItem = new JMenuItem("开局");
			gameFile.add(startItem);
			gameFile.insertSeparator(1);
			JMenuItem firstLevel= new JMenuItem("初级");
			gameFile.add(firstLevel);
			JMenuItem secondLevel= new JMenuItem("中级");
			gameFile.add(secondLevel);
			JMenuItem thirdLevel= new JMenuItem("高级");
			gameFile.add(thirdLevel);
			JMenuItem userDefined= new JMenuItem("自定义");
			gameFile.add(userDefined);
			gameFile.insertSeparator(6);
			JMenuItem rank= new JMenuItem("扫雷英雄榜");
			gameFile.add(rank);
			gameFile.insertSeparator(9);
			JMenuItem close= new JMenuItem("关闭");
			gameFile.add(close);
		JMenu helpFile = new JMenu("帮助");
			JMenuItem aboutItem = new JMenuItem("关于");
			helpFile.add(aboutItem);
		menubar.add(helpFile);
		//-----------记数板------------
		mineLastLabel= new JLabel(String.valueOf(mineLast));
		timeLabel = new JLabel(String.valueOf(timeCount));
		restart = new JButton(new ImageIcon(images[0]));
		countNum = new JPanel(new FlowLayout());
		countNum.add(mineLastLabel);
		countNum.add(restart);
		countNum.add(timeLabel);
		frame.add(countNum,BorderLayout.NORTH);
		//-----------雷区-------------
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
		frame.addWindowListener(new WindowAdapter(){//系统关闭
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		//-----------菜单监听事件---------------
		startItem.addMouseListener(new MouseAdapter() {//开局
			public void mousePressed(MouseEvent e) {
				new MenuClass(outer()).start();
			}
		});
		firstLevel.addMouseListener(new MouseAdapter() {//初级
			public void mousePressed(MouseEvent e) {
				new MenuClass(outer()).firstLevel();
			}
		});
		secondLevel.addMouseListener(new MouseAdapter() {//中级
			public void mousePressed(MouseEvent e) {
				new MenuClass(outer()).secondLevel();
			}
		});
		thirdLevel.addMouseListener(new MouseAdapter() {//高级
			public void mousePressed(MouseEvent e) {
				new MenuClass(outer()).thirdLevel();
			}
		});
		userDefined.addMouseListener(new MouseAdapter() {//高级
			public void mousePressed(MouseEvent e) {
				new MenuClass(outer()).userDefined();
			}
		});
		rank.addMouseListener(new MouseAdapter() {//扫雷英雄榜
			public void mousePressed(MouseEvent e) {
				new MenuClass(outer()).rankList();
			}
		});
		close.addMouseListener(new MouseAdapter() {//关闭
			public void mousePressed(MouseEvent e) {
				new MenuClass(outer()).close();
			}
		}); 
		aboutItem.addMouseListener(new MouseAdapter() {//关于
			public void mousePressed(MouseEvent e) {
				new MenuClass(outer()).about();
			}
		}); 
		
		restart.addMouseListener(new MouseAdapter() {//重新开始
			public void mousePressed(MouseEvent e) {
				new Refresh(outer()).refresh();
			}
		}); 

	}
	class ButtonMonitor extends MouseAdapter{//雷区button的监听
		
		public void mouseReleased(MouseEvent e) {
			if(e.getButton()==e.BUTTON1)//左键的监听
				new ButtonEvent(outer(),e).LeftMouse();
			if(e.getButton()==e.BUTTON3)//右键的监听
				new ButtonEvent(outer(),e).RightMouse();
		}
	}
	
	public static void main(String [] args){
		new Main().launchFrame();
	}
}
