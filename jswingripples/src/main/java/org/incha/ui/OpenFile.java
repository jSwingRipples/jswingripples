package org.incha.ui;

public class OpenFile {
	
	public static void main (final String args[]){
		try{

	        if ((new File("c:\Users\The Clansman\Desktop\5447730409670.pdf")).exists()) {

	            Process p = Runtime
	               .getRuntime()
	               .exec("rundll32 url.dll,FileProtocolHandler c:\Users\The Clansman\Desktop\5447730409670.pdf");
	            p.waitFor();

	        } else {

	            System.out.println("File does not exist");

	        }

	      } catch (Exception ex) {
	        ex.printStackTrace();
	      }
	}

}
