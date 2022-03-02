package com.example.quizapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.quizapp.adapter.OptionAdapter;
import com.example.quizapp.databinding.FragmentQuestionBinding;
import com.example.quizapp.model.Answers;
import com.example.quizapp.model.Question;
import com.example.quizapp.viewmodel.QuestionViewmodel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class QuestionFrag extends Fragment implements OptionAdapter.ItemOnClickListener {

    FragmentQuestionBinding binding;
    QuestionViewmodel viewmodel;
    int quesNo = 0;
    String question;
    List<Question> list = new ArrayList<>();
    int rightAns = 0, wrongAns = 0;
    int points = 0;
    int pointsCounter = 0;
    boolean isSelect = false;
    int timer;
    String ans;
    int selectedOption;
    OptionAdapter adapter;
    private CountDownTimer countDownTimer;

    public QuestionFrag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentQuestionBinding.inflate(getLayoutInflater());
        viewmodel = new ViewModelProvider(this).get(QuestionViewmodel.class);
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setAdapter();

        viewmodel.getAllQuestion().observe(getViewLifecycleOwner(), new Observer<List<Question>>() {
            @Override
            public void onChanged(List<Question> questions) {

                if (questions.size() > 0) {
                    binding.optionLL.setVisibility(View.VISIBLE);
                    list = questions;
                    setUpQuestion(questions);
                } else {
                    binding.questionTV.setText("Question unavailable in this level\n             Try another level !");
                    binding.optionLL.setVisibility(View.GONE);
                    binding.backBtn.setVisibility(View.VISIBLE);

                }

            }
        });

    }



    private void setUpQuestion(List<Question> questionModels) {

        //clearQuesitonView();
        isSelect = false;
        if (questionModels.size() > quesNo) {

            binding.questionProgress.setProgress(quesNo + 1);
            binding.questionProgress.setMax(questionModels.size());
            binding.questionnoTV.setText(String.format(getResources().getString(R.string.question_no), quesNo + 1, questionModels.size()));

            binding.rightAnsTV.setText(String.valueOf(rightAns));
            binding.wrongAnsTV.setText(String.valueOf(wrongAns));
            binding.rightProgess.setProgress(rightAns);
            binding.wrongProgess.setProgress(wrongAns);

         /*   if (rightAns % 5 == 0 && rightAns > 0) {
                showCelebrationAnimation();
            }*/

            Question model = questionModels.get(quesNo);
            setOption(model.getAnswers());

            timer = 10 * 1000;
            ans = model.getCorrectAnswer();

            question = model.getQuestion();
            points = model.getScore();
            binding.progressCircularId.setMax(10);
            binding.questionTV.setText(model.getQuestion());
            binding.pointTV.setText(String.format(getString(R.string.points), points));

            Glide.with(getContext())
                    .load(model.getQuestionImageUrl())
                    .placeholder(R.drawable.logo)
                    .into(binding.questionIV);

            try {
                countDownTimer = new CountDownTimer(timer, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        long seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished);
                        binding.progressCircularId.setProgress((int) seconds);
                        binding.counterTV.setText(String.valueOf((int) seconds));
                    }
                    @Override
                    public void onFinish() {
                        quesNo++;
                        if (!isSelect) {
                            wrongAns++;
                            showCorrectAns();
                        }

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                setUpQuestion(questionModels);
                            }
                        }, 2000);
                    }
                };
                countDownTimer.start();

            } catch (Exception e) {
                e.printStackTrace();
            }


        } else {
            Bundle bundle = new Bundle();
            bundle.putInt("points", pointsCounter);
            //   Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.pointsBoardFrag, bundle);
        }

    }


    private void setOption(Answers answers) {
        adapter.setOptionList(answers.generateOptionList());
    }

    public void setAdapter()
    {
        adapter = new OptionAdapter(getContext());
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        binding.optionRV.setLayoutManager(llm);
        binding.optionRV.setAdapter(adapter);
        adapter.setItemListener(QuestionFrag.this);
    }

    @Override
    public void selectOption(int option) {
        selectedOption = option;

        if (getCorrectAns()==selectedOption) {
            rightAns++;
            pointsCounter = pointsCounter + points;
            showCorrectAns();
            countDownTimer.cancel();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    quesNo++;
                    setUpQuestion(list);
                }
            }, 2000);

        } else {
            wrongAns++;
            showCorrectAns();
            countDownTimer.cancel();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    quesNo++;

                    setUpQuestion(list);
                }
            }, 2000);
        }
    }

    private int getCorrectAns() {

        if (ans.equals("A")) {
            return 1;
        } else if (ans.equals("B")) {
            return 2;
        } else if (ans.equals("C")) {
            return 3;
        } else if (ans.equals("D")) {
            return 4;
        }
        else
        {
            return 0;
        }
    }
    private void showCorrectAns() {
        adapter.showCorrectAns(getCorrectAns(),selectedOption);

    }

}