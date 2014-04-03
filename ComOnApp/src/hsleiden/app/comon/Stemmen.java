package hsleiden.app.comon;

import com.example.comonnavigation.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RadioButton;

public class Stemmen extends Activity
{
	ImageButton stembutton;
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stemmen_layout);
		getSelected();
		buttonListener();
	}
	
	public void getSelected()
	{
		RadioButton button1 = (RadioButton) this.findViewById(R.id.radioButton1);
		if(button1.isChecked()) {}
		
		RadioButton button2 = (RadioButton) this.findViewById(R.id.RadioButton01);
		if(button1.isChecked()) {}

		RadioButton button3 = (RadioButton) this.findViewById(R.id.RadioButton02);
		if(button1.isChecked()) {}

		RadioButton button4 = (RadioButton) this.findViewById(R.id.RadioButton03);
		if(button1.isChecked()) {}

		RadioButton button5 = (RadioButton) this.findViewById(R.id.RadioButton04);
		if(button1.isChecked()) {}
		
		RadioButton button6 = (RadioButton) this.findViewById(R.id.RadioButton05);
		if(button1.isChecked()) {}
		
		RadioButton button7 = (RadioButton) this.findViewById(R.id.RadioButton06);
		if(button1.isChecked()) {}

		RadioButton button8 = (RadioButton) this.findViewById(R.id.RadioButton07);
		if(button1.isChecked()) {}

		RadioButton button9 = (RadioButton) this.findViewById(R.id.RadioButton08);
		if(button1.isChecked()) {}

		RadioButton button10 = (RadioButton) this.findViewById(R.id.RadioButton09);
		if(button1.isChecked()) {}
		
		RadioButton button11 = (RadioButton) this.findViewById(R.id.RadioButton10);
		if(button1.isChecked()) {}
		
		RadioButton button12 = (RadioButton) this.findViewById(R.id.RadioButton11);
		if(button1.isChecked()) {}

		RadioButton button13 = (RadioButton) this.findViewById(R.id.RadioButton12);
		if(button1.isChecked()) {}

		RadioButton button14 = (RadioButton) this.findViewById(R.id.RadioButton13);
		if(button1.isChecked()) {}

		RadioButton button15 = (RadioButton) this.findViewById(R.id.RadioButton14);
		if(button1.isChecked()) {}
		
		RadioButton button16 = (RadioButton) this.findViewById(R.id.RadioButton15);
		if(button1.isChecked()) {}

		RadioButton button17 = (RadioButton) this.findViewById(R.id.RadioButton01);
		if(button1.isChecked()) {}
	}
	
	public void buttonListener()
	{
		final Context context = this;
		stembutton = (ImageButton) findViewById(R.id.stembutton);
		stembutton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0) 
			{
				
			}
		});
	}
	
}