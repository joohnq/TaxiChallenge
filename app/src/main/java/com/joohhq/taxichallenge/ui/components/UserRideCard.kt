package com.joohhq.taxichallenge.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joohhq.taxichallenge.utils.DateTimeFormatter
import com.joohhq.taxichallenge.R
import com.joohhq.taxichallenge.data.mapper.formatTo2Decimal
import com.joohhq.taxichallenge.entities.Ride
import com.joohhq.taxichallenge.ui.theme.Colors
import com.joohhq.taxichallenge.ui.theme.TextStyles

@Composable
fun UserRideCard(
    ride: Ride,
) {
    Card(
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
            val annotatedString = buildAnnotatedString {
                append(ride.origin)
                appendInlineContent("arrow", "[arrow]")
                append(ride.destination)
            }
            Text(
                text = annotatedString,
                inlineContent = mapOf(
                    "arrow" to InlineTextContent(
                        placeholder = Placeholder(
                            width = 30.sp,
                            height = 20.sp,
                            placeholderVerticalAlign = PlaceholderVerticalAlign.Center
                        )
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_arrow),
                                contentDescription = "Arrow",
                                modifier = Modifier
                                    .size(20.dp)
                                    .rotate(135f),
                                tint = Colors.Black
                            )
                        }
                    }
                ),
                modifier = Modifier.fillMaxWidth(),
                style = TextStyles.H3Bold
            )
            VerticalSpacer(5.dp)
            Text(
                text = stringResource(R.string.driver, ride.driver.name),
                style = TextStyles.BodyMedium,
                color = Colors.Black
            )
            VerticalSpacer(15.dp)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp)
                        .background(color = Colors.Primary, shape = RoundedCornerShape(16.dp)),
                    horizontalArrangement = Arrangement.spacedBy(
                        10.dp,
                        Alignment.CenterHorizontally
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_clock),
                        contentDescription = "Duration",
                        tint = Colors.White,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = ride.duration,
                        style = TextStyles.Button,
                        color = Colors.White
                    )
                }
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp)
                        .background(color = Colors.Primary, shape = RoundedCornerShape(16.dp)),
                    horizontalArrangement = Arrangement.spacedBy(
                        10.dp,
                        Alignment.CenterHorizontally
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_distance),
                        contentDescription = "Distance",
                        tint = Colors.White,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = ride.distance.formatTo2Decimal(),
                        style = TextStyles.Button,
                        color = Colors.White
                    )
                }
            }
            VerticalSpacer(15.dp)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = DateTimeFormatter(ride.date), style = TextStyles.H3Medium)
                Text(text = "$ " + ride.value.formatTo2Decimal(), style = TextStyles.H3Medium)
            }
        }
    }
}