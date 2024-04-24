package com.example.myapplicationcscsasac
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplicationcscsasac.ui.theme.MyApplicationcscsasacTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationcscsasacTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BMICalculatorScreen()                }
            }
        }
    }
}


@Composable
fun BMICalculatorScreen() {
    var height by rememberSaveable { mutableStateOf("") }
    var weight by rememberSaveable { mutableStateOf("") }
    var bmi by rememberSaveable { mutableStateOf(0f) }
    var bmiCategory by rememberSaveable { mutableStateOf("") }
    var heightError by rememberSaveable { mutableStateOf(false) }
    var weightError by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Calculate BMI", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = height,
            onValueChange = { height = it; heightError = false },
            label = { Text("Height (cm)") },
            placeholder = { Text("Enter height in cm") },
            isError = heightError,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        )
        if (heightError) {
            Text("Height is required", color = Color.Red, style = MaterialTheme.typography.bodyMedium)
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = weight,
            onValueChange = { weight = it; weightError = false },
            label = { Text("Weight (kg)") },
            placeholder = { Text("Enter weight in kg") },
            isError = weightError,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        )
        if (weightError) {
            Text("Weight is required", color = Color.Red, style = MaterialTheme.typography.bodyMedium)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (height.isEmpty()){
                    heightError = true
                }
                if (weight.isEmpty()){
                    weightError = true
                }
                    if (!weight.isEmpty() and ! height.isEmpty() )  {
                        bmi = calculateBMI(height.toFloat(), weight.toFloat())
                        bmiCategory = getBMICategory(bmi)
                    }
                }
            ,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calculate")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (bmi > 0 && !heightError && !weightError) {
            BMICategoryIndicator(bmi, bmiCategory)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}


@Composable
fun BMICategoryIndicator(bmi: Float, category: String) {
    var textcolor = Color.Black
    if(category == "Underweight"){
        textcolor  = Color.Yellow
    }
    else if (category == "Normal Weight"){
        textcolor = Color.Green
    }
    else{
        textcolor = Color.Red
    }
    Text("$bmi", fontSize = 48.sp, fontWeight = FontWeight.Bold, color =textcolor )
    Text(category, fontSize = 24.sp, fontWeight = FontWeight.Bold, color =textcolor)
}


fun calculateBMI(height: Float, weight: Float): Float {
    return weight / ((height / 100) * (height / 100))
}

fun getBMICategory(bmi: Float): String {
    return when {
        bmi < 18.5 -> "Underweight"
        bmi in 18.5..24.9 -> "Normal Weight"
        bmi in 25.0..29.9 -> "Overweight"
        bmi in 30.0..34.9 -> "Obese Class I"
        bmi in 35.0..39.9 -> "Obese Class II"
        else -> "Obese Class III"
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationcscsasacTheme {
        Greeting("Android")
    }
}