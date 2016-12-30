package rtc.nuttamon.kantana.monopolygame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ShowScore extends AppCompatActivity implements View.OnClickListener {

    private TextView textView;
    private ImageView palyImageView, exitImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_score);

        //Bind Widget
        textView = (TextView) findViewById(R.id.textView);
        palyImageView = (ImageView) findViewById(R.id.imageView14);
        exitImageView = (ImageView) findViewById(R.id.imageView15);

        textView.setText(Integer.toString(getIntent().getIntExtra("Score", 0)));

        palyImageView.setOnClickListener(this);
        exitImageView.setOnClickListener(this);

    } // Main Method

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.imageView14) {
            startActivities(new Intent(ShowScore.this, PlayGame.class));
        }


        finish();

    }
} // Main Class
