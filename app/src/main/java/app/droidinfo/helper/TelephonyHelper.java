package app.droidinfo.helper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellSignalStrengthCdma;
import android.telephony.CellSignalStrengthGsm;
import android.telephony.CellSignalStrengthLte;
import android.telephony.TelephonyManager;

import app.droidinfo.R;

public class TelephonyHelper {

    @SuppressLint("MissingPermission")
    public static String getIMEI(Activity context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager.getDeviceId().equals("000000000000000")) {
            return context.getString(R.string.Unknown);
        } else {
            return telephonyManager.getDeviceId();
        }
    }

    public static String getStatus(Activity activity) {
        TelephonyManager telephonyManager = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
        int simState = telephonyManager.getSimState();
        switch (simState) {
            case TelephonyManager.SIM_STATE_ABSENT:
                return activity.getString(R.string.Absent);
            case TelephonyManager.SIM_STATE_CARD_IO_ERROR:
                return activity.getString(R.string.CardIOError);
            case TelephonyManager.SIM_STATE_CARD_RESTRICTED:
                return activity.getString(R.string.CardRestricted);
            case TelephonyManager.SIM_STATE_NETWORK_LOCKED:
                return activity.getString(R.string.NetworkLocked);
            case TelephonyManager.SIM_STATE_NOT_READY:
                return activity.getString(R.string.NotReady);
            case TelephonyManager.SIM_STATE_PERM_DISABLED:
                return activity.getString(R.string.PermDisabled);
            case TelephonyManager.SIM_STATE_PIN_REQUIRED:
                return activity.getString(R.string.PinRequired);
            case TelephonyManager.SIM_STATE_PUK_REQUIRED:
                return activity.getString(R.string.PukRequired);
            case TelephonyManager.SIM_STATE_READY:
                return activity.getString(R.string.Ready);
            case TelephonyManager.SIM_STATE_UNKNOWN:
                return activity.getString(R.string.Unknown);
            default:
                return activity.getString(R.string.Unknown);
        }
    }

    public static String getPhoneType(Activity activity) {
        TelephonyManager telephonyManager = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
        int phoneType = telephonyManager.getPhoneType();

        switch (phoneType) {
            case TelephonyManager.PHONE_TYPE_CDMA:
                return "CDMA";
            case TelephonyManager.PHONE_TYPE_GSM:
                return "GSM";
            default:
                return activity.getString(R.string.Unknown);
        }
    }

    public static String getOperator(Context context) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            //String operator = "";
            //SubscriptionManager subscriptionManager = SubscriptionManager.from(context);
            //if (getDualSIM(context).equals(String.valueOf(R.string.Yes))) {
            //    SubscriptionInfo sim1 = subscriptionManager.getActiveSubscriptionInfo(0);
            //    SubscriptionInfo sim2 = subscriptionManager.getActiveSubscriptionInfo(1);
            //    return sim1.getCarrierName() + " | " + sim2.getCarrierName();
            //} else {
            //    SubscriptionInfo sim1 = subscriptionManager.getActiveSubscriptionInfo(0);
                //return sim1.getCarrierName() + " (" + sim1.getNumber() + ")";
                return "Gesu";
            //}
        }
        else {
            return context.getString(R.string.RequiredAPI22);
        }

    }

    public static String getPhoneNumber(Activity activity) {
        TelephonyManager telephonyManager = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
        String phoneNumber = telephonyManager.getLine1Number();
        if (!phoneNumber.equals("")) return phoneNumber;
        else activity.getString(R.string.Unknown); return activity.getString(R.string.Unknown);
    }

    public static String getNetworkType(Activity activity) {
        TelephonyManager telephonyManager = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
        int networkType = telephonyManager.getNetworkType();
        switch (networkType) {
            case TelephonyManager.NETWORK_TYPE_1xRTT:
                return "1xRTT";
            case TelephonyManager.NETWORK_TYPE_CDMA:
                return "CMDA";
            case TelephonyManager.NETWORK_TYPE_EDGE:
                return "EDGE";
            case TelephonyManager.NETWORK_TYPE_EHRPD:
                return "eHRPD";
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
                return "EVDO rev. 0";
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
                return "EVDO rev. A";
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
                return "EVDO rev. B";
            case TelephonyManager.NETWORK_TYPE_GPRS:
                return "GPRS";
            case TelephonyManager.NETWORK_TYPE_HSDPA:
                return "HSDPA";
            case TelephonyManager.NETWORK_TYPE_HSPA:
                return "HSPA";
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return "HSPA+";
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return "iDen";
            case TelephonyManager.NETWORK_TYPE_LTE:
                return "LTE";
            case TelephonyManager.NETWORK_TYPE_UMTS:
                return "UMTS";
            default:
                return activity.getString(R.string.Unknown);
        }
    }

    public static String getSignalStength(Activity activity) {
        WifiManager wifiManager = (WifiManager) activity.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifiManager.isWifiEnabled()) {
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            if (String.valueOf(wifiInfo.getSupplicantState()).equals("COMPLETED")) {
                return String.valueOf(wifiInfo.getRssi() + " dBm");
            }
        }

        TelephonyManager telephonyManager = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
        String phoneType = getPhoneType(activity);
        switch (phoneType) {
            case "CDMA":
                CellInfoCdma cellInfoCdma = (CellInfoCdma) telephonyManager.getAllCellInfo().get(0);
                CellSignalStrengthCdma cellSignalStrengthCdma = cellInfoCdma.getCellSignalStrength();
                return cellSignalStrengthCdma.getDbm() + " / " + cellSignalStrengthCdma.getAsuLevel() + " asu";
            case "GSM":
                if (telephonyManager.getNetworkType() == TelephonyManager.NETWORK_TYPE_LTE) {
                    CellInfoLte cellInfoLte = (CellInfoLte) telephonyManager.getAllCellInfo().get(0);
                    CellSignalStrengthLte cellSignalStrengthLte = cellInfoLte.getCellSignalStrength();
                    return cellSignalStrengthLte.getDbm() + " dBm / " + cellSignalStrengthLte.getAsuLevel() + " asu";
                } else if (telephonyManager.getNetworkType() != 0){
                    CellInfoGsm cellInfoGsm = (CellInfoGsm) telephonyManager.getAllCellInfo().get(0);
                    CellSignalStrengthGsm cellSignalStrengthGsm = cellInfoGsm.getCellSignalStrength();
                    return cellSignalStrengthGsm.getDbm() + " dBm / " + cellSignalStrengthGsm.getAsuLevel() + "asu";
                }
            default:
                activity.getString(R.string.Unknown);
                break;
        }
        return activity.getString(R.string.Unknown);
    }

}
