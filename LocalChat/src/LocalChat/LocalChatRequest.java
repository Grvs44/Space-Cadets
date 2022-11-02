package LocalChat;

import java.io.IOException;
import java.io.InputStream;

public abstract class LocalChatRequest extends LocalChatRequestResponse {
  public static LocalChatRequest deserialize(InputStream byteStream) throws IOException, ClassNotFoundException {
    return (LocalChatRequest) LocalChatRequestResponse.deserialize(byteStream);
  }
}
