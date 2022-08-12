package com.example.nousdigitaltestbyhamza

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import org.json.JSONObject

class Utils {
    companion object {
        fun parseErrorBody(string: String?): String {
            try {
                val jObjError = JSONObject(string)
                return jObjError.getJSONObject("error").getString("message")
            } catch (e: Exception) {
                return e.message.toString()
            }
        }

        fun composeEmail(
            context: Context,
            addresses: Array<String>,
            title: String,
            body: String,
            attachment: Uri
        ) {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "*/*"
                putExtra(Intent.EXTRA_EMAIL, addresses)
                putExtra(Intent.EXTRA_SUBJECT, title)
                putExtra(Intent.EXTRA_TEXT, body)
                putExtra(Intent.EXTRA_STREAM, attachment)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            if (intent.resolveActivity(context.packageManager) != null) {
                context.startActivity(intent)
            } else {
                Toast.makeText(context, "No email clint found", Toast.LENGTH_LONG).show()
            }
        }

    }

}