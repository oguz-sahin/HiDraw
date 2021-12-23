package com.huawei.hidraw.util

import com.huawei.hidraw.data.model.CommonBasicResultModel
import com.huawei.hidraw.data.model.DrawModel
import com.huawei.hidraw.ui.DrawTypes

class InputValidation {

    private val result = CommonBasicResultModel<String>(true, "")
    private val err = "value is invalid.\n"

    fun checkInputs(model: DrawModel, type: DrawTypes): CommonBasicResultModel<String> {
        return if (type == DrawTypes.INSTAGRAM)
            inputCheckInstagram(model)
        else
            inputCheckCustom(model)
    }

    private fun inputCheckInstagram(model: DrawModel): CommonBasicResultModel<String> {

        with(model) {

            if (drawName.isEmpty()) {
                result.passed = false
                appendToInfo("Name")
            }
            if (startDate.toString().isEmpty()) {
                result.passed = false
                appendToInfo("Start Date")
            }
            if (endDate.toString().isEmpty()) {
                result.passed = false
                appendToInfo("End Date")
            }
            if (postUrl.isEmpty()) {
                result.passed = false
                appendToInfo("Url")
            }
            if (description.isEmpty()) {
                result.passed = false
                appendToInfo("Description")
            }
            if (minLabelCount < 0 || minLabelCount.toString().isEmpty()) {
                result.passed = false
                appendToInfo("Min Label Count")
            }
            if (permenantUserCount < 0 || permenantUserCount.toString().isEmpty()) {
                result.passed = false
                appendToInfo("Winner")
            }
            if (spareUserCount < 0 || spareUserCount.toString().isEmpty()) {
                result.passed = false
                appendToInfo("Reserve")
            }

            if (startDate.toString().isNotEmpty() && endDate.toString().isNotEmpty()) {
                if (endDate < startDate) {
                    result.passed = false
                    appendToInfo("Date Interval must be valid.", false)
                }
            }
        }
        return result
    }

    private fun inputCheckCustom(model: DrawModel): CommonBasicResultModel<String> {
        with(model) {

            if (drawName.isEmpty()) {
                result.passed = false
                appendToInfo("Name")
            }
            if (description.isEmpty()) {
                result.passed = false
                appendToInfo("Description")
            }
            if (permenantUserCount < 0 || permenantUserCount.toString().isEmpty()) {
                result.passed = false
                appendToInfo("Winner")
            }
            if (spareUserCount < 0 || spareUserCount.toString().isEmpty()) {
                result.passed = false
                appendToInfo("Reserve")
            }
            if (participantNames.isEmpty()) {
                result.passed = false
                appendToInfo("Participant Names")
            }
        }
        return result
    }

    private fun appendToInfo(append: String, withCommonError: Boolean = true) {
        if (withCommonError)
            result.info = "${result.info} $append $err"
        else
            result.info = "${result.info} $append"
    }
}
