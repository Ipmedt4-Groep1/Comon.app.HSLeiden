package hsleiden.app.comon;

import com.example.comonnavigation.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class Info extends Activity
{
	ImageButton infoMenu;
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		 requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.info_layout);
		buttonListener();
	}
	
    private void buttonListener() 
    {
	    infoMenu = (ImageButton) findViewById(R.id.imageButton1);
	    infoMenu.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View arg0) 
			{
				Intent intent = new Intent(getApplicationContext(), Main.class);
                startActivity(intent); 
			}		
		});	
	}
}