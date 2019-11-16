package com.example.heartdiseaseprediction;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

public class MainActivity extends AppCompatActivity {
    EditText blood_pressure_field, pulse_rate_field, age_field;
    Attribute attrib_age, attrib_sex, attrib_blood_pressure, attrib_pulse_rate, attrib_target;
    Instances training_set;
    Spinner sex_field;
    Classifier cls ;
    double blood_pressure;
    int pulse_rate;
    int age;
    int sex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        blood_pressure_field = (EditText) findViewById(R.id.blood_pressure);
        pulse_rate_field = (EditText) findViewById(R.id.pulse_rate);
        age_field = (EditText) findViewById(R.id.age);
        sex_field = (Spinner) findViewById(R.id.sex);

        attrib_age = new Attribute("age");
        attrib_sex = new Attribute("sex");
        attrib_blood_pressure = new Attribute("trestbps");
        attrib_pulse_rate = new Attribute("thalach");
        attrib_target = new Attribute("target");

        ArrayList<Attribute> atts = new ArrayList<Attribute>();
        atts.add(attrib_age);
        atts.add(attrib_sex);
        atts.add(attrib_blood_pressure);
        atts.add(attrib_pulse_rate);
        atts.add(attrib_target);

        training_set= new Instances("rel",atts,1);
        training_set.setClassIndex(4);
        /*
        try {

        }
        catch (Exception e){}*/
        createNotificationChannel();
    }

    public void predict(View v) {
        blood_pressure = Double.parseDouble(blood_pressure_field.getText().toString());
        pulse_rate = Integer.parseInt(pulse_rate_field.getText().toString());
        age = Integer.parseInt(age_field.getText().toString());
        sex = sex_field.getSelectedItemPosition();

        try
        {

            cls = (Classifier) weka.core.SerializationHelper.read(getAssets().open("heart.model"));
            Instance instance = new DenseInstance(4);
            instance.setValue(attrib_age,age);
            instance.setValue(attrib_sex,sex);
            instance.setValue(attrib_blood_pressure,blood_pressure);
            instance.setValue(attrib_pulse_rate,pulse_rate);

            training_set.add(instance);
            double target=1.0;
            target = cls.classifyInstance(training_set.instance(0));
            String s = "hello " + blood_pressure + " " + pulse_rate + " " + age + " " + sex+" target "+target;
            Toast t = Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG);
            t.show();
            if(target==1.0) {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channel_1")
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle("Heart Disease Prediction")
                        .setContentText("High Probability of heart attack")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

// notificationId is a unique int for each notification that you must define
                notificationManager.notify(1, builder.build());}

        }
        catch (Exception e)
        {
            Toast t = Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG);
            t.show();
        }
    }
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "channel1";
            String description = "heart";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("channel_1", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}