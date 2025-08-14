package com.jsl.Example.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.jsl.Example.loginView
import com.jsl.Example.ui.theme.ExampleTheme

class LoginActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent { loginView() }


    }


}

@Preview(showBackground = true)
@Composable
private fun GreetingPreview() {
    ExampleTheme {
//        TipTimeLayout()
//        playVideoTest()
        loginView();
    }
}