package com.sfkiller.plugin.singletoncreator;

import javax.swing.*;

/**
 * Created by qipu on 2017/8/28.
 */
public class TypeSelectDialog extends JDialog {

    private JPanel contentPanel;
    private JButton okButton;
    private JButton cancelButton;
    private JList typeList;

    public TypeSelectDialog() {
        setContentPane(contentPanel);
        setModal(true);
        getRootPane().setDefaultButton(okButton);
        setTitle("Choose a type");
        okButton.addActionListener();
    }
}
