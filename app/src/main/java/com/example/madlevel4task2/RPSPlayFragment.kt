package com.example.madlevel4task2

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_rps_play.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class RPSPlayFragment : Fragment() {

    private lateinit var rpsRepository: RPSRepository
    private val mainScope = CoroutineScope(Dispatchers.Main)

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rps_play, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rpsRepository = RPSRepository(requireContext())
        initViews()
    }

    private fun initViews(){
        //btnHistory.setOnClickListener {
        //    var historyIntent = Intent(this, RPSHistory::class.java)
        //    startActivity(historyIntent)
        //}

        val toolbar: Toolbar

        btnPlayRock.setOnClickListener {
            playerPick(RPS.Choice.ROCK)
        }
        btnPlayPaper.setOnClickListener {
            playerPick(RPS.Choice.PAPER)
        }
        btnPlayScissor.setOnClickListener {
            playerPick(RPS.Choice.SCISSORS)
        }
    }

    private fun playerPick(playerChoice: RPS.Choice){
        val computerChoice = RPS.Choice.values()[(RPS.Choice.values().indices).random()]
        mainScope.launch {
            val rps = RPS(
                playerChoice.ordinal,
                computerChoice.ordinal,
                getWinner(playerChoice, computerChoice),
                Date()
            )
            ivPlayer.setImageResource(GetImage(playerChoice))
            ivComputer.setImageResource(GetImage(computerChoice))
            withContext(Dispatchers.IO) {
                rpsRepository.insertProduct(rps)
            }
        }
    }

    private fun GetImage(choice: RPS.Choice): Int{
        return when(choice) {
            RPS.Choice.ROCK ->
                R.drawable.rock
            RPS.Choice.PAPER ->
                R.drawable.paper
            RPS.Choice.SCISSORS ->
                R.drawable.scissors
        }
    }

    private fun getWinner(playerChoice: RPS.Choice, computerChoice: RPS.Choice) : RPS.Result{
        if(playerChoice == computerChoice)
            return RPS.Result.DRAW
        else if (playerChoice == RPS.Choice.ROCK && computerChoice == RPS.Choice.SCISSORS ||
            playerChoice == RPS.Choice.PAPER && computerChoice == RPS.Choice.ROCK ||
            playerChoice == RPS.Choice.SCISSORS && computerChoice == RPS.Choice.PAPER)
            return RPS.Result.WIN
        else
            return RPS.Result.LOSS
    }

}