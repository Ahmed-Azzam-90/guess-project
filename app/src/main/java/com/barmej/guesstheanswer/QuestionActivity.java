package com.barmej.guesstheanswer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class QuestionActivity extends AppCompatActivity {
    private TextView mTextViewQuestion;
    private String[] QUESTIONS;
    private static final Boolean[] ANSWERS={
            false,
            true,
            true,
            false,
            true,
            false,
            false,
            false,
            false,
            true,
            true,
            false,
            true
    };
    private String[] ANSWERS_DETAILS;
    public String mCurrentQuestions , mCurrentAnswerDetail;
    public boolean mCurrentAnswer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextViewQuestion=findViewById(R.id.text_view_question);
        QUESTIONS = getResources().getStringArray(R.array.questions);
        ANSWERS_DETAILS = getResources().getStringArray(R.array.answers_details);
        showNewQuestion();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()== R.id.menuChangeLang){
            showLanguageDialog();
            return true;
        }else{
        return super.onOptionsItemSelected(item);
        }
    }
    private void showLanguageDialog (){
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(R.string.change_lang_text)
                .setItems(R.array.languages, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        String language = "ar";
                        switch (which) {
                            case 0:
                                language = "ar";
                                break;
                            case 1:
                                language = "en";
                                break;
                            case 2:
                                language = "fr";
                                break;
                        }

                        LocaleHelper.setLocale(QuestionActivity.this, language);
                        Intent intent = new Intent(getApplicationContext(), QuestionActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }).create();
        alertDialog.show();
    }

    private void showNewQuestion(){
        Random mRandom = new Random();
        int randomQuestionIndex = mRandom.nextInt(QUESTIONS.length);
        mCurrentQuestions = QUESTIONS[randomQuestionIndex];
        mCurrentAnswerDetail= ANSWERS_DETAILS[randomQuestionIndex];
        mCurrentAnswer = ANSWERS[randomQuestionIndex];
        mTextViewQuestion.setText(mCurrentQuestions);
    }
    public void onChangeQuestionClicked (View view) {
        showNewQuestion();
    }
    public void onTrueClicked (View view){
        if (mCurrentAnswer==true){
            Toast.makeText(this, "أجابة صحيحة", Toast.LENGTH_SHORT).show();
            showNewQuestion();
        }else{
            Toast.makeText(this, "أجابة خاطئة", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(QuestionActivity.this,AnswerActivity.class);
            intent.putExtra("question answer",mCurrentAnswerDetail);
            startActivity(intent);
        }
    }
    public void onFalseClicked (View view){
        if (mCurrentAnswer==false){
            Toast.makeText(this, "أجابة صحيحة", Toast.LENGTH_SHORT).show();
            showNewQuestion();
        }else {
            Toast.makeText(this, "أجابة خاطئة", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(QuestionActivity.this,AnswerActivity.class);
            intent.putExtra("question answer",mCurrentAnswerDetail);
            startActivity(intent);
        }
    }
    public void onShareQuestionClicked(View view) {
        Intent intent = new Intent(QuestionActivity.this,ShareActivity.class);
        intent.putExtra("question text extra",mCurrentQuestions);
        startActivity(intent);
    }
}