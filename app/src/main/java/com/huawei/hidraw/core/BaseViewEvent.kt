/*
 * Copyright 2022. Explore in HMS. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.huawei.hidraw.core

import androidx.annotation.StringRes
import androidx.navigation.NavDirections

/**
 * Created by Oguz Sahin on 10/26/2021.
 */
sealed class BaseViewEvent {

    data class ShowError(val message: String) : BaseViewEvent()

    data class ShowErrorWithId(@StringRes val messageId: Int) : BaseViewEvent()

    data class ShowSuccess(val message: String) : BaseViewEvent()

    data class ShowSuccessWithId(@StringRes val message: Int) : BaseViewEvent()

    data class NavigateTo(val directions: NavDirections) : BaseViewEvent()

    object NavigateBack : BaseViewEvent()
}
