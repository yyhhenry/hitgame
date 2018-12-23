import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
public class SocketService {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter writer;
    public SocketService(ServerSocket server){
        try{
            socket=server.accept();
            in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer=new PrintWriter(socket.getOutputStream());
            System.out.println("服务器启动成功");
        }catch(Exception e) {
            System.out.println("没有启动监听："+e);
        }
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
}