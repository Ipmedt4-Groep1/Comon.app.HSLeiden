package hsleiden.app.comon;

import com.example.comonnavigation.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class Opdrachtgevers extends Activity
{
	ImageButton Opdrachtgever1;			//TheArtShop
	ImageButton Opdrachtgever2;			//Acoustic Valley
	ImageButton Opdrachtgever3;			//Area071
	ImageButton Opdrachtgever4;			//Be Silk
	ImageButton Opdrachtgever5;			//BPlusC
	ImageButton Opdrachtgever6;			//Esthetica
	ImageButton Opdrachtgever7;			//Gemeente Leiden			
	ImageButton Opdrachtgever8;			//Gordijn
	ImageButton Opdrachtgever9;			//Hutspop
	ImageButton Opdrachtgever10;		//Leids Lekkers
	ImageButton Opdrachtgever11;		//Naturalis
	ImageButton Opdrachtgever12;		//PaulineAssisteert
	ImageButton Opdrachtgever13;		//Stichting Present
	ImageButton Opdrachtgever14;		//Swerts
	ImageButton Opdrachtgever15;		//Unity FM
	ImageButton Opdrachtgever16;		//Vincentuis' Hof
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.opdrachtgevers_layout);
		buttonListener();
	}
	
	public void buttonListener()
	{
		final Context context = this;

		
		Opdrachtgever1 = (ImageButton) findViewById(R.id.Opdrachtgever1);
		Opdrachtgever1.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0) 
			{
				//Intent intent = new Intent(context, Opdrachtgever1.class);
                //startActivity(intent); 
			}
		});
	}
}
