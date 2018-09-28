package com.uibenglish.aslan.mvpmindorkssample.ui.main.definition;

import com.uibenglish.aslan.mvpmindorkssample.R;
import com.uibenglish.aslan.mvpmindorkssample.data.DataManager;
import com.uibenglish.aslan.mvpmindorkssample.data.content.TranslationResponse;
import com.uibenglish.aslan.mvpmindorkssample.data.models.PostDataResponse;
import com.uibenglish.aslan.mvpmindorkssample.ui.main.definition.DefinitionContract;
import com.uibenglish.aslan.mvpmindorkssample.ui.base.BasePresenter;

import retrofit2.Response;

public class DefinitionPresenter <V extends DefinitionContract.DefinitionMvpView> extends BasePresenter<V> implements DefinitionContract.DefinitionMvpPresenter<V> {

    public DefinitionPresenter(DataManager mDataManager) {
        super(mDataManager);
    }

    @Override
    public void getWordDefinition(String word) {
        getMvpView().showLoading();
        getDataManager().requestForWordTranslation(word, new DataManager.GetWordTranslation() {
            @Override
            public void onSuccess(TranslationResponse response) {
                if (response.getWordForms().isEmpty()){
                    getMvpView().showToastMessage(R.string.not_single_word);
                }
                else {
                    getMvpView().setWordDefinition(response);
                }
                getMvpView().hideLoading();
            }

            @Override
            public void onError() {
                getMvpView().hideLoading();
            }
        });
    }

    @Override
    public void addToDictionaryAfterShow(String word) {
        if (word.equals("")||word.isEmpty()) {
            return;
        }

        getMvpView().showLoading();
        getDataManager().postUnknownWord(word, new DataManager.GetVoidPostCallback() {
            @Override
            public void onSuccess(Response<PostDataResponse> response) {
                getMvpView().hideLoading();
                getMvpView().showToastMessage(R.string.added_to_word_book);
            }

            @Override
            public void onError(Throwable t) {
                getMvpView().hideLoading();

                getMvpView().showToastMessage(R.string.get_wrong);
            }
        });
    }
}
