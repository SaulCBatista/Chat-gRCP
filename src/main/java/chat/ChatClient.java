package chat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Consumer;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import proto.chat.Chat;
import proto.chat.ChatServiceGrpc;

public class ChatClient {
    private final ManagedChannel channel;
    private final ChatServiceGrpc.ChatServiceStub asyncStub;
    private final String username;
    private Consumer<String> messageListener;

    public ChatClient(String host, int port, String username) {
        this.channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
        this.asyncStub = ChatServiceGrpc.newStub(channel);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void start() {
        StreamObserver<Chat.ChatMessage> responseObserver = new StreamObserver<Chat.ChatMessage>() {
            @Override
            public void onNext(Chat.ChatMessage message) {
                String formattedMessage = "[" + message.getTimestamp() + "] " + message.getUsername() + " : "
                        + message.getMessage();
                if (messageListener != null) {
                    messageListener.accept(formattedMessage);
                }
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("Erro na conexão: " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Chat encerrado.");
            }
        };

        asyncStub.joinChat(responseObserver);
    }

    public void sendMessage(String message) {
        String timestamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
        Chat.ChatMessage chatMessage = Chat.ChatMessage.newBuilder().setTimestamp(timestamp).setUsername(username)
                .setMessage(message).build();
        asyncStub.joinChat(new StreamObserver<Chat.ChatMessage>() {
            @Override
            public void onNext(Chat.ChatMessage chatMessage) {
            }

            @Override
            public void onError(Throwable t) {
            }

            @Override
            public void onCompleted() {
            }
        }).onNext(chatMessage);
    }

    public void setMessageListener(Consumer<String> listener) {
        this.messageListener = listener;
    }
}
