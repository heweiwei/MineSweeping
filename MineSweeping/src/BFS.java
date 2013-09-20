import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;


public class BFS {

	static int level=9;
	static List <JButton> buttons = new ArrayList<JButton>();
	public static void main(String[] args) {

		for(int i=0;i<level*level;i++){
			JButton b= new JButton();
			b.setName(String.valueOf(i));
			buttons.add(b);
		}
		buttons.remove(new BFS().findOutButton(80));
		System.out.println(new BFS().findOutButton(80).getName());
	}
	public  JButton findOutButton(int tmp){//buttonÐé×ø±ê
		JButton tmpB=null;
		int tmpX=tmp/level;
		int tmpY=tmp%level;
		boolean flag=false;
		for(Iterator<JButton> it=buttons.iterator();it.hasNext();){
			tmpB=it.next();
			if(tmpB.getName().equals(String.valueOf((tmpX)*level+tmpY))){
				flag=true;
				break;
			}
		}
		if(flag)
			return tmpB;
		else 
			return null;
	}
}
