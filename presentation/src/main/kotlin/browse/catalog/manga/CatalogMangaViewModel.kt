/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.browse.catalog.manga

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.launch
import tachiyomi.domain.catalog.interactor.GetLocalCatalog
import tachiyomi.domain.library.interactor.ChangeMangaFavorite
import tachiyomi.domain.manga.interactor.GetChapters
import tachiyomi.domain.manga.interactor.GetManga
import tachiyomi.ui.core.viewmodel.BaseViewModel
import javax.inject.Inject

class CatalogMangaViewModel @Inject constructor(
  private val params: Params,
  private val getManga: GetManga,
  private val getLocalCatalog: GetLocalCatalog,
  private val getChapters: GetChapters,
  private val changeMangaFavorite: ChangeMangaFavorite,
) : BaseViewModel() {

  var isRefreshing by mutableStateOf(false)
    private set

  val manga by getManga.subscribe(params.mangaId).asState(null)

  val chapters by getChapters.subscribeForManga(params.mangaId).asState(emptyList())

//  init {
//    scope.launch {
//      withContext(Dispatchers.IO) {
//        manga = getManga.await(params.mangaId)
//
//        manga?.let { manga ->
//          getLocalCatalog.get(manga.sourceId)?.source?.let { source ->
//            chapters = getChaptersFromSource.await(source, manga)
//          }
//        }
//      }
//    }
//  }

  fun favorite() {
    scope.launch {
      manga?.let {
        changeMangaFavorite.await(it)
      }
    }
  }

  data class Params(val mangaId: Long)
}
