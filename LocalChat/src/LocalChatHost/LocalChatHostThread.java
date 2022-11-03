package LocalChatHost;

import LocalChat.*;

import java.io.*;
import java.net.Socket;

public class LocalChatHostThread extends Thread {
  private Socket socket;
  private LocalChatHost host;

  public LocalChatHostThread(Socket socket, LocalChatHost host) {
    this.socket = socket;
    this.host = host;
  }

  public void run() {
    try {
      String clientIP = socket.getInetAddress().getHostAddress();
      ChatUser user = host.users.getOrDefault(clientIP, null);
      LocalChatRequest request = LocalChatRequest.deserialize(socket.getInputStream());
      LocalChatResponse response;
      if (request instanceof MessageRequest messageRequest){
        ChatRoom chatRoom = host.chatRooms.getOrDefault(messageRequest.chatID, null);
        if (user == null) {
          response = new User404Response();
        } else if (chatRoom == null){
          response = new ChatRoom404Response();
        } else if (chatRoom.getUsers().contains(user)) {
          System.out.println("\n{" + "} [" + user.userName + "] " + messageRequest.message);
          response = new MessageResponse();
          new AlertChatUserThread(chatRoom.getUsers().iterator(), user, new IncomingMessageRequest(user.userName, messageRequest.message));
        } else {
          response = new UserNotInChatResponse();
        }
      } else if (request instanceof ChatRoomJoinRequest chatRoomJoinRequest){
        if (user == null) {
          response = new User404Response();
        } else {
          ChatRoom chatRoom = host.chatRooms.getOrDefault(chatRoomJoinRequest.chatID, null);
          if (chatRoom == null) {
            response = new ChatRoom404Response();
          } else if (chatRoom.getUsers().contains(user)){
            response = new UserInChatResponse();
          } else {
            System.out.println("JoinRequest id:" + chatRoomJoinRequest.chatID + ", username:" + chatRoomJoinRequest.userName);
            response = new ChatRoomJoinResponse();
          }
        }
      } else if (request instanceof UserJoinRequest userJoinRequest) {
        if (user == null) {
          host.users.put(clientIP, new ChatUser(clientIP, userJoinRequest.port, userJoinRequest.userName));
          response = new UserJoinResponse();
        } else{
          response = new UserExistsResponse();
        }
      } else if (request instanceof ShutdownRequest) {
        if (user == null) {
          response = new User404Response();
        } else {
          response = new ShutdownResponse();
          new AlertShutdownThread(host.users.values().iterator(), user, new ServerShuttingDownRequest()).start();
        }
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
