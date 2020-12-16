
public class Triangle {
	private Point p1,p2,p3;
	private boolean draw;

	public Triangle(Point p1, Point p2, Point p3) {
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
		this.draw=true;
	}
	
	public Point getP1() {
		return p1;
	}

	public void setP1(Point p1) {
		this.p1 = p1;
	}

	public Point getP2() {
		return p2;
	}

	public void setP2(Point p2) {
		this.p2 = p2;
	}

	public Point getP3() {
		return p3;
	}

	public void setP3(Point p3) {
		this.p3 = p3;
	}
	
	public boolean getDraw() {
		return draw;
	}

	public void setDraw(boolean draw) {
		this.draw = draw;
	}

	public int[] getXPoints(){
		int[] array= {(int) p1.getX(),(int) p2.getX(),(int) p3.getX()};
		return array;
	}
	
	public int[] getYPoints(){
		int[] array= {(int) p1.getY(),(int) p2.getY(),(int) p3.getY()};
		return array;
	}
	
	public Point[] getPoints() {
		Point[] points= {p1,p2,p3};
		return points;
	}
	
	public double getSize() {
		return Math.abs(p2.getX()-p3.getX());
	}
	
	
}
