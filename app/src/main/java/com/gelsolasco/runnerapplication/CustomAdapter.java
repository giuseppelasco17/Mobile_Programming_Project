package com.gelsolasco.runnerapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**Questa classe permette di riempire la lista attraverso list_item.xml che rappresenta il singolo
 *elemento della stessa con gli oggetti di tipo Athlete.
 */

public class CustomAdapter extends BaseAdapter {

    private final Context mContext;
    private LayoutInflater inflater;
    private List<Athlete> athleteList;
    private ArrayList<Athlete> arraylist;

    CustomAdapter(Context context, List<Athlete> athleteList) {
        mContext =  context;
        this.athleteList = athleteList;
        inflater = LayoutInflater.from(context);
        this.arraylist = new ArrayList<>();
        this.arraylist.addAll(athleteList);
    }

    public class ViewHolder {
        TextView name;
        TextView date;
        ImageView sexIv, thumb;
    }

    @Override
    public int getCount() {
        return athleteList.size();
    }

    @Override
    public Athlete getItem(int position) {
        return athleteList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int i, View view, ViewGroup parent) {
        ViewHolder holder;
        View row;
        view = null;
        row = view;
        if (view == null) {
            holder = new ViewHolder();
            //"Gonfio" l'elemento list_item nella lista
            row = inflater.inflate(R.layout.list_item, null);
            holder.name = row.findViewById(R.id.name_tv);
            holder.date = row.findViewById(R.id.date_tv);
            holder.sexIv = row.findViewById(R.id.sex_iv);
            holder.thumb = row.findViewById(R.id.profile_image);
        } else {
            holder = (ViewHolder) row.getTag();
        }
        /* Imposto i campi delle TextViews con i dati dell'oggetto Athlete nella lista corrispondente
         * all'indice di riga*/
        holder.name.setText(getItem(i).getName());
        holder.date.setText(getItem(i).getBirth());
        //Preparo l'URL per scaricare l'immagine da impostare nella ImageView
        try {
            URL url = new URL(getItem(i).getPicture());
            new PictureAsyncTask(holder.thumb).execute(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        //Imposto l'ImageView in base al sesso dell'atleta
        if(getItem(i).getSex().equals("M"))
            holder.sexIv.setImageResource(R.drawable.ic_immagine2);
        else
            holder.sexIv.setImageResource(R.drawable.ic_immagine3);
        /* Implemento il clickListner dell'elemento della lista e passo alla DescriptionActivity
         * inviandogli l'oggetto Athlete relativo alla riga*/
        row.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(mContext, DescriptionActivity.class);
                Bundle extras = new Bundle();
                //Invio l'oggetto Athlete all'activity DescriptionActivity
                extras.putParcelable("object_key", athleteList.get(i));
                intent.putExtras(extras);
                mContext.startActivity(intent);

            }
        });

        return row;
    }

    // Creo un filtro per la ricerca sulla lista
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        athleteList.clear();
        if (charText.length() == 0) {
            athleteList.addAll(arraylist);
        } else {
            for (Athlete athlete : arraylist) {
                if (athlete.getName().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    athleteList.add(athlete);
                }
            }
        }
        notifyDataSetChanged();
    }
}
