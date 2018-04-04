package com.example.modernartui;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SeekBar seekBar = (SeekBar)findViewById(R.id.seekBar);

        /**
         *  6 Different View one for each color
         */
        final View vGreen = (View)findViewById(R.id.colored_bar_green);
        final View vBlue = (View)findViewById(R.id.colored_bar_blue);
        final View vViolet = (View)findViewById(R.id.colored_bar_violet);
        final View vRed = (View)findViewById(R.id.colored_bar_red);
        final View vOrange = (View)findViewById(R.id.colored_bar_orange);
        final View vYellow = (View)findViewById(R.id.colored_bar_yellow);

        seekBar.setMax(100);

        /**
         * seekbar onSeekBarChangeLister:
         *      detects the change of the seekbar when the progress bar is being moved.
         *      5 diferent thresholds:
         *      0 -20;
         *      20 -40;
         *      40-60;
         *      60-80;
         *      80-100;
         *      At each threshold each color's shade is changed slightly
         */
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                progressChangedValue=i;
                seekBar.setProgress(i);
                if(i>0 && i<20) {
                    vRed.setBackgroundColor(Color.parseColor("#ff1000"));
                    vBlue.setBackgroundColor(Color.parseColor("#D3D3D3"));
                    vViolet.setBackgroundColor(Color.parseColor("#D50BF6"));
                    vOrange.setBackgroundColor(Color.parseColor("#ff8c00"));
                    vGreen.setBackgroundColor(Color.parseColor("#0BF628"));
                    vYellow.setBackgroundColor(Color.parseColor("#ffff19"));

                }
                if(i>20 && i<40) {
                    vRed.setBackgroundColor(Color.parseColor("#ff5000"));
                    vBlue.setBackgroundColor(Color.parseColor("#D3D3D3"));
                    vViolet.setBackgroundColor(Color.parseColor("#C00BF6"));
                    vOrange.setBackgroundColor(Color.parseColor("#ff9719"));
                    vGreen.setBackgroundColor(Color.parseColor("#0BF65B"));
                    vYellow.setBackgroundColor(Color.parseColor("#ffff7f"));
                }
                if(i>40 && i<60) {
                    vRed.setBackgroundColor(Color.parseColor("#F60F0B"));
                    vBlue.setBackgroundColor(Color.parseColor("#D3D3D3"));
                    vViolet.setBackgroundColor(Color.parseColor("#9F0BF6"));
                    vOrange.setBackgroundColor(Color.parseColor("#ffa332"));
                    vGreen.setBackgroundColor(Color.parseColor("#0BF68D"));
                    vYellow.setBackgroundColor(Color.parseColor("#ffff99"));
                }
                if(i>60 && i<80) {
                    vRed.setBackgroundColor(Color.parseColor("#F6270B"));
                    vBlue.setBackgroundColor(Color.parseColor("#D3D3D3"));
                    vViolet.setBackgroundColor(Color.parseColor("#7F0BF6"));
                    vOrange.setBackgroundColor(Color.parseColor("#ffae4c"));
                    vGreen.setBackgroundColor(Color.parseColor("#0BF6A6"));
                    vYellow.setBackgroundColor(Color.parseColor("#ffffb2"));
                }
                if(i>80 && i<90) {
                    vRed.setBackgroundColor(Color.parseColor("#F6390B"));
                    vBlue.setBackgroundColor(Color.parseColor("#D3D3D3"));
                    vViolet.setBackgroundColor(Color.parseColor("#F6390B"));
                    vOrange.setBackgroundColor(Color.parseColor("#ffba66"));
                    vGreen.setBackgroundColor(Color.parseColor("#0BF6D2"));
                    vYellow.setBackgroundColor(Color.parseColor("#ffffcc"));
                }if(i>90 && i<100) {
                    vRed.setBackgroundColor(Color.parseColor("#F64B0B"));
                    vBlue.setBackgroundColor(Color.parseColor("#D3D3D3"));
                    vViolet.setBackgroundColor(Color.parseColor("#620BF6"));
                    vOrange.setBackgroundColor(Color.parseColor("#ffc57f"));
                    vGreen.setBackgroundColor(Color.parseColor("#0BF6DD"));
                    vYellow.setBackgroundColor(Color.parseColor("#ffffe5"));
                }
                Log.d("Current Progress: ",Integer.toString(seekBar.getProgress()));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                /*int progrees =seekBar.getProgress();
                Log.d("Current Progress: ",Integer.toString(progrees));*/
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getApplicationContext(),"Seek bar progess is:" + progressChangedValue,Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override
    /**
     * Options menu, at the top right corner of the screen:
     * verticlr 'elipse'
     * on click inflates a layout: top_menu.xml which then displays an option "info"
     */
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_menu,menu);
        return true;
    }
    @Override
    /**
     * When "info" is selected from the the options menu:
     * a DiologBox is displayed that gives some information and provides two options:
     * a button for "Not Now" and another for "Visit MoMa"
     *
     *  Option 1) Not Now:
     *          If this option is selected, the diolag box is dismissed and you can continue to look at the UI and play with the seekbar.
     *  Option 2) Visit MoMa:
     *          If this options is selected, an intent is created that then starts a new activity "WebActivity".
     *          This activity contains a webview and fetches the contents of moma.org and puts in the the webview for browsing.
     */
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.info:
                //Toast.makeText(getApplicationContext(),"Made it to info option",Toast.LENGTH_LONG).show();
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setTitle("Inspired by the works of artist such as Peit Mondrian and Ben Nicholson.");
                alertDialog.setMessage("Click Here to learn more!");
                /**
                 * Option 2
                 */
                alertDialog.setPositiveButton("Visit MOMA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Toast.makeText(getApplicationContext(),"Made it to info option",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), WebActivity.class);
                        startActivity(intent);
                    }
                });
                /**
                 * Option 1
                 */
                alertDialog.setNegativeButton("Not Now", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                /*alertDialog.setP(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });*/
                alertDialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
