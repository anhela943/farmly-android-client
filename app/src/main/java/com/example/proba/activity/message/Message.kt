package com.example.proba.activity.message

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proba.R

@Composable
fun messageView(
    producer: String,
    lastMssg: String,
    timeMssg: Int,
    @DrawableRes imageUser: Int
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(R.color.greenSrLight))
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(30.dp))
                        .border(3.dp, colorResource(R.color.grey), CircleShape)
                ) {
                    Image(
                        painter = painterResource(imageUser),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Column {
                    Text(
                        text = producer,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.black)
                    )
                    Row() {
                        Text(
                            text = lastMssg + "    -",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(R.color.grey)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = timeMssg.toString(),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(R.color.grey)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun messageViewReview(){
    messageView(
        producer = "Milena",
        lastMssg = "Hvala na ogovoru",
        timeMssg = 5,
        imageUser = R.drawable.user
    )
}