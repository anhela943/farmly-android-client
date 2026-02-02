package com.example.proba.activity

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proba.R

@Composable
fun SearchView(
    onSearchChange: (String) -> Unit = {},
    onMenuClick: () -> Unit = {},
    onSearchClick: () -> Unit = {}
) {
    var text by remember { mutableStateOf(TextFieldValue("")) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .imePadding()
            .padding(16.dp),
        shape = RoundedCornerShape(30.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            colorResource(R.color.greenBackground),
                            colorResource(R.color.greenFilter)
                        )
                    )
                )
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Slika korisnika
                IconButton(
                    onClick = {
                        Log.d("SearchView", "MENU kliknut")
                        onMenuClick()
                    },
                    modifier = Modifier.size(30.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.menu),
                        contentDescription = "Menu"
                    )
                }


                Spacer(modifier = Modifier.width(8.dp))

                TextField(
                    value = text,
                    onValueChange = {
                        text = it
                        onSearchChange(it.text)
                    },
                    placeholder = { Text("Search", color = colorResource(R.color.grey)) },
                    singleLine = true,
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedTextColor = colorResource(R.color.black),
                        unfocusedTextColor = colorResource(R.color.black),
                        focusedPlaceholderColor = colorResource(R.color.grey),
                        unfocusedPlaceholderColor = colorResource(R.color.grey),
                        cursorColor = colorResource(R.color.black),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )

                Spacer(modifier = Modifier.width(8.dp))

                IconButton(
                    onClick = {
                        Log.d("SearchView", "MENU SEARCH")
                        onSearchClick()
                    },
                    modifier = Modifier.size(30.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.search),
                        contentDescription = "Search"
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchViewPreview() {
    SearchView(
        onMenuClick = { println("Menu clicked") },
        onSearchClick = { println("Search clicked")
        }
    )
}
