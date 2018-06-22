package com.coderefer.firebaseauth

import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation


class ResizeAnimation(internal var view: View, internal val targetWidth: Int, internal var startWidth: Int) : Animation() {

    override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
        val newWidth = (startWidth + targetWidth * interpolatedTime).toInt()
        //to support decent animation, change new heigt as Nico S. recommended in comments
        //int newWidth = (int) (startWidth+(targetWidth - startWidth) * interpolatedTime);
        view.layoutParams.width = newWidth
        view.requestLayout()
    }

    override fun willChangeBounds(): Boolean {
        return true
    }
}