import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
class SocketService {
    public SocketService(String ss) throws Exception{
        try{
        	socket = new Socket(ss,5209);
            System.out.println("客户端启动成功");
            writer = new PrintWriter(socket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }catch(Exception e) {
			JOptionPane.showMessageDialog(null, "无法连接服务端","连接错误",JOptionPane.ERROR_MESSAGE);
            throw e;
        }
    }
    public String readLine() {
    	try {
			return in.readLine();
		} catch (IOException e) {
			return null;
		}
    }
    public void Write(Object...line) {
    	for(int i=0;i<line.length;i++) {
    		writer.println(line[i].toString());
        	writer.flush();
    	}
    }
    ServerSocket server;
    Socket socket;
    BufferedReader in;
    PrintWriter writer;
}
public class DrawSee extends JFrame implements KeyListener{
	private static final long serialVersionUID = 1211111L;
	MyPanel mp;
    private Color rectColor = new Color(255,255,255);
    public DrawSee() throws Exception{
    	Container p = getContentPane();
    	mp=new MyPanel();
    	this.add(mp);
    	this.addKeyListener(this);
    	setBounds(100, 100, 800, 600);
    	setVisible(true);
    	p.setBackground(rectColor);
    	setLayout(null);   
    	setResizable(false);
    	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	this.setTitle("yyhhenry");
    	setTimer();
	}
	private void setTimer(){
    	Timer timeAction = new Timer(100, new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			repaint();
    		}
    	});
        timeAction.start();
	}   
	public void repaint() {
		mp.react();
		mp.repaint();
    }
	public void keyPressed(KeyEvent e) {
		char t=e.getKeyChar();
		mp.kp(t);
	}
	public void keyReleased(KeyEvent e) {
		char t=e.getKeyChar();
		mp.kr(t);
	}
	public void keyTyped(KeyEvent e) {
	}
}
class MyPanel extends JPanel{
	private static final long serialVersionUID = 1211112L;
	SocketService ss;
	private int[][]a;
	private int n,m,k;
	private int rx,ry,fl,fq,rdt,opx,opy,edx,edy,drx,dry;
	private boolean win,lost,dwin,dlost,dwait,hbg;
	private boolean fdd[];
	public void kr(char c) {
		if(c<0||c>300) {
			return;
		}
    	if(c>='A'&&c<='Z') {
    		c-='A';
    		c+='a';
    	}
		fdd[c]=false;
	}
	public void kp(char c) {
		if(c<0||c>300) {
			return;
		}
    	if(c>='A'&&c<='Z') {
    		c-='A';
    		c+='a';
    	}
    	fdd[c]=true;
		if(c=='k'&&!(rx!=n&&a[rx+1][ry]!=1)){
			ry+=5;
			ry=Math.min(ry,m);
			fq=3;
		}
		if(c=='j'&&!(rx!=n&&a[rx+1][ry]!=1)){
			ry-=5;
			ry=Math.max(ry,1);
			fq=3;
		}
		if(c=='u'&&!(ry==1||a[rx][ry-1]==2)){
			a[rx][ry-1]=0;
			lhit();
			wrdel(rx,ry-1);
		}
		if(c=='i'&&!(ry==m||a[rx][ry+1]==2)){
			a[rx][ry+1]=0;
			rhit();
			wrdel(rx,ry+1);
		}
	}
    public void paint(Graphics g){
        super.paint(g);
        g.setColor(new Color(240,240,240));
        g.fillRect(0, 0, 800, 600);
        if(!hbg) {
            g.setColor(Color.RED);
            g.setFont(new Font("微软雅黑", Font.PLAIN, 40));
        	g.drawString("等待你的对手", 280, 320);
        	return;
        }
        int q=15;
        int wq=20;
        int sq=30;
        int xb=Math.max(rx-15, 1);
        int yb=Math.max(ry-20, 1);
        for(int i=xb;i<=n;i++) {
			g.setColor(Color.BLUE);
			g.drawString(""+i, 0, sq+q*(i-xb+1));
        	for(int j=yb;j<=m;j++) {
        		if(i==edx&&j==edy&&a[i][j]==0) {
        			g.setColor(Color.yellow );
        		}else if(i==opx&&j==opy&&a[i][j]==0) {
        			g.setColor(Color.yellow );
        		}else if(a[i][j]==1) {
        			g.setColor(Color.BLACK );
        		}else if(a[i][j]==0) {
        			g.setColor(Color.WHITE);
        		}else if(a[i][j]==2) {
        			g.setColor(Color.magenta);
        		}
        		g.fillRect(wq+q*(j-yb),sq+q*(i-xb), q-1, q-1);
        	}
        }
		g.setColor(Color.GREEN);
		g.fillRect(wq+q*(dry-yb),sq+q*(drx-xb), q-1, q-1);
		g.setColor(Color.RED);
		g.fillRect(wq+q*(ry-yb),sq+q*(rx-xb), q-1, q-1);
        g.setColor(Color.RED);
        if(win) {
        	g.drawString("你赢了！！", 200, 20);
        }else if(lost){
        	g.drawString("你挂了！！", 200, 20);
        }else {
        	g.drawString("剩余材料："+k, 200, 20);
        }
        if(dwin) {
        	g.drawString("对方赢了！！", 100, 20);
        }else if(dlost) {
        	g.drawString("对方挂了！！", 100, 20);
        }
        if(dwait) {
        	g.drawString("Warning:连接中断", 300, 20);
        }
    }
    private void rekt() {
    	if(win)return;
    	if(lost)return;
    	if(!hbg)return;
    	if(fl>0){
    		if(fl>1) {
    			if(rx!=1&&a[rx-1][ry]!=1) {
    				rx--;
    			}else fl=2;
    		}
    		fl--;
    	}else if(rx<n&&a[rx+1][ry]!=1){
    		if(fq==0) {
    			rx++;
    			fq=3;
    		}else {
    			fq--;
    		}
    	}
    	if(rx==n||a[rx+1][ry]==1) {
    		rdt=2;
    		fq=0;
    	}
		if(fdd['w']&&rdt>0&&fl<=1){
			rdt--;
			fl=5;
			fq=3;
		}
		if(fdd['a']&&!(ry==1||a[rx][ry-1]==1)){
			ry--;
		}
		if(fdd['d']&&!(ry==m||a[rx][ry+1]==1)){
			ry++;
		}
		if(fdd['s']&&rx!=n){
			rx++;
			rx=Math.min(rx,n);
		}
		if(fdd['e']&&k!=0&&!(rx==1||a[rx-1][ry]==1)&&!(rx!=n&&a[rx+1][ry]==0)){
			a[rx][ry]=1;
			wradd(rx,ry);
			rx--;
			rx=Math.max(rx,1);
			k--;
		}
		if(fdd['k']&&!(rx!=n&&a[rx+1][ry]!=1)){
			ry+=5;
			ry=Math.min(ry,m);
			fq=3;
		}
		if(fdd['j']&&!(rx!=n&&a[rx+1][ry]!=1)){
			ry-=5;
			ry=Math.max(ry,1);
			fq=3;
		}
		if(fdd['u']&&!(ry==1||a[rx][ry-1]==2)){
			a[rx][ry-1]=0;
			lhit();
			wrdel(rx,ry-1);
		}
		if(fdd['i']&&!(ry==m||a[rx][ry+1]==2)){
			a[rx][ry+1]=0;
			rhit();
			wrdel(rx,ry+1);
		}
    	if(a[rx][ry]==2) {
    		lost=true;
    		wrlost();
    	}else if(rx==edx&&ry==edy) {
    		win=true;
    		wrwin();
    	}
		if(a[rx][ry]==1) {
			a[rx][ry]=0;
			wrdel(rx,ry);
		}
    }
    public void react() {
    	int nrx=rx;
    	int nry=ry;
    	rekt();
    	if(rx!=nrx||ry!=nry)wrat();
    }
    void wrat() {
    	ss.Write("at",rx,ry);
    }
    void lhit() {
    	ss.Write("lhit");
    }
    void rhit() {
    	ss.Write("rhit");
    }
    void wrdel(int x,int y) {
    	ss.Write("del",x,y);
    }
    void wradd(int x,int y) {
    	ss.Write("add",x,y);
    }
    void wrwin() {
    	ss.Write("win");
    }
    void wrlost() {
    	ss.Write("lost");
    }
    class PeekMSG extends Thread{
		@Override
        public void run() {
	    	String v;
	    	int xx,yy;
	    	while(true) {
	        	v=ss.readLine();
	        	hbg=true;
	        	if(v==null) {
	        		dwait=true;
	        		break;
	        	}else if(v.equals("at")) {
	        		drx=Integer.parseInt(ss.readLine());
	        		dry=Integer.parseInt(ss.readLine());
	        	}else if(v.equals("del")) {
	        		xx=Integer.parseInt(ss.readLine());
	        		yy=Integer.parseInt(ss.readLine());
	        		a[xx][yy]=0;
	        	}else if(v.equals("add")) {
	        		xx=Integer.parseInt(ss.readLine());
	        		yy=Integer.parseInt(ss.readLine());
	        		a[xx][yy]=1;
	        	}else if(v.equals("win")) {
	        		dwin=true;
	        	}else if(v.equals("lost")) {
	        		dlost=true;
	        	}else if(v.equals("lhit")) {
	        		if(!win&&!lost)
	        		if((drx-rx)*(drx-rx)+(dry-ry)*(dry-ry)<=9) {
	        			if(ry!=1&&a[rx][ry-1]!=1) {
	        				ry--;
	        			}
	        		}
	        	}else if(v.equals("rhit")) {
	        		if(!win&&!lost)
	        		if((drx-rx)*(drx-rx)+(dry-ry)*(dry-ry)<=9) {
	        			if(ry!=n&&a[rx][ry+1]!=1) {
	        				ry++;
	        			}
	        		}
	        	}
	    	}
		}
	}
    private String getServiceIP() throws FileNotFoundException {
    	Scanner fin;
    	try {
			fin=new Scanner(new File("./data/serv.ini"));
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "找不到关键文件serv.ini。","文件缺失",JOptionPane.ERROR_MESSAGE);
			throw e;
		}
    	final String ans=fin.next();
    	fin.close();
    	return ans;
    }
    private void getMapData() throws FileNotFoundException {
    	Scanner fin;
    	try {
			fin=new Scanner(new File("./data/map.hg"));
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "找不到关键文件map.hg。","文件缺失",JOptionPane.ERROR_MESSAGE);
			throw e;
		}
    	n=fin.nextInt();
    	m=fin.nextInt();
    	k=fin.nextInt();
    	a=new int[n+3][m+3];
    	for(int i=1;i<=n;i++) {
    		for(int j=1;j<=m;j++) {
    			a[i][j]=fin.nextInt();
    		}
    	}
		fdd=new boolean[303];
		if(fin.hasNextInt()) {
			opx=fin.nextInt();
			opy=fin.nextInt();
			edx=fin.nextInt();
			edy=fin.nextInt();
		}else {
			opx=n;
			opy=1;
			edx=1;
			edy=m;
		}
		fin.close();
    }
    private void initGameData() {
    	win=false;
    	lost=false;
    	dwait=false;
    	hbg=false;
		fq=0;
    	rx=opx;
    	ry=opy;
    	drx=opx;
    	dry=opy;
    }
	public MyPanel() throws Exception{
		getMapData();
		initGameData();
		ss=new SocketService(getServiceIP());
		this.new PeekMSG().start();
	}
}