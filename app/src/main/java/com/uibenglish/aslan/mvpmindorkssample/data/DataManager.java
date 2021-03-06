package com.uibenglish.aslan.mvpmindorkssample.data;

import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.util.Log;

import com.uibenglish.aslan.mvpmindorkssample.BuildConfig;
import com.uibenglish.aslan.mvpmindorkssample.data.content.EngInformationResponse;
import com.uibenglish.aslan.mvpmindorkssample.data.content.LoginResponse;
import com.uibenglish.aslan.mvpmindorkssample.data.content.Translate;
import com.uibenglish.aslan.mvpmindorkssample.data.content.TranslationResponse;
import com.uibenglish.aslan.mvpmindorkssample.data.local.AppDatabase;
import com.uibenglish.aslan.mvpmindorkssample.data.local.PreferenceHelper;
import com.uibenglish.aslan.mvpmindorkssample.data.local.SharedPrefsHelper;
import com.uibenglish.aslan.mvpmindorkssample.data.models.BBCCategories;
import com.uibenglish.aslan.mvpmindorkssample.data.models.BBCEnglish;
import com.uibenglish.aslan.mvpmindorkssample.data.models.BBCLesson;
import com.uibenglish.aslan.mvpmindorkssample.data.models.BBCLessonsList;
import com.uibenglish.aslan.mvpmindorkssample.data.models.EnglishInfo;
import com.uibenglish.aslan.mvpmindorkssample.data.models.PostDataResponse;
import com.uibenglish.aslan.mvpmindorkssample.data.models.ResultStudentTasks;
import com.uibenglish.aslan.mvpmindorkssample.data.models.WordCollection;
import com.uibenglish.aslan.mvpmindorkssample.data.remote.ApiFactory;
import com.uibenglish.aslan.mvpmindorkssample.ui.main.content.Grammar;
import com.uibenglish.aslan.mvpmindorkssample.ui.main.content.Listening;
import com.uibenglish.aslan.mvpmindorkssample.ui.main.content.Reading;
import com.uibenglish.aslan.mvpmindorkssample.ui.main.content.Topic;
import com.uibenglish.aslan.mvpmindorkssample.ui.main.content.Word;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.JsonObject;
import com.uibenglish.aslan.mvpmindorkssample.utils.CommonUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.constraint.Constraints.TAG;

/**
 * Created by aslan on 17.05.2018.
 */

public class DataManager implements DataManagerContract {

    private static final String TAG = "DataManager";

    private PreferenceHelper prefsHelper;
    private AppDatabase appDatabase;

    public DataManager(SharedPrefsHelper prefsHelper, AppDatabase appDatabase) {
        this.prefsHelper = prefsHelper;
        this.appDatabase = appDatabase;
    }

    // TODO: BBCEnglish routing


