package com.samcab.basiccameraapp.utility

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.File
import java.io.FileInputStream

class AppUtils {
    companion object {

        fun getFileListInFolder(context : Context, childPath: String): Array<File> {
            val path = File(context.filesDir, childPath)
            if (!path.exists()) path.mkdirs()
            return File(path.path).listFiles()!!
        }

        fun getBitmap(path: String): Bitmap? {
            var bitmap: Bitmap? = null
            try {
                val f = File(path)
                val options = BitmapFactory.Options()
                options.inPreferredConfig = Bitmap.Config.ARGB_8888
                bitmap = BitmapFactory.decodeStream(FileInputStream(f), null, options)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return bitmap
        }
    }
}