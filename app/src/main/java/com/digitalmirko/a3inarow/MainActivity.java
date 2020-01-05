package com.digitalmirko.a3inarow;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    // Key 0 = yellow, 1 = red, 2 = empty

    // which are yellow, red or empty
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};

    // array of winning position
    int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8},  // horizontal
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // vertical
            {0, 4, 8}, {2, 4, 6}}; // diagonal

    int activePlayer = 0;

    // checking to stop if someone has won
    boolean gameActive = true;

    public void dropIn(View view){

        ImageView counter = (ImageView) view;

//        Log.i("Tag",counter.getTag().toString());

        // update gamestate for counter colors
        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        // if game counter is equal to 2, the space is empty and
        // the game is activeso we can continue
        if(gameState[tappedCounter] == 2 && gameActive == true) {

            gameState[tappedCounter] = activePlayer;

            counter.setTranslationY(-1500);

            if (activePlayer == 0) {

                //drops down yellow color disc
                counter.setImageResource(R.drawable.yellow);

                activePlayer = 1; // red, next player

            } else {

                //drops down red color disc
                counter.setImageResource(R.drawable.red);

                activePlayer = 0; // yellow, next player

            }

            counter.animate().translationYBy(1500).rotation(3600).setDuration(500);

            // looking for all the same in the winning positions
            for (int[] winningPosition : winningPositions) {

                if (gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                        gameState[winningPosition[1]] == gameState[winningPosition[2]] && gameState[winningPosition[0]] != 2) {

                    // someone has won
//                Toast.makeText(this,"Someone has won",Toast.LENGTH_LONG).show();

                    gameActive = false;

                    String winner = "";

                    if (activePlayer == 1) {

                        winner = "Yellow";

                    } else {

                        winner = "Red";
                    }

//                    Toast.makeText(this, winner + " has won!", Toast.LENGTH_LONG).show();

                    Button playAgainButton = (Button) findViewById(R.id.playAgainButton);
                    TextView winnerTextView = (TextView) findViewById(R.id.winnerTextView);

                    winnerTextView.setText(winner + " has won!");

                    // making the button and textview visible, initially invisible
                    playAgainButton.setVisibility(View.VISIBLE);
                    winnerTextView.setVisibility(View.VISIBLE);

                }
            }

        }

    }

    // Allows game to be played again after its over
    public void playAgain(View view) {

        Button playAgainButton = (Button) findViewById(R.id.playAgainButton);

        TextView winnerTextView = (TextView) findViewById(R.id.winnerTextView);

        // makes textview and button disappear
        playAgainButton.setVisibility(View.INVISIBLE);

        winnerTextView.setVisibility(View.INVISIBLE);

        androidx.gridlayout.widget.GridLayout gridLayout = (androidx.gridlayout.widget.GridLayout) findViewById(R.id.gridLayout);

        for(int i = 0; i < gridLayout.getChildCount(); i++){

            ImageView counter = (ImageView) gridLayout.getChildAt(i);

            counter.setImageDrawable(null);

        }

        for(int i =0; i<gameState.length; i++) {

            gameState[i] = 2;
        }

        activePlayer = 0;

        // checking to stop if someone has won
        gameActive = true;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
