package com.jsl.Example.view

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jsl.Example.ui.theme.ExampleTheme
import com.jsl.Example.utils.CommonUtils

class SecondActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    secondPreview()
                }
            }
        }
    }
}

@Composable
fun preview(){
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "这是第二个Activity")
        Button(onClick = {  }) {
            Text(text = "返回按钮")
        }
    }

    Column(
        modifier = Modifier
            .padding(30.dp)
            .fillMaxWidth(),
        horizontalAlignment =  Alignment.End
    ) {
        Button(onClick = { CommonUtils.navigation(context, CameraActivity::class.java) }) {
            Text(text = "跳转至拍照")
        }
    }

}

@Preview (showBackground = true)
@Composable
private fun secondPreview(){
    ExampleTheme {
        preview()
    }
}