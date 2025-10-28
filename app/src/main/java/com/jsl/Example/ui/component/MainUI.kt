@file:OptIn(ExperimentalMaterial3Api::class)

package com.jsl.Example.ui.component

import android.app.Activity
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.compose.ui.unit.dp
import com.jsl.Example.R
import com.jsl.Example.utils.CommonUtils
import com.jsl.Example.view.SecondActivity
import com.jsl.Example.view.SettingActivity
import kotlinx.coroutines.launch
import kotlin.system.exitProcess

@Composable
fun TipTimeLayout() {       //页面显示
    var amountInput by remember { mutableStateOf("") }
    var customTip by remember { mutableStateOf("") }
    var roundUp by remember { mutableStateOf(false) }   //默认switch为false关
    val amount = amountInput.toDoubleOrNull() ?: 0.0
    val tipPercent = customTip.toDoubleOrNull() ?: 0.0
    val tip = CommonUtils.calculateTip(amount, tipPercent, roundUp)

    var showdialog by remember { mutableStateOf(false) }

    Column() {
        TopBar(
            showdialog = { showdialog = !showdialog }
        )
        if (showdialog){
            dialogTest { showdialog = !showdialog }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .safeDrawingPadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = stringResource(R.string.calculate_tip),
                modifier = Modifier
                    .padding(bottom = 16.dp, top = 20.dp)
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
                icon = R.drawable.account
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
                icon = R.drawable.password
            )

            swtichRow(
                roundUp = roundUp,
                onRoundUpChanged = { roundUp = it }
            )

            Text(
                text = stringResource(R.string.tip_amount, tip),
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier.padding(top = 32.dp)
            )

            userInfomation(modifier = Modifier.padding(30.dp))

            outLineTest()

            customLayout(text = "")

            showMessage("显示SnackBar" )

            Spacer(modifier = Modifier.height(150.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun outLineTest(){  //输入框，类似EditText
    var name by remember { mutableStateOf("") }
    OutlinedTextField(
        value = name,
        onValueChange = { name = it },
        label = { Text("Name") }
    )
}


@Composable
fun dialogTest(showDialog: () -> Unit){     //Dialog框
    AlertDialog(
        onDismissRequest = { /*TODO*/ },
        text = { Text(text = "请确认是否退出") },
        dismissButton = {
            Button(
                onClick = showDialog
            ) {
                Text(text = "取消")
            }
        },
        confirmButton = {
            Button(
                onClick = { exitProcess(0) }
            ) {
                Text(text = "确认")
            }
        }
    )
}

@Composable
fun showMessage(message: String){   //SnackBar，再Compose作为替代Toast
    val snackBarHost = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    Box(){
        Button(onClick = {
            coroutineScope.launch {
                snackBarHost.showSnackbar("", duration = SnackbarDuration.Short)
            }
        }){}
        SnackbarHost(   //使用SnackBar替换了Toast
            hostState = snackBarHost,
            modifier = Modifier.align(Alignment.BottomCenter)
        ){
            Snackbar(
                contentColor = Color.Magenta,
                shape = ShapeDefaults.ExtraSmall
            ) {
                Row {
                    Text(text = message)
                }
            }
        }
    }
}

@Composable
fun customSnack(){     //SnackBar
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text("Show snackbar") },
                icon = { Icon(Icons.Filled.Share, contentDescription = "") },
                onClick = {
                    scope.launch {
                        snackbarHostState.showSnackbar("Snackbar")
                    }
                }
            )
        }
    ) { contentPadding ->
        TipTimeLayout()
        Box(modifier = Modifier.padding(contentPadding))
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(showdialog: () -> Unit) {        //顶部栏，用于设置等
    val context = LocalContext.current
    TopAppBar(
        title = { Text("这是标题") },
        navigationIcon = {
            IconButton(onClick = showdialog ) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "返回按钮")
            }
        },
        actions = {
            IconButton(onClick = { CommonUtils.navigation(context, SecondActivity::class.java) }) {
                Icon(imageVector = Icons.Filled.Share, contentDescription = null)
            }
            IconButton(onClick = { CommonUtils.navigation(context, SettingActivity::class.java)}) {
                Icon(imageVector = Icons.Filled.Settings, contentDescription = null)
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
        .fillMaxWidth()) {
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
fun swtichRow(roundUp: Boolean, onRoundUpChanged: (Boolean) -> Unit, modifier: Modifier = Modifier){
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
            onCheckedChange = onRoundUpChanged
        )
    }
}