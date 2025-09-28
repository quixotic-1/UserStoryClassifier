package nz.ac.auckland;


import javax.json.Json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import nz.ac.auckland.apiproxy.chat.openai.ChatCompletionResult;
import nz.ac.auckland.apiproxy.chat.openai.ChatMessage;
import nz.ac.auckland.se283.GptClient;
import nz.ac.auckland.se283.prompts.PromptEngineering;


public class App {
  private static LLMOutput output;

  public static void main(String[] args) throws Exception {
    output = null;
    
    GptClient client = new GptClient();
    String systemPrompt = PromptEngineering.getPrompt("prompt");

    List<ChatMessage> messages = new ArrayList<>();
    // Add system messages
    messages.add(new ChatMessage("system", systemPrompt));
    // Add user messages
    List<ChatMessage> userMessages = loadUserStoriesFromJson("src/main/resources/input/input.json");
    messages.addAll(userMessages);

    ChatCompletionResult result = client.runOnce(messages, 1, 0.2, 1.0, 2000);
    String response = result.getFirstChoice().getChatMessage().getContent();
    
    // To prevent extensive api calls
    int writeFails = -1;
    boolean writeSuccess = false;

    while (writeFails < 4 && !writeSuccess) {
      writeSuccess = writeOutputJson(response, "target/output/output.json");
      writeFails++;
    }

    if (writeFails > 3) {
      System.out.println("Quit to prevent extensive API calls");
    } else {
      double acc = accuracy();
      System.out.printf("Accuracy: %d%%%n", (int) (acc * 100));
    }
  }

  public static List<ChatMessage> loadUserStoriesFromJson(String path) throws Exception {
    List<ChatMessage> messages = new ArrayList<>();
    try (JsonReader reader = Json.createReader(new FileReader(path))) {
      JsonArray arr = reader.readArray();
      for (JsonObject obj : arr.getValuesAs(JsonObject.class)) {
        messages.add(new ChatMessage("user", "US_ID: " + obj.getString("US_ID") + "\nUS_text: " + obj.getString("US_text")));
      }
    } catch (Exception e) {
      System.err.println("Failed to load messages from JSON: " + e.getMessage());
      e.printStackTrace();
    }
    return messages;
  }

  public static boolean writeOutputJson(String gptOutput, String path){
    // Create folder if not created
    File folder = new File("target/output");
    if (!folder.exists()) {
      folder.mkdirs();
    }   

    ObjectMapper mapper = new ObjectMapper();
    try {
      output = mapper.readValue(gptOutput, LLMOutput.class);
      mapper.writerWithDefaultPrettyPrinter().writeValue(new File(path), output);
    } catch (JsonProcessingException e) {
      System.err.println("Invalid JSON format: " + e.getMessage());
      return false;
    } catch (IOException ex) {
      System.err.println("IO Exception: " + ex.getMessage());
      return false;
    }
    return true;
  }

  public static double accuracy() {
    int i = 0;
    int correct_problem_id = 0;
    try (JsonReader reader = Json.createReader(new FileReader("src/main/resources/ground_truth/ground_truth_labels.json"))) {
      JsonArray arr = reader.readArray();
      for (JsonObject obj : arr.getValuesAs(JsonObject.class)) {
        if (obj.getJsonNumber("ground_truth_problem_id").intValue() == output.getResults().get(i).getEstimated_problem_id()) {
          correct_problem_id++;
        }
        i++;
      }
    } catch (Exception e) {
      System.err.println("Failed to load messages from JSON: " + e.getMessage());
      e.printStackTrace();
    }

    return ((double) correct_problem_id / i);
  }

}
