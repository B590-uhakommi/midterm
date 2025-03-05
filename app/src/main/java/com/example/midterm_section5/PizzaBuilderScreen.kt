package com.example.midterm_section5

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role.Companion.Button
//import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import com.example.midterm_section5.model.Topping
import com.example.midterm_section5.model.ToppingPlacement
import com.example.midterm_section5.ui.theme.ToppingCell
import androidx.compose.material3.Text
import androidx.compose.ui.unit.dp
import java.util.Locale

@Preview
@Composable
fun PizzaBuilderScreen(
    modifier: Modifier=Modifier
){
    Column(modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween) {
        ToppingsList(modifier = Modifier.weight(1f,fill=true))
        OrderButton(modifier=Modifier.fillMaxWidth().padding(10.dp))
    }
}



@Composable
private fun ToppingsList(
    modifier: Modifier = Modifier
) {
//    ToppingCell(topping = Topping.Pepperoni,
//        placement = ToppingPlacement.Left,
//        onClickTopping = {},
//        modifier=modifier )
    LazyColumn(modifier = modifier) {
        items(Topping.values()) { topping ->
            ToppingCell(
                topping = topping,
                placement = ToppingPlacement.Left,
                onClickTopping = { /* Handle click */ }
            )
        }
    }
}

@Composable
private fun OrderButton(
    modifier: Modifier = Modifier
) {
    Button(
        modifier = modifier,
        onClick ={
    }
) {
// TODO
        Text(
            text = stringResource(R.string.place_order_button)
                .toUpperCase( Locale.getDefault()) )
    }
}
