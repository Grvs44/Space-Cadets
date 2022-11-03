package LocalChatHost;

import LocalChat.LocalChatRequest;
import LocalChat.ServerShuttingDownRequest;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;

public class AlertChatUserThread extends Thread {
  private final ChatUser noAlertUser;
  private final Iterator<ChatUser> users;
  private final LocalChatRequest alertRequest;

  public AlertChatUserThread(Iterator<ChatUser> users, ChatUser noAlertUser, LocalChatRequest alertRequest) {
    this.noAlertUser = noAlertUser;
    this.users = users;
    this.alertRequest = alertRequest;
  }

  public void run() {
    for (Iterator<ChatUser> it = users; it.hasNext(); ) {
      ChatUser user = it.next();
      if (user == noAlertUser) continue;
      try (Socket socket = new Socket(user.ipAddress, user.port)) {
        alertRequest.serialize(socket.getOutputStream());
      } catch (IOException e) {
        System.out.println("Error: " + e.getMessage());
      }
    }
  }
}
