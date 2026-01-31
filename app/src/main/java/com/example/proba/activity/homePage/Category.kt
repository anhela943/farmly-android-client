package com.example.proba.activity.homePage

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
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
fun CategoryView(
    name: String,
    @DrawableRes imageCategory: Int,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .height(56.dp)
            .wrapContentWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(34.dp),
        border = BorderStroke(
            2.dp,
            Brush.verticalGradient(
                listOf(
                    colorResource(R.color.greenBackground),
                    colorResource(R.color.lightGreen)
                )
            )
        ),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Row(
            modifier = Modifier
                .fillMaxHeight() // 👈 OVO JE KLJUČ
                .background(
                    Brush.verticalGradient(
                        listOf(
                            colorResource(R.color.greenFilter),
                            colorResource(R.color.darkGreenTxt)
                        )
                    )
                )
                .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(imageCategory),
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = name,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun CategoryViewPreview() {
    CategoryView(name = "Dairy",imageCategory = R.drawable.logo)
}
