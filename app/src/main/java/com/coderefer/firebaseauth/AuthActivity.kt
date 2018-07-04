package com.coderefer.firebaseauth

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.CardView
import android.view.View
import android.view.ViewAnimationUtils
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Build
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import com.airbnb.lottie.LottieAnimationView

class AuthActivity : AppCompatActivity() {

    lateinit var cardView: CardView
    lateinit var bgView: View
    lateinit var tvLogin: View
    lateinit var successFailAnimation: LottieAnimationView
    var success = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        cardView = findViewById(R.id.cv_login)
        cardView.setOnClickListener {
            tvLogin = findViewById(R.id.tv_login)
            tvLogin.visibility = View.INVISIBLE
            successFailAnimation = findViewById(R.id.logging_animation)
            addSuccessFailAnimationListener()
            scaleView(cardView)
        }
    }

    private fun addSuccessFailAnimationListener() {
        successFailAnimation.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(p0: Animator?) {

            }

            override fun onAnimationCancel(p0: Animator?) {

            }

            override fun onAnimationEnd(p0: Animator?) {

                if (success) {
                    successFailAnimation.cancelAnimation()
                    successFailAnimation.setMinAndMaxFrame(200, 400)
                    successFailAnimation.playAnimation()
                } else {
                    successFailAnimation.setMinAndMaxFrame(600, 800)
                }
                if (successFailAnimation.frame ==200) {
                    handler.postDelayed({
                        successFailAnimation.visibility = View.GONE
                        revealAnimation()
                    },3500)
                    success = false
                }
            }

            override fun onAnimationStart(p0: Animator?) {

            }

        })
    }

    private fun revealAnimation() {

        /**
         *  to create circular reveal animation for next screen transition
         * */
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

    private val handler: Handler
        get() {
            val handler = Handler()
            return handler
        }

    inner class AnimationListenerer: Animation.AnimationListener{
        override fun onAnimationRepeat(p0: Animation?) {

        }

        override fun onAnimationEnd(p0: Animation?) {
            successFailAnimation.setMinAndMaxFrame(0, 200)
            successFailAnimation.playAnimation()
            successFailAnimation.visibility = View.VISIBLE

        }

        override fun onAnimationStart(p0: Animation?) {
            successFailAnimation.visibility = View.VISIBLE
        }
    }
}
