package LocalChatHost;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyException;

public class LocalChatHost {
  public static void main(String args[]) {
    int port = 8000;
    boolean output = false;
    int index = 0;
    while (index < args.length) {
      if (args[index].equals("-o")) {
        output = true;
      } else if (args[index].equals("-p")) {
        index++;
        try{
          port = Integer.parseInt(args[index]);
        } catch (NumberFormatException e) {}
      }
      index++;
    }
    new LocalChatHost(port, output);
  }

  public LocalChatHost(int port, boolean output) {
    try (ServerSocket serverSocket = new ServerSocket(port)) {
      System.out.println("LocalChatHost running on " + InetAddress.getLocalHost().getHostAddress() +  ":" + port);
      while (true) {
        Socket socket = serverSocket.accept();
        System.out.println("Incoming request");
        new LocalChatHostThread(socket).start();
      }
    } catch (IOException e) {
      System.out.println(e);
    }
  }
}
