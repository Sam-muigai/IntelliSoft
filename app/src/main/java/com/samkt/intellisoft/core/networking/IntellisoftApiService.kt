package com.samkt.intellisoft.core.networking

import com.samkt.intellisoft.core.networking.dtos.AddPatientRequest
import com.samkt.intellisoft.core.networking.dtos.AddPatientResponse
import com.samkt.intellisoft.core.networking.dtos.GetVisitsRequest
import com.samkt.intellisoft.core.networking.dtos.GetVisitsResponse
import com.samkt.intellisoft.core.networking.dtos.LoginRequest
import com.samkt.intellisoft.core.networking.dtos.LoginResponse
import com.samkt.intellisoft.core.networking.dtos.PatientsResponse
import com.samkt.intellisoft.core.networking.dtos.SaveVisitRequest
import com.samkt.intellisoft.core.networking.dtos.SaveVisitResponse
import com.samkt.intellisoft.core.networking.dtos.SaveVitalsRequest
import com.samkt.intellisoft.core.networking.dtos.SaveVitalsResponse
import com.samkt.intellisoft.core.networking.dtos.SignUpRequest
import com.samkt.intellisoft.core.networking.dtos.SignUpResponse
import com.samkt.intellisoft.core.networking.helpers.ApiResponse

interface IntellisoftApiService {
    suspend fun signUp(signUpRequest: SignUpRequest): ApiResponse<SignUpResponse>
    suspend fun login(loginRequest: LoginRequest): ApiResponse<LoginResponse>
    suspend fun savePatient(addPatientRequest: AddPatientRequest): AddPatientResponse
    suspend fun setVitals(saveVitalsRequest: SaveVitalsRequest): SaveVitalsResponse
    suspend fun getPatients(): PatientsResponse
    suspend fun saveVisits(saveVisitRequest: SaveVisitRequest): SaveVisitResponse
    suspend fun getVisits(getVisitsRequest: GetVisitsRequest): ApiResponse<GetVisitsResponse>
}