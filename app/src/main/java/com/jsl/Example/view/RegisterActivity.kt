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
                            val userList = DbHelper.get().getUserList()
                            var existUser = false
                            for (item in userList){
                                if (item.account.equals(registerAccount)){
                                    existUser = true
                                    break
                                }
                            }
                            if (!existUser){
                                if (pwd.equals(confirmPwd)) {
                                    val userBean = UserBean(userName, registerAccount, pwd, email, phoneNumber)
                                    DbHelper.get().saveUser(userBean)
                                    Toast.makeText(this, "用户注册成功", Toast.LENGTH_LONG).show()
                                } else {
                                    Toast.makeText(this, "注册密码不同，请检查", Toast.LENGTH_LONG).show()
                                }
                            } else{
                                Toast.makeText(this, "账号已存在，请检查", Toast.LENGTH_LONG).show()
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