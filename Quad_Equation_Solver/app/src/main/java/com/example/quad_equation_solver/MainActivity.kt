package com.example.quad_equation_solver

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.Modifier
//import androidx.compose.ui.tooling.preview.Preview
import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import com.example.quad_equation_solver.ui.theme.Quad_Equation_SolverTheme
import kotlinx.coroutines.CoroutineScope
import java.math.BigDecimal
import java.math.RoundingMode
import kotlinx.coroutines.launch
import kotlin.math.sqrt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Render main screen
        setContent {
            Quad_Equation_SolverTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    var a by remember { mutableStateOf("") }
    var b by remember { mutableStateOf("") }
    var c by remember { mutableStateOf("") }
    val context: Context = LocalContext.current
    val snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
    val coroutineScope: CoroutineScope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) {
        paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(48.dp))
            Text(
                text = "Quadratic Equation Solver",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                label = { Text("Coefficient a") },
                modifier = Modifier.fillMaxWidth(),
                value = a,
                onValueChange = {
                    if (it.matches(Regex("^-?\\d*\\.?\\d*\$"))) {
                        a = it
                    }
                }
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                label = { Text("Coefficient b") },
                modifier = Modifier.fillMaxWidth(),
                value = b,
                onValueChange = {
                    if (it.matches(Regex("^-?\\d*\\.?\\d*\$"))) {
                        b = it
                    }
                }
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                label = { Text("Constant c") },
                modifier = Modifier.fillMaxWidth(),
                value = c,
                onValueChange = {
                    if (it.matches(Regex("^-?\\d*\\.?\\d*\$"))) {
                        c = it
                    }
                }
            )
            Spacer(modifier = Modifier.height(32.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    performCalculation(a, b, c, context, coroutineScope, snackbarHostState)
                }
            ) {
                Text("Calculate")
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun MainScreenPreview() {
//    Quad_Equation_SolverTheme {
//        MainScreen()
//    }
//}


fun performCalculation(a: String, b: String, c: String, context: Context, coroutineScope: CoroutineScope, snackbarHostState: SnackbarHostState) {
    val numA = a.toDoubleOrNull()
    val numB = b.toDoubleOrNull()
    val numC = c.toDoubleOrNull()

    if (numA == null) {
        coroutineScope.launch {
            snackbarHostState.showSnackbar(message = "Invalid input 'a'")
        }
    }

    if (numB == null) {
        coroutineScope.launch {
            snackbarHostState.showSnackbar(message = "Invalid input 'b'")
        }
    }

    if (numC == null) {
        coroutineScope.launch {
            snackbarHostState.showSnackbar(message = "Invalid input 'c'")
        }
    }

    if (numA != null && numB != null && numC != null) {
        val solutions = computeSolution(numA, numB, numC)

        val intent = Intent(context, ResultActivity::class.java).apply {
            putExtra("DETERMINANT", solutions[0].toDoubleOrNull())
            putExtra("NATURE_OF_ROOTS", solutions[1])
            putExtra("ROOT1", solutions[2].toDoubleOrNull())
            putExtra("ROOT2", solutions[3].toDoubleOrNull())
        }
        context.startActivity(intent)
    }
}

fun computeSolution(numA: Double, numB: Double, numC: Double): List<String> {
    val determinant: Double = (numB * numB) - (4 * numA * numC)
    val natureOfRoots: String = when {
        (determinant < 0) -> "Complex, Distinct"
        (determinant > 0) -> "Real, Distinct"
        else -> "Real, Equal"
    }
    var root1 : Double = when {
        (determinant >= 0) -> ((-1 * numB) + sqrt(determinant)) / (2 * numA)
        else -> Double.NaN
    }
    var root2 : Double = when {
        (determinant >= 0) -> ((-1 * numB) - sqrt(determinant)) / (2 * numA)
        else -> Double.NaN
    }
    if (!root1.isNaN()) {
        root1 = BigDecimal(root1).setScale(2, RoundingMode.HALF_EVEN).toDouble()
    }
    if (!root2.isNaN()) {
        root2 = BigDecimal(root2).setScale(2, RoundingMode.HALF_EVEN).toDouble()
    }

    return listOf(determinant.toString(), natureOfRoots, root1.toString(), root2.toString())
}
