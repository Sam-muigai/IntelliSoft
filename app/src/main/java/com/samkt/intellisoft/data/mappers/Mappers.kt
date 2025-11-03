package com.samkt.intellisoft.data.mappers

import com.samkt.intellisoft.core.networking.dtos.SignUpRequest
import com.samkt.intellisoft.domain.model.SignUp


fun SignUp.toData(): SignUpRequest {
    return SignUpRequest(
        email = email,
        password = password,
        firstname = firstname,
        lastname = lastname
    )
}