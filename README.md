# UserStoryClassifier

A Java application that uses Large Language Models (LLM) to classify user stories with high accuracy. This project leverages OpenAI's GPT API through a proxy service to analyze and categorize user stories based on predefined criteria.

## 📋 Table of Contents

- [Features](#features)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Configuration](#configuration)
- [Usage](#usage)
- [Project Structure](#project-structure)
- [Input/Output Format](#inputoutput-format)
- [API Integration](#api-integration)
- [Contributing](#contributing)

## ✨ Features

- **LLM-Powered Classification**: Uses GPT models to intelligently classify user stories
- **Batch Processing**: Processes multiple user stories in a single API call
- **Accuracy Measurement**: Compares results against ground truth data
- **JSON Input/Output**: Easy-to-use JSON format for data exchange
- **Configurable Prompts**: Customizable system prompts for different classification needs
- **Error Handling**: Robust error handling with retry mechanisms
- **API Rate Limiting**: Built-in protection against excessive API calls

## 🔧 Prerequisites

- **Java 21** or higher
- **Maven** (or use the included Maven wrapper)
- **Internet connection** (for API calls)
- **API Proxy Configuration** (see Configuration section)

## 📦 Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/quixotic-1/UserStoryClassifier.git
   cd UserStoryClassifier
   ```

2. Build the project:
   ```cmd
   .\mvnw.cmd clean compile
   ```

## ⚙️ Configuration

1. **API Proxy Setup**: Configure your API proxy settings in `apiproxy.config`
2. **Input Data**: Place your user stories in `src/main/resources/input/input.json`
3. **Ground Truth**: Add expected results in `src/main/resources/ground_truth/ground_truth_labels.json`
4. **Prompts**: Customize the system prompt in `src/main/resources/prompts/prompt.txt`

## 🚀 Usage

### Basic Usage

Run the application using Maven wrapper:

```cmd
.\mvnw.cmd clean compile exec:java
```

### With Maven (if installed):

```cmd
mvn clean compile exec:java
```

### With Custom Arguments:

```cmd
.\mvnw.cmd clean compile exec:java -Dexec.args="your arguments here"
```

### Example Workflow:

1. The application reads user stories from `input/input.json`
2. Sends them to the LLM with the configured system prompt
3. Processes the classification results
4. Saves output to `target/output/output.json`
5. Calculates accuracy against ground truth data
6. Displays the accuracy percentage

## 📁 Project Structure

```
UserStoryClassifier/
├── src/main/java/nz/ac/auckland/
│   ├── App.java                     # Main application class
│   ├── LLMOutput.java              # Output data model
│   ├── Result.java                 # Result data model
│   ├── apiproxy/                   # API proxy integration
│   │   ├── chat/openai/            # OpenAI chat completion models
│   │   ├── config/                 # Configuration classes
│   │   ├── exceptions/             # Custom exceptions
│   │   ├── service/                # Service endpoints
│   │   └── tts/                    # Text-to-speech functionality
│   └── se283/
│       ├── GptClient.java          # GPT client wrapper
│       └── prompts/
│           └── PromptEngineering.java # Prompt management
├── src/main/resources/
│   ├── ground_truth/
│   │   └── ground_truth_labels.json # Expected classification results
│   ├── input/
│   │   └── input.json              # Input user stories
│   └── prompts/
│       └── prompt.txt              # System prompt template
├── target/output/                  # Generated output directory
├── apiproxy.config                 # API proxy configuration
├── pom.xml                         # Maven configuration
└── README.md                       # This file
```

## 📊 Input/Output Format

### Input Format (`input/input.json`):
```json
[
  {
    "US_ID": "US1",
    "US_text": "As a user, I want to login, so that I can access my account."
  }
]
```

### Output Format (`target/output/output.json`):
```json
[
  {
    "US_ID": "US1",
    "classification": "Authentication",
    "confidence": 0.95
  }
]
```

### Ground Truth Format (`ground_truth/ground_truth_labels.json`):
```json
[
  {
    "US_ID": "US1",
    "expected_classification": "Authentication"
  }
]
```

## 🔌 API Integration

This project uses a proxy service to communicate with OpenAI's GPT API. The integration includes:

- **Chat Completion**: For text classification tasks
- **Text-to-Speech**: For audio output (optional)
- **Rate Limiting**: Automatic retry mechanism with exponential backoff
- **Error Handling**: Comprehensive exception handling for API failures

### Key Components:

- `GptClient`: Main interface for GPT API calls
- `ChatCompletionRequest/Result`: Request/response models
- `ApiProxyConfig`: Configuration management
- `EndPoints`: API endpoint definitions

## 🧪 Testing

The application includes accuracy measurement by comparing results against ground truth data:

```java
double acc = accuracy();
System.out.printf("Accuracy: %d%%%n", (int) (acc * 100));
```

## 📈 Performance Features

- **Batch Processing**: Processes multiple user stories in single API calls
- **Rate Limiting**: Prevents excessive API usage with built-in limits
- **Retry Logic**: Automatic retry for failed API calls (up to 4 attempts)
- **Memory Optimization**: Efficient JSON processing with streaming

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is part of SOFTENG283-2025 coursework.

## 🆘 Troubleshooting

### Common Issues:

1. **Java Version Error**: Ensure Java 21 is installed and configured
2. **API Connection Issues**: Check your internet connection and proxy configuration
3. **Input File Not Found**: Verify the input JSON file exists in the correct location
4. **Permission Errors**: Ensure write permissions for the `target/output` directory

### Debug Tips:

- Check console output for detailed error messages
- Verify API proxy configuration in `apiproxy.config`
- Ensure input JSON format matches the expected schema
- Monitor API rate limits and usage

---

For more information or support, please refer to the course materials or contact the development team.