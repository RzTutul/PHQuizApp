package com.example.quizapp.adapter;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapp.QuestionFrag;
import com.example.quizapp.R;
import com.example.quizapp.databinding.OptionLayoutBinding;
import com.example.quizapp.model.Answers;

import java.util.ArrayList;
import java.util.List;

public class OptionAdapter extends RecyclerView.Adapter<OptionAdapter.OptionViewHolder> {

    List<String> list = new ArrayList<>();
    Context context;
    ItemOnClickListener listener;
    int correctOption = 0;
    int selectOption = 0;

    public OptionAdapter(Context context) {
        this.context = context;
    }

    @NonNull

    @Override
    public OptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        OptionLayoutBinding binding = OptionLayoutBinding.inflate(LayoutInflater.from(context), parent, false);
        return new OptionViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(@NonNull OptionViewHolder holder, int position) {

        String optionValue = list.get(position);

        holder.mbinding.optionTV.setText(optionValue);

        holder.mbinding.optionTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.selectOption((position + 1));
            }
        });

        holder.mbinding.optionTV.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.white_round_shape, 0);
        holder.mbinding.optionTV.setBackground(context.getResources().getDrawable(R.drawable.choose_ans_border));

        if (correctOption == selectOption && correctOption!=0) {
            holder.mbinding.optionTV.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_check, 0);
            holder.mbinding.optionTV.setBackground(context.getResources().getDrawable(R.drawable.choose_ans_right_border));
        }

         if (correctOption != selectOption && correctOption!=0) {
            holder.mbinding.optionTV.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_wrong, 0);
            holder.mbinding.optionTV.setBackground(context.getResources().getDrawable(R.drawable.choose_ans_wrong_border));
        }




    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void showCorrectAns(int correctAns, int select) {
        correctOption = correctAns;
        selectOption = select;
        notifyDataSetChanged();
    }

    private void EnableOptionClick(TextView te) {
        //  optionTV.setClickable(true);
    }

    private void DisableOptionClick() {
        //optionTV.setClickable(false);
    }


    public void setItemListener(ItemOnClickListener itemListener) {
        this.listener = itemListener;
    }

    public interface ItemOnClickListener {
        void selectOption(int selectedOption);
    }


    public void setOptionList(List<String> modelList) {
        this.list = modelList;
        correctOption=0;
        selectOption =0;
        notifyDataSetChanged();
    }


    public static class OptionViewHolder extends RecyclerView.ViewHolder {

        OptionLayoutBinding mbinding;

        public OptionViewHolder(@NonNull OptionLayoutBinding binding) {
            super(binding.getRoot());
            mbinding = binding;

        }
    }
}
