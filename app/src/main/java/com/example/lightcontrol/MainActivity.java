package com.example.lightcontrol;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.lightcontrol.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    private Button commandButton;
    private Button manualButton;
    private RadioGroup radioGroup;

    private Mode mode;
    private int redValue;
    private int greenValue;
    private int blueValue;
    private int brightnessValue;
    private int sensitivityValue;

    private EditText redEdit;
    private EditText greenEdit;
    private EditText blueEdit;

    private SeekBar seekBarBrightness;
    private SeekBar seekBarSensitivity;

    private Communication communication;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mode=Mode.NOT_DEFINED;

        commandButton=findViewById(R.id.send_command);
        manualButton=findViewById(R.id.show_manual);
        radioGroup=findViewById(R.id.radioGroup);

        redEdit=findViewById(R.id.red_edit_text);
        greenEdit=findViewById(R.id.green_edit_text);
        blueEdit=findViewById(R.id.blue_edit_text);

        seekBarBrightness=findViewById(R.id.seekbar_brightness);
        seekBarSensitivity=findViewById(R.id.seekbar_photoresistor);

        communication=new Communication(this);

        manualButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ManualActivity.class);
                startActivityForResult(intent,1);
            }
        });


        commandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getInfo();
                sendCommand();
                System.out.println(mode.toString());
                System.out.println("red: "+redValue+"green: "+greenValue+"blue: "+blueValue);
                System.out.println(brightnessValue);
                System.out.println(sensitivityValue);

            }
        });


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.radio_normal) mode=Mode.NORMAL;
                else if(checkedId==R.id.radio_photo) mode=Mode.PHOTO;
                else if(checkedId==R.id.radio_motion) mode=Mode.MOTION;
                else if (checkedId==R.id.radio_tv) mode=Mode.TV;
            }
        });
    }

    void getInfo()
    {
        String red=redEdit.getText().toString();
        String green=greenEdit.getText().toString();
        String blue=blueEdit.getText().toString();

        if(red.isEmpty())
        {
            red="0";
            redEdit.setText("0");
        }
        if(green.isEmpty())
        {
            green="0";
            greenEdit.setText("0");
        }
        if(blue.isEmpty())
        {
            blue="0";
            blueEdit.setText("0");
        }

        redValue=Integer.parseInt(red);
        greenValue=Integer.parseInt(green);
        blueValue=Integer.parseInt(blue);

        if(redValue<0)
        {
            redValue=0;
            redEdit.setText("0");
        }
        else if(redValue>255)
        {
            redValue=255;
            redEdit.setText("255");
        }
        if(greenValue<0)
        {
            greenValue=0;
            greenEdit.setText("0");
        }
        else if(greenValue>255)
        {
            greenValue=255;
            greenEdit.setText("255");
        }
        if(blueValue<0)
        {
            blueValue=0;
            blueEdit.setText("0");
        }
        else if(blueValue>255)
        {
            blueValue=255;
            blueEdit.setText("255");
        }

        brightnessValue=seekBarBrightness.getProgress();
        sensitivityValue=seekBarSensitivity.getProgress();
    }

    void sendCommand()
    {
        communication.sendCommand(mode,redValue,greenValue,blueValue,brightnessValue, sensitivityValue);
    }


}