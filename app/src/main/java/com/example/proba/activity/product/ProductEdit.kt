package com.example.proba.activity.product

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.proba.R
import com.example.proba.activity.bottomBarView

@Composable
fun ProductEditView(
    navController: NavController,
    onBackClick: () -> Unit,
    defaultProductName: String = "Tomatoes",
    defaultPrice: String = "200",
    defaultCity: String = "Niš",
    defaultDescription: String = ""
) {
    var productName by rememberSaveable { mutableStateOf(defaultProductName) }
    var price by rememberSaveable { mutableStateOf(defaultPrice) }
    var city by rememberSaveable { mutableStateOf(defaultCity) }
    var description by rememberSaveable { mutableStateOf(defaultDescription) }

    Scaffold(
        bottomBar = { bottomBarView(navController) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Image(
                painter = painterResource(id = R.drawable.backgorund),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(0.25f),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp)
                    .padding(top = 8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp, bottom = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBackClick) {
                        Image(
                            painter = painterResource(id = R.drawable.arrow),
                            contentDescription = "Back",
                            modifier = Modifier
                                .size(22.dp)
                                .rotate(180f)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                }

                val screenHeight = LocalConfiguration.current.screenHeightDp.dp
                val cardMinHeight = maxOf(screenHeight - 140.dp, 560.dp)

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = cardMinHeight),
                        shape = RoundedCornerShape(topStart = 80.dp, topEnd = 80.dp),
                        elevation = CardDefaults.cardElevation(6.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = colorResource(R.color.greenBackground)
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .verticalScroll(rememberScrollState())
                                .padding(start = 20.dp, end = 20.dp, top = 24.dp, bottom = 96.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Spacer(modifier = Modifier.height(4.dp))

                            ProductEditField(
                                label = "Product Name",
                                value = productName,
                                onValueChange = { productName = it }
                            )

                            ProductEditPriceField(
                                value = price,
                                onValueChange = { price = it }
                            )

                            ProductEditField(
                                label = "City",
                                value = city,
                                onValueChange = { city = it }
                            )

                            ProductEditField(
                                label = "Description",
                                value = description,
                                onValueChange = { description = it },
                                placeholder = "Describe the product",
                                singleLine = false,
                                minLines = 4
                            )

                            ProductEditPictureField()

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                ProductEditButton(
                                    text = "Save product",
                                    containerColor = colorResource(R.color.darkGreenTxt),
                                    contentColor = colorResource(R.color.white),
                                    onClick = { },
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(48.dp)
                                )
                                ProductEditButton(
                                    text = "Delete product",
                                    containerColor = Color(0xFF791515),
                                    contentColor = colorResource(R.color.white),
                                    onClick = { },
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(48.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ProductEditField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String? = null,
    singleLine: Boolean = true,
    minLines: Int = 1
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(R.color.darkGreenTxt)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 6.dp,
                    shape = RoundedCornerShape(12.dp),
                    clip = false
                )
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(12.dp)
                )
        ) {
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                singleLine = singleLine,
                minLines = minLines,
                placeholder = {
                    if (placeholder != null) {
                        Text(text = placeholder, fontSize = 13.sp, color = colorResource(R.color.grey))
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedTextColor = colorResource(R.color.black),
                    unfocusedTextColor = colorResource(R.color.black),
                    cursorColor = colorResource(R.color.darkGreenTxt)
                )
            )
        }
    }
}

@Composable
private fun ProductEditPriceField(
    value: String,
    onValueChange: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Price",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(R.color.darkGreenTxt)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .shadow(
                        elevation = 6.dp,
                        shape = RoundedCornerShape(12.dp),
                        clip = false
                    )
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(12.dp)
                    )
            ) {
                OutlinedTextField(
                    value = value,
                    onValueChange = onValueChange,
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        focusedTextColor = colorResource(R.color.black),
                        unfocusedTextColor = colorResource(R.color.black),
                        cursorColor = colorResource(R.color.darkGreenTxt)
                    )
                )
            }

            Box(
                modifier = Modifier
                    .height(54.dp)
                    .width(64.dp)
                    .shadow(
                        elevation = 6.dp,
                        shape = RoundedCornerShape(12.dp),
                        clip = false
                    )
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "RSD",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(R.color.darkGreenTxt)
                )
            }
        }
    }
}

@Composable
private fun ProductEditPictureField() {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Picture",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(R.color.darkGreenTxt)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .shadow(
                    elevation = 6.dp,
                    shape = RoundedCornerShape(12.dp),
                    clip = false
                )
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(12.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Add Picture",
                fontSize = 14.sp,
                color = colorResource(R.color.grey)
            )
        }
    }
}

@Composable
private fun ProductEditButton(
    text: String,
    containerColor: Color,
    contentColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(13.dp),
        color = containerColor
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = contentColor
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductEditPreview() {
    ProductEditView(
        navController = rememberNavController(),
        onBackClick = {}
    )
}
