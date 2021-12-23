package com.huawei.hidraw.util.customdraw

import com.huawei.hidraw.data.model.CommonBasicResultModel
import com.huawei.hidraw.data.model.DrawModel
import kotlin.random.Random

class CustomDrawHelper {

    private var names = emptyList<String>()
    private var winnerNames: MutableList<String> = mutableListOf()
    private var reserveNames: MutableList<String> = mutableListOf()
    private var winnerCount: Int = 0
    private var reserveCount: Int = 0
    private var totalNameCount: Int = 0

    fun draw(model: DrawModel): CommonBasicResultModel<String> {
        return draw(model.permenantUserCount, model.spareUserCount, model.participantNames) // CHECK !!
    }

    private fun draw(_winnerCount: Int, _reserveCount: Int, namesWithComma: String): CommonBasicResultModel<String> {

        names = namesWithComma.split(",").map { it -> it.trim() }
        winnerCount = _winnerCount
        reserveCount = _reserveCount
        totalNameCount = names.size

        setWinners()

        if (reserveCount > 0)
            setReserves()

        return generateResultFromLists()
    }

    private fun setWinners() {
        while (winnerCount > 0) {

            val randomNumber = Random.nextInt(0, totalNameCount)
            val chosenOne = names[randomNumber]

            if (winnerNames.contains(chosenOne))
                continue

            winnerNames.add(chosenOne)
            winnerCount--
        }
    }

    private fun setReserves() {

        while (reserveCount > 0) {

            val randomNumber = Random.nextInt(0, totalNameCount)
            val chosenOne = names[randomNumber]

            if (winnerNames.contains(chosenOne) || reserveNames.contains(chosenOne))
                continue

            reserveNames.add(chosenOne)
            reserveCount--
        }
    }

    private fun generateResultFromLists(): CommonBasicResultModel<String> {

        var result = ""

        winnerNames.forEach {
            result += "Winner Name $it \n"
        }

        reserveNames.forEach {
            result += "Reserve Name $it \n"
        }

        return CommonBasicResultModel(true, result)
    }
}
