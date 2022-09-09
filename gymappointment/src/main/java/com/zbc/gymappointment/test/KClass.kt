package com.zbc.gymappointment.test

class KClass :BaseClass,IAnim {
    var tag :String
    constructor(tag:String):super(){
        this.tag=tag
    }

    override fun isAttachToWindow(): Boolean {
       return true
    }

    override fun toString(): String {
        return tag
    }
}