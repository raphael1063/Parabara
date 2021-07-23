/*중복클릭 방지 클래스*/
package com.example.parabara.util

import android.os.SystemClock
import android.view.View

class OnSafeClickListener(
    private var defaultInterval: Int = 300,
    private val action: View.OnClickListener?
) : View.OnClickListener {
    constructor(action: View.OnClickListener?) : this(300, action)

    private var lastTimeClicked: Long = 0
    override fun onClick(v: View) {
        if (SystemClock.elapsedRealtime() - lastTimeClicked < defaultInterval) {
            return
        } else {
            lastTimeClicked = SystemClock.elapsedRealtime()
            action?.onClick(v)
        }
    }
}