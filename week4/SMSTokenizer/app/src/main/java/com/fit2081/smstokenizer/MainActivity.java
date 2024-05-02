package com.fit2081.smstokenizer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "LIFE_CYCLE_TRACING";

    EditText editText;
    EditText editText1;
    EditText editText2;
    EditText editText3;
    ToggleButton toggleButton;

    private String itemname;
    private String cost;
    private String quantity;
    private String description;
    private boolean togglebutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
        setContentView(R.layout.activity_main);


        editText = findViewById(R.id.editText3);
        editText1 = findViewById(R.id.editText4);
        editText2 = findViewById(R.id.editText5);
        editText3 = findViewById(R.id.editText6);
        toggleButton = findViewById(R.id.toggleButton);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS}, 0);
        MyBroadCastReceiver myBroadCastReceiver = new MyBroadCastReceiver();
        registerReceiver(myBroadCastReceiver, new IntentFilter(SMSReceiver.SMS_FILTER));
    }
    class MyBroadCastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            /*
             * Retrieve the message from the intent
             * */
            String msg = intent.getStringExtra(SMSReceiver.SMS_MSG_KEY);
            /*
             * String Tokenizer is used to parse the incoming message
             * The protocol is to have the account holder name and account number separate by a semicolon
             * */

            StringTokenizer sT = new StringTokenizer(msg, ";");

            String itemSMS = sT.nextToken();
            String quantitySMS = sT.nextToken();
            String costSMS = sT.nextToken();
            String descriptionSMS = sT.nextToken();
            String frozenSMS = sT.nextToken();
            editText.setText(itemSMS);
            editText1.setText(quantitySMS);
            editText2.setText(costSMS);
            editText3.setText(descriptionSMS);

            if (frozenSMS.equals("true")) {
                toggleButton.setText("Yes");
            } else if (frozenSMS.equals("false")) {
                toggleButton.setText("No");
            }
        }
    }

    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");
    }

    protected void onResume() {
        restoreSharedPreferences();
        super.onResume();
        Log.i(TAG, "onResume");
    }

    protected void onPause() {
        super.onPause();
        saveSharedPreferences();
        Log.i(TAG, "onPause");

    }

    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");
    }

    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart");
    }

    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("keyName", itemname);
        outState.putString("keyQuantity", quantity);
        outState.putString("keyCost", cost);
        outState.putString("keyDescription", description);
        outState.putBoolean("keyTogglebutton", togglebutton);
        Log.i(TAG, "onSaveInstanceState");

    }

    //only gets executed if inState != null so no need to check for null Bundle
    protected void onRestoreInstanceState(Bundle inState) {
        super.onRestoreInstanceState(inState);
        itemname = inState.getString("keyName");
        quantity = inState.getString("keyQuantity");
        cost = inState.getString("keyCost");
        description = inState.getString("keyDescription");
        togglebutton = inState.getBoolean("keyTogglebutton");
        Log.i(TAG, "onRestoreInstanceState");
    }

    public void saveSharedPreferences(){

        itemname = editText.getText().toString();
        quantity = editText1.getText().toString();
        cost = editText2.getText().toString();
        description = editText3.getText().toString();
        togglebutton = toggleButton.isChecked();

        SharedPreferences sp = getSharedPreferences("save", 0);
        SharedPreferences.Editor editSP = sp.edit();


        editSP.putString("nameKey", itemname);
        editSP.putString("quantityKey", quantity);
        editSP.putString("costKey", cost);
        editSP.putString("descriptionKey", description);
        editSP.putBoolean("togglebuttonKey", togglebutton);
        editSP.apply();
    }

    public void restoreSharedPreferences(){
        SharedPreferences sp = getSharedPreferences("save", 0);


        itemname = sp.getString("nameKey", "");
        quantity = sp.getString("quantityKey", "0");
        cost = sp.getString("costKey", "0.0");
        description = sp.getString("descriptionKey", "");
        togglebutton = sp.getBoolean("togglebuttonKey", false);


        editText.setText(itemname);

        if (quantity != ""){
            editText1.setText(quantity);
        }else{
            editText1.setText("0");
        }

        if (cost != ""){
            editText2.setText(cost);
        }else{
            editText2.setText("0.0");
        }

        editText3.setText(description);
        toggleButton.setChecked(togglebutton);
    }

    public void onClickaddnewitem(View view){
        itemname = editText.getText().toString();
        Toast toastNew = Toast.makeText(this,"New item"  + editText + " has been added.", Toast.LENGTH_LONG);
        toastNew.show();
    }

    public void onClickClearbutton(View view){
        editText.setText("");
        editText1.setText("");
        editText2.setText("");
        editText3.setText("");
        toggleButton.setChecked(false);
    }
}


