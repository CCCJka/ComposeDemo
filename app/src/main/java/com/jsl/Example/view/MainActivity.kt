package com.jsl.Example.view

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jsl.Example.bean.ResponBean
import com.jsl.Example.ui.component.customSnack
import com.jsl.Example.ui.theme.ExampleTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ExampleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GreetingPreview()
                }
            }
        }

        val bean = ResponBean()
        val unParameter = ResponBean("使用数据类时使用参数")
        val copyBean = unParameter.copy("赋值Bean类数据后修改为这句话")
        Log.e("测试数据类", bean.data)
        Log.e("测试数据类", unParameter.data)
        Log.e("测试数据类", copyBean.data)

    }

}


@Preview(showBackground = true)
@Composable
private fun GreetingPreview() {
    ExampleTheme {
//        TipTimeLayout()
//        playVideoTest()
        customSnack();
    }
}