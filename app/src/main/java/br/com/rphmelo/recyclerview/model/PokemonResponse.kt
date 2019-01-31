package br.com.rphmelo.recyclerview.model

import com.google.gson.annotations.SerializedName

data class PokemonResponse(@SerializedName("content") val pokemons: List<Pokemon>)

data class Pokemon(
        @SerializedName("name") val name: String,
        @SerializedName("number") val number: String,
        @SerializedName("imageURL") val image: String
)