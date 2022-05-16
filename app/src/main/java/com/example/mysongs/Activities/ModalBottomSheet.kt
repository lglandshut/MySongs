package org.geeksforgeeks.gfgmodalsheet

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mysongs.Comparator.ComparatorArtist
import com.example.mysongs.Comparator.ComparatorTitle
import com.example.mysongs.Songs.Song
import com.example.mysongs.Utils.ObjectBoxUtils
import com.example.mysongs.Utils.SongListUtils
import com.example.mysongs.databinding.ActivityMainBinding
import com.example.mysongs.databinding.ModalBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ModalBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding: ModalBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {

        binding = ModalBottomSheetBinding.inflate(layoutInflater)
        val compTitleAsc = binding.textViewTitleAsc
        val compTitleDesc = binding.textViewTitleDesc
        val compArtistAsc = binding.textViewArtistAsc
        val compArtistDesc = binding.textViewArtistDesc

        compTitleAsc.setOnClickListener {
            SongListUtils.sortSongs(ComparatorTitle())
            ObjectBoxUtils.updateComparatorDB("TitleAsc")
            dismiss()
        }
        compTitleDesc.setOnClickListener {
            SongListUtils.sortSongs(ComparatorTitle(false))
            ObjectBoxUtils.updateComparatorDB("TitleDesc")
            dismiss()
        }
        compArtistAsc.setOnClickListener {
            SongListUtils.sortSongs(Comparator.nullsLast(ComparatorArtist()))
            ObjectBoxUtils.updateComparatorDB("ArtistAsc")
            dismiss()
        }
        compArtistDesc.setOnClickListener {
            SongListUtils.sortSongs(Comparator.nullsLast(ComparatorArtist(false)))
            ObjectBoxUtils.updateComparatorDB("ArtistDesc")
            dismiss()
        }
        return binding.root
    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }
}