package hsleiden.app.comon;

import com.example.comonnavigation.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class Deelnemers extends Activity
{

	ImageButton Deelnemer1;		//Bureau5
	ImageButton Deelnemer2;		//Complete
	ImageButton Deelnemer3;		//Echt
	ImageButton Deelnemer4;		//Experienca
	ImageButton Deelnemer5;		//Femotion
	ImageButton Deelnemer6;		//Fresh Events
	ImageButton Deelnemer7;		//Goodfellas			
	ImageButton Deelnemer8;		//Gutenberg & Coster
	ImageButton Deelnemer9;		//MENOK Media
	ImageButton Deelnemer10;	//Peach PR
	ImageButton Deelnemer11;	//Remerkable
	ImageButton Deelnemer12;	//Ristretto
	ImageButton Deelnemer13;	//Sound Marketing
	ImageButton Deelnemer14;	//Spriet
	ImageButton Deelnemer15;	//Streep
	ImageButton Deelnemer16;	//The Inspiration Factory
	ImageButton Deelnemer17;	//Wij Voor Jullie
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.deelnemers_layout);
		buttonListener();
	}
	
	public void buttonListener()
	{
		final Context context = this;
	
		Deelnemer1 = (ImageButton) findViewById(R.id.Deelnemer1);
		Deelnemer1.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0) 
			{
				//Intent intent = new Intent(context, Deelnemer1.class);
	            //startActivity(intent); 
			}
		});
	}
}
