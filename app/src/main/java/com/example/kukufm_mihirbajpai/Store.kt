package com.example.kukufm_mihirbajpai

/*
 * Copyright 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.ui.graphics.vector.ImageVector

public val Icons.Filled.Store: ImageVector
    get() {
        if (_store != null) {
            return _store!!
        }
        _store = materialIcon(name = "Filled.Store") {
            materialPath {
                moveTo(20.0f, 6.0f)
                horizontalLineToRelative(-1.22f)
                lineToRelative(-1.5f, -3.0f)
                curveTo(17.09f, 2.36f, 16.57f, 2.0f, 16.0f, 2.0f)
                horizontalLineTo(8.0f)
                curveTo(7.43f, 2.0f, 6.91f, 2.36f, 6.72f, 2.86f)
                lineToRelative(-1.5f, 3.0f)
                horizontalLineTo(4.0f)
                curveTo(2.9f, 6.0f, 2.0f, 6.9f, 2.0f, 8.0f)
                verticalLineToRelative(2.0f)
                curveTo(2.0f, 10.33f, 2.18f, 10.64f, 2.5f, 10.78f)
                curveTo(2.82f, 10.92f, 3.18f, 10.83f, 3.41f, 10.59f)
                lineTo(5.0f, 9.0f)
                verticalLineToRelative(10.0f)
                curveTo(5.0f, 20.1f, 5.9f, 21.0f, 7.0f, 21.0f)
                horizontalLineTo(17.0f)
                curveTo(18.1f, 21.0f, 19.0f, 20.1f, 19.0f, 19.0f)
                verticalLineTo(9.0f)
                lineTo(20.59f, 10.59f)
                curveTo(20.82f, 10.83f, 21.18f, 10.92f, 21.5f, 10.78f)
                curveTo(21.82f, 10.64f, 22.0f, 10.33f, 22.0f, 10.0f)
                verticalLineTo(8.0f)
                curveTo(22.0f, 6.9f, 21.1f, 6.0f, 20.0f, 6.0f)
                close()
                moveTo(9.5f, 3.0f)
                horizontalLineToRelative(5.0f)
                lineToRelative(1.0f, 2.0f)
                horizontalLineTo(8.5f)
                lineTo(9.5f, 3.0f)
                close()
                moveTo(7.0f, 19.0f)
                verticalLineTo(11.0f)
                horizontalLineTo(9.0f)
                verticalLineTo(19.0f)
                horizontalLineTo(7.0f)
                close()
                moveTo(11.0f, 19.0f)
                verticalLineTo(11.0f)
                horizontalLineTo(13.0f)
                verticalLineTo(19.0f)
                horizontalLineTo(11.0f)
                close()
                moveTo(15.0f, 19.0f)
                verticalLineTo(11.0f)
                horizontalLineTo(17.0f)
                verticalLineTo(19.0f)
                horizontalLineTo(15.0f)
                close()
            }
        }
        return _store!!
    }

private var _store: ImageVector? = null
