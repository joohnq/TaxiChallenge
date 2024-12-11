package com.joohhq.taxichallenge.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.joohhq.taxichallenge.ui.theme.Colors
import com.joohhq.taxichallenge.ui.theme.TextStyles

@Composable
fun TextFieldWithLabel(
    text: String,
    placeholder: String,
    value: String,
    error: String? = null,
    imeAction: ImeAction = ImeAction.Next,
    onValueChange: (String) -> Unit
) {
    Column {
        Text(
            text = text,
            style = TextStyles.BodyMedium
        )
        VerticalSpacer(5.dp)
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            shape = RoundedCornerShape(16.dp),
            isError = error != null,
            singleLine = true,
            placeholder = {
                Text(text = placeholder, style = TextStyles.BodyRegular)
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = imeAction
            ),
            colors = OutlinedTextFieldDefaults.colors(
                disabledBorderColor = Colors.BG1,
                errorBorderColor = Colors.Red,
                focusedBorderColor = Colors.Primary,
                unfocusedBorderColor = Colors.BG1,
                focusedContainerColor = Colors.Gray4,
                errorContainerColor = Colors.Gray4,
                disabledContainerColor = Colors.Gray4,
                unfocusedContainerColor = Colors.Gray4,
                errorLeadingIconColor = Colors.Gray3,
                disabledLeadingIconColor = Colors.Gray3,
                focusedLeadingIconColor = Colors.Gray3,
                unfocusedLeadingIconColor = Colors.Gray3,
                errorPlaceholderColor = Colors.Red,
                disabledPlaceholderColor = Colors.Gray3,
                focusedPlaceholderColor = Colors.Black,
                unfocusedPlaceholderColor = Colors.Gray3
            ),
            textStyle = TextStyles.BodyRegular,
            value = value,
            onValueChange = onValueChange,
        )
        VerticalSpacer(5.dp)
        error?.let {
            Text(
                text = it,
                style = TextStyles.BodyRegular,
                color = Colors.Red
            )
        }
    }
}

@Composable
fun DropDownWithLabel(
    text: String,
    value: String,
    onClick: (String) -> Unit
) {
    var isDropDownExpanded by remember { mutableStateOf(false) }

    Column {
        Text(
            text = text,
            style = TextStyles.BodyMedium
        )
        VerticalSpacer(5.dp)
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .clickable { isDropDownExpanded = true }
            .background(color = Colors.Gray4, shape = RoundedCornerShape(16.dp))
            .padding(horizontal = 10.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(text = value, style = TextStyles.BodyRegular)
        }
        DropdownMenu(
            containerColor = Colors.Gray4,
            modifier = Modifier.fillMaxWidth(),
            expanded = isDropDownExpanded,
            onDismissRequest = {
                isDropDownExpanded = false
            }) {
            for (i in 1..3) {
                DropdownMenuItem(
                    colors = MenuItemColors(
                        textColor = Colors.Black,
                        disabledTextColor = Colors.Black,
                        leadingIconColor = Color.Unspecified,
                        trailingIconColor = Color.Unspecified,
                        disabledLeadingIconColor = Color.Unspecified,
                        disabledTrailingIconColor = Color.Unspecified
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    text = { Text(text = i.toString(), style = TextStyles.BodyRegular) },
                    onClick = { onClick(i.toString()) }
                )
            }
        }
        VerticalSpacer(5.dp)
    }
}