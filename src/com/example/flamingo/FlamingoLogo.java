package com.example.flamingo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class FlamingoLogo extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().getAttributes().windowAnimations = R.style.Fade;
		setContentView(R.layout.flamingologo);
		
		Thread CreationBench = new Thread(){
		       public void run()
		     {
		        try{
		           sleep(3000);
		            }
		         catch(InterruptedException e)
		         {
		           e.printStackTrace();
		          }
		         finally 
		         {
		           Intent CreationDesk= new Intent("com.example.flamingo.CREATIONDESK");
		           startActivity(CreationDesk);
		         }
		       }
		};

		CreationBench.start();
		
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}

	
	
}
