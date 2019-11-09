package com.example.lab6;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    String[] companyName, country, industry, CEO, description;
    int[] logo = {R.drawable.logo1, R.drawable.logo2, R.drawable.logo3, R.drawable.logo4, R.drawable.logo5, R.drawable.logo6
            , R.drawable.logo7, R.drawable.logo8, R.drawable.logo9, R.drawable.logo10, R.drawable.logo11
            , R.drawable.logo12, R.drawable.logo13, R.drawable.logo14};
    ListView listVersions;
    ArrayList<androidVersion> versions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        companyName = getResources().getStringArray(R.array.companyName);
        country = getResources().getStringArray(R.array.cCountry);
        industry = getResources().getStringArray(R.array.cIndustry);
        CEO = getResources().getStringArray(R.array.cCEO);
        description = getResources().getStringArray(R.array.cDescription);

        for (int i = 0; i < companyName.length;  i++){
            versions.add(new androidVersion(logo[i], companyName[i], country[i], industry[i], CEO[i], description[i]));
        }

        androidAdapter adapter = new androidAdapter(this,R.layout.newlayout, versions);
        listVersions =  findViewById(R.id.lvAndroid);
        listVersions.setAdapter(adapter);
        listVersions.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view,final int i, long id) {
        final File folder = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        final File file = new File(folder, "companies.txt");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            String choice_name = versions.get(i).getName();
            String choice_country = versions.get(i).getcCountry();
            String choice_industry = versions.get(i).getcIndustry();
            String choice_ceo = versions.get(i).getcCEO();
            fos.write(choice_name.getBytes());
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setIcon(logo[i]);
            dialog.setTitle(companyName[i]);
            dialog.setMessage(description[i]);
            dialog.setNeutralButton("Close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    Toast.makeText(MainActivity.this, companyName[i], Toast.LENGTH_LONG).show();
                }
            });
            dialog.create().show();

        } catch (FileNotFoundException e) {
            Toast.makeText(MainActivity.this, "File Not Found", Toast.LENGTH_LONG).show();
        } catch (IOException io) {
            Toast.makeText(MainActivity.this, "IO Exception", Toast.LENGTH_LONG).show();
        }
    }
}