package com.mesalabs.on.workshop.utils;

import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.system.Os;
import android.system.StructUtsname;
import android.text.format.DateFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mesalabs.cerberus.utils.PropUtils;

/*
 * On Workshop
 *
 * Coded by BlackMesa @2020
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 */

public class DeviceInfoUtils {
    private static String TAG = "FirmwareInfoUtils";

    private static String GALAXY_S8 = "Galaxy S8";
    private static String GALAXY_S8_PLUS = "Galaxy S8+";
    private static String GALAXY_NOTE8 = "Galaxy Note8";

    private static String EXYNOS8895 = "Exynos 8895";

    public static String getBasebandVersion() {
        return PropUtils.get("gsm.version.baseband", "Unknown");
    }

    public static String getDeviceManufacturingDate() {
        String rfcalDate = PropUtils.get("ril.rfcal_date", "");
        if (rfcalDate.isEmpty()) {
            return "Unknown";
        }
        try {
            return DateFormat.format(DateFormat.getBestDateTimePattern(Locale.getDefault(), "dMMMMyyyy"), new SimpleDateFormat("yyyyMMdd").parse(rfcalDate)).toString();
        } catch (ParseException e) {
            return rfcalDate;
        }
    }

    public static String getEnsoVersion() {
        int prop = PropUtils.getInt("ro.on.enso.version", 0);

        if (prop != 0) {
            return prop / 10000 + "." + (prop % 10000) / 100 + "." + (prop % 1000) / 100;
        } else
            return "Unknown";
    }

    public static String getKernelVersion() {
        StructUtsname uname = Os.uname();

        if (uname == null) {
            return "Unknown";
        }

        Matcher m = Pattern.compile("(#\\d+) (?:.*?)?((Sun|Mon|Tue|Wed|Thu|Fri|Sat).+)").matcher(uname.version);
        if (!m.matches()) {
            LogUtils.e(TAG, "Regex did not match on uname version " + uname.version);
            return "Unknown";
        }
        return uname.release + "\n" + m.group(1) + " " + m.group(2);
    }

    public static String getDeviceModelName() {
        String modelNumber = PropUtils.get("ro.boot.em.model", "");

        if (modelNumber.startsWith("SM-G950")) return GALAXY_S8;
        if (modelNumber.startsWith("SM-G955")) return GALAXY_S8_PLUS;
        if (modelNumber.startsWith("SM-N950")) return GALAXY_NOTE8;
        return "Unknown";
    }

    public static String getDeviceModelNumber() {
        return PropUtils.get("ro.boot.em.model", "Unknown");
    }

    public static String getDeviceSocName() {
        String modelNumber = PropUtils.get("ro.hardware", "");

        if (modelNumber.equals("samsungexynos8895")) return EXYNOS8895;
        return "Unknown";
    }

    public static String getOneUIVersion() {
        int prop = PropUtils.getInt("ro.build.version.sep", 0);

        if (prop != 0) {
            int oneUIversion = prop - 90000;
            return oneUIversion / 10000 + "." + (oneUIversion % 10000) / 100;
        } else
            return "Unknown";
    }

    public static String getROMVersion() {
        int prop = PropUtils.getInt("ro.on.core.version", 0);

        if (prop != 0) {
            return prop / 10000 + "." + (prop % 10000) / 100 + "." + (prop % 1000) / 100;
        } else
            return "Unknown";
    }

    public static String getSecurityPatchVersion() {
        String patch = Build.VERSION.SECURITY_PATCH;
        if (patch.isEmpty()) {
            return "Unknown";
        }
        try {
            return DateFormat.format(DateFormat.getBestDateTimePattern(Locale.getDefault(), "dMMMMyyyy"), new SimpleDateFormat("yyyy-MM-dd").parse(patch)).toString();
        } catch (ParseException e) {
            return patch;
        }
    }

}
