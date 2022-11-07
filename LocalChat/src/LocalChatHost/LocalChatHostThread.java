package LocalChatHost;

import LocalChat.*;

import java.io.*;
import java.net.Socket;
import java.util.Iterator;

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
      System.out.println("clientIP: " + clientIP);
      ChatUser user = host.users.getOrDefault(clientIP, null);
      LocalChatRequest request = LocalChatRequest.deserialize(socket);
      LocalChatResponse response;
      if (request instanceof MessageRequest messageRequest){
        ChatRoom chatRoom = host.chatRooms.getOrDefault(messageRequest.chatID, null);
        if (user == null) {
          response = new User404Response();
          System.err.println("Send message: user not found");
        } else if (chatRoom == null){
          response = new ChatRoom404Response();
          System.err.println("Send message: chat room not found");
        } else if (chatRoom.users.contains(user)) {
          response = new MessageResponse();
          new AlertChatUserThread(chatRoom.users.iterator(), user, new IncomingMessageRequest(user.userName, messageRequest.message, messageRequest.chatID));
          System.out.println("Send message: " + user.userName + " sent " + messageRequest.message + " to " + chatRoom.name);
        } else {
          response = new UserNotInChatResponse();
          System.err.println("Send message: " + user.userName + " is not in " + chatRoom.name);
        }
      } else if (request instanceof ChatRoomJoinRequest chatRoomJoinRequest){
        if (user == null) {
          response = new User404Response();
          System.err.println("Join chat room: user not found");
        } else {
          ChatRoom chatRoom = host.chatRooms.getOrDefault(chatRoomJoinRequest.chatID, null);
          if (chatRoom == null) {
            response = new ChatRoom404Response();
            System.err.println("Join chat room: chat room not found");
          } else if (chatRoom.users.contains(user)){
            response = new UserInChatResponse();
            System.err.println("Join chat room: user already in room");
          } else {
            System.out.println("JoinRequest chat name:" + chatRoom.name + ", username:" + user.userName);
            response = new ChatRoomJoinResponse(chatRoom.name);
            chatRoom.users.add(user);
            user.chatRooms.add(chatRoom);
            System.out.println("Join chat room: " + user.userName + " joined " + chatRoom.name);
          }
        }
      } else if (request instanceof ChatRoomCreateRequest createRequest) {
        if (user == null) {
          response = new User404Response();
          System.err.println("Create chat room: user not found");
        } else {
          ChatRoom room = new ChatRoom(createRequest.name);
          room.users.add(user);
          user.chatRooms.add(room);
          host.chatRooms.put(nextChatID, room);
          response = new ChatRoomCreateResponse(nextChatID);
          nextChatID++;
          System.out.println("Create chat room: " + user.userName + " created " + room.name);
        }
      } else if (request instanceof UserJoinRequest userJoinRequest) {
        if (user == null) {
          ChatUser newUser = new ChatUser(clientIP, userJoinRequest.port, userJoinRequest.userName);
          host.users.put(clientIP, newUser);
          response = new UserJoinResponse();
          System.out.println("New user: " + newUser.userName + " at " + newUser.ipAddress + ":" + newUser.port);
        } else{
          response = new UserExistsResponse();
          System.err.println("New user: " + user.userName + " already exists at " + user.ipAddress + ":" + user.port);
        }
      } else if (request instanceof ChatRoomLeaveRequest leaveRequest) {
        if (user == null) {
          response = new User404Response();
          System.err.println("Leave chat room: user doesn't exist");
        } else {
          ChatRoom room = host.chatRooms.getOrDefault(leaveRequest.chatID, null);
          if (room == null) {
            response = new ChatRoom404Response();
            System.err.println("Leave chat room: chat room doesn't exist");
          } else if (room.users.contains(user)) {
            room.users.remove(user);
            user.chatRooms.remove(room);
            response = new ChatRoomLeaveResponse();
            System.out.println("Leave chat room: " + user.userName + " left " + room.name);
          } else {
            response = new UserNotInChatResponse();
            System.err.println("Leave chat room: " + user.userName + " is not in " + room.name);
          }
        }
      } else if(request instanceof UserLeaveRequest) {
        if (user == null) {
          response = new User404Response();
          System.err.println("Leave user: user doesn't exist");
        } else {
          for (Iterator<ChatRoom> it = user.chatRooms.iterator(); it.hasNext(); ) {
            ChatRoom room = it.next();
            room.users.remove(user);
          }
          host.users.remove(clientIP);
          response = new UserLeaveResponse();
          System.out.println("Leave user: " + user.userName + " left the server");
        }
      } else if (request instanceof ShutdownRequest) {
        if (user == null) {
          response = new User404Response();
          System.err.println("Shut down: user not found");
        } else {
          response = new ShutdownResponse();
          new AlertShutdownThread(host.users.values().iterator(), user).start();
          System.out.println("Shutdown: successful request by " + user.userName);
        }
      } else {
        response = new UnknownResponse();
        if (user == null) {
          System.err.println("Unknown request from anonymous user");
        } else {
          System.err.println("Unknown request from " + user.userName);
        }
      }
      response.serialize(socket);
      socket.close();
    } catch (IOException | ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }
}
