package org.incha.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.incha.core.jswingripples.GraphBuilder;

public class Node_color_changer implements ActionListener {
	String c;
	public Node_color_changer(String color){
		c = color;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		GraphBuilder.getInstance().getDependencyGraph().addAttribute("ui.stylesheet", "node {fill-color:"+c+";}");
	}

}
