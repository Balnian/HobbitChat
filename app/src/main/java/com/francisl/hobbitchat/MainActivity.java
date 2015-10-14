package com.francisl.hobbitchat;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.Resources;
import android.opengl.EGLExt;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.AndroidCharacter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Spinner SP_Region;
    TextView TB_Port;
    TextView TB_Nom;
    Button BT_Demarrer;
    int RegionSelected=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SP_Region = (Spinner) findViewById(R.id.SP_Region);
        TB_Port = (TextView) findViewById(R.id.TB_Port);
        TB_Nom = (TextView) findViewById(R.id.TB_Nom);
        BT_Demarrer = (Button) findViewById(R.id.BT_Demarrer);

        /* Essaie back button*/
        Intent create = getIntent();
        SP_Region.setSelection(create.getIntExtra("Region",0));
        TB_Port.setText(create.getCharSequenceExtra("Port"));
        TB_Nom.setText(create.getCharSequenceExtra("Nom"));
        /*Fin Essaie*/




        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.regions, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        SP_Region.setAdapter(adapter);



        SP_Region.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                RegionSelected = position;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        BT_Demarrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LancerTchat();
            }
        });

    }

    private void LancerTchat()
    {
        /* Essaie back button*/
        Intent parent = new Intent(this,MainActivity.class);
        parent.putExtra("Region", RegionToIp(RegionSelected));
        parent.putExtra("Port", TB_Port.getText());
        parent.putExtra("Nom", TB_Nom.getText());


        PendingIntent pendingIntent =
                TaskStackBuilder.create(this)
                        // add all of DetailsActivity's parents to the stack,
                        // followed by DetailsActivity itself
                        .addNextIntentWithParentStack(parent)
                        .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentIntent(pendingIntent);
         /*Fin Essaie*/

        Intent intent = new Intent(this, Tchat.class);
        intent.putExtra("Region",RegionToIp(RegionSelected));
        intent.putExtra("Port",TB_Port.getText());
        intent.putExtra("Nom", TB_Nom.getText());
        startActivity(intent);
    }

    private String RegionToIp(int Region)
    {
        String Addr="";
        switch (Region)
        {
            case 0:
                Addr = "230.0.0.1";
                break;
            case 1:
                Addr = "230.0.0.2";
                break;
            case 2:
                Addr = "230.0.0.3";
                break;
        }
        return Addr;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("Region", RegionSelected);
        outState.putCharSequence("Port", TB_Port.getText());
        outState.putCharSequence("Nom", TB_Nom.getText());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        RegionSelected = savedInstanceState.getInt("Region",0);
        TB_Port.setText(savedInstanceState.getCharSequence("Port",""));
        TB_Nom.setText(savedInstanceState.getCharSequence("Nom",""));
    }

}
