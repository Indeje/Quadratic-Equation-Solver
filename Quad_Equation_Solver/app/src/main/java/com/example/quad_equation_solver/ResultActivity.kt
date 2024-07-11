package com.example.quad_equation_solver

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
//import androidx.compose.ui.tooling.preview.Preview
import com.example.quad_equation_solver.ui.theme.Quad_Equation_SolverTheme


class ResultActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Quad_Equation_SolverTheme {
                val natureOfRoots = intent.getStringExtra("NATURE_OF_ROOTS") ?: "N/A"
                val determinant = intent.getDoubleExtra("DETERMINANT", 0.0)
                val root1 = intent.getDoubleExtra("ROOT1", 0.0)
                val root2 = intent.getDoubleExtra("ROOT2", 0.0)

                ResultScreen(determinant, natureOfRoots, root1, root2)
            }
        }
    }
}

@Composable
fun ResultScreen(determinant: Double, natureOfRoots: String, root1: Double, root2: Double) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {}
        ) {
            Text(text = "Determinant = $determinant", style = MaterialTheme.typography.titleLarge)
        }
        Spacer(modifier = Modifier.height((32.dp)))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {}
        ) {
            Text(text = "$natureOfRoots Roots", style = MaterialTheme.typography.titleLarge)
        }
        Spacer(modifier = Modifier.height((32.dp)))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {}
        ) {
            Text(text = "($root1, $root2)", style = MaterialTheme.typography.titleLarge)
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun ResultScreenPreview() {
//    Quad_Equation_SolverTheme {
//        ResultScreen(1.0, "REAL", 1.0, 1.0)
//    }
//}
