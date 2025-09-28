package nz.ac.auckland;

import java.util.List;

public class LLMOutput {
  private List<Result> results;

  public LLMOutput() {}

  public LLMOutput(List<Result> results) {
    this.results = results;
  }

  public List<Result> getResults() {
    return results;
  }

  public void setResults(List<Result> results) {
    this.results = results;
  }
}
