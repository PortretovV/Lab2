package com.student.portretov.lab2;

import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText mainEditText;
    Button effectButton;

    Long first, second, valueM;
    boolean showResultBeforeEnterChar = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainEditText = (EditText) findViewById(R.id.mainEditText);

        findViewById(R.id.btn0).setOnClickListener(this);
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);
        findViewById(R.id.btn5).setOnClickListener(this);
        findViewById(R.id.btn6).setOnClickListener(this);
        findViewById(R.id.btn7).setOnClickListener(this);
        findViewById(R.id.btn8).setOnClickListener(this);
        findViewById(R.id.btn9).setOnClickListener(this);
        findViewById(R.id.btnC).setOnClickListener(this);
        findViewById(R.id.btnEquals).setOnClickListener(this);
        findViewById(R.id.btnDivine).setOnClickListener(this);
        findViewById(R.id.btnPlus).setOnClickListener(this);
        findViewById(R.id.btnStar).setOnClickListener(this);
        findViewById(R.id.btnMinus).setOnClickListener(this);
        findViewById(R.id.btnArrow).setOnClickListener(this);
        findViewById(R.id.btnX).setOnClickListener(this);
        findViewById(R.id.btnV).setOnClickListener(this);
        findViewById(R.id.btnM).setOnClickListener(this);
        findViewById(R.id.btnMC).setOnClickListener(this);
    }

    private boolean checkToEmptyStr(){
        return mainEditText == null || mainEditText.getText().length() <= 0;
    }

    private void canOperation(Button button){
        if (mainEditText != null) {
            if (mainEditText.getText().length() == 0 && first != null) {
                setEffectButton(button);
            } else if (mainEditText.getText().length() > 0 && first == null) {
                setEffectButton(button);
                setFirstValue();
            } else if (mainEditText.getText().length() > 0 && first != null && !showResultBeforeEnterChar) {
                Long result = calculate();
                if (result != null) {
                    first = result;
                    second = null;
                    showResultBeforeEnterChar = true;
                    setEffectButton(button);
                } else {
                    resetEffectButton();
                    resetVariable();
                }
            } else if (mainEditText.getText().length() > 0 && first != null && showResultBeforeEnterChar) {
                setEffectButton(button);
            }
        }
    }

    private void canOtherOperation(Button button){
        if(!checkToEmptyStr()){
            Long valueNow = Long.parseLong(mainEditText.getText().toString());
            String simbol = button.getText().toString();

            switch (simbol) {
                case "Х²":
                    clearAll();
                    Long x2 = valueNow*valueNow;
                    mainEditText.getText().append(x2.toString());
                    break;
                case "√":
                    clearAll();
                    Long sqrt = Math.round(Math.sqrt(valueNow));
                    mainEditText.getText().append(sqrt.toString());
                    break;
                case "M":
                    if (valueM == null) {
                        valueM = valueNow;
                        Toast.makeText(getApplicationContext(),
                                "Число "+valueM+" сохранено в памяти", Toast.LENGTH_LONG).show();
                    } else {
                        mainEditText.getText().clear();
                        mainEditText.getText().append(valueM.toString());
                    }
                    break;
                case "MC":
                    if (valueM != null) {
                        mainEditText.getText().clear();
                        mainEditText.getText().append(valueM.toString());
                        valueM = null;
                    }
                    break;
            }

        }
    }

    private void setEffectButton(Button button){
        resetEffectButton();
        effectButton = button;
        effectButton.getBackground().setColorFilter(0xFB363636, PorterDuff.Mode.MULTIPLY);
    }

    private void resetEffectButton(){
        if(effectButton != null){
            effectButton.getBackground().setColorFilter(0xFFD7D8D8, PorterDuff.Mode.MULTIPLY);
        }
    }

    private void resetVariable(){
        effectButton = null;
        first = null;
        second = null;
    }

    private void setFirstValue(){
        first = Long.parseLong(mainEditText.getText().toString());
        mainEditText.getText().clear();
    }

    private Long calculate(){
        Long result = 0L;
        try{
            if(first != null && effectButton != null){
                second = Long.parseLong(mainEditText.getText().toString());

                mainEditText.getText().clear();
                String simbol = effectButton.getText().toString();


                switch (simbol){
                    case "+":  result = first + second;
                        break;
                    case "-":result = first - second;
                        break;
                    case "*":result = first * second;
                        break;
                    case "/":
                        if(second == 0){
                            throw new ArithmeticException();
                        }
                        result = first / second;
                        break;
                }
                mainEditText.getText().append(result.toString());
            }
            return result;
        } catch (ArithmeticException e){
            Toast.makeText(getApplicationContext(),
                    "На ноль делить нельзя!", Toast.LENGTH_LONG).show();
            return null;
        }
    }

    private void calculateValues(){
        if(mainEditText.getText().length() <= 0){
            return;
        }
        calculate();
        resetEffectButton();
        resetVariable();
    }

    private void clearAll(){
        if(!checkToEmptyStr()){
            mainEditText.getText().clear();
        }
        resetEffectButton();
        resetVariable();
    }

    private void updateFirstValue(){
        if(showResultBeforeEnterChar){
            first = Long.parseLong(mainEditText.getText().toString());
        }
    }

    @Override
    public void onClick(View view) {
        Button clickBtn = (Button) view;
        String simbol = clickBtn.getText().toString();
        switch (simbol){
            case "=":
                calculateValues();
                break;
            case "C":
                clearAll();
                break;
            case "←":
                if(!checkToEmptyStr()){
                    int textLenght = mainEditText.getText().length();
                    mainEditText.getText().delete(textLenght - 1, textLenght);
                    updateFirstValue();
                }
                break;
            case "+":
            case "-":
            case "*":
            case "/":
                canOperation(clickBtn);
                break;
            case "Х²":
            case "√":
            case "M":
            case "MC":
                canOtherOperation(clickBtn);
                break;
            case "0":
            case "1":
            case "2":
            case "3":
            case "4":
            case "5":
            case "6":
            case "7":
            case "8":
            case "9":
                if(showResultBeforeEnterChar){
                    mainEditText.getText().clear();
                    showResultBeforeEnterChar = false;
                }
                mainEditText.getText().append(simbol);
                break;
        }
    }
}