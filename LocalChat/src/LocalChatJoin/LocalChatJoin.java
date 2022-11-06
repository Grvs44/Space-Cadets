package LocalChatJoin;

import LocalChat.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

public class LocalChatJoin extends JFrame implements ActionListener {
  private final JPanel panel = new JPanel();
  private final JTextField userNameField = new JTextField(20);
  private final JButton userButton = new JButton("Create user");
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
    this.addWindowListener(new LocalChatJoinListener(this));
    userButton.addActionListener(this);
    joinButton.addActionListener(this);
    createButton.addActionListener(this);
    panel.add(userNameField);
    panel.add(userButton);
    panel.add(chatIDField);
    panel.add(joinButton);
    panel.add(chatNameField);
    panel.add(createButton);
    add(panel);
    setVisible(true);
  }

  public void actionPerformed(ActionEvent event) {
    Object source = event.getSource();
    if (source == joinButton) {
      try {
        sendJoinRequest();
      } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "The chat ID must be an integer", "LocalChatJoin", JOptionPane.ERROR_MESSAGE);
      }
    } else if (source == createButton) {
      sendCreateRequest();
    } else if (source == userButton) {
      sendUserJoinRequest();
    }
  }

  private void sendJoinRequest() {
    int chatID = Integer.parseInt(chatIDField.getText());
    try (Socket socket = new Socket(hostIP, hostPort)) {
      new ChatRoomJoinRequest(chatID).serialize(socket);
      LocalChatResponse response = LocalChatResponse.deserialize(socket);
      if (response instanceof ChatRoomJoinResponse joinResponse) {
        new ChatWindow(this, chatID, joinResponse.name);
      } else {
        JOptionPane.showMessageDialog(this, "Couldn't join chat room", "LocalChatJoin", JOptionPane.ERROR_MESSAGE);
      }
    } catch (ClassNotFoundException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  private void sendCreateRequest() {
    String name = chatNameField.getText();
    if (name.isBlank()) return;
    try (Socket socket = new Socket(hostIP, hostPort)) {
      new ChatRoomCreateRequest(name).serialize(socket);
      LocalChatResponse response = LocalChatResponse.deserialize(socket);
      if (response instanceof ChatRoomCreateResponse createResponse) {
        new ChatWindow(this, createResponse.chatID, name);
      } else {
        JOptionPane.showMessageDialog(this, "Couldn't create chat room", "LocalChatJoin", JOptionPane.ERROR_MESSAGE);
      }
    } catch (ClassNotFoundException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  private void sendUserJoinRequest() {
    String name = userNameField.getText();
    if (name.isBlank()) return;
    try (Socket socket = new Socket(hostIP, hostPort)) {
      new UserJoinRequest(hostPort, name).serialize(socket);
      LocalChatResponse response = LocalChatResponse.deserialize(socket);
      if (response instanceof UserJoinResponse) {
        userNameField.setEnabled(false);
        userButton.setEnabled(false);
        JOptionPane.showMessageDialog(this, "Successfully registered user", "LocalChatJoin", JOptionPane.INFORMATION_MESSAGE);
      } else if (response instanceof UserExistsResponse) {
        JOptionPane.showMessageDialog(this, "A user already exists at this location", "LocalChatJoin", JOptionPane.ERROR_MESSAGE);
      } else {
        JOptionPane.showMessageDialog(this, "Unknown error", "LocalChatJoin", JOptionPane.ERROR_MESSAGE);
      }
    } catch (ClassNotFoundException | IOException e) {
      throw new RuntimeException(e);
    }
  }
}
