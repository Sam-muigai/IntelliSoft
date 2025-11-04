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
import com.samkt.intellisoft.core.networking.helpers.safeApiCall
import com.samkt.intellisoft.core.preferences.AppPreferences
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.utils.EmptyContent.contentType
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import kotlinx.coroutines.flow.first

class IntellisoftApiServiceImpl(
    private val client: HttpClient,
    private val preferences: AppPreferences
) : IntellisoftApiService {
    override suspend fun signUp(signUpRequest: SignUpRequest): ApiResponse<SignUpResponse> {
        return safeApiCall {
            client.post(BASE_URL + "user/signup") {
                setBody(signUpRequest)
            }
        }
    }

    override suspend fun login(loginRequest: LoginRequest): ApiResponse<LoginResponse> {
        return safeApiCall {
            client.post(BASE_URL + "user/signin") {
                setBody(loginRequest)
            }
        }
    }

    override suspend fun savePatient(addPatientRequest: AddPatientRequest): AddPatientResponse {
        return client.post(BASE_URL + "patients/register") {
            header(HttpHeaders.Authorization, "Bearer ${preferences.getAccessToken().first()}")
            contentType(ContentType.Application.Json)
            setBody(addPatientRequest)
        }.body()
    }

    override suspend fun setVitals(saveVitalsRequest: SaveVitalsRequest): SaveVitalsResponse {
        return client.post(BASE_URL + "vital/add") {
            header(HttpHeaders.Authorization, "Bearer ${preferences.getAccessToken().first()}")
            contentType(ContentType.Application.Json)
            setBody(saveVitalsRequest)
        }.body()
    }

    override suspend fun getPatients(): PatientsResponse {
        return client.get(BASE_URL + "patients/view") {
            header(HttpHeaders.Authorization, "Bearer ${preferences.getAccessToken().first()}")
            contentType(ContentType.Application.Json)
        }.body()
    }

    override suspend fun saveVisits(saveVisitRequest: SaveVisitRequest): SaveVisitResponse {
        return client.post(BASE_URL + "visits/add") {
            header(HttpHeaders.Authorization, "Bearer ${preferences.getAccessToken().first()}")
            contentType(ContentType.Application.Json)
            setBody(saveVisitRequest)
        }.body()
    }

    override suspend fun getVisits(getVisitsRequest: GetVisitsRequest): ApiResponse<GetVisitsResponse> {
        return safeApiCall {
            client.post(BASE_URL + "visits/view") {
                header(HttpHeaders.Authorization, "Bearer ${preferences.getAccessToken().first()}")
                contentType(ContentType.Application.Json)
                setBody(getVisitsRequest)
            }
        }
    }


    companion object {
        const val BASE_URL = "https://patientvisitapis.intellisoftkenya.com/api/"
    }
}