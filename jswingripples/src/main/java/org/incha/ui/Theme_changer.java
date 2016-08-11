package org.incha.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.graphstream.graph.Graph;
import org.incha.core.jswingripples.GraphBuilder;

public class Theme_changer implements ActionListener {
	
	int theme;
	public Theme_changer(int i){
		theme = i;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Graph g1 = GraphBuilder.getInstance().getDependencyGraph();
		Graph g2 = GraphBuilder.getInstance().getImpactSetGraph();
		String style = "";
		g1.removeAttribute("ui.stylesheet");
		g2.removeAttribute("ui.stylesheet");
		if (theme == 0){
			style = "graph{fill-color:#fff4e6; } node {size-mode:dyn-size; size: 40px, 40px;shape: circle;fill-color: #854442;text-alignment: at-right;text-size:20;text-color: #3c2f2f;stroke-mode:plain; stroke-color:#3c2f2f; stroke-width:2;}      edge{fill-color:#3c2f2f; shape:cubic-curve;size:3;} node:clicked { fill-color: darkmagenta;}       node:selected{stroke-mode:plain; stroke-color:black; stroke-width:5;} node.blank {size: 20px, 20px;shape: circle;fill-color: black;text-mode: normal;text-background-mode: rounded-box;text-visibility-mode: normal;text-alignment: at-right;}      node.changed {size: 20px, 20px;shape: circle;fill-color: red;text-mode: normal;text-background-mode: rounded-box;text-visibility-mode: normal;text-alignment: at-right;}      node.impacted {size: 20px, 20px;shape: circle;fill-color: darkred;text-mode: normal;text-background-mode: rounded-box;text-visibility-mode: normal;text-alignment: at-right;}      node.next {size: 20px, 20px;shape: circle;fill-color: darkgreen;text-mode: normal;text-background-mode: rounded-box;text-visibility-mode: normal;text-alignment: at-right;}      node.propagating {size: 20px, 20px;shape: circle;fill-color: darkorange;text-mode: normal;text-background-mode: rounded-box;text-visibility-mode: normal;text-alignment: at-right;}";
		}else if(theme == 1){
			style = "graph { canvas-color: black; fill-mode: gradient-vertical; fill-color: black, #004; padding: 20px; } node { text-visibility-mode: under-zoom; text-visibility: 0.3;text-size: 18; text-color: white;shape: circle; size-mode: dyn-size; size: 10px; fill-mode: gradient-radial; fill-color: #FFFC, #FFF0; stroke-mode: none; shadow-mode: gradient-radial; shadow-color: #FFF5, #FFF0; shadow-width: 5px; shadow-offset: 0px, 0px; } node:clicked { fill-color: #F00A, #F000; } node:selected { fill-color: #00FA, #00F0; } edge { shape: L-square-line; size: 1px; fill-color: #FFF3; fill-mode: plain; arrow-shape: none; } sprite { shape: circle; fill-mode: gradient-radial; fill-color: #FFF8, #FFF0; }";
		}else if(theme == 2){
			style = "graph {fill-color: #f5f5f5;} node { text-size: 18; fill-color: #7ddc1f; text-color: #444444; shape: circle; text-alignment: at-right; stroke-mode:plain; stroke-color:#0073e5; stroke-width:3;} edge{fill-color:#0073e5; shape:blob;size: 3;}";
		}else if(theme == 3){
			style = "graph {fill-color: #1C232B, #2D3645; fill-mode: gradient-vertical;}  edge{arrow-shape:circle;fill-color:#626A77;shape:line;}    node { text-size: 18; fill-color: rgba(0,0,0,0); text-color: #626A77; shape: circle; text-alignment: at-right; stroke-mode:plain; stroke-color:#626A77; stroke-width:3;} edge{fill-color:#626A77; shape:blob;size: 3;}";
		}else if(theme == 4){
			style = "graph { fill-color: black; } edge{fill-color:#626A77;shape:angle;size:15;}        node { size: 15px; fill-color: #44F; stroke-mode: plain; stroke-width: 2px; stroke-color: #CCF; shadow-mode: gradient-radial; shadow-width: 10px; shadow-color: #EEF, #000; shadow-offset: 0px; } edge { fill-color: #CCF; size: 2px; }";
		}else if(theme == 5){
			style = "graph {fill-color: #e1e1e1;}   node { size-mode:dyn-size; size: 20px; fill-color: #4a6084, rgba(0,0,0,0); fill-mode: gradient-radial; stroke-mode:plain; stroke-color:#9ca2c4; stroke-width:3; text-size: 15; text-color: #292929;}   edge{fill-color:#9ca2c4;shape:line;size:10;} ";
		}
		g1.addAttribute("ui.stylesheet", style);
		g2.addAttribute("ui.stylesheet", style);

	}

}
