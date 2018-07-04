package com.coderefer.firebaseauth

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.CardView
import android.view.View
import android.view.ViewAnimationUtils
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Build
import android.view.animation.Animation
import android.view.animation.ScaleAnimation

class AuthActivity : AppCompatActivity() {

    lateinit var cardView: CardView
    lateinit var bgView: View
    lateinit var tvLogin: View
    lateinit var tick: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        cardView = findViewById(R.id.cv_login)
        cardView.setOnClickListener {
            tvLogin = findViewById(R.id.tv_login)
            tvLogin.visibility = View.INVISIBLE
            tick = findViewById(R.id.animation_view_2)
            scaleView(cardView)
        }
    }

    private fun revealAnimation() {
        bgView = findViewById(R.id.revealView)

        // Check if the runtime version is at least Lollipop
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // get the center for the clipping circle
//            taking screen's center
            val cx = bgView.width / 2
//            taking button position's y axis
            val posArray = IntArray(2)
            cardView.getLocationOnScreen(posArray)// to fetch the coordinates of login cardView's location
            val cy = posArray[1]

            // get the final radius for the clipping circle
            val finalRadius = Math.hypot(cx.toDouble(), cy.toDouble()).toFloat()

            // create the animator for this view (the start radius is zero)
            val anim = ViewAnimationUtils.createCircularReveal(bgView, cx, cy, 0f, finalRadius)
            anim.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
//                    perform actions on animation end
                    super.onAnimationEnd(animation)
                    bgView.visibility = View.INVISIBLE
                }
            })
            // make the view visible and start the animation
            bgView.visibility = View.VISIBLE
            anim.start()
        } else {
            // set the view to visible without a circular reveal animation below Lollipop
            bgView.visibility = View.VISIBLE
        }
    }

    fun scaleView(v: View) {
        val anim = ScaleAnimation(
                1f, 0f, // Start and end values for the X axis scaling
                1f, 1f, // Start and end values for the Y axis scaling
                Animation.RELATIVE_TO_SELF, .5f, // Pivot point of X scaling
                Animation.RELATIVE_TO_SELF, .5f) // Pivot point of Y scaling
        anim.fillAfter = true // Needed to keep the result of the animation
        anim.duration = 200
        val animListener = AnimationListenerer()
        anim.setAnimationListener(animListener)
        v.startAnimation(anim)
    }

    inner class AnimationListenerer: Animation.AnimationListener{
        override fun onAnimationRepeat(p0: Animation?) {

        }

        override fun onAnimationEnd(p0: Animation?) {
            tick.visibility = View.VISIBLE
            revealAnimation()
        }

        override fun onAnimationStart(p0: Animation?) {
            tick.visibility = View.GONE
        }
    }
}
