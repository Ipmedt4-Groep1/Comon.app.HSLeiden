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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
 
public class Opdrachtgevers extends ListActivity {
 
    // Progress Dialog
    private ProgressDialog pDialog;
    ImageView tumb;
    
    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();
 
    ArrayList<HashMap<String, String>> opdrachtgeversList;
 
    // url to get all products list
    private static String url_all_opdrachtgevers = "http://www.jellescheer.nl/get_all_opdrachtgevers.php";
 
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_OPDRACHTGEVERS = "opdrachtgevers";
    private static final String TAG_PID = "pid";
    private static final String TAG_NAAM = "naam";
    private static final String TAG_LOGO = "logo";
    
    ImageButton opdrachtgeverMenu;
    
    // products JSONArray
    JSONArray opdrachtgevers = null;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.opdrachtgevers_layout);
        buttonListener();
 
        // Hashmap for ListView
        opdrachtgeversList = new ArrayList<HashMap<String, String>>();
 
        // Loading products in Background Thread
        new LoadAllOpdrachtgevers().execute();
 
//         Get listview
        ListView lv = getListView();
      
        // on seleting single product
        // launching Edit Product Screen
        lv.setOnItemClickListener(new OnItemClickListener() {
 
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
            {
//                 getting values from selected ListItem
                 String pid = ((TextView) view.findViewById(R.id.pid)).getText().toString();
                 
                 Intent in = new Intent(getApplicationContext(),
                         DetailOpdrachtgevers.class);
                 
                 // sending pid to next activity
                 in.putExtra(TAG_PID, pid);
  
                 // starting new activity and expecting some response back
                 startActivityForResult(in, 100);

            } });
 
    }
    
    private void buttonListener() 
    {
	    opdrachtgeverMenu = (ImageButton) findViewById(R.id.imageButton1);
	    opdrachtgeverMenu.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View arg0) 
			{
				Intent intent = new Intent(getApplicationContext(), Main.class);
                startActivity(intent); 
			}		
		});	
	}
 
    // Response from Edit Product Activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // if result code 100
        if (resultCode == 100) {
            // if result code 100 is received
            // means user edited/deleted product
            // reload this screen again
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
 
    }
 
    /**
     * Background Async Task to Load all product by making HTTP Request
     * */
    class LoadAllOpdrachtgevers extends AsyncTask<String, String, String> {
 
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Opdrachtgevers.this);
            pDialog.setMessage("Loading opdrachtgevers. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
            
            
        }
 
        /**
         * getting All products from url
         * */
        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_all_opdrachtgevers, "GET", params);
 
            // Check your log cat for JSON reponse
            Log.d("All Opdrachtgevers: ", json.toString());
 
            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);
 
                if (success == 1) {
                    // products found
                    // Getting Array of Products
                    opdrachtgevers = json.getJSONArray(TAG_OPDRACHTGEVERS);
 
                    // looping through All Products
                    for (int i = 0; i < opdrachtgevers.length(); i++) {
                        JSONObject c = opdrachtgevers.getJSONObject(i);
 
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
                        opdrachtgeversList.add(map);
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
                            Opdrachtgevers.this, opdrachtgeversList,
                            R.layout.list_opdrachtgevers_layout, new String[] {TAG_PID,
                                    TAG_NAAM, TAG_LOGO},
                            new int[] { R.id.pid, R.id.naam, R.id.logo });
                    // updating listview
                    setListAdapter(adapter);
                }
            });
 
        }
 
    }
}