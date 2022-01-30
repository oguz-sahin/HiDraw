/**
 * Created by Oguz Sahin on 12/20/2021.
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
    const val version_name = "${version_major}.${version_minor}.${version_patch}"
}
