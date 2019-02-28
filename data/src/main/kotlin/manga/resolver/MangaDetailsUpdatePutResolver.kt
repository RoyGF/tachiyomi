/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.data.manga.resolver

import android.content.ContentValues
import com.pushtorefresh.storio3.sqlite.StorIOSQLite
import com.pushtorefresh.storio3.sqlite.operations.put.PutResolver
import com.pushtorefresh.storio3.sqlite.operations.put.PutResult
import com.pushtorefresh.storio3.sqlite.queries.UpdateQuery
import tachiyomi.data.manga.table.MangaTable
import tachiyomi.domain.manga.model.Manga

internal class MangaDetailsUpdatePutResolver : PutResolver<Manga>() {

  override fun performPut(db: StorIOSQLite, manga: Manga): PutResult {
    val updateQuery = mapToUpdateQuery(manga)
    val contentValues = mapToContentValues(manga)

    val numberOfRowsUpdated = db.lowLevel().update(updateQuery, contentValues)
    return PutResult.newUpdateResult(numberOfRowsUpdated, updateQuery.table())
  }

  fun mapToUpdateQuery(manga: Manga): UpdateQuery {
    return UpdateQuery.builder()
      .table(MangaTable.TABLE)
      .where("${MangaTable.COL_ID} = ?")
      .whereArgs(manga.id)
      .build()
  }

  fun mapToContentValues(manga: Manga): ContentValues {
    return ContentValues(9).apply {
      put(MangaTable.COL_KEY, manga.key)
      put(MangaTable.COL_TITLE, manga.title)
      put(MangaTable.COL_ARTIST, manga.artist)
      put(MangaTable.COL_AUTHOR, manga.author)
      put(MangaTable.COL_DESCRIPTION, manga.description)
      put(MangaTable.COL_GENRES, manga.genres.joinToString(separator = ";"))
      put(MangaTable.COL_STATUS, manga.status)
      put(MangaTable.COL_COVER, manga.cover)
      put(MangaTable.COL_LAST_INIT, manga.lastInit)
    }
  }

}
