package com.jihan.app.presentation.screens.components
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.animation.with
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun MyButton(
    text: String = "Login",
    modifier: Modifier = Modifier.fillMaxWidth(),
    enabled: Boolean = true,
    elevation: Dp = 8.dp,
    cornerRadius: Int = 10,
    showProgress: Boolean = false,
    onClick: () -> Unit,
) {
    ElevatedButton(
        onClick = onClick,
        modifier = modifier,
        elevation = ButtonDefaults.elevatedButtonElevation(elevation),
        shape = RoundedCornerShape(cornerRadius),
        enabled = enabled && !showProgress,
        colors = ButtonDefaults.elevatedButtonColors(
            contentColor = MaterialTheme.colorScheme.onSurface,
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    ) {
        AnimatedContent(
            targetState = showProgress,
            transitionSpec = {
                fadeIn(animationSpec = tween(300)) + scaleIn() togetherWith
                        fadeOut(animationSpec = tween(300)) + scaleOut()
            }, label = ""
        ) { targetState ->
            if (targetState) {
                OrbitLoading(Modifier.size(40.dp))
            } else {
                Text(text)
            }
        }
    }
}
