package LocalChatJoin;

import LocalChat.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

public class LocalChatJoin extends JFrame implements ActionListener {
  private final JPanel panel = new JPanel();
  private final JTextArea messageBox = new JTextArea(5, 40);
  private final JTextArea sendBox = new JTextArea(5, 40);
  private final JScrollPane messageBoxScroll = new JScrollPane(messageBox);
  private final JScrollPane sendBoxScroll = new JScrollPane(sendBox);
  private final JButton sendButton = new JButton("Send");
  private int userID = 1;
  private int chatID = 1;

  public static void main(String[] args) {
    new LocalChatJoin();
  }

  public LocalChatJoin() {
    super("LocalChatJoin");
    setSize(500, 500);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    sendButton.addActionListener(this);
    panel.add(sendButton);
    panel.add(messageBoxScroll);
    panel.add(sendBoxScroll);
    add(panel);
    setVisible(true);
  }

  public void actionPerformed(ActionEvent event) {
    sendJoinRequest();
    sendMessage();
  }

  private void sendJoinRequest() {
    try (Socket socket = new Socket("localhost", 8000)) {
      new ChatRoomJoinRequest("Joe", chatID).serialize(socket.getOutputStream());
      LocalChatResponse response = LocalChatResponse.deserialize(socket.getInputStream());
      if (response instanceof ChatRoomJoinResponse) {
        ChatRoomJoinResponse chatRoomJoinResponse = (ChatRoomJoinResponse) response;
        System.out.println("Success: " + chatRoomJoinResponse.isSuccess() + ", userID: " + chatRoomJoinResponse.getUserID());
      }
    } catch (ClassNotFoundException | IOException e) {
      System.out.println("Error: " + e.getMessage());
    }
  }

  private void sendMessage() {
    try (Socket socket = new Socket("localhost", 8000)) {
      new MessageRequest(userID, chatID, this.sendBox.getText()).serialize(socket.getOutputStream());
      LocalChatResponse response = LocalChatResponse.deserialize(socket.getInputStream());
      if (response instanceof MessageResponse) {
        this.sendBox.setText("");
        System.out.println("Message received");
      } else {
        System.out.println("Issue sending message");
      }
    } catch (ClassNotFoundException | IOException e) {
      System.out.println("Error: " + e.getMessage());
    }
  }
}
