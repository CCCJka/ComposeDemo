package com.jsl.Example.view

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.jsl.Example.ui.theme.ExampleTheme
import kotlinx.coroutines.launch

class WebViewActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent(){
            ExampleTheme() {
                Surface {
                    webviewPreview()
                }
            }
        }
    }

}

@Composable
fun webviewTest(modifier: Modifier = Modifier,
                url:String,
                onBack: (webView: WebView?) -> Unit,
                onProgressChange: (progress:Int)->Unit = {},
                initSettings: (webSettings: WebSettings?) -> Unit = {},
                onReceivedError: (error: WebResourceError?) -> Unit = {}){
    val webViewChromeClient = object: WebChromeClient(){
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            //回调网页内容加载进度
            onProgressChange(newProgress)
            super.onProgressChanged(view, newProgress)
        }
    }
    val webViewClient = object: WebViewClient(){
        override fun onPageStarted(view: WebView?, url: String?,
                                   favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            onProgressChange(-1)
        }
        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            onProgressChange(100)
        }
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            if(null == request?.url) return false
            val showOverrideUrl = request.url.toString()
            try {
                if (!showOverrideUrl.startsWith("http://")
                    && !showOverrideUrl.startsWith("https://")) {
                    //处理非http和https开头的链接地址
                    Intent(Intent.ACTION_VIEW, Uri.parse(showOverrideUrl)).apply {
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        view?.context?.applicationContext?.startActivity(this)
                    }
                    return true
                }
            }catch (e:Exception){
                //没有安装和找到能打开(「xxxx://openlink.cc....」、「weixin://xxxxx」等)协议的应用
                return true
            }
            return super.shouldOverrideUrlLoading(view, request)
        }

        override fun onReceivedError(
            view: WebView?,
            request: WebResourceRequest?,
            error: WebResourceError?
        ) {
            super.onReceivedError(view, request, error)
            //自行处理....
            onReceivedError(error)
        }
    }
    var webView:WebView? = null
    val coroutineScope = rememberCoroutineScope()
    AndroidView(modifier = modifier,factory = { ctx ->
        WebView(ctx).apply {
            this.webViewClient = webViewClient
            this.webChromeClient = webViewChromeClient
            //回调webSettings供调用方设置webSettings的相关配置
            initSettings(this.settings)
            webView = this
            loadUrl(url)
        }
    })
    BackHandler {
        coroutineScope.launch {
            //自行控制点击了返回按键之后，关闭页面还是返回上一级网页
            onBack(webView)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun webviewPreview(){
    ExampleTheme() {
        val context = LocalContext.current as Activity //这样做可能会有安全隐患
        val TAG = WebViewActivity::class.qualifiedName
//使用
        webviewTest(modifier = Modifier.fillMaxSize(),
            url = "https://www.baidu.com/",
            initSettings = {settings->
                settings?.apply {
                    //支持js交互，此项类似java中的WebSettings
                    javaScriptEnabled = true
                    //....
                }
            }, onBack = { webView ->
                //可根据需求处理此处
                if (webView?.canGoBack() == true) {
                    //返回上一级页面
                    webView.goBack()
                } else {
                    //关闭activity
                    context.finish()
                }
            },onReceivedError = {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Log.d(TAG,">>>>>>${it?.description}")
                }
            })
    }
}

