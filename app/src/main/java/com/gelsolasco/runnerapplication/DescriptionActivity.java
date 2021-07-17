package com.gelsolasco.runnerapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.net.MalformedURLException;
import java.net.URL;

/**Quest'activity mostra all'utente i dati relativi all'atleta e vi accede tramite il click
 * sull'elemento della lista desiderato
 */

public class DescriptionActivity extends AppCompatActivity {

    private Athlete athlete;
    private TextView tvName, tvSex, tvBirth, tvCartNo, tvExpCard, tvExpCert, tvEmail, tvNat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        Intent intent = getIntent();
        //Recupero l'oggetto Athlete passato a tale activity
        Bundle extras = intent.getExtras();
        if (extras != null) {
            athlete = extras.getParcelable("object_key");
        }
        tvName = findViewById(R.id.tv_name);
        tvSex = findViewById(R.id.tv_sex);
        tvBirth = findViewById(R.id.tv_birth);
        tvCartNo = findViewById(R.id.tv_cartno);
        tvExpCard = findViewById(R.id.tv_expcard);
        tvExpCert = findViewById(R.id.tv_expcert);
        tvEmail = findViewById(R.id.tv_email);
        tvNat = findViewById(R.id.tv_nat);
        ImageView profile = findViewById(R.id.profile);
        setTextView();
        try {
            URL url = new URL(athlete.getPicture());
            new PictureAsyncTask(profile).execute(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
    public void setTextView(){
        tvName.setText(athlete.getName());
        tvSex.setText(athlete.getSex());
        tvBirth.setText(athlete.getBirth());
        tvCartNo.setText(String.valueOf(athlete.getCardNo()));
        tvExpCard.setText(athlete.getExpcard());
        tvExpCert.setText(athlete.getExpcert());
        tvEmail.setText(athlete.getEmail());
        tvNat.setText(athlete.getNationality());
    }

}
