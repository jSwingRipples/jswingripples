package org.incha.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.incha.core.jswingripples.GraphBuilder;

public class Zoomed_text_changer implements ActionListener {
	int state;
	public Zoomed_text_changer(){
		state = 0;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (state == 0){
			GraphBuilder.getInstance().getDependencyGraph().addAttribute("ui.stylesheet", "node {text-visibility-mode: under-zoom; text-visibility: 0.2;}");
			state = 1;
		}else{
			GraphBuilder.getInstance().getDependencyGraph().addAttribute("ui.stylesheet", "node {text-visibility-mode: normal; text-visibility: 0.5;}");
			state = 0;
		}

	}

}
