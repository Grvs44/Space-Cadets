package LocalChat;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public abstract class LocalChatRequest extends LocalChatRequestResponse {
  public static LocalChatRequest deserialize(Socket socket) throws IOException, ClassNotFoundException {
    return (LocalChatRequest) LocalChatRequestResponse.deserialize(socket);
  }
}
