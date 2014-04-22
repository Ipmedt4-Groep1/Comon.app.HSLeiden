package hsleiden.app.comon;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Locale;

import com.example.comonnavigation.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings.Secure;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Kalender extends Activity {

	ImageButton kalenderMenu;
	
	public GregorianCalendar month, itemmonth;// calendar instances.

	public KalenderAdapter adapter;// adapter instance
	public Handler handler;// for grabbing some event values for showing the dot
							// marker.
	public ArrayList<String> items; // container to store calendar items which
									// needs showing the event marker
		
	public TextView evenementTitel, evenementDatum, evenementOmschrijving, evenementLocatie;

	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kalender_layout);
		 Locale.setDefault( Locale.US );
		month = (GregorianCalendar) GregorianCalendar.getInstance();
		itemmonth = (GregorianCalendar) month.clone();
		buttonListener();

		items = new ArrayList<String>();
		adapter = new KalenderAdapter(this, month);
		
		GridView gridview = (GridView) findViewById(R.id.gridview);
		gridview.setAdapter(adapter);
		
		showToast("Evenementen ophalen..");		
		
		//details van evenementen
		evenementTitel = (TextView) findViewById(R.id.evenementTitel);
		evenementDatum = (TextView) findViewById(R.id.evenementDatum);
		evenementOmschrijving = (TextView) findViewById(R.id.evenementOmschrijving);
		evenementLocatie = (TextView) findViewById(R.id.evenementLocatie);

		handler = new Handler();
		handler.post(calendarUpdater); //update when opened

		TextView title = (TextView) findViewById(R.id.title);
		title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));

		RelativeLayout previous = (RelativeLayout) findViewById(R.id.previous);

		previous.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setPreviousMonth();
				refreshCalendar();
			}
		});

		RelativeLayout next = (RelativeLayout) findViewById(R.id.next);
		next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setNextMonth();
				refreshCalendar();

			}
		});

		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {

				((KalenderAdapter) parent.getAdapter()).setSelected(v);
				String selectedGridDate = KalenderAdapter.dayString //retrieve selected date
						.get(position);
				String[] separatedTime = selectedGridDate.split("-");
				String gridvalueString = separatedTime[2].replaceFirst("^0*",
						"");// taking last part of date. ie; 2 from 2012-12-02.
				int gridvalue = Integer.parseInt(gridvalueString);
				// navigate to next or previous month on clicking offdays.
				if ((gridvalue > 10) && (position < 8)) {
					setPreviousMonth();
					refreshCalendar();
				} else if ((gridvalue < 7) && (position > 28)) {
					setNextMonth();
					refreshCalendar();
				}
				((KalenderAdapter) parent.getAdapter()).setSelected(v);
				//evenementen details
				if (selectedGridDate.equals("2014-01-01"))
				{
					evenementTitel.setText("Nieuwjaarsdag");
					evenementDatum.setText("2014-01-01");
					evenementOmschrijving.setText("Gelukkig nieuw jaar!");
					evenementLocatie.setText("");
				}
				else if (selectedGridDate.equals("2014-02-14"))
				{
					evenementTitel.setText("Valentijnsdag");
					evenementDatum.setText("2014-02-14");
					evenementOmschrijving.setText("Dag van de liefde!");
					evenementLocatie.setText("De kroeg");
				}
				else if (selectedGridDate.equals("2014-03-30"))
				{
					evenementTitel.setText("Zomertijd");
					evenementDatum.setText("2014-03-30");
					evenementOmschrijving.setText("Uurtje minder slapen!");
					evenementLocatie.setText("De wereld");
				}
				else if (selectedGridDate.equals("2014-04-20"))
				{
					evenementTitel.setText("Pasen");
					evenementDatum.setText("2014-04-20");
					evenementOmschrijving.setText("Paaseieren zoeken!");
					evenementLocatie.setText("De achtertuin");
				}
				else if (selectedGridDate.equals("2014-05-05"))
				{
					evenementTitel.setText("Bevrijdingsdag");
					evenementDatum.setText("2014-05-05");
					evenementOmschrijving.setText("Twee minuten stilte.");
					evenementLocatie.setText("Nederland");
				}
				else if (selectedGridDate.equals("2014-06-08"))
				{
					evenementTitel.setText("Pinksteren");
					evenementDatum.setText("2014-06-08");
					evenementOmschrijving.setText("Jezus stond op uit zijn middagdutje.");
					evenementLocatie.setText("");
				}
				else if (selectedGridDate.equals("2014-07-28"))
				{
					evenementTitel.setText("Suikerfeest");
					evenementDatum.setText("2014-07-28");
					evenementOmschrijving.setText("OMNOMNOM");
					evenementLocatie.setText("");
				}
				else if (selectedGridDate.equals("2014-09-16"))
				{
					evenementTitel.setText("Prinsjesdag");
					evenementDatum.setText("2014-09-16");
					evenementOmschrijving.setText("Helemaal niet belangrijk eigenlijk.");
					evenementLocatie.setText("");
				}
				else if (selectedGridDate.equals("2014-10-04"))
				{
					evenementTitel.setText("Dierendag");
					evenementDatum.setText("2014-10-04");
					evenementOmschrijving.setText("Geef m een koekje extra.");
					evenementLocatie.setText("");
				}
				else if (selectedGridDate.equals("2014-12-31"))
				{
					evenementTitel.setText("Oudejaarsdag");
					evenementDatum.setText("2014-12-31");
					evenementOmschrijving.setText("Je bent een runt als je met vuurwerk stunt.");
					evenementLocatie.setText("");
				}
				else
				{
					evenementTitel.setText("Geen evenement op deze datum");
					evenementDatum.setText("");
					evenementOmschrijving.setText("");
					evenementLocatie.setText("");
				}

//				showToast("");

			}
		});
	}
	
	protected void setNextMonth() {
		if (month.get(GregorianCalendar.MONTH) == month
				.getActualMaximum(GregorianCalendar.MONTH)) {
			month.set((month.get(GregorianCalendar.YEAR) + 1),
					month.getActualMinimum(GregorianCalendar.MONTH), 1);
		} else {
			month.set(GregorianCalendar.MONTH,
					month.get(GregorianCalendar.MONTH) + 1);
		}

	}

	protected void setPreviousMonth() {
		if (month.get(GregorianCalendar.MONTH) == month
				.getActualMinimum(GregorianCalendar.MONTH)) {
			month.set((month.get(GregorianCalendar.YEAR) - 1),
					month.getActualMaximum(GregorianCalendar.MONTH), 1);
		} else {
			month.set(GregorianCalendar.MONTH,
					month.get(GregorianCalendar.MONTH) - 1);
		}

	}

	protected void showToast(String string) {
		Toast.makeText(this, string, Toast.LENGTH_SHORT).show();

	} 

	public void refreshCalendar() {
		TextView title = (TextView) findViewById(R.id.title);

		adapter.refreshDays();
		adapter.notifyDataSetChanged();
		handler.post(calendarUpdater); // generate some calendar items

		title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));
	}

	public Runnable calendarUpdater = new Runnable() {

		@Override
		public void run() {
			items.clear();

			// Print dates of the current week
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd",Locale.US);
			// Itemvalue gets used, ignore warning
			String itemvalue;
			for (int i = 0; i < 712; i++) {
				itemvalue = df.format(itemmonth.getTime());
				itemmonth.add(GregorianCalendar.DATE, 1);
				items.add("2014-01-01");
				items.add("2014-02-14");
				items.add("2014-03-30");
				items.add("2014-04-20");
				items.add("2014-05-05");
				items.add("2014-06-08");
				items.add("2014-07-28");
				items.add("2014-09-16");
				items.add("2014-10-04");
				items.add("2014-12-31");
			}

			adapter.setItems(items);
			adapter.notifyDataSetChanged();
		}
	};
	
    private void buttonListener() 
    {
    	kalenderMenu = (ImageButton) findViewById(R.id.imageButton1);
		kalenderMenu.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View arg0) 
			{
				Intent intent = new Intent(getApplicationContext(), Main.class);
                startActivity(intent); 
			}		
		});	
	}
}