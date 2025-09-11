# Remote Desktop

A lightweight remote desktop application designed for efficient screen sharing over a network.

## Building

This project uses Maven for dependency management. To build:

```bash
mvn clean package
```

Ensure you have Java 8 or higher installed.

## Running

### Start the Server
```bash
java -jar remote-server/target/remote-server-0.0.1-SNAPSHOT-jar-with-dependencies.jar --address 127.0.0.1 --port 12398
```

### Start the Client
```bash
java -jar remote-client/target/remote-client-0.0.1-SNAPSHOT-jar-with-dependencies.jar --address 127.0.0.1 --port 12398
```

By default, the server listens on port 12398 and the client connects to localhost. Adjust parameters as needed for your network setup.

## License

This project is licensed under the MIT License. See the LICENSE file for details.
