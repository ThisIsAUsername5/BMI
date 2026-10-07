package com.example.bmi;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText editTextHeight;
    private EditText editTextWeight;
    private RadioGroup radioGroupGender;
    private CheckBox checkBoxSave;
    private Button buttonCalculate;
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextHeight = findViewById(R.id.editTextHeight);
        editTextWeight = findViewById(R.id.editTextWeight);
        radioGroupGender = findViewById(R.id.radioGroupGender);
        checkBoxSave = findViewById(R.id.checkBoxSave);
        buttonCalculate = findViewById(R.id.buttonCalculate);
        textViewResult = findViewById(R.id.textViewResult);

        buttonCalculate.setOnClickListener(view -> calculateBMI());
    }

    private void calculateBMI() {

        String heightText = editTextHeight.getText().toString().trim();

        if (heightText.isEmpty()) {
            editTextHeight.setError("Podaj wzrost");
            editTextHeight.requestFocus();
            return;
        }

        String weightText = editTextWeight.getText().toString().trim();

        if (weightText.isEmpty()) {
            editTextWeight.setError("Podaj wagę");
            editTextWeight.requestFocus();
            return;
        }

        // Sprawdzenie płci
        int selectedGenderId = radioGroupGender.getCheckedRadioButtonId();

        if (selectedGenderId == -1) {
            Toast.makeText(
                    this,
                    "Wybierz płeć",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        double height;
        double weight;

        try {
            // Zamiana przecinka na kropkę
            height = Double.parseDouble(heightText.replace(",", "."));
            weight = Double.parseDouble(weightText.replace(",", "."));
        } catch (NumberFormatException e) {
            Toast.makeText(
                    this,
                    "Podaj poprawne wartości liczbowe",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        // Dodatkowa walidacja
        if (height <= 0) {
            editTextHeight.setError("Wzrost musi być większy od 0");
            editTextHeight.requestFocus();
            return;
        }

        if (weight <= 0) {
            editTextWeight.setError("Waga musi być większa od 0");
            editTextWeight.requestFocus();
            return;
        }

        // Zamiana wzrostu z cm na metry
        double heightInMeters = height / 100.0;

        // Obliczenie BMI
        double bmi = weight / (heightInMeters * heightInMeters);

        // Interpretacja wyniku
        String interpretation;

        if (bmi < 18.5) {
            interpretation = "Niedowaga";
        } else if (bmi < 25) {
            interpretation = "Waga prawidłowa";
        } else if (bmi < 30) {
            interpretation = "Nadwaga";
        } else {
            interpretation = "Otyłość";
        }

        // Odczyt wybranej płci
        RadioButton selectedRadioButton =
                findViewById(selectedGenderId);

        String gender = selectedRadioButton.getText().toString();

        // Wyświetlenie wyniku
        String result = String.format(
                Locale.getDefault(),
                "BMI: %.2f\n%s\nPłeć: %s",
                bmi,
                interpretation,
                gender
        );

        textViewResult.setText(result);

        // Obsługa checkboxa "Zapisz moje dane"
        if (checkBoxSave.isChecked()) {
            Toast.makeText(
                    this,
                    "Dane zostały zaznaczone do zapisania",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}