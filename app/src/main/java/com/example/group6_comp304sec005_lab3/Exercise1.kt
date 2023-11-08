package com.example.group6_comp304sec005_lab3

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.*

import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowManager

class Exercise1 : AppCompatActivity() {

    private var reusableImageView: ImageView? = null

    // variables for coordinates of line movement
    private var startx = 10
    private var starty = 10
    private var endx = 10
    private var endy = 10

    // properties for x and y coordinates
    private var xCoordText: TextView? = null
    private var yCoordText: TextView? = null

    // canvas variables
    private var paint: Paint? = null
    private lateinit var bitmap: Bitmap
    private lateinit var canvas: Canvas

    // line properties
    private var lineColor = ""
    private val strokeWidth = 20

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise1)

        // create paint for the drawing
        paint = Paint()
        paint!!.strokeWidth = strokeWidth.toFloat()

        var screenHeight = 0
        var screenWidth = 0

        val windowManager = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val metrics = DisplayMetrics()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics = windowManager.currentWindowMetrics
            val bounds = windowMetrics.bounds
            screenWidth = bounds.width()
            screenHeight = bounds.height()
        } else {
            windowManager.defaultDisplay.getMetrics(metrics)
            screenHeight = metrics.heightPixels
            screenWidth = metrics.widthPixels
        }

        bitmap = Bitmap.createBitmap(screenWidth, screenHeight, Bitmap.Config.ARGB_8888)

        // tell canvas about the content view
        canvas = Canvas(bitmap)
        // set the background for your drawings
        canvas.drawColor(Color.WHITE)

        // setup the image view
        reusableImageView = findViewById<View>(R.id.imageViewForDrawing) as ImageView

        // tell the image view the bitmap format/content
        reusableImageView!!.setImageBitmap(bitmap)
        reusableImageView!!.visibility = View.VISIBLE
        xCoordText = findViewById<View>(R.id.xCoordEditText) as TextView
        yCoordText = findViewById<View>(R.id.yCoordEditText) as TextView

        // setup line color selection
        setupLineColor()
    }

    fun clearCanvas(v: View?) {
        // reset the background to remove the previous drawing
        canvas.drawColor(Color.WHITE)

        // reset the coordinates; default to 10 since on 0 the line is not viewable on the sides
        startx = 10
        starty = 10
        endx = 10
        endy = 10

        // reset the edit text
        xCoordText!!.text = ""
        yCoordText!!.text = ""
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // Handle back button press by finishing the current activity
            finish()
            return true
        }

        if (!isArrowKeys(keyCode)) {
            return super.onKeyDown(keyCode, event)
        }

        checkLineThickness()

        if (lineColor == "" || lineColor.isEmpty()) {
            Toast.makeText(applicationContext, "Please select line color", Toast.LENGTH_SHORT).show()
            return false
        }

        when (keyCode) {
            KeyEvent.KEYCODE_DPAD_DOWN -> {
                processLineMovement(Line.DOWN)
                return true
            }
            KeyEvent.KEYCODE_DPAD_UP -> {
                processLineMovement(Line.UP)
                return true
            }
            KeyEvent.KEYCODE_DPAD_RIGHT -> {
                processLineMovement(Line.RIGHT)
                return true
            }
            KeyEvent.KEYCODE_DPAD_LEFT -> {
                processLineMovement(Line.LEFT)
                return true
            }
        }
        return false
    }

    fun moveDown(view: View?) {
        processLineMovement(Line.DOWN)
    }

    fun moveUp(view: View?) {
        processLineMovement(Line.UP)
    }

    fun moveRight(view: View?) {
        processLineMovement(Line.RIGHT)
    }

    fun moveLeft(view: View?) {
        processLineMovement(Line.LEFT)
    }

    private fun isArrowKeys(keyCode: Int): Boolean {
        return keyCode == KeyEvent.KEYCODE_DPAD_DOWN || keyCode == KeyEvent.KEYCODE_DPAD_UP ||
                keyCode == KeyEvent.KEYCODE_DPAD_RIGHT || keyCode == KeyEvent.KEYCODE_DPAD_LEFT
    }

    private fun checkLineThickness() {
        val lineThicknessSpinner = findViewById<Spinner>(R.id.lineThicknessSpinner)
        val selectedItemPosition = lineThicknessSpinner.selectedItemPosition
        val strokeWidthValue = selectedItemPosition.toString().toInt()

        if (strokeWidth != strokeWidthValue) {
            paint!!.strokeWidth = strokeWidthValue.toFloat()
        }
    }

    private fun processLineMovement(lineMovement: Line) {
        checkLineThickness()

        val rbRed = findViewById<RadioButton>(R.id.redRadioBtn)
        val rbGreen = findViewById<RadioButton>(R.id.greenRadioBtn)
        val rbYellow = findViewById<RadioButton>(R.id.yellowRadioBtn)

        if(!(rbRed.isChecked || rbGreen.isChecked || rbYellow.isChecked) ) return

        val xCoordString = xCoordText!!.text.toString()
        val yCoordString = yCoordText!!.text.toString()

        if (!xCoordString.isEmpty()) {
            val xFromEditView = xCoordText!!.text.toString().toInt()
            if (xFromEditView <= 10) {
                startx = 10
                endx = 10
            }
            if (xFromEditView != startx) {
                startx = xFromEditView
                endx = xFromEditView
            }
        }

        if (!yCoordString.isEmpty()) {
            val yFromEditView = yCoordText!!.text.toString().toInt()
            if (yFromEditView <= 10) {
                starty = 10
                endy = 10
            }
            if (yFromEditView != starty) {
                starty = yFromEditView
                endy = yFromEditView
            }
        }

        when (lineMovement) {
            Line.DOWN -> {
                endy += 5
                drawLine(canvas)
            }
            Line.UP -> {
                val endyMinus = endy - 5
                endy = if (endy <= 10) {
                    10
                } else {
                    endyMinus
                }
                drawLine(canvas)
            }
            Line.RIGHT -> {
                endx += 5
                drawLine(canvas)
            }
            Line.LEFT -> {
                val endxMinus = endx - 5
                endx = if (endxMinus <= 10) {
                    10
                } else {
                    endx - 5
                }
                drawLine(canvas)
            }
        }
    }

    private fun drawLine(canvas: Canvas?) {
        xCoordText!!.text = endx.toString()
        yCoordText!!.text = endy.toString()
        canvas!!.drawLine(
            startx.toFloat(), starty.toFloat(), endx.toFloat(), endy.toFloat(),
            paint!!
        )
        startx = endx
        starty = endy
        reusableImageView!!.isFocusable = true
        reusableImageView!!.requestFocus()
        reusableImageView!!.invalidate()
    }

    private fun setupLineColor() {
        val lineColorRadioGrp = findViewById<RadioGroup>(R.id.lineColorRbGrp)
        lineColorRadioGrp.setOnCheckedChangeListener { group, checkedId ->
            val rbRed = findViewById<RadioButton>(R.id.redRadioBtn)
            val rbGreen = findViewById<RadioButton>(R.id.greenRadioBtn)

            if (rbRed.isChecked) {
                lineColor = "RED"
                paint!!.color = Color.RED
            } else if (rbGreen.isChecked) {
                lineColor = "GREEN"
                paint!!.color = Color.GREEN
            } else {
                lineColor = "YELLOW"
                paint!!.color = Color.YELLOW
            }
        }
    }
}
