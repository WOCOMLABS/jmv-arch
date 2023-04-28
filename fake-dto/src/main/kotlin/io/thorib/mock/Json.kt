package io.thorib.mock

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okio.FileSystem
import okio.Path.Companion.toPath


/**
 * Instance from file
 *
 * @param T
 * @param file
 * @return
 */
inline fun <reified T> instanceFromFile(file : String) : T = Gson().fromJson(
    FileSystem.RESOURCES.read(file.toPath()) {
        readUtf8()
    } ,
    object : TypeToken<T>() {}.type
)


/**
 * To instance
 *
 * @param T
 * @return
 */
inline fun <reified T> String.toInstance() : T =
    Gson().fromJson(
        FileSystem.RESOURCES.read(toPath()) {
            readUtf8()
        } ,
        object : TypeToken<T>() {}.type
    )


fun String.readFile() : String = FileSystem.RESOURCES.read(toPath()) {
    readUtf8()
}

