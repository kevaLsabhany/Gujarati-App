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

public class PhrasesActivity extends AppCompatActivity {

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

        ArrayList<Word> phrasesList = new ArrayList<Word>();
        phrasesList.add(new Word("Where are you going?", "તમે ક્યાં જાવ છો?", R.raw.phrase_where_are_you_going));
        phrasesList.add(new Word("What is your name?", "તમારું નામ શું છે?", R.raw.phrase_what_is_your_name));
        phrasesList.add(new Word("My name is...", "મારું નામ...", R.raw.phrase_my_name_is));
        phrasesList.add(new Word("How are you feeling?", "તમને કેવું લાગે છે?", R.raw.phrase_how_are_you_feeling));
        phrasesList.add(new Word("I'm feeling good.", "મને સારુ લાગી રહ્યુ છે", R.raw.phrase_im_feeling_good));
        phrasesList.add(new Word("Are you coming?", "તમે આવો છો?", R.raw.phrase_are_you_coming));
        phrasesList.add(new Word("Yes, I'm coming.", "હા, હું આવું છું", R.raw.phrase_yes_im_coming));
        phrasesList.add(new Word("I'm coming.", "હું આવું છું", R.raw.phrase_im_coming));
        phrasesList.add(new Word("Let's go.", "ચાલો જઇએ", R.raw.phrase_lets_go));
        phrasesList.add(new Word("Come here.", "અહી આવો", R.raw.phrase_come_here));

        ListView phrasesListView = (ListView) findViewById(R.id.word_list_view);
        WordAdapter adapter = new WordAdapter(this, phrasesList, R.color.yellow_600);
        phrasesListView.setAdapter(adapter);

        phrasesListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Word word = phrasesList.get(position);
                        releaseMediaPlayer();
                        int result = audioManager.requestAudioFocus(onAudioFocusChangeListener,
                                // Use the music stream.
                                AudioManager.STREAM_MUSIC,
                                // Request permanent focus.
                                AudioManager.AUDIOFOCUS_GAIN);

                        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                            // Start playback
                            mediaPlayer = MediaPlayer.create(PhrasesActivity.this, word.getAudioResourceId());
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