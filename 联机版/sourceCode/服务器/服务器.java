import java.io.IOException;
import java.net.ServerSocket;
import java.util.Scanner;

import javax.swing.JOptionPane;
public class ������ {
	public static Scanner cin=new Scanner(System.in);
    static class TransferStation extends Thread{
    	private Player a;
    	private Player b;
    	public TransferStation(Player _a,Player _b) {
    		a=_a;
    		b=_b;
    	}
		@Override
        public void run() {
			while(true) {
				String bs=a.readLine();
				if(bs==null)break;
				b.Write(bs);
			}
			System.out.println("Player "+a.getCnt()+" unconnected");
			a.close();
			b.close();
		}
    }
    public static void main(String[] args){
    	try {
	    	ServerSocket ss=new ServerSocket(5209);
	    	System.out.println("hitgame������������");
	    	while(true) {
		    	final Player a=new Player(ss);
		    	final Player b=new Player(ss);
		    	final TransferStation threadA=new TransferStation(a,b);
		    	final TransferStation threadB=new TransferStation(b,a);
		    	a.Write("begin");
		    	b.Write("begin");
		        threadA.start();
		        threadB.start();
	    	}
    	}catch(IOException e) {
    		JOptionPane.showMessageDialog(null, "hitgame�������������߶���","���Ӵ���",JOptionPane.ERROR_MESSAGE);
    	}
    }
}