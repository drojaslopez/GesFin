package com.gesfin.widget.ui.widget.action

import android.content.Context
import android.content.Intent
import androidx.glance.GlanceId
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.action.ActionCallback
import com.gesfin.widget.core.datastore.SessionDataStore
import com.gesfin.widget.ui.gasto.RegistrarGastoActivity
import com.gesfin.widget.ui.login.LoginActivity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class RegistrarGastoAction : ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        val sessionDataStore = SessionDataStore(context)
        val sessionData = sessionDataStore.sessionData.first()
        
        if (!sessionData.isLoggedIn) {
            val intent = Intent(context, LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            }
            context.startActivity(intent)
            return
        }
        
        val intent = Intent(context, RegistrarGastoActivity::class.java).apply {
            putExtra("appWidgetId", glanceId.toString())
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        context.startActivity(intent)
    }
}
