import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SpirographVariableWindow extends JFrame implements ActionListener {
  private final JTextField RField = new JTextField(10);
  private final JTextField lengthField = new JTextField(10);
  private final JTextField OField = new JTextField(10);
  private final JTextField rField = new JTextField(10);

  public static void main(String[] args) {
    new SpirographVariableWindow();
  }

  public SpirographVariableWindow() {
    super("Spirograph");
    setSize(630, 80);
    setResizable(false);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    JButton button = new JButton("Run");
    button.addActionListener(this);
    RField.setText("100");
    lengthField.setText("1000");
    OField.setText("100");
    rField.setText("1");
    JPanel panel = new JPanel();
    panel.add(new JLabel("R"));
    panel.add(RField);
    panel.add(new JLabel("length"));
    panel.add(lengthField);
    panel.add(new JLabel("O"));
    panel.add(OField);
    panel.add(new JLabel("r"));
    panel.add(rField);
    panel.add(button);
    add(panel);
    setVisible(true);
  }

  public void actionPerformed(ActionEvent e) {
    try {
      new SpirographViewer(
          Integer.parseInt(RField.getText()),
          Integer.parseInt(lengthField.getText()),
          Integer.parseInt(OField.getText()),
          Integer.parseInt(rField.getText())
      );
    } catch (NumberFormatException error) {
      System.err.println("Inputs must be numbers");
    }
  }
}
