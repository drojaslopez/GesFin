package com.gesfin.widget.ui.widget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.width
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.gesfin.widget.R
import com.gesfin.widget.core.datastore.SessionDataStore
import com.gesfin.widget.ui.widget.action.RegistrarGastoAction
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class GesfinGastoWidget : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val sessionDataStore = SessionDataStore(context)
        val sessionData = sessionDataStore.sessionData.first()
        val nombreUsuario = if (sessionData.isLoggedIn) sessionData.nombreUsuario else ""
        
        val connectionChecker = ConnectionStatusChecker(context)
        val connectionResult = connectionChecker.checkConnection()

        provideContent {
            WidgetContent(context, nombreUsuario, connectionResult)
        }
    }

    @Composable
    private fun WidgetContent(
        context: Context, 
        nombreUsuario: String,
        connectionResult: ConnectionCheckResult
    ) {
        Column(
            modifier = GlanceModifier
                .fillMaxSize()
                .background(Color.White)
                .padding(12.dp),
            verticalAlignment = Alignment.Vertical.CenterVertically,
            horizontalAlignment = Alignment.Horizontal.CenterHorizontally
        ) {
            Text(
                text = context.getString(R.string.registrar_gasto),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    color = ColorProvider(Color.Black)
                )
            )
            
            Spacer(modifier = GlanceModifier.height(6.dp))
            
            Row(
                modifier = GlanceModifier
                    .fillMaxWidth()
                    .clickable(actionRunCallback<RegistrarGastoAction>())
                    .padding(6.dp),
                verticalAlignment = Alignment.Vertical.CenterVertically,
                horizontalAlignment = Alignment.Horizontal.CenterHorizontally
            ) {
                Image(
                    provider = ImageProvider(R.drawable.ic_launcher_foreground),
                    contentDescription = context.getString(R.string.anadir_gasto)
                )
                Spacer(modifier = GlanceModifier.width(8.dp))
                Text(
                    text = context.getString(R.string.anadir_gasto),
                    style = TextStyle(color = ColorProvider(Color.Black))
                )
            }
            
            if (nombreUsuario.isNotEmpty()) {
                Spacer(modifier = GlanceModifier.height(2.dp))
                Text(
                    text = context.getString(R.string.realizado_por, nombreUsuario),
                    style = TextStyle(
                        color = ColorProvider(Color.Gray),
                        fontWeight = FontWeight.Normal
                    )
                )
            }
            
            Spacer(modifier = GlanceModifier.height(6.dp))
            
            Row(
                modifier = GlanceModifier
                    .fillMaxWidth()
                    .padding(4.dp),
                verticalAlignment = Alignment.Vertical.CenterVertically,
                horizontalAlignment = Alignment.Horizontal.CenterHorizontally
            ) {
                val ledColor = when (connectionResult.status) {
                    ConnectionStatus.CONNECTED -> Color.Green
                    ConnectionStatus.WARNING -> Color.Yellow
                    ConnectionStatus.ERROR -> Color.Red
                    ConnectionStatus.CHECKING -> Color.Gray
                }
                Text(
                    text = "●",
                    style = TextStyle(
                        color = ColorProvider(ledColor),
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = GlanceModifier.width(4.dp))
                Text(
                    text = connectionResult.message,
                    style = TextStyle(
                        color = ColorProvider(Color.Black),
                        fontWeight = FontWeight.Normal
                    ),
                    maxLines = 2
                )
            }
        }
    }
}
