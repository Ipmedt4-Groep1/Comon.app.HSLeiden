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
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class Deelnemers extends ListActivity {
	
	// Progress Dialog
    private ProgressDialog pDialog;
 
    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();
    
    ArrayList<HashMap<String, String>> studentenbedrijfjesList;
    
    // url to get all products list
    private static String url_all_studentenbedrijfjes = "http://www.jellescheer.nl/get_all_studentenbedrijfjes.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_STUDENTENBEDRIJFJES = "studentenbedrijfjes";
    private static final String TAG_PID = "pid";
    private static final String TAG_NAAM = "naam";
    private static final String TAG_LOGO = "logo";
    
    // products JSONArray
    JSONArray studentenbedrijfjes = null; 
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.deelnemers_layout);
		
        // Hashmap for ListView
		studentenbedrijfjesList = new ArrayList<HashMap<String, String>>();
        
        // Loading products in Background Thread
        new LoadAllStudentenbedrijfjes().execute();

        // Get listview
        ListView lv = getListView();
        
        // on seleting single product
        // launching Edit Product Screen
        lv.setOnItemClickListener(new OnItemClickListener() {
 
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
            {
                 //getting values from selected ListItem
                 //String pid = ((TextView) view.findViewById(R.id.pid)).getText().toString();

             }
        });
 
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
                    // products found
                    // Getting Array of Products
                    studentenbedrijfjes = json.getJSONArray(TAG_STUDENTENBEDRIJFJES);
 
                    // looping through All Products
                    for (int i = 0; i < studentenbedrijfjes.length(); i++) {
                        JSONObject c = studentenbedrijfjes.getJSONObject(i);
 
                        // Storing each json item in variable
                        String id = c.getString(TAG_PID);
                        String naam = c.getString(TAG_NAAM);
                        String logo = c.getString(TAG_LOGO);
 
                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();
 
                        // adding each child node to HashMap key => value
                        map.put(TAG_PID, id);
                        map.put(TAG_NAAM, naam);
                        map.put(TAG_LOGO, logo);
 
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
                            Deelnemers.this, studentenbedrijfjesList,
                            R.layout.list_deelnemers_layout, new String[] {TAG_PID,
                                    TAG_NAAM, TAG_LOGO},
                            new int[] { R.id.pid, R.id.naam, R.id.logo });
                    // updating listview
                    setListAdapter(adapter);
                }
            });
 
        }
 
    }
	
	
}
