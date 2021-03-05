package com.example.gujarati;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ColorsActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    private AudioManager audioManager;

    private MediaPlayer.OnCompletionListener completionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };

    private AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                mediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                releaseMediaPlayer();
            }
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        ArrayList<Word> colorsList = new ArrayList<Word>();
        colorsList.add(new Word("red", "લાલ", R.drawable.color_red, R.raw.color_red));
        colorsList.add(new Word("dusty yellow", "ડસ્ટી પીળો", R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow));
        colorsList.add(new Word("mustard yellow", "સરસવ પીળો", R.drawable.color_mustard_yellow, R.raw.color_mustard_yellow));
        colorsList.add(new Word("brown", "ભુરો", R.drawable.color_brown, R.raw.color_brown));
        colorsList.add(new Word("green", "લીલો", R.drawable.color_green, R.raw.color_green));
        colorsList.add(new Word("gray", "ભુખરો", R.drawable.color_gray, R.raw.color_gray));
        colorsList.add(new Word("black", "કાળો", R.drawable.color_black, R.raw.color_black));
        colorsList.add(new Word("White", "સફેદ", R.drawable.color_white, R.raw.color_white));

        ListView colorsListView = (ListView) findViewById(R.id.word_list_view);
        WordAdapter adapter = new WordAdapter(this, colorsList, R.color.green_600);
        colorsListView.setAdapter(adapter);
        colorsListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Word word = colorsList.get(position);
                        releaseMediaPlayer();
                        int result = audioManager.requestAudioFocus(onAudioFocusChangeListener,
                                // Use the music stream.
                                AudioManager.STREAM_MUSIC,
                                // Request permanent focus.
                                AudioManager.AUDIOFOCUS_GAIN);

                        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                            // Start playback
                            mediaPlayer = MediaPlayer.create(ColorsActivity.this, word.getAudioResourceId());
                            mediaPlayer.start();

                            mediaPlayer.setOnCompletionListener(completionListener);
                        }
                    }
                }
        );
    }

    private void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
            audioManager.abandonAudioFocus(onAudioFocusChangeListener);
        }
    }
}