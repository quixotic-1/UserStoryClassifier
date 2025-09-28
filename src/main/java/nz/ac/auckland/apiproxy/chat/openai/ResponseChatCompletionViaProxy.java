package nz.ac.auckland.apiproxy.chat.openai;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseChatCompletionViaProxy {

  public final Boolean success;
  public final Integer code;
  public final String message;
  public final Map<String, Object> chat_completion;

  public ResponseChatCompletionViaProxy(
      @JsonProperty("success") Boolean success,
      @JsonProperty("code") Integer code,
      @JsonProperty("message") String message,
      @JsonProperty("chat_completion") Map<String, Object> chat_completion) {
    this.success = success;
    this.code = code;
    this.message = message;
    this.chat_completion = chat_completion;
  }
}
