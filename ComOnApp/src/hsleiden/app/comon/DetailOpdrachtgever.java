package hsleiden.app.comon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.comonnavigation.R;

public class DetailOpdrachtgever extends Activity
{	
	public TextView detailinformatie;
	int opdrachtgeverID;

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.opdrachtgever_detail_layout);	
		
		detailinformatie = (TextView) findViewById(R.id.textView1);
		setInfo(opdrachtgeverID);
	}
	
	public void setInfo(int opdrachtgeverID)
	{
		if (Opdrachtgevers.opdrachtgeverID == 1)
			{
		 		detailinformatie.setText("Opdrachtgever voor A");
			}
		if (Opdrachtgevers.opdrachtgeverID == 2)
			{
	     		detailinformatie.setText("Opdrachtgever voor B");
			}
		if (Opdrachtgevers.opdrachtgeverID == 3)
			{
	     		detailinformatie.setText("Opdrachtgever voor C");   	
			}
		if (Opdrachtgevers.opdrachtgeverID == 4)
			{
		 		detailinformatie.setText("Opdrachtgever voor D");
			}
		if (Opdrachtgevers.opdrachtgeverID == 5)
			{
	     		detailinformatie.setText("Opdrachtgever voor E");
			}
		if (Opdrachtgevers.opdrachtgeverID == 6)
			{
	     		detailinformatie.setText("Opdrachtgever voor F");   	
			}
		if (Opdrachtgevers.opdrachtgeverID == 7)
			{
		 		detailinformatie.setText("Opdrachtgever voor G");
			}
		if (Opdrachtgevers.opdrachtgeverID == 8)
			{
	     		detailinformatie.setText("Opdrachtgever voor H");
			}
		if (Opdrachtgevers.opdrachtgeverID == 9)
			{
	     		detailinformatie.setText("Opdrachtgever voor I");   	
			}
		if (Opdrachtgevers.opdrachtgeverID == 10)
			{
		 		detailinformatie.setText("Opdrachtgever voor J");
			}
		if (Opdrachtgevers.opdrachtgeverID == 11)
			{
	     		detailinformatie.setText("Opdrachtgever voor K");
			}
		if (Opdrachtgevers.opdrachtgeverID == 12)
			{
	     		detailinformatie.setText("Opdrachtgever voor L");   	
			}
		if (Opdrachtgevers.opdrachtgeverID == 13)
			{
		 		detailinformatie.setText("Opdrachtgever voor M");
			}
		if (Opdrachtgevers.opdrachtgeverID == 14)
			{
	     		detailinformatie.setText("Opdrachtgever voor N");
			}
		if (Opdrachtgevers.opdrachtgeverID == 15)
			{
	     		detailinformatie.setText("Opdrachtgever voor O");   	
			}
		if (Opdrachtgevers.opdrachtgeverID == 16)
			{
		 		detailinformatie.setText("Opdrachtgever voor P");
			}
	}
}