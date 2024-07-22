package com.alawnehj.mycalculator;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.alawnehj.mycalculator.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "yazan";
    private ActivityMainBinding activityMainBinding;
    private Double inputValue1, inputValue2;
    private Double result;
    private StringBuilder equation = new StringBuilder().append(ZERO);
    private static final String DECIMAL_POINT = ".";
    private static final String ZERO = "0";
    private static final String DOUBLE_ZERO = "00";
    private static final String MINUS = "-";
    private Operators operators;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(activityMainBinding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        setButtonListeners();
        setNightModeIndicator();
    }

    private void setNightModeIndicator() {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            activityMainBinding.imageNightMode.setImageResource(R.drawable.ic_sun);
        } else {
            activityMainBinding.imageNightMode.setImageResource(R.drawable.ic_moon);
        }
    }

    private void toggleNightMode() {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        recreate();
    }


    private void onPercentageClicked() {
        if (inputValue2 == null) {
            double percentage = getInputValue1() / 100;
            inputValue1 = percentage;
            equation.setLength(0);
            equation.append(percentage);
            updateInputOnDisplay();
        } else {
            double percentageOfValue1 = (getInputValue1() * getInputValue2()) / 100;
            double percentageOfValue2 = getInputValue2() / 100;
            if (operators != null) {
                switch (operators) {
                    case ADDITION:
                        result = getInputValue1() + percentageOfValue1;
                        break;
                    case SUBTRACTION:
                        result = getInputValue1() - percentageOfValue1;
                        break;
                    case MULTIPLICATION:
                        result = getInputValue1() * percentageOfValue2;
                        break;
                    case DIVISION:
                        result = getInputValue2() / percentageOfValue2;
                        break;
                }
            }
            clearEquation(ZERO);
            updateResultOnDisplay(true);
            inputValue1 = result;
            result = null;
            inputValue2 = null;
            operators = null;
        }
    }

    private void clearEquation(String s) {
        equation.setLength(0);
        equation.append(s);
    }

    private void onPlusMinusClicked() {
        if (equation.toString().equals("0") || equation.toString().equals("0.0")) {
            return;
        }
        if (equation.toString().startsWith(MINUS)) {
            equation.deleteCharAt(0);

        } else {
            equation.insert(0, MINUS);
        }
        setInput();
        updateInputOnDisplay();

    }


    private Double calculate() {
        if (operators != null) {
            switch (operators) {
                case ADDITION:
                    return getInputValue1() + getInputValue2();

                case SUBTRACTION:
                    return getInputValue1() - getInputValue2();

                case DIVISION:
                    return getInputValue1() / getInputValue2();

                case MULTIPLICATION:
                    return getInputValue1() * getInputValue2();

            }
        }

        return null;
    }

    private void onAllClearClicked() {
        inputValue1 = 0.0;
        inputValue2 = null;
        result = null;
        operators = null;
        equation.setLength(0);
        equation.append(ZERO);
        clearDisplay();
    }

    private void onOperatorClicked(Operators currentOperator) {
        onEqualsClicked();
        this.operators = currentOperator;

    }

    private void onEqualsClicked() {
        if (inputValue2 != null) {
            result = calculate();
            equation.setLength(0);
            equation.append(ZERO);
            updateResultOnDisplay(false);
            inputValue1 = result;
            inputValue2 = null;
            operators = null;

        } else {
            equation.setLength(0);
            equation.append(ZERO);
        }
    }

    private void setButtonListeners() {
        for (Button button : getNumericButtons()) {
            button.setOnClickListener(v -> {
                Log.d(TAG, "setButtonListeners: " + button.getText().toString());
                onNumberClicked(button.getText().toString());
            });
        }
        activityMainBinding.buttonDoubleZero.setOnClickListener(v -> {
            onDoubleZeroClicked();
        });
        activityMainBinding.buttonZero.setOnClickListener(v -> {
            onZeroClicked();

        });
        activityMainBinding.buttonDecimalPoint.setOnClickListener(v -> {
            onDecimalPointerClickedListener();
        });
        activityMainBinding.buttonAddition.setOnClickListener(v -> {
            onOperatorClicked(operators.ADDITION);
        });
        activityMainBinding.buttonSubtraction.setOnClickListener(v -> {
            onOperatorClicked(operators.SUBTRACTION);
        });
        activityMainBinding.buttonMultiplication.setOnClickListener(v -> {
            onOperatorClicked(operators.MULTIPLICATION);
        });
        activityMainBinding.buttonDivision.setOnClickListener(v -> {
            onOperatorClicked(operators.DIVISION);
        });
        activityMainBinding.buttonEqual.setOnClickListener(v -> {
            onEqualsClicked();
        });
        activityMainBinding.buttonAllClear.setOnClickListener(v -> {
            onAllClearClicked();
        });
        activityMainBinding.buttonPlusMinus.setOnClickListener(v -> {
            onPlusMinusClicked();
        });
        activityMainBinding.buttonPercentage.setOnClickListener(v -> {
            onPercentageClicked();
        });
        activityMainBinding.imageNightMode.setOnClickListener(v -> {
            toggleNightMode();
        });
        activityMainBinding.buttonTwintyDiscount.setOnClickListener(v -> {
            calculateDiscount(20.0);
        });
        activityMainBinding.thairteenDiscount.setOnClickListener(v -> {
            calculateDiscount(30.0);
        });
        activityMainBinding.fourteen.setOnClickListener(v -> {
            calculateDiscount(40.0);
        });
        activityMainBinding.fifteenDiscount.setOnClickListener(v -> {
            calculateDiscount(50.0);
        });



    }

    private void calculateDiscount(double discount) {

        double percentageOfDiscount = (getInputValue1() * discount)/ 100 ;
        result = (getInputValue1() - percentageOfDiscount);
        this.operators = operators.SUBTRACTION;
        inputValue2 = discount;
        updateResultOnDisplay(true);
        inputValue1 = result;
        result = null;
        inputValue2 = null;
        operators = null;

    }

    private void onDecimalPointerClickedListener() {
        if (equation.toString().contains(DECIMAL_POINT))
            return;
        equation.append(DECIMAL_POINT);
        updateInputOnDisplay();
    }

    private Button[] getNumericButtons() {
        Button[] numericButtons = {
                activityMainBinding.buttonOne,
                activityMainBinding.buttonTwo,
                activityMainBinding.buttonThree,
                activityMainBinding.buttonFour,
                activityMainBinding.buttonFive,
                activityMainBinding.buttonSix,
                activityMainBinding.buttonSeven,
                activityMainBinding.buttonEight,
                activityMainBinding.buttonNine,
        };
        return numericButtons;
    }

    private void onNumberClicked(String textNumber) {
        if (equation.toString().startsWith(ZERO)) {
            Log.d(TAG, "onNumberClicked: equation start with zero ");
            equation.deleteCharAt(0);
        } else if (equation.toString().startsWith(String.format("%s %s", MINUS, ZERO))) {
            Log.d(TAG, "onNumberClicked: equation start with -0 ");
            equation.deleteCharAt(1);
        }

        equation.append(textNumber);
        setInput();
        updateInputOnDisplay();
    }

    private void onZeroClicked() {
        if (equation.toString().startsWith(ZERO) && !equation.toString().contains(DECIMAL_POINT))
            return;
        onNumberClicked(ZERO);
    }

    private void onDoubleZeroClicked() {
        if (equation.toString().startsWith(ZERO) && !equation.toString().contains(DECIMAL_POINT))
            return;
        onNumberClicked(DOUBLE_ZERO);
    }


    private void setInput() {
        if (operators == null) {
            Log.d(TAG, "setInput: operators = null,equation= " + equation.toString());
            inputValue1 = Double.parseDouble(equation.toString());

        } else {
            Log.d(TAG, "setInput: operators != null, equation = " + equation.toString());
            inputValue2 = Double.parseDouble(equation.toString());

        }
    }

    private void clearDisplay() {
        activityMainBinding.textInput.setText(getFormattedDisplayValue(inputValue1));
        activityMainBinding.textEquation.setText(null);
    }

    private void updateResultOnDisplay(Boolean isPercentage) {
        activityMainBinding.textInput.setText(getFormattedDisplayValue(result));
        String input2Text = getFormattedDisplayValue(inputValue2);
        if (isPercentage) {
            input2Text = String.format(input2Text + "%s", getString(R.string.percentage));
        }

        activityMainBinding.textEquation.setText(
                String.format("%s %s %s",
                        getFormattedDisplayValue(inputValue1),
                        getOperatorSymbol(),
                        input2Text)
        );


    }


    private void updateInputOnDisplay() {
        if (result == null) {
            Log.d(TAG, "updateInputOnDisplay: result = null");
            activityMainBinding.textEquation.setText(null);
        }

        activityMainBinding.textInput.setText(equation);
    }


    public Double getInputValue1() {

        return inputValue1 != null ? inputValue1 : 0.0;
    }

    public Double getInputValue2() {
        return inputValue2 != null ? inputValue2 : 0.0;
    }

    private String getOperatorSymbol() {
        // i think that here is need to inject an operator as a dependency
        if (operators != null) {
            switch (operators) {
                case ADDITION:
                    return getString(R.string.addition);

                case SUBTRACTION:
                    return getString(R.string.subtraction);

                case MULTIPLICATION:
                    return getString(R.string.multiplication);

                case DIVISION:
                    return getString(R.string.division);

            }

        }

        return null;

    }


    private String getFormattedDisplayValue(Double value) {
        if (value == null) {
            return null;
        } else if (value % 1 == 0.0) {
            return String.valueOf(value.intValue());
        } else {
            return value + "";
        }


    }

    public enum Operators {
        ADDITION,
        SUBTRACTION,
        MULTIPLICATION,
        DIVISION
    }
}