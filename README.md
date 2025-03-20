# 🖥️ Chat Application (gRPC-based)

This project is a simple chat application built using gRPC. It includes a server and a client with a graphical user interface (GUI). The application allows multiple users to communicate over a network, displaying messages in real time.

## 🚀 Features

- **gRPC Communication:** The client and server communicate using gRPC, ensuring efficient and reliable message exchange.
- **Graphical User Interface:** The client application provides a user-friendly GUI for inputting messages and viewing chat history.
- **Server GUI:** The server includes a minimal interface displaying connection details and a button to stop the server.
- **Customizable Connection Settings:** Users can enter the server address, port, and username through the GUI.
- **Standalone Execution:** The server and client can be compiled into separate `.jar` files for independent execution.

## Installation and Execution

### Prerequisites

Ensure you have the following installed:
- Java (JDK 17 or higher)
- Maven
- gRPC dependencies (automatically handled by Maven)

### 1️⃣ Clone the Repository

```sh
git clone https://github.com/SaulCBatista/Chat-gRCP
cd Chat-gRCP
```

### 2️⃣ Compile the Project

Run the following command to clean and package the application:

```sh
mvn clean package
```

### 3️⃣ Generate Executable JAR Files

After packaging, you need to create separate JAR files for the **server** and **client**:

#### **Server JAR**

```sh
jar cfe target/chat-server.jar chat.ChatServer -C target/classes .
```

To run the server:

```sh
java -jar target/chat-server.jar
```

#### **Client JAR**

```sh
jar cfe target/chat-client.jar chat.Main -C target/classes .
```

To run the client:

```sh
java -jar target/chat-client.jar
```

### 4️⃣ Alternative: Running Directly with Maven

If you prefer, you can run the server and client directly:

#### Run the Server:

```sh
mvn exec:java -Dexec.mainClass="chat.ChatServer"
```

A GUI will appear allowing you to enter the server address, port, and username.

#### Run the Client:

```sh
mvn exec:java -Dexec.mainClass="chat.Main"
```

## Notes

- If dependencies are missing, run `mvn install` first.
- If the `.jar` files don't work properly due to missing dependencies, consider using the `maven-assembly-plugin` or `maven-shade-plugin` to create a **Fat JAR**.

## Usage

1. Start the server first.
2. Launch one or more client instances.
3. Enter the server details in the client GUI and connect.
4. Send and receive messages in real-time.
5. Stop the server using the provided button.

## 🛠️ Troubleshooting

- ❌ **Issue:** Client cannot connect to the server.
  - Ensure the server is running.
  - Verify the correct IP address and port are used.
  - Check firewall and network settings.

- ❌ **Issue:** Messages are not displayed correctly.
  - Ensure each client has a unique username.
  - Restart the server and clients.


