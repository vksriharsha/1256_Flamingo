package com.example.flamingo;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class FirstActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().getAttributes().windowAnimations = R.style.Fade;
		setContentView(R.layout.activity_first);
		

		 Thread timer= new Thread(){
		            
		         public void run(){
		             
		        try{
		            sleep(2000);
		            }
		        catch(InterruptedException e)
		          {
		            e.printStackTrace();
		           }
		        finally
		          {
		            Intent flamingologo= new Intent("com.example.flamingo.FLAMINGOLOGO");
		            startActivity(flamingologo);
		          }
		         }
		 };
		       
		       timer.start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.first, menu);
		return true;
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
	
	

}
