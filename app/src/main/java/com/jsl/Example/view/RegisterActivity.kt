package com.jsl.Example.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.jsl.Example.registerView
import com.jsl.Example.ui.theme.ExampleTheme

class RegisterActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            registerView()
        }
    }

}

@Preview(showBackground = true)
@Composable
fun preRegisterView(){
    ExampleTheme {
        registerView()
    }
}