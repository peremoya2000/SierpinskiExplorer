import javax.swing.JFrame;

public class ExploratorMain extends JFrame{
	
	public static void main(String[] args) {
		ExploratorMain e= new ExploratorMain();
	}
	public ExploratorMain() {
		short width=800;
		short height=800;
		Input i= new Input();
		Viewer v= new Viewer(width, height, i);
		this.add(v);
        this.setSize(width, height);        
        this.setVisible(true);
        this.setFocusable(true);
        this.requestFocus();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.addKeyListener(i);
        v.init();
	}

}
