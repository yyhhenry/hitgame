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

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class DrawSee extends JFrame implements KeyListener{
	private static final long serialVersionUID = 111111L;
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
	private static final long serialVersionUID = 111112L;
	private int[][]a;
	private int n,m,k;
	private int rx,ry,fl,fq,rdt,opx,opy,edx,edy;
	private boolean win,lost,jo;
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
			if(ry==1||a[rx][ry-1]!=1)return;
			a[rx][ry-1]=0;
		}
    	if(win)return;
    	if(lost)return;
		if(fdd['i']){
			if(ry==m||a[rx][ry+1]!=1)return;
			a[rx][ry+1]=0;
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
		}
    }
    public void react() {
    	rekt();
    	if(a[rx][ry]==2) {
    		lost=true;
    	}
    	if(lost)return;
    	if(rx==edx&&ry==edy) {
    		win=true;
    	}
    	if(win)return;
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
    }
    public MyPanel(String s){
    	Scanner fin;
    	try {
			fin=new Scanner(new File("./"+s));
		} catch (FileNotFoundException e) {
			System.out.println("应用找不到关键文件。");
			return;
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
    	win=false;
    	lost=false;
    	jo=false;
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
	}
}