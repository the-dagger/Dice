package io.github.the_dagger.diceroll;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity {
    private Random random = new Random();
    private int userCurrentScore = 0;
    private int userTotalScore = 0;
    private int comTotalScore = 0;
    private int comCurrentScore = 0;
    private TextView userTotalScoreView;
    private TextView comTotalScoreView;
    private TextView currentRoll;
    private Button roll;
    private Button hold;
    int userTempScore = 0;  //TO keep track of score even if Hold has not been pressed
    int comTempScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        userTotalScoreView = (TextView) findViewById(R.id.score_human);
        comTotalScoreView = (TextView) findViewById(R.id.score_computer);
        currentRoll = (TextView) findViewById(R.id.current_roll);
        roll = (Button) findViewById(R.id.roll);
        hold = (Button) findViewById(R.id.hold);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return id == R.id.action_settings || super.onOptionsItemSelected(item);

    }

    public void rollDice(View view) {
        int userSemiTempScore = random.nextInt(6 - 1) + 1; //TO keep track of score in one particular roll
        userTempScore += userSemiTempScore;
        currentRoll.setText("You rolled " + userSemiTempScore);
        Log.e("User Roll", String.valueOf(userSemiTempScore));
        if (userSemiTempScore != 1) {
            userCurrentScore += userTempScore;
            Log.e("User Current Score", String.valueOf(userCurrentScore));
        } else {
            userCurrentScore = 0;
            Log.e("User Current Score", String.valueOf(userCurrentScore));
            roll.setVisibility(GONE);
            hold.setVisibility(GONE);
            comTurn();
        }
    }

    public void holdDice(View view) {
        userTotalScore += userCurrentScore;
        userTotalScoreView.setText(userTotalScore+"");
        userCurrentScore = 0;
        userTempScore = 0;
        winner();
        comTurn();
    }

    public void resetScore(View view) {
        userCurrentScore = 0;
        userTotalScore = 0;
        comCurrentScore = 0;
        comTotalScore = 0;
        userTotalScoreView.setText(userTotalScore+"");
        comTotalScoreView.setText(comTotalScore+"");
    }

    private void comTurn() {
        while (comCurrentScore < 20) {
            comTempScore = random.nextInt(6 - 1) + 1;
            Log.e("Com temp Score", String.valueOf(comTempScore));
            currentRoll.setText("Computer rolled " + comTempScore);
            if (comTempScore != 1) {
                comCurrentScore += comTempScore;
                Log.e("Com Current Score", String.valueOf(comCurrentScore));
            }
            else{
                comCurrentScore = 0;
                break;
            }
        }
        comTotalScore+=comCurrentScore;
        comTotalScoreView.setText(comTotalScore+"");
        Log.e("Com total Score", String.valueOf(comTotalScore));
        winner();
        roll.setVisibility(View.VISIBLE);
        hold.setVisibility(View.VISIBLE);
    }

    private void winner (){
        if(userTotalScore>100){
            currentRoll.setText("You Won");
            resetScore(this.findViewById(android.R.id.content));
        }
        else if(comTotalScore>100){
            currentRoll.setText("Computer Won");
            resetScore(this.findViewById(android.R.id.content));
        }
    }
}
