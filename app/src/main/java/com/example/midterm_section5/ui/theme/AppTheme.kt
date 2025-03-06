
package com.example.midterm_section5.ui
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun AppTheme (
    content: @Composable () -> Unit
) = MaterialTheme (
        colorScheme = lightColorScheme (
            primary = Color(0xFFB72A33),
            primaryContainer = Color ( 0xFFA6262E),
            secondary = Color (0xFF03C4DD),
        secondaryContainer = Color(0xFF03B2C9),)) {
    content()
}
