package com.example.aslan.mvpmindorkssample.data;

import android.util.Log;

import com.example.aslan.mvpmindorkssample.BuildConfig;
import com.example.aslan.mvpmindorkssample.data.content.EngInformationResponse;
import com.example.aslan.mvpmindorkssample.data.content.LoginResponse;
import com.example.aslan.mvpmindorkssample.data.content.Translate;
import com.example.aslan.mvpmindorkssample.data.content.TranslationResponse;
import com.example.aslan.mvpmindorkssample.data.local.AppDatabase;
import com.example.aslan.mvpmindorkssample.data.local.PreferenceHelper;
import com.example.aslan.mvpmindorkssample.data.local.SharedPrefsHelper;
import com.example.aslan.mvpmindorkssample.data.remote.ApiFactory;
import com.example.aslan.mvpmindorkssample.ui.main.content.Grammar;
import com.example.aslan.mvpmindorkssample.ui.main.content.Reading;
import com.example.aslan.mvpmindorkssample.ui.main.content.Topic;
import com.example.aslan.mvpmindorkssample.ui.main.content.Word;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by aslan on 17.05.2018.
 */

public class DataManager implements DataManagerContract {

    private PreferenceHelper prefsHelper;
    private AppDatabase appDatabase;

    public DataManager(SharedPrefsHelper prefsHelper, AppDatabase appDatabase) {
        this.prefsHelper = prefsHelper;
        this.appDatabase = appDatabase;
    }

    public void sendForToken(String username, String password, final GetTokenCallbacks callback) {
        ApiFactory
                .getApiService()
                .getValidationInfo(username, password)
                .enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        callback.onSuccess(response.body());
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        callback.onError();
                    }
                });

    }

    public void requestForEnglishInformation(String userId, final GetEnglishInformation callback) {
        ApiFactory
                .getApiService()
                .getStudentSubjectInformation("25147")
                .enqueue(new Callback<EngInformationResponse>() {
                    @Override
                    public void onResponse(Call<EngInformationResponse> call, Response<EngInformationResponse> response) {
                        callback.onSuccess(response.body());
                    }

                    @Override
                    public void onFailure(Call<EngInformationResponse> call, Throwable t) {
                        callback.onError();
                    }
                });
    }

    public void requestForWordTranslation(String word, final GetWordTranslation callback) {
        ApiFactory
                .getApiService()
                .getWordTranslate("http://api.lingualeo.com/gettranslates?word=" + word)
                .enqueue(new Callback<TranslationResponse>() {
                    @Override
                    public void onResponse(Call<TranslationResponse> call, Response<TranslationResponse> response) {
                        Log.d("", "onResponse: " + response.errorBody());
                        callback.onSuccess(response.body().getTranslate());
                    }

                    @Override
                    public void onFailure(Call<TranslationResponse> call, Throwable t) {
                        callback.onError();
                    }
                });
    }

    public void requestPostTaskResult(String ex_id, int topicId, int result, final GetVoidPostCallback callback) {
        ApiFactory.changeApiBaseUrl(BuildConfig.API_ENDPOINT_ENG);
        ApiFactory.recreate();
        ApiFactory.getApiService()
                .postGrammarResult(ex_id, getPrefUserid(), topicId, result)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        callback.onSuccess(response);
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        callback.onError(t);
                    }
                });
    }


    public void postChapterResult(JsonObject jsonObject, final GetVoidPostCallback callback) {
        ApiFactory.changeApiBaseUrl(BuildConfig.API_ENDPOINT_ENG);
        ApiFactory.recreate();
        ApiFactory.getApiService()
                .postTaskResult(String.valueOf(jsonObject))
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        callback.onSuccess(response);
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        callback.onError(t);
                    }
            });
    }

    public void saveTopics(Topic... topic) {
        appDatabase.topicDao().insertAll(topic);
    }

    public List<Topic> getAllTopics() {
        return appDatabase.topicDao().getAll();
    }

    public void deleteTopic(Topic topic) {
        appDatabase.topicDao().delete(topic);
    }

    public void clearAllDatabase() {
        appDatabase.clearAllTables();
    }

    public void saveWords(Word word, String topicId) {
        word.setTopic_id(topicId);
        appDatabase.topicDao().insertWord(word);
    }

    public void saveReading(Reading reading, String topicId) {
        reading.setTopic_id(topicId);
        appDatabase.topicDao().insertReading(reading);
    }

    public void saveGrammar(Grammar grammar, String topic_id) {
        grammar.setTopic_id(topic_id);
        appDatabase.topicDao().insertGrammar(grammar);
    }

    public List<Grammar> getGrammarByTopicId(String topicId) {
        return appDatabase.topicDao().findGrammarById(topicId);
    }

    public List<Word> getAllWords() {
        return appDatabase.topicDao().getAllWords();
    }

    public List<Word> getWordsByTopicId(String topicId) {
        return appDatabase.topicDao().findByTopicId(topicId);
    }

    public List<Reading> getTextByTopicId(String topicId) {
        return appDatabase.topicDao().findTextById(topicId);
    }

    @Override
    public void clear() {
        prefsHelper.clear();
    }

    @Override
    public void putToken(String token) {
        prefsHelper.putToken(token);
    }

    @Override
    public boolean getLoggedMode() {
        return prefsHelper.getLoggedMode();
    }

    @Override
    public void putPassword(String password) {
        prefsHelper.putPassword(password);
    }

    @Override
    public String getPrefPassword() {
        return prefsHelper.getPrefPassword();
    }

    @Override
    public String getPrefToken() {
        return prefsHelper.getPrefToken();
    }

    @Override
    public void putUserId(String password) {
        prefsHelper.putUserId(password);
    }

    @Override
    public String getPrefUserid() {
        return prefsHelper.getPrefUserid();
    }

    @Override
    public void putBarcode(String barcode) {
        prefsHelper.putBarcode(barcode);
    }

    @Override
    public String getBarcode() {
        return prefsHelper.getBarcode();
    }

    @Override
    public void setLoggedMode(boolean loggedIn) {
        prefsHelper.setLoggedMode(loggedIn);
    }

    public interface GetTokenCallbacks {
        void onSuccess(LoginResponse response);

        void onError();
    }

    public interface GetWordTranslation {
        void onSuccess(List<Translate> response);

        void onError();
    }

    public interface GetEnglishInformation {
        void onSuccess(EngInformationResponse response);

        void onError();
    }

    public interface GetVoidPostCallback {
        void onSuccess(Response<String> response);
        void onError(Throwable t);
    }
}
