package com.jsl.Example.ui.component

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.jsl.Example.utils.CommonUtils
import com.jsl.Example.view.CameraActivity

@Composable
fun preview(){
    val context = LocalContext.current as Activity
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "这是第二个Activity")
        Button(onClick = { context.finish()}) {
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