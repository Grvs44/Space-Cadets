package LocalChatJoin;

import LocalChat.*;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.Socket;

public class LocalChatJoinListener extends LocalChatWindowListener {
  public LocalChatJoinListener(LocalChatJoin localChatJoin) {
    super(localChatJoin);
  }

  public void windowClosing(WindowEvent event) {
    try (Socket socket = new Socket(localChatJoin.hostIP, localChatJoin.hostPort)) {
      new UserLeaveRequest().serialize(socket);
      LocalChatResponse response = LocalChatResponse.deserialize(socket);
      if (response instanceof UserLeaveResponse) {
        System.out.println("Left server");
      } else {
        JOptionPane.showMessageDialog(localChatJoin, "Issue with leaving server", "LocalChatJoin", JOptionPane.ERROR_MESSAGE);
      }
    } catch (ClassNotFoundException | IOException e) {
      throw new RuntimeException(e);
    }
  }
}
