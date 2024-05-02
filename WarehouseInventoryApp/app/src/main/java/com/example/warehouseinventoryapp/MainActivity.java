package com.example.warehouseinventoryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "LIFE_CYCLE_TRACING";

    private EditText nameEditText;
    private EditText quantityEditText;
    private EditText costEditText;
    private EditText descriptionEditText;
    private ToggleButton frozenButton;

    private String name;
    private String quantity;
    private String cost;
    private String description;
    private boolean frozen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
        setContentView(R.layout.activity_main);

        //Get the reference
        nameEditText = findViewById(R.id.editName);
        quantityEditText = findViewById(R.id.editQuantity);
        costEditText = findViewById(R.id.editCost);
        descriptionEditText = findViewById(R.id.editDescription);
        frozenButton = findViewById(R.id.frozenBt);
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
        outState.putString("keyName", name);
        outState.putString("keyQuantity", quantity);
        outState.putString("keyCost", cost);
        outState.putString("keyDescription", description);
        outState.putBoolean("keyFrozen", frozen);
        Log.i(TAG, "onSaveInstanceState");

    }

    //only gets executed if inState != null so no need to check for null Bundle
    protected void onRestoreInstanceState(Bundle inState) {
        super.onRestoreInstanceState(inState);
        name = inState.getString("keyName");
        quantity = inState.getString("keyQuantity");
        cost = inState.getString("keyCost");
        description = inState.getString("keyDescription");
        frozen = inState.getBoolean("keyFrozen");
        Log.i(TAG, "onRestoreInstanceState");
    }

    public void saveSharedPreferences(){
        //Get the data from edit text box
        name = nameEditText.getText().toString();
        quantity = quantityEditText.getText().toString();
        cost = costEditText.getText().toString();
        description = descriptionEditText.getText().toString();
        frozen = frozenButton.isChecked();

        SharedPreferences sp = getSharedPreferences("save", 0);
        SharedPreferences.Editor editSP = sp.edit();

        //Save the data
        editSP.putString("nameKey", name);
        editSP.putString("quantityKey", quantity);
        editSP.putString("costKey", cost);
        editSP.putString("descriptionKey", description);
        editSP.putBoolean("frozenKey", frozen);
        editSP.apply();
    }

    public void restoreSharedPreferences(){
        SharedPreferences sp = getSharedPreferences("save", 0);
//保存输入框的内容
        //Get the data saved before
        name = sp.getString("nameKey", "");
        quantity = sp.getString("quantityKey", "0");
        cost = sp.getString("costKey", "0.0");
        description = sp.getString("descriptionKey", "");
        frozen = sp.getBoolean("frozenKey", false);

        //Restore the data
        nameEditText.setText(name);

        if (quantity != ""){
            quantityEditText.setText(quantity);
        }else{
            quantityEditText.setText("0");
        }

        if (cost != ""){
            costEditText.setText(cost);
        }else{
            costEditText.setText("0.0");
        }

        descriptionEditText.setText(description);
        frozenButton.setChecked(frozen);
    }

    public void onClickNew(View view){
        name = nameEditText.getText().toString();
        Toast toastNew = Toast.makeText(this,"New item (" + name + ") has been added.", Toast.LENGTH_LONG);
        toastNew.show();
    }

    public void onClickClear(View view){
        nameEditText.setText("");
        quantityEditText.setText("");
        costEditText.setText("");
        descriptionEditText.setText("");
        frozenButton.setChecked(false);
    }
}


