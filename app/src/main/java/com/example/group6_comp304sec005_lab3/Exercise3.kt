package com.example.group6_comp304sec005_lab3

import android.animation.ObjectAnimator
import android.graphics.Path
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView

class Exercise3 : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise3)



        val earthImageView: ImageView = findViewById(R.id.ImageViewForTweeningEarth)
        val sunImageView: ImageView = findViewById(R.id.ImageViewForTweeningsun)

        // Handle Fade Button
        val rotateButton: Button = findViewById(R.id.ButtonAlpha)
        earthImageView.pivotX = sunImageView.x + sunImageView.width / 2
        earthImageView.pivotY = sunImageView.y + sunImageView.height / 2
        val path = Path().apply {
            addCircle(sunImageView.x, sunImageView.y, 450f, Path.Direction.CW)
        }
//        val pathInterpolator = PathInterpolatorCompat.create(path)
        rotateButton.setOnClickListener {
            /*  val animator = ObjectAnimator.ofFloat(earthImageView, "rotation", 0f, 360f)
              animator.duration = 1000
              animator.repeatCount = 1
              animator.interpolator = LinearInterpolator()
              animator.start()*/
            val revolvingAnimator = ObjectAnimator.ofFloat(
                earthImageView,
                "translationX",
                "translationY",
                path
            )
            revolvingAnimator.duration = 3000
            revolvingAnimator.repeatCount = 1
            revolvingAnimator.repeatMode = ObjectAnimator.RESTART
            //revolvingAnimator.interpolator = pathInterpolator
            revolvingAnimator.start()


        }

        // Handle Grow Button
        val growButton: Button = findViewById<View>(R.id.ButtonScale) as Button
        growButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                performAnimation(R.anim.grow)
            }
        })

        // Handle Move Button
        val moveButton: Button = findViewById<View>(R.id.ButtonTranslate) as Button
        moveButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                performAnimation(R.anim.translate_position)
            }
        })

        // Handle Spin Button
        val spinButton: Button = findViewById<View>(R.id.ButtonRotate) as Button
        spinButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                performAnimation(R.anim.spin)
            }
        })

        // Handle All Button
        val allButton: Button = findViewById<View>(R.id.ButtonAll) as Button
        allButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                performAnimation(R.anim.shakenotstirred)
            }
        })
    }

    private fun performAnimation(animationResourceID: Int) {
        // We will animate the imageview
        val reusableImageView: ImageView =
            findViewById<View>(R.id.ImageViewForTweeningEarth) as ImageView
        //reusableImageView.setImageResource(R.drawable.green_rect)
        reusableImageView.setVisibility(View.VISIBLE)
        reusableImageView.translationX = 0f
        reusableImageView.translationY = 0f
        // Load the appropriate animation
        val an: Animation = AnimationUtils.loadAnimation(this, animationResourceID)
        // Register a listener, so we can disable and re-enable buttons
        an.setAnimationListener(MyAnimationListener())
        // Start the animation
        reusableImageView.startAnimation(an)
    }

    private fun toggleButtons(clickableState: Boolean) {
        val fadeButton: Button = findViewById<View>(R.id.ButtonAlpha) as Button
        fadeButton.setClickable(clickableState)

        // Handle Grow Button
        val growButton: Button = findViewById<View>(R.id.ButtonScale) as Button
        growButton.setClickable(clickableState)

        // Handle Move Button
        val moveButton: Button = findViewById<View>(R.id.ButtonTranslate) as Button
        moveButton.setClickable(clickableState)

        // Handle Spin Button
        val spinButton: Button = findViewById<View>(R.id.ButtonRotate) as Button
        spinButton.setClickable(clickableState)

        // Handle Spin Button
        val allButton: Button = findViewById<View>(R.id.ButtonAll) as Button
        allButton.setClickable(clickableState)
    }

    internal inner class MyAnimationListener : Animation.AnimationListener {
        override fun onAnimationEnd(animation: Animation) {
            // Hide our ImageView
            val reusableImageView: ImageView =
                findViewById<View>(R.id.ImageViewForTweeningEarth) as ImageView
            reusableImageView.setVisibility(View.INVISIBLE)

            // Enable all buttons once animation is over
            toggleButtons(true)
        }

        override fun onAnimationRepeat(animation: Animation) {
            // what to do when animation loops

        }

        override fun onAnimationStart(animation: Animation) {
            // Disable all buttons while animation is running
            toggleButtons(false)

        }
    }

}