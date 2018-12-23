import java.io.IOException;
import java.net.ServerSocket;
import java.util.Scanner;
public class Ghit_main {
	public static Scanner cin=new Scanner(System.in);
	private static SocketService a;
	private static SocketService b;
	private static ServerSocket ss;
	private Ta threadA;
	private Tb threadB;
    class Ta extends Thread{
		@Override
        public void run() {
			while(true) {
				String as=a.readLine();
				if(as==null)break;
				b.Write(as);
			}
			System.out.println("Player A unconnected");
			a.close();
			b.close();
		}
    }
    class Tb extends Thread{
		@Override
        public void run() {
			while(true) {
				String bs=b.readLine();
				if(bs==null)break;
				a.Write(bs);
			}
			System.out.println("Player B unconnected");
			a.close();
			b.close();
		}
    }
    public static void main(String[] args) throws IOException{
    	ss=new ServerSocket(5209);
    	System.out.println("Wait for Players");
    	a=new SocketService(ss);
    	System.out.println("Player A connected");
    	b=new SocketService(ss);
    	System.out.println("Player B connected");
    	a.Write("begin");
    	b.Write("begin");
    	Ghit_main pp=new Ghit_main();
        pp.threadA = pp.new Ta();
        pp.threadB = pp.new Tb();
        pp.threadA.start();
        pp.threadB.start();
    }
}