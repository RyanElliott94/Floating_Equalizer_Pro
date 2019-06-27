package com.simplistic.floatingequalizerpro;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.audiofx.BassBoost;
import android.media.audiofx.Equalizer;
import android.media.audiofx.PresetReverb;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.simplistic.floatingequalizerpro.service.Floating;

import java.util.List;


public class EqualizerActivity extends Activity implements OnSeekBarChangeListener,
OnCheckedChangeListener,
OnClickListener, OnItemClickListener
{

static final int MAX_SLIDERS = 5; // Must match the XML layout
public final static int REQUEST_CODE = -1010101;

SeekBar bass_boost = null;

TextView bass_boost_label = null;
BassBoost bb = null;

CheckBox enabled = null;
Equalizer eq;
    short[] r;

int max_level = getMaxBandLevelRange();
int min_level = getMinBandLevelRange();
int num_sliders = 0;
    PresetReverb rv;
    TextView[] slider_labels = new TextView[MAX_SLIDERS];
    SeekBar[] sliders = new SeekBar[MAX_SLIDERS];
private SharedPreferences sp;

private String[] music_styles;

private ListView list;
private TextView preset;


    /*=============================================================================
    formatBandLabel
    =============================================================================*/
public String formatBandLabel (int[] band)
{
return milliHzToString(band[0]) + "-" + milliHzToString(band[1]);
}



/*=============================================================================
milliHzToString
=============================================================================*/


public String milliHzToString(int milliHz) {
String string = "Hz";
if ((milliHz = (milliHz / 1000)) < 1000) return (milliHz + string);
milliHz/=1000;
string = "Khz";
return (milliHz + string);
}


/*=============================================================================
onCheckedChange
=============================================================================*/
@Override
public void onCheckedChanged (CompoundButton view, boolean isChecked)
{
if (view == enabled) {
    if (isChecked) {
        enabled.setText("Enabled");
        eq.setEnabled(true);
    } else {
        enabled.setText("Disabled");
    }
}
}

/*=============================================================================
onClick
=============================================================================*/
@Override
public void onClick (View view)
{
switch(view.getId()){
case R.id.flat:
	setFlat();
	break;
case R.id.currentPreset:
	preset.setVisibility(View.GONE);
	for (int i = 0; i < num_sliders && i < MAX_SLIDERS; i++)
    {
    sliders[i].setVisibility(View.VISIBLE);
    slider_labels[i].setVisibility(View.VISIBLE);
    }
	break;
}
}

/*=============================================================================
onCreate
=============================================================================*/
@SuppressLint("WrongViewCast")
@Override
public void onCreate(Bundle savedInstanceState)
  {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.eq);

      Toolbar toolbar = findViewById(R.id.toolBar);
      setActionBar(toolbar);
      getActionBar().setDisplayShowTitleEnabled(false);
      getActionBar().setDisplayHomeAsUpEnabled(true);

      
  enabled = findViewById(R.id.enabled);
  enabled.setOnCheckedChangeListener(this);

  preset = findViewById(R.id.currentPreset);
  preset.setOnClickListener(this);

  Typeface typeface = Typeface.createFromAsset(getAssets(), "RobotoThin.ttf");
  preset.setTypeface(typeface);

  bass_boost = findViewById(R.id.bass_boost);
  bass_boost.setOnSeekBarChangeListener(this);
  bass_boost_label = findViewById (R.id.bass_boost_label);


  sliders[0] = findViewById(R.id.slider_6);
  slider_labels[0] = findViewById(R.id.slider_label_6);
  sliders[1] = findViewById(R.id.slider_7);
  slider_labels[1] = findViewById(R.id.slider_label_7);
  sliders[2] = findViewById(R.id.slider_8);
  slider_labels[2] = findViewById(R.id.slider_label_8);
  sliders[3] = findViewById(R.id.slider_9);
  slider_labels[3] = findViewById(R.id.slider_label_9);
  sliders[4] = findViewById(R.id.slider_10);
  slider_labels[4] = findViewById(R.id.slider_label_10);

  list = findViewById(R.id.spinner);

  list.setOnItemClickListener(this);

  eq = new Equalizer (100, 0);
  setUpEQ();

  bb = new BassBoost (100, 0);

  if (bb != null)
    {
        getBass();
    }
  else
    {
    bass_boost.setVisibility(View.GONE);
    bass_boost_label.setVisibility(View.GONE);
    }
getEqualizer();
updateUI();

short m = eq.getNumberOfPresets();
music_styles = new String[m];
for(int k=0; k <m ; k++)
music_styles[k] = eq.getPresetName((short) k);

ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.preset_item, R.id.preset, music_styles);
list.setAdapter(adapter);
checkDrawOverlayPermission();

