package com.uibenglish.aslan.mvpmindorkssample.data.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.uibenglish.aslan.mvpmindorkssample.ui.main.content.Grammar;
import com.uibenglish.aslan.mvpmindorkssample.ui.main.content.Reading;
import com.uibenglish.aslan.mvpmindorkssample.ui.main.content.Topic;
import com.uibenglish.aslan.mvpmindorkssample.ui.main.content.Word;

import org.w3c.dom.Text;

import java.util.List;

@Dao
public interface TopicDao {

    @Query("SELECT * FROM topics")
    List<Topic> getAll();

    @Query("SELECT * FROM topics where number LIKE  :firstName")
    Topic findByName(String firstName);

    @Query("SELECT COUNT(*) from topics")
    int countUsers();

    @Insert
    void insertAll(Topic... topics);

    @Delete
    void delete(Topic user);

    @Insert
    void insertWord(Word word);

    @Query("Select * from words")
    List<Word> getAllWords();

    @Query("Select * from words where topic_id = :topicId")
    List<Word> findByTopicId(String topicId);


    //Reading  table

    @Insert
    void insertReading(Reading reading);

    @Query("Select * from reading where topic_id = :topicId")
    List<Reading> findTextById(String topicId);


    //Grammar table

    @Insert
    void insertGrammar(Grammar grammar);

    @Query("Select * from grammar where topic_id = :topic_id")
    List<Grammar> findGrammarById(String topic_id);

}
