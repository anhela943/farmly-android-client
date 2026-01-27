package com.example.proba.activity.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.proba.R

@Composable
fun LoginScreen(
    onLoginClick: () -> Unit = {},
    onSignUpClick: () -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.splash3),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize().offset(y = (-270).dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(
                    color = colorResource(R.color.background),
                    shape = RoundedCornerShape(topStart = 90.dp, topEnd = 90.dp)
                )
                .padding(24.dp)
        ) {
            Text(
                text = "Login",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2F6B2F),
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = androidx.compose.ui.text.TextStyle(
                    shadow = Shadow(
                        color = Color.Black.copy(alpha = 0.6f),
                        offset = Offset(4f, 4f),
                        blurRadius = 6f
                    )
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = "Don't have an account? ",
                    fontSize = 13.sp,
                    color = Color.Gray
                )
                Text(
                    text = "Sign Up",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2F6B2F),
                    modifier = Modifier.clickable { onSignUpClick() }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            SignUpInput(label = "Email", defaultValue = "")
            SignUpInput(label = "Password", defaultValue = "", isPassword = true)

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                var remember by remember { mutableStateOf(false) }
                Checkbox(
                    checked = remember,
                    onCheckedChange = { remember = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color(0xFF2F6B2F)
                    )
                )
                Text("Remember me", fontSize = 12.sp)
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Forgot Password?",
                    fontSize = 12.sp,
                    color = colorResource(R.color.darkGreenTxt),
                    modifier = Modifier.clickable { }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onLoginClick,
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2F6B2F),
                    contentColor = Color.White
                )
            ) {
                Text("Log In")
            }
        }

        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(10.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.arrow),
                contentDescription = "Back",
                modifier = Modifier
                    .size(32.dp)
                    .rotate(180f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    MaterialTheme {
        LoginScreen(
            onLoginClick = { },
            onSignUpClick = { }
        )
    }
}
