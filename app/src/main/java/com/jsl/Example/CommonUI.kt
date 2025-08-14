package com.jsl.Example

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp

@Composable
fun iconEditText(vector: ImageVector, info: String, onChange: (String) -> Unit){
    var value by remember { mutableStateOf("") }
    var hasFocus by remember { mutableStateOf(false) }
    Row (
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(10.dp)
            .background(colorResource(id = R.color.button_background), RoundedCornerShape(10.dp))
            .border(BorderStroke(1.dp, Color.Black), RoundedCornerShape(10.dp))
    ){
        BasicTextField(
            modifier = Modifier
                .size(250.dp, 30.dp)
                .align(Alignment.CenterVertically)
                .padding(start = 5.dp)
                .onFocusChanged { hasFocus = it.isFocused },
            value = value,
            onValueChange = {
                value = it
                onChange(it)
            },
            singleLine = true,
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = vector, contentDescription = null)
                    Spacer(modifier = Modifier.width(10.dp))
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .align(Alignment.CenterVertically)
                    ) {
                        // 当空字符时, 显示hint
                        if (value.isEmpty())
                            Text(
                                modifier = Modifier.align(Alignment.CenterStart),
                                text = info,
                                color = Color.Gray
                            )
                        // 原本输入框的内容
                        innerTextField()
                    }
                    // 存在焦点 且 有输入内容时. 显示叉号
                    if(hasFocus && value.isNotEmpty()) {
                        Icon(imageVector = Icons.Filled.Clear, // 清除图标
                            contentDescription = null,
                            // 点击就清空text
                            modifier = Modifier
                                .clickable { value = "" }
                        )
                    }
                }

            }
        )
    }
}