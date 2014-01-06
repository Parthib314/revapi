/*
 * Copyright 2014 Lukas Krejci
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package org.revapi.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.revapi.Archive;

/**
 * @author Lukas Krejci
 * @since 1.0
 */
final class FileArchive implements Archive {

    private final File file;

    public FileArchive(File f) {
        file = f;
    }

    @Override
    public String getName() {
        return file.getName();
    }

    @Override
    public InputStream openStream() throws IOException {
        return new FileInputStream(file);
    }
}
