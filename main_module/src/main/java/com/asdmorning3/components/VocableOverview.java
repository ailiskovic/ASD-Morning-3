package com.asdmorning3.components;

import com.asdmorning3.basic.Edit;
import com.asdmorning3.basic.Vocable;
import com.asdmorning3.basic.VocableDictionary;
import com.asdmorning3.test.InterfaceLanguages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class VocableOverview {

	private JFrame frame_;
	private JScrollPane pane_;
	private JTable table_;
	private InterfaceLanguages.Languages interfaceLanguage_;
	private ArrayList<List<Vocable>> vocable_list_;
	private static int  WIDTH = 200;
	private Object[][] data_;
	private String[] columns_;
	private InterfaceLanguages languages;
	private JPopupMenu popupMenu_;
	private JMenuItem item_;
	private VocableDictionary dict_;

	public void changeLanguage(InterfaceLanguages.Languages interfaceLanguage) {
		if (interfaceLanguage == interfaceLanguage_)
			return;
		this.interfaceLanguage_ = interfaceLanguage;
		frame_.setName(languages.getString(interfaceLanguage_, "overview"));
		item_.setText(languages.getString(interfaceLanguage_, "edit"));
	}

	public VocableOverview(VocableDictionary dict, InterfaceLanguages.Languages interfaceLanguage)
	{
		languages = new InterfaceLanguages();
		interfaceLanguage_ = interfaceLanguage;
		frame_ = new JFrame(languages.getString(interfaceLanguage, "overview"));
		columns_ = new String[Vocable.Language.class.getEnumConstants().length];
		popupMenu_ = new JPopupMenu();
		item_ = new JMenuItem();
		item_.setText(languages.getString(interfaceLanguage, "edit"));
		dict_ = dict;
		changeLanguage(interfaceLanguage);
		int i = 0;
		for (Vocable.Language language: Vocable.Language.class.getEnumConstants())
		{
			columns_[i] = Vocable.getLanguageWord(language);
			i++;
		}

		String data_[][] = dict.getTable();

		table_ = new JTable(data_, columns_);

		item_.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				if(!data_[table_.getSelectedRow()][table_.getSelectedColumn()].equals("")){
					Vocable v = dict_.findVocable(data_[table_.getSelectedRow()][table_.getSelectedColumn()],
							Vocable.Language.class.getEnumConstants()[table_.getSelectedColumn()]).get(0);
					Edit e = new Edit(frame_, dict_, v, interfaceLanguage_);
					dict_ = e.edit();
					String[] newData = dict_.getTable()[table_.getSelectedRow()];
					for(int c = 0; c < Vocable.Language.class.getEnumConstants().length; c++) {
						table_.setValueAt(newData[c], table_.getSelectedRow(), c);
						data_[table_.getSelectedRow()][c] = newData[c];
					}
					assert((String[])data_[table_.getSelectedRow()] == newData);
				}
			}
		});


		pane_ = new JScrollPane(table_);
		frame_.setSize(Vocable.Language.class.getEnumConstants().length * WIDTH, 400);
		frame_.add(pane_);
		popupMenu_.add(item_);
		table_.setComponentPopupMenu(popupMenu_);
		changeVisibility(true);
	}

	public void changeVisibility(boolean visibility)
	{
		frame_.setVisible(visibility);
	}

	public JScrollPane getContent()
	{
		return pane_;

	}

	public static void main(String args[])
	{
		VocableDictionary d = new VocableDictionary();
		d.addVocable(new Vocable("hallo", Vocable.Language.GER), new Vocable("salut", Vocable.Language.FRA));
		InterfaceLanguages.Languages lang = InterfaceLanguages.Languages.EN;
		VocableOverview v = new VocableOverview(d, lang);

	}
}
