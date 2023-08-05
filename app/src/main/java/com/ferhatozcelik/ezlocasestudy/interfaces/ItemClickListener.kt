package com.ferhatozcelik.ezlocasestudy.interfaces

import android.view.View

interface ItemClickListener {
    fun onClick(objects: Any?)
}

interface ItemLongClickListener {
    fun onClick(objects: Any?, view: View?)
}
