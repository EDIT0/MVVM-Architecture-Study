package com.example.mvvmarchitecturestudy.presentation.ui.fragment

import android.app.ProgressDialog.show
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.accessibility.AccessibilityEventCompat.setAction
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmarchitecturestudy.R
import com.example.mvvmarchitecturestudy.data.model.MovieModelResult
import com.example.mvvmarchitecturestudy.databinding.FragmentSavedMoviesBinding
import com.example.mvvmarchitecturestudy.presentation.adapter.SavedMoviesAdapter
import com.example.mvvmarchitecturestudy.presentation.ui.activity.MainActivity
import com.example.mvvmarchitecturestudy.presentation.ui.activity.MovieInfoActivity
import com.example.mvvmarchitecturestudy.presentation.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*


class SavedMoviesFragment : Fragment() {

    private val CLASS_NAME = SavedMoviesFragment::class.java.simpleName

    lateinit var fragmentSavedMoviesBinding : FragmentSavedMoviesBinding

    lateinit var mainViewModel : MainViewModel
    private lateinit var savedMoviesAdapter: SavedMoviesAdapter

    private var isLoading = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentSavedMoviesBinding = FragmentSavedMoviesBinding.bind(inflater.inflate(R.layout.fragment_saved_movies, container, false))
        return fragmentSavedMoviesBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel = (activity as MainActivity).mainViewModel
        savedMoviesAdapter = (activity as MainActivity).savedMoviesAdapter

        showProgressBar()
        initSavedMoviesRecyclerView()
        observeListener()
        searchListener()
        actionbarElevation()
    }

    private fun initSavedMoviesRecyclerView() {
        fragmentSavedMoviesBinding.rvSavedMoviesList.apply {
            adapter = savedMoviesAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@SavedMoviesFragment.onScrollListener)
        }

        savedMoviesAdapter.setOnItemClickListener {
            val intent = Intent(requireActivity(), MovieInfoActivity::class.java)
            intent.putExtra("MovieInfo", it)
            requireActivity().startActivity(intent)
        }

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val movie = savedMoviesAdapter.currentList[position]

                mainViewModel.deleteSavedMovie(movie)

                Snackbar.make(view!!, "Delete Movie", Snackbar.LENGTH_LONG).apply {
                    setAction("되돌리기") {
                        mainViewModel.saveMovie(movie)
                    }
                    show()
                }
            }

        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(fragmentSavedMoviesBinding.rvSavedMoviesList)
        }
    }

    private fun observeListener() {
//        mainViewModel.getSavedMovies().observe(viewLifecycleOwner, {
//            savedMoviesAdapter.replaceItems(it)
//
//            if(it.size > 0) {
//                fragmentSavedMoviesBinding.tvDataEmpty.visibility = View.INVISIBLE
//            } else {
//                fragmentSavedMoviesBinding.tvDataEmpty.visibility = View.VISIBLE
//            }
//
//            hideProgressBar()
//        })

        mainViewModel.getSavedMovies.observe(viewLifecycleOwner, {
            Log.i(MainActivity.TAG + CLASS_NAME, "현재 저장된 아이템 갯수 : ${it.size}")
            savedMoviesAdapter.replaceItems(it)

            if(it.size > 0) {
                fragmentSavedMoviesBinding.tvDataEmpty.visibility = View.INVISIBLE
            } else {
                fragmentSavedMoviesBinding.tvDataEmpty.visibility = View.VISIBLE
            }

            hideProgressBar()
        })
    }

    private fun actionbarElevation() {
        if(Build.VERSION.SDK_INT > 23) {
            fragmentSavedMoviesBinding.rvSavedMoviesList.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                fragmentSavedMoviesBinding.etSavedMovieSearch.isSelected = fragmentSavedMoviesBinding.rvSavedMoviesList.canScrollVertically(-1)
            }
        }
    }

    private fun showProgressBar() {
        isLoading = true
        fragmentSavedMoviesBinding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        isLoading = false
        fragmentSavedMoviesBinding.progressBar.visibility = View.INVISIBLE
    }

    private val onScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)

            val lastVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition() // 화면에 보이는 마지막 아이템의 position
            val itemTotalCount = recyclerView.adapter!!.itemCount - 1 // 어댑터에 등록된 아이템의 총 개수 -1

            // 스크롤이 끝에 도달했는지 확인
            if (lastVisibleItemPosition == itemTotalCount) {

            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

        }
    }

    private fun searchListener() {
        fragmentSavedMoviesBinding.etSavedMovieSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if(p0?.length!! > 0 && !isLoading) {
                    showProgressBar()

                    MainScope().launch {
                        delay(2000)
                        mainViewModel.keyword.value = p0.toString()

                    }
                } else if(p0?.length!! == 0) {
                    mainViewModel.keyword.value = p0.toString()

                }
            }

        })
    }
}