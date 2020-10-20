package com.example.madlevel4task2

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_rps_history.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class RPSHistoryFragment : Fragment() {

    private val matchList = arrayListOf<RPS>()
    private val matchAdapter = RPSAdapter(matchList)
    private lateinit var rpsRepository: RPSRepository
    private val scope = CoroutineScope(Dispatchers.Main)

    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rps_history, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rpsRepository = RPSRepository(requireContext())
        initViews()
        setHasOptionsMenu(true)
    }

    private fun initViews(){

        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.game_history)

        viewManager = LinearLayoutManager(activity)
        rvHistory.addItemDecoration(
            DividerItemDecoration(
                activity,
                DividerItemDecoration.VERTICAL
            )
        )
        rvHistory.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = matchAdapter
        }
        getAllMatches()

        //btnDelete.setOnClickListener{ clearHistory() }
    }

    private fun getAllMatches(){
        scope.launch {
            val list = withContext(Dispatchers.IO){
                rpsRepository.getAllProducts()
            }
            this@RPSHistoryFragment.matchList.clear()
            this@RPSHistoryFragment.matchList.addAll(list)
            this@RPSHistoryFragment.matchAdapter.notifyDataSetChanged()
        }
    }

    private fun clearHistory() {
        scope.launch {
            withContext(Dispatchers.IO) {
                rpsRepository.deleteAllProducts()
            }
            getAllMatches()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_fragment_rps_history, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.action_history) {
            clearHistory()
        }

        return super.onOptionsItemSelected(item)
    }
}