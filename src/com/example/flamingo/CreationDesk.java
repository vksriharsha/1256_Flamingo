package com.example.flamingo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.DragEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Environment;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.Bitmap;


public class CreationDesk extends Activity {

	public int touch_flag=0;
	public static ArrayList<Slides> slide = new ArrayList<Slides>();
	public static int RESULT_LOAD_IMAGE=1;
	public int view_counter=0;
	public int just_clicked;
	public int source;
	public int x,y;
	int img_count;
	int xaxis,yaxis;
	int blinker;
	public ImageView current_image;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().getAttributes().windowAnimations = R.style.Fade;
		setContentView(R.layout.creationdesk);
		Slides slide0 = new Slides();
		 slide.add(slide0);
		 
		 findViewById(R.id.imageView4).setOnClickListener( new View.OnClickListener() {

		       public void onClick(View view)
		  {
		      (Slides.max_slide)++;
		       Slides.current_slide = Slides.max_slide;
		       TextView t=(TextView) findViewById(R.id.textView2);
		       t.setText("Slide "+(Slides.max_slide+1));
		       slide.add(new Slides());
		       ViewGroup vg= (ViewGroup) findViewById(R.id.slidedesk);
		    	       vg.removeAllViews();
		     }
		    });
		 
		 findViewById(R.id.button1).setOnClickListener( new View.OnClickListener() {
			 
		        public void onClick(View v) {

		        EditText ed = (EditText)findViewById(R.id.editText1);
		        String filename = ed.getText().toString();

		        boolean success=false;
		        
		        String path= Environment.getExternalStorageDirectory().toString();
		        new File(path+"/"+filename).mkdirs();

		        for(int i=0;i<slide.size();i++)
		       {
		          new File(path+"/"+filename+"/"+"slide"+(i+1)).mkdirs();
		         for(int j=0;j<slide.get(i).img_list.size() ; j++)
		          {
		            
		           ImageView iv = slide.get(i).img_list.get(j).image;
		         
		            BitmapDrawable bitDw = (BitmapDrawable) iv.getDrawable();
		            Bitmap bitmap = bitDw.getBitmap();

		         
		            String imagename="/"+filename+"/slide"+(i+1)+"/image"+(j+1)+".png"; 
		            File image = new File(path,imagename);
		         
		            FileOutputStream outStream;

		            try {

		              outStream = new FileOutputStream(image);
		              bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream); 
		             

		              outStream.flush();
		              outStream.close();
		              success=true;
		              }
		           catch (Exception e) {
		              e.printStackTrace();
		          } 
		        
		          
		          }
		         for(int j=0;j<slide.get(i).txt_list.size();j++)
		         {
		          TextView txtv = slide.get(i).txt_list.get(j).textView;
		          String textData = txtv.getText().toString();

		            try 
		              {

		                String textname="/"+filename+"/slide"+(i+1)+"/textbox"+(j+1); 
		                File myFile = new File(path,textname);
		                myFile.createNewFile();
		                FileOutputStream fout = new FileOutputStream(myFile);

		                OutputStreamWriter mywriter = new OutputStreamWriter(fout);
		                mywriter.append(textData);
		                mywriter.close();
		                fout.close();
		                if(success)
		                    success=true;
		                    else
		                    success=false;


		               }
		             catch(Exception e) 
		             {
		               e.printStackTrace();
		              }
		            }
		         if (success) {
		              Toast.makeText(getApplicationContext(), "file "+filename+" saved with success",
		                      Toast.LENGTH_LONG).show();
		          } else {
		              Toast.makeText(getApplicationContext(),
		                      "Error during saving of file", Toast.LENGTH_LONG).show();
		          }
		       }
		       
		        try
		        {
		        File xmlFile = new File(path , "/"+filename+"/"+filename+".xml");
		        xmlFile.createNewFile();
		        XMLWrite.writeFile(xmlFile);
		        }
		        catch(Exception e)
		       {
		         e.printStackTrace();
		        }
		    }
		   });
		 
		 findViewById(R.id.imageView3).setOnClickListener( new View.OnClickListener() {

			 public void onClick(View v) {

			File file = new File("/sdcard/");
			Intent intent = new Intent();
			intent.setAction(android.content.Intent.ACTION_VIEW);
			Uri data = Uri.fromFile(file);
			String type = "*/*";
			intent.setDataAndType(data, type);
			startActivity(intent);

			 }
			});

		 
		 
		 
		 
		 findViewById(R.id.imageView6).setOnClickListener( new View.OnClickListener() {
			 
	          public void onClick(View view)
	     {
	        	  if(Slides.current_slide >0)
	        	  {
	         (Slides.current_slide)--;
	         TextView t=(TextView) findViewById(R.id.textView2);
	         t.setText("Slide "+(Slides.current_slide+1));

	         ViewGroup vg= (ViewGroup) findViewById(R.id.slidedesk);
	         vg.removeAllViews(); 

	         Iterator<ImageProps> it = slide.get(Slides.current_slide).img_list.iterator();
	   while(it.hasNext())
	  {
	    ImageProps obj = it.next();
	    
	    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

	    params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE); 
	    params.leftMargin = obj.coords[0];
	    params.topMargin = obj.coords[1];
	    RelativeLayout container = (RelativeLayout) findViewById(R.id.slidedesk);
	    container.addView(obj.image,params);
	    (obj.image).setVisibility(View.VISIBLE);
	    
	      }
	         
	    Iterator<TextProps> it2 = slide.get(Slides.current_slide).txt_list.iterator();
	   while(it2.hasNext())
	  {
	    TextProps obj = it2.next();
	    
	    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

	    params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE); 
	    params.leftMargin = obj.coords[0];
	    params.topMargin = obj.coords[1];
	    RelativeLayout container = (RelativeLayout) findViewById(R.id.slidedesk);
	    container.addView(obj.textView,params);
	    (obj.textView).setVisibility(View.VISIBLE);
	    
	      }
	        	  }
	  }
	  });
		 
		 findViewById(R.id.imageView7).setOnClickListener( new View.OnClickListener() {
			 
	          public void onClick(View view)
	     {
	        	  if(Slides.current_slide <Slides.max_slide)
	        	  {
	         (Slides.current_slide)++;
	         TextView t=(TextView) findViewById(R.id.textView2);
	         t.setText("Slide "+(Slides.current_slide+1));

	         ViewGroup vg= (ViewGroup) findViewById(R.id.slidedesk);
	         vg.removeAllViews(); 

	         Iterator<ImageProps> it = slide.get(Slides.current_slide).img_list.iterator();
	   while(it.hasNext())
	  {
	    ImageProps obj = it.next();
	    
	    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

	    params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE); 
	    params.leftMargin = obj.coords[0];
	    params.topMargin = obj.coords[1];
	    RelativeLayout container = (RelativeLayout) findViewById(R.id.slidedesk);
	    container.addView(obj.image,params);
	    (obj.image).setVisibility(View.VISIBLE);
	    
	      }
	         
	    Iterator<TextProps> it2 = slide.get(Slides.current_slide).txt_list.iterator();
	   while(it2.hasNext())
	  {
	    TextProps obj = it2.next();
	    
	    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

	    params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE); 
	    params.leftMargin = obj.coords[0];
	    params.topMargin = obj.coords[1];
	    RelativeLayout container = (RelativeLayout) findViewById(R.id.slidedesk);
	    container.addView(obj.textView,params);
	    (obj.textView).setVisibility(View.VISIBLE);
	    
	      }
	        	  }
	  }
	  });
		
		findViewById(R.id.imageView1).setOnTouchListener(new OnTouchListener(){
		       public boolean onTouch(View view, MotionEvent motionEvent){
		           
		           if(motionEvent.getAction()== MotionEvent.ACTION_DOWN)
		          {
		     DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view); 
		            view.startDrag(null, shadowBuilder, view, 0); 
		            touch_flag=1;
		            source=1;
		            return true; 
		           }
		           else { 
		            return false; 
		             } 
		         } });
		findViewById(R.id.slidedesk).setOnDragListener( new OnDragListener(){
				
				public boolean onDrag(View layoutview, DragEvent dragevent) {
				    int action = dragevent.getAction();
				    switch (action) {
				    case DragEvent.ACTION_DRAG_STARTED:
				        Log.d("Logcat","Drag event started");
				    break;
				    case DragEvent.ACTION_DRAG_ENTERED:
				      Log.d("LOGCAT", "Drag event entered into "+layoutview.toString());
				    break;
				    case DragEvent.ACTION_DRAG_EXITED:
				      Log.d("LOGCAT", "Drag event exited from "+layoutview.toString());
				    break;
				    case DragEvent.ACTION_DROP:
				    Log.d("LOGCAT", "Dropped");
				    View view = (View) dragevent.getLocalState();
				    
				    ImageView imgv= (ImageView)view;
				    Drawable copyImage = imgv.getDrawable();
				    ImageView image = new ImageView(CreationDesk.this);
				    image.setImageDrawable(copyImage);
				     xaxis = (int) dragevent.getX();
			            yaxis = (int) dragevent.getY();
			           
				    
				      View view2 = (View)image;
				      
				      if(touch_flag==0)
				      {
				    	  view2.setId(view_counter++);
				    	  if(source==1)
				    	  image.setTag(view_counter);
				    	  blinker=0;
				    	 
				    	  view2.setOnClickListener(new View.OnClickListener() {
				              
				              @Override
				              public void onClick(View arg0) {
				                   
				                  Intent i = new Intent(
				                          Intent.ACTION_PICK,
				                          android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				                  just_clicked= arg0.getId();
				                  startActivityForResult(i, RESULT_LOAD_IMAGE);
				                  if(blinker==1)
				                	  slide.get(Slides.current_slide).img_count++;
				              }
				          });
				    	  
				    	  view2.setOnLongClickListener(new OnLongClickListener(){
						       public boolean onLongClick(View view){
						           
						           
						     DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view); 
						            view.startDrag(null, shadowBuilder, view, 0); 
						            source=0;
						            touch_flag=0;
						            return true; 
						          
						         } });
				    	  
				    	 
				    	  

				       }
				      if(touch_flag==1 && source==1)
				      {
				       EditText txt= new EditText(getApplicationContext());
				       txt.setHint("Click to edit text");
				       txt.setSingleLine(false);
				       txt.setTextColor(Color.parseColor("#111111"));
				       
				       txt.setImeOptions(EditorInfo.IME_FLAG_NO_ENTER_ACTION);
				       txt.setOnLongClickListener( new OnLongClickListener(){

				           public boolean onLongClick(View v)
				       {
				        	   EditText newEdit= (EditText)v;
				            String text = newEdit.getText().toString();
				            TextView newText= new TextView(getApplicationContext());
				            newText.setText(text);
				            newText.setSingleLine(false);
				           
				            newText.setHorizontallyScrolling(false);
				            newText.setTextColor(Color.BLACK);
				            newText.setTextSize(20);
				            
				            slide.get(Slides.current_slide).txtv.add(newText);
				            slide.get(Slides.current_slide).txtv_count++;
				            int [] coords = new int[2];
				            coords[0]=xaxis;
				            coords[1]=yaxis;
				            slide.get(Slides.current_slide).txtv_coordinates.add(coords);
				            TextProps theText = new TextProps(newText,coords);
				            
				            slide.get(Slides.current_slide).txt_list.add(theText);
				            newEdit.setTextColor(Color.parseColor("#991111"));
				            return true;
				          }
				        });
				       
				      
				     
				     txt.setOnClickListener( new View.OnClickListener(){

				         public void onClick(View v)
				      {
				        EditText newEdit= (EditText)v;
				        newEdit.setTextColor(Color.parseColor("#111111"));
				         
				        }
				      });
				       view2 = txt;
				       }
				      
				      RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				  	params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE); 
				  	           params.leftMargin = xaxis;
				  	           params.topMargin = yaxis;
				  	           RelativeLayout container = (RelativeLayout) layoutview;
				  	         if(source==0){
				  	           ViewGroup owner = (ViewGroup) view.getParent();
				  	           owner.removeView(view); 
				  	            }
				  	           container.addView(view2,params);
				  	           
				  	         if(touch_flag==0)
				  	       {
				  	        	  
						  	       Iterator<ImageProps> it = slide.get(Slides.current_slide).img_list.iterator();
						  	         while(it.hasNext())
						  	        {
						  	          ImageProps obj = it.next();
						  	        ImageView dragged_image = imgv;
						  	          if(obj.image == dragged_image)
						  	          slide.get(Slides.current_slide).img_list.remove(obj);
						  	          
						  	         }
						  	            
						  	          int [] coords = new int[2];
						  	           coords[0]=xaxis;
						  	           coords[1]=yaxis;

						  	           current_image = (ImageView) view2;
						  	           ImageProps newImg = new ImageProps(current_image,coords);
						  	           
						  	           slide.get(Slides.current_slide).img_list.add(newImg);

				  	          }
				  	           view.setVisibility(View.VISIBLE);
				  	        
				  	          
				      break;
				    case DragEvent.ACTION_DRAG_ENDED:
				        Log.d("LOGCAT", "Drag ended");
				      break;
				    default:
				      break;
				    }
				    return true;
				}

				
		}
				);
		
		
		
		
		
		
		
		findViewById(R.id.imageView2).setOnTouchListener(new OnTouchListener(){
		       public boolean onTouch(View view, MotionEvent motionEvent){
		           
		           if(motionEvent.getAction()== MotionEvent.ACTION_DOWN)
		          {
		     DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view); 
		            view.startDrag(null, shadowBuilder, view, 0); 
		            touch_flag=0;
		            source=1;
		            return true; 
		           }
		           else { 
		            return false; 
		             } 
		         } });
		
		
		
		
	}
	
	
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
         
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
 
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
 
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
             
            ImageView imageView = (ImageView) findViewById(just_clicked);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            if(imageView != null)
            	 blinker=1;
            slide.get(Slides.current_slide).img.add(imageView);

         
        }
     
     
    }
	
	  public void playshow(View view)
	  {
	       Intent intent2 = new Intent(this, ShowRoom.class);
	       startActivity(intent2);
	     }
	 
    }

	



