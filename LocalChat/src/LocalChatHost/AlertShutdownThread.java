package LocalChatHost;

import LocalChat.ServerShuttingDownRequest;

import java.util.Iterator;

public class AlertShutdownThread extends AlertChatUserThread {
  public AlertShutdownThread(Iterator<ChatUser> users, ChatUser noAlertUser) {
    super(users, noAlertUser, new ServerShuttingDownRequest());
  }

  @Override
  public void run() {
    super.run();
    System.exit(0);
  }
}
