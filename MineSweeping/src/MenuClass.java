import java.awt.BorderLayout;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 * 菜单响应事件
 * @author wizardwsk
 * 
 */
public class MenuClass extends JFrame{
	Main m;
	int level;
	int mineNum;
	JFrame frame;
	TextField tf1;
	TextField tf2;
	MenuClass(Main m){
		this.m=m;
	}
	public void start(){ //系统初始化
		new Refresh(m).refresh();
	}
	
	public void firstLevel(){
		m.setMineNum(10);
		m.setLevel(9);
		m.frame.setSize(171,275);
		new Refresh(m).refresh();
	}
	public void secondLevel(){
		m.setMineNum(40);
		m.setLevel(16);
		m.frame.setSize(297,395);
		new Refresh(m).refresh();
	}
	public void thirdLevel(){
		m.setMineNum(99);
		m.setLevel(20);
		m.frame.setSize(408,504);
		new Refresh(m).refresh();
	}
	public void userDefined(){
		frame= new JFrame("请输入自定义参数");
		frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);
		tf1 = new TextField();
		tf2= new TextField();
		JButton b= new JButton("提交");
		b.addActionListener(new ButtonMonitor());
		Label lbl = new Label("请输入行数                    请输入雷数");
		frame.add(lbl,BorderLayout.NORTH);
		frame.add(tf1,BorderLayout.WEST);
		frame.add(tf2,BorderLayout.EAST);
		frame.add(b,BorderLayout.SOUTH);
		frame.setLocation(300, 200);
		frame.setSize(200,150);
		frame.setVisible(true);
	}
	public void rankList(){
		JFrame frame= new JFrame("扫雷英雄排行榜");
		frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);
		//读取成绩
		File file=new File("./score.txt");
		InputStream is=null;
		String firstLevel=null;
		String secondLevel=null;
		String thirdLevel=null;
		String firstLevelName=null;
		String secondLevelName=null;
		String thirdLevelName=null;
		try {
			is = new FileInputStream(file);
			BufferedInputStream bis = new BufferedInputStream(is);
			Properties  properties = new Properties();
			properties.load(bis);

			firstLevel=properties.getProperty("firstLevel");
			secondLevel=properties.getProperty("secondLevel");
			thirdLevel=properties.getProperty("thirdLevel");
			
			firstLevelName=properties.getProperty("firstLevelName");
			secondLevelName=properties.getProperty("secondLevelName");
			thirdLevelName=properties.getProperty("thirdLevelName");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(is!=null){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				is=null;
			}
		}

		
		String rows[][]={
				{"初级",firstLevel,firstLevelName},{"中级",secondLevel,secondLevelName},{"高级",thirdLevel,thirdLevelName}
		};
		String columns[] ={"等级", "时间","英雄"};//二维表
		TableModel model = new DefaultTableModel(rows, columns);
		JTable table = new JTable(model);
		RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
		table.setRowSorter(sorter);
		JScrollPane pane = new JScrollPane(table);
		table.setEnabled(false);
		
		frame.setLocation(300, 200);
		frame.add(pane, BorderLayout.CENTER);
		frame.setSize(300,200);
		frame.setVisible(true);
	}
	
	public void close(){
		System.exit(0);
	}
	
	public void about(){
		JFrame frame= new JFrame("关于");
		frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);
		String str="作者：何伟伟   时间：2010年7月27日";
		Label  lbl= new Label(str,Label.CENTER);
		frame.add(lbl,BorderLayout.CENTER);
		frame.setLocation(300, 200);
		frame.setSize(300,200);
		frame.setVisible(true);
	}
	
	class ButtonMonitor implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			level=Integer.parseInt(tf1.getText());		
			mineNum=Integer.parseInt(tf2.getText());
			frame.dispose();
			m.setMineNum(mineNum);
			m.setLevel(level);
			m.frame.setSize(18+18*level,122+17*level);
			new Refresh(m).refresh();
		}
	}
	public static void main(String[] args){
		new MenuClass(new Main()).rankList();
	}
}
