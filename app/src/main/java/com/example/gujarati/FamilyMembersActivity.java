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

public class FamilyMembersActivity extends AppCompatActivity {

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

        ArrayList<Word> familyMembersList = new ArrayList<Word>();
        familyMembersList.add(new Word("father", "પિતા", R.drawable.family_father, R.raw.family_father));
        familyMembersList.add(new Word("mother", "માતા", R.drawable.family_mother, R.raw.family_mother));
        familyMembersList.add(new Word("son", "પુત્ર", R.drawable.family_son, R.raw.family_son));
        familyMembersList.add(new Word("daughter", "પુત્રી", R.drawable.family_daughter, R.raw.family_daughter));
        familyMembersList.add(new Word("older brother", "મોટો ભાઈ", R.drawable.family_older_brother, R.raw.family_older_brother));
        familyMembersList.add(new Word("younger brother", "નાનો ભાઈ", R.drawable.family_younger_brother, R.raw.family_younger_brother));
        familyMembersList.add(new Word("older sister", "મોટી બહેન", R.drawable.family_older_sister, R.raw.family_older_sister));
        familyMembersList.add(new Word("younger sister", "નાની બહેન", R.drawable.family_younger_sister, R.raw.family_younger_sister));
        familyMembersList.add(new Word("grandfather", "દાદા", R.drawable.family_grandfather, R.raw.family_grandfather));
        familyMembersList.add(new Word("grandmother", "દાદી", R.drawable.family_grandmother, R.raw.family_grandmother));

        ListView familyMembersListView = (ListView) findViewById(R.id.word_list_view);
        WordAdapter adapter = new WordAdapter(this, familyMembersList, R.color.blue_600);
        familyMembersListView.setAdapter(adapter);

        familyMembersListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Word word = familyMembersList.get(position);
                        releaseMediaPlayer();
                        int result = audioManager.requestAudioFocus(onAudioFocusChangeListener,
                                // Use the music stream.
                                AudioManager.STREAM_MUSIC,
                                // Request permanent focus.
                                AudioManager.AUDIOFOCUS_GAIN);

                        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                            // Start playback
                            mediaPlayer = MediaPlayer.create(FamilyMembersActivity.this, word.getAudioResourceId());
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