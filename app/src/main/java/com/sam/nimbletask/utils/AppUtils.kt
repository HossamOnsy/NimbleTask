package com.sam.nimbletask.utils

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import androidx.appcompat.widget.Toolbar
import androidx.palette.graphics.Palette
import com.sam.nimbletask.R

object AppUtils {

    fun setIntoSharedPref(context: Context?, key: String, value: String) {
        val sharedPref =
            context?.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE)
                ?: return
        with(sharedPref.edit()) {
            putString(key, value)
            apply()
        }
    }

    fun getFromSharedPref(context: Context?, key: String): String? {
        val sharedPref =
            context?.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        return sharedPref?.getString(key, "")
    }


    fun setToolbarColor(activity: Activity, bitmap: Bitmap) {
        // Generate the palette and get the vibrant swatch
        val vibrantSwatch = createPaletteSync(bitmap).vibrantSwatch

        // Set the toolbar background and text colors.
        // Fall back to default colors if the vibrant swatch is not available.
        with(activity.findViewById<Toolbar>(R.id.toolbar)) {
            setBackgroundColor(
                vibrantSwatch?.rgb ?: androidx.core.content.ContextCompat.getColor(
                    context,
                    com.sam.nimbletask.R.color.colorPrimary
                )
            )
        }
    }

    fun createPaletteSync(bitmap: Bitmap): Palette = Palette.from(bitmap).generate()
}