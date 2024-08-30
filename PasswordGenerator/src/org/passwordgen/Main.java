package org.passwordgen;

import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatGitHubIJTheme;

import javax.swing.*;
import java.awt.Color;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Main  extends JFrame {
    public String n;
    public String sm_alphabets = "qwertyuiopasdfghjklzxcvbnm";
    public String cap_alpha = "QWERTYUIOPASDFGHJKLZXCVBNM";
    public String nstring = "0123456789";
    public String spl = "`-=[]\\;',./~!@#$%^&*()_+{}|:<>?";
    public StringBuilder jumble(String s){
        SecureRandom random = new SecureRandom();
        StringBuilder builder = new StringBuilder();
        for (int a =0; a < s.length(); a++){
            builder.append(s.charAt(random.nextInt(0, s.length())));
        }
        return builder;
    }
    public String generateThePass(String[] types, int n){
        SecureRandom random = new SecureRandom();
        StringBuilder sbt = new StringBuilder();
        StringBuilder builder = new StringBuilder();
        for (String type : types){
            if(Objects.equals(type, "sl")){
                sbt.append(sm_alphabets);
            } else if (Objects.equals(type, "cl")) {
                sbt.append(cap_alpha);
            } else if (Objects.equals(type, "spc")) {
                sbt.append(spl);
            } else if (Objects.equals(type, "n")) {
                sbt.append(nstring);
            } else if (Objects.equals(type, "all")) {
                sbt.append(sm_alphabets).append(cap_alpha).append(spl).append(nstring);
            }
        }
        for (int i = 0; i < n; i++) {
            builder.append(sbt.charAt(random.nextInt(0, jumble(sbt.toString()).length())));
        }
        return builder.toString();
    }
    public Main(){
        try {
            UIManager.setLookAndFeel(new FlatGitHubIJTheme());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        JLabel label1 = new JLabel("Length: ");
        JLabel labelT = new JLabel("Types: ");
        JButton b1 = new JButton("Get password");
        JLabel l1 = new JLabel("Filename");
        JTextField textField1 = new JTextField();
        JTextField textFieldForType = new JTextField();
        JTextField textFieldLen = new JTextField();
        JButton hint = new JButton("Guide of choices");
        JTextField textFieldForDir = new JTextField();
        JButton buttonForTheDir = new JButton("Choose directory");
        JCheckBox saveCheck = new JCheckBox("Save Password");
        JTextField textFieldForName = new JTextField();
        JButton showSavesPasswordButton = new JButton("See the saved password");
        JTextField textFieldForOpen = new JTextField();
        JButton buttonOpen = new JButton("Open");
        JLabel seePassL = new JLabel("See saved passwords");

        l1.setForeground(new Color(0, 0, 0));
        label1.setForeground(new Color(0, 0, 0));
        labelT.setForeground(new Color(0, 0, 0));
        label1.setBounds(5, 25, 50, 20);
        labelT.setBounds(100, 20, 60, 30);
        textFieldLen.setBounds(50, 20, 40, 30);
        textFieldForType.setBounds(160, 20, 100, 30);
        b1.setBounds(350, 60,120, 40);
        textField1.setBounds(50, 60, 300, 40);
        buttonForTheDir.setBounds(330, 140, 150, 40);
        hint.setBounds(270, 20, 150, 30);
        saveCheck.setBounds(10, 100, 120, 30);
        textFieldForDir.setBounds(70, 140, 250, 40);
        textFieldForName.setBounds(70, 190, 250, 40);
        l1.setBounds(10, 190, 50, 20);
        seePassL.setBounds(20, 240, 300, 20);
        showSavesPasswordButton.setBounds(20, 320, 250, 40);
        textFieldForOpen.setBounds(20, 270, 250, 40);
        buttonOpen.setBounds(320, 270, 100, 40);


        b1.addActionListener(_ -> {
            try {
                String text = textFieldForType.getText();
                text = text.replace(" ", "").replace("\t", "");
                textField1.setText(generateThePass(text.split(","), Integer.parseInt(textFieldLen.getText())));
                if(saveCheck.isSelected()){
                    n = JOptionPane.showInputDialog(this, "Enter a name to save password ");
                    System.out.println(n);
                    try {
                        if(new File(textFieldForDir.getText()+"\\"+textFieldForName.getText()).exists()){
                            InputStream s1 = new FileInputStream(textFieldForDir.getText()+"\\"+textFieldForName.getText());
                            ObjectInputStream os1 = new ObjectInputStream(s1);
                            List<DataObject> objects = (List<DataObject>) os1.readObject();


                            OutputStream stream = new FileOutputStream(textFieldForDir.getText()+"\\"+textFieldForName.getText());
                            ObjectOutputStream out = new ObjectOutputStream(stream);

                            DataObject object = new DataObject(n, textField1.getText());
                            objects.add(object);
                            out.writeObject(objects);
                            out.close();
                            stream.close();
                        } else {
                            List<DataObject> objects = new ArrayList<>();


                            OutputStream stream = new FileOutputStream(textFieldForDir.getText()+"\\"+textFieldForName.getText());
                            ObjectOutputStream out = new ObjectOutputStream(stream);

                            DataObject object = new DataObject(n, textField1.getText());
                            objects.add(object);
                            out.writeObject(objects);
                            out.close();
                            stream.close();
                        }
                    } catch (IOException ex) {
                        String x = ex.toString();
                        System.out.println(x);
                        JOptionPane.showMessageDialog(this, x, "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                    System.out.println(Arrays.toString(n.getBytes(StandardCharsets.UTF_8)));
                } else {
                    n = "";
                }
            } catch (Exception e2){
                System.out.println("Couldn't generate the password");
                JOptionPane.showMessageDialog(this, "Couldn't generate password", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        buttonForTheDir.addActionListener(_ -> {
            try {
                JFileChooser chooser = new JFileChooser();
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                chooser.showDialog(this, "Choose");
                textFieldForDir.setText(chooser.getSelectedFile().toString());
            } catch (NullPointerException e){
                System.out.println("Folder is not taken here");
            }
        });
        hint.addActionListener(_ -> {
            String s1 = "sl --> small letters\ncl --> capital letters\nspc --> special characters\nn --> number\nall --> for selecting all choice\nSeparate choice by using commas (,)";
            JOptionPane.showMessageDialog(this, s1, "Guide", JOptionPane.PLAIN_MESSAGE);
        });
        showSavesPasswordButton.addActionListener(_ ->{
            try {
                InputStream inputStream = new FileInputStream(textFieldForOpen.getText());
                ObjectInputStream stream = new ObjectInputStream(inputStream);
                JFrame frame1 = new JFrame("Passwords saved");
                List<DataObject> dataObject = (List<DataObject>) stream.readObject();
                JTextArea area = new JTextArea();
                for(DataObject object : dataObject){
                    area.append(object.label + "   :    "+object.pass);
                    area.append("\n");
                }

                area.setEditable(false);
                frame1.setVisible(true);
                frame1.setBounds(300, 300, 360, 360);
                area.setBounds(1, 1, 200, 200);
                JScrollPane scrollPane = new JScrollPane(area, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                frame1.add(scrollPane);
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error reading file");
            }
        });
        buttonOpen.addActionListener(_ ->{
            try {
                JFileChooser chooser = new JFileChooser();
                chooser.showDialog(this, "Choose");
                textFieldForOpen.setText(chooser.getSelectedFile().toString());
            } catch (NullPointerException e){
                System.out.println("File is not taken here");
            }
        });
        textField1.setEditable(false);


        setTitle("Password Generator");
        setBounds(30, 30, 500, 500);
        setLayout(null);
        setVisible(true);
        add(b1);
        add(label1);
        add(textFieldLen);
        add(textField1);
        add(labelT);
        add(textFieldForType);
        add(hint);
        add(saveCheck);
        add(textFieldForDir);
        add(buttonForTheDir);
        add(l1);
        add(textFieldForName);
        add(showSavesPasswordButton);
        add(textFieldForOpen);
        add(buttonOpen);
        add(seePassL);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static void main(String[] args) {
        new Main();
    }
}