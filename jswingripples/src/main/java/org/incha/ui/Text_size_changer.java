package org.incha.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.graphstream.graph.Graph;
import org.incha.core.jswingripples.GraphBuilder;

public class Text_size_changer implements ActionListener {
	int type;
	public Text_size_changer(int i){
		type = i;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Graph g = GraphBuilder.getInstance().getDependencyGraph();
		int text_size = GraphBuilder.getInstance().getTextsize();
		if (type == 0){
			text_size = text_size + 3;
		}else{
			if(text_size > 4){
				text_size = text_size - 3;
			}
		}
		GraphBuilder.getInstance().setTextSize(text_size);
		g.addAttribute("ui.stylesheet", "node {text-size:" + Integer.toString(text_size) + ";}");
	}

}
