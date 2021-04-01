package `in`.simbu.toasterlibrary

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.*

class ToasterMessage {
    companion object {
        fun s(c: Context, message: String) {
            Toast.makeText(c, message, Toast.LENGTH_SHORT).show()
        }

        fun saveImage(itemImage: View, activity: Activity) {
            val fileName: String
            val imageFromView = getBitmapFromView(itemImage)

            ByteArrayOutputStream().apply {
                imageFromView.compress(Bitmap.CompressFormat.JPEG, 100, this)
                fileName = UUID.nameUUIDFromBytes(this.toByteArray()).toString().replace("-", "")
            }

            val imageFile =  File("${activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)}/ChatOut/$fileName.jpg/")

            if (!imageFile.exists()) {

                val contentResolver = ContentValues().apply {
                    put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis())
                    put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                    put(MediaStore.Images.Media.DATA, imageFile.absolutePath)
                }

                activity.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentResolver).apply {
                    imageFromView.compress(Bitmap.CompressFormat.JPEG, 100, activity.contentResolver.openOutputStream(this!!))
                }


                Toast.makeText(activity, "saved", Toast.LENGTH_SHORT).show()
            } else
                Toast.makeText(activity, "Already saved", Toast.LENGTH_SHORT).show()
        }

        fun getBitmapFromView(view: View): Bitmap {
            return Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888).apply {
                Canvas(this).apply {
                    view.draw(this)
                }
            }
        }
    }






}