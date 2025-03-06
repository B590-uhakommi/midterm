package com.example.midterm_section5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.midterm_section5.model.Topping
import com.example.midterm_section5.model.ToppingPlacement
import com.example.midterm_section5.ui.AppTheme
import com.example.midterm_section5.ui.theme.ToppingCell

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                PizzaBuilderScreen()
            }
        }
    }


}
