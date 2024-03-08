package com.jsl.Example.view

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.hardware.Camera
import android.os.Bundle
import android.util.Log
import android.view.SurfaceView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.jsl.Example.R
import com.jsl.Example.ui.theme.ExampleTheme
import com.jsl.Example.utils.CommonUtils
import com.jsl.Example.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.NumberFormat
import kotlin.system.exitProcess


class MainActivity : ComponentActivity() {

    val viewModel: MainViewModel = MainViewModel()
    val TAG = MainActivity::class.qualifiedName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.init()
        setContent {
            ExampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GreetingPreview()
                }
            }
        }
        var count: Int = 0
        GlobalScope.launch(context = Dispatchers.IO){
            while (count < 100){
                count++
                Log.e(TAG, count.toString())
            }
        }
    }

}

@Composable
fun TipTimeLayout() {
    var amountInput by remember { mutableStateOf("") }
    var customTip by remember { mutableStateOf("") }
    var roundUp by remember { mutableStateOf(false) }   //默认switch为false关
    val amount = amountInput.toDoubleOrNull() ?: 0.0
    val tipPercent = customTip.toDoubleOrNull() ?: 0.0
    val tip = CommonUtils.calculateTip(amount, tipPercent, roundUp)
    val context = LocalContext.current

    Column {
        Row(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(text = "布局显示")
            Text(text = "布局显示")
        }
        Column(
            modifier = Modifier
                .statusBarsPadding()
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .safeDrawingPadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            TopBar()
            Text(
                text = stringResource(R.string.calculate_tip),
                modifier = Modifier
                    .padding(bottom = 16.dp, top = 40.dp)
                    .align(alignment = Alignment.Start)
            )
            EditNumberField(
                modifier = Modifier
                    .padding(bottom = 32.dp)
                    .fillMaxWidth(),
                value = amountInput,
                onValueChanged = { amountInput = it },
                label = R.string.bill_amount,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                icon = R.drawable.ic_launcher_foreground
            )

            EditNumberField(
                modifier = Modifier
                    .padding(bottom = 32.dp)
                    .fillMaxWidth(),
                value = customTip,
                onValueChanged = { customTip = it },
                label = R.string.how_was_the_service,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                icon = R.drawable.ic_launcher_foreground
            )

            swtichRow(
                roundUp = roundUp,
                onRoundUpChanged = { roundUp = it },
                context
            )

            Text(
                text = stringResource(R.string.tip_amount, tip),
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier.padding(top = 32.dp)
            )

            userInfomation(modifier = Modifier.padding(10.dp))

            customLayout(text = "")


            Spacer(modifier = Modifier.height(150.dp))
        }
    }
}



@Composable
fun playVideoTest(){
    AndroidView(factory = {context ->
        SurfaceView(context)
    },
    modifier = Modifier.fillMaxSize())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    val context = LocalContext.current
    TopAppBar(
        title = { Text("这是标题") },
        navigationIcon = {
            IconButton(onClick = { exitProcess(1) }) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "返回按钮")
            }
        },
        actions = {
            IconButton(onClick = { }) {
                Icon(imageVector = Icons.Filled.Share, contentDescription = null)
            }
            IconButton(onClick = { }) {
                Icon(imageVector = Icons.Filled.Settings, contentDescription = null)
            }
            IconButton(onClick = { CommonUtils.navigation(context, SecondActivity::class.java) }) {
                Icon(imageVector = Icons.Filled.Build, contentDescription = null)
            }
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNumberField(modifier: Modifier = Modifier,
                    value: String,
                    onValueChanged: (String) -> Unit,
                    @StringRes label: Int,
                    keyboardOptions: KeyboardOptions,
                    @DrawableRes icon: Int) {
    TextField(
        value = value,
        onValueChange = onValueChanged,
        modifier = modifier.padding(start = 40.dp, end = 40.dp),
        label = { Text( text = stringResource(id = label))},
        leadingIcon = { Icon(painter = painterResource(id = icon), null) },
        singleLine = true,
        keyboardOptions = keyboardOptions
    )
}

@Composable
fun userInfomation(modifier: Modifier = Modifier){
    Row(modifier = modifier
        .fillMaxWidth()
        .padding(start = 20.dp)) {
        Image(
            modifier = Modifier
                .wrapContentSize()
                .size(width = 50.dp, height = 50.dp),
            painter = painterResource(id = R.drawable.search),
            contentDescription = "Image"
        )
        Column(modifier = Modifier.padding(start = 30.dp)){
            Text(text = "Test")
            Text(text = "page1")
        }
    }
}

@Composable
fun layoutTest( modifier: Modifier = Modifier,
                content: @Composable () -> Unit) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        val placeables = measurables.map { measurable ->
            measurable.measure(constraints)
        }
        var yPosition = 0
        layout(constraints.maxWidth, constraints.maxHeight) {
            placeables.forEach { placeable ->
                placeable.placeRelative(x = 0, y = yPosition)
                yPosition += placeable.height
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun customLayout(modifier: Modifier = Modifier, text: String){
    var str by remember { mutableStateOf(text) }
    layoutTest(modifier = modifier.padding(20.dp)) {
        TextField(
            value = str,
            onValueChange = { str = it },
            placeholder = { Text(
                text = "请输入内容",
                color = Color.Gray) }
        )
    }
}

@Composable
fun swtichRow(roundUp: Boolean, onRoundUpChanged: (Boolean) -> Unit, context: Context, modifier: Modifier = Modifier){
    Row (modifier = modifier
        .fillMaxWidth()
        .padding(start = 40.dp, end = 40.dp)
        .size(48.dp),
        verticalAlignment =  Alignment.CenterVertically){
        Text(
            text = stringResource(R.string.round_up_tip),
        )
        Switch(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End),
            checked = roundUp,
            onCheckedChange = { onRoundUpChanged }
        )
    }
}

@Composable
fun cameraTest(){
}

@Preview(showBackground = true)
@Composable
private fun GreetingPreview() {
    ExampleTheme {
        TipTimeLayout()
//        playVideoTest()
    }
}