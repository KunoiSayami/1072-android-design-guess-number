package com.example.guess;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    EditText et_input_num;
    Button btn_confirm, btn_new_game;
    TextView txt_result;
    int guest_times = 0;

    static int randInt = 0;

    public static String mainName = "GuessNumberMain";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.findViews();
        this.new_game();
        //MainActivity.generate_random_int();
        this.init_btns();
        Log.d(MainActivity.mainName, "loaded");
    }

    public static void generate_random_int() {
        MainActivity.randInt = new Random().nextInt(100) + 1;
    }

    private void findViews() {
        this.et_input_num = findViewById(R.id.inputNum);
        this.btn_new_game = findViewById(R.id.btnNewGame);
        this.btn_confirm = findViewById(R.id.btnConfirm);
        this.txt_result = findViewById(R.id.textResult);
    }

    private void init_btns() {
        this.btn_new_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new_game();
                //Log.d(MainActivity.mainName, "clicked");
            }
        });
        this.btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compare();
                //Log.d(MainActivity.mainName, "Clicked");
            }
        });
    }

    private void compare() {
        try {
            int a = Integer.parseInt(this.et_input_num.getText().toString());
            String txtResult = String.format(
                    "%s %s %s",
                    getString(R.string.textResultPrefix),
                    a == MainActivity.randInt ? getString(R.string.textResultEqual) : (a < MainActivity.randInt ? getString(R.string.textResultSmaller): getString(R.string.textResultBigger)),
                    getString(R.string.textResultSuffix)
            );
            this.txt_result.setText(txtResult);
            if (a == MainActivity.randInt) {
                this.btn_confirm.setEnabled(false);
                this.btn_new_game.setEnabled(true);
            }
        } catch (Exception e){
            throw_err();
            Log.e(MainActivity.mainName, "Throw");
        }
    }

    private void throw_err() {
        /* copied from https://stackoverflow.com/questions/6264694/how-to-add-message-box-with-ok-button */
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        dlgAlert.setMessage("Please input a valid number");
        dlgAlert.setTitle("Exception occurred!");
        //dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //dismiss the dialog
                    }
                });
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    }

    private void new_game() {
        this.guest_times = 0;
        MainActivity.generate_random_int();
        Log.d(MainActivity.mainName, Integer.toString(MainActivity.randInt));
        this.txt_result.setText("");
        this.btn_new_game.setEnabled(false);
        this.btn_confirm.setEnabled(true);
    }
}
