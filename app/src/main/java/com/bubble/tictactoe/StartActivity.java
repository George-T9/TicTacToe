package com.bubble.tictactoe;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    public void startGame(View view) {

        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_players_name,null));
        AlertDialog dialog= builder.create();
        dialog.show();

        final TextInputEditText editText_player1 = dialog.findViewById(R.id.editText_player1);
        final TextInputEditText editText_player2 = dialog.findViewById(R.id.editText_player2);
        Button btnStart = dialog.findViewById(R.id.btn_startGame);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String player_name1 = editText_player1.getText().toString();
                String player_name2 = editText_player2.getText().toString();
                if(player_name1.equals("")){
                    player_name1="Player 1";
                }
                if(player_name2.equals("")){
                    player_name2="Player 2";
                }
                Intent intent=new Intent(StartActivity.this,GameActivity.class);
                intent.putExtra("playerName1",player_name1);
                intent.putExtra("playerName2",player_name2);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        final AlertDialog dialog;
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setMessage("Do u want to quit");
        builder.setPositiveButton("Yes",new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        dialog = builder.create();
        dialog.show();

    }

}