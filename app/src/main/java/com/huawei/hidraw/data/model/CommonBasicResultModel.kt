package com.huawei.hidraw.data.model

data class CommonBasicResultModel<T> (
    var passed: Boolean,
    var info: T
){

}
