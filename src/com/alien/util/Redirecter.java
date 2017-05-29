package com.alien.util;

import java.io.*; 
import javax.swing.*;

public class Redirecter extends OutputStream {
    private JTextArea textArea;
     
    public Redirecter(JTextArea textArea) {
        this.textArea = textArea;
    }
     
    @Override
    public void write(int b) throws IOException {
        textArea.append(String.valueOf((char)b));
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }
}