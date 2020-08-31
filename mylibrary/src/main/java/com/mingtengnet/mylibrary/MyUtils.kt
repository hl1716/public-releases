package com.mingtengnet.mylibrary

import android.content.Context
import android.widget.Toast

class MyUtils {
    fun hollowWorld(context: Context, str: String) {
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show()
    }
}