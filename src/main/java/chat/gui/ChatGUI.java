package chat.gui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import chat.ChatClient;

public class ChatGUI extends JFrame {
    private final ChatClient chatClient;
    private final JTextArea chatArea;
    private final JTextField messageField;
    private final JButton sendButton;

    public ChatGUI(ChatClient chatClient) {
        this.chatClient = chatClient;

        setTitle("Chat - " + chatClient.getUsername());
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Área de mensagens
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);
        add(scrollPane, BorderLayout.CENTER);

        // Painel inferior
        JPanel bottomPanel = new JPanel(new BorderLayout());
        messageField = new JTextField();
        sendButton = new JButton("Enviar");

        bottomPanel.add(messageField, BorderLayout.CENTER);
        bottomPanel.add(sendButton, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);

        // Evento do botão enviar
        sendButton.addActionListener(e -> sendMessage());
        messageField.addActionListener(e -> sendMessage());

        chatClient.setMessageListener(this::receiveMessage);

        setVisible(true);
    }

    private void sendMessage() {
        String message = messageField.getText().trim();
        if (!message.isEmpty()) {
            chatClient.sendMessage(message);
            messageField.setText("");
        }
    }

    private void receiveMessage(String message) {
        SwingUtilities.invokeLater(() -> chatArea.append(message + "\n"));
    }
}
