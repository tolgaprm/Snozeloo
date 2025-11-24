package com.prmto.snozeloo.data.repository

import android.content.Context
import android.media.RingtoneManager
import com.prmto.snozeloo.domain.model.Ringtone
import com.prmto.snozeloo.domain.repository.RingtoneRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import javax.inject.Inject

class RingtoneRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : RingtoneRepository {

    override suspend fun getRingtones(): ImmutableList<Ringtone> {
        val ringtoneManager = RingtoneManager(context)
        ringtoneManager.setType(RingtoneManager.TYPE_RINGTONE)
        val cursor = ringtoneManager.cursor
        val ringtones = mutableListOf<Ringtone>()

        while (cursor.moveToNext()) {
            val title = cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX)
            val uri = cursor.getString(RingtoneManager.URI_COLUMN_INDEX)
            val id = cursor.getString(RingtoneManager.ID_COLUMN_INDEX)
            ringtones.add(Ringtone(id = id, name = title, uri = "$uri/$id"))
        }

        return ringtones.toImmutableList()
    }
}
