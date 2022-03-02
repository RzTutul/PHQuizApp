package com.example.quizapp.network;

import com.example.quizapp.model.QuestionResponse;

import retrofit2.Call;
import retrofit2.http.GET;


public interface ApiInterface {

    @GET("quiz.json")
    Call<QuestionResponse> getAllQuestion();


}
