package com.example.todoapp

import android.content.Intent
import android.graphics.*
import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import java.lang.Double.min
import kotlin.math.min


class splashscreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        lateinit var bitmap: Bitmap
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)
        val textview = findViewById<TextView>(R.id.textView)
        val paint = textview.paint
        val width = paint.measureText(textview.text.toString())
        val textShader: Shader = LinearGradient(0f, 0f, width, textview.textSize, intArrayOf(
            Color.parseColor("#A8EDEA"),
            Color.parseColor("#FED6E3"),
            /*Color.parseColor("#64B678"),
            Color.parseColor("#478AEA"),*/
            Color.parseColor("#8446CC")
        ), null, Shader.TileMode.REPEAT)

        textview.paint.shader = textShader
//        findViewById<ImageView>(R.id.imageView).setLayerPaint()

        val bitmapResourceID: Int = R.drawable.gradient
        val imageView = findViewById<ImageView>(R.id.imageView)
        imageView.setImageBitmap(BitmapFactory.decodeResource(resources, bitmapResourceID))
        bitmap = BitmapFactory.decodeResource(resources, bitmapResourceID)
        // Create a circular bitmap
        bitmap = getCircularBitmap(bitmap)
        imageView.setImageBitmap(bitmap)
        findViewById<ImageView>(R.id.imageView2).setImageBitmap(bitmap)
        findViewById<ImageView>(R.id.imageView3).setImageBitmap(bitmap)
        findViewById<Button>(R.id.getstarted).setOnClickListener {
            startActivity(Intent (this, MainActivity::class.java))
            finish()
        }
    }
    private fun getCircularBitmap(srcBitmap: Bitmap?): Bitmap {
        val squareBitmapWidth = min(srcBitmap!!.width, srcBitmap.height)
        // Initialize a new instance of Bitmap
        // Initialize a new instance of Bitmap
        val dstBitmap = Bitmap.createBitmap(
            squareBitmapWidth,  // Width
            squareBitmapWidth,  // Height
            Bitmap.Config.ARGB_8888 // Config
        )
        val canvas = Canvas(dstBitmap)
        // Initialize a new Paint instance
        // Initialize a new Paint instance
        val paint = Paint()
        paint.isAntiAlias = true
        val rect = Rect(0, 0, squareBitmapWidth, squareBitmapWidth)
        val rectF = RectF(rect)
        canvas.drawOval(rectF, paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        val left = ((squareBitmapWidth - srcBitmap.width) / 2).toFloat()
        val top = ((squareBitmapWidth - srcBitmap.height) / 2).toFloat()
        canvas.drawBitmap(srcBitmap, left, top, paint)
        srcBitmap.recycle()
        return dstBitmap
    }

}