    public void getBBCCategories(final GetBBCCategories callback){
        ApiFactory.changeApiBaseUrl(BuildConfig.API_ENDPOINT_ENG);
        ApiFactory.recreate();
        ApiFactory.
                getApiService().
                getBBCCategories(1).
                enqueue(new Callback<BBCEnglish>() {
            @Override
            public void onResponse(Call<BBCEnglish> call, Response<BBCEnglish> response) {
                if(response.isSuccessful()){
                    if (response.body().getStatus() == 1 &&
                            response.body().getCategories().size()!=0){
                        callback.onSuccess(response.body().getCategories());
                    }
                    else callback.onError(new Throwable());
                }
            }

            @Override
            public void onFailure(Call<BBCEnglish> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public void getBBCLessonList(int category, final GetBBCLessonList callback){
        ApiFactory.changeApiBaseUrl(BuildConfig.API_ENDPOINT_ENG);
        ApiFactory.recreate();
        ApiFactory.
                getApiService().
                getBBCLessonsList(category).
                enqueue(new Callback<BBCLessonsList>() {
            @Override
            public void onResponse(Call<BBCLessonsList> call, Response<BBCLessonsList> response) {
                if (response.isSuccessful()
                        && response.body().getStatus() == 1
                        && response.body().getLessons().size()!=0){
                    callback.onSuccess(response.body().getLessons());
                }
                else callback.onError(new Throwable());
            }

            @Override
            public void onFailure(Call<BBCLessonsList> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public void getBBCLesson(String lesson_id, final GetBBCLesson callback){
        ApiFactory.
                getApiService().
                getBBCLesson(lesson_id).
                enqueue(new Callback<BBCLesson>() {
            @Override
            public void onResponse(Call<BBCLesson> call, Response<BBCLesson> response) {
                if (response.isSuccessful() &&
                        response.body().getStatus()==1){
                    callback.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<BBCLesson> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public void postBBCQuestions(String lesson_id, int taskId, long start_time, Map<String, String> answers,  final GetVoidPostCallback callback){
        ApiFactory
                .getApiService()
                .postBBCQuestions(getPrefUserid(), lesson_id, taskId, start_time, answers)
                .enqueue(new Callback<PostDataResponse>() {
                    @Override
                    public void onResponse(Call<PostDataResponse> call, Response<PostDataResponse> response) {
                        callback.onSuccess(response);
                    }

                    @Override
                    public void onFailure(Call<PostDataResponse> call, Throwable t) {
                        callback.onError(t);
                    }
                });
    }



    //TODO: Token push notification

    public void sendDeviceToken(final GetVoidPostCallback callback){
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String newToken = instanceIdResult.getToken();
                Log.e("newToken",newToken);
                ApiFactory.changeApiBaseUrl(BuildConfig.API_ENDPOINT_ENG);
                ApiFactory.recreate();
                ApiFactory.getApiService()
                        .sendTokenToServer(
                                getPrefUserid(),
                                newToken,
                                "android",
                                Build.DEVICE,
                                Build.BOARD,
                                Build.BOOTLOADER,
                                Build.BRAND,
                                Build.DISPLAY,
                                Build.FINGERPRINT,
                                Build.HARDWARE,
                                Build.HOST,
                                Build.ID,
                                Build.MANUFACTURER,
                                Build.MODEL,
                                Build.PRODUCT,
                                Build.TAGS,
                                Build.TYPE,
                                Build.VERSION.RELEASE)
                        .enqueue(new Callback<PostDataResponse>() {
                            @Override
                            public void onResponse(Call<PostDataResponse> call, Response<PostDataResponse> response) {
                                if (response.isSuccessful())
                                    callback.onSuccess(response);
                            }

                            @Override
                            public void onFailure(Call<PostDataResponse> call, Throwable t) {
                                callback.onError(t);
                            }
                        });

            }
        });
    }


    //TODO: English information


    public void postUserFeedback(String message, final GetVoidPostCallback callback){
        ApiFactory.changeApiBaseUrl(BuildConfig.API_ENDPOINT_ENG);
        ApiFactory.recreate();
        ApiFactory
                .getApiService()
                .postUserFeedback(getPrefUserid(), Build.VERSION.RELEASE, CommonUtils.getDeviceName(), message)
                .enqueue(new Callback<PostDataResponse>() {
            @Override
            public void onResponse(Call<PostDataResponse> call, Response<PostDataResponse> response) {
                callback.onSuccess(response);
            }
            @Override
            public void onFailure(Call<PostDataResponse> call, Throwable t) {
                callback.onError(t);
            }
        });

    }


    public void sendForToken(String username, String password, final GetTokenCallbacks callback) {
        ApiFactory.changeApiBaseUrl(BuildConfig.API_ENDPOINT_MOODLE);
        ApiFactory.recreate();
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
                        callback.onError(t);
                    }
                });
    }

    public void requestForStudentInformation(final GetStudentInformation callback){
        ApiFactory.changeApiBaseUrl(BuildConfig.API_ENDPOINT_MOODLE);
        ApiFactory.recreate();
        ApiFactory
                .getApiService()
                .getStudentInformation(getPrefUserid())
                .enqueue(new Callback<EnglishInfo>() {
                    @Override
                    public void onResponse(Call<EnglishInfo> call, Response<EnglishInfo> response) {
                        if (response.isSuccessful())
                            callback.onSuccess(response.body());
                    }

                    @Override
                    public void onFailure(Call<EnglishInfo> call, Throwable t) {
                        Log.e(TAG, "onFailure: "+t.getMessage()+" "+t.getStackTrace().toString()+ t.getLocalizedMessage() );
                        callback.onError();
                    }
                });
    }

    public void requestForEnglishInformation(final GetEnglishInformation callback) {

        /*FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String newToken = instanceIdResult.getToken();
                Log.e("newToken",newToken);

            }
        });*/

        ApiFactory.changeApiBaseUrl(BuildConfig.API_ENDPOINT_MOODLE);
        ApiFactory.recreate();
        ApiFactory
                .getApiService()
                .getStudentSubjectInformation(getPrefUserid())
                .enqueue(new Callback<EngInformationResponse>() {
                    @Override
                    public void onResponse(Call<EngInformationResponse> call, Response<EngInformationResponse> response) {
                        if (response.isSuccessful())
                            callback.onSuccess(response.body());
                    }

                    @Override
                    public void onFailure(Call<EngInformationResponse> call, Throwable t) {
                        Log.e(TAG, "onFailure: "+t.getMessage()+" "+t.getStackTrace() );
                        callback.onError();
                    }
                });
    }

    public void requestForWordTranslation(String word, final GetWordTranslation callback) {
        ApiFactory
                .getApiService()
                .getWordTranslate("https://api.lingualeo.com/gettranslates?port=1001&word=" + word)
                .enqueue(new Callback<TranslationResponse>() {
                    @Override
                    public void onResponse(Call<TranslationResponse> call, Response<TranslationResponse> response) {
                        if (response.body().getErrorMsg().equals(""))
                            callback.onSuccess(response.body());
                        else callback.onError();
                    }

                    @Override
                    public void onFailure(Call<TranslationResponse> call, Throwable t) {
                        callback.onError();
                    }
                });
    }


    public void postChapterResult(Map<String, Integer> jsonObject, int result, String topic_id, String chapter, long start_time, final GetVoidPostCallback callback) {
        ApiFactory.changeApiBaseUrl(BuildConfig.API_ENDPOINT_ENG);
        ApiFactory.recreate();
        ApiFactory.getApiService()
                .postVocabularyResult(chapter, getPrefUserid(), topic_id, result, start_time,  jsonObject)
                .enqueue(new Callback<PostDataResponse>() {
                    @Override
                    public void onResponse(Call<PostDataResponse> call, Response<PostDataResponse> response) {
                        if (response.isSuccessful())
                            callback.onSuccess(response);
                    }

                    @Override
                    public void onFailure(Call<PostDataResponse> call, Throwable t) {
                        callback.onError(t);
                    }
                });
    }

    public void postToChangeWordAsKnown(String word_id, final GetVoidPostCallback callback){
        ApiFactory.getApiService()
                .changeWordAsKnown(word_id)
                .enqueue(new Callback<PostDataResponse>() {
                    @Override
                    public void onResponse(Call<PostDataResponse> call, Response<PostDataResponse> response) {
                        if (response.isSuccessful())
                            callback.onSuccess(response);
                    }

                    @Override
                    public void onFailure(Call<PostDataResponse> call, Throwable t) {
                        callback.onError(t);
                    }
                });
    }

    public void postUnknownWord(String word, final GetVoidPostCallback callback){
        ApiFactory.changeApiBaseUrl(BuildConfig.API_ENDPOINT_ENG);
        ApiFactory.recreate();
        ApiFactory.getApiService()
                .postUnknownWord(getPrefUserid(), getCourseId(), word)
                .enqueue(new Callback<PostDataResponse>() {
                    @Override
                    public void onResponse(Call<PostDataResponse> call, Response<PostDataResponse> response) {
                        if (response.isSuccessful())
                            callback.onSuccess(response);
                    }

                    @Override
                    public void onFailure(Call<PostDataResponse> call, Throwable t) {
                        callback.onError(t);
                    }
                });

    }

    public void getStudentDictionary(final GetWordCollectionCallback callback){
        ApiFactory.changeApiBaseUrl(BuildConfig.API_ENDPOINT_ENG);
        ApiFactory.recreate();
        ApiFactory.getApiService()
                .getStudentWallet(getPrefUserid(), getCourseId())
                .enqueue(new Callback<List<WordCollection>>() {
                    @Override
                    public void onResponse(Call<List<WordCollection>> call, Response<List<WordCollection>> response) {
                        if (response.isSuccessful())
                            callback.onSuccess(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<WordCollection>> call, Throwable t) {
                        callback.onError(t);
                    }
                });
    }

    public void getReadingArray(String topic_id, final GetReadingListCallback callback){
        ApiFactory.changeApiBaseUrl(BuildConfig.API_ENDPOINT_ENG);
        ApiFactory.recreate();
        ApiFactory.getApiService()
                .getReadingArray(topic_id)
                .enqueue(new Callback<List<Reading>>() {
                    @Override
                    public void onResponse(Call<List<Reading>> call, Response<List<Reading>> response) {
                        if (response.isSuccessful())
                            callback.onSuccess(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<Reading>> call, Throwable t) {
                        callback.onError(t);
                    }
                });
    }

    public void postReadingResult(String topic_id, Integer result_tf, Integer result_ans, long startTime,  final GetVoidPostCallback callback){
        ApiFactory.getApiService()
                .postReadingResult(getPrefUserid(), topic_id, result_ans, result_tf, startTime)
                .enqueue(new Callback<PostDataResponse>() {
                    @Override
                    public void onResponse(Call<PostDataResponse> call, Response<PostDataResponse> response) {
                        if (response.isSuccessful()){
                            callback.onSuccess(response);
                        }
                    }

                    @Override
                    public void onFailure(Call<PostDataResponse> call, Throwable t) {
                        callback.onError(t);
                    }
                });

    }

    public void getListeningArray(String topic_id, final GetListeningListCallback callback){
        ApiFactory.changeApiBaseUrl(BuildConfig.API_ENDPOINT_ENG);
        ApiFactory.recreate();
        ApiFactory.getApiService()
                .getListeningArray(topic_id)
                .enqueue(new Callback<List<Listening>>() {
                    @Override
                    public void onResponse(Call<List<Listening>> call, Response<List<Listening>> response) {
                        if (response.isSuccessful())
                            callback.onSuccess(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<Listening>> call, Throwable t) {
                        callback.onError(t);
                    }
                });
    }

    public void postListeningResult(String topic_id, int result_ans, long startTime,  final GetVoidPostCallback callback){
        ApiFactory.getApiService()
                .postListeningResult(getPrefUserid(), topic_id, result_ans, startTime)
                .enqueue(new Callback<PostDataResponse>() {
                    @Override
                    public void onResponse(Call<PostDataResponse> call, Response<PostDataResponse> response) {
                        if (response.isSuccessful()){
                            callback.onSuccess(response);
                        }
                    }

                    @Override
                    public void onFailure(Call<PostDataResponse> call, Throwable t) {
                        callback.onError(t);
                    }
                });
    }

    public void getGrammarArray(String topic_id, final GetGrammarListCallback callback){
        ApiFactory.changeApiBaseUrl(BuildConfig.API_ENDPOINT_ENG);
        ApiFactory.recreate();
        ApiFactory.getApiService()
                .getGrammarArray(topic_id)
                .enqueue(new Callback<List<Grammar>>() {
                    @Override
                    public void onResponse(Call<List<Grammar>> call, Response<List<Grammar>> response) {
                        if (response.isSuccessful())
                            callback.onSuccess(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<Grammar>> call, Throwable t) {
                        callback.onError(t);
                    }
                });
    }

    public void postGrammarResult(String topicId, int result_ans, int result_cons, long startTime, final GetVoidPostCallback callback) {
        ApiFactory.changeApiBaseUrl(BuildConfig.API_ENDPOINT_ENG);
        ApiFactory.recreate();
        ApiFactory.getApiService()
                .postGrammarResultNew(getPrefUserid(), topicId, result_ans, result_cons, startTime)
                .enqueue(new Callback<PostDataResponse>() {
                    @Override
                    public void onResponse(Call<PostDataResponse> call, Response<PostDataResponse> response) {
                        if(response.isSuccessful())
                            callback.onSuccess(response);
                    }

                    @Override
                    public void onFailure(Call<PostDataResponse> call, Throwable t) {
                        callback.onError(t);
                    }
                });
    }



    public void getUserResult(final GetUserResultCallback callback){
        ApiFactory.changeApiBaseUrl(BuildConfig.API_ENDPOINT_ENG);
        ApiFactory.recreate();
        ApiFactory.getApiService()
                .getUserResult(getPrefUserid(), getCourseId())
                .enqueue(new Callback<ResultStudentTasks>() {
                    @Override
                    public void onResponse(Call<ResultStudentTasks> call, Response<ResultStudentTasks> response) {
                        if (response.isSuccessful())
                            callback.onSuccess(response.body());
                    }

                    @Override
                    public void onFailure(Call<ResultStudentTasks> call, Throwable t) {
                        callback.onError(t);
                    }
                });
    }

    //TODO: Realm for vocabulary

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
        //appDatabase.topicDao().insertGrammar(grammar);
    }

/*    public List<Grammar> getGrammarByTopicId(String topicId) {
        return appDatabase.topicDao().findGrammarById(topicId);
    }*/

    public List<Word> getAllWords() {
        return appDatabase.topicDao().getAllWords();
    }

    public List<Word> getWordsByTopicId(String topicId) {
        return appDatabase.topicDao().findByTopicId(topicId);
    }

    public List<Reading> getTextByTopicId(String topicId) {
        return appDatabase.topicDao().findTextById(topicId);
    }


    //TODO: Shared Preference

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

    @Override
    public void putCourseId(String course_id) {
        prefsHelper.putCourseId(course_id);
    }

    @Override
    public String getCourseId() {
        return prefsHelper.getCourseId();
    }

    @Override
    public void putName(String name) {
        prefsHelper.putName(name);
    }

    @Override
    public String getName() {
        return prefsHelper.getName();
    }

    @Override
    public void putGroup(String group) {
        prefsHelper.putGroup(group);
    }

    @Override
    public String getGroup() {
        return prefsHelper.getGroup();
    }

    @Override
    public void putProgram(String program) {
        prefsHelper.putProgram(program);
    }

    @Override
    public String getProgram() {
        return prefsHelper.getProgram();
    }

    //TODO: Server callbacks

    public interface GetTokenCallbacks {
        void onSuccess(LoginResponse response);
        void onError(Throwable t);
    }

    public interface GetWordTranslation {
        void onSuccess(TranslationResponse response);
        void onError();
    }

    public interface GetEnglishInformation {
        void onSuccess(EngInformationResponse response);

        void onError();
    }

    public interface GetStudentInformation {
        void onSuccess(EnglishInfo response);
        void onError();
    }



    public interface GetVoidPostCallback {
        void onSuccess(Response<PostDataResponse> response);
        void onError(Throwable t);
    }

    public interface GetWordCollectionCallback{
        void onSuccess(List<WordCollection> wordCollectionList);
        void onError(Throwable t);
    }

    public interface GetReadingListCallback{
        void onSuccess(List<Reading> readingList);
        void onError(Throwable t);
    }

    public interface GetListeningListCallback{
        void onSuccess(List<Listening> readingList);
        void onError(Throwable t);
    }

    public interface GetGrammarListCallback{
        void onSuccess(List<Grammar> grammarList);
        void onError(Throwable t);
    }
    public interface GetUserResultCallback{
        void onSuccess(ResultStudentTasks resultStudentTasks);
        void onError(Throwable t);
    }

    public interface GetBBCCategories{
        void onSuccess(List<BBCCategories> bbcCategories);
        void onError(Throwable t);
    }
    public interface GetBBCLessonList{
        void onSuccess(List<BBCLessonsList.Lesson> bbcLessonsLists);
        void onError(Throwable t);
    }

    public interface GetBBCLesson{
        void onSuccess(BBCLesson bbcLessons);
        void onError(Throwable t);
    }
}
