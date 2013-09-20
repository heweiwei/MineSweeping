import java.util.Random;

/**
 * ���ɵ��׾���
 * @author wiardwsk
 *
 */
public class Mine {
	int len,num;
	Mine(int len,int num){
		this.len=len;
		this.num=num;
	}
	
	public int[][] getMines(){
		int [][] mines=new int[len+2][len+2];
		//��Χǽ,-2
		for(int i=0;i<len+2;i++){
			for(int j=0;j<len+2;j++){
				if(i==0||i==len+1||j==0||j==len+1){
					mines[i][j]=-2;
				}
			}
		}
		Random rand = new Random();
		//����
		for(int i=0;i<num;i++){
			for(;;){
				int x=rand.nextInt(len);
				int y=rand.nextInt(len);
				if(mines[x+1][y+1]!=-1){
					mines[x+1][y+1]=-1;break;
				}
			}
		}
		//��������
		for(int i=1;i<len+1;i++){
			for(int j=1;j<len+1;j++){
				if(mines[i][j]==0){
					int count=0;
					if(mines[i-1][j-1]==-1)count++;
					if(mines[i-1][j]==-1)count++;
					if(mines[i-1][j+1]==-1)count++;
					if(mines[i][j-1]==-1)count++;
					if(mines[i][j+1]==-1)count++;
					if(mines[i+1][j-1]==-1)count++;
					if(mines[i+1][j]==-1)count++;
					if(mines[i+1][j+1]==-1)count++;
					mines[i][j]=count;
				}
				else 
				{
					continue;
				}
			}
		}
		//�������
		for(int i=0;i<len+2;i++){
			for(int j=0;j<len+2;j++){
				System.out.print(mines[i][j]+" ");
			}
			System.out.println();
		}
		return mines;
	}
	public static void main(String[] args) {
		new Mine(9,10).getMines();
	}

}
