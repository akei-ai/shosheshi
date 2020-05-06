package com.github.doomsdayrs.apps.shosetsu.viewmodel

import android.util.Log
import androidx.lifecycle.*
import app.shosetsu.lib.Formatter
import com.github.doomsdayrs.apps.shosetsu.backend.async.CatalogueLoader
import com.github.doomsdayrs.apps.shosetsu.common.dto.HResult
import com.github.doomsdayrs.apps.shosetsu.common.ext.defaultListing
import com.github.doomsdayrs.apps.shosetsu.common.ext.smallMessage
import com.github.doomsdayrs.apps.shosetsu.domain.repository.model.NovelsRepository
import com.github.doomsdayrs.apps.shosetsu.variables.recycleObjects.NovelListingCard
import com.github.doomsdayrs.apps.shosetsu.viewmodel.base.ICatalogViewModel
import kotlinx.coroutines.Dispatchers
import org.luaj.vm2.LuaError

/*
 * This file is part of shosetsu.
 *
 * shosetsu is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * shosetsu is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with shosetsu.  If not, see <https://www.gnu.org/licenses/>.
 */

/**
 * shosetsu
 * 01 / 05 / 2020
 */
class CatalogViewModel()
	: ViewModel(), ICatalogViewModel {
	inner class PageLoader(
			val currentMaxPage: Int = 1,
			val formatter: Formatter,
			val filterValues: Array<*>,
			val selectedListing: Int = formatter.defaultListing,
			val novelsRepository: NovelsRepository
	) {
		suspend fun execute() {
			try {
				val loader = CatalogueLoader(formatter, filterValues, selectedListing)
				val novels =
						if (v.isNotEmpty())
							loader.execute(v[0])
						else loader.execute()
				it.recyclerArray.addAll(novels.map {
					with(it) {
						NovelListingCard(imageURL, title, ID, link)
					}
				})
				Log.d("FragmentRefresh", "Complete")
				true
			} catch (e: LuaError) {
				catalogController.activity?.toast(e.smallMessage())
				Log.e("CataloguePageLoader", e.message ?: "UNKNOWN ERROR")
				false
			} catch (e: Exception) {
				catalogController.activity?.toast(e.message ?: "UNKNOWN ERROR")
				false
			}
		}
	}

	override var currentMaxPage: Int = 1

	override val daoLiveData: LiveData<HResult<List<NovelListingCard>>> by lazy {
		liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {

		}
	}

	override fun subscribeDao(
			owner: LifecycleOwner,
			observer: Observer<HResult<List<NovelListingCard>>>
	) {
		TODO("Not yet implemented")
	}

	override fun loadMore() {
		TODO("Not yet implemented")
	}

	override fun clearAndLoad() {
		TODO("Not yet implemented")
	}

	override fun subscribeObserver(owner: LifecycleOwner, observer: Observer<List<NovelListingCard>>) {
		TODO("Not yet implemented")
	}


	override suspend fun getLiveData(): List<NovelListingCard> {
		TODO("Not yet implemented")
	}

	override fun loadDataSnap(): HResult<List<NovelListingCard>> {
		TODO("Not yet implemented")
	}

}