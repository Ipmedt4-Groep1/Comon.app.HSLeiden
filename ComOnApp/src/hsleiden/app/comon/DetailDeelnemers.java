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
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("NewApi")
public class DetailDeelnemers extends Activity {
	 
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
 
    // single product url
    private static final String url_product_detials = "http://www.jellescheer.nl/get_studentenbedrijfjes_details.php";
 
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
 
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressLint("NewApi")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.detail_deelnemers);
        
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
     * Background Async Task to Get complete product details
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
 
                            // product with this pid found
                            // Edit Text
                            txtNaam = (TextView) findViewById(R.id.inputNaam);
                            txtSoort = (TextView) findViewById(R.id.inputSoort);
                            txtBeschrijving = (TextView) findViewById(R.id.inputBeschrijving);
                            txtWebsite = (TextView) findViewById(R.id.inputWebsite);
                            txtOpdrachtgever = (TextView) findViewById(R.id.inputOpdrachtgever);
                            txtDeelnemers = (TextView) findViewById(R.id.inputDeelnemers);
                            txtLikes = (TextView) findViewById(R.id.inputLikes);
                            
 
                            // display product data in EditText
                            txtNaam.setText(product.getString(TAG_NAAM));
                            txtSoort.setText(product.getString(TAG_SOORT));
                            txtBeschrijving.setText(product.getString(TAG_BESCHRIJVING));
                            txtWebsite.setText(product.getString(TAG_WEBSITE));
                            txtOpdrachtgever.setText(product.getString(TAG_OPDRACHTGEVER));
                            txtDeelnemers.setText(product.getString(TAG_DEELNEMERS));
                            txtLikes.setText(product.getString(TAG_LIKES));
                           
                            // Afbeelding weergeven
                            int loader = R.drawable.loader; 
                            String imageURL = product.getString(TAG_LOGO_FULL);
                            
                            ImageView imagePhoto = (ImageView) findViewById(R.id.logo);    
                            ImageLoader imgLoader = new ImageLoader(getApplicationContext());        
                            imgLoader.DisplayImage(imageURL, loader, imagePhoto);
                            
                                
                            
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
 
    }
