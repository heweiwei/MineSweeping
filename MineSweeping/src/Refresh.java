import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * 刷新类
 * @author wizardwsk
 *
 */
public class Refresh {
	Main m;
	ButtonEvent be;
	Refresh(Main m){
		this.m=m;
	}
	public void refresh(){
		//其他变量初始化
		m.beWin=true;
		//初始化记时器
		m.timeOn=false; //关闭计时器
		m.timeLabel.setText("0");//计时器清零
		//初始化记分板
		m.setMineLast(m.getMineNum());
		m.restart.setIcon(new ImageIcon("images/restart.png"));
		m.countNum.updateUI();
		m.mineLastLabel.setText(String.valueOf(m.getMineLast()));
		//初始化雷区
		m.p.removeAll();
		m.p.setLayout(new GridLayout(m.getLevel(),m.getLevel()));
		//p = new JPanel(new GridLayout(level,level));
		m.mines= new Mine(m.getLevel(),m.getMineNum()).getMines(); 
		m.buttons = new ArrayList<JButton>();
		for(int i=0;i<m.getLevel()*m.getLevel();i++){
			JButton b= new JButton();
			b.setName(String.valueOf(i));
			m.buttons.add(b);
			m.p.add(b);
			b.addMouseListener(m.new ButtonMonitor());
		}
		m.p.updateUI();
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
