/*
 * Copyright 2022. Explore in HMS. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

object Config {

    const val compile_sdk_version = 31
    const val application_id = "com.huawei.hidraw"
    const val min_sdk_version = 21
    const val target_sdk_version = 31

    private const val version_major = 1
    private const val version_minor = 0
    private const val version_patch = 0

    const val version_code = version_major * 10000 + version_minor * 100 + version_patch
    const val version_name = "$version_major.$version_minor.$version_patch"
}
