package com.example.gujarati;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {

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

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        ArrayList<Word> numberList = new ArrayList<Word>();
        numberList.add(new Word("one", "એક", R.drawable.number_one, R.raw.number_one));
        numberList.add(new Word("two", "બે", R.drawable.number_two, R.raw.number_two));
        numberList.add(new Word("three", "ત્રણ", R.drawable.number_three, R.raw.number_three));
        numberList.add(new Word("four", "ચાર", R.drawable.number_four, R.raw.number_four));
        numberList.add(new Word("five", "પાંચ", R.drawable.number_five, R.raw.number_five));
        numberList.add(new Word("six", "છ", R.drawable.number_six, R.raw.number_six));
        numberList.add(new Word("seven", "સાત", R.drawable.number_seven, R.raw.number_seven));
        numberList.add(new Word("eight", "આઠ", R.drawable.number_eight, R.raw.number_eight));
        numberList.add(new Word("nine", "નવ", R.drawable.number_nine, R.raw.number_nine));
        numberList.add(new Word("ten", "દસ", R.drawable.number_ten, R.raw.number_ten));

        ListView numbersListView = (ListView) findViewById(R.id.word_list_view);
        WordAdapter adapter = new WordAdapter(this, numberList, R.color.red_600);
        numbersListView.setAdapter(adapter);
        numbersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word = numberList.get(position);
                releaseMediaPlayer();

                int result = audioManager.requestAudioFocus(onAudioFocusChangeListener,
                        // Use the music stream.
                        AudioManager.STREAM_MUSIC,
                        // Request permanent focus.
                        AudioManager.AUDIOFOCUS_GAIN);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    // Start playback
                    mediaPlayer = MediaPlayer.create(NumbersActivity.this, word.getAudioResourceId());
                    mediaPlayer.start();

                    mediaPlayer.setOnCompletionListener(completionListener);
                }

            }
        });
    }

    private void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
            audioManager.abandonAudioFocus(onAudioFocusChangeListener);
        }
    }
}