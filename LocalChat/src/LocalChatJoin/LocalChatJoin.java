package LocalChatJoin;

import LocalChat.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

public class LocalChatJoin extends JFrame implements ActionListener {
  private final JPanel panel = new JPanel();
  private final JTextField chatIDField = new JTextField(20);
  private final JButton joinButton = new JButton("Join");
  private final JTextField chatNameField = new JTextField(20);
  private final JButton createButton = new JButton("Create");
  public final String hostIP;
  public final int hostPort;
  public final String userName;

  public static void main(String[] args) {
    if (args.length == 3) {
      try {
        new LocalChatJoin(args[0], Integer.parseInt(args[1]), args[2]);
      } catch (NumberFormatException e) {
        System.err.println("Port number (second argument) must be an integer");
      }
    } else {
      System.err.println("Must provide host IP address, and host port, and username as arguments in format host-IP host-port username");
    }
  }

  public LocalChatJoin(String hostIP, int hostPort, String userName) {
    super("LocalChatJoin");
    this.hostIP = hostIP;
    this.hostPort = hostPort;
    this.userName = userName;
    setSize(500, 500);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    joinButton.addActionListener(this);
    createButton.addActionListener(this);
    panel.add(chatIDField);
    panel.add(joinButton);
    panel.add(chatNameField);
    panel.add(createButton);
    add(panel);
    setVisible(true);
  }

  public void actionPerformed(ActionEvent event) {
    if (event.getSource() == joinButton) {
      try {
        sendJoinRequest();
      } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "LocalChatJoin", "The chat ID must be an integer", JOptionPane.ERROR_MESSAGE);
      }
    } else if (event.getSource() == createButton) {
      sendCreateRequest();
    }
  }

  private void sendJoinRequest() {
    int chatID = Integer.parseInt(chatIDField.getText());
    try (Socket socket = new Socket(hostIP, hostPort)) {
      new ChatRoomJoinRequest(chatID).serialize(socket.getOutputStream());
      LocalChatResponse response = LocalChatResponse.deserialize(socket.getInputStream());
      if (response instanceof ChatRoomJoinResponse joinResponse) {
        System.out.println("Success");
        new ChatWindow(this, chatID, joinResponse.name);
      }
    } catch (ClassNotFoundException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  private void sendCreateRequest() {
    String name = chatNameField.getText();
    if (name.isBlank()) return;
    try (Socket socket = new Socket(hostIP, hostPort)) {
      new ChatRoomCreateRequest(name).serialize(socket.getOutputStream());
      LocalChatResponse response = LocalChatResponse.deserialize(socket.getInputStream());
      if (response instanceof ChatRoomCreateResponse createResponse) {
        System.out.println("Success");
        new ChatWindow(this, createResponse.chatID, name);
      }
    } catch (ClassNotFoundException | IOException e) {
      throw new RuntimeException(e);
    }
  }
}
