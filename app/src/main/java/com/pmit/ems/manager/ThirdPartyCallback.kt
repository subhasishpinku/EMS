package com.pmit.ems.manager

import com.pmit.ems.model.BaseResponse
import com.pmit.ems.model.Data
import com.pmit.ems.model.Response
import com.pmit.ems.model.ThirdPartyLogin

interface ThirdPartyCallback {
    fun onThirdPartyLogin(res: Data<Response>, provider: Int, thirdPartyLogin: ThirdPartyLogin)
    fun onErrorOccured(error: BaseResponse)
}