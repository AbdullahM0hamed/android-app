/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.library

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AmbientTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tachiyomi.domain.library.model.LibraryManga
import tachiyomi.ui.core.coil.CoilImage
import tachiyomi.ui.core.coil.MangaCover
import tachiyomi.ui.core.components.AutofitGrid
import tachiyomi.ui.core.util.Typefaces

@Composable
fun LibraryMangaComfortableGrid(
  library: List<LibraryManga>,
  onClickManga: (LibraryManga) -> Unit = {}
) {
  AutofitGrid(
    data = library,
    modifier = Modifier.fillMaxSize().padding(4.dp),
    defaultColumnWidth = 160.dp
  ) { manga ->
    LibraryMangaComfortableGridItem(
      manga = manga,
      unread = null, // TODO
      downloaded = null, // TODO
      onClick = { onClickManga(manga) }
    )
  }
}

@Composable
private fun LibraryMangaComfortableGridItem(
  manga: LibraryManga,
  unread: Int?,
  downloaded: Int?,
  onClick: () -> Unit = {},
) {
  val cover = remember { MangaCover.from(manga) }
  val fontStyle = AmbientTextStyle.current.merge(
    TextStyle(letterSpacing = 0.sp, fontFamily = Typefaces.ptSansFont, fontSize = 14.sp)
  )

  Box(modifier = Modifier.padding(4.dp)
    .fillMaxWidth()
    .clickable(onClick = onClick)
  ) {
    Column {
      CoilImage(
        model = cover,
        modifier = Modifier.aspectRatio(3f / 4f).clip(MaterialTheme.shapes.medium)
      )
      Text(
        text = manga.title,
        style = fontStyle,
        maxLines = 3,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 4.dp)
      )
    }
    LibraryMangaBadges(
      unread = unread,
      downloaded = downloaded,
      modifier = Modifier.padding(4.dp)
    )
  }
}
