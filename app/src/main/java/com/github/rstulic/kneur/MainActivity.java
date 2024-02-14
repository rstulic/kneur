package com.github.rstulic.kneur;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.github.rstulic.kneur.R;

public class MainActivity extends Activity {

    EditText editTextInput;
    TextView textViewHrkToEur;
    TextView textViewEurToHrk;

    int[] buttonIds = new int[] { R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6,
            R.id.button7, R.id.button8, R.id.button9 };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextInput = (EditText) findViewById(R.id.editTextInput);
        textViewHrkToEur = (TextView) findViewById(R.id.TextViewHrkToEur);
        textViewEurToHrk = (TextView) findViewById(R.id.textViewEurToHrk);

        editTextInput.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                convert();

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {
            }
        });

        for (int i = 0; i < buttonIds.length; i++) {
            Button btn = (Button) findViewById(buttonIds[i]);
            final int n = i;
            btn.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    numberPressed(n);
                }
            });
        }

        Button btnErase = (Button) findViewById(R.id.buttonErase);
        btnErase.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                erasePressed();
            }
        });

        Button btnDecimal = (Button) findViewById(R.id.buttonDecimal);
        btnDecimal.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                decimalPressed();
            }
        });

        Button btnClear = (Button) findViewById(R.id.buttonClear);
        btnClear.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                clearPressed();
            }
        });
    }

    private void numberPressed(int number) {
        editTextInput.append(Integer.toString(number));
    }

    private void erasePressed() {
        int posStart = editTextInput.getSelectionStart();
        int posEnd = editTextInput.getSelectionEnd();
        System.out.println("posStart = " + posStart + ", posEnd = " + posEnd);
        if (posEnd > posStart) {
            editTextInput.getText().delete(posStart, posEnd);
        } else if (posStart > 0) {
            editTextInput.getText().delete(posStart - 1, posStart);
        }
    }

    private void clearPressed() {
        editTextInput.setText("");
    }

    private void decimalPressed() {
        editTextInput.append(".");
    }

    final static double CONVERSION = 7.5345;

    private void convert() {
        try {
            double input = Double.parseDouble(editTextInput.getText().toString());

            double hrk = input * CONVERSION;
            double eur = input / CONVERSION;

            DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
            symbols.setDecimalSeparator('.');
            symbols.setGroupingSeparator(' ');

            DecimalFormat formatterHrk = new DecimalFormat("###,##0.00  kn", symbols);
            DecimalFormat formatterEur = new DecimalFormat("###,##0.00 eur", symbols);

            textViewEurToHrk.setText(formatterHrk.format(hrk));
            textViewHrkToEur.setText(formatterEur.format(eur));
        } catch (Exception e) {
            textViewEurToHrk.setText("");
            textViewHrkToEur.setText("");
        }

    }

}
