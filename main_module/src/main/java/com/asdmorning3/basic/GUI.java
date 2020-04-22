package com.asdmorning3.basic;

import com.asdmorning3.test.InterfaceLanguages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Dictionary;
import java.util.List;
import java.util.Objects;

public class GUI {

    VocableDictionary vcb;
    JFrame frame;
    JPanel pane;
    JButton btnSubmit;
    //JButton btnSave;
    JMenuBar menuBar;
    JMenu menuFile;
    JMenuItem itemSave;
    JMenuItem itemLoad;

    JTextField txtFld1;
    JTextField txtFld2;
    JLabel lblLang1;
    JLabel lblLang2;
    JLabel lblWord1;
    JLabel lblWord2;
    JLabel lblIntLang;
    public JComboBox<Vocable.Language> comboBoxLang1;
    public JComboBox<Vocable.Language> comboBoxLang2;
    public JComboBox<InterfaceLanguages.Languages> comboBoxInterfaceLang;
    InterfaceLanguages.Languages lang;

    public GUI(VocableDictionary v)
    {
        vcb = v;
        pane = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        btnSubmit = new JButton();

        menuBar = new JMenuBar();
        menuFile = new JMenu();
        itemSave = new JMenuItem();
        itemLoad = new JMenuItem();
        //btnSave = new JButton();
        txtFld1 = new JTextField();
        txtFld2 = new JTextField();
        lblLang1 = new JLabel();
        comboBoxInterfaceLang = new JComboBox<>(InterfaceLanguages.Languages.class.getEnumConstants());
        lang = (InterfaceLanguages.Languages) comboBoxInterfaceLang.getSelectedItem();
        frame = new JFrame();
        lblLang2 = new JLabel();
        lblWord1 = new JLabel();
        lblWord2 = new JLabel();
        lblIntLang = new JLabel();
        getIntLang();
        comboBoxLang1 = new JComboBox<>(Vocable.Language.values());
        comboBoxLang2 = new JComboBox<>(Vocable.Language.values());

        frame.setSize(600, 600);

        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(0,35,111,0);
        pane.add(lblIntLang, c);
        c.gridx = 1;
        c.insets = new Insets(0,0,11,0);
        c.anchor = GridBagConstraints.NORTHWEST;
        pane.add(comboBoxInterfaceLang, c);
        comboBoxInterfaceLang.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                lang = (InterfaceLanguages.Languages)comboBoxInterfaceLang.getSelectedItem();
                getIntLang();
            }
        });
        c.gridx = 0;
        c.gridy++;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = .05;
        c.insets = new Insets(10, 55, 0, 100);
        pane.add(lblLang1, c);
        c.gridx = 1;
        c.insets = new Insets(10, 0, 0, 55);
        pane.add(lblLang2, c);
        c.gridx = 0;
        c.gridy++;
        c.insets = new Insets(10, 55, 10, 100);
        pane.add(comboBoxLang1, c);
        c.gridx = 1;
        c.insets = new Insets(10, 0, 10, 55);
        pane.add(comboBoxLang2, c);

        c.gridx = 0;
        c.gridy++;
        c.insets = new Insets(10, 55, 0, 100);
        pane.add(lblWord1, c);
        c.gridx = 1;
        c.insets = new Insets(10, 0, 0, 55);
        pane.add(lblWord2, c);

        c.gridx = 0;
        c.gridy++;
        c.insets = new Insets(10, 55, 0, 100);
        pane.add(txtFld1, c);
        c.gridx = 1;
        c.insets = new Insets(10, 0, 0, 55);
        pane.add(txtFld2, c);

        c.gridx = 1;
        c.gridy++;
        btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    vcb.addVocable(new Vocable(txtFld1.getText(), (Vocable.Language) Objects.requireNonNull(comboBoxLang1.getSelectedItem())),
                            new Vocable(txtFld2.getText(), (Vocable.Language) Objects.requireNonNull(comboBoxLang2.getSelectedItem())));
                    txtFld1.setText("");
                    txtFld2.setText("");
                }
                catch(NullPointerException ex)
                {
                    System.out.println("one of the objects is null");
                }
             }
        });
        pane.add(btnSubmit, c);

        itemSave.addActionListener(actionEvent -> {
            try {
                JFileChooser tosave = new JFileChooser();
                if(tosave.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION)
                {
                    vcb.save(tosave.getSelectedFile().getPath());
                }

            }
            catch(NullPointerException | IOException ex)
            {
                System.out.println("one of the objects is null");
            }
        });
        menuFile.add(itemSave);

        itemLoad.addActionListener(actionEvent -> {
            try {
                JFileChooser toload = new JFileChooser();
                if(toload.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION)
                {
                    vcb.load(toload.getSelectedFile().getPath());
                }

            }
            catch(NullPointerException | IOException | ClassNotFoundException ex)
            {
                System.out.println("one of the objects is null");
            }
        });
        menuFile.add(itemLoad);

        menuBar.add(menuFile);

        frame.add(menuBar, BorderLayout.NORTH);
        frame.add(pane, BorderLayout.CENTER);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    JFileChooser tosave = new JFileChooser();
                    if(tosave.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION)
                    {
                        vcb.save(tosave.getSelectedFile().getPath());
                    }

                }
                catch(NullPointerException | IOException ex)
                {
                    System.out.println("one of the objects is null");
                }
                super.windowClosing(e);
            }
        });
        frame.setVisible(true);
    }

     //testing
    public static void main(String args[])
    {
        VocableDictionary d = new VocableDictionary();
        d.addVocable(new Vocable("hallo", Vocable.Language.GER),
                new Vocable("hello", Vocable.Language.ENG));
        GUI g = new GUI(d);
    }

    private void getIntLang()
    {
        lblIntLang.setText(InterfaceLanguages.getString(lang, "interfacelanguage"));
        lblLang1.setText(InterfaceLanguages.getString(lang, "language"));
        lblLang2.setText(InterfaceLanguages.getString(lang, "language"));
        lblWord1.setText(InterfaceLanguages.getString(lang, "word"));
        lblWord2.setText(InterfaceLanguages.getString(lang, "word"));
        btnSubmit.setText(InterfaceLanguages.getString(lang, "add"));
        itemSave.setText(InterfaceLanguages.getString(lang, "save"));
        frame.setTitle(InterfaceLanguages.getString(lang, "vocab-trainer"));
        menuFile.setText(InterfaceLanguages.getString(lang, "file"));
        itemLoad.setText(InterfaceLanguages.getString(lang, "load"));
    }
}
