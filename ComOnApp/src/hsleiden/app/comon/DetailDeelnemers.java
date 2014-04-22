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
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("NewApi")
public class DetailDeelnemers extends Activity {

	public String aantalLikes;
	static ImageButton likeButton;
	static ImageButton terugButton;

	TextView txtNaam;
	TextView txtBeschrijving;
	TextView txtWebsite;
	TextView txtLikes;
	TextView txtSoort;
	TextView txtDeelnemers;
	TextView txtOpdrachtgever;
	ImageView txtLogo;
	String pid;

	// Progress Dialog
	private ProgressDialog pDialog;

	// JSON parser class
	JSONParser jsonParser = new JSONParser();

	// single deelnemer url
	private static final String url_deelnemer_detials = "http://www.jellescheer.nl/get_studentenbedrijfjes_details.php";

	private static final String url_update_likes = "http://www.jellescheer.nl/update_likes.php";

	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_STUDENTENBEDRIJFJES = "studentenbedrijfjes";
	private static final String TAG_PID = "pid";
	private static final String TAG_NAAM = "naam";
	private static final String TAG_OPDRACHTGEVER = "opdrachtgever";
	private static final String TAG_BESCHRIJVING = "beschrijving";
	private static final String TAG_DEELNEMERS = "deelnemers";
	private static final String TAG_WEBSITE = "website";
	private static final String TAG_SOORT = "soort";
	private static final String TAG_LIKES = "likes";
	private static final String TAG_LOGO_FULL = "logo_full";

	public int likeaantal;

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.detail_deelnemers);
		buttonListener();

		// getting deelnemer details from intent
		Intent i = getIntent();

		// getting deelnemer id (pid) from intent
		pid = i.getStringExtra(TAG_PID);

		// Getting complete deelnemer details in background thread
		new GetProductDetails().execute();                 

		if (android.os.Build.VERSION.SDK_INT > 8) 
		{
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}

	}

	/**
	 * Background Async Task to Get complete deelnemer details
	 * */
	class GetProductDetails extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(DetailDeelnemers.this);
			pDialog.setMessage("Loading Deelnemer, Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * Getting deelnemer details in background thread
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

						// getting deelnemer details by making HTTP request
						// Note that deelnemer details url will use GET request
						JSONObject json = jsonParser.makeHttpRequest(
								url_deelnemer_detials, "GET", params);

						// check your log for json response
						Log.d("Single Product Details", json.toString());

						// json success tag
						success = json.getInt(TAG_SUCCESS);
						if (success == 1) {
							// successfully received deelnemer details
							JSONArray productObj = json
									.getJSONArray(TAG_STUDENTENBEDRIJFJES); // JSON Array

							// get first deelnemer object from JSON Array
							JSONObject deelnemer = productObj.getJSONObject(0);

							// deelnemer with this pid found
							// Edit Text
							txtNaam = (TextView) findViewById(R.id.inputNaam);
							txtSoort = (TextView) findViewById(R.id.inputSoort);
							txtBeschrijving = (TextView) findViewById(R.id.inputBeschrijving);
							txtWebsite = (TextView) findViewById(R.id.inputWebsite);
							txtOpdrachtgever = (TextView) findViewById(R.id.inputOpdrachtgever);
							txtDeelnemers = (TextView) findViewById(R.id.inputDeelnemers);
							txtLikes = (TextView) findViewById(R.id.inputLikes);


							// display deelnemer data 
							txtNaam.setText(deelnemer.getString(TAG_NAAM));
							txtSoort.setText(deelnemer.getString(TAG_SOORT));
							txtBeschrijving.setText(deelnemer.getString(TAG_BESCHRIJVING));
							txtWebsite.setText(deelnemer.getString(TAG_WEBSITE));
							txtOpdrachtgever.setText(deelnemer.getString(TAG_OPDRACHTGEVER));
							txtDeelnemers.setText(deelnemer.getString(TAG_DEELNEMERS));
							txtLikes.setText(deelnemer.getString(TAG_LIKES));

							// Afbeelding weergeven
							int loader = R.drawable.loader; 
							String imageURL = deelnemer.getString(TAG_LOGO_FULL);

							ImageView imagePhoto = (ImageView) findViewById(R.id.logo);    
							ImageLoader imgLoader = new ImageLoader(getApplicationContext());        
							imgLoader.DisplayImage(imageURL, loader, imagePhoto);



						}else{
							// deelnemer with pid not found
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

	public void buttonListener()  
	{
		likeButton = (ImageButton) findViewById(R.id.like); 
		likeButton.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0) 
			{
				verhoogAantalLikes(likeaantal);		

				// starting background task to update deelnemer
				new updateLikes().execute();
			}
		});
		terugButton = (ImageButton) findViewById(R.id.imageButton1); 
		terugButton.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0) 
			{
				Intent intent = new Intent(getApplicationContext(), Deelnemers.class);
				startActivity(intent);
			}
		});
	}

	protected void verhoogAantalLikes(int stemaantal) 
	{
		String aantalLikes = txtLikes.getText().toString();
		likeaantal = Integer.parseInt(aantalLikes);
		likeaantal = likeaantal + 1;
		txtLikes.setText(Integer.toString(likeaantal));
	}

	class updateLikes extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(DetailDeelnemers.this);
			pDialog.setMessage("Stem verwerken ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * Saving deelnemer
		 * */
		protected String doInBackground(String... args) {

			// getting updated data from EditTexts
			String likes = txtLikes.getText().toString();

			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair(TAG_PID, pid));
			params.add(new BasicNameValuePair(TAG_LIKES, likes));

			// sending modified data through http request
			// Notice that update deelnemer url accepts POST method
			JSONObject json = jsonParser.makeHttpRequest(url_update_likes,
					"POST", params);

			// check json success tag
			try {
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					// successfully updated
					Intent i = getIntent();
					// send result code 100 to notify about deelnemer update
					setResult(100, i);
					startActivity(i);



				} else {
					// failed to update deelnemer
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
			// dismiss the dialog once deelnemer uupdated
			pDialog.dismiss();

		}
	}


}
