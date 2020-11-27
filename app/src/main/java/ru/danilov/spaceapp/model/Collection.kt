package ru.danilov.spaceapp.model

data class Collection (

	val href : String,
	val items : List<Items>,
	val links : List<Links>,
	val metadata : Metadata,
	val version : Double
)