import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Viewer extends JPanel implements Runnable{
	private static final long serialVersionUID = -5089726768418513408L;
	private final Thread myThread;
	private BufferedImage img;
	private short width, height,halfWidth,halfHeight, sleep, range, counter;
	private long scale;
	private Graphics2D g2img;
	private Input input;
	private float xoffset,yoffset;
	private ArrayList<Triangle> tris;
	public Viewer(short width, short height, Input input) {
		this.width=width;
		this.height=height;
		this.halfWidth=(short) (width/2-5);
		this.halfHeight=(short) (height/2-20);
		this.scale=height/15;
		this.tris = new ArrayList<Triangle> ();
		this.myThread=new Thread(this);
		this.input = input;
		this.sleep=4;
		this.range=3;
		this.counter=0;
	}
	
	public void init() {
		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		g2img = img.createGraphics();
		g2img.setColor(Color.DARK_GRAY);
		myThread.start();
	}
	
	public boolean generate() {	
		if(tris.get(tris.size()-1).getSize()*scale>4.5) {
			return true;
		}else {
			return false;
		}
	}
	@Override
	public void run() {
		generateTriangles(new Point(0,-5),new Point(-5,5),new Point(5,5),12);
		while (true) {
			float xspd=0;
			float yspd=0;
			if (input.GetKey(KeyEvent.VK_A)) {
				xspd+=0.1/scale*0.8f;
			}
			if (input.GetKey(KeyEvent.VK_D)) {
				xspd-=0.1/scale*0.8f;
			}
			if (input.GetKey(KeyEvent.VK_S)) {
				yspd-=0.1/scale*0.8f;
			}
			if (input.GetKey(KeyEvent.VK_W)) {
				yspd+=0.1/scale*0.8f;
			}
			xoffset+=xspd;
			yoffset+=yspd;
			if(generate()) {
				int size= tris.size();
				for (int i=0; i<size; ++i) {
					Triangle t = tris.get(i);
					generateTriangles(t.getP1(),t.getP2(),t.getP3(),range);
				}
			}
			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			++scale;
			scale*=1.002;
			myPaint();
			garbageCollector();
		}
	}
	
	public void generateTriangles(Point p1, Point p2, Point p3, int iterations) {
		if(iterations==1) {	
			Triangle nt = new Triangle(p1,p2,p3);
			tris.add(nt);
		}else {
			Point n1,n2,n3;
			n1=p1.midpoint(p2);
			n2=p2.midpoint(p3);
			n3=p1.midpoint(p3);
			generateTriangles(p1, n1, n3, --iterations);
			generateTriangles(n1, p2, n2, iterations);
			generateTriangles(n3, n2, p3, iterations);
		}

	}
	
	public void paintComponent(Graphics g) {}
	
	public void myPaint() {
		g2img.setColor(Color.DARK_GRAY);
		Graphics2D g2 = (Graphics2D) this.getGraphics();
		g2img.fillRect(0, 0, width, height);
		g2img.setColor(Color.WHITE);
		for(Triangle t: tris) {
			Point[] points= t.getPoints();
			int[] xpoints= new int[3];
			int[] ypoints= new int[3];
			for(int i=0; i<3; ++i) {
				xpoints[i]=points[i].getScaledX(halfWidth, scale,xoffset);
				ypoints[i]=points[i].getScaledY(halfHeight, scale, yoffset);
			}
			if(onScreen(xpoints,ypoints)) {
				t.setDraw(true);
				g2img.drawPolygon(xpoints, ypoints, 3);
			}else {
				t.setDraw(false);
			}		
		}		
		g2.drawImage(img, 0, 0, null);
	}
	
	public boolean onScreen(int[] xpoints, int[] ypoints) {
		int xmax=(Math.max(xpoints[0], Math.max(xpoints[1],xpoints[2])));
		int ymax=(Math.max(ypoints[0], Math.max(ypoints[1],ypoints[2])));
		int xmin=(Math.min(xpoints[0], Math.min(xpoints[1],xpoints[2])));
		int ymin=(Math.min(ypoints[0], Math.min(ypoints[1],ypoints[2])));
		if(xmax<0 || xmin>width || ymin>height || ymax<0) {
			return false;
		}else {
			return true;
		}	
	}	
	
	public void garbageCollector() {
		if(++counter>40) {
			counter=0;
			Triangle t;
			ArrayList<Triangle> temp = new ArrayList<Triangle>();
			int size=(int) tris.size();
			for (int i=0; i<size; ++i) {
				t=tris.get(i);
				if(t.getDraw()) {
					temp.add(t);
				}
			}
			tris=temp;
		}
	}
}
