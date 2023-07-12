package com.example.bookshelf.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieClipSpec
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.bookshelf.R

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    val lottieAnimation by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.book_loading))
    LottieAnimation(
        composition = lottieAnimation,
        iterations = LottieConstants.IterateForever,
        modifier = modifier
    )
}

@Composable
fun ErrorScreen(onRetry: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_cloud_off_24),
            contentDescription = stringResource(
                id = R.string.no_internet
            ),
            modifier = Modifier.size(128.dp)
        )
        Text(text = stringResource(id = R.string.no_internet), fontSize = 20.sp, fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRetry) {
            Text(text = stringResource(id = R.string.retry))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorPreview(){
    ErrorScreen(onRetry = {})
}

@Composable
fun InitialScreen(modifier: Modifier=Modifier){
    val lottieAnimation by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.book_loading))
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        LottieAnimation(
            composition = lottieAnimation,
            clipSpec = LottieClipSpec.Progress(0f,.2f),
            iterations = 1,
            modifier = Modifier.size(300.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = stringResource(id = R.string.search_to_get_results), textAlign = TextAlign.Center)
    }
}