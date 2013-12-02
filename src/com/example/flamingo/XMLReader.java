package com.example.flamingo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.os.Environment;
import android.widget.Toast;

public class XMLReader {
	static int coords[];
	
	public static int parseXML(String filename) throws XmlPullParserException, IOException {

		int current=0,sign=0;
		int marker=0;
		boolean isimage = false;
		
	     try
	      {
	        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();

	        factory.setNamespaceAware(true);

	        XmlPullParser xpp = factory.newPullParser();

	        // get a reference to the file.

	        File file = new File(Environment.getExternalStorageDirectory() +"/"+filename+"/"+filename+".xml");

	        

	        // create an input stream to be read by the stream reader.

	        FileInputStream fis = new FileInputStream(file);

	 
	        // set the input for the parser using an InputStreamReader

	        xpp.setInput(new InputStreamReader(fis));

	        coords = new int[2];

	        int eventType = xpp.getEventType();
	       
	        while(eventType != XmlPullParser.END_DOCUMENT) {
	     
	        	sign++;
	        	
	               if(eventType==XmlPullParser.START_TAG)
	             {
	            	   marker=0;
	            	  
	                 String tag= xpp.getName();
	                 
	                
	                if(tag.equals("xcoord"))
	               {
	                String x= xpp.nextText();
	                coords[0]= Integer.parseInt(x);
	                }
	               else if(tag.equals("ycoord"))
	               {
	                String y = xpp.nextText();
	                coords[1]=Integer.parseInt(y);
	                marker=1;
	               }
	              
	             if(marker==1) 
	             PlaySlide.playslide.get(current).imagecoords.add(coords);
	             
	            
	             }

	            


	        	eventType = xpp.next();
	        }
	          }

	     


	      
	     catch(Exception e)
	     {
	    	 sign=-1;
	       e.printStackTrace();
	      }
	     return sign;
	        }

}
