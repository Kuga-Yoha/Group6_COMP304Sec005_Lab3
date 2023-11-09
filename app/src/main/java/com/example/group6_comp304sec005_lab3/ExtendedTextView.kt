package com.example.group6_comp304sec005_lab3

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import java.text.SimpleDateFormat
import java.util.*
import androidx.core.content.res.use

class ExtendedTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {
    private val longDateFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())

    var longDate: Long = 0
        set(value) {
            field = value
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = value
            val formattedDate = longDateFormat.format(calendar.time)
            text = formattedDate
        }

    // Constructor used when inflating the View from XML when it has a
    // style attribute
    // These three constructors are required for all Views
    init {
        //read the custom attributes in your class by using
        // the obtainStyledAttributes method:
        val a = context.obtainStyledAttributes(
            attrs,
            R.styleable.ExtendedTextView,  // The <declare-styleable> name
            defStyleAttr,
            0
        ) // An optional R.style to use for default values
        /* if (a.hasValue(R.styleable.ExtendedTextView_date)) {
             longDate = a.ge(R.styleable.ExtendedTextView_date, 0f) // default value
         }*/

        a.recycle() //recycles the TypedArray
    }
}
