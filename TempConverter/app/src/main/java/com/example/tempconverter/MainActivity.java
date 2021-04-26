package com.example.tempconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private TextView txtError;
    private RadioButton radioButtonCelToFah;
    private RadioButton radioButtonFahToCel;
    private EditText editTextInput;
    private EditText editTextResult;
    private ListView histories;
    private RadioGroup radioGroup;
    private ArrayAdapter<String> toHistoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {//this is the main method
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set up all attributes
        txtError = findViewById(R.id.txtError);
        radioGroup = findViewById(R.id.radioGroup);
        View view = findViewById(R.id.view);
        radioButtonCelToFah = radioGroup.findViewById(R.id.radioCelToFah);
        radioButtonFahToCel = radioGroup.findViewById(R.id.radioFahToCel);
        editTextInput = findViewById(R.id.txtInput);
        editTextResult = findViewById(R.id.txtResult);
        histories = findViewById(R.id.historiesView);
        toHistoryAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1);

        txtError.setVisibility(View.GONE);
        editTextResult.setFocusable(false);
        editTextResult.setClickable(false);
        view.setFocusable(false);
        view.setClickable(false);
    }

    //OnClick events
    public void globalOnClicked(View view) {
        txtError.setVisibility(View.GONE);
        editTextResult.setText("", TextView.BufferType.EDITABLE);
        //radioGroup.clearCheck();
    }

    public void radioFahToCel_onClicked(View view) {
        radioButtonCelToFah.setSelected(false);
    }

    public void radioCelToFah_onClicked(View view) {
        radioButtonFahToCel.setSelected(false);
    }

    @SuppressLint("SetTextI18n")
    public void btnConvert_onClicked(View view) {
        String txtInput = editTextInput.getText().toString();

        if (txtInput.matches("")) {
            txtError.setText("Empty Input!!");
            txtError.setVisibility(View.VISIBLE);
        }
        else if (!radioButtonCelToFah.isChecked() && !radioButtonFahToCel.isChecked()) {
            txtError.setText("Please choose a method");
            txtError.setVisibility(View.VISIBLE);
        }
        else if (!isNumeric(txtInput)) {
            txtError.setText("Invalid input!!");
            txtError.setVisibility(View.VISIBLE);
        }
        else {
            double input = Double.parseDouble(txtInput);

            if (radioButtonCelToFah.isChecked()) {
                double result = (input * 9.0 / 5.0) + 32.0;
                String txtResultValue = String.valueOf(result);
                String toHistory = input + "°C=" + result + "°F";

                editTextResult.setText(txtResultValue, TextView.BufferType.EDITABLE);
                toHistoryAdapter.add(toHistory);
                histories.setAdapter(toHistoryAdapter);
                txtError.setVisibility(View.GONE);
            }
            else if (radioButtonFahToCel.isChecked()) {
                double result = (input - 32.0) * 5.0 / 9.0;
                String txtResultValue = String.valueOf(result);
                String toHistory = input + "°F=" + result + "°C";

                editTextResult.setText(txtResultValue, TextView.BufferType.EDITABLE);
                toHistoryAdapter.add(toHistory);
                histories.setAdapter(toHistoryAdapter);
                txtError.setVisibility(View.GONE);
            }
        }
    }

    public void btnReset_onClicked(View view) {
        editTextInput.setText("", TextView.BufferType.EDITABLE);
        editTextResult.setText("", TextView.BufferType.EDITABLE);
        radioGroup.clearCheck();
        toHistoryAdapter.clear();
        toHistoryAdapter.notifyDataSetChanged();
        histories.setAdapter(toHistoryAdapter);
    }

    private static boolean isNumeric(String input) {
        Pattern pattern = Pattern.compile("[-+]?\\d*\\.?\\d+");
        return input.matches(String.valueOf(pattern));
    }
}