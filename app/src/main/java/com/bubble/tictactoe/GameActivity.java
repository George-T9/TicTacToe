package com.bubble.tictactoe;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {

    ImageView iv_1, iv_2, iv_3, iv_4, iv_5, iv_6, iv_7, iv_8, iv_9;
    boolean activePlayer,winnerFound;                     //true player 1 false player 2
    String playerName1, playerName2,str=null;
    TextView playerTurn, winnerlog,TV_Player1,TV_Player2;
    int[] gameState = {0, 0, 0, 0, 0, 0, 0, 0, 0};  //0=null ,1=player 1, 2 = player 2
    int[][] winnerState = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};
    int[] winnerLog = {0, 0};
    int boxFilled = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);


        iv_1 = findViewById(R.id.iv_1);
        iv_2 = findViewById(R.id.iv_2);
        iv_3 = findViewById(R.id.iv_3);
        iv_4 = findViewById(R.id.iv_4);
        iv_5 = findViewById(R.id.iv_5);
        iv_6 = findViewById(R.id.iv_6);
        iv_7 = findViewById(R.id.iv_7);
        iv_8 = findViewById(R.id.iv_8);
        iv_9 = findViewById(R.id.iv_9);
        playerTurn = findViewById(R.id.TV_showTurn);
        winnerlog = findViewById(R.id.winnerLog);
        TV_Player1=findViewById(R.id.TV_player1);
        TV_Player2=findViewById(R.id.TV_player2);

        Intent intent =getIntent();
        playerName1 =intent.getStringExtra("playerName1");
        playerName2 =intent.getStringExtra("playerName2");

        TV_Player1.setText(playerName1);
        TV_Player2.setText(playerName2);
        gameReset();
    }

    private void gameReset() {

        iv_1.setImageResource(0);
        iv_2.setImageResource(0);
        iv_3.setImageResource(0);
        iv_4.setImageResource(0);
        iv_5.setImageResource(0);
        iv_6.setImageResource(0);
        iv_7.setImageResource(0);
        iv_8.setImageResource(0);
        iv_9.setImageResource(0);

        for (int x = 0; x < 9; x++) {
            gameState[x] = 0;
        }
        str=null;
        boxFilled = 0;
        winnerFound=false;
        showTurn(activePlayer);
    }

    private void showTurn(boolean player) {
        if (player) {
            playerTurn.setText(String.format("%s Turn 0", playerName1));
        } else {
            playerTurn.setText(String.format("%s Turn X", playerName2));
        }
    }

    public void playerTap(View view) {

        ImageView img = (ImageView) view;
        int tappedImg = Integer.parseInt(img.getTag().toString());

        if (gameState[tappedImg] == 0) {
            if (activePlayer) {
                gameState[tappedImg] = 1;
                img.setImageResource(R.drawable.o);
                activePlayer = false;
            } else {
                gameState[tappedImg] = 2;
                img.setImageResource(R.drawable.x);
                activePlayer = true;
            }
            showTurn(activePlayer);
            boxFilled += 1;

            for (int[] winPosition : winnerState) {
                if (gameState[winPosition[0]] == gameState[winPosition[1]] && gameState[winPosition[2]] == gameState[winPosition[1]] && gameState[winPosition[0]] != 0) {
                    winnerFound=true;

                    if(gameState[winPosition[0]]==1) {
                        str = String.format("%s Won", playerName1);
                        winnerLog[0] += 1;
                    }else {
                        str = String.format("%s Won", playerName2);
                        winnerLog[1] += 1;
                    }
                    showResult();
                    winnerlog.setText(String.format("%d : %d", winnerLog[0], winnerLog[1]));
                }
            }

            if(!winnerFound && boxFilled==9){
                str="Draw";
                showResult();
            }
        }
    }

    private void showResult() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater=this.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.winner_dialog,null));
        final AlertDialog dialog= builder.create();
        dialog.show();

        TextView winnerMsg = dialog.findViewById(R.id.TV_winner);
        winnerMsg.setText(str);

        Button btnHome =dialog.findViewById(R.id.BT_MainMenu);
        Button btnNextGame=dialog.findViewById(R.id.BT_NextGame);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(GameActivity.this, StartActivity.class);
                startActivity(intent);
            }
        });

        btnNextGame.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                gameReset();
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onBackPressed() {
        final AlertDialog dialog;
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setMessage("Return to Main Menu");
        builder.setPositiveButton("YES",new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent=new Intent(GameActivity.this,StartActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialog = builder.create();
        dialog.show();
    }
}