package org.incha.ui.search;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class NewSearchMenu {
	
	private JTextField searchedWords;
	private JPanel searchpanel;
	//private Search search;   Clase que implementa la búsqueda
	
	/**
     * Default constructor. Se crea un panel de búsqueda para colocarlo en algún menú.
     */
	public NewSearchMenu() {
		searchpanel = new JPanel();
		searchedWords = new JTextField(15);	
        searchpanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
        searchpanel.setLayout(new FlowLayout());      
        searchpanel.add(new JLabel("Search:"));
        searchpanel.add(searchedWords);    
        JButton searchButton = new JButton("Go");
        searchButton.addActionListener(new StartSearchAction(searchedWords));
        searchpanel.add(searchButton);
	}
	
	public JPanel getSearchPanel() {
		return searchpanel;
	}
}
