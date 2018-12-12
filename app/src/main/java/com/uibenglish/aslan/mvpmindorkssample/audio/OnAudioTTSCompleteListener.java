package com.uibenglish.aslan.mvpmindorkssample.audio;

import java.util.concurrent.Callable;

public interface OnAudioTTSCompleteListener {

    void onAudioStart();
    void onAudioDone();
    void onAudioError();
}

/*    private Callable onAudioStartCallback;
    private Callable onAudioDoneCallback;
    private Callable onAudioErrorCallback;

    public OnAudioTTSCompleteListener(Callable onAudioStartCallback, Callable onAudioDoneCallback, Callable onAudioErrorCallback) {
        this.onAudioStartCallback = onAudioStartCallback;
        this.onAudioDoneCallback = onAudioDoneCallback;
        this.onAudioErrorCallback = onAudioErrorCallback;
    }

    public void onAudioStart(){
        try{
            this.onAudioStartCallback.call();
        } catch (Exception e){

        }
    }

    public void onAudioDone(){
        try{
            this.onAudioDoneCallback.call();
        } catch (Exception e){

        }
    }
    public void onAudioError(){
        try{
            this.onAudioErrorCallback.call();
        } catch (Exception e){

        }
    }*/
