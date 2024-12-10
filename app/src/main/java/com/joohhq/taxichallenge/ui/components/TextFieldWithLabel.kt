package com.joohhq.taxichallenge.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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