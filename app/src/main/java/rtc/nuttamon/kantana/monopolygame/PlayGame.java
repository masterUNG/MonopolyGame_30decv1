package rtc.nuttamon.kantana.monopolygame;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

public class PlayGame extends AppCompatActivity {

    // Explicit
    private TextView questionTextView, showTimeTextView, showScoreTextView;
    private RadioButton[] choiceRadioButtons;
    private ImageView imageView;
    private int timesAnInt = 0, myTime = 0, scoreAnInt = 0,
            myChoose = 0;
    private RadioGroup radioGroup;
    private boolean aBoolean = true;
    private String[] questionStrings, choice1Strings, choice2Strings,
            choice3Strings, choice4Strings, answerStrings;
    private ImageView[] termoImageViews = new ImageView[8];
    private int[] soundInts = new int[]{R.raw.ok1, R.raw.no1, R.raw.game_over};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);

        bindWidget();

        clearTermo();

        showView(timesAnInt);

        buttonController();

        redioController();

        checkTime();


    } // Main Method

    private void clearTermo() {

        for (int i=0;i<termoImageViews.length;i++) {
            termoImageViews[i].setVisibility(View.INVISIBLE);
        }

    }

    private void checkTime() {

        //To Do
        if (myTime == 180) {
            soundEffect(soundInts[2]);
            aBoolean = false;
            Intent intent = new Intent(PlayGame.this, ShowScore.class);
            intent.putExtra("Score", scoreAnInt);
            startActivity(intent);
            finish();
        } else {

            showTimeTextView.setText(Integer.toString(180 - myTime) + " วินาที");
            myTime += 1;

        }


        //Delay
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (aBoolean) {
                    checkTime();
                }
            }
        }, 1000);

    }   // checkTime

    private void redioController() {

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                switch (i) {

                    case R.id.radioButton4:
                        myChoose = 1;
                        break;
                    case R.id.radioButton3:
                        myChoose = 2;
                        break;
                    case R.id.radioButton2:
                        myChoose = 3;
                        break;
                    case R.id.radioButton:
                        myChoose = 4;
                        break;

                }   // switch

                Log.d("30decV1", "myChoose ==> " + myChoose);

            }   // onCheck
        });


    }

    private void buttonController() {

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("30decV1", "answer(" + timesAnInt + ")==> " + answerStrings[timesAnInt]);

                //Check Score
                if (myChoose == Integer.parseInt(answerStrings[timesAnInt])) {
                    scoreAnInt += 1;
                    showScoreTextView.setText("คะแนน = " + Integer.toString(scoreAnInt));

                    soundEffect(soundInts[0]);

                } else {
                    soundEffect(soundInts[1]);
                }

                checkTermoImage();

                radioGroup.clearCheck();

                timesAnInt += 1;
                Log.d("26decV1", "times ==> " + timesAnInt);
                showView(timesAnInt);


            }   // onClick
        });


    }   // buttonController

    private void soundEffect(int soundInt) {


        final MediaPlayer mediaPlayer = MediaPlayer.create(getBaseContext(), soundInt);
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mediaPlayer.release();
            }
        });
    }

    private void checkTermoImage() {

        if (scoreAnInt > 0) {
            termoImageViews[0].setVisibility(View.VISIBLE);
            if (scoreAnInt > 2) {
                termoImageViews[1].setVisibility(View.VISIBLE);
                if (scoreAnInt > 4) {
                    termoImageViews[2].setVisibility(View.VISIBLE);
                    if (scoreAnInt > 6) {
                        termoImageViews[3].setVisibility(View.VISIBLE);
                        if (scoreAnInt > 8) {
                            termoImageViews[4].setVisibility(View.VISIBLE);
                            if (scoreAnInt > 10) {
                                termoImageViews[5].setVisibility(View.VISIBLE);
                                if (scoreAnInt > 12) {
                                    termoImageViews[6].setVisibility(View.VISIBLE);
                                    if (scoreAnInt >14) {
                                        termoImageViews[7].setVisibility(View.VISIBLE);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }






    }   // check


    private void showView(int index) {

        try {

            synQuestion objSynQuestion = new synQuestion(PlayGame.this);
            objSynQuestion.execute();
            String s = objSynQuestion.get();

            JSONArray jsonArray = new JSONArray(s);
            questionStrings = new String[jsonArray.length()];
            choice1Strings = new String[jsonArray.length()];
            choice2Strings = new String[jsonArray.length()];
            choice3Strings = new String[jsonArray.length()];
            choice4Strings = new String[jsonArray.length()];
            answerStrings = new String[jsonArray.length()];

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                questionStrings[i] = jsonObject.getString("Question");
                choice1Strings[i] = jsonObject.getString("Choice1");
                choice2Strings[i] = jsonObject.getString("Choice2");
                choice3Strings[i] = jsonObject.getString("Choice3");
                choice4Strings[i] = jsonObject.getString("Choice4");
                answerStrings[i] = jsonObject.getString("Answer");
            }

            questionTextView.setText(Integer.toString(index + 1) + ". " + questionStrings[index]);
            choiceRadioButtons[0].setText(choice1Strings[index]);
            choiceRadioButtons[1].setText(choice2Strings[index]);
            choiceRadioButtons[2].setText(choice3Strings[index]);
            choiceRadioButtons[3].setText(choice4Strings[index]);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }   // showView

    private void bindWidget() {

        questionTextView = (TextView) findViewById(R.id.textView4);
        choiceRadioButtons = new RadioButton[4];
        choiceRadioButtons[0] = (RadioButton) findViewById(R.id.radioButton4);
        choiceRadioButtons[1] = (RadioButton) findViewById(R.id.radioButton3);
        choiceRadioButtons[2] = (RadioButton) findViewById(R.id.radioButton2);
        choiceRadioButtons[3] = (RadioButton) findViewById(R.id.radioButton);
        imageView = (ImageView) findViewById(R.id.imageView8);
        radioGroup = (RadioGroup) findViewById(R.id.ragChoice);
        showTimeTextView = (TextView) findViewById(R.id.textView2);
        showScoreTextView = (TextView) findViewById(R.id.textView3);
        termoImageViews[0] = (ImageView) findViewById(R.id.imageView4);
        termoImageViews[1] = (ImageView) findViewById(R.id.imageView6);
        termoImageViews[2] = (ImageView) findViewById(R.id.imageView7);
        termoImageViews[3] = (ImageView) findViewById(R.id.imageView9);
        termoImageViews[4] = (ImageView) findViewById(R.id.imageView10);
        termoImageViews[5] = (ImageView) findViewById(R.id.imageView11);
        termoImageViews[6] = (ImageView) findViewById(R.id.imageView12);
        termoImageViews[7] = (ImageView) findViewById(R.id.imageView13);


    }

} // Main Class
