import java.util.ArrayList;
import java.util.Iterator;

public class ASTNode {
  private String label;
  private ArrayList<ASTNode> children = new ArrayList<>();
  private ASTNodeType type;

  public ASTNode(String label, ASTNodeType type) {
    this.label = label;
    this.type = type;
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
    ASTNode clearVariable = new ASTNode("(?<name>*)", ASTNodeType.variable);
    clear.addChild(clearVariable);
    root.addChild(clear);

    ASTNode incr = new ASTNode("incr", ASTNodeType.incr);
    ASTNode incrVariable = new ASTNode("(?<name>*)", ASTNodeType.variable);
    incr.addChild(incrVariable);
    root.addChild(incr);

    ASTNode decr = new ASTNode("decr", ASTNodeType.decr);
    ASTNode decrVariable = new ASTNode("(?<name>*)", ASTNodeType.variable);
    decr.addChild(decrVariable);
    root.addChild(decr);

    ASTNode whileStart = new ASTNode("while", ASTNodeType.while_start);
    ASTNode whileVariable = new ASTNode("(?<name>*)", ASTNodeType.variable);
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
