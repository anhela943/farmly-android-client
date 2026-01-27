package com.example.proba.activity

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proba.R

class Category {

}

@Composable
fun CategoryView(name: String) {
    Card(
        modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight()
            .padding(16.dp),
        shape = RoundedCornerShape(46.dp),
        border = BorderStroke(
            2.dp,
            Brush.verticalGradient(
                listOf(
                    colorResource(R.color.greenBackground),
                    colorResource(R.color.lightGreen)
                )
            )
        ),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(46.dp))
                .background(
                    Brush.verticalGradient(
                        listOf(
                            colorResource(R.color.greenFilter),
                            colorResource(R.color.darkGreenTxt)
                        )
                    )
                )
                .padding(horizontal = 15.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(40.dp))
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_launcher_background),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Text(
                text = name,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.black)
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun CategoryViewPreview() {
    CategoryView(name = "Dairy")
}