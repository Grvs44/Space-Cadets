package LocalChatJoin;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class MessageReceiver extends Thread {
  private final LocalChatJoin localChatJoin;

  public MessageReceiver(LocalChatJoin localChatJoin) {
    this.localChatJoin = localChatJoin;
  }

  public void run() {
    try (ServerSocket serverSocket = new ServerSocket(localChatJoin.clientPort)) {
      System.out.println("LocalChatJoin listening for message on " + InetAddress.getLocalHost().getHostAddress() +  ":" + localChatJoin.clientPort);
      while (true) {
        Socket socket = serverSocket.accept();
        System.out.println("Incoming request");
        new MessageReceiverThread(socket, localChatJoin).start();
      }
    } catch (IOException e) {
      System.out.println(e);
      e.printStackTrace();
    }
  }
}
