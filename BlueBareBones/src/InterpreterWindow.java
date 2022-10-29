import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

class InterpreterWindow extends JFrame implements ActionListener {
  private final JPanel panel = new JPanel();
  private final JTextArea codeBox = new JTextArea(5, 40);
  private final JTextArea outBox = new JTextArea(5, 40);
  private final JScrollPane codeBoxScroll = new JScrollPane(codeBox);
  private final JScrollPane outBoxScroll = new JScrollPane(outBox);
  private final JButton runButton = new JButton("Run");
  private final JButton stepButton = new JButton("Step");
  private ASTNode AST = ASTNode.makeRootNode();

  public InterpreterWindow(String path) {
    super(((path == null)? "Untitled": path) + " - BlueBareBones");
    setSize(500, 500);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    runButton.addActionListener(this);
    stepButton.addActionListener(this);
    panel.add(runButton);
    panel.add(codeBoxScroll);
    panel.add(stepButton);
    panel.add(outBoxScroll);
    add(panel);
    setVisible(true);
  }

  public void actionPerformed(ActionEvent event) {
    JButton source = (JButton) event.getSource();
    if (source == runButton) run();
    else if (source == stepButton) step();
    System.out.println(source.getParent().getParent().getParent().getParent().getParent() == this);
  }

  private void run() {
    String[] code = codeBox.getText().split(";");
    for(String line : code) System.out.println(line);
  }

  private void step() {
    System.out.println("Clicked step");
  }
}
