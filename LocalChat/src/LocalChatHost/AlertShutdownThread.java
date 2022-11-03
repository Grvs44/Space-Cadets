package LocalChatHost;

import LocalChat.LocalChatRequest;

import java.util.Iterator;

public class AlertShutdownThread extends AlertChatUserThread {
  public AlertShutdownThread(Iterator<ChatUser> users, ChatUser noAlertUser, LocalChatRequest alertRequest) {
    super(users, noAlertUser, alertRequest);
  }

  @Override
  public void run() {
    super.run();
    System.exit(0);
  }
}
