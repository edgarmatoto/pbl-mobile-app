package com.example.pblmobile.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pblmobile.ui.theme.PblMobileTheme

@Composable
fun OnboardingContent(onboardingModel: OnboardingModel) {
    Column(Modifier.fillMaxSize().padding(horizontal = 24.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(onboardingModel.image),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.size(18.dp))

        Text(
            text = onboardingModel.title,
            modifier = Modifier.fillMaxWidth(),
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.size(5.dp))

        Text(
            text = onboardingModel.description,
            modifier = Modifier.fillMaxWidth(),
            fontWeight = FontWeight.Normal,
            color = Color.Black,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingContentFirstPagePreview() {
    PblMobileTheme {
        OnboardingContent(OnboardingModel.FirstPage)
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingContentSecondPagePreview() {
    PblMobileTheme {
        OnboardingContent(OnboardingModel.SecondPage)
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingContentThirdPagePreview() {
    PblMobileTheme {
        OnboardingContent(OnboardingModel.ThirdPage)
    }
}