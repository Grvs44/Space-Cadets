import javax.swing.*;
import java.awt.*;
import java.awt.geom.GeneralPath;

public class SpirographViewer extends JFrame {
  private final int R;
  private final int length;
  private final int O;
  private final int r;

  public SpirographViewer(int R, int length, int O, int r) {
    super("Spirograph Viewer");
    this.R = R;
    this.length = length;
    this.O = O;
    this.r = r;
    setSize(500, 500);
    setResizable(false);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    setVisible(true);
  }

  public void paint(Graphics g) {
    Graphics2D graphics = (Graphics2D) g;
    //int R = 100;//10
    //int length = 1000;//1000
    //int O = 100;//10
    //int r = 1;//1
    int t = 0;//variable
    int belowZero = 0;
    int aboveZero = 0;
    GeneralPath polyline = new GeneralPath(GeneralPath.WIND_EVEN_ODD, length);
    polyline.moveTo((R-r)*Math.cos(t) + O*Math.cos(((float)(R-r)/r)*t), (R-r)*Math.sin(t) - O*Math.sin(((float)(R-r)/r)*t));
    for (t = 1; t < length; t++) {
      polyline.lineTo(
          (R-r)*Math.cos(t) + O*Math.cos(((double)(R-r)/r)*t) + 250,
          (R-r)*Math.sin(t) - O*Math.sin(((double)(R-r)/r)*t) + 250
      );
    }
    graphics.draw(polyline);
    System.out.println(aboveZero);
    System.out.println(belowZero);
  }
}
