import javax.swing.*;
import java.awt.*;
import java.awt.geom.GeneralPath;

public class Spirograph extends JFrame {
  public static void main(String[] args) {
    new Spirograph();
  }

  public Spirograph() {
    super("Spirograph");
    setSize(500, 500);
    setResizable(false);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setVisible(true);
  }

  public void paint(Graphics g) {
    Graphics2D graphics = (Graphics2D) g;
    int R = 10;
    int length = 1000;
    int O = 10;
    int r = 1;
    int t = 0;
    GeneralPath polyline = new GeneralPath(GeneralPath.WIND_EVEN_ODD, length);
    polyline.moveTo((R-r)*Math.cos(t) + O*Math.cos(((float)(R-r)/r)*t), (R-r)*Math.sin(t) - O*Math.sin(((float)(R-r)/r)*t));
    for (t = 1; t < length; t++) {
      polyline.lineTo((R-r)*Math.cos(t) + O*Math.cos(((float)(R-r)/r)*t), (R-r)*Math.sin(t) - O*Math.sin(((float)(R-r)/r)*t));
    }
    graphics.draw(polyline);
  }
}
