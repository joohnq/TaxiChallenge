package com.joohhq.taxichallenge.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joohhq.taxichallenge.entities.Option

@Composable
fun DetailBottomSheet(
    isLoading: Boolean,
    options: List<Option>,
    onClick: (Option) -> Unit
) {
    var selectedOption by remember { mutableStateOf<Int?>(null) }

    LazyColumn(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        if (options.isEmpty()) {
            item {
                EmptyBox()
            }
        } else {
            itemsIndexed(options) { i, option ->
                DriverCard(driver = option, isLoading = isLoading && selectedOption == i) {
                    selectedOption = i
                    onClick(option)
                }
            }
        }
    }
}