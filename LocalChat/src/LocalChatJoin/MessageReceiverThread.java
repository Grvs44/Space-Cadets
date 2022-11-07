package LocalChatJoin;

import LocalChat.*;

import java.io.IOException;
import java.net.Socket;

public class MessageReceiverThread extends Thread {
  private final Socket socket;
  private final LocalChatJoin localChatJoin;

  public MessageReceiverThread(Socket socket, LocalChatJoin localChatJoin) {
    this.socket = socket;
    this.localChatJoin = localChatJoin;
  }

  public void run() {
    try {
      LocalChatRequest request = LocalChatRequest.deserialize(socket);
      if (request instanceof IncomingMessageRequest messageRequest) {
        ChatWindow chatWindow = localChatJoin.chatWindows.getOrDefault(messageRequest.chatID, null);
        if (chatWindow == null) {
          System.err.println("The current user isn't registered in chat room " + messageRequest.chatID);
        } else {
          System.out.println("Incoming message");
          chatWindow.addMessage(messageRequest.userName, messageRequest.message);
        }
      }
      socket.close();
    } catch (IOException | ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }
}
