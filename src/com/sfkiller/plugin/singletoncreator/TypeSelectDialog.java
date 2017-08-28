package com.sfkiller.plugin.singletoncreator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TypeSelectDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPanel static_inner_class;

    private JList typeList;

    private OnTypeSelectedListener listener;
    private JTextArea textArea1;
    private JTextArea textArea2;

    public TypeSelectDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setTitle("Choose a type");

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        typeList.setSelectedIndex(0);
        typeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        typeList.addKeyListener(new KeyListener() {

            int preIndex = -1;

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                preIndex = typeList.getSelectedIndex();
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int curIndex = typeList.getSelectedIndex();
                if (preIndex == curIndex) {
                    if (0 == curIndex && KeyEvent.VK_UP == e.getKeyCode()) {
                        typeList.setSelectedIndex(4);
                    } else if (4 == curIndex && KeyEvent.VK_DOWN == e.getKeyCode()) {
                        typeList.setSelectedIndex(0);
                    }
                }
            }
        });

        setBounds(new Rectangle(300, 200));
        setLocationRelativeTo(null);
    }

    private void onOK() {
        dispose();
        if (null != listener) {
            listener.onTypeSelected(typeList.getSelectedIndex());
        }
    }

    private void onCancel() {
        dispose();
    }

    public void setOnTypeSelectedListener(OnTypeSelectedListener l) {
        this.listener = l;
    }

//    public static void main(String[] args) {
//        TypeSelectDialog dialog = new TypeSelectDialog();
//        dialog.pack();
//        dialog.setVisible(true);
//        System.exit(0);
//    }
}
