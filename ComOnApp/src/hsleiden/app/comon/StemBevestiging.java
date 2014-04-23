package hsleiden.app.comon;

import java.util.ArrayList;
import java.util.List;

import com.example.comonnavigation.R;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
 
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("NewApi")
public class StemBevestiging extends Activity {
	
	//variabelen die nodig zijn in deze klasse
	static ImageButton bevestigButton;
	TextView txtStemmen;
	public String aantalstemmen;
	
    TextView txtNaam;
    TextView txtBeschrijving;
    TextView txtWebsite;
    TextView txtLikes;
    TextView txtSoort;
    TextView txtDeelnemers;
    TextView txtOpdrachtgever;
    String pid;
    static String stemNaam;
 
    // Progress Dialog
    private ProgressDialog pDialog;
 
    // JSON parser
    JSONParser jsonParser = new JSONParser();
 
    //url's die we nodig hebben vanaf de server
    private static final String url_product_detials = "http://www.jellescheer.nl/get_stem_details.php";
    
	private static final String url_update_stemmen = "http://www.jellescheer.nl/update_stemmen.php";

    // JSON Node namen
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_STUDENTENBEDRIJFJES = "studentenbedrijfjes";
    private static final String TAG_PID = "pid";
    private static final String TAG_NAAM = "naam";
    private static final String TAG_OPDRACHTGEVER = "opdrachtgever";
    private static final String TAG_DEELNEMERS = "deelnemers";
    private static final String TAG_SOORT = "soort";
    private static final String TAG_STEMMEN = "stemmen";
    
    public int stemaantal;
 
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressLint("NewApi")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stem_bevestiging_layout);
        buttonListener();
             
         // getting product details from intent
        Intent i = getIntent();
 
        // getting product id (pid) from intent
        pid = i.getStringExtra(TAG_PID);
 
        // Getting complete product details in background thread
        new GetProductDetails().execute();
        
        if (android.os.Build.VERSION.SDK_INT > 8) 
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }
 
    /**
     * Background Async Task to Get complete stem details
     * */
    class GetProductDetails extends AsyncTask<String, String, String> {
 
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(StemBevestiging.this);
            pDialog.setMessage("Stemopties laden, Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
 
        /**
         * Getting product details in background thread
         * */
        protected String doInBackground(String... params) {
 
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    // Check for success tag
                    int success;
                    try {
                        // Building Parameters
                        List<NameValuePair> params = new ArrayList<NameValuePair>();
                        params.add(new BasicNameValuePair("pid", pid));
 
                        // getting product details by making HTTP request
                        // Note that product details url will use GET request
                        JSONObject json = jsonParser.makeHttpRequest(
                                url_product_detials, "GET", params);
 
                        // check your log for json response
                        Log.d("Single Product Details", json.toString());
 
                        // json success tag
                        success = json.getInt(TAG_SUCCESS);
                        if (success == 1) {
                            // successfully received product details
                            JSONArray productObj = json
                                    .getJSONArray(TAG_STUDENTENBEDRIJFJES); // JSON Array
 
                            // get first product object from JSON Array
                            JSONObject product = productObj.getJSONObject(0);
 
                            // studentenbedrijfje with this pid found
                            // Edit Text
                            txtNaam = (TextView) findViewById(R.id.inputNaam);
                            txtSoort = (TextView) findViewById(R.id.inputSoort);
                            txtOpdrachtgever = (TextView) findViewById(R.id.inputOpdrachtgever);
                            txtDeelnemers = (TextView) findViewById(R.id.inputDeelnemers);
                            txtStemmen = (TextView) findViewById(R.id.aantalstemmen);
                            //txtStemmen.setVisibility(View.GONE);
                                                        
                             // info studentenbedrijfe in EditText
                            txtNaam.setText(product.getString(TAG_NAAM));
                            txtSoort.setText(product.getString(TAG_SOORT));
                            txtOpdrachtgever.setText(product.getString(TAG_OPDRACHTGEVER));
                            txtDeelnemers.setText(product.getString(TAG_DEELNEMERS));
                            txtStemmen.setText(product.getString(TAG_STEMMEN));
                            txtStemmen.setKeyListener(null);
                        }else{
                            // product with pid not found
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
 
            return null;
        }
 
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once got all details
            pDialog.dismiss();
        }
    }
    
    //methode om de knop te laten functioneren
    public void buttonListener()  
	{
        bevestigButton = (ImageButton) findViewById(R.id.bevestigButton); 
        bevestigButton.setOnClickListener(new OnClickListener()
		{
        	
			@Override
			public void onClick(View arg0) 
			{
					//methode om het aantal stemmen te verhogen aanroepen
					verhoogAantalStemmen(stemaantal);		
									
					// starting background task to update stemmen
					new updateStemmen().execute();
			}
		});
    }

    //methode om het aantal stemmen uit naar een int te zetten
    //en deze met 1 te verhogen
	protected void verhoogAantalStemmen(int stemaantal) 
	{
		String aantalstemmen = txtStemmen.getText().toString();
		stemaantal = Integer.parseInt(aantalstemmen);
		stemaantal = stemaantal + 1;
		txtStemmen.setText(Integer.toString(stemaantal));
	}
    
	class updateStemmen extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(StemBevestiging.this);
			pDialog.setMessage("Stem verwerken ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * Saving stemmen
		 * */
		protected String doInBackground(String... args) {

			// getting updated data from EditTexts
			String stemmen = txtStemmen.getText().toString();

			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair(TAG_PID, pid));
			params.add(new BasicNameValuePair(TAG_STEMMEN, stemmen));

			// sending modified data through http request
			// Notice that update product url accepts POST method
			JSONObject json = jsonParser.makeHttpRequest(url_update_stemmen,
					"POST", params);

			// check json success tag
			try {
				int success = json.getInt(TAG_SUCCESS);
				
				if (success == 1) {
					// successfully updated
					Intent i = getIntent();
					// send result code 100 to notify about product update
					setResult(100, i);
					//naam van het studentenbedrijfje wordt naar een string vertaald voor op het startscherm
					stemNaam = txtNaam.getText().toString();
					finish();

				} else {
					// failed to update product
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}


		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once product updated
			pDialog.dismiss();

		}
	}

}
