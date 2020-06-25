package lk.sltc.mad.calculatordemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText questionText;
    EditText answerText;

    boolean operatorInUse;

    String previousOperator;
    String currentOperator;

    List<Double> values = new ArrayList<Double>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculator);

        questionText = findViewById(R.id.et_question);
        answerText = findViewById(R.id.et_answer);
    }

    public void numberTapped(View view) {
        Button numberButton = (Button) view;
        String numberText = numberButton.getText().toString();
        questionText.append(numberText);

        operatorInUse = false;
    }

    public void operatorTapped(View view) {
        if (!operatorInUse) {
            Button operatorButton = (Button) view;
            String operatorText = operatorButton.getText().toString();

            switch (operatorButton.getId()) {
                case R.id.btn_equal:
                    values.add(getLastValue());
                    calculate();
                    questionText.setText("");
                    break;
                case R.id.btn_dot:
                    questionText.append(operatorText);
                    break;

                default:
                    currentOperator = operatorText;
                    questionText.append(operatorText);
                    values.add(getLastValue());

                    calculate();
                    break;
            }

            operatorInUse = true;
        }
    }

    private double getLastValue() {
        String questionString = questionText.getText().toString();
        String []numberStrings =  questionString.split("[+-/*]");
        String lastNumberString = numberStrings[numberStrings.length - 1];
        double lastValue = Double.parseDouble(lastNumberString);

        return lastValue;
    }

    private void calculate() {
        double answer = 0.0;
        if (values.size() == 1) {
            answer = values.get(0);
        } else if (values.size() > 1) {
            double value1 = values.get(0);
            double value2 = values.get(1);

            switch (previousOperator) {
                case "+":
                    answer = value1 + value2;
                    break;
                case "-":
                    answer = value1 - value2;
                    break;
                case "/":
                    answer = value1 / value2;
                    break;
                case "*":
                    answer = value1 * value2;
                    break;
            }
        }

        String answerString = String.valueOf(answer);
        answerText.setText(answerString);

        values.clear();
        values.add(answer);

        previousOperator = currentOperator;
    }


    public void clearTapped(View view) {
        questionText.setText("");
        answerText.setText("");
        operatorInUse = false;
    }
}
