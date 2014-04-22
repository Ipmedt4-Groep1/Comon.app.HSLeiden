package hsleiden.app.comon;

import com.example.comonnavigation.R;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.Window;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_activity);
		
		//er wordt een nieuwe thread aangemaakt
		Thread splashscreen = new Thread()
		{
			public void run()
			{
				try
				{
					//na 2 seconden wordt de run methode doorgestart
					sleep(2000);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				finally
				{		
					//het startscherm van de app wordt getoond
					startActivity(new Intent(getApplicationContext(), Main.class));
					finish();					
				}
			}
		};
		//het starten van de thread
		splashscreen.start();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	

}
