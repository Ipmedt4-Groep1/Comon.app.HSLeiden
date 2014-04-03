package hsleiden.app.comon;

import com.example.comonnavigation.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class Main extends Activity {
	
	ImageButton opdrachtgeverButton;
	ImageButton deelnemerButton;
	ImageButton kalenderButton;
	ImageButton stemButton;
	ImageButton infoButton;
	ImageButton nieuwsButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		buttonListener();
		
	}
	
	//de buttonListener methode zorgt ervoor dat er een nieuwe activity (pagina in de app) wordt aangeroepen wanneer
	//op een van de knoppen op de menu pagina wordt gedrukt.
	public void buttonListener()  
	{
		final Context context = this;
		//de knop voor de betrokken opdrachtgevers
		opdrachtgeverButton = (ImageButton) findViewById(R.id.opdrachtgeverButton);
		opdrachtgeverButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0) 
			{
				Intent intent = new Intent(context, Opdrachtgevers.class);
                startActivity(intent); 
			}
		});
		//de knop voor de deelnemende studentenbedrijfjes
		deelnemerButton = (ImageButton) findViewById(R.id.deelnemerButton);
		deelnemerButton.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View arg0) 
			{
				Intent intent = new Intent(context, Deelnemers.class);
                startActivity(intent); 
			}		
		});	
		//de knop voor de kalender
		kalenderButton = (ImageButton) findViewById(R.id.kalenderButton);
		kalenderButton.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View arg0) 
			{
				Intent intent = new Intent(context, Kalender.class);
                startActivity(intent); 
			}
		});	
		//de knop voor de informatie over ComOn
		infoButton = (ImageButton) findViewById(R.id.infoButton);
		infoButton.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View arg0) 
			{
				Intent intent = new Intent(context, Info.class);
                startActivity(intent); 
			}		
		});	
		//de knop voor het laatste nieuws
		nieuwsButton = (ImageButton) findViewById(R.id.nieuwsButton);
		nieuwsButton.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View arg0) {

				Intent intent = new Intent(context, Nieuws.class);
                startActivity(intent); 
			}		
		});	
		//de knop om te kunnen stemmen
		stemButton = (ImageButton) findViewById(R.id.stemButton);
		stemButton.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View arg0) 
			{
				Intent intent = new Intent(context, Stemmen.class);
                startActivity(intent); 
			}		
		});	
	}
}
