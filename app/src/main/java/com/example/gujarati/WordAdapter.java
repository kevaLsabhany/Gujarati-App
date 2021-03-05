package com.example.gujarati;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word> {

    private int colorResourceId;

    public WordAdapter(Activity context, ArrayList<Word> wordList, int colorResourceId) {
        super(context, 0, wordList);
        this.colorResourceId = colorResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Word word = getItem(position);
        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.single_number, parent, false);

        TextView gujaratiTranslation = (TextView) convertView.findViewById(R.id.gujarati_translation);
        gujaratiTranslation.setText(word.getGujaratiTranslation());

        TextView defaultTranslation = (TextView) convertView.findViewById(R.id.default_translation);
        defaultTranslation.setText(word.getDefaultTranslation());

        ImageView imageView = (ImageView) convertView.findViewById(R.id.image);
        if(word.hasImage()) {
            imageView.setImageResource(word.getImageResourceId());
            imageView.setVisibility(View.VISIBLE);
        } else {
            imageView.setVisibility(View.GONE);
        }
        View textContainer = convertView.findViewById(R.id.text_container);
        int color = ContextCompat.getColor(getContext(), colorResourceId);
        textContainer.setBackgroundColor(color);
        return convertView;
    }
}
