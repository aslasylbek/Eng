package com.uibenglish.aslan.mvpmindorkssample.audio;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.uibenglish.aslan.mvpmindorkssample.ui.vocabulary.remember.RememberFragment;

import java.util.Locale;

import static android.support.constraint.Constraints.TAG;


public class AudioSyntethis extends UtteranceProgressListener implements TextToSpeech.OnInitListener {


    private String text;
    private OnAudioTTSCompleteListener callback;
    private TextToSpeech tts;


    public AudioSyntethis(Context context, OnAudioTTSCompleteListener callback) {
        tts = new TextToSpeech(context, this);
        this.callback = callback;
    }

    public void setText(String text) {
        this.text = text;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onInit(int status) {
        if(status == TextToSpeech.SUCCESS){
            int result = tts.setLanguage(Locale.UK);
            tts.setOnUtteranceProgressListener(this);
            if(result==TextToSpeech.LANG_MISSING_DATA ||
                    result==TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("error", "This Language is not supported");
            }
            else{
                //ConvertTextToSpeech();
            }
        }
        else
            Log.e("error", "Initilization Failed!");
    }

    @Override
    public void onStart(String utteranceId) {
        callback.onAudioStart();
    }

    @Override
    public void onDone(String utteranceId) {
        Log.e(TAG, "onDone: "+utteranceId );
        callback.onAudioDone();

    }

    @Override
    public void onError(String utteranceId) {
        callback.onAudioError();
    }

    public void stopAudioPlayer(){
        if(tts != null){
            tts.stop();
            tts.shutdown();
        }
    }


    public void playSyntethMedia(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ConvertTextToSpeech();
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void ConvertTextToSpeech() {

        Log.e(TAG, "ConvertTextToSpeech: "+text );

        if(text==null||"".equals(text))
        {
            text = "Content not available";

            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "1");
        }else
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "2");
    }

}
