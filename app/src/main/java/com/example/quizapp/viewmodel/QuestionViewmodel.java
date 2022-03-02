package com.example.quizapp.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.quizapp.model.Question;
import com.example.quizapp.model.QuestionResponse;
import com.example.quizapp.repository.QuestionRepository;

import java.util.List;

public class QuestionViewmodel extends ViewModel {
    QuestionRepository questionRepository;

    public QuestionViewmodel() {
        questionRepository = new QuestionRepository();
    }

    public MutableLiveData<List<Question>> getAllQuestion() {
        return questionRepository.getAllQuestion();
    }

}