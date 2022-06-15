package com.guowei.twentyfourgame

data class CardModel(
    val type: Type,
    val value: Int
) {
    fun getImage(): Int {
        return when (type) {
            Type.Club -> when (value) {
                1 -> R.drawable.clovers_a_white
                2 -> R.drawable.clovers_2_white
                3 -> R.drawable.clovers_3_white
                4 -> R.drawable.clovers_4_white
                5 -> R.drawable.clovers_5_white
                6 -> R.drawable.clovers_6_white
                7 -> R.drawable.clovers_7_white
                8 -> R.drawable.clovers_8_white
                9 -> R.drawable.clovers_9_white
                10 -> R.drawable.clovers_10_white
                11 -> R.drawable.clovers_jack_white
                12 -> R.drawable.clovers_queen_white
                13 -> R.drawable.clovers_king_white
                else -> error("")
            }
            Type.Diamond -> when (value) {
                1 -> R.drawable.tiles_a_white
                2 -> R.drawable.tiles_2_white
                3 -> R.drawable.tiles_3_white
                4 -> R.drawable.tiles_4_white
                5 -> R.drawable.tiles_5_white
                6 -> R.drawable.tiles_6_white
                7 -> R.drawable.tiles_7_white
                8 -> R.drawable.tiles_8_white
                9 -> R.drawable.tiles_9_white
                10 -> R.drawable.tiles_10_white
                11 -> R.drawable.tiles_jack_white
                12 -> R.drawable.tiles_queen_white
                13 -> R.drawable.tiles_king_white
                else -> error("")
            }
            Type.Heart -> when (value) {
                1 -> R.drawable.pikes_a_white
                2 -> R.drawable.pikes_2_white
                3 -> R.drawable.pikes_3_white
                4 -> R.drawable.pikes_4_white
                5 -> R.drawable.pikes_5_white
                6 -> R.drawable.pikes_6_white
                7 -> R.drawable.pikes_7_white
                8 -> R.drawable.pikes_8_white
                9 -> R.drawable.pikes_9_white
                10 -> R.drawable.pikes_10_white
                11 -> R.drawable.pikes_jack_white
                12 -> R.drawable.pikes_queen_white
                13 -> R.drawable.pikes_king_white
                else -> error("")
            }
            Type.Spade -> when (value) {
                1 -> R.drawable.hearts_a_white
                2 -> R.drawable.hearts_2_white
                3 -> R.drawable.hearts_3_white
                4 -> R.drawable.hearts_4_white
                5 -> R.drawable.hearts_5_white
                6 -> R.drawable.heats_6_white
                7 -> R.drawable.hearts_7_white
                8 -> R.drawable.hearts_8_white
                9 -> R.drawable.hearts_9_white
                10 -> R.drawable.hearts_10_white
                11 -> R.drawable.hearts_jack_white
                12 -> R.drawable.hearts_queen_white
                13 -> R.drawable.hearts_king_white
                else -> error("")
            }
        }
    }
}


enum class Type {
    Club, Diamond, Heart, Spade
}