closeFloatingIfRunning(Floating.class.getName());
}

    public void checkDrawOverlayPermission() {
        /** check if we already  have permission to draw over other apps */
        if (!Settings.canDrawOverlays(getApplicationContext())) {
            /** if not construct intent to request permission */
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            /** request permission via start activity for result */
            startActivityForResult(intent, REQUEST_CODE);
        }
    }

public void setUpEQ(){
	 if (eq != null)
	    {
	    eq.setEnabled(true);
	    int num_bands = eq.getNumberOfBands();
	    num_sliders = num_bands;
            short[] r = eq.getBandLevelRange();
	    min_level = r[0];
	    max_level = r[1];
	    for (int i = 0; i < num_sliders && i < MAX_SLIDERS; i++)
	      {
	      int[] freq_range = eq.getBandFreqRange((short)i);
	      sliders[i].setOnSeekBarChangeListener(this);
	      slider_labels[i].setText (formatBandLabel (freq_range));
	      }
	    }else{
	    	eq.getEnabled();
         enabled.setText("Disbaled");
	    }
	  for (int i = num_sliders ; i < MAX_SLIDERS; i++)
	    {
	    sliders[i].setVisibility(View.GONE);
	    slider_labels[i].setVisibility(View.GONE);
	    }
}

public int getMaxBandLevelRange() {
    if (eq != null) return eq.getBandLevelRange()[1];
    return 1500;
}

public int getMinBandLevelRange() {
    if (eq != null) return eq.getBandLevelRange()[0];
    return -1500;
}


/*=============================================================================
onCreate
=============================================================================*/
@Override
public boolean onCreateOptionsMenu(Menu menu)
{
MenuInflater inflater = getMenuInflater();
inflater.inflate(R.menu.static_menu, menu);
return true;
}



/*=============================================================================
onOptionsItemSelected
=============================================================================*/
@Override
public boolean onOptionsItemSelected(MenuItem item)
 {
 switch (item.getItemId())
   {
       case android.R.id.home:
           finish();
           break;
       case R.id.reset:
           setFlat();
           break;
 case R.id.rate:
		try {
		    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.simplistic.floatingequalizerpro")));
		} catch (android.content.ActivityNotFoundException anfe) {
		    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=com.simplistic.floatingequalizerpro")));
		}
		return true;
 case R.id.mini:
     saveEqualizer();
     saveBass();
     Intent intent = new Intent(getApplicationContext(), Floating.class);
     intent.putExtra("basic_eq", "isFromBasic");
     startService(intent);
     finish();
     break;
 	case R.id.stop:
     Sure();
     break;
   }
 return super.onOptionsItemSelected(item);
 }


/*=============================================================================
onProgressChanged
=============================================================================*/
@Override
public void onProgressChanged (SeekBar seekBar, int level,
boolean fromTouch)
{
  if (seekBar == bass_boost)
    {
    bb.setEnabled (true);
    bb.setStrength ((short)level); // Already in the right range 0-1000
    }
  else if (eq != null)
    {
    int new_level = min_level + (max_level - min_level) * level / 100;
    for (int i = 0; i < num_sliders; i++)
      {
      if (sliders[i] == seekBar)
        {
    	sliders[i].setEnabled(true);
        eq.setBandLevel ((short)i, (short)new_level);
        break;
        }
      }
    }
}

