package LocalChatJoin;

import LocalChat.*;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.Socket;

public class ChatWindowListener extends LocalChatWindowListener {
  private final int chatID;
  public ChatWindowListener(LocalChatJoin localChatJoin, int chatID) {
    super(localChatJoin);
    this.chatID = chatID;
  }

  public void windowClosing(WindowEvent event) {
    try (Socket socket = new Socket(localChatJoin.hostIP, localChatJoin.hostPort)) {
      new ChatRoomLeaveRequest(chatID).serialize(socket);
      LocalChatResponse response = LocalChatResponse.deserialize(socket);
      if (response instanceof ChatRoomLeaveResponse) {
        System.out.println("Left chat room");
      } else {
        JOptionPane.showMessageDialog(localChatJoin, "Issue leaving chat room", "LocalChatJoin", JOptionPane.ERROR_MESSAGE);
      }
    } catch (ClassNotFoundException | IOException e) {
      throw new RuntimeException(e);
    }
  }
}
