package com.example.flamingo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.StringWriter;

import org.xmlpull.v1.XmlSerializer;

import android.util.Xml;

public class XMLWrite {
	
	public static void writeFile(File filename)
	{

	 String xcoord;
	 String ycoord;

	  try
	  {
	   FileOutputStream fos= new FileOutputStream(filename); 
	    XmlSerializer xmlSerializer = Xml.newSerializer();              
	     StringWriter writer = new StringWriter();

	      xmlSerializer.setOutput(writer);
	 
	     xmlSerializer.startDocument("UTF-8",true);

	     xmlSerializer.startTag(null,"presentation");


	 
	  for(int i=0;i<CreationDesk.slide.size();i++)
	 {

	    xmlSerializer.startTag(null,"slide"+(i+1));
	   for(int j=0;j<CreationDesk.slide.get(i).img_list.size() ; j++)
	    {

	     int x= (CreationDesk.slide.get(i)).img_list.get(j).coords[0];
	     int y= (CreationDesk.slide.get(i)).img_list.get(j).coords[1];

	     xcoord= Integer.toString(x);
	     ycoord= Integer.toString(y);
	  
	     xmlSerializer.startTag(null,"image"+(j+1));
	     xmlSerializer.startTag(null,"xcoord");
	     xmlSerializer.text(xcoord);
	     xmlSerializer.endTag(null,"xcoord");
	     xmlSerializer.startTag(null,"ycoord");
	     xmlSerializer.text(ycoord);
	     xmlSerializer.endTag(null,"ycoord");
	     xmlSerializer.endTag(null,"image"+(j+1));
	    }
	   
	   for(int j=0;j<CreationDesk.slide.get(i).txt_list.size() ; j++)
	    {
	      int x= (CreationDesk.slide.get(i)).txt_list.get(j).coords[0];
	      int y= (CreationDesk.slide.get(i)).txt_list.get(j).coords[1];


	      xcoord= Integer.toString(x);
	      ycoord= Integer.toString(y);
		  
		     xmlSerializer.startTag(null,"text"+(j+1));
		     xmlSerializer.startTag(null,"xcoord");
		     xmlSerializer.text(xcoord);
		     xmlSerializer.endTag(null,"xcoord");
		     xmlSerializer.startTag(null,"ycoord");
		     xmlSerializer.text(ycoord);
		     xmlSerializer.endTag(null,"ycoord");
		     xmlSerializer.endTag(null,"text"+(j+1));
		    }

	        
	     xmlSerializer.endTag(null,"slide"+(i+1));
	    }
	     xmlSerializer.endTag(null,"presentation");

	     xmlSerializer.endDocument();
	     xmlSerializer.flush();
	     String dataWrite=writer.toString();
	     fos.write(dataWrite.getBytes());
	     fos.close();
	    }
	    
	     catch(Exception e)
	    {
	      e.printStackTrace();
	     }
	}

}