/*=============================================================================
onStartTrackingTouch
=============================================================================*/
@Override
public void onStartTrackingTouch(SeekBar seekBar)
{
}

/*=============================================================================
onStopTrackingTouch
=============================================================================*/
@Override
public void onStopTrackingTouch(SeekBar seekBar)
{
}

public void releaseEqualizer() {
    if (eq != null) {
       eq.release();
  }
    if (bb != null) {
        bb.release();
    }
}

public int saveBass(){
	sp = getSharedPreferences("Bass", MODE_PRIVATE);
	SharedPreferences.Editor editor = sp.edit();
	editor.putInt("bass_boost", bb.getRoundedStrength());
	editor.apply();
	return 0;

}

public void getBass(){
	sp = getSharedPreferences("Bass", MODE_PRIVATE);
    bb.setStrength((short) sp.getInt("bass_boost", 0));
    bass_boost.setProgress(sp.getInt("bass_boost", 0));

}

public void saveEqualizer(){
    SharedPreferences.Editor editor = getSharedPreferences("Equalizer", MODE_PRIVATE).edit();
    for (int i = 0; i < MAX_SLIDERS; i++)
    {
        System.out.print(sliders[i]);
        editor.putInt("Band_" + sliders[i].getId(), sliders[i].getProgress());
        editor.commit();
    }
}

    public int saveProgress() {
        SharedPreferences.Editor editor = getSharedPreferences("Equalizer", MODE_PRIVATE).edit();
        editor.putInt("1", sliders[0].getProgress());
        editor.putInt("2", sliders[1].getProgress());
        editor.putInt("3", sliders[2].getProgress());
        editor.putInt("4", sliders[3].getProgress());
        editor.putInt("5", sliders[4].getProgress());
        editor.commit();
        return (min_level - max_level);
    }

    public int getProgress() {
        SharedPreferences sharedPreferences = getSharedPreferences("Equalizer", MODE_PRIVATE);
        sliders[0].setProgress(sharedPreferences.getInt("1", -1));
        sliders[1].setProgress(sharedPreferences.getInt("2", -1));
        sliders[2].setProgress(sharedPreferences.getInt("3", -1));
        sliders[3].setProgress(sharedPreferences.getInt("4", -1));
        sliders[4].setProgress(sharedPreferences.getInt("5", -1));
        return (min_level - max_level);
    }

public void getEqualizer(){
	SharedPreferences eqSp = getSharedPreferences("Equalizer", MODE_PRIVATE);
    for (int i = 0; i < MAX_SLIDERS; i++)
    {
        System.out.print(sliders[i].getProgress());
        sliders[i].setProgress(eqSp.getInt("Band_" + sliders[i].getId(), -1));
    }
}

/*
public int savePreset(){
	sp = getSharedPreferences("Preset", MODE_PRIVATE);
	SharedPreferences.Editor editor = sp.edit();
	editor.putInt("preset_name", eq.getCurrentPreset());
	editor.commit();
	return 0;
}

public int getPreset(){
	sp = getSharedPreferences("Preset", MODE_PRIVATE);
	eq.usePreset((short) savePreset());

	preset.setText("Current Preset: " + eq.getPresetName((short)savePreset()));
	preset.setVisibility(View.VISIBLE);
	for (int i = 0; i < num_sliders && i < MAX_SLIDERS; i++)
    {
	sliders[i].setVisibility(View.GONE);
	slider_labels[i].setVisibility(View.GONE);
    }
	return 0;

}
*/
/*=============================================================================
setFlat
=============================================================================*/
public void setFlat ()
{
if (eq != null)
  {
  for (int i = 0; i < num_sliders; i++)
    {
    eq.setBandLevel((short)i, (short)0);

    }
  }

if (bb != null)
  {
  bb.setEnabled(false);
  bb.setStrength((short)0);
  }

updateUI();
}

