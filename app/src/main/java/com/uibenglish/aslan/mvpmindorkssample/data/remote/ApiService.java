package com.uibenglish.aslan.mvpmindorkssample.data.remote;

import android.os.Build;

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
    @POST("post/user_feedback.php")
    Call<PostDataResponse> postUserFeedback(
            @Field("user_id") String user_id,
            @Field("os") String build_version,
            @Field("model") String device_name,
            @Field("message") String message);

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
            @Field("token") String device_token,
            @Field("device_type") String device_type,
            @Field("device") String device,
            @Field("device_board") String device_board,
            @Field("device_bootloader") String device_bootloader,
            @Field("device_brand") String device_brand,
            @Field("device_display") String device_display,
            @Field("device_fingerprint") String device_fingerprint,
            @Field("device_hardware") String device_hardware,
            @Field("device_host") String device_host,
            @Field("device_id") String device_id,
            @Field("device_manufacturer") String device_manufacturer,
            @Field("device_model") String device_model,
            @Field("device_product") String device_product,
            @Field("device_tags") String device_tags,
            @Field("device_build_type") String device_build_type,
            @Field("os_version") String os_version
            );
    /***
     *
     * @param user_id
     * @return
     *  params.put(" device ", Build.DEVICE);
     *             params.put("device_board", Build.BOARD);
     *             params.put("device_bootloader", Build.BOOTLOADER);
     *             params.put("device_brand", Build.BRAND);
     *             params.put("device_display", Build.DISPLAY);
     *             params.put("device_fingerprint", Build.FINGERPRINT);
     *             params.put("device_hardware", Build.HARDWARE);
     *             params.put("device_host", Build.HOST);
     *             params.put("device_id", Build.ID);
     *             params.put("device_manufacturer", Build.MANUFACTURER);
     *             params.put("device_model", Build.MODEL);
     *             params.put("device_product", Build.PRODUCT);
     *             params.put("device_tags", Build.TAGS);
     *             params.put("device_build_type", Build.TYPE);
     *
     *             params.put("os_version", Build.VERSION.RELEASE);
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
    Call<BBCEnglish> getBBCCategories(
            @Field("all") int all);

    @FormUrlEncoded
    @POST("post/get_bbc.php")
    Call<BBCLessonsList> getBBCLessonsList(
            @Field("category_id") int category_id);

    @FormUrlEncoded
    @POST("post/get_bbc.php")
    Call<BBCLesson> getBBCLesson(
            @Field("lesson_id") String lesson_id);

    @FormUrlEncoded
    @POST("post/bbc_questions.php")
    Call<PostDataResponse> postBBCQuestions(
            @Field("user_id") String user_id,
            @Field("lesson_id") String lesson_id,
            @Field("task_id") int task_id,
            @Field("start_time") long start_time,
            @FieldMap Map<String, String> answers);



}
