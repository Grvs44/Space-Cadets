package LocalChatJoin;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public abstract class LocalChatWindowListener implements WindowListener {
  protected final LocalChatJoin localChatJoin;

  public LocalChatWindowListener(LocalChatJoin localChatJoin){
    this.localChatJoin = localChatJoin;
  }

  @Override
  public void windowOpened(WindowEvent e) {
  }

  @Override
  public void windowClosed(WindowEvent e) {
  }

  @Override
  public void windowIconified(WindowEvent e) {
  }

  @Override
  public void windowDeiconified(WindowEvent e) {
  }

  @Override
  public void windowActivated(WindowEvent e) {
  }

  @Override
  public void windowDeactivated(WindowEvent e) {
  }
}