/*=============================================================================
showAbout
=============================================================================*/

public void Sure() {
    MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder((this));
    builder.setTitle(("Please Choose:"));
    builder.setMessage(("Float it!: This will keep both the EQ and the Floating Icon running\n\nClose EQ: This will close both the EQ and the floating button"));
    builder.setCancelable(true);
    builder.setIcon(R.mipmap.ic_launcher);
    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){

        @Override
		public void onClick(DialogInterface dialogInterface2, int dialogInterface) {
        }
    });
    builder.setNeutralButton("Float it!", new DialogInterface.OnClickListener(){

        @Override
		public void onClick(DialogInterface dialogInterface2, int dialogInterface) {
            saveEqualizer();
            saveBass();
            Intent intent = new Intent(getApplicationContext(), Floating.class);
            intent.putExtra("basic_eq", "isFromBasic");
            startService(intent);
            finish();
        }
    });
    builder.setPositiveButton("Close EQ", new DialogInterface.OnClickListener(){

        @Override
		public void onClick(DialogInterface dialogInterface2, int dialogInterface) {
            saveBass();
            saveEqualizer();
            Intent intent = new Intent(getApplicationContext(), Floating.class);
            stopService(intent);
            releaseEqualizer();
            finish();

        }
    });
    builder.create().show();
}

/*=============================================================================
updateBassBoost
=============================================================================*/
public void updateBassBoost ()
{
if (bb != null)
bass_boost.setProgress (bb.getRoundedStrength());
else
bass_boost.setProgress(0);
}

/*=============================================================================
updateSliders
=============================================================================*/
public void updateSliders ()
{
  for (int i = 0; i < num_sliders; i++)
    {
    int level;
    if (eq != null)
      level = eq.getBandLevel((short)i);
    else
      level = 0;
    int pos = 100 * level / (max_level - min_level) + 50;
    sliders[i].setProgress (pos);
    }
}
/*=============================================================================
updateUI
=============================================================================*/
public void updateUI ()
{
updateSliders();
updateBassBoost();
enabled.setChecked(eq.getEnabled());
}

    public boolean closeFloatingIfRunning(String serviceClassName){
        final ActivityManager activityManager = (ActivityManager)getSystemService(ACTIVITY_SERVICE);
        final List<ActivityManager.RunningServiceInfo> services = activityManager.getRunningServices(Integer.MAX_VALUE);

        for (ActivityManager.RunningServiceInfo runningServiceInfo : services) {
            if (runningServiceInfo.service.getClassName().equals(serviceClassName) && runningServiceInfo.service != null){
                Intent intent = new Intent(getApplicationContext(), Floating.class);
                stopService(intent);
                return true;
            }
        }
        return false;
    }

@Override
public void onActivityResult(int requestCode, int resultCode, Intent i){
    super.onActivityResult(requestCode, resultCode, i);

    String quote = getIntent().getStringExtra("hello");

    if(quote == "hello"){
        Toast.makeText(this, "Hello result " + requestCode, Toast.LENGTH_LONG).show();
    }
}
@Override
public void onBackPressed(){
	Sure();
}

@Override
public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	eq.usePreset((short)position);
	preset.setText("Current Preset: " + eq.getPresetName((short)position));
	preset.setVisibility(View.VISIBLE);
	for (int i = 0; i < num_sliders && i < MAX_SLIDERS; i++)
    {
     updateSliders();
	sliders[i].setVisibility(View.GONE);
	slider_labels[i].setVisibility(View.GONE);
    }

}

@Override
public void onResume(){
    super.onResume();
    updateSliders();
}
@Override
    public void onDestroy(){
    super.onDestroy();
    releaseEqualizer();
}
}



