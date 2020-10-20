package com.example.madlevel4task2

import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.Toolbar
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.activity_main.*
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

        setHasOptionsMenu(true)
        rpsRepository = RPSRepository(requireContext())
        initViews()

    }

    private fun initViews(){
        //btnHistory.setOnClickListener {
        //    var historyIntent = Intent(this, RPSHistory::class.java)
        //    startActivity(historyIntent)
        //}

        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.mad_level)

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
        if(playerChoice == computerChoice) {
            tvResults.text = getString(R.string.draw)
            return RPS.Result.DRAW
        }
        else if (playerChoice == RPS.Choice.ROCK && computerChoice == RPS.Choice.SCISSORS ||
            playerChoice == RPS.Choice.PAPER && computerChoice == RPS.Choice.ROCK ||
            playerChoice == RPS.Choice.SCISSORS && computerChoice == RPS.Choice.PAPER) {
            tvResults.text = getString(R.string.playerWins)
            return RPS.Result.WIN
        }
            else {
            tvResults.text = getString(R.string.computerWin)
            return RPS.Result.LOSS
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_fragment_rps_play, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.action_play) {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        return super.onOptionsItemSelected(item)
    }
}