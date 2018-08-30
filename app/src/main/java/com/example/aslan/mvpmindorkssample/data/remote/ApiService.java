package com.example.aslan.mvpmindorkssample.data.remote;

import com.example.aslan.mvpmindorkssample.data.content.EngInformationResponse;
import com.example.aslan.mvpmindorkssample.data.content.LoginResponse;
import com.example.aslan.mvpmindorkssample.data.content.TranslationResponse;
import com.example.aslan.mvpmindorkssample.data.models.PostDataResponse;
import com.example.aslan.mvpmindorkssample.data.models.WordCollection;
import com.example.aslan.mvpmindorkssample.ui.main.content.Grammar;
import com.example.aslan.mvpmindorkssample.ui.main.content.Listening;
import com.example.aslan.mvpmindorkssample.ui.main.content.Reading;
import com.mindorks.placeholderview.annotations.Position;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
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
    Call<LoginResponse> getValidationInfo(@Field("username") String username, @Field("password") String password);

    /***
     *
     * @param user_id add device token
     * @return
     */
    @FormUrlEncoded
    @POST("mobile_eng/data.php")
    Call<EngInformationResponse> getStudentSubjectInformation(@Field("user_id") String user_id);

    /***
     *
     * @param url lingualeo
     * @return
     */
    @GET
    Call<TranslationResponse> getWordTranslate(@Url String url);


    /***
     * de.uib.kz
     *
     *
     * @return
     */

    @FormUrlEncoded
    @POST("modules/online_lessons/add_results.php")
    Call<PostDataResponse> postGrammarResult(@Field("exercise_id") String ex_id, @Field("user_id") String user_id, @Field("topic_id") int topic_id, @Field("result") int result);

    @POST("modules/online_lessons/add_results.php")
    Call<PostDataResponse> postTaskResult(@Body String data);

    @FormUrlEncoded
    @POST("post/user_word_data.php")
    Call<PostDataResponse> postUnknownWord(@Field("user_id") String user_id, @Field("course_id") String course_id, @Field("word") String word);

    @FormUrlEncoded
    @POST("post/user_word_data.php")
    Call<PostDataResponse> changeWordAsKnown(@Field("word_id") String word_id);

    @FormUrlEncoded
    @POST("post/reading_questions.php")
    Call<PostDataResponse> postReadingResult(@Field("user_id") String user_id, @Field("topic_id") String topic_id, @Field("result_ans") int result_ans, @Field("result_tf") int result_tf, @Field("start_time") long start_time);

    @FormUrlEncoded
    @POST("post/listening_questions.php")
    Call<PostDataResponse> postListeningResult(@Field("user_id") String user_id, @Field("topic_id") String topic_id, @Field("result_ans") int result_ans, @Field("start_time") long start_time);

    @FormUrlEncoded
    @POST("post/grammatika_questions.php")
    Call<PostDataResponse> postGrammarResultNew(@Field("user_id") String user_id, @Field("topic_id") String topic_id, @Field("result_ans") int result_ans, @Field("result_cons") int result_cons, @Field("start_time") long start_time);

    /***
     *
     * @param user_id
     * @return
     */
    @FormUrlEncoded
    @POST("post/grammatika_questions.php")
    Call<PostDataResponse> postToDeleteDeviceToken(@Field("user_id") String user_id);


    //Get
    @FormUrlEncoded
    @POST("post/words_data.php")
    Call<List<WordCollection>> getStudentWallet(@Field("user_id") String user_id, @Field("course_id") String course_id);

    @FormUrlEncoded
    @POST("post/get_reading.php")
    Call<List<Reading>> getReadingArray(@Field("topic_id") String topic_id);

    @FormUrlEncoded
    @POST("post/get_listening.php")
    Call<List<Listening>> getListeningArray(@Field("topic_id") String topic_id);

    @FormUrlEncoded
    @POST("post/get_grammatika.php")
    Call<List<Grammar>> getGrammarArray(@Field("topic_id") String topic_id);






}
