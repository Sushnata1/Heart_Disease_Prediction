package com.example.heartdiseaseprediction;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText blood_pressure_field,pulse_rate_field,age_field;
    Spinner sex_field;
    double blood_pressure;
    int pulse_rate;
    int age;
    int sex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        blood_pressure_field = (EditText)findViewById(R.id.blood_pressure);
        pulse_rate_field = (EditText)findViewById(R.id.pulse_rate);
        age_field = (EditText)findViewById(R.id.age);
        sex_field = (Spinner)findViewById(R.id.sex);
    }
    public void predict(View v)
    {
        blood_pressure = Double.parseDouble(blood_pressure_field.getText().toString());
        pulse_rate = Integer.parseInt(pulse_rate_field.getText().toString());
        age = Integer.parseInt(age_field.getText().toString());
        sex = sex_field.getSelectedItemPosition();
        String s = "hello "+blood_pressure+" "+pulse_rate+" "+age+" "+sex;
        Toast t = Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG);
        t.show();
    }
}
