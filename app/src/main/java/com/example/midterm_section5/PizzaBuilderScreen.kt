package com.example.midterm_section5

import android.icu.number.NumberFormatter
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Scaffold

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.dp
import com.example.midterm_section5.model.Pizza
import java.util.Locale
import  androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.example.midterm_section5.ui.theme.PizzaHeroImage
import com.example.midterm_section5.ui.theme.ToppingPlacementDialog
import java.text.NumberFormat




//
//private var pizza=
//    Pizza(
//        toppings = mapOf(
//            Topping.Pepperoni to ToppingPlacement.All,
//            Topping.Pineapple to ToppingPlacement.All
//        )
//    )
//    set(value) {
//        Log.d("PizzaBuilderScreen","Reassigned pizza to $value")
//        field=value
//    }


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun PizzaBuilderScreen(
    modifier: Modifier = Modifier
) {
    var pizza by rememberSaveable { mutableStateOf(Pizza()) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.app_name)) }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                ToppingsList(
                    pizza = pizza,
                    onEditPizza = { pizza = it },
                    modifier = modifier
                        .fillMaxWidth()
                        .weight(1f, fill = true)
                )
                OrderButton(
                    pizza = pizza,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                )
            }
        }
    )

}




@Composable
private fun ToppingsList(
    pizza: Pizza,
    onEditPizza: (Pizza) -> Unit,
    modifier: Modifier = Modifier
) {
//    ToppingCell(topping = Topping.Pepperoni,
//        placement = ToppingPlacement.Left,
//        onClickTopping = {},
//        modifier=modifier )
    var toppingBeingAdded by rememberSaveable { mutableStateOf<Topping?>(null
    ) }
   toppingBeingAdded?.let {
       topping ->
       ToppingPlacementDialog(
           topping = topping,
           onSetToppingPlacement={placement ->
               onEditPizza(pizza.withTopping(topping,placement))
           },
           onDismissRequest = {
               toppingBeingAdded = null
           }
       )
   }

    LazyColumn(modifier = modifier) {
        item {
            PizzaHeroImage(
                pizza = pizza,
                modifier = Modifier.padding(16.dp)
            )
        }
        items(Topping.values()) { topping ->
            ToppingCell(
                topping = topping,
                placement = pizza.toppings[topping],
                onClickTopping = {
//                    val isOnPizza = pizza.toppings[topping]!=null
//                    onEditPizza( pizza.withTopping(
//                        topping = topping,
//                        placement = if(isOnPizza){
//                            null
//                        }else{
//                            ToppingPlacement.All
//                        }
                  toppingBeingAdded = topping
                }
            )
        }
    }
}

@Composable
private fun OrderButton(
    pizza: Pizza,
    modifier: Modifier = Modifier
) {
    Button(
        modifier = modifier,
        onClick ={
    }
) {
// TODO
        val currencyFormatter = remember{ NumberFormat.getCurrencyInstance() }
        val price = currencyFormatter.format(pizza.price)
        Text(
            text = stringResource(R.string.place_order_button,price)
                .toUpperCase( Locale.getDefault()) )
    }
}
