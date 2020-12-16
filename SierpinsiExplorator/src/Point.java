
public class Point {
	private double x,y;
	Point(){}
	Point(double x, double y){
		this.x=x;
		this.y=y;
	}
	public Point midpoint(Point other) {
		return new Point((this.x+other.getX())/2,((this.y+other.getY())/2));
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public int getScaledX(short halfWidth, long scale, float xoffset) {
		return (int) ((this.x+xoffset)*scale+halfWidth);
	} 
	public int getScaledY(short halfHeight, long scale, float yoffset) {
		return (int) ((this.y+yoffset)*scale+halfHeight);
	}
	
	
}
