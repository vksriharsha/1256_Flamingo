package com.example.flamingo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ShowRoom extends Activity{
	SlideElements obj;
	int current_slide;
	File image;
	File textfile;
	File slide;
	MediaPlayer demoSong;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().getAttributes().windowAnimations = R.style.Fade;
		setContentView(R.layout.showroom);

		findViewById(R.id.button1).setOnClickListener(new View.OnClickListener(){

			public void onClick(View v){

				EditText et= (EditText)findViewById(R.id.editText1);
				String fname= et.getText().toString();

				demoSong = MediaPlayer.create(getApplicationContext(), R.raw.weown);

				 demoSong.start();
				 Toast.makeText(getApplicationContext(), "Demo mp3 Playing...custom mp3 will be available soon",
			                Toast.LENGTH_LONG).show();
				current_slide=0;


				File dir = new File(Environment.getExternalStorageDirectory() + "/"+fname);
				if(dir.exists() && dir.isDirectory()) {



					for(int i=0;;i++)
					{

						slide = new File(Environment.getExternalStorageDirectory()+"/"+fname+"/slide"+(i+1));

						if(slide.exists() && slide.isDirectory())
						{

							obj= new SlideElements();
							for(int j=0;;j++)
							{
								image = new File(Environment.getExternalStorageDirectory()+"/"+fname+"/slide"+(i+1)+"/image"+(j+1)+".png");
								textfile = new File(Environment.getExternalStorageDirectory()+"/"+fname+"/slide"+(i+1)+"/textbox"+(j+1));


								if(image.exists() || textfile.exists()){   

									if(image.exists())
									{

										ImageView myimage= new ImageView(getApplicationContext());
										myimage.setImageBitmap(BitmapFactory.decodeFile(image.getAbsolutePath()));

										obj.image.add(myimage);


									}
									if(textfile.exists())
									{


										try{
											TextView textv = new TextView(getApplicationContext());
											String line;
											StringBuilder textdata = new StringBuilder();
											BufferedReader br= new BufferedReader(new FileReader(textfile));


											while((line=br.readLine())!=null){
												textdata.append(line);
												textdata.append('\n');
											}   
											textv.setText(textdata);
											obj.text.add(textv);
										}
										catch(Exception e){


											e.printStackTrace();
										}


									}




								}


								else
								{
									break;

								}  
							}

							PlaySlide.playslide.add(obj);

						}

						else{

							break;

						}


					}
					
					

					try{
						int msg=XMLReader.parseXML(fname);
					
					}
					catch(Exception e)
					{
						Toast.makeText(getApplicationContext(), "some exception in xml",
								Toast.LENGTH_LONG).show();
						e.printStackTrace();
					}

					
					


					for(int j=0;j<PlaySlide.playslide.get(0).image.size();j++)
					{
						ImageView vi = PlaySlide.playslide.get(0).image.get(j);
						View view3= (View) vi;
						int [] icor = PlaySlide.playslide.get(0).imagecoords.get(j);

						RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);


						params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE); 
						params.leftMargin=70;
						params.topMargin=10+150*j;
						

						RelativeLayout container = (RelativeLayout) findViewById(R.id.screen);

						container.addView(view3,params);


					}

					for(int k=0;k<PlaySlide.playslide.get(0).text.size();k++)
					{
						TextView tv = PlaySlide.playslide.get(0).text.get(k);
						tv.setTextColor(Color.parseColor("#111111"));
						View v4 = (View) tv;
						int [] tcor = PlaySlide.playslide.get(0).textcoords.get(k);

						RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);



						params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE); 
						params.leftMargin=470+30*k;
						params.topMargin=90+50*k;

						RelativeLayout container = (RelativeLayout) findViewById(R.id.screen);

						container.addView(v4,params);
					}


				}
				else
				{
					Toast.makeText(getApplicationContext(), "The presentation doesnot exist",
							Toast.LENGTH_LONG).show();
				}
			}
		});

		findViewById(R.id.imageView2).setOnClickListener(new View.OnClickListener(){

			public void onClick(View v) {

				if(current_slide < PlaySlide.playslide.size()-1)
				{
					current_slide++;

					ViewGroup vg = (ViewGroup) findViewById(R.id.screen);   
					vg.removeAllViews();
					for(int j=0;j<PlaySlide.playslide.get(current_slide).image.size();j++)
					{

						ImageView v1 = PlaySlide.playslide.get(current_slide).image.get(j);
						View view3= (View) v1;
						RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

						params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE); 
						params.leftMargin=70;
						params.topMargin=10+150*j;

						RelativeLayout container = (RelativeLayout) findViewById(R.id.screen);

						container.addView(view3,params);
					}

					for(int i=0;i<PlaySlide.playslide.get(current_slide).text.size();i++)
					{
						TextView tv = PlaySlide.playslide.get(current_slide).text.get(i);
						tv.setTextColor(Color.parseColor("#111111"));
						View v4 = (View) tv;
						RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

						params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE); 
						params.leftMargin=470+30*i;
						params.topMargin=90+50*i;

						RelativeLayout container = (RelativeLayout) findViewById(R.id.screen);

						container.addView(v4,params);
					}
				}

			}
		});

		findViewById(R.id.imageView1).setOnClickListener(new View.OnClickListener(){

			public void onClick(View v) {

				if(current_slide > 0)
				{
					current_slide--;

					ViewGroup vg = (ViewGroup) findViewById(R.id.screen);   
					vg.removeAllViews();
					for(int j=0;j<PlaySlide.playslide.get(current_slide).image.size();j++)
					{
						ImageView v1 = PlaySlide.playslide.get(current_slide).image.get(j);
						View view3= (View) v1;
						RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

						params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE); 
						params.leftMargin=70;
						params.topMargin=10+150*j;

						RelativeLayout container = (RelativeLayout) findViewById(R.id.screen);

						container.addView(view3,params);
					}
					for(int i=0;i<PlaySlide.playslide.get(current_slide).text.size();i++)
					{
						TextView tv = PlaySlide.playslide.get(current_slide).text.get(i);
						tv.setTextColor(Color.parseColor("#111111"));
						View v4 = (View) tv;
						RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

						params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE); 
						params.leftMargin=470+30*i;
						params.topMargin=90+50*i;

						RelativeLayout container = (RelativeLayout) findViewById(R.id.screen);

						container.addView(v4,params);
					}
				}

			}
		});

	}
	
	
	
	
}
