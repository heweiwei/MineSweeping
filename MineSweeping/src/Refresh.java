import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * ˢ����
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
		//����������ʼ��
		m.beWin=true;
		//��ʼ����ʱ��
		m.timeOn=false; //�رռ�ʱ��
		m.timeLabel.setText("0");//��ʱ������
		//��ʼ���Ƿְ�
		m.setMineLast(m.getMineNum());
		m.restart.setIcon(new ImageIcon("images/restart.png"));
		m.countNum.updateUI();
		m.mineLastLabel.setText(String.valueOf(m.getMineLast()));
		//��ʼ������
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
