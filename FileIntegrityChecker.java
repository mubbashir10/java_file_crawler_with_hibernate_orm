import java.util.*;
import java.io.*;
import java.security.*;
import java.security.*;

public class FileIntegrityChecker{

	//hashing file
    private void hashFile(File file){

		try{

			FileInputStream inputStream = new FileInputStream(file);
        	MessageDigest digest = MessageDigest.getInstance("SHA-1");

	        //file buffer array
	        byte[] bytesBuffer = new byte[1024];
	        int bytesRead = -1;
	 
	        //reading bytes into MessaageDigest object
	        while ((bytesRead = inputStream.read(bytesBuffer)) != -1) {
	            digest.update(bytesBuffer, 0, bytesRead);
	        }
	 		
	 		//creating byte array
	        byte[] hashedBytes = digest.digest();
	    	StringBuffer stringBuffer = new StringBuffer();

	    	//converting bytes to hexadecimal
	    	for (int i = 0; i < hashedBytes.length; i++) {
	            stringBuffer.append(Integer.toString((hashedBytes[i] & 0xff) + 0x100, 16).substring(1));
        	}

        	//final hash
        	String sha1Hash = stringBuffer.toString();

	    	System.out.println("SHA-1 Hash: " + sha1Hash); 
	    }
	    catch(Exception e){
	    	e.printStackTrace();
	    }	
	}
 
 	//crawler method
	public boolean crawler(File dir){

        if (dir.exists()){

            //File array
            File[] list = dir.listFiles();

            //empty directory
            if (list == null){
            	System.out.println("The entered directory is empty!");
            	return false;
            }	

            //walking
            for ( File f : list ) {

            	//directory
                if (f.isDirectory())
                    crawler( new File(f.getAbsolutePath()));
                //file
                else
                	hashFile(f);
            }

            return true;    
	    }
        else{
            System.out.println("Directory doesn't exist!");
            return false;
        }
    }

    //main method
    public static void main(String[] args) {

    	FileIntegrityChecker fic = new FileIntegrityChecker();
        
        //user input
        Scanner input = new Scanner(System.in);
        System.out.println("Enter directory you would like to crawl");
        
        //making file
        String userDir = input.nextLine();
        File dir = new File(userDir);
        
        //crawling
        fic.crawler(dir);
 
    }	
}