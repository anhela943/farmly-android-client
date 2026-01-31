package com.example.proba.activity.product

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proba.R

@Composable
fun ProductPageView(
    productName: String,
    price: Double,
    producerName: String,
    review: Double,
    city: String,
    @DrawableRes imageProduct: Int,
    @DrawableRes imageProducer: Int,
    isFavorite: Boolean,
    onBackClick: () -> Unit,
    onProducerClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    onContactProducerClick: () -> Unit
){
    Image(
        painter = painterResource(R.drawable.backgorund),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer {
                alpha = 0.25f
            }
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .align(Alignment.TopStart)
                .size(30.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.arrow),
                contentDescription = "Back",
                modifier = Modifier
                    .size(24.dp)
                    .rotate(180f)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.85f)
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(topStart = 100.dp, topEnd = 100.dp))
                .background(colorResource(R.color.greenBackground)),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(110.dp))

            Text(
                text = productName,
                fontSize = 35.sp,
                fontWeight = FontWeight.Medium,
                color = colorResource(R.color.black)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = price.toString() + " din",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.black)
                )

                IconButton(
                    onClick = onFavoriteClick,
                    modifier = Modifier
                        .size(30.dp)
                        .clip(RoundedCornerShape(10.dp))
                ) {
                    Image(
                        painter = painterResource(
                            if (isFavorite) R.drawable.heart else R.drawable.favorite
                        ),
                        contentDescription = "Favorite",
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onProducerClick,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(50.dp))
                        .border(3.dp, colorResource(R.color.grey), CircleShape)
                ) {
                    Image(
                        painter = painterResource(imageProducer),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Column {
                    Text(
                        text = producerName,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.black)
                    )
                    Spacer(modifier = Modifier.height(7.dp))

                    Row{
                        Image(
                            painter = painterResource(R.drawable.star),
                            contentDescription = "Star",
                            modifier = Modifier.size(15.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = "$review",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(R.color.black)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(110.dp))
                Text(
                    text = city,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.black)
                )
            }

            Column(modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "citysaxfvdcsgbfdvhbngvhnghbsfvhsfhbnsgvfdnhbgdvnadnavb" +
                            "n dsvfgnsdgfvhbadf bdf bfd esdfrb hasdfghbn rf bferbh",
                    fontSize = 20.sp,
                    color = colorResource(R.color.grey)
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "Orders are delivered every day",
                    fontSize = 15.sp,
                    color = colorResource(R.color.grey),
                    modifier = Modifier.padding(10.dp)
                )
                Button(
                    onClick = onContactProducerClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.darkGreenTxt)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Contact producer",
                        color = Color.White,
                        fontSize = 18.sp
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .width(220.dp)
                .aspectRatio(1f)
                .clip(RoundedCornerShape(16.dp))
                .border(
                    width = 4.dp,
                    color = colorResource(R.color.darkGreenTxt),
                    shape = RoundedCornerShape(16.dp)
                )
                .align(Alignment.TopCenter)
        ) {
            Image(
                painter = painterResource(imageProduct),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ProductPageViewReview(){
    ProductPageView(
        productName = "Sljive",
        price = 230.0,
        producerName = "Milena Petrovic",
        review = 2.3,
        city = "Nis",
        imageProduct = R.drawable.basket,
        imageProducer = R.drawable.user,
        isFavorite = false,
        onBackClick = { },
        onProducerClick = { },
        onFavoriteClick = { },
        onContactProducerClick = { }
    )
}
