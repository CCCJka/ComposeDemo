package com.jsl.Example.ui.component

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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jsl.Example.R

var userName = ""
var registerAccount = ""
var pwd = ""
var confirmPwd = ""
var phoneNumber = ""
var verificationCode = ""
var email = ""

@Composable
fun registerView(exit: () -> Unit, getCode: () -> Unit, register: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.login_background_color))
    ) {
        Icon(
            modifier = Modifier
                .padding(top = 10.dp)
                .size(40.dp, 40.dp)
                .clickable(onClick = exit),
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = null)
        Text(
            modifier = Modifier
                .padding(bottom = 5.dp)
                .align(Alignment.CenterHorizontally),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            text = "用户注册"
        )
        Box(
            modifier = Modifier
                .size(300.dp, 450.dp)
                .align(Alignment.CenterHorizontally)
                .background(Color.White, RoundedCornerShape(20.dp))
        ){
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.align(Alignment.Center)
            ) {
                iconEditText(vector = Icons.Filled.Person, info = "设置用户名", onChange = { userName = it })
                iconEditText(vector = Icons.Filled.AccountBox, info = "设置账号", onChange = { registerAccount = it })
                iconEditText(vector = Icons.Filled.Lock, info = "设置密码(6~15位)", onChange = { pwd = it })
                iconEditText(vector = Icons.Filled.Lock, info = "确认密码", onChange = { confirmPwd = it })
                iconEditText(vector = Icons.Filled.Phone, info = "手机号", onChange = { phoneNumber = it })
                Box {
                    iconEditText(vector = Icons.Filled.Info, info = "验证码", onChange = { verificationCode = it })
                    Text(
                        modifier = Modifier
                            .size(80.dp, 30.dp)
                            .align(Alignment.CenterEnd)
                            .background(
                                colorResource(id = R.color.button_color),
                                RoundedCornerShape(10.dp)
                            )
                            .padding(top = 4.dp)
                            .clickable(onClick = getCode),
                        fontSize = 12.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        text = "获取验证码"
                    )
                }
                iconEditText(vector = Icons.Filled.Email, info = "邮箱(选填)", onChange = { email = it })

                Row(
                    modifier = Modifier
                        .background(
                            colorResource(id = R.color.button_color),
                            RoundedCornerShape(30.dp)
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        modifier = Modifier
                            .size(250.dp, 40.dp)
                            .clickable(onClick = register)
                            .padding(10.dp),
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        text = "注册"
                    )
                }
            }
        }
    }

}