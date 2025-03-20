package chat;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import io.grpc.Server;
import io.grpc.ServerBuilder;

public class ChatServer {
    private Server server;
    private JFrame frame;

    public void start(int port) throws IOException {
        server = ServerBuilder.forPort(port).addService(new ChatServiceImpl()).build().start();

        // Obtém o IP da máquina
        String ipAddress = getLocalIpAddress();

        // Cria a interface gráfica
        SwingUtilities.invokeLater(() -> createGUI(ipAddress, port));

        System.out.println("Servidor rodando em: " + ipAddress + ":" + port);
    }

    private void createGUI(String ip, int port) {
        frame = new JFrame("Servidor de Chat");
        frame.setSize(300, 150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JLabel label = new JLabel("<html>Servidor rodando em:<br><b>" + ip + ":" + port + "</b></html>",
                SwingConstants.CENTER);
        frame.add(label, BorderLayout.CENTER);

        JButton stopButton = new JButton("Encerrar Servidor");
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stop();
            }
        });

        frame.add(stopButton, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    public void stop() {
        if (server != null) {
            server.shutdown();
            System.out.println("Servidor encerrado.");
            frame.dispose();
        }
    }

    private String getLocalIpAddress() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            return "localhost";
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        int port = 50051;
        ChatServer server = new ChatServer();
        server.start(port);
    }
}
