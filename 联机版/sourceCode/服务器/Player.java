import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
public class Player {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter writer;
    private static int allCount=0;
    private int cnt;
    public Player(ServerSocket server) throws IOException{
    	cnt=++allCount;
        socket=server.accept();
        in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer=new PrintWriter(socket.getOutputStream());
    	System.out.println("Player "+cnt+" connected");
    }
    public String readLine() {
    	if(socket.isClosed())return null;
    	try {
			return in.readLine();
		} catch (IOException e) {
			return null;
		}
    }
    public void Write(String line) {
    	if(socket.isClosed())return;
    	writer.println(line);
        writer.flush();
    }
    public void close() {
    	try {
			socket.close();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
    }
    public int getCnt() {
    	return cnt;
    }
}