package com.jsl.Example.ui.component

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.jsl.Example.R
import com.jsl.Example.db.DbHelper
import com.jsl.Example.utils.CommonUtils
import com.jsl.Example.view.MainActivity
import com.jsl.Example.view.RegisterActivity

var account = ""
var password = ""
var dialogAccount = ""
var dialogNewPassword = ""

@Preview(showBackground = true)
@Composable
fun loginView(){
    val context = LocalContext.current
    var dialogShowFlag by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        if (dialogShowFlag){
            passwordView(
                showDialog = {
                    //点击dialog的取消后隐藏dialog
                    dialogShowFlag = !dialogShowFlag
                },
                changePassword = {
                    val userList = DbHelper.get().getUserList()
                    for (item in userList){
                        if (item.account.equals(dialogAccount)){
                            item.pwd = dialogNewPassword
                            DbHelper.get().saveUser(item)
                            Toast.makeText(context, "更改用户密码成功，请登录", Toast.LENGTH_SHORT).show()
                            break
                        }
                    }
                }
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.login_background_color)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            userInfo(onCLick = { dialogShowFlag = ! dialogShowFlag })
            loginOperation(
                login = {
                    val userlist = DbHelper.get().getUserList()
                    if (userlist.isEmpty()){
                        Toast.makeText(context, "请输入用户名和密码", Toast.LENGTH_SHORT).show()
                    } else{
                        for (item in userlist){
                            if (item.account.equals(account) && item.pwd.equals(password)){
                                CommonUtils.navigation(context, MainActivity::class.java)
                            } else if(item.account.equals(account) && !item.pwd.equals(password)) {
                                Toast.makeText(context, "用户名或密码错误", Toast.LENGTH_SHORT).show()
                            } else if(!item.account.equals(account) && !item.pwd.equals(password)) {
                                Toast.makeText(context, "请检查账户信息，请检查", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                },
                register = {
                    CommonUtils.navigation(context, RegisterActivity::class.java)
                })
        }
    }
}

@Composable
fun userInfo(onCLick: () -> Unit){
    Box(
        modifier = Modifier
            .size(350.dp, 200.dp)
            .background(Color.White, RoundedCornerShape(10)),
        contentAlignment = Alignment.BottomCenter,
    ) {
        Column {
            iconEditText(vector = Icons.Filled.Person, "账号/邮箱/手机号", onChange = { account = it })
            iconEditText(vector = Icons.Filled.Lock, "密码", onChange =  { password = it })
            Row(
                modifier = Modifier.align(alignment = Alignment.End)
            ) {
                TextButton(onClick = onCLick) {
                    Text(
                        fontSize = 11.sp,
                        text = "忘记密码?"
                    )
                }
            }
        }

    }

}

@Composable
fun loginOperation(login: () -> Unit, register: () -> Unit){

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedButton(
            modifier = Modifier
                .size(350.dp, 60.dp)
                .padding(top = 20.dp),
            colors = ButtonDefaults.outlinedButtonColors(colorResource(id = R.color.button_color)),
            border = BorderStroke(1.dp, colorResource(id = R.color.button_color)),
            onClick = login) {
                Text(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    color = Color.White,
                    fontSize = 15.sp,
                    text = "登录"
                )
            }

        Row (
            modifier = Modifier
                .padding(top = 30.dp, bottom = 30.dp)
                .align(Alignment.CenterHorizontally)
        ){
            Divider(
                modifier = Modifier
                    .size(150.dp, 1.dp)
                    .align(alignment = Alignment.CenterVertically)
                    .padding(end = 50.dp),
                color = Color.Gray,
                thickness = 1.dp
            )
            Text(
                color = Color.Black,
                fontSize = 15.sp,
                text = "或"
            )
            Divider(
                modifier = Modifier
                    .size(150.dp, 1.dp)
                    .align(alignment = Alignment.CenterVertically)
                    .padding(start = 50.dp),
                color = Color.Gray,
                thickness = 1.dp
            )
        }

        OutlinedButton(
            modifier = Modifier
                .size(350.dp, 60.dp)
                .padding(bottom = 20.dp),
            colors = ButtonDefaults.outlinedButtonColors(colorResource(id = R.color.white)),
            border = BorderStroke(1.dp, colorResource(id = R.color.button_color)),
            onClick = register) {
            Text(
                text = "立即注册",
                color = colorResource(id = R.color.button_color)
            )
        }
    }

}

@Composable
fun passwordView(showDialog: () -> Unit, changePassword: () -> Unit){
    Dialog(
        onDismissRequest = {  },
    ) {
        Column(
            modifier = Modifier.background(Color.White, RoundedCornerShape(10))
        ){
            iconEditText(vector = Icons.Filled.AccountBox, info = "请输入账号", onChange = { dialogAccount = it })
            iconEditText(vector = Icons.Filled.Lock, info = "请输入新的密码", onChange = { dialogNewPassword = it })
            Row(
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(
                    modifier = Modifier
                        .clickable( onClick = showDialog )
                        .padding(top = 10.dp, end = 20.dp, bottom = 10.dp),
                    text = "取消"
                )
                Text(
                    modifier = Modifier
                        .clickable( onClick =  {

                        })
                        .padding(top = 10.dp, end = 20.dp, bottom = 10.dp),
                    text = "确认"
                )
            }
        }

    }
}