package LocalChatHost;

import LocalChat.*;

import java.io.*;
import java.net.Socket;

public class LocalChatHostThread extends Thread {
  private final Socket socket;
  private final LocalChatHost host;
  private static int nextChatID = 0;

  public LocalChatHostThread(Socket socket, LocalChatHost host) {
    this.socket = socket;
    this.host = host;
  }

  public void run() {
    try {
      String clientIP = socket.getInetAddress().getHostAddress();
      ChatUser user = host.users.getOrDefault(clientIP, null);
      LocalChatRequest request = LocalChatRequest.deserialize(socket);
      LocalChatResponse response;
      if (request instanceof MessageRequest messageRequest){
        ChatRoom chatRoom = host.chatRooms.getOrDefault(messageRequest.chatID, null);
        if (user == null) {
          response = new User404Response();
        } else if (chatRoom == null){
          response = new ChatRoom404Response();
        } else if (chatRoom.users.contains(user)) {
          System.out.println("\n{" + "} [" + user.userName + "] " + messageRequest.message);
          response = new MessageResponse();
          new AlertChatUserThread(chatRoom.users.iterator(), user, new IncomingMessageRequest(user.userName, messageRequest.message));
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
          } else if (chatRoom.users.contains(user)){
            response = new UserInChatResponse();
          } else {
            System.out.println("JoinRequest chat name:" + chatRoom.name + ", username:" + user.userName);
            response = new ChatRoomJoinResponse(chatRoom.name);
            chatRoom.users.add(user);
            user.chatRooms.add(chatRoom);
          }
        }
      } else if (request instanceof ChatRoomCreateRequest createRequest) {
        if (user == null) {
          response = new User404Response();
        } else {
          ChatRoom room = new ChatRoom(createRequest.name);
          room.users.add(user);
          user.chatRooms.add(room);
          host.chatRooms.put(nextChatID, room);
          response = new ChatRoomCreateResponse(nextChatID);
          nextChatID++;
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
          new AlertShutdownThread(host.users.values().iterator(), user).start();
        }
      } else {
        System.out.println("Unknown request");
        response = new UnknownResponse();
      }
      response.serialize(socket);
      socket.close();
    } catch (IOException | ClassNotFoundException e) {
      //System.out.println("Error: " + e.getMessage());
      throw new RuntimeException(e);
    }
  }
}
