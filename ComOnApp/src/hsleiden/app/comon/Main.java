package hsleiden.app.comon;

import com.example.comonnavigation.R;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

public class Main extends Activity {
	
	//variabelen die nodig zijn in deze klasse
	ImageButton opdrachtgeverButton;
	ImageButton deelnemerButton;
	ImageButton kalenderButton;
	static ImageButton stemButton;
	ImageButton infoButton;
	ImageButton nieuwsButton;
	
	//een ID om te bepalen of er al een stem is uitgebracht
	//deze staat standaard op 0
	static int stemId = 0;
	static String stem;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
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
				//huidige intent wordt vervangen voor de klasse van de doelpagina
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
				//huidige intent wordt vervangen voor de klasse van de doelpagina
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
				//huidige intent wordt vervangen voor de klasse van de doelpagina
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
				//huidige intent wordt vervangen voor de klasse van de doelpagina
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

				//huidige intent wordt vervangen voor de klasse van de doelpagina
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
				//als het stemID de waarde 0 heeft wordt de stempagina getoond
				//wanneer op de stemButton wordt gedrukt
				if (stemId == 0)
				{
					Intent intent = new Intent(context, Stemmen.class);
	                startActivity(intent);
	            }
				//wanneer het stemID een andere waarde heeft wordt er een toast bericht
				//getoond met daarin de melding dat er al gestemd is op een van de 
				//studentenbedrijfjes (en op welke)
				else
				{
					Toast.makeText(getApplicationContext(), "U heeft al gestemd op " + stem + "." + "\n" + "U kunt niet meer stemmen", Toast.LENGTH_LONG).show();
				}
			}		
		});	
	}
	
	//met deze methode wordt het resultaatcode van updaten van de stemmen in de database opgehaald
	//wanneer deze overeenkomt met 100, wordt de stemButton gedisabled en kan de stempagina niet
	//meer worden bereikt
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // if result code 100
        if (resultCode == 100) {
            // if result code 100 is received
            // means user edited/deleted product
          stemButton.setEnabled(false);
        }
    }
}
