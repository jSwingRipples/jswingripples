package org.incha.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.graphstream.graph.Graph;
import org.incha.core.jswingripples.GraphBuilder;

public class Node_changer implements ActionListener {
	int type;
	public Node_changer(int i){
		type = i;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Graph g = GraphBuilder.getInstance().getDependencyGraph();
		int size = GraphBuilder.getInstance().getSize();
		if (type == 0){
		size = size + 4;
		}else{
			if (size > 4){
				size = size - 4;
				}
		}
		GraphBuilder.getInstance().setSize(size);
		g.addAttribute("ui.stylesheet", "node {size:" + Integer.toString(size) + "px," + Integer.toString(size) + "px;}");

	}

}
