import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ASTNode {
  private String label;
  private ArrayList<ASTNode> children = new ArrayList<>();
  private ASTNodeType type;

  public ASTNode(String label, ASTNodeType type) {
    this.label = label;
    this.type = type;
  }

  public ArrayList<ASTNode> match(String[] line) {
    ArrayList<ASTNode> nodeList = new ArrayList<>();
    Iterator<ASTNode> children = this.getChildren();
    for (int i = 0; i < line.length; i++) {
      System.out.println("Checking line " + i + ": " + line[i]);
      boolean found = false;
      for (Iterator<ASTNode> it = children; it.hasNext(); ) {
        ASTNode node = it.next();
        Pattern pattern = Pattern.compile(node.label);
        Matcher matcher = pattern.matcher(line[i]);
        System.out.println("Testing node: " + node.label);
        if (matcher.find()){
          System.out.println("Count: " + matcher.groupCount());
          System.out.println("Group: " + matcher.group(1));
        }
        if (matcher.matches()) {
          children = node.getChildren();
          found = true;
          System.out.println("Found node: " + node.label);
          break;
        }
      }
      if(!found) {
        return null;
      }
    }
    return nodeList;
  }

  public void addChild(ASTNode child) {
    children.add(child);
  }

  public Iterator<ASTNode> getChildren() {
    return children.iterator();
  }

  public ASTNodeType getType() {return this.type;}

  public static ASTNode makeRootNode() {
    ASTNode root = new ASTNode(null, ASTNodeType.root_node);

    ASTNode clear = new ASTNode("clear", ASTNodeType.clear);
    ASTNode clearVariable = new ASTNode("*(?<name>+)", ASTNodeType.variable);
    clear.addChild(clearVariable);
    root.addChild(clear);

    ASTNode incr = new ASTNode("incr", ASTNodeType.incr);
    ASTNode incrVariable = new ASTNode("(?<name>)", ASTNodeType.variable);
    incr.addChild(incrVariable);
    root.addChild(incr);

    ASTNode decr = new ASTNode("decr", ASTNodeType.decr);
    ASTNode decrVariable = new ASTNode("(?<name>)", ASTNodeType.variable);
    decr.addChild(decrVariable);
    root.addChild(decr);

    ASTNode whileStart = new ASTNode("while", ASTNodeType.while_start);
    ASTNode whileVariable = new ASTNode("(?<name>)", ASTNodeType.variable);
    ASTNode not = new ASTNode("not", ASTNodeType.not);
    ASTNode whileNotVariable = new ASTNode("(?<name>*)", ASTNodeType.variable);
    whileStart.addChild(whileNotVariable);
    whileStart.addChild(not);
    not.addChild(whileVariable);
    root.addChild(whileStart);

    ASTNode whileEnd = new ASTNode("end", ASTNodeType.while_end);
    root.addChild(whileEnd);

    return root;
  }
}
