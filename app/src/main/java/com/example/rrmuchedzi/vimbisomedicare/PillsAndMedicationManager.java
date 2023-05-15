package com.example.rrmuchedzi.vimbisomedicare;

import android.util.Log;

class PillsAndMedicationManager {
    private static final String TAG = "PillsAndMedicationManag";
    static int getPillIndentity( final int selectedPill ) {

        Integer resourceIdentifier;

        switch ( selectedPill ) {
            case ProgramIcons.GREEN_PILL: {
                resourceIdentifier = R.drawable.drugpill_green;
                break;
            }
            case ProgramIcons.ORANGE_PILL: {
                resourceIdentifier = R.drawable.drugpill_orange;
                break;
            }
            case ProgramIcons.RED_PILL: {
                resourceIdentifier = R.drawable.drugpill_red;
                break;
            }
            case ProgramIcons.PURPLE_PILL: {
                resourceIdentifier = R.drawable.drugpill_purple;
                break;
            }
            case ProgramIcons.BLUE_PILL: {
                resourceIdentifier = R.drawable.drugpill_blue;
                break;
            }
            default: {
                Log.e(TAG, "getPillIndentity: Value passed is Unknown for Pill Selection");
                resourceIdentifier = R.drawable.drugpill_blue;
            }
        }
        return resourceIdentifier;
    }

    private static class ProgramIcons {
        private static final int BLUE_PILL = 1;
        private static final int GREEN_PILL = 2;
        private static final int ORANGE_PILL = 3;
        private static final int PURPLE_PILL = 4;
        private static final int RED_PILL = 5;
    }
}
