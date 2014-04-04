package hsleiden.app.comon;

import hsleiden.app.comon.Deelnemers.LoadAllStudentenbedrijfjes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Locale;

import com.example.comonnavigation.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Kalender extends Activity {

	public GregorianCalendar month, itemmonth;// calendar instances.

	public CalendarAdapter adapter;// adapter instance
	public Handler handler;// for grabbing some event values for showing the dot
							// marker.
	public ArrayList<String> items; // container to store calendar items which
									// needs showing the event marker
		
	public TextView evenementTitel, evenementDatum, evenementOmschrijving, evenementLocatie;

	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calendar);
		 Locale.setDefault( Locale.US );
		month = (GregorianCalendar) GregorianCalendar.getInstance();
		itemmonth = (GregorianCalendar) month.clone();

		items = new ArrayList<String>();
		adapter = new CalendarAdapter(this, month);
		
		GridView gridview = (GridView) findViewById(R.id.gridview);
		gridview.setAdapter(adapter);
		
		evenementTitel = (TextView) findViewById(R.id.evenementTitel);
		evenementDatum = (TextView) findViewById(R.id.evenementDatum);
		evenementOmschrijving = (TextView) findViewById(R.id.evenementOmschrijving);
		evenementLocatie = (TextView) findViewById(R.id.evenementLocatie);

		handler = new Handler();
		handler.post(calendarUpdater);

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

				((CalendarAdapter) parent.getAdapter()).setSelected(v);
				String selectedGridDate = CalendarAdapter.dayString
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
				((CalendarAdapter) parent.getAdapter()).setSelected(v);
				
				if (selectedGridDate.equals("2014-01-01"))
				{
					evenementTitel.setText("Sander slaan");
					evenementDatum.setText("2014-01-01");
					evenementOmschrijving.setText("Willem vs Sander, TO THE DEATH");
					evenementLocatie.setText("Noordwijk");
				}
				else if (selectedGridDate.equals("2014-02-02"))
				{
					evenementTitel.setText("Dennis slaan");
					evenementDatum.setText("2014-02-02");
					evenementOmschrijving.setText("Willem vs Sander, TO THE DEATH");
					evenementLocatie.setText("Noordwijk");
				}
				else if (selectedGridDate.equals("2014-03-03"))
				{
					evenementTitel.setText("Peter slaan");
					evenementDatum.setText("2014-03-03");
					evenementOmschrijving.setText("Willem vs Sander, TO THE DEATH");
					evenementLocatie.setText("Noordwijk");
				}
				else if (selectedGridDate.equals("2014-04-04"))
				{
					evenementTitel.setText("Jaimy slaan");
					evenementDatum.setText("2014-04-04");
					evenementOmschrijving.setText("Willem vs Sander, TO THE DEATH");
					evenementLocatie.setText("Noordwijk");
				}
				else if (selectedGridDate.equals("2014-05-05"))
				{
					evenementTitel.setText("Jelle aaien");
					evenementDatum.setText("2014-05-05");
					evenementOmschrijving.setText("Willem vs Sander, TO THE DEATH");
					evenementLocatie.setText("Noordwijk");
				}
				else if (selectedGridDate.equals("2014-06-06"))
				{
					evenementTitel.setText("William slaan");
					evenementDatum.setText("2014-06-06");
					evenementOmschrijving.setText("Willem vs Sander, TO THE DEATH");
					evenementLocatie.setText("Noordwijk");
				}
				else if (selectedGridDate.equals("2014-07-07"))
				{
					evenementTitel.setText("Gydo slaan");
					evenementDatum.setText("2014-07-07");
					evenementOmschrijving.setText("Willem vs Sander, TO THE DEATH");
					evenementLocatie.setText("Noordwijk");
				}
				else if (selectedGridDate.equals("2014-08-08"))
				{
					evenementTitel.setText("Barry slaan");
					evenementDatum.setText("2014-08-08");
					evenementOmschrijving.setText("Willem vs Sander, TO THE DEATH");
					evenementLocatie.setText("Noordwijk");
				}
				else if (selectedGridDate.equals("2014-09-09"))
				{
					evenementTitel.setText("Ami slaan");
					evenementDatum.setText("2014-09-09");
					evenementOmschrijving.setText("Willem vs Sander, TO THE DEATH");
					evenementLocatie.setText("Noordwijk");
				}
				else if (selectedGridDate.equals("2014-10-10"))
				{
					evenementTitel.setText("Rutger slaan");
					evenementDatum.setText("2014-10-10");
					evenementOmschrijving.setText("Willem vs Sander, TO THE DEATH");
					evenementLocatie.setText("Noordwijk");
				}
				else if (selectedGridDate.equals("2014-11-11"))
				{
					evenementTitel.setText("Jos slaan");
					evenementDatum.setText("2014-11-11");
					evenementOmschrijving.setText("Willem vs Sander, TO THE DEATH");
					evenementLocatie.setText("Noordwijk");
				}
				else if (selectedGridDate.equals("2014-12-12"))
				{
					evenementTitel.setText("Vincent slaan");
					evenementDatum.setText("2014-12-12");
					evenementOmschrijving.setText("Willem vs Sander, TO THE DEATH");
					evenementLocatie.setText("Noordwijk");
				}
				else
				{
					evenementTitel.setText("Geen evenement op deze datum");
					evenementDatum.setText("");
					evenementOmschrijving.setText("");
					evenementLocatie.setText("");
				}

	//			showToast(selectedGridDate);

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
		Toast.makeText(this, string, Toast.LENGTH_LONG).show();

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
			String itemvalue;
			for (int i = 0; i < 712; i++) {
				itemvalue = df.format(itemmonth.getTime());
				itemmonth.add(GregorianCalendar.DATE, 1);
				items.add("2014-01-01");
				items.add("2014-02-02");
				items.add("2014-03-03");
				items.add("2014-04-04");
				items.add("2014-05-05");
				items.add("2014-06-06");
				items.add("2014-07-07");
				items.add("2014-08-08");
				items.add("2014-09-09");
				items.add("2014-10-10");
				items.add("2014-11-11");
				items.add("2014-12-12");
			}

			adapter.setItems(items);
			adapter.notifyDataSetChanged();
		}
	};
	
	public void getItems()
	{
		
	String gelezenArray = "";
		
		for (String s : items)
		{
			gelezenArray += s + "\t";
		}
		
	System.out.println(gelezenArray);
	}
}