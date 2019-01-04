package com.uibenglish.aslan.mvpmindorkssample.data.remote;

import com.uibenglish.aslan.mvpmindorkssample.data.content.EngInformationResponse;
import com.uibenglish.aslan.mvpmindorkssample.data.content.LoginResponse;
import com.uibenglish.aslan.mvpmindorkssample.data.content.TranslationResponse;
import com.uibenglish.aslan.mvpmindorkssample.data.models.BBCEnglish;
import com.uibenglish.aslan.mvpmindorkssample.data.models.BBCLesson;
import com.uibenglish.aslan.mvpmindorkssample.data.models.BBCLessonsList;
import com.uibenglish.aslan.mvpmindorkssample.data.models.PostDataResponse;
import com.uibenglish.aslan.mvpmindorkssample.data.models.ResultStudentTasks;
import com.uibenglish.aslan.mvpmindorkssample.data.models.WordCollection;
import com.uibenglish.aslan.mvpmindorkssample.ui.main.content.Grammar;
import com.uibenglish.aslan.mvpmindorkssample.ui.main.content.Listening;
import com.uibenglish.aslan.mvpmindorkssample.ui.main.content.Reading;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface ApiService {

    /***
     * Moodle
     * @param username
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST("mobile/login.php")
    Call<LoginResponse> getValidationInfo(
            @Field("username") String username,
            @Field("password") String password);


    @FormUrlEncoded
    @POST("mobile_eng/data.php")
    Call<EngInformationResponse> getStudentSubjectInformation(
            @Field("user_id") String user_id);

    /***
     *
     * @param url lingualeo
     * @return
     */
    @GET
    Call<TranslationResponse> getWordTranslate(
            @Url String url);


    /***
     * de.uib.kz
     *
     *
     * @return
     */

    @FormUrlEncoded
    @POST("modules/online_lessons/add_results.php")
    Call<PostDataResponse> postVocabularyResult(
            @Field("exercise_id") String ex_id,
            @Field("user_id") String user_id,
            @Field("topic_id") String topic_id,
            @Field("result") int result,
            @Field("start_time") long start_time,
            @FieldMap Map<String, Integer> each_result);

    @FormUrlEncoded
    @POST("modules/online_lessons/add_results.php")
    Call<PostDataResponse> postTaskResult(
            @FieldMap Map<String, String> data);

    @FormUrlEncoded
    @POST("post/user_word_data.php")
    Call<PostDataResponse> postUnknownWord(
            @Field("user_id") String user_id,
            @Field("course_id") String course_id,
            @Field("word") String word);

    @FormUrlEncoded
    @POST("post/user_word_data.php")
    Call<PostDataResponse> changeWordAsKnown(
            @Field("word_id") String word_id);

    @FormUrlEncoded
    @POST("post/reading_questions.php")
    Call<PostDataResponse> postReadingResult(
            @Field("user_id") String user_id,
            @Field("topic_id") String topic_id,
            @Field("result_ans") Integer result_ans,
            @Field("result_tf") Integer result_tf,
            @Field("start_time") long start_time);

    @FormUrlEncoded
    @POST("post/listening_questions.php")
    Call<PostDataResponse> postListeningResult(
            @Field("user_id") String user_id,
            @Field("topic_id") String topic_id,
            @Field("result_ans") int result_ans,
            @Field("start_time") long start_time);

    @FormUrlEncoded
    @POST("post/grammatika_questions.php")
    Call<PostDataResponse> postGrammarResultNew(
            @Field("user_id") String user_id,
            @Field("topic_id") String topic_id,
            @Field("result_ans") int result_ans,
            @Field("result_cons") int result_cons,
            @Field("start_time") long start_time);

    @FormUrlEncoded
    @POST("mobile/tokens.php")
    Call<PostDataResponse> sendTokenToServer(
            @Field("user_id") String user_id,
            @Field("device_token") String device_token);


    @FormUrlEncoded
    @POST("post/bbc_questions.php")
    Call<BBCLesson> postBBCLesson(
            @Field("user_id") String user_id,
            @Field("lesson_id") int lesson_id,
            @FieldMap Map<String, String> vocabulary,
            @FieldMap Map<String, String> transcript);

    /***
     *
     * @param user_id
     * @return
     */

    //Get
    @FormUrlEncoded
    @POST("post/words_data.php")
    Call<List<WordCollection>> getStudentWallet(
            @Field("user_id") String user_id,
            @Field("course_id") String course_id);

    @FormUrlEncoded
    @POST("post/get_reading.php")
    Call<List<Reading>> getReadingArray(
            @Field("topic_id") String topic_id);

    @FormUrlEncoded
    @POST("post/get_listening.php")
    Call<List<Listening>> getListeningArray(
            @Field("topic_id") String topic_id);

    @FormUrlEncoded
    @POST("post/get_grammatika.php")
    Call<List<Grammar>> getGrammarArray(
            @Field("topic_id") String topic_id);

    @FormUrlEncoded
    @POST("post/get_user_results.php")
    Call<ResultStudentTasks> getUserResult(
            @Field("user_id") String user_id,
            @Field("course_id") String course_id);

    @FormUrlEncoded
    @POST("post/get_bbc.php")
    Call<BBCEnglish> getBBCCategories(@Field("all") int all);

    @FormUrlEncoded
    @POST("post/get_bbc.php")
    Call<BBCLessonsList> getBBCLessonsList(@Field("category_id") int category_id);

    @FormUrlEncoded
    @POST("post/get_bbc.php")
    Call<BBCLesson> getBBCLesson(@Field("lesson_id") String lesson_id);









}
