package una.delta.frontenddelta.utils

import okhttp3.Interceptor
import okhttp3.Response
import una.delta.frontenddelta.utils.MyApplication.Companion.sessionManager


class AuthorizationInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        //if token has been saved, add it to the request
        sessionManager?.fetchAuthToken()?.let{
            requestBuilder.addHeader("Authorization",it)
        }
        return chain.proceed(requestBuilder.build())
    }
}