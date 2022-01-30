package com.huawei.hidraw.data.mapper

/**
 * Created by Oguz Sahin on 12/28/2021.
 */
abstract class Mapper<From, To> {
    abstract fun map(from: From): To
}
