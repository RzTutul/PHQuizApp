package com.example.quizapp.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.quizapp.model.Question;
import com.example.quizapp.model.QuestionResponse;
import com.example.quizapp.network.ApiInterface;
import com.example.quizapp.network.RetrofitApiCilent;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionRepository {

    MutableLiveData<List<Question>> questionLD = new MutableLiveData<>();
    MutableLiveData<String> errorMsg = new MutableLiveData<>();

    ApiInterface apiInterface;

    public QuestionRepository() {
        apiInterface = RetrofitApiCilent.getApiClient().create(ApiInterface.class);
    }

    public MutableLiveData<List<Question>> getAllQuestion() {
        apiInterface.getAllQuestion().enqueue(new Callback<QuestionResponse>() {
            @Override
            public void onResponse(Call<QuestionResponse> call, Response<QuestionResponse> response) {
                if (response.code()==200&&response.body()!=null)
                {
                    questionLD.postValue(response.body().getQuestions());
                }
                else
                {
                    errorMsg.postValue(response.message());
                }
            }

            @Override
            public void onFailure(Call<QuestionResponse> call, Throwable t) {
                errorMsg.postValue(t.getLocalizedMessage());

            }
        });

        return questionLD;
    }

    public MutableLiveData<String> getErrorMsg()
    {
        return errorMsg;
    }
}
