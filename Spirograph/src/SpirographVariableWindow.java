import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SpirographVariableWindow extends JFrame implements ActionListener {

  public static void main(String[] args) {
    new SpirographVariableWindow();
    new SpirographViewer(100, 1000, 100, 1);
  }

  public SpirographVariableWindow() {
    super("Spirograph");setSize(500, 500);
    setResizable(false);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setVisible(true);
  }

  public void actionPerformed(ActionEvent e) {

  }
}
