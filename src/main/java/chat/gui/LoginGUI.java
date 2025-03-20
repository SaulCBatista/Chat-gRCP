package chat.gui;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import chat.ChatClient;

public class LoginGUI extends JFrame {
    private JTextField hostField;
    private JTextField portField;
    private JTextField usernameField;
    private JButton connectButton;

    public LoginGUI() {
        setTitle("Login - Chat gRPC");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 2, 5, 5));

        // Componentes do formulário
        add(new JLabel("Servidor:"));
        hostField = new JTextField("localhost");
        add(hostField);

        add(new JLabel("Porta:"));
        portField = new JTextField("50051");
        add(portField);

        add(new JLabel("Usuário:"));
        usernameField = new JTextField();
        add(usernameField);

        connectButton = new JButton("Conectar");
        add(new JLabel()); // Espaço vazio
        add(connectButton);

        // Evento do botão de conectar
        connectButton.addActionListener(e -> connectToChat());

        setVisible(true);
    }

    private void connectToChat() {
        String host = hostField.getText().trim();
        int port = Integer.parseInt(portField.getText().trim());
        String username = usernameField.getText().trim();

        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite um nome de usuário!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Criar o cliente e abrir a GUI do chat
        ChatClient client = new ChatClient(host, port, username);
        client.start();
        new ChatGUI(client);

        // Fecha a tela de login
        dispose();
    }
}
