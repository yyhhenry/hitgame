import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
class SocketService {
    public SocketService(String ss){
        oneServer(ss);
    }
    public String readLine() {
    	try {
			return in.readLine();
		} catch (IOException e) {
			return "";
		}
    }
    public void Write(String line) {
    	writer.println(line);
        writer.flush();
    }
    ServerSocket server;
    Socket socket;
    BufferedReader in;
    PrintWriter writer;
    public  void oneServer(String ss){
        try{
        	socket = new Socket(ss, 5209);
            System.out.println("客户端启动成功");
            writer = new PrintWriter(socket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }catch(Exception e) {
            System.out.println("Error."+e);
        }
    }
}
public class DrawSee extends JFrame implements KeyListener{
	private static final long serialVersionUID = 1211111L;
	MyPanel mp;
    private Color rectColor = new Color(255,255,255);
    public DrawSee(String s){
    	Container p = getContentPane();
    	mp=new MyPanel(s);
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
       			react();
    			repaint();
    		}
    	});
        timeAction.start();
	}   
	public void repaint() {
		mp.repaint();
    }
	public void react() {
		mp.react();
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
	private boolean win,lost,jo,dwin,dlost,dwait;
	private boolean fdd[];
	public void kr(char c) {
		fdd[c]=false;
	}
	public void kp(char c) {
    	if(win)return;
    	if(lost)return;
    	if(c>='A'&&c<='Z') {
    		c-='A';
    		c+='a';
    	}
    	if(fdd[c])return;
    	fdd[c]=true;
		if(c=='w'){
			fdd['s']=false;
		}else if(c=='s'){
			fdd['w']=false;
		}else if(c=='d'){
			fdd['a']=false;
		}else if(c=='e'){
		}else if(c=='k'){
			fdd['j']=false;
		}else if(c=='j'){
			fdd['k']=false;
		}
		rekt();
		jo=true;
	}
    public void paint(Graphics g){
        super.paint(g);
        int q=15;
        int wq=20;
        int sq=30;
        int xb=Math.max(rx-15, 1);
        int xr=Math.min(xb+30, n);
        int yb=Math.max(ry-20, 1);
        int yr=Math.min(yb+40, m);
        for(int i=xb;i<=xr;i++) {
			g.setColor(Color.BLUE);
			g.drawString(""+i, 0, sq+q*(i-xb+1));
        	for(int j=yb;j<=yr;j++) {
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
        		if(i==rx&&j==ry) {
        			g.setColor(Color.RED);
        		}else if(i==drx&&j==dry) {
        			g.setColor(Color.GREEN);
        		}
        		g.fillOval(wq+q*(j-yb)+1,sq+q*(i-xb)+1, q-1, q-1);
        	}
        }
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
    public void rekt() {
    	if(win)return;
    	if(lost)return;
    	if(jo) {
    		jo=false;
    		return;
    	}
		if(fdd['w']){
			if(rdt==0)return;
			if(fl>1)return;
			rdt--;
			fl=5;
			fq=3;
		}
    	if(win)return;
    	if(lost)return;
		if(fdd['a']){
			if(ry==1||a[rx][ry-1]==1)return;
			ry--;

			if(a[rx][ry]==2) {
				lost=true;
			}
			if(rx==edx&&ry==edy) {
	    		win=true;
	    	}
		}
    	if(win)return;
    	if(lost)return;
		if(fdd['s']){
			if(rx==n)return;
			rx++;
			rx=Math.min(rx,n);

			if(a[rx][ry]==2) {
				lost=true;
			}
			if(rx==edx&&ry==edy) {
	    		win=true;
	    	}
		}
    	if(win)return;
    	if(lost)return;
		if(fdd['d']){
			if(ry==m||a[rx][ry+1]==1)return;
			ry++;
			if(a[rx][ry]==2) {
				lost=true;
			}
			if(rx==edx&&ry==edy) {
	    		win=true;
	    	}
		}
    	if(win)return;
    	if(lost)return;
		if(fdd['e']){
			if(k==0)return;
			if(rx==1||a[rx-1][ry]==1)return;
			if(rx!=n&&a[rx+1][ry]==0)return;
			a[rx][ry]=1;
			wradd(rx,ry);
			rx--;
			rx=Math.max(rx,1);
			k--;
			if(a[rx][ry]==2) {
				lost=true;
			}
			if(rx==edx&&ry==edy) {
	    		win=true;
	    	}
		}
    	if(win)return;
    	if(lost)return;
		if(fdd['k']){
			if(rx!=n&&a[rx+1][ry]!=1)return;
			ry+=5;
			ry=Math.min(ry,m);
			fq=3;
			if(a[rx][ry]==2) {
				lost=true;
			}
			if(rx==edx&&ry==edy) {
	    		win=true;
	    	}
		}
    	if(win)return;
    	if(lost)return;
		if(fdd['j']){
			if(rx!=n&&a[rx+1][ry]!=1)return;
			ry-=5;
			ry=Math.max(ry,1);
			fq=3;
			if(a[rx][ry]==2) {
				lost=true;
			}
			if(rx==edx&&ry==edy) {
	    		win=true;
	    	}
		}
    	if(win)return;
    	if(lost)return;
		if(fdd['u']){
			if(ry==1||a[rx][ry-1]==2)return;
			a[rx][ry-1]=0;
			lhit();
			wrdel(rx,ry-1);
		}
    	if(win)return;
    	if(lost)return;
		if(fdd['i']){
			if(ry==m||a[rx][ry+1]==2)return;
			a[rx][ry+1]=0;
			rhit();
			wrdel(rx,ry+1);
		}
    	if(win)return;
    	if(lost)return;
    	if(a[rx][ry]==2) {
    		lost=true;
    	}else if(rx==edx&&ry==edy) {
    		win=true;
    	}
		if(a[rx][ry]==1) {
			a[rx][ry]=0;
			wrdel(rx,ry);
		}
    }
    public void react() {
    	rekt();
    	if(a[rx][ry]==2) {
    		lost=true;
    	}
    	if(lost) {
    		wrlost();
    		wrat();
    		wrend();
        	peekmsg();
    		return;
    	}
    	if(rx==1&&ry==m) {
    		win=true;
    	}
    	if(win) {
    		wrwin();
    		wrat();
    		wrend();
        	peekmsg();
    		return;
    	}
    	if(fl>0){
    		if(fl>1) {
    			if(rx!=1&&a[rx-1][ry]!=1) {
    				rx--;
    				if(a[rx][ry]==2) {
    					lost=true;
    				}
    			}
    			else fl=2;
    		}
    		fl--;
    	}else if(rx<n&&a[rx+1][ry]!=1){
    		if(fq==0) {
    			rx++;
    			if(a[rx][ry]==2) {
    				lost=true;
    			}
    			fq=3;
    		}else {
    			fq--;
    		}
    	}
    	if(rx==n||a[rx+1][ry]==1) {
    		rdt=2;
    		fq=0;
    	}
    	wrat();
    	wrend();
    	peekmsg();
    }
    void wrat() {
    	ss.Write("at");
    	ss.Write(""+rx);
    	ss.Write(""+ry);
    }
    void lhit() {
    	ss.Write("lhit");
    }
    void rhit() {
    	ss.Write("rhit");
    }
    void wrdel(int x,int y) {
    	ss.Write("del");
    	ss.Write(""+x);
    	ss.Write(""+y);
    }
    void wradd(int x,int y) {
    	ss.Write("add");
    	ss.Write(""+x);
    	ss.Write(""+y);
    }
    void wrwin() {
    	ss.Write("win");
    }
    void wrlost() {
    	ss.Write("lost");
    }
    void wrend() {
    	ss.Write("end");
    }
    void peekmsg() {
    	String v;
    	int xx,yy;
    	boolean lh=false,rh=false;
    	while(true) {
        	v=ss.readLine();
        	if(v=="") {
        		dwait=true;
        		break;
        	}else {
        		dwait=false;
        	}
        	if(v.length()>1&&v.charAt(0)=='a'&&v.charAt(1)=='t') {
        		drx=Integer.parseInt(ss.readLine());
        		dry=Integer.parseInt(ss.readLine());
        	}
        	if(v.length()>2&&v.charAt(0)=='d'&&v.charAt(1)=='e'&&v.charAt(2)=='l') {
        		xx=Integer.parseInt(ss.readLine());
        		yy=Integer.parseInt(ss.readLine());
        		if(lh&&xx==rx&&yy==ry) {
        			if(ry!=1&&a[rx][ry-1]!=1) {
        				lh=false;
        				ry--;
        			}
        		}
        		if(rh&&xx==rx&&yy==ry) {
        			if(ry!=m&&a[rx][ry+1]!=1) {
        				rh=false;
        				ry++;
        			}
        		}
        		a[xx][yy]=0;
        	}
        	if(v.length()>2&&v.charAt(0)=='a'&&v.charAt(1)=='d'&&v.charAt(2)=='d') {
        		xx=Integer.parseInt(ss.readLine());
        		yy=Integer.parseInt(ss.readLine());
        		a[xx][yy]=1;
        	}
        	if(v.length()>2&&v.charAt(0)=='w'&&v.charAt(1)=='i'&&v.charAt(2)=='n') {
        		dwin=true;
        	}
        	if(v.length()>3&&v.charAt(0)=='l'&&v.charAt(1)=='o'&&v.charAt(2)=='s'&&v.charAt(3)=='t') {
        		dlost=true;
        	}
        	if(v.length()>3&&v.charAt(0)=='l'&&v.charAt(1)=='h'&&v.charAt(2)=='i'&&v.charAt(3)=='t') {
        		lh=true;
        	}
        	if(v.length()>3&&v.charAt(0)=='r'&&v.charAt(1)=='h'&&v.charAt(2)=='i'&&v.charAt(3)=='t') {
        		rh=true;
        	}
    		if(v.length()>2&&v.charAt(0)=='e'&&v.charAt(1)=='n'&&v.charAt(2)=='d') {
    			break;
    		}
    	}
    }
    public MyPanel(String s){
    	Scanner fin;
    	try {
			fin=new Scanner(new File("./"+s));
		} catch (FileNotFoundException e) {
			System.out.println("应用找不到关键文件。");
			return;
		}
    	Scanner fin2;
    	try {
			fin2=new Scanner(new File("./serv.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("应用找不到关键文件。");
			return;
		}
    	ss=new SocketService(fin2.nextLine());
    	n=fin.nextInt();
    	m=fin.nextInt();
    	k=fin.nextInt();
    	a=new int[n+3][m+3];
    	for(int i=1;i<=n;i++) {
    		for(int j=1;j<=m;j++) {
    			a[i][j]=fin.nextInt();
    		}
    	}
    	win=false;
    	lost=false;
    	jo=false;
    	dwait=false;
		fq=0;
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
    	rx=opx;
    	ry=opy;
    	drx=opx;
    	dry=opy;
	}
}