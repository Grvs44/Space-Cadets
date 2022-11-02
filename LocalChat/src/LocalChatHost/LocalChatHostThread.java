package LocalChatHost;

import LocalChat.*;

import java.io.*;
import java.net.Socket;

public class LocalChatHostThread extends Thread {
  private Socket socket;

  public LocalChatHostThread(Socket socket) {
    this.socket = socket;
  }

  public void run() {
    try {
      LocalChatRequest request = LocalChatRequest.deserialize(socket.getInputStream());
      LocalChatResponse response;
      if(request instanceof MessageRequest){
        MessageRequest messageRequest = (MessageRequest) request;
        System.out.println("MessageRequest id: " + messageRequest.getUserID() + ", message:" + messageRequest.getMessage());
        response = new MessageResponse();
      } else if(request instanceof JoinRequest){
        JoinRequest joinRequest = (JoinRequest) request;
        System.out.println("JoinRequest id:" + joinRequest.getChatID() + ", username:" + joinRequest.getUserName());
        response = new JoinResponse(true, 1);
      } else {
        System.out.println("Unknown request");
        response = new UnknownResponse();
      }
      response.serialize(socket.getOutputStream());
      socket.close();
    } catch (IOException | ClassNotFoundException e) {
      System.out.println("Error: " + e.getMessage());
    }
  }
}
