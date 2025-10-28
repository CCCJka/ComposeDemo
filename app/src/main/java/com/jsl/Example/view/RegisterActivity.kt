package com.jsl.Example.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.jsl.Example.db.DbHelper
import com.jsl.Example.db.bean.UserBean
import com.jsl.Example.ui.component.confirmPwd
import com.jsl.Example.ui.component.email
import com.jsl.Example.ui.component.phoneNumber
import com.jsl.Example.ui.component.pwd
import com.jsl.Example.ui.component.registerAccount
import com.jsl.Example.ui.component.registerView
import com.jsl.Example.ui.component.userName
import com.jsl.Example.ui.component.verificationCode
import com.jsl.Example.ui.theme.ExampleTheme

class RegisterActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExampleTheme {
                Surface {
                    registerView(
                        exit = {
                            finish()
                        },
                        getCode = {

                        },
                        register = {
                            if (pwd.equals(confirmPwd)) {
                                val userBean = UserBean(userName, registerAccount, pwd, email, phoneNumber)
                                DbHelper.get().saveUser(userBean)
                            } else {
                                Toast.makeText(this, "注册信息有误，请检查", Toast.LENGTH_LONG)
                            }
                        })
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun preRegisterView(){
    ExampleTheme {
        registerView(exit = {  }, getCode = {  }, register = {  })
    }
}