package ru.danilov.spaceapp.extensions

fun String.getFirstSentence() : String {
    return "(.*\\.)".toRegex().find(this).toString()
}