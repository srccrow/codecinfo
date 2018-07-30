@file:Suppress("EnumEntryName")

package com.parseus.codecinfo.codecinfo.profilelevels

enum class VC1Profiles(val value: Int) {

    OMX_VIDEO_VC1ProfileUnused(0x00),
    OMX_VIDEO_VC1ProfileSimple(0x01),
    OMX_VIDEO_VC1ProfileMain(0x02),
    OMX_VIDEO_VC1ProfileAdvanced(0x03),
    OMX_VIDEO_VC1ProfileMax(0x7F000001);

    companion object {
        fun from(findValue: Int, extension: String = ""): String? = try {
            VC1Profiles.values().first {
                if (it.value > 0x7F000000) {
                    it.value == findValue && it.name.contains(extension, true)
                } else {
                    it.value == findValue
                }
            }.name
        } catch (e: NoSuchElementException) {
            null
        }
    }

}

enum class VC1Levels(val value: Int) {

    OMX_VIDEO_VC1LevelUnused(0x00),
    OMX_VIDEO_VC1LevelLow(0x01),
    OMX_VIDEO_VC1LevelMedium(0x02),
    OMX_VIDEO_VC1LevelHigh(0x03),
    OMX_VIDEO_VC1Level0(0x04),
    OMX_VIDEO_VC1Level1(0x05),
    OMX_VIDEO_VC1Level2(0x06),
    OMX_VIDEO_VC1Level3(0x07),
    OMX_VIDEO_VC1Level4(0x08),
    OMX_VIDEO_VC1LevelMax(0x7F000001);

    companion object {
        fun from(findValue: Int, extension: String = ""): String? = try {
            VC1Levels.values().first {
                if (it.value > 0x7F000000) {
                    it.value == findValue && it.name.contains(extension, true)
                } else {
                    it.value == findValue
                }
            }.name
        } catch (e: NoSuchElementException) {
            null
        }
    }

}