package com.joohhq.taxichallenge.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.joohhq.taxichallenge.R
import com.joohhq.taxichallenge.entities.Option
import com.joohhq.taxichallenge.ui.theme.Colors
import com.joohhq.taxichallenge.ui.theme.TextStyles

@Composable
fun DriverCard(
    isLoading: Boolean,
    driver: Option,
    onClick: () -> Unit,
) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(24.dp),
        colors = CardColors(
            containerColor = Colors.ButtonBg,
            contentColor = Colors.Black,
            disabledContainerColor = Colors.ButtonBg,
            disabledContentColor = Colors.Black,
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = driver.name, style = TextStyles.H2Bold)
                Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                    Icon(
                        painter = painterResource(R.drawable.ic_star),
                        contentDescription = "Star",
                        tint = Colors.Yellow,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = driver.review.rating.toString(),
                        style = TextStyles.BodyMedium
                    )
                }
            }
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(text = driver.vehicle, style = TextStyles.BodyMedium)
                VerticalSpacer(5.dp)
                Text(text = driver.description, style = TextStyles.Body2Regular)
                VerticalSpacer(5.dp)
            }
            VerticalSpacer(15.dp)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp)
                        .background(color = Colors.Primary, shape = RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(40.dp),
                            color = Colors.White
                        )
                    } else {
                        Text(
                            text = stringResource(R.string.select),
                            style = TextStyles.Button,
                            color = Colors.White
                        )
                    }
                }
                Text(text = "$" + driver.value.toString(), style = TextStyles.H3Medium)
            }
        }
    }
}