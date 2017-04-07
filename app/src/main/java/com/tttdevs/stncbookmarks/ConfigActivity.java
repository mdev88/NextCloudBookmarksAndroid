package com.tttdevs.stncbookmarks;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ConfigActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        final EditText edtUrl = (EditText) findViewById(R.id.edtUrl);

        SharedPreferences sharedPref = getSharedPreferences("com.tttdevs.ncbookmarks.config", Context.MODE_PRIVATE);
        String serverURL = sharedPref.getString("server_url", "");
        edtUrl.setText(serverURL);

        Button btnSaveUrl = (Button) findViewById(R.id.btnSaveUrl);
        btnSaveUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPref = getSharedPreferences("com.tttdevs.ncbookmarks.config", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                // TODO: Input validation
                Log.d("TEST", "Server url: "+edtUrl.getText().toString());
                editor.putString("server_url", edtUrl.getText().toString());
                editor.apply();

                Toast.makeText(ConfigActivity.this, "Configuration Saved. You can close this window.", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void aboutTTT(View view) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse("https://tttdevs.com"));
        startActivity(i);
    }
}
