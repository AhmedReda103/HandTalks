package com.example.handtalks.other

import com.example.handtalks.R
import com.example.handtalks.models.AlphapetsItem
import com.example.handtalks.models.DictionaryItem

object Constants {
    const val SHARED_PREFERENCES_NAME = "SHARED_PREFERENCES_NAME"
    const val ASL_PATH = "Asl.tflite"
    const val ARSL_PATH = "Arsl.tflite"

    const val ASL = "ASL"
    const val ARSL = "ARSL"

    fun getASLItems(): List<AlphapetsItem> {
        val list = mutableListOf<AlphapetsItem>()
        val firstItem = AlphapetsItem("Alphabets A-H", "8 letters")
        list.add(firstItem)
        val secondItem = AlphapetsItem("Alphabets I-P", "8 letters")
        list.add(secondItem)
        val thirdItem = AlphapetsItem("Alphabets Q-Y", "9 letters")
        list.add(thirdItem)

        return list
    }

    fun getARSLItems(): List<AlphapetsItem> {
        val list = mutableListOf<AlphapetsItem>()
        val firstItem = AlphapetsItem(" الحروف من الألف الي الذال ", " تسع حروف ")
        list.add(firstItem)
        val secondItem = AlphapetsItem("الحروف من الراء الي الغين ", "عشر حروف ")
        list.add(secondItem)
        val thirdItem = AlphapetsItem("الحروف من الفاء الي الياء  ", " تسع حروف ")
        list.add(thirdItem)

        return list
    }

    val arabicAlphabets = arrayListOf<String>(
        "أ",
        "ب",
        "ت",
        "ث",
        "ج",
        "ح",
        "خ",
        "د",
        "ذ",
        "ر",
        "ز",
        "س",
        "ش",
        "ص",
        "ض",
        "ط",
        "ظ",
        "ع",
        "غ",
        "ف",
        "ق",
        "ك",
        "ل",
        "م",
        "ن",
        "هـ",
        "و",
        "ي"
    )
    val englishAlphabets = arrayListOf<String>(
        "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N",
        "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y"
    )

    val ASL_DIC_LIST = listOf<DictionaryItem>(
        DictionaryItem("A", R.drawable.a),
        DictionaryItem("B", R.drawable.b),
        DictionaryItem("C", R.drawable.c),
        DictionaryItem("D", R.drawable.d),
        DictionaryItem("E", R.drawable.e),
        DictionaryItem("F", R.drawable.f),
        DictionaryItem("G", R.drawable.g),
        DictionaryItem("H", R.drawable.h),
        DictionaryItem("I", R.drawable.i),
        DictionaryItem("J", R.drawable.j),
        DictionaryItem("K", R.drawable.k),
        DictionaryItem("L", R.drawable.l),
        DictionaryItem("M", R.drawable.m),
        DictionaryItem("N", R.drawable.n),
        DictionaryItem("O", R.drawable.o),
        DictionaryItem("P", R.drawable.p),
        DictionaryItem("Q", R.drawable.q),
        DictionaryItem("R", R.drawable.r),
        DictionaryItem("S", R.drawable.s),
        DictionaryItem("T", R.drawable.t),
        DictionaryItem("U", R.drawable.u),
        DictionaryItem("V", R.drawable.v),
        DictionaryItem("W", R.drawable.w),
        DictionaryItem("X", R.drawable.x),
        DictionaryItem("Y", R.drawable.y)


    )

    val ARSL_DIC_LIST = listOf<DictionaryItem>(
        DictionaryItem("أ", R.drawable.aleff),
        DictionaryItem("ب", R.drawable.baa),
        DictionaryItem("ت", R.drawable.ta),
        DictionaryItem("ث", R.drawable.tha),
        DictionaryItem("ج", R.drawable.jeem),
        DictionaryItem("ح", R.drawable.ha),
        DictionaryItem("خ", R.drawable.khaa),
        DictionaryItem("د", R.drawable.dal),
        DictionaryItem("ذ", R.drawable.thal),
        DictionaryItem("ر", R.drawable.raa),
        DictionaryItem("ز", R.drawable.zay),
        DictionaryItem("س", R.drawable.seen),
        DictionaryItem("ش", R.drawable.sheen),
        DictionaryItem("ص", R.drawable.saad),
        DictionaryItem("ض", R.drawable.daad),
        DictionaryItem("ط", R.drawable.taa),
        DictionaryItem("ظ", R.drawable.thah),
        DictionaryItem("ع", R.drawable.ain),
        DictionaryItem("غ", R.drawable.ghain),
        DictionaryItem("ف", R.drawable.fa),
        DictionaryItem("ق", R.drawable.qaaf),
        DictionaryItem("ك", R.drawable.kaaf),
        DictionaryItem("ل", R.drawable.laam),
        DictionaryItem("م", R.drawable.meem),
        DictionaryItem("ن", R.drawable.nun),
        DictionaryItem("هـ", R.drawable.haa),
        DictionaryItem("و", R.drawable.waw),
        DictionaryItem("ي", R.drawable.ya),


    )


}