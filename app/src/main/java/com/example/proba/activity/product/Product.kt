package com.example.proba.activity.product

import com.example.proba.R
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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

class Product{
}


@Composable
fun ProductView(
    productName: String,
    price: Double,
    producer: String,
    producerReview: Double,
    city: String
) {
    Card(
        modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight()
            .padding(16.dp),
        shape = RoundedCornerShape(36.dp),
        border = BorderStroke(
            10.dp,
            Brush.verticalGradient(
                listOf(
                    colorResource(R.color.greenStrokeLight),
                    colorResource(R.color.greenStrokeDark)
                )
            )
        ),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .width(220.dp)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(16.dp))
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_launcher_background),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(90.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = productName,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.darkGreenTxt)
                    )
                    Text(
                        text = "${price} RSD",
                        fontSize = 16.sp,
                        color = colorResource(R.color.darkGreenTxt)
                    )
                }

                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.LightGray)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.Gray)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Column {
                    Text(
                        text = producer,
                        fontSize = 16.sp,
                        color = colorResource(R.color.darkGreenTxt)
                    )
                    Text(
                        text = "Ocena: $producerReview",
                        fontSize = 14.sp,
                        color = colorResource(R.color.darkGreenTxt)
                    )
                }

                Spacer(modifier = Modifier.width(38.dp))

                Text(
                    text = city,
                    fontSize = 14.sp,
                    color = colorResource(R.color.darkGreenTxt)
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ProductViewPreview() {
    ProductView(
        productName = "Jabuke",
        price = 120.0,
        producer = "Marko",
        producerReview = 4.5,
        city = "Novi Sad"
    )
}
