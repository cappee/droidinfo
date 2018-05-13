package it.k4ppaj.droidinfo.helper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;

import java.util.List;

import it.k4ppaj.droidinfo.R;
import it.k4ppaj.droidinfo.util.DualSIMInfo;

public class TelephonyHelper {

    public static String getDualSIM(Context context) {
        Activity activity = (Activity) context;
        DualSIMInfo dualSIMInfo = DualSIMInfo.getInstance(context);
        if (dualSIMInfo != null) {
            if (dualSIMInfo.isDualSIM()) {
                return activity.getString(R.string.Yes);
            } else {
                return activity.getString(R.string.No);
            }
        } else {
            return activity.getString(R.string.No);
        }
    }

    @SuppressLint("MissingPermission")
    public static String getIMEI(Activity context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
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
        String operator = "";
        SubscriptionManager subscriptionManager = SubscriptionManager.from(context);
        List<SubscriptionInfo> activeSubscruptionInfo = subscriptionManager.getActiveSubscriptionInfoList();
        for (SubscriptionInfo subscriptionInfo : activeSubscruptionInfo) {
            operator = String.valueOf(subscriptionInfo.getCarrierName());
        }
        return operator;
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
            case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                return activity.getString(R.string.Unknown);
            default:
                return activity.getString(R.string.Unknown);
        }
    }

    /*public static String getSisgnalStength(Activity activity) {
        TelephonyManager telephonyManager = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
        String phoneType = getPhoneType(activity);
        switch (phoneType) {
            case "CDMA":
                CellInfoCdma cellInfoCdma = (CellInfoCdma) telephonyManager.getAllCellInfo().get(0);
                CellSignalStrengthCdma cellSignalStrengthCdma = cellInfoCdma.getCellSignalStrength();
                return cellSignalStrengthCdma.getDbm() + " / " + cellSignalStrengthCdma.getAsuLevel();
            case "GSM":
                CellInfoGsm cellInfoGsm = (CellInfoGsm) telephonyManager.getAllCellInfo().get(0);
                CellSignalStrengthGsm cellSignalStrengthGsm = cellInfoGsm.getCellSignalStrength();
                return cellSignalStrengthGsm.getDbm() + " dBm";
            default:
                activity.getString(R.string.Unknown);
                break;
        }
        return activity.getString(R.string.Unknown);
    }*/

}
