package com.example.nousdigitaltestbyhamza

import android.content.Context
import android.graphics.Bitmap
import java.io.File
import java.io.IOException


class FileUtils {

    companion object {
        @Throws(IOException::class)
        fun createImageFile(applicationContext: Context): File {
            val imagePath: File = applicationContext.filesDir
            val newFile = File(imagePath, "${System.currentTimeMillis()}.jpg")
            return newFile
        }

        @Throws(IOException::class)
        fun Bitmap.writerToFile(file: File) {
            val fileOutputStream = file.outputStream()
            this.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
            fileOutputStream.close()
        }
    }
}