package com.jsl.Example.view

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.jsl.Example.ui.theme.ExampleTheme
import com.jsl.Example.ui.component.webviewTest

class WebViewActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent(){
            ExampleTheme() {
                Surface {
                    val context = LocalContext.current as Activity //杩欐牱鍋氬彲鑳戒細鏈夊畨鍏ㄩ殣鎮£
                    val TAG = WebViewActivity::class.qualifiedName
                    webviewTest(modifier = Modifier.fillMaxSize(),
                        url = "https://www.baidu.com/",
                        initSettings = {settings->
                            settings?.apply {
                                javaScriptEnabled = true
                                //....
                            }
                        }, onBack = { webView ->
                            if (webView?.canGoBack() == true) {
                                webView.goBack()
                            } else {
                                context.finish()
                            }
                        },onReceivedError = {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                Log.d(TAG,">>>>>>${it?.description}")
                            }
                        })
                }
            }
        }
    }

}