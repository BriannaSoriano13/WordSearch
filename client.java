
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class client {
    JFrame frame;

    public void createScreen() {
        frame = new JFrame("My First GUI");
        frame.setSize(1280, 720);
        JPanel mainContent = new JPanel();
        BoxLayout box = new BoxLayout(mainContent, BoxLayout.Y_AXIS);
        mainContent.setLayout(box);

        JPanel inputPanel = new JPanel();
        BoxLayout inputGrid = new BoxLayout(inputPanel, BoxLayout.X_AXIS);
        inputPanel.setLayout(inputGrid);

        JTextField text = new JTextField();
        text.setMaximumSize(new Dimension(490, 30));
        JLabel lab = new JLabel("Word to search for: ");
        lab.setMaximumSize(new Dimension(150, 30));
        inputPanel.add(lab);
        inputPanel.add(Box.createRigidArea(new Dimension(500, 0)));
        inputPanel.add(text);
        inputPanel.setMaximumSize(new Dimension(1000, 30));

        JPanel outputPanel = new JPanel();
        BoxLayout outputGrid = new BoxLayout(outputPanel, BoxLayout.X_AXIS);
        outputPanel.setLayout(outputGrid);

        DefaultListModel<Integer> model = new DefaultListModel<>();
        JList<Integer> list = new JList<>(model);
        JScrollPane pane = new JScrollPane(list);
        pane.setMaximumSize(new Dimension(372, 500));

        JLabel lab2 = new JLabel("Response:");
        lab2.setMaximumSize(new Dimension(150, 30));

        outputPanel.add(lab2);
        outputPanel.add(Box.createRigidArea(new Dimension(500, 0)));
        outputPanel.add(pane);
        outputPanel.setMaximumSize(new Dimension(1000, 500));

        JPanel buttonPanel = new JPanel();
        BoxLayout buttonGrid = new BoxLayout(buttonPanel, BoxLayout.X_AXIS);
        buttonPanel.setLayout(buttonGrid);

        JButton but = new JButton("Request");
        buttonPanel.add(Box.createRigidArea(new Dimension(650, 0)));
        buttonPanel.add(but);

        but.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Socket client;
                try {
                    model.clear();
                    client = new Socket("127.0.0.1", 9999);
                    OutputStream out = client.getOutputStream();
                    PrintWriter send = new PrintWriter(out, true);
                    InputStream in = client.getInputStream();
                    BufferedReader read = new BufferedReader(new InputStreamReader(in));
                    send.println(text.getText());
                    String s;
                    while ((s = read.readLine()) != null) {
                        model.addElement(Integer.parseInt(s));
                        System.out.println(s);
                    }
                    client.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
        });

        mainContent.add(inputPanel);
        mainContent.add(outputPanel);
        mainContent.add(buttonPanel);
        frame.add(mainContent);

        frame.setVisible(true);
    }

    public client() throws UnknownHostException, IOException {
        createScreen();
    }
}