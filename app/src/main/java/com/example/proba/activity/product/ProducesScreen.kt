package com.example.proba.activity.product

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proba.R
import com.example.proba.activity.SearchView
import com.example.proba.activity.bottomBarView

@Composable
fun ProducesScreenView() {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.backgorund),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    alpha = 0.5f
                },
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 100.dp)
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        // TODO: Navigate back
                    },
                    modifier = Modifier.size(30.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.arrow),
                        contentDescription = "Back",
                        modifier = Modifier
                            .rotate(180f)
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Box(modifier = Modifier.weight(1f)) {
                    SearchView(
                        onMenuClick = { /* TODO */ },
                        onSearchClick = { /* TODO */ },
                        onSearchChange = { query -> /* TODO */ }
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Products",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.darkGreenTxt),
                modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ProductView(
                        productName = "Tomatoes",
                        price = 200.0,
                        producer = "Proizvodjac",
                        producerReview = 4.5,
                        city = "Niš",
                        imageProducer = R.drawable.user,
                        imageProduct = R.drawable.basket,
                        modifier = Modifier.weight(1f)
                    )
                    ProductView(
                        productName = "Potatoes",
                        price = 220.0,
                        producer = "Proizvodjac",
                        producerReview = 4.5,
                        city = "Niš",
                        imageProducer = R.drawable.user,
                        imageProduct = R.drawable.basket,
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ProductView(
                        productName = "Strawberry",
                        price = 250.0,
                        producer = "Proizvodjac",
                        producerReview = 4.5,
                        city = "Niš",
                        imageProducer = R.drawable.user,
                        imageProduct = R.drawable.basket,
                        modifier = Modifier.weight(1f)
                    )
                    ProductView(
                        productName = "Plum",
                        price = 300.0,
                        producer = "Proizvodjac",
                        producerReview = 4.5,
                        city = "Niš",
                        imageProducer = R.drawable.user,
                        imageProduct = R.drawable.basket,
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ProductView(
                        productName = "Potatoes",
                        price = 220.0,
                        producer = "Proizvodjac",
                        producerReview = 4.5,
                        city = "Niš",
                        imageProducer = R.drawable.user,
                        imageProduct = R.drawable.basket,
                        modifier = Modifier.weight(1f)
                    )
                    ProductView(
                        productName = "Strawberry",
                        price = 250.0,
                        producer = "Proizvodjac",
                        producerReview = 4.5,
                        city = "Niš",
                        imageProducer = R.drawable.user,
                        imageProduct = R.drawable.basket,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        Column(
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            bottomBarView()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProducesScreenViewPreview() {
    ProducesScreenView()
}
