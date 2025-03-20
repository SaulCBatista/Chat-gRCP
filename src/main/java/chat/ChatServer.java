package chat;

import java.net.InetAddress;
import java.util.logging.Logger;

import io.grpc.Server;
import io.grpc.ServerBuilder;

public class ChatServer {
    private static Logger LOG = Logger.getLogger("Logger");
    private final int port;
    private final Server server;

    public ChatServer(int port) throws Exception {
        this.port = port;
        this.server = ServerBuilder.forPort(port).addService(new ChatServiceImpl()) // Instancia o serviço extraído
                .build();
    }

    public void start() throws Exception {
        server.start();
        String ip = InetAddress.getLocalHost().getHostAddress();
        LOG.info("Chat server started on " + ip + ":" + port);
        server.awaitTermination();
    }

    public static void main(String[] args) throws Exception {
        ChatServer server = new ChatServer(50051);
        server.start();
    }
}
