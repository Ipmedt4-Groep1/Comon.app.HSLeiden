package hsleiden.app.comon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.comonnavigation.R;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Stemmen extends ListActivity 
{
	//een ID om te bepalen of er al een stem is uitgebracht
	//deze staat standaard op 0
	static int stemId = 0;
	static String stem;
	
	ImageButton stemMenu;
	
	// Progress Dialog
    private ProgressDialog pDialog;
 
    //JSON Parser object
    JSONParser jParser = new JSONParser();
    
    ArrayList<HashMap<String, String>> studentenbedrijfjesList;
    
    //url om alle studentenbedrijfjes (namen) op te halen
    private static String url_all_studentenbedrijfjes = "http://www.jellescheer.nl/get_all_studentenbedrijfjes.php";
    
    // JSON Node namen die nodig zijn voor de connectie
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_STUDENTENBEDRIJFJES = "studentenbedrijfjes";
    private static final String TAG_PID = "pid";
    private static final String TAG_NAAM = "naam";
    private static final String TAG_STEMMEN = "stemmen";

    //JSONArray
    JSONArray studentenbedrijfjes = null; 
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.stemmen_layout);
		buttonListener();
		
        // Hashmap voor de ListView waar de namen in komen te staan die uit de database worden gehaald
		studentenbedrijfjesList = new ArrayList<HashMap<String, String>>();
        
        // Laden van studentenbedrijfjes in Background Thread
        new LoadAllStudentenbedrijfjes().execute();

        //Get listview
        ListView lv = getListView();
        
        //Bij het klikken op een van de namen wordt het stembevestigingsstem getoond
        lv.setOnItemClickListener(new OnItemClickListener() {
        	 
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) 
            {
            	//als het stemID de waarde 0 heeft wordt de stempagina getoond
				//wanneer op de stemButton wordt gedrukt
            	if (stemId == 0)
				{
            	    // getting values from selected ListItem
                    String pid = ((TextView) view.findViewById(R.id.pid)).getText().toString();
                    
                    Intent in = new Intent(getApplicationContext(), StemBevestiging.class);
                    
                    // sending pid to next activity
                    in.putExtra(TAG_PID, pid);
     
                    // starting new activity and expecting some response back
                    startActivityForResult(in, 100);
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
	
    private void buttonListener() 
    {
    	//als op de menu knop bovenin wordt gedrukt, wordt het startscherm weer getoond
    	stemMenu = (ImageButton) findViewById(R.id.imageButton1);
		stemMenu.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View arg0) 
			{
				Intent intent = new Intent(getApplicationContext(), Main.class);
                startActivity(intent); 
			}		
		});	
	}

	// Response van stem bevestigen
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // if result code 100
        if (resultCode == 100) {
            // if result code 100 is received
            // means user voted on studentenbedrijfje
            // reload this screen again
            Intent intent = new Intent(getApplicationContext(), Main.class);
           //het stemID wordt op 1 gezet in plaats van 0           
            stemId = 1;
            stem = StemBevestiging.stemNaam;
            startActivity(intent);
        }
    }
	
	class LoadAllStudentenbedrijfjes extends AsyncTask<String, String, String>
	{
		@Override
		protected void onPreExecute()
		{
			super.onPreExecute();
            pDialog = new ProgressDialog(Stemmen.this);
            pDialog.setMessage("Loading stemopties. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
		}
		
		protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_all_studentenbedrijfjes, "GET", params);
 
            // Check your log cat for JSON reponse
            Log.d("All Opdrachtgevers: ", json.toString());
 
            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);
 
                if (success == 1) {
                    // products found
                    // Getting Array of Products
                    studentenbedrijfjes = json.getJSONArray(TAG_STUDENTENBEDRIJFJES);
 
                    // looping through All Products
                    for (int i = 0; i < studentenbedrijfjes.length(); i++) {
                        JSONObject c = studentenbedrijfjes.getJSONObject(i);
 
                        // Storing each json item in variable
                        String id = c.getString(TAG_PID);
                        String naam = c.getString(TAG_NAAM);
                        String stem = c.getString(TAG_STEMMEN);
 
                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();
 
                        // adding each child node to HashMap key => value
                        map.put(TAG_PID, id);
                        map.put(TAG_NAAM, naam);
                        map.put(TAG_STEMMEN, stem);
 
                        // adding HashList to ArrayList
                        studentenbedrijfjesList.add(map);
                    }
                }}
                    
            catch (JSONException e) {
                e.printStackTrace();
            }
 
            return null;
        }
 
		 /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * */
                    ListAdapter adapter = new SimpleAdapter(
                            Stemmen.this, studentenbedrijfjesList,
                            R.layout.list_stemmen_layout, new String[] {TAG_PID,
                                    TAG_NAAM, TAG_STEMMEN},
                            new int[] { R.id.pid, R.id.naam, R.id.stemmen});
                    //listview updaten
                    setListAdapter(adapter);
                }
            });
 
        }
 
    }
}
