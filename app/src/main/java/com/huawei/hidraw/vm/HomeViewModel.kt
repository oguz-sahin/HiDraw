package com.huawei.hidraw.vm

import androidx.lifecycle.MutableLiveData
import com.huawei.hidraw.core.BaseViewModel
import com.huawei.hidraw.data.model.DrawModel
import com.huawei.hidraw.data.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Oguz Sahin on 11/23/2021.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : BaseViewModel() {

    private val _activeDraws = MutableLiveData<List<DrawModel>>()
    val activeDraws get() = _activeDraws

    init {
        getActiveDraws()
    }

    private fun getActiveDraws() {
        makeNetworkRequest(
            requestFunc = { homeRepository.getActiveDraws() },
            onSuccess = { draws ->
                _activeDraws.postValue(draws)
            }
        )
    }
}
