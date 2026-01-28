package com.example.proba.activity.product

import androidx.annotation.DrawableRes
import com.example.proba.R
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
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
    city: String,
    @DrawableRes imageProducer: Int,
    @DrawableRes imageProduct: Int,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.wrapContentHeight(),
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
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(16.dp))
            ) {
                Image(
                    painter = painterResource(imageProduct),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = productName,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.darkGreenTxt)
                    )
                    Text(
                        text = "${price} RSD",
                        fontSize = 13.sp,
                        color = colorResource(R.color.darkGreenTxt)
                    )
                }

                IconButton(
                    onClick = {
                        //
                    },
                    modifier = Modifier.size(30.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.favorite),
                        contentDescription = "Favorite"
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {

                    },
                    modifier = Modifier.size(30.dp)
                ) {
                    Image(
                        painter = painterResource(imageProducer),
                        contentDescription = "Producer"
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = producer,
                        fontSize = 13.sp,
                        color = colorResource(R.color.darkGreenTxt)
                    )
                    Text(
                        text = city,
                        fontSize = 12.sp,
                        color = colorResource(R.color.darkGreenTxt)
                    )
                }

                Text(
                    text = "$producerReview",
                    fontSize = 12.sp,
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
        city = "Novi Sad",
        imageProducer = R.drawable.user,
        imageProduct = R.drawable.basket,
        modifier = Modifier
            .width(200.dp)
            .padding(16.dp)
    )
}
