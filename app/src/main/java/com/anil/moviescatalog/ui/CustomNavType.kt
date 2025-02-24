package com.anil.moviescatalog.ui

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import com.anil.moviescatalog.model.Movie
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object CustomNavType {
    val MovieNavType  = object: NavType<Movie>(isNullableAllowed = false) {
        override fun get(bundle: Bundle, key: String): Movie? {
            return bundle.getString(key)?.let { Json.decodeFromString<Movie>(it) }
        }

        override fun parseValue(value: String): Movie {
            return Json.decodeFromString(Uri.decode(value))
        }

        override fun serializeAsValue(value: Movie): String {
            return Uri.encode(Json.encodeToString(value))
        }

        override fun put(bundle: Bundle, key: String, value: Movie) {
            bundle.putString(key, Json.encodeToString(value))
        }
    }
}