package org.incha.ui;

import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class OpenFile {
	
	boolean isAlreadyOneClick;

	public void mouseClicked(MouseEvent mouseEvent) {
	    if (isAlreadyOneClick) {
	    	
	        System.out.println("double click");
	        isAlreadyOneClick = false;
	    } else {
	        isAlreadyOneClick = true;
	        Timer t = new Timer("doubleclickTimer", false);
	        t.schedule(new TimerTask() {

	            @Override
	            public void run() {
	                isAlreadyOneClick = false;
	            }
	        }, 500);
	    }
	}
	
	public static void openFile(String dir){
		try{
	        if ((new File(dir)).exists()) {
	            Process p = Runtime
	               .getRuntime()
	               .exec(dir);
	            p.waitFor();

	        } else {
	            System.out.println("File does not exist");
	        }
	      } catch (Exception ex) {
	        ex.printStackTrace();
	      }
		
	}
	
	public static void main (final String args[]){
		openFile(args[0]);	
	}

}
