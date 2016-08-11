package org.incha.ui.search;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class SearchMenu {

	private JPanel searchPanel;
	
	/**
     * Default constructor. Se crea un panel de búsqueda para colocarlo en algún menú.
     */
	public SearchMenu() {
		searchPanel = new JPanel();
		JTextField searchedWords = new JTextField(15);
        searchPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
        searchPanel.setLayout(new FlowLayout());
        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchedWords);
        JButton searchButton = new JButton("Go");
        JButton clearButton = new JButton("Clear");
        searchButton.addActionListener(new StartSearchAction(searchedWords));
        clearButton.addActionListener(new ClearSearchAction(searchedWords));
        searchPanel.add(searchButton);
        searchPanel.add(clearButton);
	}
	
	public JPanel getSearchPanel() {
		return searchPanel;
	}
}
