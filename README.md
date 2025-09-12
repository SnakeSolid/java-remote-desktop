# Remote Desktop

This project implements a simple remote desktop application in Java, consisting of a client and a server. The client captures the screen and sends it to the server, which displays it in a window. Mouse and keyboard events generated on the server side are sent back to the client for execution.

## Features

* Screen capture and streaming from client to server.
* Basic mouse and keyboard event forwarding from server to client.
* Simple image compression for screen tiles.
* Caching of screen tiles to reduce network traffic.
* Multiple client connections supported on the server via tabs.

## Modules

* `remote-core`: Contains core logic for image compression, decompression, tiling, and caching.
* `remote-eventloop`: Handles network communication using Kryo serialization for messages between client and server.
* `remote-client`: The client application. Captures the screen, compresses tiles, and sends them to the server. Receives and executes input events.
* `remote-server`: The server application. Accepts connections, receives screen tiles, displays them, and sends input events from the user interface back to the respective client.

## Building

This project uses Maven for building. To build all modules, navigate to the root directory and run:

```bash
mvn clean install
```

This will compile the code and package each module into a JAR file located in the `target` directory of each module.

## Running

### Server

1.  Navigate to the `remote-server` directory.
2.  Run the server JAR file:

    ```bash
    java -jar target/remote-server-<version>.jar [options]
    ```

**Options:**

* `-l`, `--address <address>`: Listen on address (default: 127.0.0.1).
* `-p`, `--port <port>`: Listen on port (default: 12398).
* `--help`: Display help message.

### Client

1.  Navigate to the `remote-client` directory.
2.  Run the client JAR file:

    ```bash
    java -jar target/remote-client-<version>.jar [options]
    ```

**Options:**

* `-a`, `--address <address>`: Server hostname or IP address (default: 127.0.0.1).
* `-p`, `--port <port>`: Server port number to connect to (default: 12398).
* `-r`, `--reconnect`: Reconnect until the server becomes available.
* `-d`, `--reconnect-delay <seconds>`: Delay between connection attempts in seconds (default: 5).
* `--help`: Display help message.

## Usage

1.  Start the server on one machine.
2.  Start the client on the machine you wish to share the screen of, ensuring it connects to the server's address and port.
3.  The client's screen will appear in a tab on the server's window.
4.  Interact with the server window (mouse clicks, movements, keyboard input) to control the client machine.

## Notes

* The application uses `java.awt.Robot` for screen capture and input event generation on the client side. Ensure the environment allows this.
* Network performance and image quality are balanced using a simple tiling and compression scheme. Image quality is reduced.
* Security is not implemented. Use only in trusted networks.
* The server GUI is built using Swing.

## License

This project is licensed under the MIT License. See the LICENSE file for details.
