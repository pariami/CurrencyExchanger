package msi.paria.domain.model


sealed class Resource<T>(val data: T?, val message: String?){
    class Loading<T>(): Resource<T>(null, null)
    class Success<T>(data: T): Resource<T>(data, null)
    class Error<T>(message: String?): Resource<T>(null, message)
    class Empty<T>(): Resource<T>(null, null)
}