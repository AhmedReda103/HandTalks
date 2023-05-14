package com.example.handtalks.repositories

import com.example.handtalks.R
import com.example.handtalks.models.PracticeItem
import com.example.handtalks.other.Constants
import com.example.handtalks.other.Constants.ASL
import com.example.handtalks.other.Constants.ASL_PATH


object PracticeItemsRepo {

    private val firstItemEnglishCharacter =
        arrayListOf<String>("A", "B", "C", "D", "E", "F", "G", "H")
    private val secondItemEnglishCharacter =
        arrayListOf<String>("I", "J", "K", "L", "M", "N", "O", "P")
    private val thirdItemEnglishCharacter =
        arrayListOf<String>("Q", "R", "S", "T", "U", "V", "W", "X", "Y")


    private val firstItemEnglishImages = arrayListOf<Int>(
        R.drawable.a,
        R.drawable.b,
        R.drawable.c,
        R.drawable.d,
        R.drawable.e,
        R.drawable.f,
        R.drawable.g,
        R.drawable.h
    )
    private val secondItemEnglishImages = arrayListOf<Int>(
        R.drawable.i,
        R.drawable.j,
        R.drawable.k,
        R.drawable.l,
        R.drawable.m,
        R.drawable.n,
        R.drawable.o,
        R.drawable.p
    )

    private val thirdItemEnglishImages = arrayListOf<Int>(
        R.drawable.q,
        R.drawable.r,
        R.drawable.s,
        R.drawable.t,
        R.drawable.u,
        R.drawable.v,
        R.drawable.w,
        R.drawable.x,
        R.drawable.y
    )


    private val firstItemArabicCharacter =
        arrayListOf<String>("aleff", "baa", "ta", "tha", "jem", "haa", "khaa", "dal" , "thal")
    private val secondItemArabicCharacter =
        arrayListOf<String>("ra", "zay", "seen", "sheen", "saad", "daad", "taa", "thah" , "ain" , "ghain")
    private val thirdItemArabicCharacter =
        arrayListOf<String>("fa", "qaaf", "kaaf", "laam", "meem", "nun", "ha", "waw", "ya")


    private val firstItemArabicImages = arrayListOf<Int>(
        R.drawable.aleff,
        R.drawable.baa,
        R.drawable.ta,
        R.drawable.tha,
        R.drawable.jeem,
        R.drawable.ha,
        R.drawable.khaa,
        R.drawable.dal ,
        R.drawable.thal
    )
    private val secondItemArabicImages = arrayListOf<Int>(
        R.drawable.raa,
        R.drawable.zay,
        R.drawable.seen,
        R.drawable.sheen,
        R.drawable.saad,
        R.drawable.daad,
        R.drawable.taa,
        R.drawable.thah,
        R.drawable.ain ,
        R.drawable.ghain
    )

    private val thirdItemArabicImages = arrayListOf<Int>(
        R.drawable.fa,
        R.drawable.qaaf,
        R.drawable.kaaf,
        R.drawable.laam,
        R.drawable.meem,
        R.drawable.nun,
        R.drawable.haa,
        R.drawable.waw,
        R.drawable.ya
    )




    fun getFirstItemList(modelSelected : String ): ArrayList<PracticeItem> {
        val items = arrayListOf<PracticeItem>()
        val firstItem : PracticeItem
        if(modelSelected== ASL_PATH){
             firstItem = PracticeItem(firstItemEnglishImages, firstItemEnglishCharacter)
        }else{
            firstItem = PracticeItem(firstItemArabicImages, firstItemArabicCharacter)
        }
        items.add(firstItem)
        return items

    }

    fun getSecondItemList(modelSelected : String): ArrayList<PracticeItem> {
        val items = arrayListOf<PracticeItem>()
        val secondItem : PracticeItem
        if(modelSelected==ASL_PATH){
            secondItem = PracticeItem(secondItemEnglishImages, secondItemEnglishCharacter)
        }else{
            secondItem = PracticeItem(secondItemArabicImages, secondItemArabicCharacter)
        }
        items.add(secondItem)
        return items
    }

    fun getThirdItemList(modelSelected : String): ArrayList<PracticeItem> {
        val items = arrayListOf<PracticeItem>()
        val thirdItem : PracticeItem
        if(modelSelected == ASL_PATH){
            thirdItem = PracticeItem(thirdItemEnglishImages, thirdItemEnglishCharacter)
        }else{
            thirdItem = PracticeItem(thirdItemArabicImages, thirdItemArabicCharacter)
        }
        items.add(thirdItem)
        return items
    }

}