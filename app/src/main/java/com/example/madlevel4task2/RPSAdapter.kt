package com.example.madlevel4task2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item.view.*

class RPSAdapter(private val games: List<RPS>) : RecyclerView.Adapter<RPSAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return games.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(games[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val context: Context = itemView.context.applicationContext

        private fun GetImage(choice: Int): Int{
            return when(choice) {
                0 ->
                    R.drawable.rock
                1 ->
                    R.drawable.paper
                2 ->
                    R.drawable.scissors
                else -> R.drawable.paper
            }
        }

        fun bind(rps: RPS) {
            when (rps.result) {
                RPS.Result.LOSS ->
                    itemView.tvWinner.text = context.getString(R.string.computerWin)
                RPS.Result.DRAW ->
                    itemView.tvWinner.text = context.getString(R.string.draw)
                RPS.Result.WIN ->
                    itemView.tvWinner.text = context.getString(R.string.playerWins)
            }
            itemView.tvPlayDate.text = rps.date.toString()
            itemView.ivComputerHistory.setImageResource(GetImage(rps.computerChoice))
            itemView.ivPlayerHistory.setImageResource(GetImage(rps.playerChoice))
        }
    }
}