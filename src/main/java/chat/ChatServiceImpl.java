package chat;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import io.grpc.stub.StreamObserver;
import proto.chat.Chat;
import proto.chat.ChatServiceGrpc;

public class ChatServiceImpl extends ChatServiceGrpc.ChatServiceImplBase {
    private final Set<StreamObserver<Chat.ChatMessage>> clients = Collections.synchronizedSet(new HashSet<>());

    @Override
    public StreamObserver<Chat.ChatMessage> joinChat(StreamObserver<Chat.ChatMessage> responseObserver) {
        clients.add(responseObserver);
        return new StreamObserver<Chat.ChatMessage>() {
            @Override
            public void onNext(Chat.ChatMessage message) {
                broadcastMessage(message);
            }

            @Override
            public void onError(Throwable t) {
                clients.remove(responseObserver);
            }

            @Override
            public void onCompleted() {
                clients.remove(responseObserver);
                responseObserver.onCompleted();
            }
        };
    }

    private void broadcastMessage(Chat.ChatMessage message) {
        synchronized (clients) {
            for (StreamObserver<Chat.ChatMessage> client : clients) {
                client.onNext(message);
            }
        }
    }
}
