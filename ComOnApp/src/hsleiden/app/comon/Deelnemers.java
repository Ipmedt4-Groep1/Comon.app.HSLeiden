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
import android.widget.AdapterView.OnItemClickListener;

public class Deelnemers extends ListActivity {

	// Progress Dialog
	private ProgressDialog pDialog;

	// Creating JSON Parser object
	JSONParser jParser = new JSONParser();

	ArrayList<HashMap<String, String>> studentenbedrijfjesList;

	// url to get all deelnemers list
	private static String url_all_studentenbedrijfjes = "http://www.jellescheer.nl/get_all_studentenbedrijfjes.php";

	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_STUDENTENBEDRIJFJES = "studentenbedrijfjes";
	private static final String TAG_PID = "pid";
	private static final String TAG_NAAM = "naam";

	// studenbedrijfjes JSONArray
	JSONArray studentenbedrijfjes = null; 

	ImageButton deelnemersMenu;

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.deelnemers_layout);
		buttonListener();

		// Hashmap for ListView
		studentenbedrijfjesList = new ArrayList<HashMap<String, String>>();

		// Loading deelnemers in Background Thread
		new LoadAllStudentenbedrijfjes().execute();

		// Get listview
		ListView lv = getListView();

		// Bij kiezen van deelnemer
		// Verwijst naar detailpagina deelnemers
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
			{
				//                 getting values from selected ListItem
				String pid = ((TextView) view.findViewById(R.id.pid)).getText().toString();

				Intent in = new Intent(getApplicationContext(),
						DetailDeelnemers.class);

				// sending pid to next activity
				in.putExtra(TAG_PID, pid);

				// starting new activity and expecting some response back
				startActivityForResult(in, 100);

			} });

	}

	// Button verwijst naar Hoofdscherm
	private void buttonListener() 
	{
		deelnemersMenu = (ImageButton) findViewById(R.id.imageButton1);
		deelnemersMenu.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View arg0) 
			{
				Intent intent = new Intent(getApplicationContext(), Main.class);
				startActivity(intent); 
			}		
		});	
	}

	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// if result code 100
		if (resultCode == 100) {
			// if result code 100 is received
			
			// reload this screen again
			Intent intent = getIntent();
			finish();
			startActivity(intent);
		}

	}

	class LoadAllStudentenbedrijfjes extends AsyncTask<String, String, String>
	{
		@Override
		protected void onPreExecute()
		{
			super.onPreExecute();
			pDialog = new ProgressDialog(Deelnemers.this);
			pDialog.setMessage("Loading studentenbedrijfjes. Please wait...");
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
					// Deelnemers gevonden
					// Getting Array of deelnemers
					studentenbedrijfjes = json.getJSONArray(TAG_STUDENTENBEDRIJFJES);

					// looping through All Products
					for (int i = 0; i < studentenbedrijfjes.length(); i++) {
						JSONObject c = studentenbedrijfjes.getJSONObject(i);

						// Storing each json item in variable
						String id = c.getString(TAG_PID);
						String naam = c.getString(TAG_NAAM);


						// creating new HashMap
						HashMap<String, String> map = new HashMap<String, String>();

						// adding each child node to HashMap key => value
						map.put(TAG_PID, id);
						map.put(TAG_NAAM, naam);


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
			// dismiss the dialog after getting all deelnemers
			pDialog.dismiss();
			// updating UI from Background Thread
			runOnUiThread(new Runnable() {
				public void run() {
					/**
					 * Updating parsed JSON data into ListView
					 * */
					ListAdapter adapter = new SimpleAdapter(
							Deelnemers.this, studentenbedrijfjesList,
							R.layout.list_deelnemers_layout, new String[] {TAG_PID,
									TAG_NAAM},
									new int[] { R.id.pid, R.id.naam });
					// updating listview
					setListAdapter(adapter);
				}
			});

		}

	}


}
